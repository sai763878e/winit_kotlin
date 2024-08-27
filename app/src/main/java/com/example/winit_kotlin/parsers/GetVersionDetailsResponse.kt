package com.example.winit_kotlin.parsers

import org.simpleframework.xml.Element
import org.simpleframework.xml.Namespace
import org.simpleframework.xml.Root


@Root(name = "GetVersionDetailsResponse", strict = false)
@Namespace(reference = "http://tempuri.org/")
data class GetVersionDetailsResponse(
    @field:Element(name = "GetVersionDetailsResult", required = false)
    var checkVersionDO: CheckVersionDO? = null
)

@Root(name = "GetVersionDetailsResult")
@Namespace(reference = "http://tempuri.org/")
data class CheckVersionDO(

    @field:Element(name = "VersionManagementId", required = false)
    var versionManagementId : String? = null,

    @field:Element(name = "Status", required = false)
    var status : Int? = null,

    @field:Element(name = "CreatedBy", required = false)
    var createdBy : String? = null,

    @field:Element(name = "CreatedOn", required = false)
    var createdOn : String? = null,

    @field:Element(name = "ModifiedBy", required = false)
    var modifiedBy : String? = null,

    @field:Element(name = "ModifiedOn", required = false)
    var modifiedOn : String? = null,

    @field:Element(name = "APKFileName", required = false)
    var apkFileName : String = "",

    @field:Element(name = "Description", required = false)
    var description : String? = null

)
