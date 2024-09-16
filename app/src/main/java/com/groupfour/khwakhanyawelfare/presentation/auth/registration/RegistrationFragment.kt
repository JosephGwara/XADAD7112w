package com.groupfour.khwakhanyawelfare.presentation.auth.registration

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.groupfour.khwakhanyawelfare.R
import com.groupfour.khwakhanyawelfare.databinding.FragmentRegistrationBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegistrationFragment : Fragment() {

    private val viewModel: RegistrationViewModel by viewModels()
    private lateinit var binding: FragmentRegistrationBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegistrationBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()
    }


    private fun initListeners(){
       binding.apply {
           registerBtn.setOnClickListener {
           validateUsername()

               viewModel.validateUserDetails("jgwarix@gmail.com","Gwarix123456#","Gwarix23456#")
           }
         loginTxt.setOnClickListener {
               findNavController().navigate(R.id.action_registrationFragment_to_signInFragment)

           }

       }
    }

    private fun validateUsername():Boolean{
        val username = binding.usernameEditText.text.toString()
        if (username.isEmpty() || username.isBlank() ){
            //TODO Show Error to User that username is invalid
            return false
        }
        else{
            viewModel.validateUsername(username)
            return true
        }
    }


}