package com.example.winit_kotlin.dataobject

object SettingsDO {

    var SettingId: Int = 0
    var SettingName: String = ""
    var SettingValue: String = ""
    var DataType: String = ""
    var SalesOrgCode: String = ""
    var CountryId: Int = 0

    fun getIs_TAX_Percent_In_Print_Required(): String {
        return Is_TAX_Percent_In_Print_Required
    }
    private 
    const val Is_TAX_Percent_In_Print_Required: String = "Is_TAX_Percent_In_Print_Required"
    private 
    const val IS_PROMO_COND_ENABLED: String = "IS_PROMO_COND_ENABLED"
    private 
    const val GRACE_PERIOD: String = "Grace_Period"
    private 
    const val IsSignatureCapturingRequired: String = "IsSignatureCapturingRequired"
    private 
    const val MAX_INVOICE_COUNT: String = "MAX_INVOICE_COUNT"
    private 
    const val IS_CLEAR_DATA_ALLOWED: String = "IS_CLEAR_DATA_ALLOWED"
    private 
    const val GEOFENCE_RADIUS: String = "GEOFENCE_RADIUS"
    private 
    const val AUTO_CHECKOUT_RADIUS: String = "AUTO_CHECKOUT_RADIUS"
    private 
    const val DEPOT_CHECKIN_RADIUS: String = "DEPOT_CHECKIN_RADIUS"
    private 
    const val Is_FIFO_Allocation_Mandatory_AR_Collection: String =
        "Is_FIFO_Allocation_Mandatory_AR_Collection"
    private 
    const val MAX_LOAD: String = "MAX_LOAD"
    private 
    const val GEOFENCE_ALLOWED: String = "GEOFENCE_ALLOWED"
    private 
    const val PALLETS_TO_UNLOAD_ITEMS: String = "PALLETS_TO_UNLOAD_ITEMS"
    private 
    const val USERS_TO_SKIP_PRINT: String = "USERS_TO_SKIP_PRINT"
    private 
    const val New_Customer_PricelistCode: String = "New Customer PricelistCode"
    private 
    const val Is_Prospect_Registered_customer_VAT_Not_Mandate: String =
        "Is_Prospect_Registered_customer_VAT_Not_Mandate"
    private 
    const val New_Customer_MIN_INV_AMT: String = "New Customer Minimum Invoice Amt"
    private 
    const val New_Customer_MAX_INV_AMT: String = "New Customer Maximum Invoice Amt"
    private 
    const val New_Customer_MAX_NO_INV_COUNT: String = "New Customer Maximum Invoice Count"

    //added by sai ====== start ===== 050523 ====
    private 
    const val ENABLE_EINVOICE: String = "ENABLE_EINVOICE"

    fun get_ENABLe_EINVOICE(): String {
        return ENABLE_EINVOICE
    }


    //=========================== end ====================================
    fun getDual_Currency_Popup_Enable(): String {
        return Dual_Currency_Popup_Enable
    }

    private 
    const val Dual_Currency_Popup_Enable: String = "Dual_Currency_Popup_Enable"

    fun getCurrency_Conversion_FromValidation(): String {
        return Currency_Conversion_FromValidation
    }

    private 
    const val Currency_Conversion_FromValidation: String = "Currency_Conversion_FromValidation"

    fun getCurrency_Conversion_ToValidation(): String {
        return Currency_Conversion_ToValidation
    }

    private 
    const val Currency_Conversion_ToValidation: String = "Currency_Conversion_ToValidation"

    fun getPerfect_Store_Eligible_Percentage(): String {
        return Perfect_Store_Eligible_Percentage
    }

    private 
    const val Perfect_Store_Eligible_Percentage: String = "Perfect_Store_Eligible_Percentage"


    private 
    const val App_SupervisorCode: String = "App_SupervisorCode"
    private 
    const val App_SupervisorName: String = "App_SupervisorName"
    private 
    const val App_SupervisorEmail: String = "App_SupervisorEmail"
    private 
    const val App_SupervisorMobile: String = "App_SupervisorMobile"

