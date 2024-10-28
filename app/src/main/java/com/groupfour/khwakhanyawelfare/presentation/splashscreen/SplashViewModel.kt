package com.groupfour.khwakhanyawelfare.presentation.splashscreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(private val firebaseAuth: FirebaseAuth): ViewModel() {

    private var _userAuthenticated= MutableLiveData<Boolean>()
    val userAuthenticated: LiveData<Boolean> get() = _userAuthenticated

    //Check onboarding complete
    fun checkAuthStatus() = viewModelScope.launch {
        try {
            val currentUser = firebaseAuth.currentUser
            if (currentUser != null) {
                delay(1500L)
                _userAuthenticated.postValue(true)
            } else {
                delay(1500L)
                _userAuthenticated.postValue(false)
            }
        } catch (e: Exception) {
            Timber.e(e)
        }
    }



}