package com.ichwan.arch.navigationcomponent.appscomponent

import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.ichwan.arch.navigationcomponent.broadcastservice.AirplaneModeChangeReceiver
import com.ichwan.arch.navigationcomponent.databinding.ActivityActionBinding
import com.ichwan.arch.navigationcomponent.service.ForegroundService

class ActionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityActionBinding

    private val airplaneModeChangeReceiver = AirplaneModeChangeReceiver()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityActionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.POST_NOTIFICATIONS), 0)
        }

        Toast.makeText(this, "OnCreate", Toast.LENGTH_SHORT).show()

        val textMessage= "Hello World!"

        binding.apply {
            sendButton.setOnClickListener{
                val send = Intent().apply {
                    action = Intent.ACTION_SEND
                    type = "text/plain"
                    putExtra(Intent.EXTRA_TEXT, textMessage)
                }

                startActivity(send)
            }

            startButton.setOnClickListener {
                Intent(applicationContext, ForegroundService::class.java).also {
                    it.action = ForegroundService.Actions.START.toString()
                    startService(it)
                }
            }

            stopButton.setOnClickListener {
                Intent(applicationContext, ForegroundService::class.java).also {
                    it.action = ForegroundService.Actions.STOP.toString()
                    startService(it)
                }
            }
        }

        registerReceiver(airplaneModeChangeReceiver, IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED))

    }

    override fun onStart() {
        super.onStart()
        unregisterReceiver(airplaneModeChangeReceiver)
        Toast.makeText(this, "OnStart", Toast.LENGTH_SHORT).show()
    }

    override fun onResume() {
        super.onResume()
        Toast.makeText(this, "OnResume", Toast.LENGTH_SHORT).show()
    }

    override fun onPause() {
        super.onPause()
        Toast.makeText(this, "OnPause", Toast.LENGTH_SHORT).show()
    }

    override fun onStop() {
        super.onStop()
        Toast.makeText(this, "OnStop", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        Toast.makeText(this, "OnDestroy", Toast.LENGTH_SHORT).show()
    }
}