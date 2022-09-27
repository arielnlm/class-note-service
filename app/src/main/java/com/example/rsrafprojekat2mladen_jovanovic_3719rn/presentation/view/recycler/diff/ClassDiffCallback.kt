package com.example.rsrafprojekat2mladen_jovanovic_3719rn.presentation.view.recycler.diff

import androidx.recyclerview.widget.DiffUtil
import com.example.rsrafprojekat2mladen_jovanovic_3719rn.data.models.Class

class ClassDiffCallback : DiffUtil.ItemCallback<Class>() {

    override fun areItemsTheSame(oldItem: Class, newItem: Class): Boolean {
        return oldItem.classroom == newItem.classroom
                && oldItem.day == newItem.day
                && oldItem.subject == newItem.subject
                && oldItem.teacher == newItem.teacher
                && oldItem.time == newItem.time
                && oldItem.type == newItem.type
    }

    override fun areContentsTheSame(oldItem: Class, newItem: Class): Boolean {
        return oldItem.classroom == newItem.classroom
                && oldItem.day == newItem.day
                && oldItem.subject == newItem.subject
                && oldItem.teacher == newItem.teacher
                && oldItem.time == newItem.time
                && oldItem.type == newItem.type
    }
}