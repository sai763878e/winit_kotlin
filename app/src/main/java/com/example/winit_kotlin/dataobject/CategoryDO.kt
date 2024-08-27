package com.example.winit_kotlin.dataobject

import java.io.Serializable

data class CategoryDO(
    var categoryId: String = "",
    var categoryName: String = "",
    var childCount: String = "",
    var categoryIcon: String = "",
    var parentCode: String = "",
    var categoryNameAr: String = "",
    var shelfLife: String = ""
) : Serializable
