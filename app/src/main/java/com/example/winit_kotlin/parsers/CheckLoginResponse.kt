package com.example.winit_kotlin.parsers

import android.content.Context
import com.example.winit_kotlin.common.Preference
import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Namespace
import org.simpleframework.xml.Root

@Root(name = "Envelope", strict = false)
@Namespace(reference = "http://schemas.xmlsoap.org/soap/envelope/")
data class SoapEnvelope(
    @field:Element(name = "Body", required = false)
    var body: SoapBody? = null
)

@Root(name = "Body", strict = false)
data class SoapBody(
    @field:Element(name = "CheckLoginResponse", required = false)
    @Namespace(reference = "http://tempuri.org/")
    var checkLoginResponse: CheckLoginResponse? = null,

    @field:Element(name = "GetVersionDetailsResponse", required = false)
    @Namespace(reference = "http://tempuri.org/")
    var getVersionDetailsResponse: GetVersionDetailsResponse? = null,

    @field:Element(name = "IsEncryptResponse", required = false)
    @Namespace(reference = "http://tempuri.org/")
    var isEncryptResponse: IsEncryptResponse? = null,

    @field:Element(name = "CheckMobilityStatusResponse", required = false)
    @Namespace(reference = "http://tempuri.org/")
    var checkMobilityStatusResponse: CheckMobilityStatusResponse? = null,

    @field:Element(name = "PostSignInSignOutLogResponse", required = false)
    @Namespace(reference = "http://tempuri.org/")
    var postSignInSignOutLogResponse: PostSignInSignOutLogResponse? = null,

    @field:Element(name = "GetMasterDataFileResponse", required = false)
    @Namespace(reference = "http://tempuri.org/")
    var getMasterDataFileResponse: GetMasterDataFileResponse? = null,

    @field:Element(name = "GetMasterDataFileForCPSPromotionResponse", required = false)
    @Namespace(reference = "http://tempuri.org/")
    var getMasterDataFileForCPSPromotionResponse: GetMasterDataFileForCPSPromotionResponse? = null
)

//@Root(name = "Body", strict = false)
//data class SoapBody(
//
//    @field:Element(name = "CheckLoginResponse", required = false)
//    @Namespace(reference = "http://tempuri.org/")
//    var checkLoginResponse: CheckLoginResponse? = null,
//
//
//    @field:Element(name = "GetVersionDetailsResponse", required = false)
//    @Namespace(reference = "http://tempuri.org/")
//    var getVersionDetailsResponse : GetVersionDetailsResponse? = null,
//
//
//    @field:Element(name = "IsEncryptResponse", required = false)
//    @Namespace(reference = "http://tempuri.org/")
//    var isEncryptResponse: IsEncryptResponse? = null,
//
//
//    @field:Element(name = "CheckMobilityStatusResponse", required = false)
//    @Namespace(reference = "http://tempuri.org/")
//    var checkMobilityStatusResponse: CheckMobilityStatusResponse? = null,
//
//
//    @field:Element(name = "PostSignInSignOutLogResponse", required = false)
//    @Namespace(reference = "http://tempuri.org/")
//    var postSignInSignOutLogResponse: PostSignInSignOutLogResponse? = null,
//
//    //GetMasterDataFile
//
//    @field:Element(name = "GetMasterDataFileResponse", required = false)
//    @Namespace(reference = "http://tempuri.org/")
//    var getMasterDataFileResponse: GetMasterDataFileResponse
//)



@Root(name = "CheckLoginResponse", strict = false)
@Namespace(reference = "http://tempuri.org/")
data class CheckLoginResponse(
    @field:Element(name = "CheckLoginResult", required = false)
    var checkLoginResult: CheckLoginResult? = null
){
    fun saveToPreferences(context : Context){
        checkLoginResult?.let {
            val preference = Preference(context)
            preference.saveBooleanInPreference("isEOTDone", it.isEOTDone ?: false)
            preference.commitPreference()
        }
    }
}

@Root(name = "CheckLoginResult", strict = false)
@Namespace(reference = "http://tempuri.org/")
data class CheckLoginResult(
    @field:Element(name = "Status", required = false)
    var status: String? = null,

    @field:Element(name = "Message", required = false)
    var message: String? = null,

    @field:Element(name = "Count", required = false)
    var count: Int? = null,

    @field:Element(name = "ModifiedDate", required = false)
    var modifiedDate: String? = null,

    @field:Element(name = "ModifiedTime", required = false)
    var modifiedTime: String? = null,

    @field:Element(name = "IsEOTDone", required = false)
    var isEOTDone: Boolean? = null,

    @field:Element(name = "AuthStatus", required = false)
    var authStatus: Int? = null,

    @field:Element(name = "IsValidToken", required = false)
    var isValidToken: Int? = null,

    @field:Element(name = "BlaseUsers", required = false)
    var blaseUsers: BlaseUsers? = null
)

