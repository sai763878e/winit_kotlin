package com.example.winit_kotlin.utilities

import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.text.NumberFormat
import java.util.Locale

object FormatUtils {
    private var decimalFormat: DecimalFormat? = null
    private var decimalFormat1: DecimalFormat? = null
    private var percentFormat: DecimalFormat? = null
    private var amountFormate: DecimalFormat? = null
    private var amountFormate1: DecimalFormat? = null
    private var amountFormateNew: DecimalFormat? = null
    private var numFormat: DecimalFormat? = null
    private var formatter: NumberFormat? = null

    fun setDecimalFormat(curencyCode: String, numberOfDecimals: Int) {
        val format: String
        val format1: String

        when (numberOfDecimals) {
            3 -> {
                format = "#,##,##,##,###.###"
                format1 = "###.###"
            }
            2 -> {
                format = "#,##,##,###,###.##"
                format1 = "###.##"
            }
            else -> {
                format = "#,##,##,###,###.##"
                format1 = "###.##"
            }
        }

        decimalFormat = DecimalFormat(format, DecimalFormatSymbols(Locale.ENGLISH)).apply {
            minimumFractionDigits = numberOfDecimals
            maximumFractionDigits = numberOfDecimals
        }

        decimalFormat1 = DecimalFormat(format1, DecimalFormatSymbols(Locale.ENGLISH)).apply {
            minimumFractionDigits = numberOfDecimals
            maximumFractionDigits = numberOfDecimals
        }

        percentFormat = DecimalFormat(format, DecimalFormatSymbols(Locale.ENGLISH)).apply {
            minimumFractionDigits = numberOfDecimals
            maximumFractionDigits = numberOfDecimals
        }

        amountFormate = DecimalFormat(format, DecimalFormatSymbols(Locale.ENGLISH)).apply {
            minimumFractionDigits = numberOfDecimals
            maximumFractionDigits = numberOfDecimals
        }

        amountFormate1 = DecimalFormat(format, DecimalFormatSymbols(Locale.ENGLISH)).apply {
            minimumFractionDigits = 0
            maximumFractionDigits = 0
        }

        amountFormateNew = DecimalFormat(format, DecimalFormatSymbols(Locale.ENGLISH)).apply {
            minimumFractionDigits = numberOfDecimals
            maximumFractionDigits = numberOfDecimals
        }

        formatter = NumberFormat.getNumberInstance().apply {
            minimumFractionDigits = numberOfDecimals
            maximumFractionDigits = numberOfDecimals
        }

        numFormat = DecimalFormat(format).apply {
            minimumFractionDigits = 2
            maximumFractionDigits = 2
        }
    }

    // Add getter methods if needed
    fun getDecimalFormat() = decimalFormat
    fun getDecimalFormat1() = decimalFormat1
    fun getPercentFormat() = percentFormat
    fun getAmountFormate() = amountFormate
    fun getAmountFormate1() = amountFormate1
    fun getAmountFormateNew() = amountFormateNew
    fun getNumFormat() = numFormat
    fun getFormatter() = formatter
}