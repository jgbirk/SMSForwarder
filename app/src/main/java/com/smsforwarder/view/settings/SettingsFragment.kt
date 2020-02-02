package com.smsforwarder.view.settings

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.smsforwarder.R
import com.smsforwarder.databinding.FragmentSettingsBinding
import com.smsforwarder.fragment.BaseFragment

class SettingsFragment : BaseFragment() {

    private lateinit var binding: FragmentSettingsBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_settings, container, false)

        with(binding) {
            val preferences = requireContext().getSharedPreferences("sms_pref", Context.MODE_PRIVATE)

            serverUrl.setText(preferences.getString("server_url", "https://budgetalarms.woodview.be"))
            sendersNumber.setText(preferences.getString("senders_number", ""))

            save.setOnClickListener {
                preferences
                    .edit()
                    .putString("server_url", serverUrl.text.toString())
                    .putString("senders_number", sendersNumber.text.toString())
                    .apply()

                findNavController().popBackStack()
            }
        }

        return binding.root
    }
}
