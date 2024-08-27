package com.example.winit_kotlin.common

import android.graphics.Bitmap
import android.graphics.Typeface
import android.os.Environment
import android.widget.LinearLayout
import java.io.File
import java.util.Currency
import java.util.Vector

object AppConstants {

    
    const val NORMAL_PERMISSIONS: String = "android.permission.SYSTEM_ALERT_WINDOW" +
            "android.permission.WRITE_SETTINGS," +
            "com.mediatek.permission.CTA_ENABLE_BT," +
            "com.winit.alrashed.permission.MAPS_RECEIVE," +
            "com.winit.yaumi.permission.C2D_MESSAGE," +
            "com.google.android.providers.gsf.permission.READ_GSERVICES," +
            "android.permission.SDCARD_WRITE," +
            "com.winit.centurion.permission.MAPS_RECEIVE,android.permission.MANAGE_EXTERNAL_STORAGE"

    
    const val PASSCODE: String = "PASSCODE"
    
    var DIVICE_WIDTH: Int = 0
    var DIVICE_HEIGHT:Int = 0
    
    var SURVEY_TYPE: Int = 1
    
    const val SURVEY_FROM_DASHBOARD: Int = 1
    
    const val SURVEY_FROM_CUSTOMER: Int = 2
    
    var APPLICATION_ID: String = "com.winit.alseer.salesman"
    
    var ACTION_SQLITE_FILE_DOWNLOAD: String =
        "com.winit.alseer.salesman.ACTION_SQLITE_FILE_DOWNLOAD"
    
    var isServeyCompleted: Boolean = false
    
    var PDC_DATE_CONSTANT: Int = 1
    
    var ALARM_TIME_LOAD_NEW_REQ_1: String = "14:00"
    
    var ALARM_TIME_LOAD_NEW_REQ_2: String = "15:30"
    
    var ALARM_TIME_REQ_CODE1: Int = 1
    
    var PERFECT_STORE_UN_UPLOAD_STATUS: Int = 9
    
    var ALARM_TIME_REQ_CODE2: Int = 2
    
    var ALARM_TIME: String = "com.winit.alseer.salesman.PARAMETER.ALARM_TIME"
    
    var ACTION_MENUCLICK: String = "com.winit.alseer.salesman.ACTION.MENUCLICK"
    
    var ACTION_LOGOUT: String = "com.winit.alseer.salesman.ACTION.LOGOUT"
    
    var ACTION_EOTSYNC: String = "com.winit.alseer.salesman.ACTION.EOTSYNC"
    
    var ACTION_NETWORK_CHANGE_RECEIVER: String = "android.net.conn.CONNECTIVITY_CHANGE"
    
    var ACTION_HOUSE_LIST: String = "com.winit.alseer.salesman.ACTION_HOUSE_LIST_NEW"
    
    var ACTION_HOUSE_LIST_NEW: String = "com.winit.alseer.salesman.ACTION_HOUSE_LIST"
    
    var ACTION_HOUSE_LIST_NEW_LOAD: String = "com.winit.alseer.salesman.ACTION_HOUSE_LIST_LOAD"
    
    var ACTION_GOTO_TELEORDERS: String = "com.winit.alseer.salesman.ACTION_GOTO_TELEORDERS"
    
    var ACTION_GOTO_SETTINGS_FINISH: String =
        "com.winit.alseer.salesman.ACTION_GOTO_SETTINGS_FINISH"
    
    var ACTION_GOTO_AR: String = "com.winit.alseer.salesman.ACTION_GOTO_AR"
    
    var ACTION_GOTO_CRLMAIN: String = "com.winit.alseer.salesman.ACTION_GOTO_CRLMAIN"
    
    var ACTION_GOTO_CRL: String = "com.winit.alseer.salesman.ACTION_GOTO_CRL"
    
    var ACTION_GOTO_HOME: String = "com.winit.alseer.salesman.ACTION_GOTO_HOME"
    
    var ACTION_GOTO_JOURNEY: String = "com.winit.alseer.salesman.ACTION_GOTO_JOURNEY"
    
    var ACTION_GOTO_LOGIN: String = "com.winit.alseer.salesman.ACTION_GOTO_LOGIN"
    
    var ACTION_GOTO_HOME1: String = "com.winit.alseer.salesman.ACTION_GOTO_HOME1"
    
    var ACTION_GOTO_NEXT_DAY_JOURNEY: String =
        "com.winit.alseer.salesman.ACTION_GOTO_NEXT_DAY_JOURNEY"
    
    var ACTION_REFRESH_LOAD_REQUEST: String =
        "com.winit.alseer.salesman.ACTION_REFRESH_LOAD_REQUEST"
    
    var ACTION_UPLOAD_DATA: String = "com.winit.alseer.salesman.ACTION_UPLOAD_DATA"
    
    const val DATE_KEY: String = "DATE_KEY"
    
    var ACTION_NOTIFY_AUDIT: String = "com.winit.alseer.salesman.ACTION_NOTIFY_AUDIT"
    
    var ACTION_GOTO_ORDER: String = "com.winit.alseer.salesman.ACTION_GOTO_ORDER"
    
    var TIME_30_DAYS: Long = -2592000000L
    
    var LOAD_STOCK: Int = 1
    
    var UNLOAD_STOCK: Int = 2
    
    var UNLOAD_RETURN_STOCK: Int = 3
    
    var UNLOAD_RECONCILED_STOCK: Int = 4
    
    var baskinLogoPath: String = ""
    
    var CategoryIconsPath: String? = null
    
    var productCatalogPath: String? = null
    
    var LastItemClicked: String = ""
    
    var LastItemLevel: String = ""
    

    var isDayStarted: Boolean = false
    
    var STORE_SIGN_START: Int = 1
    
    var STORE_SIGN_END: Int = 2
    
    var SALES_SIGN_START: Int = 3
    
    var SALES_SIGN_END: Int = 4
    
    var DATABASE_PATH: String = ""
    var Currency: String = ""

    var DATABASE_NAME: String = "salesman.sqlite"
    
    var DATABASE_SALES_HISTORY_NAME: String = "saleshistory.sqlite"
    
    var DATABASE_NAME_CPS: String = "salesmancpsdetails.sqlite"

    
    var PREFS_NAME: String = "krprefs.xml"
    
    var ORDER_LOG: String = "OrderLog.txt"
    
    var Movement_Log: String = "MovementLog.txt"
    
    var PAYMENT_LOG: String = "PaymentLog.txt"
    
    var TabViewLog: String = "TabViewLog.txt"
    
