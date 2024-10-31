package com.groupfour.khwakhanyawelfare.presentation.onboarding.onboarding_end

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.groupfour.khwakhanyawelfare.R
import com.groupfour.khwakhanyawelfare.databinding.FragmentOnboardingEndBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OnboardingEndFragment : Fragment() {


    private val viewModel: OnboardingEndViewModel by viewModels()
    private lateinit var binding: FragmentOnboardingEndBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOnboardingEndBinding.inflate(layoutInflater)
        return binding.root
    }
}