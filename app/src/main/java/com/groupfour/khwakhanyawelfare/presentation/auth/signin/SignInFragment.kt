package com.groupfour.khwakhanyawelfare.presentation.auth.signin

import android.content.Intent
import androidx.fragment.app.viewModels
import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.groupfour.khwakhanyawelfare.R
import com.groupfour.khwakhanyawelfare.databinding.FragmentSignInBinding
import com.groupfour.khwakhanyawelfare.presentation.home.HomeActivity
import com.groupfour.khwakhanyawelfare.presentation.onboarding.OnboardingActivity
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class SignInFragment : Fragment() {
    private lateinit var binding: FragmentSignInBinding
    private val viewModel: SignInViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignInBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handleOnBack()
        initListeners()
        initObservers()
    }
    private fun handleOnBack() {
        requireActivity().onBackPressedDispatcher.addCallback(
            requireActivity(), object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    displayExitDialogue()
                }
            })
    }
    private fun displayExitDialogue(){
        val alertDialog = AlertDialog.Builder(requireContext())
        alertDialog.apply {
            setTitle(getString(R.string.exit_app))
            setMessage(getString(R.string.exit_the_app))
            setPositiveButton(getString(R.string.yes)){ _, _ ->
                exitApp()
            }
            setNegativeButton(getString(R.string.no)) { _, _ ->

            }
        }.create().show()

    }
    private fun exitApp(){
        requireActivity().finishAffinity()
    }


    private fun initListeners(){
        binding.apply {
            loginTxt.setOnClickListener {
                findNavController().navigate(R.id.action_signInFragment_to_registrationFragment)
            }
            resetPasswordTxt.setOnClickListener {
                findNavController().navigate(R.id.action_signInFragment_to_resetPasswordFragment)
            }
            signInBtn.setOnClickListener {
                if (validateEmail() && validatePassword()){
                    showProgressBar()
                    viewModel.signInUser(emailEditText.text.toString(),passwordEditText.text.toString())
                }
            }
        }

    }
    private fun initObservers(){
        viewModel.signInError.observe(viewLifecycleOwner){ error ->
            hideProgressBar()
            Snackbar.make(binding.root,error, Snackbar.LENGTH_SHORT).show()
            resetFields()

        }
        viewModel.signInSuccessful.observe(viewLifecycleOwner){ isSuccessful ->
           if (isSuccessful){
               checkOnBoardingStatus()
               resetFields()
           }
        }
        viewModel.isUserOnboarded.observe(viewLifecycleOwner){ onboarded ->
            if (!onboarded){
                hideProgressBar()
                navigateToOnboarding()
            }else{
                hideProgressBar()
                navigateToHome()
            }
        }
    }
    private fun checkOnBoardingStatus(){
        viewModel.getUserOnboardingStatus(binding.emailEditText.text.toString())
    }
    private fun navigateToOnboarding(){
        startActivity(Intent(requireContext(), OnboardingActivity::class.java))
        requireActivity().finish()
    }
    private fun navigateToHome(){
        startActivity(Intent(requireActivity(),HomeActivity::class.java))
        requireActivity().finish()
    }

    private fun validateEmail():Boolean{
        val email = binding.emailEditText.text.toString()
        val pattern = Patterns.EMAIL_ADDRESS
        if (email.isEmpty() || email.isBlank()){
            binding.emailEditText.error = getString(R.string.please_enter_an_email)
            return false
        }else if (!pattern.matcher(email).matches()){
            binding.emailEditText.error = getString(R.string.please_enter_a_valid_email)
            return false
        }
        else{
            return true
        }
    }
    private fun validatePassword():Boolean{
        val password = binding.passwordEditText.text.toString()
        if (password.isNotEmpty() || password.isNotBlank()){
            return true
        }else{
            binding.passwordEditText.error = getString(R.string.please_enter_a_password)
            return false
        }
    }
    private fun showProgressBar(){
        binding.progressBar.isVisible = true
    }
    private fun hideProgressBar(){
        binding.progressBar.isVisible = false
    }
    private fun resetFields(){
        binding.emailEditText.text?.clear()
        binding.passwordEditText.text?.clear()
    }


}