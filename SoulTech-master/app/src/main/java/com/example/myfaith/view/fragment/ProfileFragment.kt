package com.example.myfaith.view.fragment

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.myfaith.model.datasource.ApiSource
import com.example.myfaith.model.entity.response.ProfileResponse
import com.example.myfaith.R
import com.example.myfaith.databinding.ProfileFragmentBinding
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class ProfileFragment : Fragment() {

    private var _binding: ProfileFragmentBinding? = null
    private val binding get() = _binding!!

    private val PICK_IMAGE = 100
    private var imageUri: Uri? = null
    private var isEditing = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ProfileFragmentBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.profilePhoto.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, PICK_IMAGE)
        }

        binding.editProfileButton.setOnClickListener {
            if (!isEditing) {
                enableEditing(true)
                binding.editProfileButton.text = "${R.string.save}"
            } else {
                updateProfile()
                enableEditing(false)
                binding.editProfileButton.text = "${R.string.edit}"
            }
        }


        fetchProfile()

        return view
    }

    private fun fetchProfile() {
        lifecycleScope.launch {
            try {
                val response = ApiSource.profileApi.getProfile()

                if (response.isSuccessful) {
                    val profileData = response.body()
                    profileData?.let {
                        binding.profileName.setText(it.fullName)
                        binding.profileNumber.setText(it.number ?: "")
                        binding.profileBio.setText(it.bio ?: "")
                        if (!it.profilePicture.isNullOrEmpty()) {
                            try {
                                val decodedBytes = Base64.decode(it.profilePicture, Base64.DEFAULT)
                                val bitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
                                Glide.with(requireContext())
                                    .load(bitmap)
                                    .placeholder(R.drawable.compass)
                                    .circleCrop()
                                    .into(binding.profilePhoto)
                            } catch (e: Exception) {
                                Log.e("ProfileFetch", "Error decoding base64 image", e)
                                binding.profilePhoto.setImageResource(R.drawable.compass)
                            }
                        } else {
                            binding.profilePhoto.setImageResource(R.drawable.compass)
                        }

                    } ?: run {
                        Toast.makeText(requireContext(), "Profile data not found", Toast.LENGTH_SHORT).show()
                        Log.w("ProfileFetch", "Successful response but body is null")
                    }
                } else {
                    Toast.makeText(requireContext(), "Failed to load profile: ${response.code()}", Toast.LENGTH_SHORT).show()
                    Log.e("ProfileFetch", "API response error: ${response.code()} - ${response.message()}")
                    try {
                        Log.e("ProfileFetch", "Error Body: ${response.errorBody()?.string()}")
                    } catch (e: Exception) {
                        Log.e("ProfileFetch", "Error reading error body", e)
                    }
                }
            } catch (e: Exception) {
                Toast.makeText(requireContext(), "Error fetching profile: ${e.message}", Toast.LENGTH_SHORT).show()
                Log.e("ProfileFetch", "Exception: ${e.message}", e)
            }
        }
    }


    private fun updateProfile() {
        val name = RequestBody.create("text/plain".toMediaTypeOrNull(), binding.profileName.text.toString())
        val phone = RequestBody.create("text/plain".toMediaTypeOrNull(), binding.profileNumber.text.toString())
        val bio = RequestBody.create("text/plain".toMediaTypeOrNull(), binding.profileBio.text.toString())

        val base64Image = imageUri?.let { uriToBase64(it) }
        val imageRequestBody = base64Image?.let {
            RequestBody.create("text/plain".toMediaTypeOrNull(), it)
        }


        ApiSource.profileApi.updateProfile(name, phone, bio, imageRequestBody).enqueue(object : Callback<ProfileResponse> {
            override fun onResponse(call: Call<ProfileResponse>, response: Response<ProfileResponse>) {
                if (!isAdded || context == null) return

                if (response.isSuccessful) {
                    Toast.makeText(context, "Profile updated", Toast.LENGTH_SHORT).show()
                    if (imageRequestBody != null) {
                        imageUri = null
                    }

                } else {
                    Toast.makeText(context, "Update failed: ${response.code()}", Toast.LENGTH_SHORT).show()
                    Log.e("ProfileUpdate", "API response error: ${response.code()} - ${response.message()}")
                    try {
                        Log.e("ProfileUpdate", "Error Body: ${response.errorBody()?.string()}")
                    } catch (e: Exception) {
                        Log.e("ProfileUpdate", "Error reading error body", e)
                    }
                }
            }

            override fun onFailure(call: Call<ProfileResponse>, t: Throwable) {
                if (!isAdded || context == null) return
                Toast.makeText(context, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
                Log.e("ProfileUpdate", "Network/Request failure: ${t.message}", t)
            }
        })
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE && resultCode == Activity.RESULT_OK && data != null) {
            imageUri = data.data
            imageUri?.let {
                Glide.with(this)
                    .load(it)
                    .placeholder(R.drawable.compass)
                    .circleCrop()
                    .into(binding.profilePhoto)
            }
        }
    }

    private fun enableEditing(enabled: Boolean) {
        binding.profileName.isEnabled = enabled
        binding.profileNumber.isEnabled = enabled
        binding.profileBio.isEnabled = enabled
        binding.profilePhoto.isClickable = enabled
        isEditing = enabled
    }

    private fun uriToBase64(uri: Uri): String? {
        return try {
            val inputStream = requireContext().contentResolver.openInputStream(uri)
            val bytes = inputStream?.readBytes()
            inputStream?.close()
            bytes?.let {
                android.util.Base64.encodeToString(it, android.util.Base64.NO_WRAP)
            }
        } catch (e: Exception) {
            Log.e("ProfileUpdate", "Failed to convert image to Base64", e)
            null
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}