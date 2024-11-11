package com.groupfour.khwakhanyawelfare.presentation.onboarding.beneficiary_details


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.groupfour.khwakhanyawelfare.R
import com.groupfour.khwakhanyawelfare.data.enums.DonationType
import com.groupfour.khwakhanyawelfare.data.enums.UserType
import com.groupfour.khwakhanyawelfare.databinding.FragmentBeneficiaryDetailsBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class BeneficiaryDetailsFragment : Fragment() {

    private val viewModel: BeneficiaryDetailsViewModel by viewModels()
    private lateinit var binding:FragmentBeneficiaryDetailsBinding

    private lateinit var  donationTypeString: String
    private lateinit var  userTypeString: String
    private lateinit var  userType: UserType
    private lateinit var  donationType: DonationType
    private lateinit var  userName: String
    private lateinit var  phoneNumber: String
    private lateinit var  age: String


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBeneficiaryDetailsBinding.inflate(layoutInflater)
        userTypeString = requireArguments().getString("USER_TYPE") ?: ""
        userName = requireArguments().getString("USER_NAME") ?: ""
        phoneNumber = requireArguments().getString("PHONE_NUMBER") ?: ""
        age = requireArguments().getString("AGE") ?: ""
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
        setupNeedSelector()
        setupListeners()
        setupObservers()
    }

    private fun setupObservers() {
        viewModel.onBoardingComplete.observe(viewLifecycleOwner){
                 navigateToOnboardingEnd()
        }
    }

    private fun setupListeners() {
        binding.apply {

            continueBtn.setOnClickListener {
                saveUserDetails()
                toggleProgressBar(true)
            }
        }
    }

    private fun navigateToOnboardingEnd(){
        findNavController().navigate(R.id.action_beneficiaryDetailsFragment_to_onboardingEndFragment)
    }

    private fun setupNeedSelector(){
        val myAdapter = ArrayAdapter(
            requireContext(),
            R.layout.dropdown_item,
            resources.getStringArray(R.array.donation_types)
        )
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.needSelectChoice.setAdapter(myAdapter)
    }
    private fun toggleProgressBar(visible:Boolean){
        binding.progressBar.isVisible = visible
    }

    private fun saveUserDetails(){
        donationTypeString = binding.needSelectChoice.text.toString()
        donationType = when(donationTypeString){
            "MONETARY" -> DonationType.MONETARY
            "FOOD" -> DonationType.FOOD
            "TIME" -> DonationType.TIME
            "CLOTHING" -> DonationType.CLOTHING
            else -> DonationType.MONETARY
        }
        viewModel.onOnboardingComplete(name = userName, phoneNumber = phoneNumber, userType = userType, age = age, school = binding.schoolEditText.text.toString(), donationType = donationType )
    }
}