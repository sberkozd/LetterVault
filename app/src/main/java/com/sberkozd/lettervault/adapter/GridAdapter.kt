package com.sberkozd.lettervault.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.sberkozd.lettervault.convertToDateRepresentation
import com.sberkozd.lettervault.data.Note
import com.sberkozd.lettervault.databinding.ItemNoteRvGridBinding
import com.sberkozd.lettervault.ui.grid.GridFragmentDirections

class GridAdapter : RecyclerView.Adapter<GridAdapter.GridViewHolder>() {

    private val items: MutableList<Note> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GridViewHolder {
        val binding =
            ItemNoteRvGridBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GridViewHolder(binding)
    }

    fun setItems(items: List<Note>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: GridViewHolder, position: Int) {
        holder.binding.apply {
            this.noteRecyclerViewGridContext.text = items[position].noteContext
            this.noteRecyclerViewGridDateOpened.text = items[position].timeUnlocked.let {
                if (it.isBlank()) {
                    ""
                } else {
                    it.convertToDateRepresentation()
                }
            }
            this.noteRecyclerViewGridTitle.text = items[position].noteTitle
            if (items[position].isLocked == 0) {
                if(items[position].timeUnlocked.isBlank()){
                    this.noteRecyclerViewGridOpenedTV.visibility = View.GONE
                }else{
                    this.noteRecyclerViewGridOpenedTV.visibility = View.VISIBLE
                    this.noteRecyclerViewGridOpenedTV.text = "Opened: "
                }
                this.noteRecyclerViewGridLockClosedIcon.visibility = View.GONE
                this.noteRecyclerViewGridLockOpenedIcon.visibility = View.VISIBLE
            } else {
                this.noteRecyclerViewGridOpenedTV.text = "To be opened: "
                this.noteRecyclerViewGridLockOpenedIcon.visibility = View.GONE
                this.noteRecyclerViewGridLockClosedIcon.visibility = View.VISIBLE
                this.noteRecyclerViewGridContext.text = "Locked Letter"

            }
            this.root.setOnClickListener {
                it.findNavController()
                    .navigate(GridFragmentDirections.actionGridFragmentToDetailFragment(items[position].id))
            }
        }
    }

    override fun getItemCount() = items.size

    class GridViewHolder(val binding: ItemNoteRvGridBinding) :
        RecyclerView.ViewHolder(binding.root) {
        var note = binding.noteRecyclerViewGridContext
        var title = binding.noteRecyclerViewGridTitle
        var dateOpened = binding.noteRecyclerViewGridDateOpened
        var lockClosed = binding.noteRecyclerViewGridLockClosedIcon
        var lockOpened = binding.noteRecyclerViewGridLockOpenedIcon
        var labelOpened = binding.noteRecyclerViewGridOpenedTV
    }
}

