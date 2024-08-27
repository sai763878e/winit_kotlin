package com.example.winit_kotlin.parsers

import org.simpleframework.xml.Element
import org.simpleframework.xml.Namespace
import org.simpleframework.xml.Root

@Root(name = "CheckMobilityStatusResponse")
@Namespace(reference = "http://tempuri.org/")
data class CheckMobilityStatusResponse(
    @field:Element(name = "CheckMobilityStatusResult", required = false)
    var status : Int =0
)
