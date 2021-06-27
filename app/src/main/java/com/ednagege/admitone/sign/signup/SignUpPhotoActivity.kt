package com.ednagege.admitone.sign.signup

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.ednagege.admitone.home.HomeActivity
import com.ednagege.admitone.R
import com.ednagege.admitone.sign.signin.User
import com.ednagege.admitone.utils.Preferences
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import kotlinx.android.synthetic.main.activity_sign_up_photo.*
import java.util.*

class SignUpPhotoActivity : AppCompatActivity(), PermissionListener{
    val REQUEST_IMAGE_CAPTURE = 1 //untuk pencarian poto
    var statusAdd : Boolean = false //untuk status pencarian poto
    lateinit var filePath : Uri

    lateinit var storage : FirebaseStorage
    lateinit var storageReference : StorageReference
    lateinit var preferences: Preferences

    lateinit var user : User
    private lateinit var mDatabaseReference: DatabaseReference
    private lateinit var mFirebaseInstance: FirebaseDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up_photo)

        preferences = Preferences(this)
        storage = FirebaseStorage.getInstance()
        storageReference = storage.reference

        mFirebaseInstance = FirebaseDatabase.getInstance()
        mDatabaseReference = mFirebaseInstance.getReference("User")

        user = intent.getParcelableExtra("data")
        tvWelcome.text = "Welcome,\n"+user.name

        btnAdd.setOnClickListener {
            if (statusAdd) {
                statusAdd = false
                btnSaveAndNext.visibility = View.INVISIBLE
                btnAdd.setImageResource(R.drawable.ic_add_24)
                img_user_pic.setImageResource(R.drawable.user_pic)
            } else {
//                Dexter.withActivity(this)
//                    .withPermission(Manifest.permission.CAMERA)
//                    .withListener(this)
//                    .check()
                ImagePicker.with(this)
                    .galleryOnly()	//User can only select image from Gallery
                    .cropSquare()	//Crop square image, its same as crop(1f, 1f)
                    .start()
            }
        }

        btnUploadLater.setOnClickListener {
            finishAffinity()
            var intentHome = Intent(this@SignUpPhotoActivity, HomeActivity::class.java)
            startActivity(intentHome)
        }

        btnSaveAndNext.setOnClickListener {
            if (filePath != null) {
                val progressDialog = ProgressDialog(this)
                progressDialog.setTitle("Uploading...")
                progressDialog.show()

                val ref = storageReference.child("images/"+UUID.randomUUID().toString())

                ref.putFile(filePath)
                    .addOnSuccessListener {
                        progressDialog.dismiss()
                        Toast.makeText(this@SignUpPhotoActivity, "Image is successfully uploaded!", Toast.LENGTH_SHORT).show()

                        ref.downloadUrl.addOnSuccessListener {
                            saveToFirebase(it.toString())
                        }
                    }
                    .addOnFailureListener { e ->
                        progressDialog.dismiss()
                        Toast.makeText(this@SignUpPhotoActivity, "Failed to upload image." + e.message, Toast.LENGTH_SHORT).show()
                    }
                    .addOnProgressListener { taskSnapshot ->
                        val progress = 100.0 * taskSnapshot.bytesTransferred / taskSnapshot.totalByteCount
                        progressDialog.setMessage("Uploaded " + progress.toInt() + "%")
                    }
            }
        }
    }

    private fun saveToFirebase(url: String) {
        mDatabaseReference.child(user.username!!).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                user.url = url
                mDatabaseReference.child(user.username!!).setValue(user)

                preferences.setValues("name", user.name.toString())
                preferences.setValues("username", user.username.toString())
                preferences.setValues("email", user.email.toString())
                preferences.setValues("url", url)
                preferences.setValues("balance", "")
                preferences.setValues("status", "1")

                finishAffinity()
                val intent = Intent(this@SignUpPhotoActivity, HomeActivity::class.java)
                startActivity(intent)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(this@SignUpPhotoActivity, ""+databaseError, Toast.LENGTH_LONG).show()
            }
        })
    }

    override fun onPermissionGranted(response: PermissionGrantedResponse?) {
        ImagePicker.with(this)
            .galleryOnly()	//User can only select image from Gallery
            .cropSquare()	//Crop square image, its same as crop(1f, 1f)
            .start()
    }

    override fun onPermissionDenied(response: PermissionDeniedResponse?) {
        Toast.makeText(this, "Unable to add image.", Toast.LENGTH_LONG).show()
    }

    override fun onPermissionRationaleShouldBeShown(
        permission: PermissionRequest?,
        token: PermissionToken?
    ) {}

    override fun onBackPressed() {
        Toast.makeText(this, "In a rush? Click on Upload Later.", Toast.LENGTH_LONG).show()
    }

//    @SuppressLint("MissingSuperCall")
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//
//        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
//            var bitmap = data?.extras?.get("data") as Bitmap
//            statusAdd = true
//
//            filePath = data.getData()!!
//            Glide.with(this)
//                .load(bitmap)
//                .apply(RequestOptions.circleCropTransform())
//                .into(img_user_pic)
//
//            btnSaveAndNext.visibility = View.VISIBLE
//            btnAdd.setImageResource(R.drawable.ic_round_delete_outline_24)
//        }
//    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            //Image Uri will not be null for RESULT_OK
            filePath = data?.data!!
            statusAdd = true

            Glide.with(this)
                .load(filePath)
                .apply(RequestOptions.circleCropTransform())
                .into(img_user_pic)

            btnSaveAndNext.visibility = View.VISIBLE
            btnAdd.setImageResource(R.drawable.ic_round_delete_outline_24)

        } else if (resultCode == ImagePicker.RESULT_ERROR) {
            Toast.makeText(this, ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Task Cancelled", Toast.LENGTH_SHORT).show()
        }
    }
}