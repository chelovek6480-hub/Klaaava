package com.example.keyboard

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<android.widget.Button>(R.id.btnEnable).setOnClickListener {
            startActivity(android.content.Intent(android.provider.Settings.ACTION_INPUT_METHOD_SETTINGS))
        }

        findViewById<android.widget.Button>(R.id.btnPick).setOnClickListener {
            val ics = packageManager.queryIntentActivities(
                android.content.Intent(android.provider.Settings.ACTION_INPUT_METHOD_SETTINGS),
                0
            )
            Toast.makeText(this, getString(R.string.tip), Toast.LENGTH_LONG).show()
        }
    }
}
