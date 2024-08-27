package com.example.winit_kotlin.parsers

import com.google.android.gms.common.api.Status
import org.simpleframework.xml.Element
import org.simpleframework.xml.Namespace
import org.simpleframework.xml.Root

@Root(name = "IsEncryptResponse")
@Namespace(reference = "http://tempuri.org/")
data class IsEncryptResponse(
    @field:Element(name = "IsEncryptResult", required = false)
   var status: String? = null
)
