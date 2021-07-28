package com.sberkozd.lettervault.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.sberkozd.lettervault.data.Note
import com.sberkozd.lettervault.databinding.ItemNoteRvBinding

class NoteAdapter(var isLocked: Int): RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {

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

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.binding.apply {
            this.noteRecyclerViewContext.text =items[position].noteContext
            if (isLocked == 0) {
                this.noteRecyclerViewLockClosedIcon.isVisible.not()
            }
            if(isLocked == 1){
                this.noteRecyclerViewLockOpenedIcon.isVisible.not()
            }
        }
    }

    override fun getItemCount() = items.size

    class NoteViewHolder(val binding: ItemNoteRvBinding) : RecyclerView.ViewHolder(binding.root){
        var note = binding.noteRecyclerViewContext
    }
}

