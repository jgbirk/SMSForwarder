package com.smsforwarder.view.main

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.smsforwarder.R
import com.smsforwarder.activity.BaseActivity
import com.smsforwarder.databinding.ActivityMainBinding

class MainActivity : BaseActivity() {

    companion object {
        private const val SMS_PERMISSION = 0
    }

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        setSupportActionBar(binding.toolbar)

        binding.fab.setOnClickListener {
            val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
            (navHostFragment.childFragmentManager.fragments[0] as MainFragment).sendSMS()
        }

        NavigationUI.setupWithNavController(binding.toolbar, Navigation.findNavController(this, R.id.nav_host_fragment))

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED) {
            when {
                ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.RECEIVE_SMS) -> {
                    // TODO: Why you need this?
                }
                else -> {
                    ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.RECEIVE_SMS), SMS_PERMISSION)
                }
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            SMS_PERMISSION -> {
                when {
                    grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED -> {
                        // Permission granted.
                    }
                    else -> {
                        // Permission denied.
                    }
                }
            }
            else -> {
                // Ignore all other requests.
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> {
                Navigation.findNavController(binding.root.findViewById(R.id.nav_host_fragment)).navigate(R.id.action_settings)

                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun showSendButton() = binding.fab.show()

    fun hideSendButton() = binding.fab.hide()
}
