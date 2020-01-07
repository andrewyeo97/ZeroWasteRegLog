package com.example.zerowaste

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_login.emailEdit
import kotlinx.android.synthetic.main.activity_login.passwordEdit
import kotlinx.android.synthetic.main.activity_register.*

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        auth = FirebaseAuth.getInstance()

        loginButton.setOnClickListener {
            login()
        }

    }

    private fun login(){
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


        auth.signInWithEmailAndPassword(emailEdit.text.toString(), passwordEdit.text.toString())
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    if (FirebaseAuth.getInstance().currentUser !== null) {
                        val user = auth.currentUser
                        updateUI(user)
                    } else {
                        Toast.makeText(
                            baseContext, "Login failed.",
                            Toast.LENGTH_SHORT
                        ).show()
                        updateUI(null)
                    }
                }

            }

    }


    public override fun onStart() {
        super.onStart()
        val currentUser:FirebaseUser? = auth.currentUser
        updateUI(currentUser)
    }

   private fun updateUI(currentUser:FirebaseUser?){

        if(currentUser!=null){
            if(currentUser.isEmailVerified) {
                startActivity(Intent(this, HomepageActivity::class.java))
                finish()
            }else{
                Toast.makeText(baseContext, "Please verify your email address.",
                    Toast.LENGTH_SHORT).show()

            }
        }else{
            Toast.makeText(baseContext, "Login failed.",
                Toast.LENGTH_SHORT).show()
        }

    }
}