    var ProspectFile: String = "ProspectFile.txt"
    
    var DELIVERY_LOG: String = "DeliversyLog.txt"
    
    var DELIVERY_ERROR_LOG: String = "DeliveryErrorLog.txt"
    
    var SYNC_LOG: String = "SyncLog.txt"
    
    var DEFAULT_TIME_LOG: String = "1900-01-01"
    
    var PAYMENT_ERROR_MSG: String = ""
    
    var REC_ORDER_MONTHS: Int = 3
    
    var VARIANCE_LOG: String = "VanStockLog.txt"
    
    var CRASH_LOG: String = "CrashLog.txt"
    ///ClearTaxFile.txt
    
    var ClearTaxFile: String = "ClearTaxFile.txt"
    
    var UPLOAD_ISSUE_LOG: String = "UploadIssueLog.txt"
    
    var PREFERENCE_LOG: String = "PreferenceLog.txt"

    
    const val ORDER_BY: String = ""
    
    const val IMAGE_TYPE_PAYMENT: String = "PAYMENT"
    
    const val VOID_REFERENCE: String = "VOID REFERENCE"

    
    const val NUMBER_OF_DECIMALS: String = "NUMBER_OF_DECIMALS"
    
    var NUMBER_OF_DECIMALS_TO_ROUND: Int = 2

    
    const val DIRECT: Int = 100
    const val PENDING_INVOICE: Int = 200

    
    var ChequeCheckListOptions: String = "Cheque � Verification Check List"
    
    const val UAE_SALES_ORG_CODE: String = "103"
    
    const val SETTING_IMEOPTION: String = "IMEICategory"

    
    const val CREDIT_CARD_VAS: String = "Credit Card Service Charge"
    
    const val CREDIT_CARD_VAS_INV: String = "CC_VAS_INV"
    
    const val VAS_INV_TYPE: String = "VAS"

    
    var RETRUN_UNLOAD: String = "1"
    
    var DEFAULT_TIME: String = "1900-01-01T12:00:00"
    
    var DEFAULT_TIME_BACKEND: String = "1900-01-01T00:00:00"
    
    var FCM_SERVER_KEY: String =
        "AAAA6l7Mx2Q:APA91bHgrkwiYOyCwXPp3S0Z3_S-NUcUAhE9-miD5L-ZFDHjvyKLCaQVBFsQ4jxHwmY8BeFxtOe0-1pXUTMATs_GBSWbvy98L5j7Y0h09Xw-Z2Rd9mpjx2KurYH0nqMxGiTyUXeXB1iM"
    

    //for store close
    
    const val Store_close: String = "STORE CLOSE"

    
    const val langEnglish: String = "English"
    
    const val langArabic: String = "Arabic"

    
    const val TIME_NEED_TO_SPEND_IN_A_STORE: String = "10"
    


    
    const val TYPE_VANINVENTORY: Int = 200
    
    const val TYPE_WAREHOUSEINVENTORY: Int = 300
    
    const val Store_Check: Int = 1000
    
    const val Capture_Inventory: Int = 1001
    
    const val STATUS_TYPE_CUSTOMER_INFO: Int = 1001
    
    const val STATUS_TYPE_CAPTURE_ASSET: Int = 1010
    
    const val STATUS_TYPE_STORE_CHECK: Int = 1002

    
    const val ORDER_PUSH_LIMIT: Int = 15
    
    const val MAX_ORDER_PUSH_LIMIT: Int = 2
    
    const val SYNC_COUNT: Int = 400
    
    var Activity_Progress_count: Int = 0
    
    var Activity_Completed: Int = 0

    
    const val PRINT_TYPE_WITHOUT_INV: Int = -100
    
    const val PRINT_TYPE_WITH_INV: Int = 100
    
    const val PRINT_TYPE_FOUR_INCH: Int = 830
    
    const val PRINT_TYPE_THREE_INCH: Int = 560
    
    var isFromVan: Boolean = false
    
    const val FILES_Amount: Float = 0.12f
    
    const val FILES_Amount_OMR: Float = 0.012f

    
    var ROUTE_TYPE_AMBIENT: String = "Ambient"
    
    var ROUTE_TYPE_FROZEN: String = "Frozen"

    
    var SALES_ORG_SAUDI: String = "Saudi"
    
    var SALES_ORG_BAHRAIN: String = "Bahrain"
    
    var SALES_ORG_JORDAN: String = "Jordan"
    
    var SALES_ORG_KUWAIT: String = "Kuwait"
    
    var SALES_ORG_QATAR: String = "Qatar"

    
    var SALES_ORG_CODE_SAUDI_: String = "1"
    
    var SALES_ORG_CODE_BAHRAIN: String = "2"
    
    var SALES_ORG_CODE_QATAR: String = "3"
    
    var SALES_ORG_CODE_JORDAN: String = "4"
    
    var SALES_ORG_CODE_KUWAIT: String = "5"

    
    const val PRICE_CODE_BASE_PRICE: String = "1"
    
    const val PRICE_CODE_PROMO_PRICE: String = "2"
    
    const val PRICE_CODE_CUSTOMER_PRICE: String = "3"


    //for Journey plan
    
    var ALL: String = "ALL"
    
    const val PENDING: String = "PENDING"
    
    const val VISITED: String = "VISITED"
    
    const val DELIVERED: String = "DELIVERED"
    
    const val MISSED: String = "MISSED"
    
    const val ZERO_SALES_TAB: String = "ZERO SALES"

    
    const val UNPLANNEDVISIT: String = "UNPLANNED VISIT"
    

    
    var PROMOTION_ITEM_CODES: String = "T551"
    
    var Planogram_ISBeforeCaptureAllow: String = "IS_Planogram_Before_Capture_Allow"

    /********************For user Dashborad */
    
    var Code_StoreCheck: String = "KPI_SC"
    
    var Code_Tasks: String = "KPI_TS"
    
    var Code_Assets: String = "KPI_AS"
    
    var Code_Collections: String = "KPI_CC"
    
    var Code_SalesOrder: String = "KPI_SO"
    
    var Code_CaptureAsset: String = "KPI_CA"
    
    var Code_CaptureDamageOrder: String = "KPI_DO"
    
    var Code_OrderSummary: String = "KPI_OS"
    
    var Code_CustomerPayment: String = "KPI_CP"
    
    var Code_Planogram: String = "KPI_PP"
    
    var Code_ReturnOrder: String = "KPI_RO"
    
    var Code_VisibilityDisplay: String = "KPI_VD"
    
    var Code_MissedOrder: String = "KPI_MO"
    
