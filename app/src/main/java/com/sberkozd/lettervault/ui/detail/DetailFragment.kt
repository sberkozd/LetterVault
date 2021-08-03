package com.sberkozd.lettervault.ui.detail

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.view.*
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.sberkozd.lettervault.R
import com.sberkozd.lettervault.databinding.FragmentDetailBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DetailFragment : Fragment(R.layout.fragment_detail) {

    private val detailViewModel: DetailViewModel by viewModels()

    private var _binding: FragmentDetailBinding? = null

    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)

        detailViewModel.note.observe(viewLifecycleOwner) {
            binding.apply {
                detailNoteTV.setText(it.noteContext)
                detailNoteTitleTV.setText(it.noteTitle)
                if (it.isLocked == 0) {
                    detailIsLockedIcon.visibility = View.GONE
                } else {
                    detailIsLockedIcon.visibility = View.VISIBLE
                    detailNoteTitleTV.visibility = View.GONE
                    detailNoteTV.visibility = View.GONE
                }
            }
        }


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_detail, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val detailNoteTVEditText = binding.detailNoteTV as EditText
        val detailNoteTitleTVEditText = binding.detailNoteTitleTV as EditText

        detailNoteTVEditText.inputType = InputType.TYPE_CLASS_TEXT
        detailNoteTitleTVEditText.inputType = InputType.TYPE_CLASS_TEXT

        val detailNoteTVString: String = detailNoteTVEditText.text.toString()
        val detailNoteTitleTVString: String = detailNoteTitleTVEditText.text.toString()

        return when (item.itemId) {
            R.id.detail_menu_item_delete -> {
                deleteNote()
                true
            }
            R.id.detail_menu_item_share -> {
                val shareIntent = Intent(Intent.ACTION_SEND)
                shareIntent.type = "text/plain"
                shareIntent.putExtra(Intent.EXTRA_TEXT, detailNoteTVString)
                startActivity(Intent.createChooser(shareIntent, "Share using ..."))
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }


    private fun deleteNote() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes") { _, _ ->

            Toast.makeText(requireContext(), "Successfully deleted", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_detailFragment_to_homeFragment)
        }
        builder.setNegativeButton("No") { _, _ ->
            builder.setTitle("Delete Note? ")
            builder.setMessage("Are you sure you want to delete the note?")
            builder.create().show()
        }
    }



}