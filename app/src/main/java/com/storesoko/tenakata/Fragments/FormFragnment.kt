package com.storesoko.tenakata.Fragments

import android.app.Activity
import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.firebase.storage.FirebaseStorage
import com.storesoko.tenakata.R
import kotlinx.android.synthetic.main.fragment_form_fragnment.*
import kotlinx.android.synthetic.main.fragment_form_fragnment.view.*
import java.lang.Integer.parseInt
import java.util.*


class FormFragnment : Fragment() {



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_form_fragnment, container, false)

        view.submit.setOnClickListener{
            validateForm()
        }

        view.profile_image.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "image/*"
            startActivityForResult(intent,0)
        }

        return view
    }

    var selectedPhotoUri:Uri? = null

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 0 && resultCode == Activity.RESULT_OK && data !=null){
            //check the selected image
            selectedPhotoUri = data.data



            val bitmap = MediaStore.Images.Media.getBitmap(activity?.contentResolver , selectedPhotoUri)

            val bitmapDrawable = BitmapDrawable(bitmap)
            profile_image.setBackgroundDrawable(bitmapDrawable)
        }
    }

    private fun validateForm() {
        val name = name.text.toString().trim()
        val age = age.text.toString().trim()
        val height = height.text.toString().trim()
        val iqTest = iqTest.text.toString().trim()
        val choosenLocation = choosenLocation.text.toString()

        if(profile_image == null){
            Toast.makeText(activity, "Please Take a Picture", Toast.LENGTH_SHORT).show()
        }else if (name.isEmpty()){
            layoutName.error = "please fill in your Name"
        }else if (radioGroupGender.checkedRadioButtonId == -1){
            Toast.makeText(activity, "Please select a gender", Toast.LENGTH_SHORT).show()
        }else if(age.isEmpty()){
            layoutAge.error= "please Insert your age"
        }else if(radioGroupMaritalStatus.checkedRadioButtonId == -1){
            Toast.makeText(activity, "Please select your marital status",Toast.LENGTH_SHORT).show()
        }else if(height.isEmpty()){
            layoutHeight.error = "Please enter your height"
        }else if(iqTest.isEmpty()){
            layoutIqTest.error = "Please Insert iq test"
        }else if(parseInt(iqTest) < 100){
            layoutIqTest.error = " your Iq Test must be above 100"
        }else if(choosenLocation.isNullOrEmpty()){
            Toast.makeText(activity,"Please choose a location", Toast.LENGTH_SHORT).show()
        }else{

            layoutName.isErrorEnabled = false
            layoutAge.isErrorEnabled = false
            layoutHeight.isErrorEnabled= false
            layoutIqTest.isErrorEnabled = false
            Toast.makeText(activity, "Everything is okay", Toast.LENGTH_SHORT).show()



            uploadImageToFirebaseStorage()

        }
    }

    private fun uploadImageToFirebaseStorage() {
        if(selectedPhotoUri == null )return
        val filename = UUID.randomUUID().toString()
        val ref = FirebaseStorage.getInstance().getReference("/images/$filename")
        ref.putFile(selectedPhotoUri!!)
            .addOnSuccessListener {
                Toast.makeText(activity,"photo uploaded successfully",Toast.LENGTH_SHORT).show()

//                ref.downloadUrl.addOnSuccessListener {
//                    it.toString()
//                }
            }
    }

    private fun sendDataToDb() {
        TODO("Not yet implemented")
    }




}