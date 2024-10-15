package com.groupfour.khwakhanyawelfare.presentation.auth.signin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.groupfour.khwakhanyawelfare.data.models.User
import com.groupfour.khwakhanyawelfare.util.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(private val firestore: FirebaseFirestore, private val firebaseAuth: FirebaseAuth): ViewModel() {
private val TAG = this.javaClass.name
    private var _signInError = MutableLiveData<String>()
    val signInError: LiveData<String> get() = _signInError

    private var _signInSuccessful = MutableLiveData<Boolean>()
    val signInSuccessful: LiveData<Boolean> get() = _signInSuccessful

    private var _isUserOnboarded = MutableLiveData<Boolean>()
    val isUserOnboarded: LiveData<Boolean> get() = _isUserOnboarded

    fun signInUser(email:String,password:String) = viewModelScope.launch(Dispatchers.IO) {
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

    fun getUserOnboardingStatus(email: String) = viewModelScope.launch(Dispatchers.IO)  {
        firestore.collection(Constants.FIREBASE_USER_COLLECTION)
            .document(email)
            .get()
            .addOnSuccessListener { document ->

                if (document != null && document.exists()){
                    val user = document.toObject(User::class.java)
                    if (user != null) {
                        if (!user.onboardingComplete){
                            _isUserOnboarded.postValue(true)
                        }
                        else{
                            _isUserOnboarded.postValue(false)
                        }
                    }
                }else{
                    Timber.tag(TAG).d("No such user found")
                    _isUserOnboarded.postValue(false)
                }
            }.addOnFailureListener { exception ->
                Timber.tag(TAG).d("Error getting user:$exception")
            }



    }
}