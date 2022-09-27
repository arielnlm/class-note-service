package com.example.rsrafprojekat2mladen_jovanovic_3719rn.presentation.view.recycler.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.example.rsrafprojekat2mladen_jovanovic_3719rn.data.models.Class
import com.example.rsrafprojekat2mladen_jovanovic_3719rn.databinding.LayoutItemClassBinding

class ClassViewHolder (private val itemBinding: LayoutItemClassBinding) : RecyclerView.ViewHolder(itemBinding.root){
    fun bind(myClass: Class){
        itemBinding.titleTv.text = myClass.subject + " - " + myClass.type
        itemBinding.profesorTv.text = myClass.teacher
        itemBinding.dayTv.text = myClass.day
        itemBinding.classroomTv.text = myClass.classroom
        itemBinding.groupId.text = myClass.groups
        itemBinding.timeTv.text = myClass.time
    }
}