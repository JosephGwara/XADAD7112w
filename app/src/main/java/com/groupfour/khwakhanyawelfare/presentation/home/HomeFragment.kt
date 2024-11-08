package com.groupfour.khwakhanyawelfare.presentation.home

import android.content.DialogInterface
import android.content.Intent
import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.activity.OnBackPressedCallback
import androidx.activity.addCallback
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.viewModelScope
import com.groupfour.khwakhanyawelfare.R
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
       initListeners()
       initObservers()
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

}