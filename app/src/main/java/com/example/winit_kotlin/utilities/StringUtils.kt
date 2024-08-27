package com.example.winit_kotlin.utilities

import android.text.TextUtils
import com.example.winit_kotlin.common.AppConstants
import java.math.BigDecimal
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.UUID
import kotlin.math.floor
import kotlin.math.pow
import kotlin.math.roundToLong

object StringUtils {

    @JvmStatic
    fun getIntSlow(str: String?): Int {
        var value = 0

        if (str.isNullOrEmpty() || str.contains("T") || str.equals("null", ignoreCase = true) || str.contains(":"))
            return value

        var newStr = str.replace(",", "")

        if (newStr.contains("."))
            return getFloat(newStr).toInt()

        try {
            value = newStr.toInt()
        } catch (e: Exception) {
            value = getFloat(newStr).toInt()
            LogUtils.errorLog("StringUtils", "Error occurred while parsing as integer${e.message}")
        }
        return value
    }

    @JvmStatic
    fun getInt(str: String?): Int {
        return try {
            str?.toInt() ?: getIntSlow(str)
        } catch (e: Exception) {
            getIntSlow(str)
        }
    }

    @JvmStatic
    fun getBoolean(str: String?): Boolean {
        return try {
            str?.toBoolean() ?: false
        } catch (e: Exception) {
            LogUtils.errorLog("StringUtils", "Error occurred while parsing as boolean${e.message}")
            false
        }
    }

    @JvmStatic
    fun round(number: String, precision: Int): Double {
        val newPrecision = AppConstants.NUMBER_OF_DECIMALS_TO_ROUND
        val temp = getDouble(number)
        return (temp * Math.pow(10.0, newPrecision.toDouble()) + 0.5).toInt() / Math.pow(10.0, newPrecision.toDouble())
    }

    @JvmStatic
    fun roundOthers(number: String, precision: Int): Double {
        val newPrecision = AppConstants.NUMBER_OF_DECIMALS_TO_ROUND
        val temp = getDouble(number)
        return (temp * Math.pow(10.0, newPrecision.toDouble()) + 0.5).toInt() / Math.pow(10.0, newPrecision.toDouble())
    }

    @JvmStatic
    fun round(number: Double, precision: Int): Double {
        val newPrecision = AppConstants.NUMBER_OF_DECIMALS_TO_ROUND
        return (number * Math.pow(10.0, newPrecision.toDouble()) + 0.5).toInt() / Math.pow(10.0, newPrecision.toDouble())
    }

    @JvmStatic
    fun roundNormal(number: Double, precision: Int): Double {
        val newPrecision = AppConstants.NUMBER_OF_DECIMALS_TO_ROUND
        return (number * Math.pow(10.0, newPrecision.toDouble()) + 0.5).toInt() / Math.pow(10.0, newPrecision.toDouble())
    }

    @JvmStatic
    fun roundDouble(value: Double, places: Int): Double {
        val newPlaces = AppConstants.NUMBER_OF_DECIMALS_TO_ROUND
        require(newPlaces >= 0) { "Places cannot be negative" }
        return roundDoublePlacesOriginal(value, newPlaces)
    }

    @JvmStatic
    fun roundDoublePlaces(value: Double, places: Int): Double {
        require(places >= 0) { "Places cannot be negative" }
        return roundDoublePlacesOriginal(value, places)
    }

    @JvmStatic
    fun roundDoubleNew(value: Double, places: Int): Double {
        require(places >= 0) { "Places cannot be negative" }
        return roundDoublePlacesOriginal(value, places)
    }

    @JvmStatic
    fun roundDoublePlacesOriginal(value: Double, places: Int): Double {
        require(places >= 0) { "Places cannot be negative" }

        var newValue = roundDoubleExact(value, 5)
        val factor = 10.0.pow(places + 1)
        val multiple = 10.0.pow(places)
        newValue = newValue * factor + 5
        newValue /= factor
        newValue = floor(roundDoubleExact(newValue * multiple, 5)) / multiple
        return newValue
    }

    @JvmStatic
    fun roundDoubleNew1(value: Double, places: Int): Double {
        require(places >= 0) { "Places cannot be negative" }
        return roundDoublePlacesOriginal1(value, places)
    }

    @JvmStatic
    fun roundDoublePlacesOriginal1(value: Double, places: Int): Double {
        require(places >= 0) { "Places cannot be negative" }

        var newValue = roundDoubleExact(value, 5)
        val factor = 10.0.pow(places + 1)
        val multiple = 10.0.pow(places)
        newValue *= factor
        newValue /= factor
        newValue = floor(roundDoubleExact(newValue * multiple, 5)) / multiple
        return newValue
    }

    @JvmStatic
    fun roundDoubleExact(value: Double, places: Int): Double {
        require(places >= 0) { "Places cannot be negative" }

        val factor = 10.0.pow(places)
        val tmp = (value * factor).roundToLong()
        return tmp / factor
    }