    var Code_AdvancedOrder: String = "KPI_AO"
    
    var Code_MarketReport: String = "KPI_MR"
    
    var Code_Sampling: String = "KPI_SR"
    
    var Code_Quotation: String = "KPI_QR"
    
    var Code_Liquidation: String = "KPI_LL"
    
    var Code_CustomerInfo: String = "KPI_CI"
    
    var Code_Expiry_Check: String = "KPI_EC"
    
    var Code_Activation: String = "KPI_AA"
    
    var Code_Delivery_Check: String = "KPI_DC"
    
    var Code_Checkout: String = "KPI_CO"
    
    var Code_OTHER: String = "KPI_OO"
    
    var Code_Replacement_Order: String = "KPI_RR"
    
    var Code_7DaysAdvance_Load: String = "KPI_7AL"
    
    var Code_SOS: String = "KPI_SOS"
    
    var Status_SOA: String = "SOA"
    
    var Status_SOS: String = "SOS"
    
    var Type_SOS: String = "SOS"
    
    var Type_COMPETITOR: String = "COMP"
    
    var Status_Planogram: String = "Planogram Check"

    
    var Name_StoreCheck: String = "Store Check"
    
    var Name_Tasks: String = "Tasks"
    
    var Name_Assets: String = "Assets"
    
    var Name_Collections: String = "Collection"
    
    var Name_SalesOrder: String = "Sales Order"
    
    var Name_OrderSummary: String = "Order Summary"
    
    var Name_CustomerPayment: String = "Customer Payment"
    
    var Name_Planogram: String = "Planogram"
    
    var Name_ReturnOrder: String = "Return Order"
    
    var Name_VisibilityDisplay: String = "Visibility/Display"
    
    var Name_MissedOrder: String = "Missed Order"
    
    var Name_AdvancedOrder: String = "Advanced Order"
    
    var Name_MarketReport: String = "Market Report"
    
    var Name_Sampling: String = "Sampling"
    
    var Name_Quotation: String = "Quotation"
    
    var Name_SavedOrder: String = "Saved Order"
    
    var Name_Liquidation: String = "Liquidation"
    
    var Name_CustomerInfo: String = "CustomerInfo"
    
    var Name_Expiry_Check: String = "Expiry Check"
    
    var Name_Activation: String = "Activations"
    
    var Name_Delivery_Check: String = "Delivery Check"
    
    var Name_Checkout: String = "Check Out"
    
    var Name_Capture_Asset: String = "Capture Asset"
    
    var Name_Replacement_Order: String = "Replacement Order"
    
    var Name_7DaysAdvanceLoad: String = "7 Days Advance Load Request"
    
    var Name_7DaysAdvanceLoad_OS: String = "Advance Load Request"



    
    var Action_CheckIn: String = "CheckInOption"
    
    var Action_CheckIn_Skip: String = "CheckInOption_Skip"
    
    var Type_StoreCheck: String = "StoreCheck"
    
    var Type_CaptureAsset: String = "CaptureAsset"
    
    var Type_Survey: String = "Survey"
    
    var Type_Task: String = "Task"
    
    var Type_Assets: String = "Assets"
    
    var Type_Collections: String = "Collection"
    
    var Type_CrossMerchandising: String = "Cross_Merch"
    
    var Type_SalesOrder: String = "SalesOrder"
    
    var Type_PresalesOrder: String = "Presales"
    
    var Type_ReturnOrder: String = "ReturnOrder"
    
    var Type_MissedOrder: String = "MissedOrder"
    
    var Type_AdvancedOrder: String = "AdvancedOrder"
    
    var Type_Sampling: String = "Sampling"
    
    var Type_Quotation: String = "Quotation"
    
    var Type_SavedOrder: String = "SavedOrder"
    
    var Type_Liquidification: String = "Liquidification"
    
    var Type_CustomerInfo: String = "CustomerInfo"
    
    var Type_Expiry_Check: String = "ExpiryCheck"
    
    var Type_Activation: String = "Activation"
    
    var Type_Delivery_Check: String = "DeliveryCheck"
    
    var Type_Checkout: String = "Check Out"
    
    var Type_Order_Summary: String = "Order Summary"
    
    var Type_Customer_Payment: String = "Payment Summary"
    
    var Type_Code_Visibility: String = "Code Visibilty"
    
    var Type_Code_Task: String = "Code Task"
    
    var Type_other: String = "Other"
    
    var Type_Replcement_Order: String = "Replacement Order"

    //========startDate======
    
    var Status_SYNC: String = "SYNC"
    
    var Status_DATA_CHECK: String = "DATA CHECK"
    
    var Status_INTERNET_SPEED: String = "INTERNET SPEED"
    
    var Status_BATTERY_CHECK: String = "BATTERY CHECK"
    
    var Type_Attendance: String = "Attendance"

    
    var Type_CollectionEndorsement: String = "Collection Endorsement"
    
    var Type_CallEndorsement: String = "Call Endorsement"
    
    var Type_LoadEndorsement: String = "Load Endorsement"
    
    var Type_OrderSubmissionEndorsement: String = "Order Endorsement"
    
    var Type_SupervisorEndorsement: String = "Supervisor Endorsement"

    
    var LOAD_TYPE_EMERGENCY: String = "Emergency"
    
    var LOAD_TYPE_REGULAR: String = "Normal"
    
    var LOAD_TYPE_RECOMENDED: String = "Recommended"

    
    var OTHER_UOM_BELOW_SELLING_UOM: String = "OTHER_UOM_BELOW_SELLING_UOM"
    
    var STC_Blocking_Recommendedorder_enabled: String = "STC_Blocking_Recommendedorder_enabled"


    /*for view pager tabs*/ /*
	 * For Duplicate Orders Test
	 */
    
    const val MAX_DUPLICATE_ORDER_PUSH_LIMIT: Int = 5
    
    const val DUPLICATE_ORDER_PUSH_STATUS_1: Int = -100
    
    const val DUPLICATE_ORDER_PUSH_STATUS_2: Int = -1
    
    const val STATUS_TYPE_PLANOGRAM: Int = 1004
    
    const val STATUS_TYPE_VISIBILITY: Int = 1003
    
    const val STATUS_TYPE_MARKET_REPORT: Int = 1006
    
    const val STATUS_TYPE_BDA: Int = 1005
    
    const val STATUS_TYPE_SURVEY: Int = 1007

    
    const val BUNDLED_PROMOTIONS: String = "'ZA05','ZB05','ZC05'"
    
