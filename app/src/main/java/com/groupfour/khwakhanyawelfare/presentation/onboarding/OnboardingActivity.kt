package com.groupfour.khwakhanyawelfare.presentation.onboarding

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.groupfour.khwakhanyawelfare.databinding.ActivityOnboardingBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OnboardingActivity : AppCompatActivity() {

    private lateinit var binding:ActivityOnboardingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnboardingBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}