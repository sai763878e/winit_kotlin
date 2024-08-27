package com.example.winit_kotlin.menu

import android.content.Context
import com.example.winit_kotlin.R
import com.example.winit_kotlin.common.AppConstants
import com.example.winit_kotlin.common.Preference
import com.example.winit_kotlin.dataobject.ClearTaxDA
import java.util.Vector

class MenuClass(private val context: Context) {
     var preference : Preference? = null

    enum class MENUS {
        MENU_JOURNEY_PLAN,
        MENU_NEXTDAY_JOURNEY_PLAN,
        MENU_MY_CUSTOMER,
        MENU_BLOCKED_CUSTOMER,
        MENU_LOAD_MANAGEMENT,
        MENU_EXECUTION_SUMMARY,
        MENU_ADD_NEW_CUSTOMER,
        MENU_OTHERS,
        MENU_LOGOUT,
        MENU_CUSTOMER_DASHBOARD,
        MENU_SETTINGS,
        MENU_ABOUT_APPLICATION,
        MENU_VAN_STOCK,
        MENU_PAYMENT_SUMMMARY,
        MENU_ORDER_SUMMMARY,
        MENU_CHECK_OUT,
        MENU_ACTIVE_PROMOTIONS,
        MENU_ACTIVE_SP,
        MENU_SURVEY,
        MENU_ENDORSEMENT,
        MENU_LOG_REPORT,
        MENU_RETURN_SUMMARY,
        MENU_DAMAGE_RETURNS,
        MENU_EXPIRY_RETURNS,
        MENU_UNLOAD,
        MENU_FOOTER,
        MENU_DELIVERY_DOCKET
    }
    val menuVanSeller = arrayOf(
        MENUS.MENU_JOURNEY_PLAN,
        MENUS.MENU_NEXTDAY_JOURNEY_PLAN,
        MENUS.MENU_MY_CUSTOMER,
        MENUS.MENU_BLOCKED_CUSTOMER,
        MENUS.MENU_LOAD_MANAGEMENT,
        MENUS.MENU_EXECUTION_SUMMARY,
        MENUS.MENU_ADD_NEW_CUSTOMER,
        MENUS.MENU_ENDORSEMENT,
        MENUS.MENU_OTHERS,
        MENUS.MENU_LOGOUT
//        MENUS.MENU_FOOTER
    )

    val menuVanSellerAfterCheckIn = arrayOf(
        MENUS.MENU_CUSTOMER_DASHBOARD,
        MENUS.MENU_EXECUTION_SUMMARY,
        MENUS.MENU_ACTIVE_PROMOTIONS,
        MENUS.MENU_ACTIVE_SP,
        MENUS.MENU_OTHERS,
        MENUS.MENU_CHECK_OUT
//        MENUS.MENU_FOOTER
    )

    val menuPreSeller = arrayOf(
        MENUS.MENU_JOURNEY_PLAN,
        MENUS.MENU_NEXTDAY_JOURNEY_PLAN,
        MENUS.MENU_MY_CUSTOMER,
        MENUS.MENU_BLOCKED_CUSTOMER,
        MENUS.MENU_EXECUTION_SUMMARY,
        MENUS.MENU_ADD_NEW_CUSTOMER,
        MENUS.MENU_ENDORSEMENT,
        MENUS.MENU_OTHERS,
        MENUS.MENU_LOGOUT
//        MENUS.MENU_FOOTER
    )

    val menuPreSellerAfterCheckIn = arrayOf(
        MENUS.MENU_CUSTOMER_DASHBOARD,
        MENUS.MENU_EXECUTION_SUMMARY,
        MENUS.MENU_ACTIVE_PROMOTIONS,
        MENUS.MENU_ACTIVE_SP,
        MENUS.MENU_OTHERS,
        MENUS.MENU_CHECK_OUT
//        MENUS.MENU_FOOTER
    )

    val loadManagement = arrayOf(
        MENUS.MENU_VAN_STOCK,
        MENUS.MENU_DAMAGE_RETURNS,
        MENUS.MENU_EXPIRY_RETURNS,
        MENUS.MENU_UNLOAD
    )

    // main trace 1
    val executionSummary = arrayOf(
        MENUS.MENU_ORDER_SUMMMARY,
        MENUS.MENU_PAYMENT_SUMMMARY,
        MENUS.MENU_LOG_REPORT,
        MENUS.MENU_RETURN_SUMMARY
    )

    val executionSummary_1 = arrayOf(
        MENUS.MENU_ORDER_SUMMMARY,
        MENUS.MENU_DELIVERY_DOCKET,
        MENUS.MENU_PAYMENT_SUMMMARY,
        MENUS.MENU_LOG_REPORT,
        MENUS.MENU_RETURN_SUMMARY
    )