    const val FOC_PROMOTIONS: String = "'ZA11','ZB11','ZC11'"

    
    const val THINGS_TO_FOCUS_EVERY_LOGIN: String = "EveryLogin"
    
    const val THINGS_TO_FOCUS_FIRST_TIME_LOGIN: String = "OnlyAtFirstLogin"

    
    var Type_Visibility: String = "Visibility"
    
    var Type_MarketReport: String = "MarketReport"
    
    var Type_Planogram: String = "Planogram"
    
    var Type_BDA: String = "BDA"
    
    var Type_SURVEY: String = "Survey"


    
    var SUPERVISOR_LOGIN: String = "SPV0001"
    
    var SUPERVISOR_PASSWORD: String = "password"

    
    var SequenceNeedtoFollwed: String = "password"

    
    var SOS_MANDATORY_CATEGORIES: String = "SOS_MANDATORY_CATEGORIES"

    
    var DATABASE_PATH_CPS: String = ""
    
    var Collection_VOID: String = "Collection_Void"


    /*
	 * Reasons
	 */
    
    var ZERO_SALES: String = "Zero Sales"
    
    var Invoice_void: String = "Invoice void"
    
    var Remote_Collection: String = "Remote Collection"
    
    var FORCE_CHECKIN: String = "Force Check In"
    
    var NO_ASSET: String = "NO ASSET"
    
    var LOCATION_PULL_OUT: String = "Location Pull Out"

    
    var Asset_Type_Of_Issue: String = "Asset_Type_Of_Issue"
    
    var Asset_Priroity: String = "Asset_Priroity"
    
    var Asset_Model: String = "Asset_Model"
    
    var EarlyPaymentDisc: String = "EarlyPaymentDisc"

    
    var TRAY_ITEMCODE: String = "30-0101"

    
    var FOC: String = "FOC"
    
    var DAMAGED_RETURN: String = "DAMAGED RETURN"
    
    var DAMAGED_ORDER: String = "DAMAGED ORDER"
    
    var Damage: String = "Damage"
    
    var Expiry: String = "Expiry"
    
    var Returns: String = "Returns"
    
    var SALES: String = "Sales"

    
    const val REASON_PaymentTerm: String = "PaymentTerm"
    
    const val REASON_ProspectModeofdelivery: String = "ProspectModeofdelivery"
    
    const val REASON_ProspectVisitfrequency: String = "ProspectVisitfrequency"
    
    const val REASON_OutletOffDays: String = "ProspectModeofdelivery"
    
    const val REASON_WeekDays: String = "ProspectModeofdelivery"


    //	public  String Damage = "Damage";
    //	public  String Expiry = "Expiry";
    //	public  String Returns = "Returns";
    
    const val PRINT_TYPE_WITH_PRICE: Int = 1
    
    const val PRINT_TYPE_WITHOUT_PRICE: Int = 2


    /*
	 * Message Types
	 */
    
    const val MESSAGE_TYPE_AUDIT: Int = 20
    
    const val MESSAGE_TYPE_EOT: Int = 50
    
    const val MESSAGE_TYPE_JP_DIVERT: Int = 60
    
    const val MESSAGE_TYPE_COLLECTION_SETTELEMENT: Int = 13

    
    const val USER_PRESELER: Int = 100
    
    const val USER_VAN_SALES: Int = 200
    
    const val USER_MERCHANDISER: Int = 300

    
    var Device_Display_Width: String = Preference.DEVICE_DISPLAY_WIDTH
    
    var Device_Display_Height: String = Preference.DEVICE_DISPLAY_HEIGHT
    
    var DEVICE_DISPLAY_WIDTH_DEFAULT: Int = 800
    
    var DEVICE_DISPLAY_HEIGHT_DEFAULT: Int = 1216

    
    var CAPTURE_IMAGE_WIDTH: Int = 640
    
    var CAPTURE_IMAGE_HEIGHT: Int = 360

    
    const val GRVD: String = "GRVD"
    
    const val GRVG: String = "GRVG"

    
    const val TRX_TYPE_ORDER: Int = 1
    
    const val TRX_TYPE_PAYMENT: Int = 2
    
    const val TRX_TYPE_ADV_LOAD: Int = 3
    
    const val TRX_TYPE_ORDER_PAYMENT: Int = 3
    
    const val TRX_TYPE_SURVEY: Int = 4


    
    const val OVERDUE_TYPE_D: String = "D" //  # Days from the date of invoice
    
    const val OVERDUE_TYPE_W: String = "W" // # Weeks from the date of invoice
    
    const val OVERDUE_TYPE_DBL: String = "DBL" //  # Days from the date of invoice
    
    const val OVERDUE_TYPE_ADVANCE: String = "ADVANCE" //=====0
    
    const val OVERDUE_TYPE_CAD: String = "CAD" //=====0
    
    const val OVERDUE_TYPE_CASH: String = "CASH" //=====0
    
    const val OVERDUE_TYPE_CM: String = "CM" //  # Current Month END  + date of invoice
    
    const val OVERDUE_TYPE_FULLPAY: String = "FULLPAY" //=====0
    
    const val OVERDUE_TYPE_OTHER: String = "OTHER" //=====0
    
    const val OVERDUE_TYPE_Prepayment: String = "Prepayment" //=====0






    
    const val ONE_MONTH_DATA: String = "ONE MONTH DATA"
    
    var JOURNEY_CALL: String = "Journey Call"
    
    var COLLECTIONS: String = "Collection"
    
    var INVOICES: String = "Invoices"
    
    var CREDITNOTE: String = "CreditNote"
    
    var STORECHECK: String = "D-Check"
    
    var PreSalesOrder: String = "Presales"
    
    var EOT: String = "EOT"

    
    var Type_Merchandiser: String = "Merchandiser"
    
    const val CAMERA_PIC_REQUEST_FOR_MERCHANDIZECAM: Int = 137


    /*public  String presellerOptionGT[]   = {"Journey Plan","Load Management","My Customers",
		"AR Collection","Stock Inventory","Stock Verification", 
		"Add New Customer","Order Summary", "Payment Summary", 
		"Product Catalog","Today`s Dashboard","EOT", 
		"New Launches","Settings",	"Asset Request",
		"Competitor","Transfer", "About Application",
		"NotifiCations", "Logout","footer"};*/
    
    var CUSTOMER_STATEMENT_SALES_INVOICE: String = "Sales Invoice"
    
    var CUSTOMER_STATEMENT_CREDIT_NOTE: String = "Credit Note"
    
    var CUSTOMER_STATEMENT_PAYMENT: String = "Payment"
    
