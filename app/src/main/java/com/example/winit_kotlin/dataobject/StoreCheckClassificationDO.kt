package com.example.winit_kotlin.dataobject

import java.io.Serializable

data class StoreCheckClassificationDO(
    var sellingSKUClassificationId: Int = 0,
    var code: String = "",
    var description: String = "",
    var sequence: Int = 0,
    var hmProducts: LinkedHashMap<BrandDO, ArrayList<ProductDO>> = LinkedHashMap()
) : Serializable
