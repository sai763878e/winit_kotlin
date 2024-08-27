package com.example.winit_kotlin.dataobject

import java.io.Serializable

data class ChannelDO(
    var ChannelId : String = "",
    var code : String? = "",
    var name : String? = "",
    var isBloacked : Boolean = false
) :Serializable