    var CUSTOMER_STATEMENT_COLLECTION: String = "Collection"

    
    var ASSET_STATUS_ALLOCATE: Int = 1
    
    var ASSET_STATUS_ALLOCATED: Int = 2
    
    var ASSET_STATUS_RETURN: Int = 3
    
    var ASSET_STATUS_DELETE: Int = 4
    
    var ASSET_STATUS_UNLOAD: Int = 0
    //=================Newly Added====================
    
    var ASSET_STATUS_SWITCHED: Int = 5
    
    var ASSET_STATUS_SERVICE_REQUESTED: Int = 6
    
    var ASSET_STATUS_TRANSFER: Int = 7

    
    var ASSET_STATUS_PENDING: Int = 0
    
    var ASSET_STATUS_FIRST_LEVEL_APPROVAL: Int = 1
    
    var ASSET_STATUS_SECOND_LEVEL_APPROVAL: Int = 2
    
    var ASSET_STATUS_APPROVED_FINAL: Int = 3

    //////////////////////////////
    
    const val ASSSET_GENERAL: String = "General"
    
    const val ASSSET_TYPE_NEW_REQUEST: String = "New Asset"
    
    const val ASSSET_TYPE_SERVICE_REQUEST: String = "Service / Maintenance Request"
    
    const val ASSSET_TYPE_SWITCH_REQUEST: String = "Switch Request"
    
    const val ASSSET_TYPE_TRANSFER_REQUEST: String = "Transfer Request"

    
    const val ASSET_EXEC_TYPE_ASSET_CHECK: String = "Asset Check"
    
    const val ASSET_EXEC_TYPE_SERVICE_REQUEST: String = "Service Request"

    
    const val UserType_MT: String = "ModernTrade"
    
    const val UserType_GT: String = "GeneralTrade"
    
    const val UserType_TT: String = "TradtionalTrade"
    
    const val UserType_FS: String = "FoodService"
    
    const val UserType_WS: String = "WholeSales"
    
    const val PRESALES_COLLECTIONS: String = "PRESALES_COLLECTIONS"


    
    var ASSET_TYPE_PERMANENT: String = "PERMANENT"


    
    var Product_Catelog: String = "ProductCatelog"

    
    var OFFICE_CHECKIN: String = "OFFICE_CHECKIN"
    
    var OFFICE_CHECKOUT: String = "OFFICE_CHECKOUT"
    
    var DEPOT_CHECKIN: String = "DEPOT_CHECKIN"
    
    var DEPOT_CHECKOUT: String = "DEPOT_CHECKOUT"

    
    var NO_PRICE_ITEMS: String = "T551,902"



    
    const val ChatGroup: String = "ChatGroup"
    
    const val IsFromNotification: String = "IsFromNotification"
    
    const val SupervisorUser: String = "SupervisorUser"



    
    var CollectorSalesMenuOptions: Array<String> = arrayOf(
        "My Customers",
        "Blocked Customers",
        "Execution Summary",
        "Others",
        "Logout",
        "Footer"
    )
    



    /* ==============================VanSales User Menu Options ====================================*/
    
    var vanSalesMenuOptions: Array<String> = arrayOf(
        "Today's Journey Plan",
        "Next Day's Journey Plan",
        "My Customers",
        "Blocked Customers",
        "Load Management",
        "Execution Summary",
        "Add New Customer",  /*"Commissions",*/ /*"Price List",*/ //			"Product Catalog",
        //			"New Launches",
        //			"Chat",
        //			"Things To Focus",
        //			"EOT",

        "End Journey",  //			"Reports",
        //			"Fuel Fill",
        //			"Car Wash Maintenance",
        "Others",  //			"Gate Keeper",
        //			"Endorsement",
        //			"Start Journey Request",
        //			"Collection Deposite",
        //			"Available PassCode",
        //			"Load endorsement",
        //			"R Vs B capacity",
        "Logout",
        "Footer" //			,"Chat"

    )
    

    
    var customerMenuOptions: Array<String> = arrayOf(
        "Customer Dashboard",  //			"Agreement/Contract",
        //			"Rebates", 
        "Execution Summary",
        "Active Promotions",
        "Active SP",  /*"Price list",*/ //			"Product catalog",
        "Others",
        "Check out",  //			"",
        "Footer"
    )
    



    /*=========================================== Presales Menu Options=====================================================*/
    
    var presellerMenuOption: Array<String> = arrayOf(
        "Journey Plan",  //			"Next Day Journey Plan",
        //			"My Customers",
        ////			"Warehouse Stock",
        //			"Execution Summary",
        //			"Add New Customer",
        //			/*"Product Catalog",
        //			"New Launches",*/
        //			"EOT",
        //			"End Journey",
        "Others",
        "Sruvey",
        "Endorsement",
        "Logout",
        "Footer",  //			"Chat"
    )


    



    /*********************************************Pre Sales Customer Menu Options */
    
    var presellerCheckedInMenuOption: Array<String> = arrayOf(
        "Customer Dashboard",  /*"Agreement/Contract",
			"Rebates", 
			"Warehouse Stock", */
        "Execution Summary",  /*"Price list",
			"Product catalog",*/
        "Others",
        "Check out",
        "Footer"
    )
    

    /*======================================================================================================================*/
    
    var vanSalesMenuOption: Array<String> = arrayOf(
        "Journey Plan",
        "Next Day Journey Plan",
        "My Customers",
        "Load Management",
        "Execution Summary",
        "Add New Customers",
        "Collection Settlement",
        "Messaging",
        "EOT",
        "End Journey",
        "Others",
        "Things to Focus",
        "Auditor Login",
        "Audit Receipts",
        "Office Check-In/Out",
        "Logout",
        "footer"
    )

    


    
    var merchandiserMenuOption: Array<String> =
        arrayOf("My Customers", "Execution Summary", "Others", "Logout", "footer")

    



