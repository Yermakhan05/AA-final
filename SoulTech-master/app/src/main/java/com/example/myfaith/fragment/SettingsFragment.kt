package com.example.myfaith.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.myfaith.R
import com.example.myfaith.databinding.FragmentSettingsBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        activity?.findViewById<BottomNavigationView>(R.id.bottom_nav_view)?.visibility = View.GONE

        binding.accountSettings.setOnClickListener {
            findNavController().navigate(R.id.account_settings)
        }

        binding.notificationSettings.setOnClickListener {
            findNavController().navigate(R.id.notification_settings)
        }

        binding.privacySettings.setOnClickListener {
            findNavController().navigate(R.id.privacy_settings)
        }

        binding.supportSettings.setOnClickListener {
            findNavController().navigate(R.id.support_settings)
        }

        binding.appSettings.setOnClickListener {
            findNavController().navigate(R.id.app_settings)
        }
    }
    override fun onResume() {
        super.onResume()
        activity?.findViewById<BottomNavigationView>(R.id.bottom_nav_view)?.visibility = View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
