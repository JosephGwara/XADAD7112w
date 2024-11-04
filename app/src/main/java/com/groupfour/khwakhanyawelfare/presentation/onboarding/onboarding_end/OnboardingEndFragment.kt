package com.groupfour.khwakhanyawelfare.presentation.onboarding.onboarding_end

import android.content.Intent
import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.groupfour.khwakhanyawelfare.R
import com.groupfour.khwakhanyawelfare.databinding.FragmentOnboardingEndBinding
import com.groupfour.khwakhanyawelfare.presentation.home.HomeActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OnboardingEndFragment : Fragment() {


    private val viewModel: OnboardingEndViewModel by viewModels()
    private lateinit var binding: FragmentOnboardingEndBinding

    //TODO This page should be different depending on the userType

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOnboardingEndBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListeners()
    }

    private fun setupListeners() {
        binding.apply {
            startBtn.setOnClickListener {
                navigateToHome()
            }
        }
    }

    private fun navigateToHome(){
        startActivity(Intent(requireActivity(),HomeActivity::class.java))
        requireActivity().finish()
    }
}