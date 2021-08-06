package com.sberkozd.lettervault.ui.detail

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.view.*
import android.widget.EditText
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.sberkozd.lettervault.R
import com.sberkozd.lettervault.convertToDateRepresentation
import com.sberkozd.lettervault.data.Note
import com.sberkozd.lettervault.databinding.FragmentDetailBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DetailFragment : Fragment(R.layout.fragment_detail) {

    private val detailViewModel: DetailViewModel by viewModels()

    private var _binding: FragmentDetailBinding? = null

    private val binding get() = _binding!!

    private lateinit var backButtonCallback: OnBackPressedCallback

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
                detailNoteDateOpened.text = it.timeUnlocked.convertToDateRepresentation()
                if (it.isLocked == 0) {
                    detailIsLockedIcon.visibility = View.GONE
                    activity?.title =  this.detailNoteDateOpened.text
                } else {
                    detailIsLockedIcon.visibility = View.VISIBLE
                    detailNoteTitleTV.visibility = View.GONE
                    detailNoteTV.visibility = View.GONE
                    activity?.title = this.detailNoteDateOpened.text
                }

            }
        }



        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        binding.detailNoteTV.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })
        backButtonCallback = object : OnBackPressedCallback(
            true
            /** true means that the callback is enabled */
        ) {
            override fun handleOnBackPressed() {
                detailViewModel.updateNote(
                    binding.detailNoteTitleTV.text.toString(),
                    binding.detailNoteTV.text.toString()
                )
                isEnabled = false
                requireActivity().onBackPressed()
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            backButtonCallback
        )


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
                true
            }
            R.id.detail_menu_item_share -> {
                detailViewModel.note.observe(viewLifecycleOwner) {
                    binding.apply {
                        if (it.isLocked == 1) {
                            Toast.makeText(
                                requireContext(),
                                "You can not share a locked note!",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            val shareIntent = Intent(Intent.ACTION_SEND)
                            shareIntent.type = "text/plain"
                            shareIntent.putExtra(Intent.EXTRA_TEXT, detailNoteTVString)
                            startActivity(Intent.createChooser(shareIntent, "Share using ..."))
                        }
                    }
                }
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }


    private fun deleteNote(note: Note) {
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