package com.example.winit_kotlin.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.winit_kotlin.R
import com.example.winit_kotlin.activities.HomeActivity
import com.example.winit_kotlin.adapters.CustomerListAdapter
import com.example.winit_kotlin.common.Preference
import com.example.winit_kotlin.dataaccesslayer.CustomerDA
import com.example.winit_kotlin.dataaccesslayer.CustomerDetailsDA
import com.example.winit_kotlin.databinding.FragmentMyCustomerBinding
import com.example.winit_kotlin.dataobject.Customer
import com.example.winit_kotlin.dataobject.MapDO
import com.example.winit_kotlin.utils.hideLoader
import com.example.winit_kotlin.utils.showLoader
import com.example.winit_kotlin.viewmodel.SharedViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext





class MyCustomerFragment : Fragment() {
    private lateinit var binding: FragmentMyCustomerBinding
    private var originalCustomerList: List<Customer> = emptyList()
    private var filteredCustomerList: List<Customer> = emptyList()
    var objectArray = arrayOfNulls<Any?>(8)
    private var hmCreditLimit : HashMap<String, String>? = null
    private var hmVisits : HashMap<String, Int>? = null
    private lateinit var customerListAdapter : CustomerListAdapter
    private lateinit var preference : Preference
    private var isBlocked : Boolean = false
    private lateinit var activity : HomeActivity
    private lateinit var sharedViewModel: SharedViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        loaddata()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
         binding = FragmentMyCustomerBinding.inflate(inflater,container,false)

        preference = Preference(requireContext())
        activity = requireContext() as HomeActivity

        sharedViewModel = ViewModelProvider(requireActivity())[SharedViewModel::class.java]

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        customerListAdapter = CustomerListAdapter(
            this, originalCustomerList, preference, requireContext(), hmVisits
        ) { customer ->
            sharedViewModel.setCustomerData(customer)
            activity.openFragment(SalesManCheckInFragment::class.java,getString(R.string.customer))
        }

        binding.recyclerview.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerview.adapter = customerListAdapter

        binding.llsearch.apply {
            searchview.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    filterCustomerList(query ?: "")
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    filterCustomerList(newText ?: "")
                    return true
                }
            })

            // Set up a focus change listener to handle focus events
            searchview.setOnQueryTextFocusChangeListener { _, hasFocus ->
                if (hasFocus) {
                    llcancel.visibility = TextView.VISIBLE
                } else {
                    llcancel.visibility = TextView.GONE
                }
            }

            // Handle cancel button click
            llcancel.setOnClickListener {
                searchview.setQuery("", false)
                searchview.clearFocus()
                llcancel.visibility = TextView.GONE
            }
        }
        loadData()
//        showLoader(getString(R.string.loading_customers1))
    }

    companion object {
    }

    private var hmChannelCodes = mutableMapOf<String, MutableList<String>>()
    private var hmCustomerGroupCode = mutableMapOf<String, String>()
    private var hmChannelList = mutableMapOf<String, Boolean>()
    private  var arrMapLocation = mutableListOf<MapDO>()

    private fun loadData() {
        showLoader(getString(R.string.loading_customers1))

        lifecycleScope.launch {
            try {
                val (customersCreditLimit, customerVisit) = withContext(Dispatchers.IO) {
                    val customersDeferred = async { CustomerDA().getCustomerCreditLimitFromJP() }
                    val pricingDeferred = async { CustomerDA().getServedCustomerList() }

                    awaitAll(customersDeferred, pricingDeferred)
                }

                hmCreditLimit = customersCreditLimit as? HashMap<String, String>
                hmVisits = customerVisit as? HashMap<String, Int>

                val customers = withContext(Dispatchers.IO) {
                    CustomerDetailsDA().getJourneyPlan(
                        0, 0, "",
                        preference.getStringFromPreference(Preference.EMP_NO, ""),
                        preference.getStringFromPreference(Preference.ORG_CODE, ""),
                        hmCreditLimit, "", hmVisits
                    )
                }

                objectArray = customers

                // Safely extract customer list based on conditions
                originalCustomerList = when {
                    !isBlocked -> objectArray?.get(0) as? List<Customer> ?: emptyList()
                    objectArray != null && objectArray?.get(6) != null -> objectArray?.get(6) as? List<Customer> ?: emptyList()
                    else -> emptyList()
                }

                // Extract other data
                hmChannelCodes = objectArray?.get(2) as? MutableMap<String, MutableList<String>> ?: mutableMapOf()
                hmCustomerGroupCode = objectArray?.get(3) as? MutableMap<String, String> ?: mutableMapOf()
                hmChannelList = objectArray?.get(4) as? MutableMap<String, Boolean> ?: mutableMapOf()
                arrMapLocation = objectArray?.get(5) as? MutableList<MapDO> ?: mutableListOf()

                // Update UI on the main thread
                withContext(Dispatchers.Main) {
                    filterCustomerList(binding.llsearch.searchview.query.toString())
                }
            } catch (e: Exception) {
                e.printStackTrace()
                // Consider showing an error message to the user here
            } finally {
                hideLoader()
            }
        }
    }

    private fun filterCustomerList(query: String) {
    filteredCustomerList = if (query.isEmpty()) {
        originalCustomerList
    } else {
        originalCustomerList.filter { customer ->
            customer.SiteName?.contains(query, ignoreCase = true) == true ||
                    customer.site?.contains(query, ignoreCase = true) == true ||
                    customer.partyName?.contains(query, ignoreCase = true) == true
        }
    }
    updateUI() // Update the UI with the filtered list
}
    private fun updateUI() {
        if (filteredCustomerList.isEmpty()) {
            binding.recyclerview.visibility = View.GONE
            binding.noData.visibility = View.VISIBLE
        } else {
            binding.recyclerview.visibility = View.VISIBLE
            binding.noData.visibility = View.GONE
            customerListAdapter.refreshData(filteredCustomerList) // Update adapter with the filtered list
        }
    }
}