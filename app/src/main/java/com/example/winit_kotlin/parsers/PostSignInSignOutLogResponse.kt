package com.example.winit_kotlin.parsers

import android.content.Context
import com.example.winit_kotlin.common.Preference
import com.google.android.gms.common.api.Status
import org.simpleframework.xml.Element
import org.simpleframework.xml.Namespace
import org.simpleframework.xml.Root

@Root(name = "PostSignInSignOutLogResponse", strict = false)
@Namespace(reference = "http://tempuri.org/")
data class PostSignInSignOutLogResponse(
    @field:Element(name = "PostSignInSignOutLogResult", required = false)
    var postSignInSignOutLogResult: PostSignInSignOutLogResult? = null
){
    fun saveToPreferences(context: Context){
        postSignInSignOutLogResult?.let {
            val preference = Preference(context)
            preference.saveBooleanInPreference("isEOTDone", it.isEOTDone ?: false)
            preference.commitPreference()
        }
    }
}
@Root(name = "PostSignInSignOutLogResult", strict = false)
@Namespace(reference = "http://tempuri.org/")
data class PostSignInSignOutLogResult(
    @field:Element(name = "Status", required = false)
    var status: String? = null,

    @field:Element(name = "Message", required = false)
    var message: String? = null,

    @field:Element(name = "Count", required = false)
    var count: Int = 0,

    @field:Element(name = "ModifiedDate", required = false)
    var modifiedDate: String? = null,

    @field:Element(name = "ModifiedTime", required = false)
    var modifiedTime: String? = null,

    @field:Element(name = "IsEOTDone", required = false)
    var isEOTDone: Boolean = false,

    @field:Element(name = "AuthStatus", required = false)
    var authStatus: Int = 0,

    @field:Element(name = "IsValidToken", required = false)
    var isValidToken: Boolean = false
)


//@Root(name = "PostSignInSignOutLogResult", strict = false)
//@Namespace(reference = "http://tempuri.org/")
//data class PostSignInSignOutLogResult(
//    @field:Element(name = "Status", required = false)
//    var status: String? = null,
//
//    @field:Element(name = "Message", required = false)
//    var message : String? = null,
//
//    @field:Element(name = "Count", required = false)
//    var count : Int,
//
//    @field:Element(name = "ModifiedDate", required = false)
//    var modifiedDate : String? = null,
//
//    @field:Element(name = "ModifiedTime", required = false)
//    var modifiedTime : String? = null,
//
//    @field:Element(name = "IsEOTDone", required = false)
//    var isEOTDone : Boolean? = false,
//
//    @field:Element(name = "AuthStatus", required = false)
//    var authStatus : Int,
//
//    @field:Element(name = "IsValidToken", required = false)
//    var IsValidToken : Boolean? = false
//
//)