    /*

	public  String merchandiserMenuOption[]   = {"Journey Plan","Next Day Journey Plan","My Customers","Others","Things to Focus","Office Check-In/Out","Logout","footer"};

	public  int merchandiserMenuOptionIcon[]   = {R.drawable.journey_plan_icon,R.drawable.next_journey_plan_icon,R.drawable.mycustomer_icon,R.drawable.about_application_icon,R.drawable.thingstofocus_icon,R.drawable.office_checkin,R.drawable.logout_menu_icon,R.drawable.footer_new};
*/
    /** */ //public  String presellerMenuOption[]   = {"Journey Plan","Next Day Journey Plan", "My Customers","Execution Summary","Add New Customers","Collection Settlement","Messaging",
    //												"EOT","Others","Things to Focus","Office Check-In/Out","Logout","footer"};
    //public  int presellerMenuOptionIcon[]   = {R.drawable.journey_plan_icon,R.drawable.next_journey_plan_icon,R.drawable.mycustomer_icon,R.drawable.orders_summary_icon,R.drawable.add_necustomer_icon,
    //	/*R.drawable.eot_icon,*/R.drawable.order_summary,R.drawable.messege,R.drawable.eot_icon,R.drawable.about_application_icon,R.drawable.thingstofocus_icon,R.drawable.office_checkin,R.drawable.logout_menu_icon,R.drawable.footer_new};
    //public  String presellerCheckedInMenuOption[]   = {"Customer Dashboard", "Execution Summary",/* "Product Catalog",*/"Others","Checkout","Add New Customers","Things to Focus"};
    //public  int presellerCheckedInMenuOptionIcon[]   = {R.drawable.next_journey_plan_icon, R.drawable.arcollection_icon,/*R.drawable.product_catalog_icon,*/
    //	R.drawable.about_application_icon,R.drawable.order_summary,R.drawable.add_necustomer_icon,R.drawable.thingstofocus_icon};
    
    var vanSalesCheckedInOption: Array<String> = arrayOf(
        "Customer Dashboard", "Load Management", "Execution Summary", "Collection Settlement",
        "Checkout", "Add New Customers", "Others", "Things to Focus"
    )

    


    
    var merchandiserCheckedInOption: Array<String> = arrayOf(
        "Customer Dashboard",
        "Checkout", "Others", "Things to Focus"
    )




    /*	public  String presellerExecutionSummaryOption[]   = {"Order Summary","Payment Summary","Survey","Competitive Execution",
		"Asset Activities","Order Trace"};

	public  int presellerExecutionSummaryOptionIcon[]   = {R.drawable.order_summary,R.drawable.paymentsummary_icon,R.drawable.invontery,R.drawable.competitor_icon,
		R.drawable.assets_request_icon,R.drawable.paymentsummary_icon};*/
    /** */
    
    var loadMangementOption: Array<String> = arrayOf(
        "Van Stock",  /*,"Return Stock","Receive/Verify Stock","Today and next 3 days load","Unload","Audit Info",*/
        "Audit Return",  //			"Current Stock Report",
        "Expiry Returns",
        "Unload Stock"
    )




    //	public  int loadMangementOptionIcon[]   = {R.drawable.salable_van_stock_icon,/*R.drawable.returnstock_stock,R.drawable.stockverification_icon,R.drawable.dailyload_icon,*/R.drawable.order_summary,R.drawable.order_summary};
    
    var othersOption: Array<String> =
        arrayOf( /*"Product Catalog",*/"Settings",  /*,"Capture Competitor"*/"About Application")

    

    var executionSummaryOption: Array<String> = arrayOf(
        "Invoice Summary",
        "Payment Summary",  /*,"Competitive Execution"*/ /*,"Order Trace"*/
        "Log Report",  /*,"Score Card"*/
        "Return Summary"
    )



    
    var checkin_executionSummaryOption: Array<String> = arrayOf(
        "Invoice Summary",
        "Payment Summary",  /*,"Competitive Execution"*/ /*,"Order Trace"*/
        "Log Report",  /*,"Score Card"*/
        "Return Summary"
    )


    /*public  String checkinexecutionSummaryOption[]   = {"Order Summary","Payment Summary","Survey","Competitive Execution",
		"Asset Activities"};

	public  int checkinexecutionSummaryOptionLoogs[]   = {R.drawable.order_summary,R.drawable.paymentsummary_icon,R.drawable.invontery,R.drawable.competitor_icon,R.drawable.assets_request_icon};*/
    /** */ /*	
	public  int presellerOptionLoogsGT[] = {R.drawable.journey_plan_icon,R.drawable.load_management_icon,R.drawable.mycustomer_icon,
		R.drawable.arcollection_icon,R.drawable.invontery, R.drawable.stockverification_icon,
		R.drawable.add_necustomer_icon,R.drawable.orders_summary_icon,R.drawable.paymentsummary_icon,
		R.drawable.product_catalog_icon,R.drawable.order_summary, R.drawable.eot_icon,
		R.drawable.new_launch_icon, R.drawable.settings_icon,R.drawable.assets_request_icon,
		R.drawable.competitor_icon,R.drawable.transfer_in,R.drawable.about_application_icon,
		R.drawable.about,R.drawable.logout_menu_icon,R.drawable.footer_new};*/
    
    var OLD_COIN_BOX_SCAN_REQUEST_CODE: Int = 100000000
    
    var NEW_COIN_BOX_SCAN_REQUEST_CODE: Int = 200
    
    var VM_PHOTOGRAPH_REQUEST_CODE: Int = 300
    

    
    var RETURN_REASONS: String = "Return request reason"
    

    
    var REQUEST_CODE: Int = 0
    
    var VendingMachineName: String = ""
    

    
    var invoiceNo: Int = 10
    
    var customerSiteno: Int = 10
    
    const val ITEM_TYPE_ORDER: String = "O"
    
    const val ITEM_TYPE_REPLACE: String = "R"
    
    const val ITEM_TYPE_DEAL_ITEM: String = "Z"
    
    const val ITEM_TYPE_PROMO: String = "F"

    
    var isRecomendedChanged: Boolean = false
    
    var SUB_CHANEL: String = "Grocery_123"

    
    var isTaskCompleted: Boolean = false

    
    var ENABLE_FIELDS: Boolean = true

    
    const val SALESMAN_PRESELLER: String = "PreSales"
    
    const val PRESELLER: String = "Preseller"
    
    const val SALESMAN_VAN_SALES: String = "VanSales"
    
    const val SALESMAN_MERCHANDISER: String = "Merchandiser"
    
    const val SALESMAN_AUDITOR: String = "Auditor"
    
    const val SALESMAN_COLLECTOR: String = "Collector"

    
    const val MUST_HAVE: String = "Must Have"
    
    const val NEW_LAUNCHES: String = "New Launches"
    
    const val FAVOURRITE: String = "Favourite"

    
    const val NEW_LAUNCH: String = "NEW_LAUNCH"
    
    const val THINGS_TO_FOCUS: String = "THINGS_TO_FOCUS"

    
    const val SALES_ORDER_TYPE: Int = 1
    const val ADVANCE_ORDER_TYPE: Int = 0
    
    const val PROMO_TYPE_RANGE: String = "RP"


    
    var CURRENCY_UAE: String = "AED"
    
