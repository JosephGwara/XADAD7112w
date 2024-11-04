package com.groupfour.khwakhanyawelfare.presentation.onboarding.profile

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.groupfour.khwakhanyawelfare.R
import com.groupfour.khwakhanyawelfare.data.enums.UserType
import com.groupfour.khwakhanyawelfare.databinding.FragmentCreateProfileBinding
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class CreateProfileFragment : Fragment() {

    private val viewModel: CreateProfileViewModel by viewModels()
    private lateinit var binding:FragmentCreateProfileBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCreateProfileBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservers()
        setupListeners()
    }
    private fun setupListeners(){
        binding.continueBtn.setOnClickListener {
            saveUserDetails(binding.nameEditText.text.toString(),binding.phoneNumberEdittext.text.toString())
        }
    }
    private fun setupObservers() {
        viewModel.onBoardingComplete.observe(viewLifecycleOwner){
            navigateToOnboardingEnd()
        }
    }

    private fun navigateToOnboardingEnd() {
        findNavController().navigate(R.id.action_createProfileFragment_to_onboardingEndFragment)
    }


    private fun saveUserDetails(name:String,phoneNumber:String){
        val userTypeString = requireArguments().getString("USER_TYPE")
        val userType = when(userTypeString){
            "DONOR" -> UserType.DONOR
            "BENEFICIARY" -> UserType.BENEFICIARY
            "EMPLOYEE" -> UserType.EMPLOYEE
            "ANONYMOUS" -> UserType.ANONYMOUS
            else -> {
                UserType.ANONYMOUS
            }
        }
        viewModel.onOnboardingComplete(name = name, phoneNumber = phoneNumber, userType = userType)
    }
}