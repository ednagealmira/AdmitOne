package com.ednagege.admitone.sign.signin

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.ednagege.admitone.home.HomeActivity
import com.ednagege.admitone.R
import com.ednagege.admitone.sign.signup.SignUpActivity
import com.ednagege.admitone.utils.Preferences
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_sign_in.*


class SignInActivity : AppCompatActivity() {

    lateinit var mDatabase: DatabaseReference
    lateinit var preferences: Preferences
    lateinit var iUsername:String
    lateinit var iPassword:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        mDatabase = FirebaseDatabase.getInstance().getReference("User")
        preferences = Preferences(this)

        //kalau sdh klik next/skip di onboarding sampai ke halaman signup, onboarding tidak akan muncul lagi
        //onboarding hanya akan muncul saat running aplikasi pertama kali saja
        preferences.setValues("onboarding", "1")
        if(preferences.getValues("status").equals("1")) {
            finishAffinity()
            var intent = Intent(this@SignInActivity, HomeActivity::class.java)
            startActivity(intent)
        }

        btnSignIn.setOnClickListener {
            iUsername = etUsername.text.toString()
            iPassword = etPassword.text.toString()

            if(iUsername.equals("")) {
                etUsername.error = "Please enter your username."
                etUsername.requestFocus()
            } else if(iPassword.equals("")) {
                etPassword.error = "Please enter your password."
                etPassword.requestFocus()
            } else {
                var statusUsername = iUsername.indexOf(".")
                if (statusUsername >= 0) {
                    etUsername.error = "Please enter username without \".\""
                    etUsername.requestFocus()
                } else {
                    pushLogin(iUsername,iPassword)
                }
            }
        }

        btnSignUp.setOnClickListener {
            val intent = Intent(this@SignInActivity, SignUpActivity::class.java)
            startActivity(intent)
        }
    }

    private fun pushLogin(iUsername: String, iPassword: String) {

        mDatabase.child(iUsername).addValueEventListener(object : ValueEventListener {
            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(this@SignInActivity, databaseError.message, Toast.LENGTH_LONG).show()
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                var user = dataSnapshot.getValue(User::class.java)

                if(user == null) {
                    Toast.makeText(this@SignInActivity, "User not found.", Toast.LENGTH_LONG).show()
                } else {
                    if (user.password.equals(iPassword)) {
                        Toast.makeText(this@SignInActivity, "Welcome aboard.", Toast.LENGTH_LONG).show()

                        preferences.setValues("name", user.name.toString())
                        preferences.setValues("email", user.email.toString())
                        preferences.setValues("url", user.url.toString())
                        preferences.setValues("balance", user.balance.toString())
                        preferences.setValues("username", user.username.toString())
                        preferences.setValues("status", "1")

                        finishAffinity()

                        val intent = Intent(this@SignInActivity, HomeActivity::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(this@SignInActivity, "Password is incorrect.", Toast.LENGTH_LONG).show()
                    }
                }
            }

        })
    }
}