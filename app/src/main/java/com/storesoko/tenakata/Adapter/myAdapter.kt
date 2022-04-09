package com.storesoko.tenakata.Adapter

import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.storesoko.tenakata.R
import com.storesoko.tenakata.models.formModels
import kotlinx.android.synthetic.main.individual_items_view.view.*

class myAdapter(private val dataList: ArrayList<formModels>) :RecyclerView.Adapter<myAdapter.ViewHolder>(){
    class ViewHolder(itemView : View):RecyclerView.ViewHolder(itemView) {
        val name = itemView.Name
        val gender = itemView.gender
        val age = itemView.age
        val maritalStatus = itemView.maritalStatus
        val height = itemView.height1
        val iqTest = itemView.iqTest
        val image = itemView.image



    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myAdapter.ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.individual_items_view, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: myAdapter.ViewHolder, position: Int) {

        val currentItem = dataList[position]
        holder.name.text = currentItem.name
        holder.gender.text = currentItem.gender
        holder.age.text = currentItem.age
        holder.maritalStatus.text = currentItem.maritalStatus
        holder.height.text = currentItem.height
        holder.iqTest.text = currentItem.iqTest


    }

    override fun getItemCount(): Int {
        return dataList.size
    }
}