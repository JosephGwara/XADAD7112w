package com.groupfour.khwakhanyawelfare.presentation.home

import android.content.DialogInterface
import android.content.Intent
import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.PopupMenu
import androidx.activity.OnBackPressedCallback
import androidx.activity.addCallback
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import com.groupfour.khwakhanyawelfare.R
import com.groupfour.khwakhanyawelfare.databinding.FragmentHomeBinding
import com.groupfour.khwakhanyawelfare.presentation.auth.AuthActivity
import com.groupfour.khwakhanyawelfare.presentation.create_donation.CreateDonationActivity


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
        val params: ConstraintLayout.LayoutParams = ConstraintLayout.LayoutParams(
            MATCH_PARENT, MATCH_PARENT
        )
        binding.root.layoutParams = params
        handleOnBack()
        return binding.root
    }
    private fun handleOnBack() {
       requireActivity().onBackPressedDispatcher.addCallback(
            requireActivity(), object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    displayExitDialogue()
                }
            })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getBeneficiariesCount()
        viewModel.getUserName()
       initListeners()
       initObservers()
    }

    private fun initObservers() {
        viewModel.userSignedOut.observe(viewLifecycleOwner){ userSignedOut ->
            if (userSignedOut) navigateToSignIn()
        }
        viewModel.beneficiariesList.observe(viewLifecycleOwner){ beneficiariesCount ->
            if (beneficiariesCount != null ){
                toggleProgressBar(false)
                binding.totalBeneficiariesNumber.isVisible = true
                binding.totalBeneficiariesNumber.text = beneficiariesCount.toString()
            }
        }
        viewModel.userName.observe(viewLifecycleOwner){ name ->
            binding.username.text = name
        }
    }

    private fun getBeneficiariesCount(){
        toggleProgressBar(true)
        binding.totalBeneficiariesNumber.isVisible = false
        viewModel.getBeneficiaries()
    }

    private fun navigateToSignIn() {
        startActivity(Intent(requireActivity(),AuthActivity::class.java))
        requireActivity().finish()
    }
    private fun displayExitDialogue(){
        val alertDialog = AlertDialog.Builder(requireContext())
        alertDialog.apply {
            setTitle(getString(R.string.exit_app))
            setMessage(getString(R.string.exit_the_app))
            setPositiveButton(getString(R.string.yes)){ _, _ -> exitApp()}
            setNegativeButton(getString(R.string.no)) { _, _ -> }
        }.create().show()

    }
    private fun exitApp(){
        requireActivity().finishAffinity()
    }

    private fun initListeners(){
        binding.apply {
            menu.setOnClickListener {
                showMenu()
            }
            addDonationButton.setOnClickListener {
                navigateToCreateDonation()
            }

        }
    }

    private fun showMenu(){
        val popupMenu = PopupMenu(layoutInflater.context,binding.menu)
        popupMenu.menu.add("Sign Out?")

        popupMenu.setOnMenuItemClickListener { item ->
            if (item.title == "Sign Out?") {
                signUserOut()
            }
            false
        }
        popupMenu.show()
    }

    private fun signUserOut(){
        viewModel.signOutUser()
    }
    private fun navigateToCreateDonation(){
       startActivity(Intent(requireActivity(),CreateDonationActivity::class.java))
    }

    private fun toggleProgressBar(visible:Boolean){
        binding.progressBar.isVisible = visible
    }

}