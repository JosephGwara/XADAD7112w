package com.groupfour.khwakhanyawelfare.presentation.auth.registration

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.util.regex.Pattern
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(private val firebaseAuth: FirebaseAuth): ViewModel() {

    private val TAG = this.javaClass.name

    private var _registrationError = MutableLiveData<String>()
    val registrationError:LiveData<String> get() = _registrationError

    private var _registrationSuccessful = MutableLiveData<Boolean>()
    val registrationSuccessful:LiveData<Boolean> get() = _registrationSuccessful

 fun registerUser(email:String,password:String) = viewModelScope.launch {
            try {
                firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener { task ->

                    if (task.isSuccessful){
                        Timber.tag(TAG).d("User Registered successfully")
                        _registrationSuccessful.postValue(true)

                    }else if(task.exception?.message?.contains("The email address is already in use by another account")== true){
                        Timber.tag(TAG).d("Failed to signUp, email already in use")
                        viewModelScope.launch {
                            withContext(Dispatchers.Main){
                                _registrationError.postValue("Failed to Register, email already in use")
                            }
                        }

                    }else{
                        Timber.tag(TAG).d("Failed to Register")
                    }
                }
            }
        catch (e:Exception){
            Timber.e(e.message)
            _registrationError.postValue(e.message)
        }
        }
}