package com.example.winit_kotlin.dataobject

import java.io.Serializable

data class UOMConversionFactorDO(
    var factor: Float = 0f, // case to packs
    var eaConversion: Float = 0f, // case to each means units
    var UOM: String = "",
    var internalUOM: String = "",
    var ItemCode: String = "",
    var Conversion: String = "",
    var orderQty: Int = 0,
    var wastageQty: Int = 0,
    var damageQty: Int = 0,
    var expiryQty: Int = 0,
    var goodQty: Int = 0,
    var vanQty: Int = 0,
    var prevOrderQty: Int = 0,
    var tempQty: Int = 0,
    var isReturnEditableBlock: Boolean = false
) : Serializable
