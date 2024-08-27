package com.example.winit_kotlin.parsers

import org.simpleframework.xml.Element
import org.simpleframework.xml.Namespace
import org.simpleframework.xml.Root

@Root(name = "GetMasterDataFileResponse", strict = false)
@Namespace(reference = "http://tempuri.org/")
data class GetMasterDataFileResponse(
    @field:Element(name = "GetMasterDataFileResult", required = false)
    var getMasterDataFileResult: GetMasterDataFileResult? = null
)
@Root(name = "GetMasterDataFileResult", strict = false)
@Namespace(reference = "http://tempuri.org/")
data class GetMasterDataFileResult(
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