    private 
    const val App_FinanceManagerCode: String = "App_FinanceManagerCode"
    private 
    const val App_FinanceManagerName: String = "App_FinanceManagerName"
    private 
    const val App_FinanceManagerEmail: String = "App_FinanceManagerEmail"
    private 
    const val App_FinanceManagerMobile: String = "App_FinanceManagerMobile"


    fun getPromoSettingsValue(): String {
        return IS_PROMO_COND_ENABLED
    }

    fun getClearDataSettingsValue(): String {
        return IS_CLEAR_DATA_ALLOWED
    }

    fun getGeofenceRadiusSettingsValue(): String {
        return GEOFENCE_RADIUS
    }

    fun getAutoCheckOutRadiusSettingsValue(): String {
        return AUTO_CHECKOUT_RADIUS
    }

    fun getDepotCheckinRadiusSettingsValue(): String {
        return DEPOT_CHECKIN_RADIUS
    }

    fun getFIFO_Allocation_Mandatory_AR_CollectionSettingsValue(): String {
        return Is_FIFO_Allocation_Mandatory_AR_Collection
    }

    fun getMAXLoadSettingsValue(): String {
        return MAX_LOAD
    }

    fun get_NO_PRICE_ITEMS(): String {
        return NO_PRICE_ITEMS
    }

    fun getMaxInvoiceSettingsValue(): String {
        return MAX_INVOICE_COUNT
    }

    fun getGracePeriodSettingsValue(): String {
        return GRACE_PERIOD
    }

    fun getIsSignatureCapturingRequiredSettingsValue(): String {
        return IsSignatureCapturingRequired
    }

    fun getGeofenceAllowedSettingsValue(): String {
        return GEOFENCE_ALLOWED
    }

    fun getPalletsToUnloadItemsSettingsValue(): String {
        return PALLETS_TO_UNLOAD_ITEMS
    }

    fun getUsersToSkipPrintSettingsValue(): String {
        return USERS_TO_SKIP_PRINT
    }

