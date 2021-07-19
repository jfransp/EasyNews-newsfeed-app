package com.example.newsfeed.ui.activitylevelUI.settings

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.example.newsfeed.R
import com.example.newsfeed.databinding.FragmentSettingsScreenBinding
import com.example.newsfeed.ui.activitylevelUI.MainViewModel

class SettingsScreenFragment: Fragment(R.layout.fragment_settings_screen) {

    val viewModel: MainViewModel by activityViewModels()

    private var _binding: FragmentSettingsScreenBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentSettingsScreenBinding.bind(view)

        setupButtons()

    }

    fun setupButtons() {
        binding.apply {
            countryButton.setOnClickListener {
                val action = SettingsScreenFragmentDirections.actionSettingsScreenFragmentToCountryFragment()
                view?.findNavController()?.navigate(action)
            }
            categoryButton.setOnClickListener {
                val action = SettingsScreenFragmentDirections.actionSettingsScreenFragmentToCategoryFragment()
                view?.findNavController()?.navigate(action)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
            _binding = null
    }

}
