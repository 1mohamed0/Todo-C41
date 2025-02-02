package com.route.todoc41.ui.home.fragments

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.app.ActivityCompat.recreate
import androidx.fragment.app.Fragment
import com.route.todoc41.R
import com.route.todoc41.databinding.FragmentSettingsBinding
import java.util.Locale


class SettingsFragment:Fragment() {
    lateinit var binding: FragmentSettingsBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSettingsBinding.inflate(inflater,container,false)
        val language = resources.getStringArray(R.array.languages)
        val languageCode = resources.getStringArray(R.array.language_code)
        val autoCompleteTextView = binding.autoCompleteTVLanguages
        val adapter = ArrayAdapter(requireContext(),R.layout.drop_down_item,language)
        autoCompleteTextView.setAdapter(adapter)
        autoCompleteTextView.setOnItemClickListener { _, _, position, _ ->
            val selectedLanguageCode = languageCode[position]
            setAppLanguage(selectedLanguageCode)
        }
        val themes = resources.getStringArray(R.array.modes)

        // Set up AutoCompleteTextView adapter
        val adapter2 = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, themes)
        binding.autoCompleteTVModes.setAdapter(adapter2)

        // Handle theme selection
        binding.autoCompleteTVModes.setOnItemClickListener { _, _, position, _ ->
            val selectedMode = when (position) {
                0 -> AppCompatDelegate.MODE_NIGHT_NO // Light mode
                1 -> AppCompatDelegate.MODE_NIGHT_YES // Dark mode
                2 -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM // System default
                else -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
            }
            setThemeMode(selectedMode)
        }

        return binding.root

    }

    private fun setAppLanguage(languageCode: String) {
        val locale = Locale(languageCode)
        Locale.setDefault(locale)
        val config = Configuration()
        config.locale = locale
        resources.updateConfiguration(config, resources.displayMetrics)

        // Save the selected language in SharedPreferences
        val sharedPreferences = requireContext().getSharedPreferences("Settings", Context.MODE_PRIVATE)
        sharedPreferences.edit().putString("Language", languageCode).apply()

        // Restart the activity to apply the language change
        activity?.recreate()
    }


    private fun setThemeMode(mode: Int) {
        // Apply the theme globally
        AppCompatDelegate.setDefaultNightMode(mode)

        // Restart the activity to apply the new theme
        requireActivity().recreate()
    }

}