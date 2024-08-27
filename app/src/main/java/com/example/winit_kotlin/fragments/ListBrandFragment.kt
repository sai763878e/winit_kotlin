package com.example.winit_kotlin.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.winit_kotlin.R
import com.example.winit_kotlin.databinding.FragmentListBrandBinding


class ListBrandFragment : Fragment() {
    private lateinit var binding : FragmentListBrandBinding
    private var itemList: List<String>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            itemList = it.getStringArrayList(ARG_ITEM_LIST)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentListBrandBinding.inflate(layoutInflater,container,false)
        // Populate your UI using the itemList, for example:
        binding.textView.text = itemList?.joinToString(", ")
        return binding.root
    }

    companion object {
        private const val ARG_ITEM_LIST = "item_list"

        // Factory method to create a new instance of this fragment
        fun newInstance(items: List<String>): ListBrandFragment {
            val fragment = ListBrandFragment()
            val args = Bundle()
            args.putStringArrayList(ARG_ITEM_LIST, ArrayList(items))
            fragment.arguments = args
            return fragment
        }
    }


}