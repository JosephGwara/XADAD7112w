package com.groupfour.khwakhanyawelfare.presentation.onboarding

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.groupfour.khwakhanyawelfare.data.enums.UserType
import com.groupfour.khwakhanyawelfare.data.models.User
import com.groupfour.khwakhanyawelfare.util.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel  @Inject constructor(private val firebaseAuth: FirebaseAuth,private val firestore: FirebaseFirestore) : ViewModel() {

    private var _onBoardingComplete = MutableLiveData<Boolean>()
    val onBoardingComplete: LiveData<Boolean> get() = _onBoardingComplete
    
     fun onOnboardingComplete(userType: UserType) = viewModelScope.launch(Dispatchers.IO) {
        val userEmail = firebaseAuth.currentUser?.email
        if (userEmail != null){
            val user = User(userEmail,userType,true)

            firestore.collection(Constants.FIREBASE_USER_COLLECTION)
                .document(userEmail).set(user)
                .addOnSuccessListener { documentReference ->
                    Timber.d("$documentReference")
                    _onBoardingComplete.postValue(true)
                }
                .addOnFailureListener { e->
                    Timber.e(e)
                }
        }
    }
}