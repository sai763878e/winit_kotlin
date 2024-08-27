package com.example.winit_kotlin.parsers

import org.simpleframework.xml.Element
import org.simpleframework.xml.Namespace
import org.simpleframework.xml.Root

@Root(name = "GetMasterDataFileForCPSPromotionResponse", strict = false)
@Namespace(reference = "http://tempuri.org/")
data class GetMasterDataFileForCPSPromotionResponse(
    @field:Element(name = "GetMasterDataFileForCPSPromotionResult", required = false)
    var getMasterDataFileForCPSPromotionResult: GetMasterDataFileForCPSPromotionResult? = null
)
@Root(name = "GetMasterDataFileForCPSPromotionResult", strict = false)
@Namespace(reference = "http://tempuri.org/")
data class GetMasterDataFileForCPSPromotionResult(

    @field:Element(name = "Status", required = false)
    var status: String? = null,

    @field:Element(name = "ServerTime", required = false)
    var serverTime: String? = null,

    @field:Element(name = "Count", required = false)
    var count: Int? = null,

    @field:Element(name = "ModifiedDate", required = false)
    var modifiedDate: String? = null,

    @field:Element(name = "ModifiedTime", required = false)
    var modifiedTime: String? = null,

    @field:Element(name = "IsEOTDone", required = false)
    var isEOTDone: String? = null,

    @field:Element(name = "AuthStatus", required = false)
    var authStatus: String? = null,

    @field:Element(name = "IsValidToken", required = false)
    var isValidToken: String? = null,

    @field:Element(name = "sqliteFileName", required = false)
    var sqliteFileName: String? = null,

    @field:Element(name = "IsStockVerified", required = false)
    var isStockVerified: String? = null,
)
