package com.example.myfaith.view.fragment

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.myfaith.model.utils.LocaleHelper
import com.example.myfaith.R
import com.example.myfaith.databinding.FragmentAppSettingsBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class AppSettingsFragment : Fragment() {

    private var _binding: FragmentAppSettingsBinding? = null
    private val binding get() = _binding!!

    private val wallpapers = listOf(
        R.drawable.zikr_bakground,
        R.drawable.compass_background,
        R.drawable.login_background
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAppSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        activity?.findViewById<BottomNavigationView>(R.id.bottom_nav_view)?.visibility = View.GONE

        binding.languageSelection.setOnClickListener {
            showLanguageDialog()
        }

        binding.wallpaperSelection.setOnClickListener {
            showWallpaperDialog()
        }
    }
    override fun onResume() {
        super.onResume()
        activity?.findViewById<BottomNavigationView>(R.id.bottom_nav_view)?.visibility = View.GONE
    }

    private fun showLanguageDialog() {
        val languages = arrayOf("Қазақша", "Русский", "English")
        AlertDialog.Builder(requireContext())
            .setTitle(getString(R.string.change_language))
            .setItems(languages) { _, which ->
                when (which) {
                    0 -> setLanguage("kk")
                    1 -> setLanguage("ru")
                    2 -> setLanguage("en")
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun setLanguage(langCode: String) {
        val sharedPref = requireContext().getSharedPreferences("settings", Context.MODE_PRIVATE)
        sharedPref.edit().putString("lang", langCode).apply()

        LocaleHelper.setLocale(requireContext(), langCode)

        activity?.recreate()
        Toast.makeText(requireContext(), "Language set to $langCode", Toast.LENGTH_SHORT).show()
    }

    private fun showWallpaperDialog() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle(getString(R.string.change_wallpaper))

        val layout = LinearLayout(requireContext()).apply {
            orientation = LinearLayout.HORIZONTAL
            val padding = resources.getDimensionPixelSize(R.dimen.padding_small)
            setPadding(padding, padding, padding, padding)
        }

        wallpapers.forEach { resId ->
            val itemView = layoutInflater.inflate(R.drawable.wallpaper_item, layout, false)
            val imageView = itemView.findViewById<android.widget.ImageView>(R.id.wallpaper_preview)
            imageView.setImageResource(resId)
            itemView.setOnClickListener {
                setWallpaper(resId)
            }
            layout.addView(itemView)
        }

        val scrollView = android.widget.HorizontalScrollView(requireContext())
        scrollView.addView(layout)

        builder.setView(scrollView)
        builder.setNegativeButton("Cancel", null)
        builder.show()
    }


    private fun setWallpaper(resId: Int) {
        val sharedPref = requireContext().getSharedPreferences("settings", Context.MODE_PRIVATE)
        sharedPref.edit().putInt("wallpaper", resId).apply()

        Toast.makeText(requireContext(), "Wallpaper changed", Toast.LENGTH_SHORT).show()

        activity?.recreate()
        Toast.makeText(requireContext(), "Wallpaper changed", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
