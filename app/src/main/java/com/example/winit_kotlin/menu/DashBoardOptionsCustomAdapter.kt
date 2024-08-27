package com.example.winit_kotlin.menu

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.ExpandableListView
import android.widget.LinearLayout
import com.example.winit_kotlin.R
import com.example.winit_kotlin.databinding.DashboardOptionsCellBinding
import com.example.winit_kotlin.dataobject.Customer
import java.util.Vector

class DashBoardOptionsCustomAdapter(private val vecMenuDOs : Vector<MenuDO>
,private val onMenuClick: (MenuDO) -> Unit) : BaseExpandableListAdapter() {

    var groupPos : Int = -1;
    override fun getGroupCount(): Int {
        return vecMenuDOs.size
    }

    override fun getChildrenCount(groupPosition: Int): Int {
        return vecMenuDOs.get(groupPosition).vecMenuDOs.size
    }

    override fun getGroup(groupPosition: Int): Any {
       return vecMenuDOs.get(groupPosition)
    }

    override fun getChild(groupPosition: Int, childPosition: Int): Any {
       return vecMenuDOs.get(groupPosition).vecMenuDOs.get(childPosition)
    }

    override fun getGroupId(groupPosition: Int): Long {
        return groupPosition.toLong()
    }

    override fun getChildId(groupPosition: Int, childPosition: Int): Long {

        return childPosition.toLong()
    }

    override fun hasStableIds(): Boolean {
      return false
    }

    override fun getGroupView(groupPosition: Int, isExpanded: Boolean, convertView: View?, parent: ViewGroup?): View {
        val binding: DashboardOptionsCellBinding
        if (convertView == null) {
            binding = DashboardOptionsCellBinding.inflate(LayoutInflater.from(parent?.context), parent, false)
        } else {
            binding = DashboardOptionsCellBinding.bind(convertView)
        }

        val menuDO = getGroup(groupPosition) as MenuDO
        // Bind your data here
        binding.apply {
            binding.tvOptionName.text = menuDO.menuName
            ivOptionIcon.setImageResource(menuDO.menuImage)
            if(vecMenuDOs[groupPosition].vecMenuDOs.size > 0)
                ivArrowIcon.visibility =View.VISIBLE
            else
                ivArrowIcon.visibility =View.GONE
            ivChildmargin.visibility = View.GONE

            if (groupPos == groupPosition) {
                if (isExpanded) {
                    val mExpandableListView = parent as ExpandableListView
                    mExpandableListView.expandGroup(groupPosition)
                } else {
                    val mExpandableListView = parent as ExpandableListView
                    mExpandableListView.collapseGroup(groupPosition)
                }
            }


                binding.root.setOnClickListener {
                    groupPos = -1
                    if (menuDO.vecMenuDOs.isNotEmpty()) {
                        if (isExpanded) {
                            (parent as ExpandableListView).collapseGroup(groupPosition)
                        } else {
                            (parent as ExpandableListView).expandGroup(groupPosition)
                        }
                    }else{
                        onMenuClick(menuDO)
                    }
                }


            if (isExpanded) {
                ivArrowIcon.setBackgroundResource(R.drawable.baseline_keyboard_arrow_down_24)
            } else ivArrowIcon.setBackgroundResource(R.drawable.baseline_keyboard_arrow_right_24)
        }


        return binding.root
    }

    override fun getChildView(groupPosition: Int, childPosition: Int, isLastChild: Boolean, convertView: View?, parent: ViewGroup?): View {
        val binding: DashboardOptionsCellBinding
        if (convertView == null) {
            binding = DashboardOptionsCellBinding.inflate(LayoutInflater.from(parent?.context), parent, false)
        } else {
            binding = DashboardOptionsCellBinding.bind(convertView)
        }

        val childMenuDO = getChild(groupPosition, childPosition) as MenuDO
        // Bind your data here
        binding.apply {

            tvOptionName.text = childMenuDO.menuName
            ivOptionIcon.setImageResource(childMenuDO.menuImage)
            ivArrowIcon.visibility = View.GONE
            ivChildmargin.visibility = View.INVISIBLE
            binding.root.setOnClickListener {
                groupPos = groupPosition
                onMenuClick(childMenuDO)
            }


        }


        return binding.root
    }

    override fun isChildSelectable(groupPosition: Int, childPosition: Int): Boolean {
        return true
    }
}