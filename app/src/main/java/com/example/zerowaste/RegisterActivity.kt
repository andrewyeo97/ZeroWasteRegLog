package com.example.zerowaste

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_register.*
import com.example.zerowaste.User

class RegisterActivity : AppCompatActivity() {


    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        auth = FirebaseAuth.getInstance()

        registerButton.setOnClickListener {
            reg()
        }

    }


    private fun reg() {
        val username = usernameEdit.text.toString().trim()
        val email = emailEdit.text.toString().trim()
        val pass = passwordEdit.text.toString().trim()
        val number = phonenoEdit.text.toString().trim()

        if (usernameEdit.text.toString().isEmpty()) {
            usernameEdit.error = "Please enter username"
            usernameEdit.requestFocus()
            return
        }

        if (emailEdit.text.toString().isEmpty()) {
            emailEdit.error = "Please enter email"
            emailEdit.requestFocus()
            return
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(emailEdit.text.toString()).matches()) {
            emailEdit.error = "Please enter valid email"
            emailEdit.requestFocus()
            return
        }

        if (passwordEdit.text.toString().isEmpty()) {
            passwordEdit.error = "Please enter password"
            passwordEdit.requestFocus()
            return
        }

        if (phonenoEdit.text.toString().isEmpty()) {
            phonenoEdit.error = "Please enter phone number"
            phonenoEdit.requestFocus()
            return
        }






        auth.createUserWithEmailAndPassword(email, pass)
            .addOnCompleteListener {task->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    val ref = FirebaseDatabase.getInstance().getReference("Users")
                    val uid = FirebaseAuth.getInstance().uid ?:""
                    val myuser = User(uid,username,email,pass,number)

                    ref.child(uid).setValue(myuser)

                    user?.sendEmailVerification()
                        ?.addOnCompleteListener { task->
                           if(task.isSuccessful) {
                               startActivity(Intent(this,LoginActivity::class.java))
                           }
                        }

                } else {
                    Toast.makeText(
                        baseContext, "Register failed.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

    }


}

