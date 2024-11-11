package com.groupfour.khwakhanyawelfare.presentation.home

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
class HomeViewModel @Inject constructor(private val firebaseAuth: FirebaseAuth,private val firestore: FirebaseFirestore) : ViewModel() {

    private var _userSignedOut = MutableLiveData<Boolean>()
    val userSignedOut: LiveData<Boolean> get() = _userSignedOut

    private var _beneficiariesList = MutableLiveData<Int>()
    val beneficiariesList: LiveData<Int> get() = _beneficiariesList

    fun signOutUser() = viewModelScope.launch {
      try {
          firebaseAuth.signOut()
          _userSignedOut.postValue(true)
      }catch (e:Exception){
          Timber.e(e.message)
      }

    }
    fun getBeneficiaries() = viewModelScope.launch (Dispatchers.IO){
        firestore.collection(Constants.FIREBASE_USER_COLLECTION)
            .whereEqualTo("userType", UserType.BENEFICIARY.toString())
            .get()
            .addOnSuccessListener { documents ->
                Timber.tag("JosephGwara").d("Home VM Got the data")

                val userList = mutableListOf<User>()
                for (document in documents){
                    val user = document.toObject(User::class.java)
                    userList.add(user)
                    Timber.tag("JosephGwara").d("DocumentID: ${document.id} and ${document.data}")
                }
                _beneficiariesList.postValue(userList.size)

            }
            .addOnFailureListener {
                Timber.tag("JosephGwara").d("Error Fetching data Beneficiaries VM")
            }
    }






}