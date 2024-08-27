package com.example.winit_kotlin.webAccessLayer

import android.text.TextUtils
import com.example.winit_kotlin.common.Preference


class ServiceURLs {
    companion object{

        var MAIN_URL :String = ""



        const val IMAGE_GLOBAL_URL_PRODUCTION: String = "http://dev-arlavansales.winitsoftware.com/"
//===================================================================================================
       const val MAIN_GLOBAL_URL: String        = "http://alamalicecream-dev.winitsoftware.com/SiteV1/WebServices/Services.asmx"
        var IMAGE_GLOBAL_URL: String      = "http://alamalicecream-dev.winitsoftware.com/SiteV1/"
        const val MASTER_GLOBAL_URL: String     = "http://alamalicecream-dev.winitsoftware.com/SiteV1/WebServices/"
        const val IMAGE_GLOBAL_URL_FULL: String = "http://alamalicecream-dev.winitsoftware.com/SiteV1/uploadfile/upload.aspx?Module=%s"
        const val SURVEY_HOST_URL: String       = "http://alamalicecream-dev.winitsoftware.com/SiteV1/api/survey/"
        const val  IMAGE_GLOBAL_URL_FOR_SURVEY: String = "http://alamalicecream-dev.winitsoftware.com/SiteV1/uploadfile/upload.aspx"
        const val SERVER_IP_ADDRESS: String     = "http://alamalicecream-dev.winitsoftware.com/SiteV1/"
        const val UPLOAD_DB: String             = "http://alamalicecream-dev.winitsoftware.com/SiteV1/uploadfile/upload.aspx?Module=%s"
        const val VERSION_MANAGEMENT: String    = "http://alamalicecream-dev.winitsoftware.com/SiteV1/Data/APKFiles/"


     //==============================================================================================================
//     const val MAIN_GLOBAL_URL: String        = "http://dev-arlavansales.winitsoftware.com/WebServices/Services.asmx"
//     var IMAGE_GLOBAL_URL: String      = "http://dev-arlavansales.winitsoftware.com/"
//     const val MASTER_GLOBAL_URL: String     = "http://dev-arlavansales.winitsoftware.com/WebServices/"
//     const val IMAGE_GLOBAL_URL_FULL: String = "http://dev-arlavansales.winitsoftware.com/uploadfile/upload.aspx?Module=%s"
//     const val SURVEY_HOST_URL: String       = "http://dev-arlavansales.winitsoftware.com/api/survey/"
//     const val  IMAGE_GLOBAL_URL_FOR_SURVEY: String = "http://dev-arlavansales.winitsoftware.com/uploadfile/upload.aspx"
//     const val SERVER_IP_ADDRESS: String     = "http://dev-arlavansales.winitsoftware.com/"
//     const val UPLOAD_DB: String             = "http://dev-arlavansales.winitsoftware.com/uploadfile/upload.aspx?Module=%s"
//     const val VERSION_MANAGEMENT: String    = "http://dev-arlavansales.winitsoftware.com/Data/APKFiles/"

        
        const val SOAPAction: String = "http://tempuri.org/"
        
        const val GetMasterDataFile: String = "GetMasterDataFile"
        
        const val GET_DISCOUNTS: String = "GetDiscounts"
        
        const val GET_JOURNEY_PLAN: String = "GetBeats"
        
        const val GET_CUSTOMER_SITE: String = "GetCustomersByUserId"
        
        const val GET_CUSTOMER_GROUP: String = "GetCustomerGroupByUserId"
        
        const val LOGIN_METHOD: String = "CheckLogin"
        
        const val IS_ENCRYPT: String = "IsEncrypt"
        
        const val VERIFY_LOGIN_METHOD: String = "PostSignInSignOutLog"
        
        const val GET_MESSAGE: String = "getMessages"
        
        const val GET_ALL_ITEMS: String = "GetAllItems"
        
        const val GET_ALL_ROLES: String = "GetAllRoles"
        
        const val GET_ALL_USERS: String = "GetAllUsers"
        
        const val INSERT_NOTE: String = "InsertNote"
        
        const val GetGetCustomerbySellerInvoiceDetails: String =
            "GetGetCustomerbySellerInvoiceDetails"
        
        const val CHANGE_PASSWORD: String = "ChangePassword"
        
        const val INSERT_MESSAGE: String = "InsertMessage"
        
        const val GET_PENDING_SALES_INVOICE: String = "GetPendingSalesInvoice"
        
        const val InsertFreeItem: String = "InsertFreeItem"
        
        const val InsertLiquidationImage: String = "InsertLiquidationImage"
        
        const val BlkInsertHHCustomer: String = "BlkInsertHHCustomer"
        
        const val BlkInsertCustomerDocument: String = "BlkInsertCustomerDocument"
        
        const val GetIncentives: String = "GetIncentives"

        
        const val GetVisibilityAgreementDetails: String = "GetVisibilityAgreementDetails"

        
        const val InsertDeliveryCheck: String = "InsertDeliveryCheck"
        
        const val DailyLoad: String = "DailyLoad"
        
        const val GetSalesHistoryData: String = "GetSalesHistoryData"



        
        const val PostTrxDetails: String = "PostTrxDetails"
        
        const val PostTrxDetailsFromXML: String = "PostTrxDetailsFromXML"
        
        const val Hello: String = "Hello"
        
        const val PostPaymentDetailsFromXML: String = "PostPaymentDetailsFromXML"

        
        const val InsertMarketReportDetails: String = "InsertMarketReportDetails"
        
        const val InsertMarketReportImage: String = "InsertMarketReportImage"


        
        const val GetClearDataPermission: String = "GetClearDataPermission"
        
        const val PostFreeItemXMLWithAuth: String = "PostFreeItemXMLWithAuth"
        
        const val UpdateDeliveryStatus: String = "UpdateDeliveryStatus"

        
        const val GetAllTransfers: String = "GetAllTransfers"

        
        const val DELETE_MESSAGE: String = "DeleteMessages"
        
        var CheckinFile: String = "checkingFile.zip"

        
        const val GET_ALL_LOCATIONS: String = "GetAllLocations"
        
        const val OTHERS: String = "OTHERS"
        
        const val GET_ALL_REASONS: String = "GetAllReasons"
        
        const val INSERT_EOT: String = "InsertEOT"
        
        const val GETPASSCODE: String = "GetPassCode"
        
        const val GETPAYMENTDETAILSSYNC: String = "GetPaymentDetailsSync"
        
        const val GET_ALL_CATEGORY: String = "GetAllCategory"
        
        const val GET_ALL_DELETED_ITEMS: String = "GetAllHHDeletedItems"
        
        const val GET_ALL_PASS_CODE: String = "GetAllPassCode"
        
        const val GET_CUSTOMER_HISTORY: String = "getCustomerHistory"
        
        const val GET_CUSTOMER_HISTORY_WITH_SYNC: String = "getCustomerHistorywithSync"
        
        const val UPDATE_PASSCODE_STATUS: String = "UpdateDAPassCodeStatus"
        
        const val PostClearTaxResponse: String = "PostClearTaxResponse"

        
        const val GET_SPLASH_SCREEN_DATA_FOR_SYNC: String = "GetHHSplashScreenDataforSync"
        
        const val GetHouseHoldMastersWithSync: String = "GetHouseHoldMastersWithSync"
        
        const val GetSalesmanLandmarkWithSync: String = "GetSalesmanLandmarkWithSync"
        
        const val GET_All_PRICE_WITH_SYNC: String = "GetAllHHPriceWithSync"
        
        const val INSERTHH_CUSTOMER_OFFLINE: String = "InsertHHCustomerOffline"
        
        const val UpdateHHCustomer: String = "UpdateHHCustomer"
        
        const val GET_ALL_VEHICLES: String = "GetVehicles"
        
        const val GetThingsToFocusesByUserId: String = "GetThingsToFocusesByUserId"
        
        const val GET_SEQUENCE_NO: String = "GetAvailableTrxNos"
        
        const val GET_HH_SUMMARY: String = "GetVMSummary"
        
        const val GET_HH_DELETED_CUSTOMERS: String = "GetAllHHCustomerDeletedItems"
        
        const val InsertStock: String = "InsertStock"
        
        const val ImportCheckInDemandStock: String = "ImportCheckInDemandStock"
        
        const val GetTrxLogDataSync: String = "GetTrxLogDataSync"
        
        const val GetAdvanceOrderByEmpNo: String = "GetAdvanceOrderByEmpNo"
        
        const val UpdateReturnStock: String = "UpdateReturnStock"
        
        const val UpdateTransferInOrOutStock: String = "UpdateTransferInOrOutStock"
        
        const val PostTransferOuts: String = "PostTransfers"
        
        const val InsertCustomerVisit: String = "InsertCustomerVisit"
        
        const val GetCompetitorDetail: String = "GetCompetitorDetail"
        
        const val GetAllPromotions: String = "GetAllPromotions"
        
        const val GetSellingSKU: String = "GetAllSellingSkusClassifications"
        
        const val PostMovementDetailsFromXML: String = "PostMovementDetailsFromXML"
        
        const val InsertUpdateRouteVanLoadDetail: String = "InsertUpdateRouteVanLoadDetail"
        
        const val ShipStockMovementsFromXML: String = "ShipStockMovementsFromXML"
        
        const val PostSurvey: String = "PostSurvey"
        
        const val GetApprovedMovements: String = "GetApprovedMovements"
        
        const val InsertSkippingReason: String = "InsertSkippingReason"
        
        const val GetAllMovements_Sync: String = "GetAllMovements_Sync"
        
        const val GetAppActiveStatus: String = "GetAppActiveStatus"
        
        const val GetAssetMasters: String = "GetAssetMasters"
        
        const val PostAsset: String = "PostAsset"
        
        const val POSTCUSTOMERPERFECTSTOREMONTHLYSCOREFROMXML: String =
            "PostCustomerPerfectStoreMonthlyScoreFromXML"

        
        const val GetTrxHeaderForApp: String = "GetTrxHeaderForApp"
        
        const val GetTrxHeaderForReturnShelfLife: String = "GetTrxHeaderForReturnShelfLife"
        
        const val GetMessagesForApp: String = "getMessagesForApp"
        
        const val GetJPAndRouteDetails: String = "GetJPAndRouteDetails"
        
        const val GetSurveyMasters: String = "GetSurveyMasters"

        
        const val PostJourneyDetails: String = "PostJourneyDetails"
        
        const val PostOfficeInOutDetails: String = "PostOfficeInOutDetails"
        
        const val PostClientVisits: String = "PostClientVisits"
        
        const val PostAttendanceFromXML: String = "PostAttendanceFromXML"
        
        const val PostCustomerCollectionVisit: String = "PostCustomerCollectionVisit"
        
        const val InsertAssetCustomer: String = "InsertAssetCustomer"
        
        const val DELETE_RECEIPT_FROMAPP: String = "DeleteReceipt"

        
        const val updateDeviceId: String = "updateDeviceId"

        // Signature Upload Module
        
        const val stockverifiedsignature: String = "stockverifiedsignature"
        
        const val eotsignature: String = "eotsignature"
        
        const val assetservicerequest: String = "assetservicerequest"
        
        const val ORDER_IMAGES: String = "orderimage"
        
        const val GET_DA_PASSCODE: String = "GetAllAvailableDAPassCode"

        
        const val GetAllTask: String = "GetAllTask"
        
        const val InsertTaskOrder: String = "InsertTaskOrder"
        
        const val GetAuditStatus: String = "GetAuditStatus"
        
        const val GetAllAcknowledgedTask: String = "GetAllAcknowledgedTask"
        
        const val InsertCustomerGeoCode: String = "InsertCustomerGeoCode"
        
        const val PostStorecheckDetails: String = "PostStorecheckDetails"
        
        const val PostStoreCheckImageDetails: String = "PostStoreCheckImageDetails"
        
        const val InsertSKUClassification: String = "InsertSKUClassification"
        
        const val UpdateCustomerDetails: String = "UpdateCustomerDetails "
        
        const val PostGRVImages: String = "PostGRVImages"
        
        const val PostChequeImages: String = "PostChequeImages"
        
        const val PostVisibilityImages: String = "InsertVisibiityAgreementImage"
        
        const val GetAllDataSync: String = "GetAllDataSync"

        
        const val GetCommonMasterDataSync: String = "GetCommonMasterDataSync"
        
        const val GetReturnOrderStatusDetails: String = "GetReturnOrderStatusDetails"
        
        const val InsertCompetitor: String = "InsertCompetitor"
        
        const val InsertInitiatives: String = "InsertInitiative"
        
        const val INSERT_INITIATIVE_TRADE_PLAN_IMAGE: String = "InsertInitiativeTradePlanImage"
        
        const val InsertAssetHandOver: String = "InsertAssetHandOver"
        
        const val GetAssetHandOverDataSync: String = "GetAssetHandOverDataSync"
        
        const val InsertBdas: String = "InsertBDACustomerImage"
        
        const val Insertplanogram: String = "InsertPlanogramCustomerImage"

        //version -- module
        
        const val GetVersionDetails: String = "GetVersionDetails"


        
        const val PostSQDetailsFromXML: String = "PostSQDetailsFromXML"

        //survey -- module
        
        const val GET_ALL_SURVEY: String = "GetAllSurveyList?"
        
        const val GET_ALL_SURVEY_QUESTIONS: String = "GetSurveyQuestionBySurveyId?"
        
        const val GET_ADD_SURVEY_ANSWER: String = "AddSurveyAnswer"
        
        const val GET_SURVEY_DETAILS_BY_USER_ID: String = "GetSurveyDetailsByUserId?"
        
        const val GET_USER_SURVEY_ANSWER_BY_USER_ID: String = "GetUserSurveyAnswerByUserId?"
        
        const val GET_SURVEY_LIST_BY_USER_ID_BY_SYNC: String = "GetSurveyListByUserIdBySync?"

        //survey - Form - module
        
        const val GET_ALL_FORMS: String = "GetAllForms"
        
        const val GET_FORM_DETAILS_BY_FORM_ID: String = "GetFormDetailsByFromId"
        
        const val GET_INSERT_FORM_REQUEST: String = "InsertFormsRequest"
        
        const val GetAllDeleteLogs: String = "GetAllDeleteLogs"

        
        const val GetVanStockLogDetail: String = "GetVanStockLogDetail"
        
        const val VerifyStockData: String = "VerifyStockData"
        
        const val GetAllEOTDetailsByRouteCode: String = "GetAllEOTDetailsByRouteCode"
        
        const val GetAllJPDivertApprovedRequets: String = "GetAllJPDivertApprovedRequets"
        
        const val GetAllCustomerCollectionRequest: String = "GetAllCustomerCollectionRequest"
        
        const val GetJourneyStartRequestData: String = "GetJourneyStartRequestData"
        
        const val GetPricingOnline: String = "GetPricingOnline"
        
        const val GetPendingInvoiceFromSAP: String = "GetPendingInvoiceFromSAP"

        //rajaprasad
        
        const val GetPaymentHeaderFromApp: String = "GetPaymentHeaderForApp"

        
        const val GetSuggestedQtyByCustomerCode: String = "GetSuggestedQtyByCustomerCode"
        
        const val GetWarehouseStockOnline: String = "GetWarehouseStockOnline"
        
        const val GetCustomerCreditLimit: String = "GetCustomerCreditLimit"
        
        const val InsertVisibiityAgreement: String = "InsertVisibiityAgreement"
        
        const val InsertNearExpiryItem: String = "InsertNearExpiryItem"
        
        const val updateAssetHandOver: String = "updateAssetHandOver"
        
        const val InsertUpdateVanStockReconciliation: String = "PostAuditFromXML"
        
        const val PostReturnAuditFromXML: String = "PostReturnAuditFromXML"
        
        const val GetSettlementsReceiptBySalesmanCode: String =
            "GetSettlementsReceiptBySalesmanCode"
        
        const val PostJourneyPlanDivertRequest: String = "PostJourneyPlanDivertRequest"
        
        const val PostCustomerCollectionRequest: String = "PostCustomerCollectionRequest"
        
        const val PostJourneyStartRequest: String = "PostJourneyStartRequest"

        
        const val InsertDeviceStatus: String = "InsertDeviceStatus"
        
        const val SyncOfflinePricingByCustomer: String = "SyncOfflinePricingByCustomer"
        
        const val GetPromotionsOnline: String = "GetPromotionsOnline"

        
        const val GET_ORDER_STATUS_RESPONSE: String = "getOrderStatusResponse"
        
        const val GET_CHAT_GROUP: String = "GetChatGroup"
        
        const val GET_CHAT_GROUP_USER: String = "GetChatGroupUser"
        
        const val UpdateStoreHistory: String = "UpdateStoreHistory"
        
        const val PostJPGreenCall: String = "PostJPGreenCall"

        
        const val InsertCollectionDeposite: String = "InsertCollectionDeposite"
        
        const val GetCollectionDepositSync: String = "GetCollectionDepositSync"
        
        const val VoidOrderPayments: String = "VoidOrderPayments"

        fun setImageURL(preference: Preference) {
            if (!TextUtils.isEmpty(preference.getStringFromPreference(Preference.SETTINGS_URL, "")))
                IMAGE_GLOBAL_URL = ("http://" + preference.getStringFromPreference(
                Preference.SETTINGS_URL,
                ""
            )).toString() + "/"

            if (TextUtils.isEmpty(IMAGE_GLOBAL_URL)) IMAGE_GLOBAL_URL =
                ServiceURLs.IMAGE_GLOBAL_URL_PRODUCTION
        }

        
        const val PostOrderStampStatus: String = "PostOrderStampStatus"
        
        const val PostAssetLog: String = "PostAssetLog"
        
        const val PostAssetServiceRequest: String = "PostAssetServiceRequest"
        
        const val PostAssetLogImage: String = "PostAssetLogImage"
        
        const val FCM_ServerKey: String =
            "AAAARo7XnUw:APA91bEo-9IdQ1zl6NIS3WSW6Mwkh5IIyC-LaORAdvweYuOOPaeKHj-_teTHrQB0kkuCgFCXbcBe8wmnChvgPvJP_wdUQmGefM1SEEbUqdEvm1j0z3RQmg4AkGj8IUKWDle9I2gTfYpR"
        
        const val ValidateLastEOTSettled: String = "ValidateLastEOTSettled"
        //van to van
        
        const val GET_VAN_STOCKS: String = "GetVanStockDetailByLocationCode"
        
        const val PostCreditReleaseFromXML: String = "PostCreditReleaseFromXML"
        
        const val ApproveAppMovementDetailsFromXML: String = "ApproveAppMovementDetailsFromXML"
        
        const val UpdatePassCodeStatus: String = "UpdatePassCodeStatus"
        
        const val GetVehicleStock: String = "GetVehicleStock"
        
        const val PostVehicleMaintainance: String = "PostVehicleMaintainance"
        
        const val CHECK_MOBILITY_STATUS: String = "CheckMobilityStatus"

        
        const val PostCustomerMessagesFromXML: String = "PostCustomerMessagesFromXML"
        
        const val GetCustomerMessage: String = "GetCustomerMessage"
        
        const val PostSOSDetailsFromXML: String = "PostSOSDetailsFromXML"
        
        const val PostUserImages: String = "PostUserImages"
        
        const val PostPlanogramExecutionFromXML: String = "PostPlanogramExecutionFromXML"
        
        const val InsertSalesmanLoadShipmentVariance: String = "InsertSalesmanLoadShipmentVariance "
        
        const val PostCreditLimitTransactions: String = "PostCreditLimitTransactions "
        
        const val GetMasterDataFileForCPSPromotion: String = "GetMasterDataFileForCPSPromotion"
        
        const val GetAllCPSPromotionSync: String = "GetAllCPSPromotionSync"
        
        const val InsertClosingStock: String = "InsertClosingStock"

        
        const val PostRegisteredCustomerChangeLog: String = "PostRegisteredCustomerChangeLog"
        
        const val BlkInsertHHProspect: String = "BlkInsertHHProspect"
        
        const val BlkInsertProspectDocument: String = "BlkInsertProspectDocument"
    }
}