package com.example.authenticationapplicationtelkom

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class MainActivity : AppCompatActivity() {
    private lateinit var auth : FirebaseAuth
    private lateinit var button : Button
    private lateinit var textView : TextView
    private lateinit var user : FirebaseUser
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth = FirebaseAuth.getInstance()
        button = findViewById(R.id.logout)
        textView = findViewById(R.id.user_details)
        if(auth.currentUser == null)
        {
            intent = Intent(applicationContext, Login::class.java)
            startActivity(intent)
            finish()
        }
        else
        {
            user = auth.currentUser!!
            textView.text = user.email
        }

        button.setOnClickListener{
                FirebaseAuth.getInstance().signOut()
                intent = Intent(applicationContext, Login::class.java)
                startActivity(intent)
                finish()
        }
    }
}