    val others = arrayOf(
        MENUS.MENU_SETTINGS,
        MENUS.MENU_ABOUT_APPLICATION
    )


    fun loadMenu(userType: Int, isCheckIn: Boolean): Vector<MenuDO> {
        val vecMenuDOs = Vector<MenuDO>()
        when (userType) {
            AppConstants.USER_VAN_SALES -> {
                val menus = if (isCheckIn) menuVanSellerAfterCheckIn else menuVanSeller
                for (objMenu in menus) {
                    var menuDO = getMenuDO(objMenu)
                    menuDO.objMenu = objMenu
                    menuDO = getMenuChildDO(menuDO)
                    vecMenuDOs.add(menuDO)
                }
            }
            AppConstants.USER_PRESELER -> {
                val menus = if (isCheckIn) menuPreSellerAfterCheckIn else menuPreSeller
                for (objMenu in menus) {
                    var menuDO = getMenuDO(objMenu)
                    menuDO.objMenu = objMenu
                    menuDO = getMenuChildDO(menuDO)
                    vecMenuDOs.add(menuDO)
                }
            }
        }
        return vecMenuDOs
    }
    fun getMenuDO(field: MENUS): MenuDO {
        var menu  = MenuDO()
        when (field) {
            MENUS.MENU_JOURNEY_PLAN -> {
                menu.menuName = context.getString(R.string.Journey_Plan)
                menu.menuImage = R.drawable.baseline_calendar_today_24
            }
            MENUS.MENU_NEXTDAY_JOURNEY_PLAN -> {
                menu.menuName = context.getString(R.string.Next_Day_Journey_Plan)
                menu.menuImage = R.drawable.baseline_calendar_month_24
            }
            MENUS.MENU_MY_CUSTOMER -> {
                menu.menuName = context.getString(R.string.My_Customers1)
                menu.menuImage = R.drawable.baseline_person_24
            }
            MENUS.MENU_BLOCKED_CUSTOMER -> {
                menu.menuName = context.getString(R.string.Blocked_Customers)
                menu.menuImage = R.drawable.baseline_person_off_24
            }
            MENUS.MENU_LOAD_MANAGEMENT -> {
                menu.menuName = context.getString(R.string.Daily_Load)
                menu.menuImage = R.drawable.baseline_local_shipping_24
            }
            MENUS.MENU_EXECUTION_SUMMARY -> {
                menu.menuName = context.getString(R.string.Execution_Summary)
                menu.menuImage = R.drawable.baseline_note_alt_24
            }
            MENUS.MENU_ADD_NEW_CUSTOMER -> {
                menu.menuName = context.getString(R.string.Add_Prospectus)
                menu.menuImage = R.drawable.baseline_person_add_24
            }
            MENUS.MENU_OTHERS -> {
                menu.menuName = context.getString(R.string.Others)
                menu.menuImage = R.drawable.baseline_read_more_24
            }
            MENUS.MENU_LOGOUT -> {
                menu.menuName = context.getString(R.string.Logout)
                menu.menuImage = R.drawable.baseline_logout_24
            }
            MENUS.MENU_FOOTER -> {
                menu.menuName = context.getString(R.string.Footer)
                menu.menuImage = R.drawable.footer_new
            }
            MENUS.MENU_CUSTOMER_DASHBOARD -> {
                menu.menuName = context.getString(R.string.Customer_Dashboard)
                menu.menuImage = R.drawable.baseline_dashboard_24
            }
            MENUS.MENU_ACTIVE_PROMOTIONS -> {
                menu.menuName = context.getString(R.string.active_promotions)
                menu.menuImage = R.drawable.baseline_campaign_24
            }
            MENUS.MENU_ACTIVE_SP -> {
                menu.menuName = context.getString(R.string.Active_SP)
                menu.menuImage = R.drawable.baseline_campaign_24
            }
            MENUS.MENU_CHECK_OUT -> {
                menu.menuName = context.getString(R.string.check_out)
                menu.menuImage = R.drawable.baseline_logout_24
            }
            MENUS.MENU_SURVEY -> {
                menu.menuName = context.getString(R.string.survey)
                menu.menuImage = R.drawable.baseline_note_alt_24
            }
            MENUS.MENU_ENDORSEMENT -> {
                menu.menuName = context.getString(R.string.EOT)
                menu.menuImage = R.drawable.baseline_logout_24
            }
            MENUS.MENU_SETTINGS -> {
                menu.menuName = context.getString(R.string.Sync_Data)
                menu.menuImage = R.drawable.baseline_settings_24
            }
            MENUS.MENU_ABOUT_APPLICATION -> {
                menu.menuName = context.getString(R.string.About_Application)
                menu.menuImage = R.drawable.baseline_info_outline_24
            }
            MENUS.MENU_ORDER_SUMMMARY -> {
                menu.menuName = context.getString(R.string.Order_Summary2)
                menu.menuImage = R.drawable.baseline_article_24
            }
            MENUS.MENU_DELIVERY_DOCKET -> {
                menu.menuName = context.getString(R.string.Delivery_Docket)
                menu.menuImage = R.drawable.baseline_article_24
            }
            MENUS.MENU_PAYMENT_SUMMMARY -> {
                menu.menuName = context.getString(R.string.Payment_Summary)
                menu.menuImage = R.drawable.baseline_payments_24
            }
            MENUS.MENU_LOG_REPORT -> {
                menu.menuName = context.getString(R.string.Log_Report)
                menu.menuImage = R.drawable.baseline_article_24
            }
            MENUS.MENU_RETURN_SUMMARY -> {
                menu.menuName = context.getString(R.string.Return_Summary)
                menu.menuImage = R.drawable.baseline_assignment_returned_24
            }
            MENUS.MENU_VAN_STOCK -> {
                menu.menuName = context.getString(R.string.Van_Stock)
                menu.menuImage = R.drawable.baseline_fire_truck_24
            }
            MENUS.MENU_DAMAGE_RETURNS -> {
                menu.menuName = context.getString(R.string.Damage_Returns)
                menu.menuImage = R.drawable.baseline_assignment_returned_24
            }
            MENUS.MENU_EXPIRY_RETURNS -> {
                menu.menuName = context.getString(R.string.Expiry_Returns)
                menu.menuImage = R.drawable.baseline_assignment_returned_24
            }
            MENUS.MENU_UNLOAD -> {
                menu.menuName = context.getString(R.string.Unload_Stock)
                menu.menuImage = R.drawable.baseline_local_shipping_24
            }
        }
        return menu
    }

