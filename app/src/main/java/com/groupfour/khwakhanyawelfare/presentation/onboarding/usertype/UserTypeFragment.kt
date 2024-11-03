package com.groupfour.khwakhanyawelfare.presentation.onboarding.usertype

import android.content.Intent
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
import com.groupfour.khwakhanyawelfare.databinding.FragmentUsertypeBinding
import com.groupfour.khwakhanyawelfare.presentation.home.HomeActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserTypeFragment : Fragment() {
    private val viewModel: UserTypeViewModel by viewModels()

    private lateinit var binding:FragmentUsertypeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUsertypeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListeners()
    }


    private fun setupListeners() {
        binding.apply {
            donorBtn.setOnClickListener {
                navigateToUserProfile(userType = UserType.DONOR)
            }
            beneficiaryBtn.setOnClickListener {
                navigateToUserProfile(userType = UserType.BENEFICIARY)
            }
        }
    }

    /* TODO For Donor Add make donation to home page and to onboarding
    *   View beneficiaries
    *    Beneficiaries helped total number
    *    donation history
    *    Create profile during onboarding for userTypes
    *    simplification of userTypes KwaKhanya employees will be manually created in the DB
    *    Donor name, surname ,phone number
    *    DonationType donation, amount, image of donated thing receival status
    *    Beneficiary notification of donation ,view donation
    *    donation tracker,make donation ,khwakhanya employee marks as received, beneficiary receives and notification if kicked off
    *    Make splash screen look better
    *    Beneficiaries can make requests?Causes that donors can view
    *
    *
    *
    *
    * */


    private fun navigateToUserProfile(userType: UserType){
        val bundle = Bundle()
        bundle.putString("USER_TYPE",userType.toString())
        findNavController().navigate(R.id.action_onboardingFragment_to_createProfileFragment,bundle)
    }


}