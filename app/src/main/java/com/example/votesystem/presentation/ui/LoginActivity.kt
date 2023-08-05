package com.example.votesystem.presentation.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.votesystem.R
import com.example.votesystem.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}