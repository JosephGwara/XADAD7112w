package com.groupfour.khwakhanyawelfare.presentation.auth.signin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(private val firebaseAuth: FirebaseAuth): ViewModel() {
private val TAG = this.javaClass.name
    private var _signInError = MutableLiveData<String>()
    val signInError: LiveData<String> get() = _signInError

    private var _signInSuccessful = MutableLiveData<Boolean>()
    val signInSuccessful: LiveData<Boolean> get() = _signInSuccessful

    fun signInUser(email:String,password:String) = viewModelScope.launch {
        try {
            firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener { task ->
                if (task.isSuccessful){
                    _signInSuccessful.postValue(true)
                    Timber.tag(TAG).d("Successful sign In")
                }else{
                    Timber.tag(TAG).d("Unsuccessful sign In")
                    _signInError.postValue("Unsuccessful sign In")
                }

            }
        }catch (e:Exception){
            Timber.e(e.message)
        }

    }
}