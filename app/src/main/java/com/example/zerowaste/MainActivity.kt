package com.example.zerowaste

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.widget.Button

class MainActivity : AppCompatActivity() {

    private lateinit var registerButton: Button
    private lateinit var loginButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        registerButton = findViewById(R.id.regButton)
        loginButton = findViewById(R.id.logButton)

        registerButton.setOnClickListener{
            register()
        }

        loginButton.setOnClickListener{
            login()
        }

        //Toast.makeText(this,"Firebase Connection Success",Toast.LENGTH_LONG).show()
    }

    private fun register(){
        intent = Intent(this, RegisterActivity::class.java )
        startActivity(intent)
    }

    private fun login(){
        intent = Intent(this, LoginActivity::class.java )
        startActivity(intent)
    }

}

