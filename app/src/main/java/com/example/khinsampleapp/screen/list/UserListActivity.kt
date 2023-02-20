package com.example.khinsampleapp.screen.list

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.khinsampleapp.R
import com.example.khinsampleapp.databinding.ActivityBaseBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserListActivity : AppCompatActivity() {

    private val binding by lazy { ActivityBaseBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // Attach fragment to activity
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, UserListFragment())
            .commit()
    }
}