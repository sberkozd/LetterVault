package com.sberkozd.lettervault.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.sberkozd.lettervault.data.Note
import com.sberkozd.lettervault.databinding.ItemNoteRvBinding
import com.sberkozd.lettervault.ui.home.HomeFragmentDirections

class NoteAdapter : RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {

    private val items: MutableList<Note> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val binding = ItemNoteRvBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NoteViewHolder(binding)
    }

    fun setItems(items: List<Note>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    init {

    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.binding.apply {
            this.noteRecyclerViewContext.text = items[position].noteContext
            if (items[position].isLocked == 0) {
                this.noteRecyclerViewLockClosedIcon.visibility = View.GONE
                this.noteRecyclerViewLockOpenedIcon.visibility = View.VISIBLE

            } else {
                this.noteRecyclerViewLockOpenedIcon.visibility = View.GONE
                this.noteRecyclerViewLockClosedIcon.visibility = View.VISIBLE
                this.noteRecyclerViewContext.text = "Locked Letter"

            }
            this.root.setOnClickListener{
                it.findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToDetailFragment(items[position].id))
            }
        }
    }



    override fun getItemCount() = items.size

    class NoteViewHolder(val binding: ItemNoteRvBinding) : RecyclerView.ViewHolder(binding.root) {
        var note = binding.noteRecyclerViewContext
    }

}

