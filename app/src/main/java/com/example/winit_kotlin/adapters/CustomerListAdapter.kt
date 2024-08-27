package com.example.winit_kotlin.adapters

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat.startActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.winit_kotlin.R
import com.example.winit_kotlin.common.AppConstants
import com.example.winit_kotlin.common.Preference
import com.example.winit_kotlin.databinding.CustomerCellBinding
import com.example.winit_kotlin.dataobject.Customer
import com.example.winit_kotlin.utils.getAddress

class CustomerListAdapter(private val fragment: Fragment, private var customerslist : List<Customer>, private val preference: Preference,
                          private val context: Context,
                          private val hmVisits: HashMap<String,Int>?,
                          private val onItemClick: (Customer) -> Unit) : RecyclerView.Adapter<CustomerListAdapter.ViewHolder>() {


    fun refreshData(newCustomerList: List<Customer>) {
        customerslist = newCustomerList
        notifyDataSetChanged() // Notify that the data has changed to refresh the RecyclerView
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomerListAdapter.ViewHolder {
        val binding  = CustomerCellBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CustomerListAdapter.ViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int {
        return customerslist.size
    }

   inner class ViewHolder(private val binding : CustomerCellBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int){
            binding.apply {
                val customer = customerslist[position]
                tvSiteName.text = customer.SiteName
                tvSiteID.text = customer.site
                tvAddress.text = fragment.getAddress(customer)
                tvContactNum.text = customer.address1

                if (customer.paymentType != null &&
                    customer.paymentType?.trim().equals(AppConstants.CUSTOMER_TYPE_CASH, ignoreCase = true)) {

                    ivCustomerType.setImageResource(R.drawable.cash)
                    llCredit.visibility = View.GONE
                    // tvCreditLimit.text = "N/A"
                } else {
                    if (customer.customerType != null &&
                        customer.customerType?.trim().equals(AppConstants.CUSTOMER_CREDIT_TYPE_TC, ignoreCase = true)) {

                        ivCustomerType.setImageResource(R.drawable.stc)
                    } else {
                        ivCustomerType.setImageResource(R.drawable.credit)
                    }

                    if (preference.getIntFromPreference(Preference.USER_TYPE_NEW,AppConstants.USER_MERCHANDISER) == AppConstants.USER_MERCHANDISER) {
                        llCredit.visibility = View.GONE
                    } else {
                        llCredit.visibility = View.VISIBLE
                    }

                    // convertView.alpha = 1.0f

                    // tvCreditLimit.text = "$curencyCode ${amountFormate.format(availLimit)}"
                    // tvUserCreditLimit.text = amountFormate.format(availLimit)

                    when (customer.Attribute9) {
                        "Gold" -> {
                            ivCustCategory.visibility = View.VISIBLE
                            ivCustCategory.setBackgroundResource(R.drawable.customer_gold)
                        }
                        "Silver" -> {
                            ivCustCategory.visibility = View.VISIBLE
                            ivCustCategory.setBackgroundResource(R.drawable.customer_silver)
                        }
                        "Platinum" -> {
                            ivCustCategory.visibility = View.VISIBLE
                            ivCustCategory.setBackgroundResource(R.drawable.customer_platinum)
                        }
                        else -> ivCustCategory.visibility = View.GONE
                    }
                }

                if (!customer.phoneNumber.isNullOrBlank()) {
                    tvContactNum.text = customer.phoneNumber
                } else {
                    if (!customer.mobileNumber1.isNullOrBlank()) {
                        tvContactNum.text = customer.mobileNumber1
                    } else {
                        tvContactNum.text = "N/A"
                    }
                }

                if (hmVisits?.containsKey(customer.site) == true){
                    llVisited.visibility = View.VISIBLE
                    var isGoldenStore : Int? = hmVisits?.get(customer.site)
                    if (isGoldenStore == AppConstants.GOLDEN_STORE_VAL)
                        ivPerfectStore.visibility = View.INVISIBLE
                    else
                        ivPerfectStore.visibility = View.INVISIBLE
                }else{
                    llVisited.visibility = View.GONE
                }

                tvContactNum.setOnClickListener {
                    if (customer.phoneNumber.isNullOrBlank()) {
                        Toast.makeText(context, "Phone Number Not Available", Toast.LENGTH_SHORT).show()
                    } else {
                        val intent = Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", customer.phoneNumber, null))
                        context.startActivity(intent)
                    }
                }
                root.setOnClickListener {
                    onItemClick(customer);
                }

            }
        }
    }
}