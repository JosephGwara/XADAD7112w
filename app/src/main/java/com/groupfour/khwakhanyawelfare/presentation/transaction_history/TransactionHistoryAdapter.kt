package com.groupfour.khwakhanyawelfare.presentation.transaction_history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.groupfour.khwakhanyawelfare.data.models.Donation
import com.groupfour.khwakhanyawelfare.databinding.DonationHistoryItemLayoutBinding

class TransactionHistoryAdapter (private val donationList:List<Donation>):RecyclerView.Adapter<TransactionHistoryAdapter.TransactionsViewHolder>() {
    inner class TransactionsViewHolder(val binding:DonationHistoryItemLayoutBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionsViewHolder {
        val binding = DonationHistoryItemLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return TransactionsViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return donationList.size
    }

    override fun onBindViewHolder(holder: TransactionsViewHolder, position: Int) {
        with(holder){
            with(donationList[position]){
                binding.nameTxt.text = this.recipientName
                binding.needTxt.text = this.donationType
            }
        }
    }
}