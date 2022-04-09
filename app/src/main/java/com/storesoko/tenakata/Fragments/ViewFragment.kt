package com.storesoko.tenakata.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.storesoko.tenakata.Adapter.myAdapter
import com.storesoko.tenakata.R
import com.storesoko.tenakata.models.formModels


class ViewFragment : Fragment() {
    private lateinit var auth: FirebaseAuth
    private lateinit var storageReference : StorageReference
    private lateinit var dbref : DatabaseReference
    private lateinit var userRecyclerView : RecyclerView
    private lateinit var userArrayList : ArrayList<formModels>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view =  inflater.inflate(R.layout.fragment_view, container, false)

        userRecyclerView = view.findViewById(R.id.ListData)
        userRecyclerView.layoutManager = LinearLayoutManager(context)
        userRecyclerView.setHasFixedSize(true)

        userArrayList = arrayListOf()

        getUserData()

        //firebase auth
        auth = FirebaseAuth.getInstance()

        storageReference = FirebaseStorage.getInstance().getReference("users")


        return view
    }

    private fun getUserData() {
        dbref = FirebaseDatabase.getInstance().getReference("users")

        dbref.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    for (userSnapshot in snapshot.children){
                        val user = userSnapshot.getValue(formModels::class.java)
                        userArrayList.add(user!!)
                    }

                    userRecyclerView.adapter = myAdapter(userArrayList)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }


}