@Root(name = "BlaseUsers", strict = false)
@Namespace(reference = "http://tempuri.org/")
data class BlaseUsers(
    @field:ElementList(name = "BlaseUserDco", inline = true, required = false)
    var blaseUserDcos: List<BlaseUserDco>? = null
){
    fun saveToPreference(context: Context){
//        blaseUserDcos?.let {
//            val preference = Preference(context)
//            preference.saveBooleanInPreference("isEOTDone", it.[] ?: false)
//            preference.commitPreference()
//        }

    }
}

@Root(name = "BlaseUserDco", strict = false)
@Namespace(reference = "http://tempuri.org/")
data class BlaseUserDco(
    @field:Element(name = "USERID", required = false)
    var userId: String? = null,

    @field:Element(name = "USERNAME", required = false)
    var username: String? = null,

    @field:Element(name = "ROLE", required = false)
    var role: String = "",

    @field:Element(name = "SALESMANCODE", required = false)
    var salesmanCode: String? = null,

    @field:Element(name = "Token", required = false)
    var token: String? = null,

    @field:Element(name = "UserImagePath", required = false)
    var userImagePath: String? = null,

    @field:Element(name = "PhoneNumber", required = false)
    var phoneNumber: String? = null,

    @field:Element(name = "IsLogisticUser", required = false)
    var isLogisticUser: Boolean? = null,

    @field:Element(name = "EmpNo", required = false)
    var empNo: String = "",

    @field:Element(name = "ManagerEmpNo", required = false)
    var managerEmpNo: String? = null,

    @field:Element(name = "RouteCode", required = false)
    var routeCode: String = "",

    @field:Element(name = "SalesOrgCode", required = false)
    var salesOrgCode: String? = null,

    @field:Element(name = "UserType", required = false)
    var userType: String? = null,

    @field:Element(name = "WareHouseCode", required = false)
    var wareHouseCode: String? = null,

    @field:Element(name = "Target", required = false)
    var target: Double? = null,

    @field:Element(name = "AchievedTarget", required = false)
    var achievedTarget: Double? = null,

    @field:Element(name = "WorkingDays", required = false)
    var workingDays: Int? = null,

    @field:Element(name = "TotalWorkingDays", required = false)
    var totalWorkingDays: Int? = null,

    @field:Element(name = "intUserId", required = false)
    var intUserId: Int? = null,

    @field:Element(name = "IsOnlineOnly", required = false)
    var isOnlineOnly: Boolean? = null,

    @field:Element(name = "IsFOC", required = false)
    var isFOC: Boolean? = null,

    @field:Element(name = "SalesOrgAddress", required = false)
    var salesOrgAddress: String? = null,

    @field:Element(name = "SalesOrgFax", required = false)
    var salesOrgFax: String? = null,

    @field:Element(name = "SalesOrgDescription", required = false)
    var salesOrgDescription: String? = null,

    @field:Element(name = "VersionNo", required = false)
    var versionNo: String? = null,

    @field:Element(name = "ServerName", required = false)
    var serverName: String? = null,

    @field:Element(name = "SalesGroup", required = false)
    var salesGroup: String = "",

    @field:Element(name = "IsEOT", required = false)
    var isEOT: Boolean? = null,

    @field:Element(name = "UserSubType", required = false)
    var userSubType: String = "",

    @field:Element(name = "RegionCode", required = false)
    var regionCode: String = "",

    @field:Element(name = "CreditLimit", required = false)
    var creditLimit: Boolean? = null,

    @field:Element(name = "SiteRegion", required = false)
    var siteRegion: String? = null,

    @field:Element(name = "CollectionTarget", required = false)
    var collectionTarget: Double? = null,

    @field:Element(name = "AchievedCollectionTarget", required = false)
    var achievedCollectionTarget: Double? = null,

    @field:Element(name = "LastDayEot", required = false)
    var lastDayEot: Boolean? = null,

    @field:Element(name = "isselected", required = false)
    var isselected: Boolean? = null
)
