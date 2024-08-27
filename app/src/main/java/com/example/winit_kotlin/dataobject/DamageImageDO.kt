package com.example.winit_kotlin.dataobject

import java.io.Serializable

data class DamageImageDO(
    var OrderNo: String = "",
    var ItemCode: String = "",
    var LineNo: Int = 0,
    var ImagePath: String = "",
    var CapturedDate: String = "",
    var status: Int = 0,
    var trxCode: String? = null,
    var StoreCheckAppId: String = "",
    var CustomerCode: String = "",
    var BeforeImage: String = "",
    var AfterImage: String = "",
    var Date: String = "",
    var UserCode: String = "",
    var RouteCode: String = ""
) : Serializable {

    companion object {
        const val MODULE = "GRVIMAGES"
        const val COMPETITOR_MODULE = "competitor"
        const val STATUS_IMAGE_NOT_UPLOADED = 0
        const val STATUS_IMAGE_UPLOADED = 100
        const val STATUS_UPLOADED = 200
        const val STATUS_ERROR_WHILE_UPLOADING = -100

        @JvmStatic
        fun getModule() = MODULE

        @JvmStatic
        fun getCompetitorModule() = COMPETITOR_MODULE

        @JvmStatic
        fun getImageNotUploadedStatus() = STATUS_IMAGE_NOT_UPLOADED

        @JvmStatic
        fun getImageUploadStatus() = STATUS_IMAGE_UPLOADED

        @JvmStatic
        fun getUploaded() = STATUS_UPLOADED

        @JvmStatic
        fun getError() = STATUS_ERROR_WHILE_UPLOADING
    }
}
