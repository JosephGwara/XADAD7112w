package com.groupfour.khwakhanyawelfare.presentation.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.groupfour.khwakhanyawelfare.databinding.ActivityHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    private lateinit var binding:ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}