package com.storesoko.tenakata.Fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.storesoko.tenakata.R
import kotlinx.android.synthetic.main.fragment_form_fragnment.*
import kotlinx.android.synthetic.main.fragment_form_fragnment.view.*
import java.lang.Integer.parseInt


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
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent,0)
        }

        return view
    }

    private fun validateForm() {
        val name = name.text.toString().trim()
        val age = age.text.toString().trim()
        val height = height.text.toString().trim()
        val iqTest = iqTest.text.toString().trim()
        val choosenLocation = choosenLocation.text.toString()

        if(profile_image.drawable == null){
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

            sendDataToDb()

        }
    }

    private fun sendDataToDb() {
        TODO("Not yet implemented")
    }


}