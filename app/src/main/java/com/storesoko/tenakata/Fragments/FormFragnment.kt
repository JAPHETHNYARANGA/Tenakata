package com.storesoko.tenakata.Fragments


import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.storesoko.tenakata.MainActivity
import com.storesoko.tenakata.R
import com.storesoko.tenakata.models.formModels
import kotlinx.android.synthetic.main.fragment_form_fragnment.*
import kotlinx.android.synthetic.main.fragment_form_fragnment.view.*
import java.lang.Integer.parseInt
import java.util.*


private var gender: String? = null
private var maritalStatus: String? = null

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

        view.profile_camera.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "image/*"
            startActivityForResult(intent,0)

        }





        //radio gender
        val radioGender = view.findViewById(R.id.radioGroupGender) as RadioGroup

        radioGender.setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener { group, checkedId -> // TODO Auto-generated method stub
            val childCount = group.childCount

            for (x in 0 until childCount) {
                val btn = group.getChildAt(x) as RadioButton
                if (btn.id == R.id.male) {
                    btn.text = "Male"
                } else {
                    btn.text = "Female"
                }
                if (btn.id == checkedId) {
                    gender = btn.text.toString() // here gender will contain M or F.
                }
            }
            Log.e("Gender", gender!!)

        })

        //radio marital status

        val radioMaritalStatus= view.findViewById(R.id.radioGroupMaritalStatus) as RadioGroup

        radioMaritalStatus.setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener { group, checkedId -> // TODO Auto-generated method stub
            val childCount = group.childCount

            for (x in 0 until childCount) {
                val btn = group.getChildAt(x) as RadioButton
                if (btn.id == R.id.single) {
                    btn.text = "Single"
                }else if(btn.id == R.id.married) {
                    btn.text = "Married"
                }
                else if (btn.id == R.id.others) {
                    btn.text = "Others"
                }
                if (btn.id == checkedId) {
                    maritalStatus = btn.text.toString() // here gender will contain M or F.
                }
            }
            Log.d("MaritalStatus", maritalStatus!!)
        })


        return view
    }

    //onresult for selected images

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
        var name = name.text.toString().trim()
        var age = age.text.toString().trim()
        var height = height.text.toString().trim()
        var iqTest = iqTest.text.toString().trim()
        var choosenLocation = choosenLocation.text.toString()







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
            Toast.makeText(activity, "Make sure to select image or it will not work!", Toast.LENGTH_LONG).show()




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

                ref.downloadUrl.addOnSuccessListener {
                    Toast.makeText(activity, " $it", Toast.LENGTH_SHORT).show()


                    saveUserToFirebaseDataBase(it.toString())
                }
            }
    }

    private fun saveUserToFirebaseDataBase(profileImageUrl:String) {
        val uid = FirebaseAuth.getInstance().uid ?: ""
        val ref = FirebaseDatabase.getInstance().getReference("/users").push()

        val user = formModels(uid ,  profileImageUrl,name.text.toString(), gender, age.text.toString(), maritalStatus, height.text.toString(), iqTest.text.toString())

        ref.setValue(user)
            .addOnSuccessListener {
                Log.d("Register", "Saved to firebase")


                startActivity(Intent(activity, MainActivity::class.java))
            }
    }




}


