package com.example.authenticationapplicationtelkom

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import android.content.Intent
import android.widget.ProgressBar
import android.widget.TextView


class Register : AppCompatActivity() {

    private lateinit var editTextEmail: TextInputEditText
    private lateinit var editTextPassword: TextInputEditText
    private lateinit var buttonReg: Button
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
        setContentView(R.layout.activity_register)
        editTextEmail = findViewById(R.id.email)
        editTextPassword = findViewById(R.id.password)
        buttonReg = findViewById(R.id.btn_register)
        mAuth = FirebaseAuth.getInstance()
        progressBar = findViewById(R.id.progressBar)
        textView = findViewById(R.id.loginNow)

        textView.setOnClickListener{
            var intent = Intent(applicationContext, Login::class.java)
            startActivity(intent)
            finish()
        }

        buttonReg.setOnClickListener{
                progressBar.visibility = View.VISIBLE

                var email = editTextEmail.text.toString()
                var password: String = editTextPassword.text.toString()

                if(TextUtils.isEmpty(email))
                {
                    Toast.makeText(this@Register, "Enter Email", Toast.LENGTH_SHORT).show()
                }

                if(TextUtils.isEmpty(password))
                {
                    Toast.makeText(this@Register, "Enter Password", Toast.LENGTH_SHORT).show()
                }

                mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this){ task ->
                        if (task.isSuccessful){
                            progressBar.visibility = View.GONE
                            val intent = Intent(this, MainActivity::class.java)
                            startActivity(intent)

                            Toast.makeText(baseContext, "Success", Toast.LENGTH_SHORT).show()

                        } else {
                            progressBar.visibility = View.GONE
                            Toast.makeText(baseContext, "Registration failed",
                                Toast.LENGTH_SHORT).show()
                        }
                    }
                    .addOnFailureListener {
                        Toast.makeText(this, "Error occurred ${it.localizedMessage}", Toast.LENGTH_SHORT).show()
                    }
        }
    }
}