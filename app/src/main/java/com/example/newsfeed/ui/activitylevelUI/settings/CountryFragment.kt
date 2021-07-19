package com.example.newsfeed.ui.activitylevelUI.settings

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.newsfeed.R
import com.example.newsfeed.databinding.FragmentSettingsCountryScreenBinding
import com.example.newsfeed.ui.activitylevelUI.MainViewModel
import com.example.newsfeed.util.adaptPreferenceFromDataStore
import com.example.newsfeed.util.adaptPreferenceToDataStore

class CountryFragment: Fragment(R.layout.fragment_settings_country_screen) {

    val viewModel: MainViewModel by activityViewModels()

    private var _binding: FragmentSettingsCountryScreenBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentSettingsCountryScreenBinding.bind(view)

        setupSwitches()

    }

    fun setupSwitches() {
        viewModel.userPreferencesLiveData.observe(viewLifecycleOwner, { UserPreferences ->
            val countryPreference = UserPreferences.country
            binding.radioGroup.check(adaptPreferenceFromDataStore(countryPreference))
        })

        binding.radioGroup.setOnCheckedChangeListener { _, checkedId ->
            viewModel.setCountryPreference(adaptPreferenceToDataStore(checkedId))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
