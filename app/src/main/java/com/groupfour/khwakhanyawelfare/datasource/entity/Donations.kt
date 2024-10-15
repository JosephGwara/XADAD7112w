package com.groupfour.khwakhanyawelfare.datasource.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "Donations")
data class Donations(
    @PrimaryKey(autoGenerate = true)var id:Long,
    @ColumnInfo(name = "donor") var donor:String,
    @ColumnInfo(name = "beneficiary") var beneficiary:String,
    @ColumnInfo(name = "donation_type") var donationType:String,
    @ColumnInfo(name = "donation_amount") var donationAmount:Double,
    @ColumnInfo(name = "completed") var completed:Boolean
)
