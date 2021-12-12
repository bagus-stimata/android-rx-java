package com.example.rxjava4.presentation.recyclerview

import androidx.recyclerview.widget.DiffUtil
import com.example.rxjava4.model.Note

/**
 * Created by wisnu on 8/7/18
 */
class NoteDiffUtilCallback(private val oldData: MutableList<Note>,
                           private val newData: MutableList<Note>) : DiffUtil.Callback() {

    override fun areItemsTheSame(oldItemPosition: Int,
                                 newItemPosition: Int): Boolean {
        return oldData[oldItemPosition].id == newData[newItemPosition].id
    }

    override fun getOldListSize(): Int = oldData.size

    override fun getNewListSize(): Int = newData.size

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldData[oldItemPosition] == newData[newItemPosition]
    }
}