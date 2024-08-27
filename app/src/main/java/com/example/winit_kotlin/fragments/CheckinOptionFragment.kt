package com.example.winit_kotlin.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.winit_kotlin.R
import com.example.winit_kotlin.activities.HomeActivity
import com.example.winit_kotlin.common.AppConstants
import com.example.winit_kotlin.common.Preference
import com.example.winit_kotlin.databinding.ActivityGridCellBinding
import com.example.winit_kotlin.databinding.FragmentCheckinOptionBinding
import com.example.winit_kotlin.dataobject.Customer
import com.example.winit_kotlin.menu.CustomerMenu
import com.example.winit_kotlin.menu.KpiDashboardDo
import com.example.winit_kotlin.viewmodel.SharedViewModel
import java.util.Vector

class CheckinOptionFragment : Fragment() {
    private lateinit var binding:FragmentCheckinOptionBinding
    private lateinit var preference : Preference
    private lateinit var customerDashboardAdapter: CustomerDashboardAdapter
    private lateinit var activity: HomeActivity
    private lateinit var sharedViewModel: SharedViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCheckinOptionBinding.inflate(inflater,container,false)
        preference = Preference(requireContext())
        activity = requireActivity() as HomeActivity
        sharedViewModel = ViewModelProvider(requireActivity())[SharedViewModel::class.java]
        return binding.root
    }

    companion object {

    }
    lateinit var customer : Customer
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

       sharedViewModel.customerData.observe(viewLifecycleOwner){ customerData ->
           customer = customerData;
           Toast.makeText(requireContext(), "${customerData.site}", Toast.LENGTH_SHORT).show()
       }

         customerDashboardAdapter = CustomerDashboardAdapter(CustomerMenu(requireContext()).loadDashboard(preference.getIntFromPreference(Preference.USER_TYPE_NEW,AppConstants.USER_VAN_SALES)))
        val gridLayoutManager = GridLayoutManager(context, 2) // 2 columns
        binding.recyclerview.apply {
            layoutManager = gridLayoutManager
            adapter = customerDashboardAdapter
        }
    }
    inner class CustomerDashboardAdapter(private val vectorlist: Vector<KpiDashboardDo>) : RecyclerView.Adapter<CustomerDashboardAdapter.ViewHolder>() {


        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int): CustomerDashboardAdapter.ViewHolder {
            val binding = ActivityGridCellBinding.inflate(LayoutInflater.from(parent.context),parent,false)
            return ViewHolder(binding)
        }

        override fun onBindViewHolder(holder: CustomerDashboardAdapter.ViewHolder, position: Int) {
            holder.bind(vectorlist[position])
        }
        override fun getItemCount(): Int {
            return vectorlist.size
        }
        inner class ViewHolder(private val binding : ActivityGridCellBinding) : RecyclerView.ViewHolder(binding.root) {
            fun bind(kpiDashboardDo: KpiDashboardDo){
                binding.apply {
                    tvDescription.text = kpiDashboardDo.Description
                    ivicon.setImageResource(kpiDashboardDo.image)
                    binding.root.setOnClickListener {

                        when(kpiDashboardDo.objKPI) {
                            CustomerMenu.ACTIVITIES.ACTIVITY_ASSET_CHECK -> {

                            }
                            CustomerMenu.ACTIVITIES.ACTIVITY_STORE_CHECK -> {
                                sharedViewModel.setCustomerData(customer)
                                activity.openFragment(StoreCheckFragment::class.java,getString(R.string.Store_Check))
                            }
                            CustomerMenu.ACTIVITIES.ACTIVITY_SALES_ORDER -> {
                                
                            }
                            CustomerMenu.ACTIVITIES.ACTIVITY_PRESALES_ORDER -> {
                                
                            }
                            CustomerMenu.ACTIVITIES.ACTIVITY_PLANOGRAM -> {
                                
                            }
                            CustomerMenu.ACTIVITIES.ACTIVITY_RETURNS -> {
                                
                            }
                            CustomerMenu.ACTIVITIES.ACTIVITY_COLLECTIONS -> {
                                
                            }
                            CustomerMenu.ACTIVITIES.ACTIVITY_COMPETITOR -> {
                                
                            }
                            CustomerMenu.ACTIVITIES.ACTIVITY_CHECKOUT -> {
                                
                            }
                            null ->{ Toast.makeText(requireContext(), "Error", Toast.LENGTH_SHORT).show()
                            
                            }

                        }
                    }
                }
            }
        }
    }

}