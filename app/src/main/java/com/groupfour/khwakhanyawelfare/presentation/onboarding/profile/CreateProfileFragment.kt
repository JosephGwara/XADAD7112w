package com.groupfour.khwakhanyawelfare.presentation.onboarding.profile

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.groupfour.khwakhanyawelfare.R
import com.groupfour.khwakhanyawelfare.databinding.FragmentCreateProfileBinding
import dagger.hilt.android.AndroidEntryPoint

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
    }
    private fun setupListeners(){
        binding.continueBtn.setOnClickListener {
            // TODO add validation for fields and navigation to onboarding end
            // TODO local storage for the same data that's being sent to the cloud database
        }
    }

    fun navigateToOnboardingEnd(name:String,phoneNumber:Int){
        val userType = requireArguments().getString("USER_TYPE")
    }
}