    private 
    const val FOC__ITEM_PRICE_SETTINGS_NAME: String = "FocItemPrice"
    private 
    const val BLOCK_CREDIT_SALES_DAYS_NAME: String = "BlockCreditSalesDays"
    private 
    const val BLOCK_SALES_DAYS_NAME: String = "BlockSalesDays"
    private 
    const val ORDER_PUSH_DATE: String = "ORDER_PUSH_DATE"
    private 
    const val ORDER_T_PUSH_DATE: String = "ORDER_T_PUSH_DATE"
    private 
    const val ALLOW_EDIT_ITEMS: String = "AllowEditItems"
    private 
    const val ALLOW_CASH_FOR_CREDIT_CUSTOMERS: String = "AllowCashForCreditCustomers"
    private 
    const val Is_Overdue_Mandatory: String = "Is_Overdue_Mandatory"
    private 
    const val overdue_for_credit_customer: String = "overdue_for_credit_customer"
    private 
    const val Is_Registered_Customer_Editable: String = "Is_Registered_Customer_Editable"
    private 
    const val Audit_Payment_Generation: String = "Audit_Payment_Generation"
    private 
    const val ROUTE_CREDIT_LIMIT_SKIP: String = "RouteCreditLimitSkip"
    private 
    const val STC_Blocking_Recommendedorder_enabled: String =
        "STC_Blocking_Recommendedorder_enabled"
    private 
    const val Recommended_Order_Main_Settings: String = "Recommended_Order_Main_Settings"
    private 
    const val ITEM_ORDER_QUANTITY_MAX_LIMIT: String = "ITEM_ORDER_QUANTITY_MAX_LIMIT"
    private 
    const val ITEM_LOAD_QUANTITY_MAX_LIMIT: String = "ITEM_LOAD_QUANTITY_MAX_LIMIT"
    private 
    const val Is_Audit_Mandatory: String = "Is_Audit_Mandatory"
    private 
    const val BILL_TO_BILL_MAX_LIMIT_SETTINGS_NAME: String = "B2BMaxLimit"
    private 
    const val NO_PRICE_ITEMS: String = "NO_PRICE_ITEMS"
    private 
    const val WRAPPER_FREE_ITEM: String = "WrapperFreeItem"
    private 
    const val WRAPPER_PROMO_ID: String = "WrapperPromoId"
    private 
    const val MISSING_ITEM_SETTINGS_NAME: String = "MissingItems"
    private 
    const val MISSING_ITEM_7DAYS_MSL_MANADATORY: String = "IS_7DAYS_MSL_MANADATORY"
    private 
    const val NEW_PRICE_LOGIC_SETTINGS_NAME: String = "NewPriceLogicApplicable"
    private 
    const val IS_CREDIT_INV_PRINT_MANDATORY: String = "IS_CREDIT_INV_PRINT_MANDATORY"
    private 
    const val IS_CASH_INV_PRINT_MANDATORY: String = "IS_CASH_INV_PRINT_MANDATORY"
    private 
    const val IS_ALLOCATE_ENABLED: String = "IS_ALLOCATE_ENABLED"
    private 
    const val IS_LOCAL_CURRENCY_DISABLED: String = "IS_LOCAL_CURRENCY_DISABLED"
    private 
    const val IS_ONACCOUNT_EARLY_PAYMENT_TO_CONSIDER: String =
        "IS_ONACCOUNT_EARLY_PAYMENT_TO_CONSIDER"
    private 
    const val Ignore_Roundoff: String = "Ignore_Roundoff"
    private 
    const val Ignore_EarlyPaymentRoundoffIssue: String = "Ignore_EarlyPaymentRoundoffIssue"
    private 
    const val Onaccountdisable: String = "Onaccountdisable"
    private 
    const val NEGATIVE_ALLOW_STC: String = "NEGATIVE_ALLOW_STC"
    private 
    const val QRCODE_ENABLED: String = "QRCODE_ENABLED"
    private 
    const val NEW_REPORTS_SETTINGS_NAME: String = "NewReportsApplicable"
    private 
    const val EOT_SETTINGS_NAME: String = "IsEotEnabled"
    private 
    const val LEFT_OVER_STOCK_TO_SKIP: String = "LeftOverStockToSkip"
    private 
    const val OVERDUE_SETTINGS_NAME: String = "isOverDueToConsider"
    private 
    const val ShelfLifeGracePeriod: String = "ShelfLifeGracePeriod"

    fun getPCS_UOM_Arabic(): String {
        return PCS_UOM_Arabic
    }

    fun getCASE_UOM_Arabic(): String {
        return CASE_UOM_Arabic
    }

