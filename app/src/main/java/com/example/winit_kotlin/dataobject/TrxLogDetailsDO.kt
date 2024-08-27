package com.example.winit_kotlin.dataobject

import java.io.Serializable

data class TrxLogDetailsDO(var date: String = "",
                           var customerName: String = "",
                           var customerCode: String = "",
                           var isJp: String = "",
                           var trxType: String = "",
                           var documentNumber: String = "",
                           var amount: Double = 0.0,
                           var timeStamp: String = "",
                           var columnName: String = ""
) : Serializable
