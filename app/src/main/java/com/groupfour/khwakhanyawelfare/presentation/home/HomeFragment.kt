package com.groupfour.khwakhanyawelfare.presentation.home

import android.content.Intent
import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.viewModelScope
import com.groupfour.khwakhanyawelfare.databinding.FragmentHomeBinding
import com.groupfour.khwakhanyawelfare.presentation.auth.AuthActivity


import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by viewModels()
    private lateinit var binding:FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
       initListeners()
       initObservers()
    //TODO Add back button override to prevent navigation back to registration
    }

    private fun initObservers() {
        viewModel.userSignedOut.observe(viewLifecycleOwner){ userSignedOut ->
            if (userSignedOut) navigateToSignIn()
        }
    }

    private fun navigateToSignIn() {
        startActivity(Intent(requireActivity(),AuthActivity::class.java))
        requireActivity().finish()
    }

    private fun initListeners(){
        binding.apply {
            signOutBtn.setOnClickListener {
                viewModel.signOutUser()
            }
        }
    }

}