package com.example.winit_kotlin.fragments

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.winit_kotlin.activities.HomeActivity
import com.example.winit_kotlin.common.Preference
import com.example.winit_kotlin.databinding.FragmentSalesManBinding
import com.example.winit_kotlin.dataobject.Customer
import com.example.winit_kotlin.viewmodel.SharedViewModel

class SalesManCheckInFragment : Fragment() {

    private lateinit var binding: FragmentSalesManBinding
    private lateinit var sharedViewModel: SharedViewModel
    private lateinit var activity: HomeActivity
    private lateinit var preference : Preference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity = requireActivity() as HomeActivity

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View {
        binding = FragmentSalesManBinding.inflate(inflater,container,false)
        preference = Preference(requireContext())
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

        binding.btnCheckin.setOnClickListener {
            sharedViewModel.setCustomerData(customer)
            activity.openFragment(CheckinOptionFragment::class.java,"")
        }
    }
}