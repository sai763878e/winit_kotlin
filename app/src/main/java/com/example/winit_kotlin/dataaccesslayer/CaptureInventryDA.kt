package com.example.winit_kotlin.dataaccesslayer

import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import com.example.winit_kotlin.common.AppConstants
import com.example.winit_kotlin.common.MyApplication
import com.example.winit_kotlin.databaseaccess.DatabaseHelper
import com.example.winit_kotlin.dataobject.ProductDO
import com.example.winit_kotlin.dataobject.SettingsDO
import com.example.winit_kotlin.dataobject.UOMConversionFactorDO
import com.example.winit_kotlin.utilities.StringUtils
import com.example.winit_kotlin.utils.CalendarUtils
import com.example.winit_kotlin.webAccessLayer.ConnectionHelper
import java.io.IOException
import java.io.PrintWriter
import java.io.StringWriter
import java.util.Vector

class CaptureInventryDA {
    fun getOnlinePricing(): HashMap<String, HashMap<String, Double>> {
        synchronized(MyApplication.APP_DB_LOCK) {
            var cursor: Cursor? = null
            var sqLiteDatabase: SQLiteDatabase? = null
            val hashMapPricing = HashMap<String, HashMap<String, Double>>()

            Log.e("getOfflinePricingAndPromotionsForVanseller", "started")

            try {
                if (sqLiteDatabase == null || !sqLiteDatabase.isOpen) {
                    sqLiteDatabase = DatabaseHelper.openDataBase()
                }

                val settingsDO = SettingsOrgDA().getSettingValueByName(SettingsDO.get_NO_PRICE_ITEMS(), sqLiteDatabase)
                if (settingsDO != null) {
                    AppConstants.NO_PRICE_ITEMS = settingsDO.SettingValue
                }

                val query = """
                SELECT ITEMCODE, UOM, PRICECASES, ItemType 
                FROM tblCustomerPricing 
                ORDER BY ITEMCODE, UOM
            """.trimIndent()

                cursor = sqLiteDatabase?.rawQuery(query, null)

                cursor?.use { c ->
                    if (c.moveToFirst()) {
                        do {
                            val pricingKey = c.getString(0)
                            if (c.getDouble(2) > 0 || AppConstants.NO_PRICE_ITEMS.contains(pricingKey)) {
                                val hashPriceByUOM = hashMapPricing.getOrPut(pricingKey) { HashMap() }
                                hashPriceByUOM[c.getString(1)] = StringUtils.roundDoublePlaces(c.getDouble(2), 5)
                            }
                        } while (c.moveToNext())
                    }
                }

                val hmUomfactors = HashMap<String, UOMConversionFactorDO>()
                val hashArrUoms = HashMap<String, Vector<String>>()

                // UOM factors
                val strQuery = """
                SELECT U.ItemCode, U.UOM, U.Factor, U.EAConversion 
                FROM tblUOMFactor U
            """.trimIndent()

                sqLiteDatabase?.rawQuery(strQuery, null)?.use { cursorsUoms ->
                    if (cursorsUoms.moveToFirst()) {
                        do {
                            val conversionFactorDO = UOMConversionFactorDO().apply {
                                ItemCode = cursorsUoms.getString(0)
                                UOM = cursorsUoms.getString(1)
                                factor = cursorsUoms.getFloat(2)
                                eaConversion = cursorsUoms.getFloat(3)
                            }
                            val key = "${conversionFactorDO.ItemCode}${conversionFactorDO.UOM}"
                            hmUomfactors[key] = conversionFactorDO

                            val vecUOMs = hashArrUoms.getOrPut(conversionFactorDO.ItemCode) { Vector() }
                            vecUOMs.add(conversionFactorDO.UOM)
                        } while (cursorsUoms.moveToNext())
                    }
                }

                // Recalculating pricing for other UOMs
                val uomQuery = """
                SELECT DISTINCT TUF.ItemCode, TUF.UOM, TUF.Factor, TUF.EAConversion, TP.UOM 
                FROM tblUOMFactor TUF 
                INNER JOIN tblProducts TP ON TP.ItemCode = TUF.ItemCode
            """.trimIndent()

                sqLiteDatabase?.rawQuery(uomQuery, null)?.use { cursorsUoms ->
                    if (cursorsUoms.moveToFirst()) {
                        do {
                            val conversionFactorDO = UOMConversionFactorDO().apply {
                                ItemCode = cursorsUoms.getString(0)
                                UOM = cursorsUoms.getString(1)
                                factor = cursorsUoms.getFloat(2)
                                eaConversion = cursorsUoms.getFloat(3)
                            }

                            hashMapPricing[conversionFactorDO.ItemCode]?.let { hmUomPricing ->
                                val priceUOMKey = hmUomPricing.keys.firstOrNull() ?: ""
                                hashArrUoms[conversionFactorDO.ItemCode]?.forEach { uomKey ->
                                    if (!hmUomPricing.containsKey(uomKey)) {
                                        val uomPrice = hmUomPricing[priceUOMKey] ?: 0.0
                                        val priceFactorDO = hmUomfactors["${conversionFactorDO.ItemCode}$priceUOMKey"]
                                        val actualFactorDO = hmUomfactors["${conversionFactorDO.ItemCode}$uomKey"]
                                        if (priceFactorDO != null && actualFactorDO != null && priceUOMKey != uomKey) {
                                            if (actualFactorDO.eaConversion > priceFactorDO.eaConversion && priceFactorDO.eaConversion > 0) {
                                                hmUomPricing[uomKey] = StringUtils.roundDoublePlaces(
                                                    StringUtils.roundDoublePlaces((actualFactorDO.eaConversion / priceFactorDO.eaConversion).toDouble(), 5) * uomPrice, 5)
                                            } else if (actualFactorDO.eaConversion > 0) {
                                                hmUomPricing[uomKey] = StringUtils.roundDoublePlaces(
                                                    uomPrice / StringUtils.roundDoublePlaces(
                                                        (priceFactorDO.eaConversion / actualFactorDO.eaConversion).toDouble(), 5
                                                    ), 5
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        } while (cursorsUoms.moveToNext())
                    }
                }

                Log.e("getOfflinePricingAndPromotionsForVanseller", "ended")

            } catch (e: Exception) {
                val sw = StringWriter()
                val pw = PrintWriter(sw)
                e.printStackTrace(pw)
                try {
                    ConnectionHelper.writeIntoCrashLog("\n\nCrash at ${CalendarUtils.getCurrentDateAsString()} getOnlinePricing ${sw}")
                } catch (ex: IOException) {
                    ex.printStackTrace()
                }
            } finally {
                cursor?.close()
                sqLiteDatabase?.takeIf { it.isOpen }?.close()
            }

            return hashMapPricing
        }
    }

    fun getStoreCheckedItemsNew(
        site: String,
        categoryId: String?,
        type: String?,
        mDatabase: SQLiteDatabase?,
        isBackStore: Boolean,
        isAlertNo: Boolean,
        storeCheckID: String?
    ): HashMap<String, ProductDO> {


        synchronized(MyApplication.APP_DB_LOCK) {
            val hmItems = hashMapOf<String, ProductDO>()
            var cursor: Cursor? = null
            var db = mDatabase
            try {

                if (db == null || !db.isOpen) db = DatabaseHelper.openDataBase()

                val currentDate = CalendarUtils.getCurrentDateAsStringforStoreCheck()
                var query = ""

                query = if (storeCheckID.isNullOrEmpty()) {
                    if (!categoryId.isNullOrEmpty() && !isBackStore) {
                        """
                    SELECT TCSI.ItemCode, TCSI.ItemDescription, TCSI.BrandCode, TCSI.BrandName, TCSI.ItemStatus, 
                    TCSI.ItemUOM, TCSI.StoreQnty, TCSI.ShelfQnty, TCSI.Status, TCSI.ShelfUOM, 
                    TCSI.CaptureInventoryStatus, TCSI.ItemClasification, IFNULL(TUF.EAConversion, 0) 
                    FROM tblStoreCheckItem TCSI 
                    INNER JOIN tblStoreCheck TSC ON TCSI.StoreCheckId = TSC.StoreCheckId 
                    LEFT JOIN tblUOMFactor TUF ON TCSI.ItemCode = TUF.ItemCode AND TCSI.ItemUOM = TUF.UOM 
                    WHERE TSC.ClientCode = '$site' AND TSC.Date LIKE '%$currentDate%' 
                    AND TCSI.CategoryCode = '$categoryId' AND TCSI.CaptureInventoryStatus = 'True' 
                    ORDER BY TCSI.ITEMCODE ASC
                    """.trimIndent()
                    } else if (!categoryId.isNullOrEmpty() && isBackStore) {
                        """
                    SELECT TCSI.ItemCode, TCSI.ItemDescription, TCSI.BrandCode, TCSI.BrandName, TCSI.ItemStatus, 
                    TCSI.ItemUOM, TCSI.StoreQnty, TCSI.ShelfQnty, TCSI.Status, TCSI.ShelfUOM, 
                    TCSI.CaptureInventoryStatus, TCSI.ItemClasification, IFNULL(TUF.EAConversion, 0) 
                    FROM tblStoreCheckItem TCSI 
                    INNER JOIN tblStoreCheck TSC ON TCSI.StoreCheckId = TSC.StoreCheckId 
                    LEFT JOIN tblUOMFactor TUF ON TCSI.ItemCode = TUF.ItemCode AND TCSI.ItemUOM = TUF.UOM 
                    WHERE TSC.ClientCode = '$site' AND TSC.Date LIKE '%$currentDate%' 
                    AND TCSI.CategoryCode = '$categoryId' AND TCSI.StoreCheckStatus = 'True' 
                    ORDER BY TCSI.ITEMCODE ASC
                    """.trimIndent()
                    } else {
                        """
                    SELECT TCSI.ItemCode, TCSI.ItemDescription, TCSI.BrandCode, TCSI.BrandName, TCSI.ItemStatus, 
                    TCSI.ItemUOM, TCSI.StoreQnty, TCSI.ShelfQnty, TCSI.Status, TCSI.ShelfUOM, 
                    TCSI.CaptureInventoryStatus, TCSI.ItemClasification, IFNULL(TUF.EAConversion, 0) 
                    FROM tblStoreCheckItem TCSI 
                    INNER JOIN tblStoreCheck TSC ON TCSI.StoreCheckId = TSC.StoreCheckId 
                    LEFT JOIN tblUOMFactor TUF ON TCSI.ItemCode = TUF.ItemCode AND TCSI.ItemUOM = TUF.UOM 
                    WHERE TSC.ClientCode = '$site' AND TSC.Date LIKE '%$currentDate%' 
                    AND TCSI.CaptureInventoryStatus = 'True' 
                    ORDER BY TCSI.ITEMCODE ASC
                    """.trimIndent()
                    }
                } else {
                    if (!categoryId.isNullOrEmpty() && !isBackStore) {
                        """
                    SELECT TCSI.ItemCode, TCSI.ItemDescription, TCSI.BrandCode, TCSI.BrandName, TCSI.ItemStatus, 
                    TCSI.ItemUOM, TCSI.StoreQnty, TCSI.ShelfQnty, TCSI.Status, TCSI.ShelfUOM, 
                    TCSI.CaptureInventoryStatus, TCSI.ItemClasification, IFNULL(TUF.EAConversion, 0) 
                    FROM tblStoreCheckItem TCSI 
                    INNER JOIN tblStoreCheck TSC ON TCSI.StoreCheckId = TSC.StoreCheckId 
                    LEFT JOIN tblUOMFactor TUF ON TCSI.ItemCode = TUF.ItemCode AND TCSI.ItemUOM = TUF.UOM 
                    WHERE TSC.StoreCheckId = '$storeCheckID' AND TSC.ClientCode = '$site' AND TSC.Date LIKE '%$currentDate%' 
                    AND TCSI.CategoryCode = '$categoryId' AND TCSI.CaptureInventoryStatus = 'True' 
                    ORDER BY TCSI.ITEMCODE ASC
                    """.trimIndent()
                    } else if (!categoryId.isNullOrEmpty() && isBackStore) {
                        """
                    SELECT TCSI.ItemCode, TCSI.ItemDescription, TCSI.BrandCode, TCSI.BrandName, TCSI.ItemStatus, 
                    TCSI.ItemUOM, TCSI.StoreQnty, TCSI.ShelfQnty, TCSI.Status, TCSI.ShelfUOM, 
                    TCSI.CaptureInventoryStatus, TCSI.ItemClasification, IFNULL(TUF.EAConversion, 0) 
                    FROM tblStoreCheckItem TCSI 
                    INNER JOIN tblStoreCheck TSC ON TCSI.StoreCheckId = TSC.StoreCheckId 
                    LEFT JOIN tblUOMFactor TUF ON TCSI.ItemCode = TUF.ItemCode AND TCSI.ItemUOM = TUF.UOM 
                    WHERE TSC.StoreCheckId = '$storeCheckID' AND TSC.ClientCode = '$site' AND TSC.Date LIKE '%$currentDate%' 
                    AND TCSI.CategoryCode = '$categoryId' AND TCSI.StoreCheckStatus = 'True' 
                    ORDER BY TCSI.ITEMCODE ASC
                    """.trimIndent()
                    } else {
                        """
                    SELECT TCSI.ItemCode, TCSI.ItemDescription, TCSI.BrandCode, TCSI.BrandName, TCSI.ItemStatus, 
                    TCSI.ItemUOM, TCSI.StoreQnty, TCSI.ShelfQnty, TCSI.Status, TCSI.ShelfUOM, 
                    TCSI.CaptureInventoryStatus, TCSI.ItemClasification, IFNULL(TUF.EAConversion, 0) 
                    FROM tblStoreCheckItem TCSI 
                    INNER JOIN tblStoreCheck TSC ON TCSI.StoreCheckId = TSC.StoreCheckId 
                    LEFT JOIN tblUOMFactor TUF ON TCSI.ItemCode = TUF.ItemCode AND TCSI.ItemUOM = TUF.UOM 
                    WHERE TSC.ClientCode = '$site' AND TSC.Date LIKE '%$currentDate%' 
                    AND TCSI.CaptureInventoryStatus = 'True' 
                    ORDER BY TCSI.ITEMCODE ASC
                    """.trimIndent()
                    }
                }

                cursor = db.rawQuery(query, null)
                if (cursor.moveToFirst() && !isAlertNo) {
                    do {
                        val skuCode = cursor.getString(0)
                        var productDO = hmItems[skuCode]
                        if (productDO == null) {
                            productDO = ProductDO().apply {
                                SKU = cursor.getString(0)
                                Description = cursor.getString(1)
                                brandcode = cursor.getString(2)
                                brand = cursor.getString(3)
                                status = cursor.getInt(4)
                                StoreUOM = cursor.getString(5)
                                totalBSQty = cursor.getInt(6) * cursor.getInt(12)
                                totalStoreQty = cursor.getInt(7) * cursor.getInt(12)
                                StoreQTY = totalBSQty.toString()
                                ShelfQTY = totalStoreQty.toString()
                                ShelfUOM = cursor.getString(9)
                                reason = when (cursor.getInt(8)) {
                                    StringUtils.getInt(AppConstants.REASON_MODERATE) -> AppConstants.REASON_MODERATE
                                    StringUtils.getInt(AppConstants.REASON_AVAILABLE) -> AppConstants.REASON_AVAILABLE.also {
                                        isCaptured = true
                                        isAlreadyCaptured = true
                                    }
                                    StringUtils.getInt(AppConstants.REASON_NOTAVAILABLE) -> AppConstants.REASON_NOTAVAILABLE
                                    StringUtils.getInt(AppConstants.REASON_CAPTURE) -> AppConstants.REASON_CAPTURE
                                    else -> AppConstants.REASON_NOTDONE
                                }
                                isCaptureInventoryDone = cursor.getString(10).equals("True", ignoreCase = true)
                                itemClassification = cursor.getInt(11)
                            }
                            hmItems[skuCode] = productDO
                        } else {
                            productDO.apply {
                                totalBSQty += cursor.getInt(6) * cursor.getInt(12)
                                totalStoreQty += cursor.getInt(7) * cursor.getInt(12)
                                StoreQTY = totalBSQty.toString()
                                ShelfQTY = totalStoreQty.toString()
                                if (reason.isEmpty() || reason == AppConstants.REASON_NOTDONE) {
                                    reason = when (cursor.getInt(8)) {
                                        StringUtils.getInt(AppConstants.REASON_MODERATE) -> AppConstants.REASON_MODERATE
                                        StringUtils.getInt(AppConstants.REASON_AVAILABLE) -> AppConstants.REASON_AVAILABLE.also {
                                            isCaptured = true
                                            isAlreadyCaptured = true
                                        }
                                        StringUtils.getInt(AppConstants.REASON_NOTAVAILABLE) -> AppConstants.REASON_NOTAVAILABLE
                                        StringUtils.getInt(AppConstants.REASON_CAPTURE) -> AppConstants.REASON_CAPTURE
                                        else -> AppConstants.REASON_NOTDONE
                                    }
                                    isCaptureInventoryDone = cursor.getString(10).equals("True", ignoreCase = true)
                                    itemClassification = cursor.getInt(11)
                                }
                            }
                        }
                    } while (cursor.moveToNext())
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                cursor?.close()
            }
            return hmItems
        }

    }
    fun getPreviousSalesDataForStoreCheck(
        customerCode: String,
        sqLiteDatabase: SQLiteDatabase?
    ): ArrayList<String> {



        synchronized(MyApplication.APP_DB_LOCK) {
            val arrDetails = arrayListOf<String>()
            var cursor: Cursor? = null
            try {
                var db = sqLiteDatabase
                if (db == null || !db.isOpen) db = DatabaseHelper.openDataBase()

                val targetYear = StringUtils.getInt(CalendarUtils.getTargetCurrentYear())
                val previousYear = StringUtils.getInt(CalendarUtils.getPrevYearTarget())
                val targetMonth = StringUtils.getInt(CalendarUtils.getTargetCurrentMonth())
                val query: String
                val previousMonth: Int

                query = if (targetYear > previousYear) {
                    previousMonth = 12 - AppConstants.REC_ORDER_MONTHS + targetMonth
                    """
                SELECT ItemCode, SUM(Qty) 
                FROM tblCustomerSalesHistory 
                WHERE (
                    (Year='$targetYear' AND MONTH<='$targetMonth') OR 
                    (Year='$previousYear' AND MONTH>='$previousMonth')
                ) AND CustomerCode='$customerCode' 
                GROUP BY ItemCode
                """.trimIndent()
                } else {
                    previousMonth = targetMonth - AppConstants.REC_ORDER_MONTHS
                    """
                SELECT ItemCode, SUM(Qty) 
                FROM tblCustomerSalesHistory 
                WHERE Year='$targetYear' 
                AND MONTH<='$targetMonth' 
                AND MONTH>='$previousMonth' 
                AND CustomerCode='$customerCode' 
                GROUP BY ItemCode
                """.trimIndent()
                }

                cursor = db.rawQuery(query, null)
                if (cursor.moveToFirst()) {
                    do {
                        val quantity = cursor.getDouble(1)
                        if (quantity > 0) {
                            arrDetails.add(cursor.getString(0))
                        }
                    } while (cursor.moveToNext())
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                cursor?.close()
            }
            return arrDetails
        }

    }


}