    private fun getMenuChildDO(menu: MenuDO): MenuDO {
        preference = Preference(context)
        when (menu.objMenu) {
            MENUS.MENU_OTHERS -> {
                for (objMenu in others) {
                    val menuDO = getMenuDO(objMenu)
                    menuDO.objMenu = objMenu
                    menu.vecMenuDOs.add(menuDO)
                }
            }
            MENUS.MENU_EXECUTION_SUMMARY -> {
                val executionSummaryMenus = if (ClearTaxDA().isClearTaxEnabledRoute(preference!!.getStringFromPreference(Preference.ROUTE_CODE, ""))) {
                    executionSummary_1
                } else {
                    executionSummary
                }
                for (objMenu in executionSummaryMenus) {
                    val menuDO = getMenuDO(objMenu)
                    menuDO.objMenu = objMenu
                    menu.vecMenuDOs.add(menuDO)
                }
            }
            MENUS.MENU_LOAD_MANAGEMENT -> {
                for (objMenu in loadManagement) {
                    val menuDO = getMenuDO(objMenu)
                    menuDO.objMenu = objMenu
                    menu.vecMenuDOs.add(menuDO)
                }
            }

            MENUS.MENU_JOURNEY_PLAN -> menu
            MENUS.MENU_NEXTDAY_JOURNEY_PLAN -> menu
            MENUS.MENU_MY_CUSTOMER -> menu
            MENUS.MENU_BLOCKED_CUSTOMER -> menu
            MENUS.MENU_ADD_NEW_CUSTOMER -> menu
            MENUS.MENU_LOGOUT -> menu
            MENUS.MENU_CUSTOMER_DASHBOARD -> menu
            MENUS.MENU_SETTINGS -> menu
            MENUS.MENU_ABOUT_APPLICATION -> menu
            MENUS.MENU_VAN_STOCK -> menu
            MENUS.MENU_PAYMENT_SUMMMARY -> menu
            MENUS.MENU_ORDER_SUMMMARY -> menu
            MENUS.MENU_CHECK_OUT -> menu
            MENUS.MENU_ACTIVE_PROMOTIONS -> menu
            MENUS.MENU_ACTIVE_SP -> menu
            MENUS.MENU_SURVEY -> menu
            MENUS.MENU_ENDORSEMENT -> menu
            MENUS.MENU_LOG_REPORT -> menu
            MENUS.MENU_RETURN_SUMMARY -> menu
            MENUS.MENU_DAMAGE_RETURNS -> menu
            MENUS.MENU_EXPIRY_RETURNS -> menu
            MENUS.MENU_UNLOAD -> menu
            MENUS.MENU_FOOTER -> menu
            MENUS.MENU_DELIVERY_DOCKET -> menu
            null -> menu
        }
        return menu
    }
}