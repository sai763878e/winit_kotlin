package com.example.winit_kotlin.dataobject

import java.io.Serializable

data class MapDO(
    var site: String? = "",
    var siteName: String? = "",
    var geoCodeX: Double? = 0.0,
    var geoCodeY: Double? = 0.0,
    var addresss2: String? = "",
    var addresss3: String? = "",
    var sequence: Int? = 0,
    var isJP: Boolean = false,
    var isVisited: Boolean = false
) : Serializable
