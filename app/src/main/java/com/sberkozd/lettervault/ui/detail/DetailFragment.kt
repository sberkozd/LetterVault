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
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.sberkozd.lettervault.R
import com.sberkozd.lettervault.convertToDateRepresentation
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
                detailNoteDateOpened.text = it.timeUnlocked.let {
                    if (it.isBlank()) "" else it.convertToDateRepresentation()
                }
                if (it.isLocked == 0) {
                    detailIsLockedIcon.visibility = View.GONE
                    activity?.title = this.detailNoteDateOpened.text
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

        return when (item.itemId) {
            R.id.detail_menu_item_delete -> {
                showWarningForDeletion()
                true
            }
            R.id.detail_menu_item_share -> {
                detailViewModel.note.observe(viewLifecycleOwner) {
                    binding.apply {
                        if (it.isLocked == 1) {
                            Toast.makeText(
                                requireContext(),
                                R.string.locked_note_sharing,
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

    private fun showWarningForDeletion() {
        val builder = AlertDialog.Builder(requireContext())

        MaterialAlertDialogBuilder(
            requireContext(),
            R.style.MyThemeOverlay_MaterialComponents_MaterialAlertDialog
        )
            .setMessage(R.string.delete_confirm)
            .setPositiveButton("Yes") { _, _ ->
                detailViewModel.deleteNote()
                backButtonCallback.remove()
                findNavController().popBackStack()
            }
            .setNegativeButton("No") { _, _ ->
            }
            .show()
    }
}