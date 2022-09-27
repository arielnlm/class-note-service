package com.example.rsrafprojekat2mladen_jovanovic_3719rn.presentation.view.recycler.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.rsrafprojekat2mladen_jovanovic_3719rn.data.models.Class
import com.example.rsrafprojekat2mladen_jovanovic_3719rn.databinding.LayoutItemClassBinding
import com.example.rsrafprojekat2mladen_jovanovic_3719rn.presentation.view.recycler.diff.ClassDiffCallback
import com.example.rsrafprojekat2mladen_jovanovic_3719rn.presentation.view.recycler.viewholder.ClassViewHolder

class ClassAdapter : ListAdapter<Class, ClassViewHolder>(ClassDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClassViewHolder {
        val itemBinding = LayoutItemClassBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ClassViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ClassViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

}