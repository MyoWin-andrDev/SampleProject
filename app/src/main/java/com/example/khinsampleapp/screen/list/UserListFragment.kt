package com.example.khinsampleapp.screen.list

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnScrollListener
import com.example.khinsampleapp.databinding.FragmentUserListBinding
import dagger.hilt.android.AndroidEntryPoint
import com.example.khinsampleapp.screen.list.recyclerview.UserListAdapter
import com.example.khinsampleapp.screen.userdetail.UserDetailActivity

@AndroidEntryPoint
class UserListFragment : Fragment() {

    private lateinit var binding: FragmentUserListBinding
    private val viewModel by viewModels<UserListViewModel>()

    private val userListAdapter = UserListAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUserListBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.loadDataUserList()

        with(binding) {
            // Set up list of users
            userListList.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            userListList.adapter = userListAdapter.apply {
                onUserClickListener = { user ->
                    // For more complex app, Jetpack navigation or a better navigation system that works across modules could be implemented
                    startActivity(Intent(context, UserDetailActivity::class.java).apply {
                        putExtra("userId", user.login)
                    })
                }
            }
            userListList.addOnScrollListener(object : OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    val lastVisibleItem =
                        (recyclerView.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
                    viewModel.loadNextItemsIfNeeded(lastVisibleItem, userListAdapter.itemCount)
                }
            })
        }

        viewModel.userList.observe(viewLifecycleOwner) { userList ->
            userListAdapter.insertToList(userList)
        }
    }

}