    private 
    const val PCS_UOM_Arabic: String = "PCS_UOM_Arabic"
    private 
    const val CASE_UOM_Arabic: String = "CASE_UOM_Arabic"
    private 
    const val NO_APPROVAL_REQUIRED_RETURN: String = "NO_APPROVAL_REQUIRED_RETURN"
    private 
    const val SCANNER_SETTINGS_ENABLED_SETTINGS_NAME: String = "IsScannerEnabled"
    private 
    const val IS_VIDEO_RECORDING_ENABLED_SETTINGS_NAME: String = "IsVideoRecordingEnabled"
    private 
    const val CREDIT_NOTE_VALID_DAYS: String = "Credit Note Time Limit"
    private 
    const val CREDIT_LIMIT_APPROVAL_MAX_AMOUNT: String = "Credit Limit Approval Max Amount"
    private 
    const val NON_MOBILE_HANDSET_CATEGORY: String = "NonMobileHandsetCategory"
    private 
    const val MORE_PAYMENT_ALLOWED: String = "MorePaymentAllowed"
    private 
    const val SUB_BRAND_GROUP_REQUIRED: String = "SUB_BRAND_GROUP_REQUIRED"
    private 
    const val CONSIDER_EOT: String = "CONSIDER_EOT"
    private 
    const val MAX_SLAB_DISCOUNT: String = "MAX_SLAB_DISCOUNT"
    private 
    const val VIDEO_TIME: String = "VIDEO_TIME"
    private 
    const val FOC_NEW_PRINT_SALES_ORG: String = "Foc_New_Sales_Org"
    private 
    const val SALES_ORG_PRINT: String = "SALES_ORG_PRINT"
    private 
    const val IS_PRINT_MANDATORY_TO_POST: String = "IS_PRINT_MANDATORY_TO_POST"
    private 
    const val TRIP_DAYS_ALLOWED: String = "TRIP_DAYS_ALLOWED"
    private 
    const val TAX_CODE: String = "TAX_CODE"
    private 
    const val COMPANY_NAME: String = "COMPANY_NAME"
    private 
    const val SECONDS_FOR_DUPLICATE_ALLOWED: String = "SECONDS_FOR_DUPLICATE_ALLOWED"
    private 
    const val WRAPPER_PROMO_ITEMS: String = "WRAPPER_PROMO_ITEMS"
    private 
    const val INVOICE_TAX_PERCENTAGE: String = "INVOICE_TAX_PERCENTAGE"
    private 
    const val IS_PASSCODE_ENABLE: String = "IS_PASSCODE_ENABLE"
    //	private  final String IS_FC_PASSCODE_ENABLE = "IS_FC_PASSCODE_ENABLE";
    private 
    const val IS_FC_PASSCODE_ENABLE: String = "IS_FC_PASSCODE_ENABLE_NEW"
    private 
    const val IS_VOID_INVOICE_PASSCODE_ENABLE_NEW: String = "IS_VOID_INVOICE_PASSCODE_ENABLE_NEW"

    fun getIsVoidButtonEnable(): String {
        return IS_VOID_BUTTON_ENABLE
    }

    private 
    const val IS_VOID_BUTTON_ENABLE: String = "Is_Void_Button_Enabled"
    fun get_VIDEO_TIME_SETTINGS_NAME(): String {
        return VIDEO_TIME
    }

    fun get_FOC_NEW_PRINT_SALES_ORG(): String {
        return FOC_NEW_PRINT_SALES_ORG
    }

    fun get_SALES_ORG_PRINT(): String {
        return SALES_ORG_PRINT
    }

    fun get_WRAPPER_PROMO_ITEMS(): String {
        return WRAPPER_PROMO_ITEMS
    }

    fun get_INVOICE_TAX_PERCENTAGE(): String {
        return INVOICE_TAX_PERCENTAGE
    }

    fun get_IS_PRINT_MANDATORY_TO_POST(): String {
        return IS_PRINT_MANDATORY_TO_POST
    }

    fun get_TRIP_DAYS_ALLOWED(): String {
        return TRIP_DAYS_ALLOWED
    }

    fun get_TAX_CODE(): String {
        return TAX_CODE
    }

    fun get_COMPANY_NAME(): String {
        return COMPANY_NAME
    }

    fun get_SECONDS_FOR_DUPLICATE_ALLOWED(): String {
        return SECONDS_FOR_DUPLICATE_ALLOWED
    }

    fun get_MAX_SLAB_DISCOUNT_SETTINGS_NAME(): String {
        return MAX_SLAB_DISCOUNT
    }

    fun get_NON_MOBILE_HANDSET_CATEGORY_SETTINGS_NAME(): String {
        return NON_MOBILE_HANDSET_CATEGORY
    }

    fun get_FOC_ITEM_PRIE_SETTINGS_NAME(): String {
        return FOC__ITEM_PRICE_SETTINGS_NAME
    }

    fun get_New_Customer_PricelistCode(): String {
        return New_Customer_PricelistCode
    }

    fun get_Is_Prospect_Registered_customer_VAT_Not_Mandate(): String {
        return Is_Prospect_Registered_customer_VAT_Not_Mandate
    }

    fun get_New_Customer_MIN_INV_AMT(): String {
        return New_Customer_MIN_INV_AMT
    }

