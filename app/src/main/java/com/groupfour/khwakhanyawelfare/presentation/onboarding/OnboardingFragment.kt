package com.groupfour.khwakhanyawelfare.presentation.onboarding

import android.content.Intent
import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import com.groupfour.khwakhanyawelfare.data.enums.UserType
import com.groupfour.khwakhanyawelfare.databinding.FragmentOnboardingBinding
import com.groupfour.khwakhanyawelfare.presentation.home.HomeActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OnboardingFragment : Fragment() {
    private val viewModel: OnboardingViewModel by viewModels()

    private lateinit var binding:FragmentOnboardingBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOnboardingBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservers()
        setupListeners()
    }

    private fun setupObservers() {
        viewModel.onBoardingComplete.observe(viewLifecycleOwner){
            navigateToHome()
        }
    }

    private fun setupListeners() {
        binding.apply {
            donorBtn.setOnClickListener {
               onUserTypeSelect(userType = UserType.DONOR)
            }
            beneficiaryBtn.setOnClickListener {
                onUserTypeSelect(userType = UserType.BENEFICIARY)
            }
            employeeBtn.setOnClickListener {
                onUserTypeSelect(userType = UserType.EMPLOYEE)
            }
        }
    }
    private fun navigateToHome(){
        startActivity(Intent(requireContext(),HomeActivity::class.java))
        requireActivity().finish()
    }

    private fun onUserTypeSelect(userType: UserType){
        binding.progressBar.isVisible = true
        viewModel.onOnboardingComplete(userType)
    }

}