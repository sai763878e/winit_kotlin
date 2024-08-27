package com.example.winit_kotlin.dataobject

import java.io.Serializable

data class TrxLogHeaders(
    var trxLogHeaderId: String = "",
    var trxDate: String = "",
    var totalScheduledCalls: Int = 0,
    var totalActualCalls: Int = 0,
    var totalProductiveCalls: Int = 0,
    var totalSales: Double = 0.0,
    var totalCreditNotes: Double = 0.0,
    var totalCollections: Double = 0.0,
    var currentMonthlySales: Double = 0.0,
    var totalActualCallsPlanned: Int = 0,
    var totalProductiveCallsPlanned: Int = 0,
    var vecTrxLogDetailsDO: MutableList<TrxLogDetailsDO> = mutableListOf()
) : Serializable, Cloneable {
    companion object {
        const val COL_TOTAL_SALES = "TotalSales"
        const val COL_TOTAL_CREDIT_NOTES = "TotalCreditNotes"
        const val COL_TOTAL_COLLECTIONS = "TotalCollections"
        const val COL_STORE_CHECK = "StoreCheck"
        const val COL_TOTAL_ACTUAL_CALLS = "TotalActualCalls"
    }

    public override fun clone(): TrxLogHeaders {
        return super.clone() as TrxLogHeaders
    }
}
