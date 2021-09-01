package com.sberkozd.lettervault.ui.settings

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.sberkozd.lettervault.MainActivity
import com.sberkozd.lettervault.R
import com.sberkozd.lettervault.databinding.FragmentSettingsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)

        preferenceManager.findPreference<Preference>("darkMode")?.let { pref ->
            pref.setOnPreferenceChangeListener { preference, isChecked ->
                if(isChecked as Boolean){
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                }else{
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                }
                true
            }
        }

    }


}



//    private val settingsViewModel: SettingsViewModel by viewModels()
//
//    private var _binding: FragmentSettingsBinding? = null
//
//    private val binding get() = _binding!!
//
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//    }
//
//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View {
//        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
//        return binding.root
//    }
//
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        binding.notificationSwitch.setOnCheckedChangeListener { buttonView, isChecked ->
//
//            if (isChecked)
//                Toast.makeText(context, "Notifications On!", Toast.LENGTH_SHORT).show()
//            else
//                Toast.makeText(context, "Notifications Off!", Toast.LENGTH_SHORT).show()
//        }
//
//        binding.darkModeSwitch.setOnCheckedChangeListener { buttonView, isChecked ->
//
//            if (isChecked)
//                activity?.setTheme(R.style.Theme_LetterVault_DarkMode)
//            else
//                activity?.setTheme(R.style.Theme_LetterVault)
//        }
//    }
//
//    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
//        setPreferencesFromResource(R.xml.preferences, rootKey)
//    }
//}