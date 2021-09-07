package com.sberkozd.lettervault.ui.home

import android.os.Bundle
import android.view.*
import androidx.core.app.NotificationManagerCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.sberkozd.lettervault.R
import com.sberkozd.lettervault.ui.adapter.NoteAdapter
import com.sberkozd.lettervault.databinding.FragmentHomeBinding
import com.sberkozd.lettervault.notification.NotificationHelper
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {

    private val homeViewModel: HomeViewModel by viewModels()

    private val binding by lazy { FragmentHomeBinding.inflate(layoutInflater) }

    private var gridLayoutManager: GridLayoutManager? = null

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

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        gridLayoutManager = GridLayoutManager(context, 2, LinearLayoutManager.VERTICAL, false)
        noteAdapter = NoteAdapter()

        binding.homeFragmentRV.let {
            it.layoutManager = gridLayoutManager
            it.setHasFixedSize(true)
            it.adapter = noteAdapter
        }


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

                findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToSettingsFragment())
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        homeViewModel.onCreate()
        homeViewModel.noteList.observe(viewLifecycleOwner, {
            noteAdapter?.setItems(it)
        })
    }

}