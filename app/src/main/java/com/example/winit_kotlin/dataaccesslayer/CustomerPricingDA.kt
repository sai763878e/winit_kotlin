package com.example.winit_kotlin.dataaccesslayer

import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.example.winit_kotlin.common.MyApplication
import com.example.winit_kotlin.databaseaccess.DatabaseHelper

class CustomerPricingDA {


    @Throws(Exception::class)
    fun getApplicablePricing_(
        customerCode: String,
        userCode: String,
        routeCode: String,
        isDistributionPriceApplicable: Boolean
    ): LinkedHashMap<String, Int>? {
        synchronized(MyApplication.APP_DB_LOCK) {
            var sqLiteDatabase: SQLiteDatabase? = null
            var cursor : Cursor? = null
            var Is_Pricing_At_Group_Level : Int = 1;

            return try {
                sqLiteDatabase = DatabaseHelper.openDataBase()
                sqLiteDatabase.beginTransaction()

                val subQuery = if (!isDistributionPriceApplicable) {
                    ""
//                    "WHERE P.PriceListClassificationCode != '${PricingDO.DISTRIBUTION_PRICE_CODE}' "
                } else {
                    ""
                }

                // Clear previous data
                var query = "DELETE FROM tblCustomerPriceList"
                sqLiteDatabase.execSQL(query)

                query = "DELETE FROM tblCustomerPricing"
                sqLiteDatabase.execSQL(query)


                query =
                    "SELECT SettingValue FROM tblSettings WHERE TRIM(SettingName) = 'Is_Pricing_At_Group_Level'"
                cursor = sqLiteDatabase.rawQuery(query, null)
                if (cursor.moveToFirst()) {
                    Is_Pricing_At_Group_Level = cursor.getInt(0)
                }

                if (cursor != null && !cursor.isClosed()) cursor.close()


                // Insert into tblCustomerPricing
                if (Is_Pricing_At_Group_Level == 1) {
                    query = "INSERT INTO tblCustomerPriceList(PriceListCode, Priority) " +
                            "SELECT PriceListCode, MIN([Priority]) AS [Priority] " +
                            "FROM " +
                            "( " +
                            "SELECT DISTINCT PriceListCode, 0 AS [Priority] " +
                            "FROM tblPriceListAssignment " +
                            "WHERE Code = '" + customerCode + "' " +
                            "AND AssignmentType = 8 " +
                            "UNION " +
                            "SELECT DISTINCT A.PriceListCode, 1 AS [Priority] " +
                            "FROM tblPriceListAssignment A INNER JOIN " +
                            "tblCustomer D ON D.CustomerGroupCode = A.Code " +
                            "WHERE D.SITE = '" + customerCode + "' " +
                            "AND AssignmentType = 7 " +
                            "AND D.CustomerGroupCode IS NOT NULL " +
                            "UNION " +
                            "SELECT DISTINCT A.PriceListCode, 3 AS [Priority] " +
                            "FROM tblPriceListAssignment A INNER JOIN " +
                            "tblCustomer D ON D.Attribute1 = A.Code " +
                            "WHERE D.SITE = '" + customerCode + "' " +
                            "AND AssignmentType = 12 " +
                            "AND D.Attribute1 IS NOT NULL " +
                            "UNION " +
                            "SELECT DISTINCT A.PriceListCode, 7 AS [Priority] " +
                            "FROM tblPriceListAssignment A INNER JOIN " +
                            "tblCustomer D ON D.SalesOrgCode = A.Code " +
                            "WHERE D.SITE = '" + customerCode + "' " +
                            "AND AssignmentType = 10 " +
                            "AND D.SalesOrgCode IS NOT NULL " +
                            "UNION " +
                            "SELECT DISTINCT A.PriceListCode, 4 AS [Priority] " +
                            "FROM tblPriceListAssignment A INNER JOIN " +
                            "tblCustomer D ON D.SubChannelCode = A.Code " +
                            "WHERE D.SITE = '" + customerCode + "' " +
                            "AND AssignmentType = 6 " +
                            "AND D.SubChannelCode IS NOT NULL " +
                            "UNION " +
                            "SELECT DISTINCT A.PriceListCode, 5 AS [Priority] " +
                            "FROM tblPriceListAssignment A INNER JOIN " +
                            "tblCustomer D ON D.ChannelCode = A.Code " +
                            "WHERE D.SITE = '" + customerCode + "' " +
                            "AND AssignmentType = 5 " +
                            "AND D.ChannelCode IS NOT NULL " +
                            "UNION " +
                            "SELECT DISTINCT PriceListCode, 6 AS [Priority] " +
                            "FROM tblPriceListAssignment  " +
                            "WHERE Code = '" + userCode + "' OR Code = '" + routeCode + "'" +
                            "AND AssignmentType = 4 " +
                            "UNION " +
                            "SELECT DISTINCT A.PriceListCode, 2 AS [Priority] " +
                            "FROM tblPriceListAssignment A INNER JOIN " +
                            "tblCustomer D ON D.SubSubChannelCode = A.Code " +
                            "WHERE D.SITE = '" + customerCode + "' " +
                            "AND AssignmentType = 10 " +
                            "AND D.ChannelCode IS NOT NULL " +
                            ") AS PP " +
                            "GROUP BY PriceListCode " + "ORDER BY [Priority]"
                    sqLiteDatabase.execSQL(query)

                    query =
                        ("INSERT INTO tblCustomerPricing(Priority,ITEMCODE,CUSTOMERPRICINGKEY,PRICECASES,ENDDATE,STARTDATE,DISCOUNT,"
                                + "IsExpired,emptyCasePrice,TaxGroupCode,TaxPercentage,ModifiedDate,ModifiedTime,"
                                + "UOM,CustomerCode,UserCode,ConditionType,CalcType,SalesOrgCode,IsZCash,IsVanSales,Site,"
                                + "PriceValue,ItemType,DummyPrice,PriceProtctionPeriod,CreditDays,PriceListClassificationCode,"
                                + "MinQty,MaxQty,PricingId) " +
                                "SELECT PLC.Priority,ITEMCODE,CUSTOMERPRICINGKEY,PRICECASES,ENDDATE,STARTDATE,DISCOUNT,IsExpired,"
                                + "emptyCasePrice,TaxGroupCode,TaxPercentage,ModifiedDate,ModifiedTime,UOM,CustomerCode,UserCode,"
                                + "ConditionType,CalcType,SalesOrgCode,IsZCash,IsVanSales,Site,PriceValue,ItemType,DummyPrice,"
                                + "PriceProtctionPeriod,CreditDays,PriceListClassificationCode,MinQty,MaxQty,PricingId " +
                                "FROM vw_ActivePricing P " +
                                "Inner Join tblCustomerPriceList PLC on PLC.PriceListCode =P.CUSTOMERPRICINGKEY " +
                                subQuery +
                                "ORDER BY P.ITEMCODE, P.PriceListClassificationCode, PLC.Priority ASC")
                    sqLiteDatabase.execSQL(query)

                    query = "DELETE " +
                            "FROM tblCustomerPricing  " +
                            "WHERE ITEMCODE || '_' || PriceListClassificationCode || '_' || priority not in " +
                            "( " +
                            "SELECT Key " +
                            "from " +
                            "( " +
                            "SELECT ITEMCODE || '_' || PriceListClassificationCode  || '_' || MIN(priority) AS Key, MIN(priority) " +
                            "FROM tblCustomerPricing  " +
                            "GROUP BY ITEMCODE, PriceListClassificationCode " +
                            ") " + ");"
                    sqLiteDatabase.execSQL(query)
                }else{
                    // Insert into tblCustomerPriceList
                    query = """
                INSERT INTO tblCustomerPriceList (PriceListCode, Priority)
                SELECT PriceList, MIN([Priority]) AS [Priority]
                FROM (
                    SELECT DISTINCT PriceList, 0 AS [Priority] FROM tblCustomer
                    WHERE Site = '$customerCode'
                ) AS PP
                GROUP BY PriceList
                ORDER BY [Priority]
            """.trimIndent()
                    sqLiteDatabase.execSQL(query)


                    query = """
                INSERT INTO tblCustomerPricing (
                    Priority, ITEMCODE, CUSTOMERPRICINGKEY, PRICECASES, ENDDATE, STARTDATE, DISCOUNT,
                    IsExpired, emptyCasePrice, TaxGroupCode, TaxPercentage, ModifiedDate, ModifiedTime,
                    UOM, CustomerCode, UserCode, ConditionType, CalcType, SalesOrgCode, IsZCash, IsVanSales, Site,
                    PriceValue, ItemType, DummyPrice, PriceProtctionPeriod, CreditDays, PriceListClassificationCode,
                    MinQty, MaxQty, PricingId
                )
                SELECT PLC.Priority, ITEMCODE, CUSTOMERPRICINGKEY, PRICECASES, ENDDATE, STARTDATE, DISCOUNT, IsExpired,
                       emptyCasePrice, TaxGroupCode, TaxPercentage, ModifiedDate, ModifiedTime, UOM, CustomerCode, UserCode,
                       ConditionType, CalcType, SalesOrgCode, IsZCash, IsVanSales, Site, PriceValue, ItemType, DummyPrice,
                       PriceProtctionPeriod, CreditDays, PriceListClassificationCode, MinQty, MaxQty, PricingId
                FROM vw_ActivePricing P
                INNER JOIN tblCustomerPriceList PLC ON PLC.PriceListCode = P.CUSTOMERPRICINGKEY
                $subQuery
                ORDER BY P.ITEMCODE, P.PriceListClassificationCode, PLC.Priority ASC
            """.trimIndent()
                    sqLiteDatabase.execSQL(query)

                    // Remove obsolete pricing
                    query = """
                DELETE FROM tblCustomerPricing
                WHERE ITEMCODE || '_' || PriceListClassificationCode || '_' || priority NOT IN (
                    SELECT Key1
                    FROM (
                        SELECT ITEMCODE || '_' || PriceListClassificationCode || '_' || MIN(priority) AS Key1, MIN(priority)
                        FROM tblCustomerPricing
                        GROUP BY ITEMCODE, PriceListClassificationCode
                    )
                )
            """.trimIndent()
                    sqLiteDatabase.execSQL(query)
                }
                // Commit transaction
                sqLiteDatabase.setTransactionSuccessful()
                null // Replace with actual data if needed

            } catch (e: Exception) {
                e.printStackTrace()
                throw e
            } finally {
                sqLiteDatabase?.endTransaction()
                sqLiteDatabase?.close()
            }
        }
    }

}