    var CURRENCY_SAUDI: String = "SAR"
    
    var CURRENCY_OMAN: String = "OMR"
    
    var CURRENCY_USD: String = "USD"

    
    var KPI_APPROVAL_REQD: String = "KPI_APPROVAL_REQD"

    
    var PAYMENT_TYPE_STC: String = "STC"
    
    var BARCODE: String = "BARCODE"

    
    var RETURN_PRINT_TITLE: String = "TAX CREDIT NOTE"
    
    var SALES_PRINT_TITLE: String = "Sales"

    
    var HHOrder: String = "HH order"
    
    var APPORDER: String = "App order"
    
    var ADVANCE_ORDER: String = "Advance order"
    
    var TELEORDER: String = "Tele order"
    
    var CURRENTORDER: String = "Current Order"
    
    var FREE_DELIVERY_ORDER: String = "Free Delivery"
    
    var TIME_FOR_BACKGROUND_TASK: Long = (10 * 60000).toLong()
    
    var CATEGORY: String = "4G"

    
    var CheckIN: Boolean = false
    
    var isSumeryVisited: Boolean = false
    
    var SKIPPED_CUSTOMERS: String? = null
    
    var MISSED_CUSTOMERS: String? = null
    
    var skippedCustomerSitIds: ArrayList<String>? = null
    
    var missedCustomerSitIds: ArrayList<String>? = null
    
    var strCheckIN: String? = null

    
    const val ASSET: String = "Asset"
    
    const val UNLOAD: String = "Unload"
    
    const val Movement: String = "Movement"
    
    const val NormalLoad: String = "NormalLoad"
    
    const val Verify: String = "Verify"
    
    const val Customer: String = "Customer"
    
    const val Order: String = "Order"
    
    const val DiscountCreditNote: String = "DiscountCreditNote"
    
    const val AdvanceLoadReq: String = "7DaysAdvanceLoadReq"
    
    const val GRV: String = "GRV"
    
    const val Misc: String = "Misc"
    
    const val CreditNote: String = "CreditNote"
    
    const val Draft: String = "Draft"
    
    const val Receipt: String = "Receipt"
    
    const val ONACCOUNT: String = "ONACCOUNT"
    
    const val RETURNORDER: String = "HH Return Order"
    
    const val REPLACEMETORDER: String = "Replace Order"
    
    const val LPO_ORDER: String = "LPO Order"
    
    const val MOVE_ORDER: String = "MOVE Order"
    
    const val TRNS_TYPE_IN: String = "IN"
    
    const val TRNS_TYPE_OUT: String = "OUT"
    
    var SKIP_JOURNEY_PLAN: String = "Skip Customer"
    
    var SKIP_SCRATCH_CARD: String = "Skip Scratch Card"
    
    const val asset: String = "Asset"
    
    var DEVICE_DENSITY: Float = 0f
    
    var DEVICE_WIDTH: Int = 0
    
    var DEVICE_HEIGHT: Int = 0
    
    var MULTICURRENCY_CURRENCY_SELECTION_ORDER: Boolean = true

    
    var APPFOLDERNAME: String = "AlSeer"
    
    var APPMEDIAFOLDERNAME: String = "AlSeerImages"
    
