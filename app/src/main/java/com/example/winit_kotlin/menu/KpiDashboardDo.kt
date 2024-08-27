package com.example.winit_kotlin.menu

import java.io.Serializable

data class KpiDashboardDo(
    var KPIId: String = "",
    var Code: String = "",
    var objKPI: CustomerMenu.ACTIVITIES? = null,
    var IsActive: Boolean = false,
    var Description: String = "",
    var isMedentry: Boolean = false,
    var prioritySeq: Int = -1,
    var image : Int = 0
):Serializable
