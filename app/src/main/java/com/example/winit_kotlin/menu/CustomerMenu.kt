package com.example.winit_kotlin.menu

import android.content.Context
import com.example.winit_kotlin.R
import com.example.winit_kotlin.common.AppConstants
import java.util.Vector

class CustomerMenu(private val context: Context) {

    enum class ACTIVITIES {
        ACTIVITY_ASSET_CHECK,
        ACTIVITY_STORE_CHECK,
        ACTIVITY_SALES_ORDER,
        ACTIVITY_PRESALES_ORDER,
        ACTIVITY_PLANOGRAM,
        ACTIVITY_RETURNS,
        ACTIVITY_COLLECTIONS,
        ACTIVITY_COMPETITOR,
        ACTIVITY_CHECKOUT
    }
    private val kpiDashboardDoListVansales = arrayOf(
        ACTIVITIES.ACTIVITY_ASSET_CHECK,
        ACTIVITIES.ACTIVITY_STORE_CHECK,
        ACTIVITIES.ACTIVITY_SALES_ORDER,
        ACTIVITIES.ACTIVITY_PLANOGRAM,
        ACTIVITIES.ACTIVITY_RETURNS,
        ACTIVITIES.ACTIVITY_COLLECTIONS,
        ACTIVITIES.ACTIVITY_COMPETITOR,
//        ACTIVITIES.ACTIVITY_CHECKOUT,
    )
    private val kpiDashboardDoListPresales = arrayOf(
        ACTIVITIES.ACTIVITY_ASSET_CHECK,
        ACTIVITIES.ACTIVITY_STORE_CHECK,
        ACTIVITIES.ACTIVITY_PRESALES_ORDER,
        ACTIVITIES.ACTIVITY_PLANOGRAM,
        ACTIVITIES.ACTIVITY_RETURNS,
        ACTIVITIES.ACTIVITY_COLLECTIONS,
        ACTIVITIES.ACTIVITY_COMPETITOR,
//        ACTIVITIES.ACTIVITY_CHECKOUT,
    )
    fun loadDashboard(usertype : Int) : Vector<KpiDashboardDo>{
        val vector = Vector<KpiDashboardDo>()
        when(usertype){
            AppConstants.USER_VAN_SALES ->{
                var dashboardDos = kpiDashboardDoListVansales
                for (obj in dashboardDos){
                    val kpiDashboardDo = getCustomerMenu(obj)
                    kpiDashboardDo.objKPI = obj
                    vector.add(kpiDashboardDo)
                }
            }
            AppConstants.USER_PRESELER ->{
                var dashboardDos = kpiDashboardDoListPresales
                for (obj in dashboardDos){
                    val kpiDashboardDo = getCustomerMenu(obj)
                    kpiDashboardDo.objKPI = obj
                    vector.add(kpiDashboardDo)
                }
            }
        }
        return vector
    }

    fun getCustomerMenu(customerMenu: ACTIVITIES) : KpiDashboardDo{
        var kpiDashboardDo = KpiDashboardDo();
        when(customerMenu){

            ACTIVITIES.ACTIVITY_ASSET_CHECK->{
                kpiDashboardDo.Description = context.getString(R.string.Asset_Scan)
                kpiDashboardDo.IsActive = true
                kpiDashboardDo.image = R.drawable.baseline_article_24
            }
            ACTIVITIES.ACTIVITY_STORE_CHECK->{

                kpiDashboardDo.Description = context.getString(R.string.storecheck)
                kpiDashboardDo.IsActive = true
                kpiDashboardDo.image = R.drawable.baseline_store_24
            }
            ACTIVITIES.ACTIVITY_SALES_ORDER->{

                kpiDashboardDo.Description = context.getString(R.string.salesorder)
                kpiDashboardDo.IsActive = true
                kpiDashboardDo.image = R.drawable.baseline_point_of_sale_24
            }
            ACTIVITIES.ACTIVITY_PLANOGRAM->{

                kpiDashboardDo.Description = context.getString(R.string.planogram)
                kpiDashboardDo.IsActive = true
                kpiDashboardDo.image = R.drawable.baseline_aod_24
            }
            ACTIVITIES.ACTIVITY_RETURNS->{

                kpiDashboardDo.Description = context.getString(R.string.Return_order)
                kpiDashboardDo.IsActive = true
                kpiDashboardDo.image = R.drawable.baseline_assignment_returned_24
            }
            ACTIVITIES.ACTIVITY_COLLECTIONS->{
                kpiDashboardDo.Description = context.getString(R.string.collections)
                kpiDashboardDo.IsActive = true
                kpiDashboardDo.image = R.drawable.baseline_payments_24
            }
            ACTIVITIES.ACTIVITY_COMPETITOR->{
                kpiDashboardDo.Description = context.getString(R.string.competitor_info)
                kpiDashboardDo.IsActive = true
                kpiDashboardDo.image = R.drawable.baseline_compare_arrows_24
            }
            ACTIVITIES.ACTIVITY_CHECKOUT->{
                kpiDashboardDo.Description = context.getString(R.string.check_out)
                kpiDashboardDo.IsActive = true
                kpiDashboardDo.image = R.drawable.baseline_logout_24
            }
            ACTIVITIES.ACTIVITY_PRESALES_ORDER -> {

                kpiDashboardDo.Description = context.getString(R.string.PreSales_Order)
                kpiDashboardDo.IsActive = true
                kpiDashboardDo.image = R.drawable.baseline_point_of_sale_24
            }
        }
        return kpiDashboardDo;
    }
}