    var APPFOLDERPATH: String =
        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).absolutePath + "/" + APPFOLDERNAME + "/"
    
    var APPMEDIAFOLDERPATH: String = "$APPFOLDERPATH$APPMEDIAFOLDERNAME/"
    
    var APPFOLDER: File = File(APPFOLDERPATH)
    
    var APPMEDIAFOLDER: File = File(APPMEDIAFOLDERPATH)

    
    const val MAX_IMAGE_SIZE: Int = 700
    
    var imagePath: String = ""
    
    var assetbarcodeimagePath: Bitmap? = null
    
    var assettempimagePath: Bitmap? = null
    
    var DENISITY: Int = 0
    
    var SARBelow2000: Int = 1
    
    var SARBelow3000: Int = 2
    
    var SARBelow1000: Int = 3
    
    var SARAbove4000: Int = 4
    
    var SARZero: Int = 0

    
    var Task1: String = "Capture real photo of the shelf to validate the accuracy of the plan"
    
    var Task2: String = "Capture Competitor Promotions & Marketing initiatives"
    
    var Task3: String =
        "Consumer behaviour survey for Frozen products under brand building strategy"

    
    var Task_Title1: String = "Capture Shelf Photo"
    
    var Task_Title2: String = "Competitor Promotions"
    
    var Task_Title3: String = "In store - Consumer Behaviour Survey"

    
    const val DISCOUNT_ALL_ITEM: Int = 0
    
    const val DISCOUNT_ITEM: Int = 1
    
    const val DISCOUNT_CATEGORY: Int = 2
    
    const val DISCOUNT_BRAND: Int = 3


    
    const val DISCOUNT_PERCENTAGE: Int = 0
    
    const val DISCOUNT_AMOUNT: Int = 1
    
    const val TYPE_ORDER: Int = 1

    
    const val CUSTOMER_CHANNEL_MODERN: String = "19.MODERN TRADE"
    
    const val CUSTOMER_CHANNEL_GENERAL: String = "19.GENERAL TRADE"
    
    const val CUSTOMER_CHANNEL_HORECA: String = "19.HORECA"
    
    const val CUSTOMER_CHANNEL_PARLOUR: String = "19.DELIVERY SERVICE"


    
    const val CUSTOMER_TYPE_CREDIT: String = "CREDIT"
    
    const val CUSTOMER_TYPE_CASH: String = "CASH"

    
    const val REASON_TYPE_CREDIT: String = "CREDIT"
    
    const val REASON_TYPE_CASH: String = "CASH"

    
    const val REASON_TYPE_ASSET_CHECK_CONDITION: String = "Asset_Check_Condition"
    
    const val REASON_TYPE_ASSET_SERVICE_TYPE: String = "AssetServiceType"

    
    const val DOC_NAME_VAT: String = "VAT"
    
    const val DOC_NAME_COMP_REG: String = "COMP_REG"
    
    const val DOC_NAME_MUNICIPALITY: String = "MUNICIPALITY"
    
    const val DOC_NAME_IQAMA: String = "IQAMA"

    
    const val CUSTOMER_CREDIT_TYPE_GC: String = "General Credit"
    
    const val CUSTOMER_CREDIT_TYPE_TC: String =
        "Temporary Credit" //============Route CreditLimit Enable

    
    const val PAYMENT_NOTE_CASH: String = "CASH"
    
    const val PAYMENT_NOTE_CHEQUE: String = "CHEQUE"
    
    const val PAYMENT_NOTE_ONLINE: String = "ONLINE"
    
    const val PAYMENT_NOTE_POS: String = "POS"
    
    const val PAYMENT_NOTE_CREDIT_CARD: String = "CR"

    
    const val ROUTE_START_TYPE: String = "S"
    
    const val ROUTE_END_TYPE: String = "End"

    
    const val ROUTE_TYPE_PRESALES: String = "Pre Sales"
    
    const val ROUTE_TYPE_VANSALES: String = "Van Sales"
    
    const val SELECT: String = "Select"

    
    const val MODE_OF_DELIVERY_PRESALES: String = "Pre-Sell"
    
    const val MODE_OF_DELIVERY_VANSALES: String = "Van-Sell"

    
    const val ORDER_PAYMENT_MODE_CASH: Int = 1
    
    const val ORDER_PAYMENT_MODE_CREDIT: Int = 2

    
    const val MAXIMUM_DISATNCE_OUTLET: Int = 2000
    
    var GCMRegistrationAttempts: Int = 0
    //	public final  String SENDER_ID = "586523106653";//"715019169923";
    
    const val SENDER_ID: String = "702392795826" //"715019169923";//751276589472
    
    const val MaximumGCMRegistrationAttempts: Int = 3

    
    const val CUSTOMER_SIGN: String = "customer"
    
    const val SALESMAN_SIGN: String = "salesman"

    
    const val ACCOUNT_COPY: String = "ACCOUNT_COPY"
    
    const val COLLECTION_COPY: String = "COLLECTION_COPY"
    
    var hmSurvey: HashMap<String, String>? = null

    
    const val DOT_MATRIX: String = "DotMatrix" //DotMatrix
    
    const val THERMAL: String = "Thermal" //Thermal

    
    var currentLat: String = "N/A"
    
    var currentLng: String = "N/A"

    //PAYMENT
    
    const val NOTE_TYPE_1000: String = "1000_RUPEE_NOTE"
    
    const val NOTE_TYPE_500: String = "500_RUPEE_NOTE"
    
    const val NOTE_TYPE_200: String = "200_RUPEE_NOTE"
    
    const val NOTE_TYPE_100: String = "100_RUPEE_NOTE"

    
    const val NOTE_1000_VALUE: Int = 1000
    
    const val NOTE_500_VALUE: Int = 500
    
    const val NOTE_200_VALUE: Int = 200
    
    const val NOTE_100_VALUE: Int = 100
    
    const val CART_TYPE: Int = 100

    
    const val PRIMARY_SHELF_PLACEMENT: String = "Secondary Shelf Placement"
    
    const val POSM_AVAILABILITY: String = "Store Visibility"


    
    var iconpaths: String = ""



    //	public  String CURRECNY_CODE = " SAR";
    
    var DEVICE_DISPLAY_HEIGHT: Int = 0
    
    const val KEYBOARD_OFFSET: Int = 20


    
    const val MAJOR_APP_UPDATE: Int = 102
    
    const val NORMAL_APP_UPDATE: Int = 101
    
    const val MINOR_APP_UPDATE: Int = 100

    
    const val VER_CHANGED: Int = 100
    
    const val VER_NOT_CHANGED: Int = 101
    
    const val VER_NO_BUTTON_CLICK: Int = 102
    
    const val VER_DO_EOT: Int = 103
    
    const val VER_DO_EOT_ADEOT: Int = 104
    
    const val VER_DO_ADEOT: Int = 105
    
    const val VER_UNABLE_TO_UPGRADE: Int = 106
    
    const val CALL_FROM_LOGIN: Int = 1


    //LOAD
    
    const val LOAD_VAN: String = "VL"
    
    const val UNLOAD_VAN: String = "VU"
    
    const val TRANSFER: String = "TL"
    
    const val MOVEMENT_VAN: String = "MI"
    
    const val MOVEMENT_AUDIT: String = "EX"
    
    const val ORDER_VOID: String = "OV"
    
    const val GOLDEN_STORE: String = "PerfectStorePercentage"

    
    const val LOAD_VAN_PENDING: String = "Pending"
    
    const val LOAD_VAN_PROCESSED: String = "Approved" //Processed
    
    const val LOAD_VAN_SUPERVISOR: String = "Supervisor Approved" //Processed
    //public  final String LOAD_VAN_LOGISTICS  	= "Logistics Approved";//Processed
    
    const val LOAD_VAN_LOGISTICS: String =
        "Logistics Approved" //getString(R.string.Logistics_Approved);
    
    const val LOAD_VAN_PROCESSED_ERP: String = "Processed ERP" //getString(R.string.processed_erp);
    
    const val LOAD_VAN_COLLECTED: String = "Collected" // getString(R.string.Collected);
    
    const val LOAD_VAN_REJECTED: String = "Rejected"

    
    const val SKU_CONTROLLER1_EDITED: String = "Sku Controller1"
    
    const val Editable: String = "Editable"
    
    const val SALESMAN_EDITED: String = "Edited"
    
    const val SUPERVISOR_EDITED: String = "Supervisor"
    
    const val AM_BM_EDITED: String = "AM/BM"
    
    const val SKU_CONTROLLER2_EDITED: String = "Sku Controller2"

    
    const val GOLDEN_STORE_VAL: Int = 1
    
    var PARTIAL_PAYMENT: String = "Partial Payment"
    
    var IS_KR_NETWORK_REACHABLE: Boolean = false

    
    var CUSTOMER_IMAGE: String = "Customer Image"

    
    const val DECIMAL_FORMAT_PRICE: String = "#,##,##,###,###.###"
    
    const val DECIMAL_FORMAT_PRICE_NEW: String = "##,###,###,###"
    
    const val MINIMUM_FRACTION_DIGITS: Int = 2
    
    const val MAXIMUM_FRACTION_DIGITS: Int = 2

    
    const val REASON_NOTAVAILABLE: String = "1"
    
    const val REASON_AVAILABLE: String = "2"
    
    const val REASON_MODERATE: String = "3"
    
    const val REASON_CAPTURE: String = "4"
    
    const val REASON_NOTDONE: String = "5"


    
    const val COLLECT_PAYMENT: Int = 100
    
    const val DONE: Int = 200
    
    const val PRINT: Int = 300
    
    const val EMAIL: Int = 400
    
    const val ISSTAMPED: Int = 500

    
    var Execution_Summary: String = ""
    
    var CheckIn_Execution_Summary: String = ""
    
    var Load_Management: String = ""
    
    var Others: String = ""
}