package com.groupfour.khwakhanyawelfare.presentation.auth.registration

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
import com.groupfour.khwakhanyawelfare.databinding.FragmentRegistrationBinding
import dagger.hilt.android.AndroidEntryPoint
import java.util.regex.Pattern

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
        initObservers()
    }

private fun initObservers(){
    viewModel.registrationError.observe(viewLifecycleOwner){ error ->
        binding.progressBar.isVisible = false
        Snackbar.make(binding.root,error,Snackbar.LENGTH_SHORT).show()
    }
    viewModel.registrationSuccessful.observe(viewLifecycleOwner){ successful->
       binding.progressBar.isVisible = false
        if (successful)Snackbar.make(binding.root,"RegistrationSuccessful",Snackbar.LENGTH_SHORT).show()
    }
}
    private fun initListeners(){
       binding.apply {
           registerBtn.setOnClickListener {

              if (validateUsername() &&  validateEmail() && validateFirstPassword() && validateSecondPassword() && checkPasswordsMatch()){
                  progressBar.isVisible = true
                  viewModel.registerUser(binding.emailEditText.text.toString(),binding.passwordEditText.text.toString())
              }
           }
         loginTxt.setOnClickListener {
               findNavController().navigate(R.id.action_registrationFragment_to_signInFragment)
           }

       }
    }

    private fun validateUsername():Boolean{
        val username = binding.usernameEditText.text.toString()
        val pattern = "^[A-Za-z]\\w{5,29}$"
        if (username.isEmpty() || username.isBlank() ){
           binding.usernameEditText.error = "Please enter a username"
            return false
        }
        else if(!Pattern.compile(pattern).matcher(username).matches()) {
            val errorText = when {
               !username.matches(Regex("^[A-Za-z].*")) -> "Username must start with a letter."
                username.length !in 6..30 -> "Username must be between 6 and 30 characters long."
                !username.matches(Regex("^[A-Za-z]\\\\w{5,29}\$")) -> "Username can only contain letters, digits, and underscores."
                else -> ""
            }
            binding.usernameEditText.error = errorText
            return false
        }
        else{
            return true
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
    private fun validateFirstPassword():Boolean{
        val firstPassword = binding.passwordEditText.text.toString()
        if (firstPassword.isEmpty() || firstPassword.isEmpty()){
            binding.passwordEditText.error = "Please enter a password"
            return false
        }
        else if (!validatePasswordPattern(firstPassword)){
            val errorText = when {
                !firstPassword.contains(Regex(".*[A-Z].*")) -> "Password must contain one capital letter"
                !firstPassword.contains(Regex(".*[a-z].*")) -> "Password must contain one lowercase letter"
                !firstPassword.contains(Regex(".*[0-9].*")) -> "Password must contain one digit"
                !firstPassword.contains(Regex(".*[@#\$%^&+=].*")) -> "Password must contain one special character"
                !firstPassword.contains(Regex("\\\\s")) -> "Password must contain one special character"
                firstPassword.length < 6 -> "Password must contain at least 6 characters "
                else -> ""
            }
            binding.passwordEditText.error = errorText
            return false
        }
        else{
            return true
        }
    }

    private fun validateSecondPassword():Boolean{
        val secondPassword = binding.passwordConfirmEditText.text.toString()
        if (secondPassword.isEmpty() || secondPassword.isEmpty()){
            binding.passwordConfirmEditText.error = "Please confirm your password"
            return false
        }else{
            return true
        }
    }
    private fun checkPasswordsMatch():Boolean{
        return binding.passwordEditText.text.toString() == binding.passwordConfirmEditText.text.toString()
    }


    private fun validatePasswordPattern(password: String):Boolean{
        val pattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#\$%^&+=])(?=\\S+\$).{6,}\$"
        return Pattern.compile(pattern).matcher(password).matches()
    }


}