    @JvmStatic
    fun roundFloat(value: Float, places: Int): Float {
        return try {
            val newPlaces = AppConstants.NUMBER_OF_DECIMALS_TO_ROUND
            require(newPlaces >= 0) { "Places cannot be negative" }

            val factor = 10.0.pow(newPlaces).toFloat()
            val tmp = (value * factor).roundToLong()
            tmp / factor
        } catch (e: Exception) {
            value
        }
    }
    @JvmStatic
    fun roundFloatPlaces(value: Float, places: Int): Float {
        return try {
            require(places >= 0) { "Places cannot be negative" }

            val factor = 10.0.pow(places).toFloat()
            val tmp = (value * factor).roundToLong()
            tmp / factor
        } catch (e: Exception) {
            value
        }
    }

    @JvmStatic
    fun roundFloatNew(value: Double, places: Int): Float {
        require(places >= 0) { "Places cannot be negative" }
        return roundFloatPlacesOriginal(value, places)
    }

    @JvmStatic
    fun roundFloatPlacesOriginal(value: Double, places: Int): Float {
        require(places >= 0) { "Places cannot be negative" }

        var newValue = roundFloatExact(value, 4)
        val factor = 10.0.pow(places + 1).toFloat()
        val multiple = 10.0.pow(places).toFloat()
        newValue = (newValue * factor + 5)
        newValue /= factor
        newValue = ((floor(roundDoubleExact((newValue * multiple).toDouble(), 4)) / multiple).toFloat())
        return newValue
    }

    @JvmStatic
    fun roundFloatExact(value: Double, places: Int): Float {
        require(places >= 0) { "Places cannot be negative" }

        val factor = 10.0.pow(places).toFloat()
        val tmp = (value * factor).roundToLong()
        return tmp / factor
    }

    @JvmStatic
    fun getImageNameFromUrl(url: String): String {
        return if (url.contains("*/")) {
            url.substringAfter("/img/").replace("/", "").replace("*", "")
        } else {
            url.substringAfterLast("/").substringBefore(".")
        }
    }

    @JvmStatic
    fun getString(str: Boolean): String {
        return try {
            str.toString()
        } catch (e: Exception) {
            LogUtils.errorLog("StringUtils", "Error occurred while getString${e.message}")
            ""
        }
    }

    @JvmStatic
    fun getString(str: Int): String {
        return try {
            str.toString()
        } catch (e: Exception) {
            LogUtils.errorLog("StringUtils", "Error occurred while getString${e.message}")
            ""
        }
    }

    @JvmStatic
    fun getString(str: Long): String {
        return try {
            str.toString()
        } catch (e: Exception) {
            LogUtils.errorLog("StringUtils", "Error occurred while getString${e.message}")
            ""
        }
    }

