package com.example.winit_kotlin.dataobject

import java.io.Serializable

data class SellingSKUClassification(
    var sellingSkusClassificationId: Int = -1,
    var code: String? = "",
    var groupCode: String? = "",
    var description: String? = "",
    var salesOrgCode: String? = "",
    var name: String? = "",
    var fieldName: String? = "",
    var sequence: Int = -1,
    var isActive: Boolean = false,
    var modifiedDate: Int = 0,
    var modifiedTime: Int = 0
) :  Serializable
