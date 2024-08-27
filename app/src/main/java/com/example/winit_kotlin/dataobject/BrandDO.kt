package com.example.winit_kotlin.dataobject

import java.io.Serializable

data class BrandDO(
    var categoryId: String = "",
    var categoryName: String = "",
    var brandId: String = "",
    var code: String = "",
    var parentCode: String = "",
    var itemMode: String = "",
    var brandName: String = "",
    var agency: String = "",
    var image: String = "",
    var guideline: String = "",
    var discount: Int = 0,
    var isApplicable: Int = 0,
    var brandDiscountID: Int = 0,
    var isDone: Boolean = false,
    var isSelected: Boolean = false,
    var isAvailable: Boolean = false,
    var isOOS: Boolean = true,
    var priority: String = "",
    var brandCode: String = ""
) :Serializable {

    override fun toString(): String {
        return buildString {
            append("categoryId : $categoryId\n")
            append("categoryName : $categoryName\n")
            append("brandId : $brandId\n")
            append("code : $code\n")
            append("parentCode : $parentCode\n")
            append("brandName : $brandName\n")
            append("agency : $agency\n")
            append("image : $image\n")
            append("discount : $discount\n")
            append("isApplicable : $isApplicable\n")
            append("isDone : $isDone\n")
            append("brandDiscountID : $brandDiscountID\n")
        }
    }
}