    @JvmStatic
    fun isValidEmail(string: String): Boolean {
        val emailPattern = Regex(
            "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                    "\\@" +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                    "(" +
                    "\\." +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                    ")+"
        )
        return emailPattern.matches(string)
    }

    @JvmStatic
    fun getFloatSlow(string: String?): Float {
        if (string.isNullOrEmpty() || string == "." || string.equals("null", ignoreCase = true) || string.contains("T")) {
            return 0f
        }

        val newString = string.replace(",", "")
        return try {
            newString.toFloat()
        } catch (e: Exception) {
            LogUtils.errorLog("StringUtils", "Error occurred while getFloat${e.message}")
            0f
        }
    }

    @JvmStatic
    fun getFloat(string: String?): Float {
        return try {
            string?.toFloat() ?: getFloatSlow(string)
        } catch (e: Exception) {
            getFloatSlow(string)
        }
    }

    @JvmStatic
    fun getDoubleSlow(string: String?): Double {
        if (string.isNullOrEmpty() || string == "." || string.equals("null", ignoreCase = true) || string.contains("T")) {
            return 0.0
        }

        val newString = string.replace(",", "")
        return try {
            newString.toDouble()
        } catch (e: Exception) {
            LogUtils.errorLog("StringUtils", "Error occurred while getDouble${e.message}")
            0.0
        }
    }

    @JvmStatic
    fun getDouble(string: String?): Double {
        return try {
            string?.toDouble() ?: getDoubleSlow(string)
        } catch (e: Exception) {
            getDoubleSlow(string)
        }
    }

    @JvmStatic
    fun getNumber(string: String?): Double {
        return try {
            string?.toDouble() ?: 0.0
        } catch (e: Exception) {
            getDoubleSlow(string)
        }
    }

    @JvmStatic
    fun getNumberFormatted(number: Double): String {
        return try {
            val formatter = NumberFormat.getInstance().apply { minimumFractionDigits = 2 }
            formatter.format(number)
        } catch (e: Exception) {
            LogUtils.errorLog("StringUtils", "Error occurred while getNumberFormatted${e.message}")
            ""
        }
    }

    @JvmStatic
    fun getNumberFormattedOther(number: Double): String {
        return try {
            val formatter = NumberFormat.getInstance().apply { minimumFractionDigits = 2 }
            formatter.format(number)
        } catch (e: Exception) {
            LogUtils.errorLog("StringUtils", "Error occurred while getNumberFormattedOther${e.message}")
            ""
        }
    }

    @JvmStatic
    fun getNumberFormattedExact(number: Double): String {
        return try {
            val formatter = NumberFormat.getInstance().apply { minimumFractionDigits = 4 }
            formatter.format(number)
        } catch (e: Exception) {
            LogUtils.errorLog("StringUtils", "Error occurred while getNumberFormattedExact${e.message}")
            ""
        }
    }

    @JvmStatic
    fun getInteger(number: Double): String {
        return try {
            val formatter = NumberFormat.getInstance().apply { minimumFractionDigits = 0 }
            formatter.format(number)
        } catch (e: Exception) {
            LogUtils.errorLog("StringUtils", "Error occurred while getInteger${e.message}")
            ""
        }
    }

    @JvmStatic
    fun getInteger(number: Int): String {
        return try {
            val formatter = NumberFormat.getInstance().apply { minimumFractionDigits = 0 }
            formatter.format(number)
        } catch (e: Exception) {
            LogUtils.errorLog("StringUtils", "Error occurred while getInteger${e.message}")
            ""
        }
    }

    @JvmStatic
    fun getInteger(number: Long): String {
        return try {
            val formatter = NumberFormat.getInstance().apply { minimumFractionDigits = 0 }
            formatter.format(number)
        } catch (e: Exception) {
            LogUtils.errorLog("StringUtils", "Error occurred while getInteger${e.message}")
            ""
        }
    }

    @JvmStatic
    fun getDecimal(number: Double): String {
        return try {
            val formatter = NumberFormat.getInstance().apply { minimumFractionDigits = 1 }
            formatter.format(number)
        } catch (e: Exception) {
            LogUtils.errorLog("StringUtils", "Error occurred while getDecimal${e.message}")
            ""
        }
    }

    @JvmStatic
    fun getLong(number: String?): Long {
        if (number.isNullOrEmpty() || number.equals("null", ignoreCase = true)) {
            return 0L
        }
        return try {
            number.toLong()
        } catch (e: Exception) {
            LogUtils.errorLog("StringUtils", "Error occurred while getLong${e.message}")
            0L
        }
    }

    @JvmStatic
    fun getBigDecimal(number: String?): BigDecimal {
        if (number.isNullOrEmpty() || number == "." || number.equals("null", ignoreCase = true)) {
            return BigDecimal.ZERO
        }
        return try {
            BigDecimal(number.replace(",", ""))
        } catch (e: Exception) {
            LogUtils.errorLog("StringUtils", "Error occurred while getBigDecimal${e.message}")
            BigDecimal.ZERO
        }
    }

    @JvmStatic
    fun getStringFromList(list: List<String>): String {
        return list.joinToString(",") { it.replace(",", "##") }
    }

    @JvmStatic
    fun getListFromString(str: String?): List<String> {
        return str?.split(",")?.map { it.replace("##", ",") } ?: emptyList()
    }

    @JvmStatic
    fun getStringFromListBoolean(list: List<Boolean>): String {
        return list.joinToString(",") { it.toString() }
    }

    @JvmStatic
    fun getListFromBoolean(str: String?): List<Boolean> {
        return str?.split(",")?.map { it.toBoolean() } ?: emptyList()
    }

    @JvmStatic
    fun getDateFromString(dateStr: String?, format: String): Date? {
        return try {
            SimpleDateFormat(format, Locale.ENGLISH).parse(dateStr)
        } catch (e: Exception) {
            LogUtils.errorLog("StringUtils", "Error occurred while getDateFromString${e.message}")
            null
        }
    }

    @JvmStatic
    fun getStringFromDate(date: Date?, format: String): String {
        return try {
            SimpleDateFormat(format, Locale.ENGLISH).format(date)
        } catch (e: Exception) {
            LogUtils.errorLog("StringUtils", "Error occurred while getStringFromDate${e.message}")
            ""
        }
    }

    @JvmStatic
    fun isValidPhoneNumber(phoneNumber: String): Boolean {
        return phoneNumber.length in 7..13 && TextUtils.isDigitsOnly(phoneNumber)
    }

    @JvmStatic
    fun checkSpecialCharacters(string: String?): Boolean {
        return string?.contains(Regex("[!@#$%^&*()_+=<>?/{}~`-]")) ?: false
    }
    fun getUniqueUUID(): String {
        return UUID.randomUUID().toString()
    }

}
