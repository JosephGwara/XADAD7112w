package com.groupfour.khwakhanyawelfare.data.models

import com.google.firebase.Timestamp
import com.groupfour.khwakhanyawelfare.data.enums.DonationType

data class Donation(
    val senderEmail:String? = null,
    val donationType: String? = null,
    val recipientName:String? = null,
    val recipientEmail: String? = null,
    val message:String? = null,
    val timestamp: Long? = null
)
