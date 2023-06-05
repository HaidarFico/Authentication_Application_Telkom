package com.example.authenticationapplicationtelkom

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth

class Login : AppCompatActivity() {
    private lateinit var editTextEmail: TextInputEditText
    private lateinit var editTextPassword: TextInputEditText
    private lateinit var buttonLogin: Button
    private lateinit var mAuth: FirebaseAuth
    private lateinit var progressBar: ProgressBar
    private lateinit var textView: TextView

    override fun onStart() {
        super.onStart()
        var currentUser = mAuth.currentUser
        if(currentUser != null){
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        editTextEmail = findViewById(R.id.email)
        editTextPassword = findViewById(R.id.password)
        buttonLogin= findViewById(R.id.btn_login)
        mAuth = FirebaseAuth.getInstance()
        progressBar = findViewById(R.id.progressBar)
        textView = findViewById(R.id.registerNow)

        textView.setOnClickListener(
            View.OnClickListener {
                fun onClick(view: View)
                {
                    intent = Intent(applicationContext, Register::class.java)
                    startActivity(intent)
                    finish()
                }
            }
        )

        buttonLogin.setOnClickListener(View.OnClickListener {
            fun onClick(view: View){
                progressBar.visibility = View.VISIBLE

                var email = editTextEmail.text.toString()
                var password: String = editTextPassword.text.toString()

                if(TextUtils.isEmpty(email))
                {
                    Toast.makeText(this@Login, "Enter Email", Toast.LENGTH_SHORT).show()
                    return
                }

                if(TextUtils.isEmpty(password))
                {
                    Toast.makeText(this@Login, "Enter Password", Toast.LENGTH_SHORT).show()
                    return
                }

                mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this){ task ->
                        if (task.isSuccessful){
                            val intent = Intent(this, MainActivity::class.java)
                            startActivity(intent)
                            finish()
                            Toast.makeText(baseContext, "Success", Toast.LENGTH_SHORT).show()


                        } else {
                            Toast.makeText(baseContext, "Authentication failed",
                                Toast.LENGTH_SHORT).show()
                        }
                    }
                    .addOnFailureListener {
                        Toast.makeText(this, "Authentication Failed ${it.localizedMessage}", Toast.LENGTH_SHORT).show()
                    }
        }})
    }
}