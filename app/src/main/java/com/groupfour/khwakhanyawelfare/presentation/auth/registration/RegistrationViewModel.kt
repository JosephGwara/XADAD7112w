package com.groupfour.khwakhanyawelfare.presentation.auth.registration

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.regex.Pattern
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(private val firebaseAuth: FirebaseAuth): ViewModel() {

    val TAG = this.javaClass.name

    fun validateUserDetails(email:String,firstPassword:String,secondPassword:String){
        if (validateEmailAddress(email) && validatePasswords(firstPassword,secondPassword)){
            registerUser(email,firstPassword)
        }
    }

    private fun validateEmailAddress(email:String):Boolean{
        val pattern = Patterns.EMAIL_ADDRESS
        return pattern.matcher(email).matches()
    }
    private fun validatePasswords(firstPassword:String,secondPassword:String):Boolean{
        return validatePasswordPattern(firstPassword) && firstPassword == secondPassword
    }
   fun validateUsername(username:String):Boolean{
       val pattern = "^[A-Za-z]\\w{5,29}$"
       return Pattern.compile(pattern).matcher(username).matches()
    }
   private fun validatePasswordPattern(password: String):Boolean{
        val pattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#\$%^&+=])(?=\\\\S+\$).{6,}\$"
        return Pattern.compile(pattern).matcher(password).matches()
    }


 private fun registerUser(email:String,password:String) = viewModelScope.launch {
            try {
                firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener { task ->
                    if (task.isSuccessful){
                        Timber.tag(TAG).d("User Registered successfully")

                    }else{
                        Timber.tag(TAG).d("Failed to register User")
                    }
                }
            }
        catch (e:Exception){
            Timber.e(e.message)
        }



        }


}