    fun get_New_Customer_MAX_INV_AMT(): String {
        return New_Customer_MAX_INV_AMT
    }

    fun get_New_Customer_MAX_INV_COUNT(): String {
        return New_Customer_MAX_NO_INV_COUNT
    }

    fun get_CONSIDER_EOT_SETTINGS_NAME(): String {
        return CONSIDER_EOT
    }

    fun get_CONSIDER_OVERDUE_SETTINGS_NAME(): String {
        return OVERDUE_SETTINGS_NAME
    }

    fun get_ShelfLifeGracePeriod(): String {
        return ShelfLifeGracePeriod
    }

    fun get_ReturnOrderApprovalRequired(): String {
        return NO_APPROVAL_REQUIRED_RETURN
    }

    fun get_BLOCK_CREDIT_SALES_DAYS_SETTINGS_NAME(): String {
        return BLOCK_CREDIT_SALES_DAYS_NAME
    }

    fun get_BILL_TO_BILL_MAX_LIMIT_SETTINGS_NAME(): String {
        return BILL_TO_BILL_MAX_LIMIT_SETTINGS_NAME
    }

    fun get_WRAPPER_FREE_ITEM(): String {
        return WRAPPER_FREE_ITEM
    }

    fun get_WRAPPER_PROMO_ID(): String {
        return WRAPPER_PROMO_ID
    }

    fun get_MISSING_ITEMS_FUNCTIONALITY_NAME(): String {
        return MISSING_ITEM_SETTINGS_NAME
    }

    fun get_IS_7DaysAdvanceLoad_MSL_Capture_Mandatory(): String {
        return MISSING_ITEM_7DAYS_MSL_MANADATORY
    }

    fun get_NEW_PRICE_LOGIC_SETTINGS_NAME(): String {
        return NEW_PRICE_LOGIC_SETTINGS_NAME
    }

    fun get_IS_CREDIT_INV_PRINT_MANDATORY(): String {
        return IS_CREDIT_INV_PRINT_MANDATORY
    }

    fun get_IS_CASH_INV_PRINT_MANDATORY(): String {
        return IS_CASH_INV_PRINT_MANDATORY
    }

    fun get_Ignore_Roundoff(): String {
        return Ignore_Roundoff
    }

    fun get_Ignore_EarlyPaymentRoundoffIssue(): String {
        return Ignore_EarlyPaymentRoundoffIssue
    }

    fun get_IS_ALLOCATE_ENABLED(): String {
        return IS_ALLOCATE_ENABLED
    }

    fun get_IS_LOCAL_CURRENCY_DISABLED(): String {
        return IS_LOCAL_CURRENCY_DISABLED
    }

    fun getOnaccountdisable(): String {
        return Onaccountdisable
    }

    fun get_NEGATIVE_ALLOW_STC(): String {
        return NEGATIVE_ALLOW_STC
    }

    fun get_QRCODE_ENABLED(): String {
        return QRCODE_ENABLED
    }

    fun get_IS_ONACCOUNT_EARLY_PAYMENT_TO_CONSIDER(): String {
        return IS_ONACCOUNT_EARLY_PAYMENT_TO_CONSIDER
    }

    fun get_NEW_REPORTS_SETTINGS_NAME(): String {
        return NEW_REPORTS_SETTINGS_NAME
    }

    fun get_EOT_SETTINGS_NAME(): String {
        return EOT_SETTINGS_NAME
    }

    fun get_LEFT_OVER_STOCK_TO_SKIP_NAME(): String {
        return LEFT_OVER_STOCK_TO_SKIP
    }

    fun get_SCANNER_SEETINGS_ENABLED_SETTINGS_NAME(): String {
        return SCANNER_SETTINGS_ENABLED_SETTINGS_NAME
    }

    fun get_IS_VIDEO_RECORDING_ENABLED_SETTINGS_NAME(): String {
        return IS_VIDEO_RECORDING_ENABLED_SETTINGS_NAME
    }

