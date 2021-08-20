package com.sberkozd.lettervault.ui.home

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.core.app.NotificationManagerCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.sberkozd.lettervault.R
import com.sberkozd.lettervault.adapter.NoteAdapter
import com.sberkozd.lettervault.databinding.FragmentHomeBinding
import com.sberkozd.lettervault.notification.NotificationHelper
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {

    private val homeViewModel: HomeViewModel by viewModels()

    private var _binding: FragmentHomeBinding? = null

    private val binding get() = _binding!!

    private var gridLayoutManager: GridLayoutManager? = null

    private var recyclerView: RecyclerView? = null

    private var noteAdapter: NoteAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

        val notificationHelper = NotificationHelper()

        notificationHelper.createNotificationChannel(
            requireContext(),
            NotificationManagerCompat.IMPORTANCE_HIGH, true,
            getString(R.string.app_name), "App notification channel."
        )


    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        gridLayoutManager = GridLayoutManager(context, 2, LinearLayoutManager.VERTICAL, false)
        noteAdapter = NoteAdapter()
        recyclerView = binding.homeFragmentRV
        recyclerView?.layoutManager = gridLayoutManager
        recyclerView?.setHasFixedSize(true)
        recyclerView?.adapter = noteAdapter

        homeViewModel.noteList.observe(viewLifecycleOwner, {
            noteAdapter?.setItems(it)
        })

        WorkManager.getInstance(requireContext()).getWorkInfosByTagLiveData("notificationWork")
            .observe(viewLifecycleOwner, {
                for (workInfo in it) {
                    if (workInfo != null && workInfo.state == WorkInfo.State.SUCCEEDED) {
                        homeViewModel.onCreate()
                        break
                    }
                }
            })
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_home, menu)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.home_menu_item_add -> {

                findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToAddFragment())

                true
            }
            R.id.home_menu_item_grid -> {


                findNavController().navigate(
                    HomeFragmentDirections.actionHomeFragmentToGridFragment(
                        id
                    )
                )

                true
            }
            R.id.home_menu_item_more -> {

                Toast.makeText(context, "To be implemented!", Toast.LENGTH_SHORT).show()
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }


    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    override fun onResume() {
        super.onResume()
        homeViewModel.onCreate()
        homeViewModel.noteList.observe(viewLifecycleOwner, {
            noteAdapter?.setItems(it)
        })
    }


}