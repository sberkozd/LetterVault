package com.sberkozd.lettervault.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sberkozd.lettervault.data.Note
import com.sberkozd.lettervault.databinding.ItemNoteRvBinding

class NoteAdapter: RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {

    private val items: MutableList<Note> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val binding = ItemNoteRvBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NoteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.binding.apply {
            this.noteRecyclerViewContext.text = items[position].noteContext
        }
    }


    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    class NoteViewHolder(val binding: ItemNoteRvBinding) : RecyclerView.ViewHolder(binding.root)
}

