package com.groupfour.khwakhanyawelfare.presentation.home

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
class HomeViewModel @Inject constructor(private val firebaseAuth: FirebaseAuth) : ViewModel() {

    private var _userSignedOut = MutableLiveData<Boolean>()
    val userSignedOut: LiveData<Boolean> get() = _userSignedOut
    fun signOutUser() = viewModelScope.launch {
      try {
          firebaseAuth.signOut()
          _userSignedOut.postValue(true)
      }catch (e:Exception){
          Timber.e(e.message)
      }

    }
}