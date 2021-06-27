package com.ednagege.admitone.sign.signup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.ednagege.admitone.R
import com.ednagege.admitone.sign.signin.User
import com.ednagege.admitone.utils.Preferences
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_sign_in.*
import kotlinx.android.synthetic.main.activity_sign_up.*
import kotlinx.android.synthetic.main.activity_sign_up.etPassword
import kotlinx.android.synthetic.main.activity_sign_up.etUsername

class SignUpActivity : AppCompatActivity() {
    lateinit var sName: String
    lateinit var sUsername: String
    lateinit var sPassword: String
    lateinit var sEmail: String

    private lateinit var mFirebaseInstance: FirebaseDatabase
    private lateinit var mDatabase: DatabaseReference
    private lateinit var mDatabaseReference: DatabaseReference

    private lateinit var preferences: Preferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        mFirebaseInstance = FirebaseDatabase.getInstance()
        mDatabase = FirebaseDatabase.getInstance().getReference()
        mDatabaseReference = mFirebaseInstance.getReference("User")

        preferences = Preferences(this)

        btnCreateAccount.setOnClickListener {
            sName = etName.text.toString()
            sUsername = etUsername.text.toString()
            sPassword = etPassword.text.toString()
            sEmail = etEmail.text.toString()

            if(sName.equals("")) {
                etName.error = "Please enter your name."
                etName.requestFocus()
            } else if(sUsername.equals("")) {
                etUsername.error = "Please set your username."
                etUsername.requestFocus()
            } else if(sPassword.equals("")) {
                etPassword.error = "Please set a password."
                etPassword.requestFocus()
            } else if(sEmail.equals("")) {
                etEmail.error = "Please enter your email address."
                etEmail.requestFocus()
            } else {
                var statusUsername = sUsername.indexOf(".")
                if (statusUsername >= 0) {
                    etUsername.error = "Please enter username without \".\""
                    etUsername.requestFocus()
                } else {
                    saveUsername(sName, sUsername, sPassword, sEmail)
                }
            }

        }
    }

    private fun saveUsername(sName: String, sUsername: String, sPassword: String, sEmail: String) {
        val user = User()
        user.name = sName
        user.username = sUsername
        user.password = sPassword
        user.email = sEmail

        if(sUsername != null) {
            checkingUsername(sUsername, user)
        }
    }

    //pengecekan apakah username sudah dipakai atau belum
    private fun checkingUsername(iUsername: String, data: User) {
        mDatabaseReference.child(iUsername).addValueEventListener(object : ValueEventListener{
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val user = dataSnapshot.getValue(User::class.java)

                if(user == null) {
                    mDatabaseReference.child(iUsername).setValue(data)

                    preferences.setValues("name", data.name.toString())
                    preferences.setValues("username", data.username.toString())
                    preferences.setValues("email", data.email.toString())
                    preferences.setValues("url", "")
                    preferences.setValues("balance", "")
                    preferences.setValues("status", "1")

                    val intent = Intent(this@SignUpActivity, SignUpPhotoActivity::class.java)
                        .putExtra("data", data)
                    startActivity(intent)

                } else {
                    Toast.makeText(this@SignUpActivity, "Username has been registered.", Toast.LENGTH_LONG).show()
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(this@SignUpActivity, ""+databaseError.message, Toast.LENGTH_LONG).show()
            }

        })
    }
}