package com.example.rxjava4.presentation.recyclerview

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.rxjava4.databinding.ItemNoteBinding
import com.example.rxjava4.model.Note

class CharactersAdapter(private val listener: CharacterItemListener) : RecyclerView.Adapter<CharacterViewHolder>() {

    interface CharacterItemListener {
//        fun onClickedCharacter(characterId: Int)
        fun onClickedCharacter(characterId: Int)
    }

    private val items = ArrayList<Note>()

    fun setItems(items: ArrayList<Note>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val binding: ItemNoteBinding = ItemNoteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CharacterViewHolder(binding, listener)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) = holder.bind(items[position])
}

class CharacterViewHolder(private val itemBinding: ItemNoteBinding, private val listener: CharactersAdapter.CharacterItemListener) : RecyclerView.ViewHolder(itemBinding.root),
    View.OnClickListener {

    private lateinit var character: Note

    init {
        itemBinding.root.setOnClickListener(this)
    }

    @SuppressLint("SetTextI18n")
    fun bind(item: Note) {
        this.character = item
        itemBinding.itemNoteContent.text = item.content
//        itemBinding.speciesAndStatus.text = """${item.species} - ${item.status}"""
//        Glide.with(itemBinding.root)
//            .load(item.image)
//            .transform(CircleCrop())
//            .into(itemBinding.image)
    }

    override fun onClick(v: View?) {
        listener.onClickedCharacter(character.id)
    }
}

