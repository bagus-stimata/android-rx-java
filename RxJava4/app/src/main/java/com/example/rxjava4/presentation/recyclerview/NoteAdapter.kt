package com.example.rxjava4.presentation.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.rxjava4.model.Note
import com.example.rxjava4.R
import com.example.rxjava4.databinding.ItemNoteBinding

/**
 * Created by wisnu on 8/7/18
 */
class NoteAdapter(private val itemLongClickListener: (Int) -> Unit) : RecyclerView.Adapter<NoteViewHolder>() {


    private var data: MutableList<Note> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
//        val itemView = LayoutInflater
//                .from(parent.context)
//                .inflate(R.layout.item_note, parent, false)
//        return NoteViewHolder(itemView, itemLongClickListener)

        val binding: ItemNoteBinding = ItemNoteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NoteViewHolder(binding, itemLongClickListener)
    }
    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        if (position != RecyclerView.NO_POSITION) {
            holder.bindData(data[position])
        }
    }
    override fun getItemCount(): Int = data.size

    fun getDataAtPosition(position: Int): Note = data[position]

    fun setData(data: MutableList<Note>) {
        val diffResult = DiffUtil.calculateDiff(
            NoteDiffUtilCallback(this.data, data)
        )

        this.data = data
        diffResult.dispatchUpdatesTo(this)
    }

    fun updateData(data: MutableList<Note>) {
        val diffResult = DiffUtil.calculateDiff(
            NoteDiffUtilCallback(this.data, data)
        )

        this.data.clear()
        this.data.addAll(data)
        diffResult.dispatchUpdatesTo(this)
    }

}

class NoteViewHolder(private val itemBinding: ItemNoteBinding,
                     itemLongClickListener: (Int) -> Unit) : RecyclerView.ViewHolder(itemBinding.root) {
    init {
        itemView?.setOnLongClickListener {
            itemLongClickListener(adapterPosition)
            return@setOnLongClickListener true
        }
    }
    private lateinit var character: Note
    fun bindData(note: Note) {
        this.character = note
        itemBinding.itemNoteContent.text = note.content
    }
}