package com.groupfour.khwakhanyawelfare.presentation.auth.signin

import android.content.Intent
import androidx.fragment.app.viewModels
import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.groupfour.khwakhanyawelfare.R
import com.groupfour.khwakhanyawelfare.databinding.FragmentSignInBinding
import com.groupfour.khwakhanyawelfare.presentation.home.HomeActivity
import dagger.hilt.android.AndroidEntryPoint

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
        initListeners()
        initObservers()
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
                    progressBar.isVisible = true
                    viewModel.signInUser(emailEditText.text.toString(),passwordEditText.text.toString())
                }
            }
        }

    }
    private fun initObservers(){
        viewModel.signInError.observe(viewLifecycleOwner){ error ->
            binding.progressBar.isVisible = false
            Snackbar.make(binding.root,error, Snackbar.LENGTH_SHORT).show()
        }
        viewModel.signInSuccessful.observe(viewLifecycleOwner){
            binding.progressBar.isVisible = false
            startActivity(Intent(requireActivity(),HomeActivity::class.java))
            requireActivity().finish()
            //TODO Add Clear fields after unssuccessful signIn and navigation Away
        }
    }
    private fun validateEmail():Boolean{
        val email = binding.emailEditText.text.toString()
        val pattern = Patterns.EMAIL_ADDRESS
        if (email.isEmpty() || email.isBlank()){
            binding.emailEditText.error = "Please enter an email"
            return false
        }else if (!pattern.matcher(email).matches()){
            binding.emailEditText.error = "Please enter a valid email"
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
            binding.passwordEditText.error = "Please enter a password"
            return false
        }
    }


}