    fun get_MORE_PAYMENT_ALLOWED(): String {
        return MORE_PAYMENT_ALLOWED
    }

    fun get_SUB_BRAND_GROUP_REQUIRED(): String {
        return SUB_BRAND_GROUP_REQUIRED
    }

    fun get_CREDIT_NOTE_VALID_DAYS(): String {
        return CREDIT_NOTE_VALID_DAYS
    }

    fun get_CREDIT_LIMIT_APPROVAL_MAX_AMOUNT(): String {
        return CREDIT_LIMIT_APPROVAL_MAX_AMOUNT
    }

    fun get_BLOCK_SALES_DAYS_SETTINGS_NAME(): String {
        return BLOCK_SALES_DAYS_NAME
    }

    fun get_ORDER_PUSH_DATE(): String {
        return ORDER_PUSH_DATE
    }

    fun get_ORDER_T_PUSH_DATE(): String {
        return ORDER_T_PUSH_DATE
    }

    fun get_Allow_Edit_Items(): String {
        return ALLOW_EDIT_ITEMS
    }

    fun get_ALLOW_CASH_FOR_CREDIT_CUSTOMERS(): String {
        return ALLOW_CASH_FOR_CREDIT_CUSTOMERS
    }

    fun get_Is_Overdue_Mandatory(): String {
        return Is_Overdue_Mandatory
    }

    fun get_overdue_for_credit_customer(): String {
        return overdue_for_credit_customer
    }

    fun get_Is_Registered_Customer_Editable(): String {
        return Is_Registered_Customer_Editable
    }

    fun get_Audit_Payment_Generation(): String {
        return Audit_Payment_Generation
    }

    fun get_ROUTE_CREDIT_LIMIT_SKIP(): String {
        return ROUTE_CREDIT_LIMIT_SKIP
    }

    fun get_STC_Blocking_Recommendedorder_enabled(): String {
        return STC_Blocking_Recommendedorder_enabled
    }

    fun get_Recommended_Order_Main_Settings(): String {
        return Recommended_Order_Main_Settings
    }

    fun get_ITEM_ORDER_QUANTITY_MAX_LIMIT(): String {
        return ITEM_ORDER_QUANTITY_MAX_LIMIT
    }

    fun get_ITEM_LOAD_QUANTITY_MAX_LIMIT(): String {
        return ITEM_LOAD_QUANTITY_MAX_LIMIT
    }

    fun get_Is_Audit_Mandatory(): String {
        return Is_Audit_Mandatory
    }

    fun getPasscodeSettingsValue(): String {
        return IS_PASSCODE_ENABLE
    }

    fun getFCPasscodeSettingsValue(): String {
        return IS_FC_PASSCODE_ENABLE
    }

    fun getVoidInvoicePasscodeSettingsValue(): String {
        return IS_VOID_INVOICE_PASSCODE_ENABLE_NEW
    }


    // van to van
    private 
    const val SAVED_STATUS_SETTINGS_NAME: String = "SAVED_STATUS_SETTINGS_NAME"
    private 
    const val UNLOAD_STATUS_SETTINGS_NAME: String = "UNLOAD_STATUS_SETTINGS_NAME"
    private 
    const val PASSCODE_ENABLED_SETTINGS_NAME: String = "IsPasscodeEnabled"

    private 
    const val ALLOW_WITHOUT_EOT: String = "ALLOW_WITHOUT_EOT"
    fun get_ALLOW_WITHOUT_EOT(): String {
        return ALLOW_WITHOUT_EOT
    }

    //van to van
    fun get_UNLOAD_STATUS_SETTINGS_NAME(): String {
        return UNLOAD_STATUS_SETTINGS_NAME
    }

    fun get_SAVED_STATUS_SETTINGS_NAME(): String {
        return SAVED_STATUS_SETTINGS_NAME
    }

    fun get_PASSCODE_ENABLED_SETTINGS_NAME(): String {
        return PASSCODE_ENABLED_SETTINGS_NAME
    }
}