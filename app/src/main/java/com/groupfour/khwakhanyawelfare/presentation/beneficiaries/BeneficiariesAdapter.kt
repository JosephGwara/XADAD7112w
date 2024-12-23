package com.groupfour.khwakhanyawelfare.presentation.beneficiaries

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.groupfour.khwakhanyawelfare.data.models.User
import com.groupfour.khwakhanyawelfare.databinding.BeneficiaryItemLayoutBinding

class BeneficiariesAdapter(private val beneficiariesList:List<User>):RecyclerView.Adapter<BeneficiariesAdapter.BeneficiariesViewHolder>() {

    inner class BeneficiariesViewHolder(val binding:BeneficiaryItemLayoutBinding):RecyclerView.ViewHolder(binding.root)
    private var onClickListener: View.OnClickListener? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BeneficiariesViewHolder {
       val binding = BeneficiaryItemLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return BeneficiariesViewHolder(binding)
    }

    override fun getItemCount(): Int {
       return beneficiariesList.size
    }

    override fun onBindViewHolder(holder: BeneficiariesViewHolder, position: Int) {
        with(holder){
            with(beneficiariesList[position]){
                binding.nameTxt.text = this.name
                binding.schoolTxt.text = this.school
                binding.ageTxt.text = this.age
                binding.needTxt.text = this.donationNeeded.toString()

                itemView.setOnClickListener {
                    onClickListener?.onClick(binding.root)
                }
            }
        }
    }
    interface OnClickListener {
        fun onClick(position: Int, model: User)
    }

    fun setOnClickListener(listener: View.OnClickListener?) {
        this.onClickListener = listener
    }

}