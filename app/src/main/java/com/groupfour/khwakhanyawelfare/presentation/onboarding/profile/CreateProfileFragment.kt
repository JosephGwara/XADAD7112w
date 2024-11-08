package com.groupfour.khwakhanyawelfare.presentation.onboarding.profile

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.groupfour.khwakhanyawelfare.R
import com.groupfour.khwakhanyawelfare.data.enums.UserType
import com.groupfour.khwakhanyawelfare.databinding.FragmentCreateProfileBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateProfileFragment : Fragment() {

    private val viewModel: CreateProfileViewModel by viewModels()
    private lateinit var binding:FragmentCreateProfileBinding
    private lateinit var  userTypeString: String
    private lateinit var userType:UserType

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCreateProfileBinding.inflate(layoutInflater)
        userTypeString = requireArguments().getString("USER_TYPE") ?: ""
        userType = when(userTypeString){
            "DONOR" -> UserType.DONOR
            "BENEFICIARY" -> UserType.BENEFICIARY
            "EMPLOYEE" -> UserType.EMPLOYEE
            "ANONYMOUS" -> UserType.ANONYMOUS
            else -> {
                UserType.ANONYMOUS
            }
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
        setupObservers()
        setupListeners()
    }
    private fun setupListeners(){
        binding.continueBtn.setOnClickListener {
            //TODO Add Validation for fields
            when(userType){
                UserType.BENEFICIARY -> navigateToBeneficiaryDetails(binding.nameEditText.text.toString(),binding.phoneNumberEdittext.text.toString(),binding.ageEdittext.text.toString())
                else -> saveUserDetails(binding.nameEditText.text.toString(),binding.phoneNumberEdittext.text.toString())
            }

        }
    }

    private fun navigateToBeneficiaryDetails(name: String, phoneNumber: String, age: String) {
        val bundle = Bundle()
        bundle.putString("USER_TYPE",userTypeString)
        bundle.putString("USER_NAME",name)
        bundle.putString("PHONE_NUMBER",phoneNumber)
        bundle.putString("AGE",age)
        findNavController().navigate(R.id.action_createProfileFragment_to_beneficiaryDetailsFragment,bundle)
    }

    private fun setupObservers() {
        viewModel.onBoardingComplete.observe(viewLifecycleOwner){
            navigateToOnboardingEnd()
        }
    }
    private fun initUI(){
    when(userType){
        UserType.DONOR -> showAge(false)
        UserType.BENEFICIARY -> showAge(true)
      else -> showAge(false)
    }
    }

    private fun showAge(visible:Boolean){
        binding.ageLabel.isVisible = visible
        binding.ageEdittext.isVisible = visible
    }

    private fun navigateToOnboardingEnd(){
        findNavController().navigate(R.id.action_createProfileFragment_to_onboardingEndFragment)
    }


    private fun saveUserDetails(name:String,phoneNumber:String){
        viewModel.onOnboardingComplete(name = name, phoneNumber = phoneNumber, userType = userType)
    }
}