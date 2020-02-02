package com.smsforwarder.view.settings

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.smsforwarder.R
import com.smsforwarder.databinding.FragmentSettingsBinding
import com.smsforwarder.fragment.BaseFragment
import com.smsforwarder.view.main.MainActivity

class SettingsFragment : BaseFragment() {

    private lateinit var binding: FragmentSettingsBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_settings, container, false)

        (requireActivity() as MainActivity).hideSendButton()

        setHasOptionsMenu(true)

        with(binding) {
            val preferences = requireContext().getSharedPreferences("sms_pref", Context.MODE_PRIVATE)

            serverUrl.setText(preferences.getString("server_url", "https://budgetalarms.woodview.be"))
            sendersNumber.setText(preferences.getString("senders_number", ""))
            autoSyncInterval.setText(preferences.getLong("auto_sync_interval", 1).toString())

            save.setOnClickListener {
                preferences
                    .edit()
                    .putString("server_url", serverUrl.text.toString())
                    .putString("senders_number", sendersNumber.text.toString())
                    .putLong("auto_sync_interval", autoSyncInterval.text.toString().toLong())
                    .apply()

                findNavController().popBackStack()
            }
        }

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()
    }
}
