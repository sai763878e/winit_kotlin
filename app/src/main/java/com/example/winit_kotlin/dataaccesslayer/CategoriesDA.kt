package com.example.winit_kotlin.dataaccesslayer

import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.text.TextUtils
import android.util.Log
import com.example.winit_kotlin.common.AppConstants
import com.example.winit_kotlin.common.MyApplication
import com.example.winit_kotlin.databaseaccess.DatabaseHelper
import com.example.winit_kotlin.dataobject.BrandDO
import com.example.winit_kotlin.dataobject.CategoryDO
import com.example.winit_kotlin.dataobject.Customer
import com.example.winit_kotlin.dataobject.ProductDO
import com.example.winit_kotlin.dataobject.StoreCheckClassificationDO
import com.example.winit_kotlin.dataobject.UOMConversionFactorDO
import com.example.winit_kotlin.utilities.StringUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.Vector

class CategoriesDA {

    fun getReturnReasons(channelCode: String, type: String): Vector<String> {
        synchronized(MyApplication.APP_DB_LOCK) {
            var mDatabase: SQLiteDatabase? = null
            var cursor: Cursor? = null
            val vecReasons = Vector<String>()

            try {
                mDatabase = DatabaseHelper.openDataBase()
                val query = "SELECT DISTINCT Name FROM tblReasons WHERE Type='$type' COLLATE NOCASE GROUP BY Name"
                cursor = mDatabase.rawQuery(query, null)

                if (cursor.moveToFirst()) {
                    do {
                        vecReasons.add(cursor.getString(0))
                    } while (cursor.moveToNext())
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                cursor?.close()
                mDatabase?.close()
            }

            return vecReasons
        }
    }


    fun getAllCategory(): Vector<CategoryDO> {
        synchronized(MyApplication.APP_DB_LOCK) {
            var mDatabase: SQLiteDatabase? = null
            var cursor: Cursor? = null
            val vector = Vector<CategoryDO>()

            try {
                mDatabase = DatabaseHelper.openDataBase()
                cursor = mDatabase.rawQuery("SELECT DISTINCT CategoryId, CategoryName FROM tblCategory WHERE ParentCode='' ORDER BY CategoryName ASC", null)

                if (cursor.moveToFirst()) {
                    // Adding the "ALL" category
                    val objCategory = CategoryDO().apply {
                        categoryId = "ALL"
                        categoryName = "ALL CATEGORY"
                    }
                    vector.add(objCategory)

                    do {
                        val objCategory1 = CategoryDO().apply {
                            categoryId = cursor.getString(0)
                            categoryName = cursor.getString(1)
                        }
                        vector.add(objCategory1)
                    } while (cursor.moveToNext())
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                cursor?.close()
                mDatabase?.close()
            }

            return vector
        }
    }
    fun getAllSubCategory(): Vector<CategoryDO> {

        synchronized(MyApplication.APP_DB_LOCK) {
            val vector = Vector<CategoryDO>()
            val database: SQLiteDatabase? = null
            var cursor: Cursor? = null
            try {
                val mDatabase = DatabaseHelper.openDataBase()
                val query = "SELECT DISTINCT CategoryId, CategoryName FROM tblCategory WHERE ParentCode != '' ORDER BY CategoryName ASC"
                cursor = mDatabase.rawQuery(query, null)

                if (cursor.moveToFirst()) {
                    val allCategory = CategoryDO(categoryId = "ALL", categoryName = "ALL SUBCATEGORY")
                    vector.add(allCategory)

                    do {
                        val category = CategoryDO(
                            categoryId = cursor.getString(0),
                            categoryName = cursor.getString(1)
                        )
                        vector.add(category)
                    } while (cursor.moveToNext())
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                cursor?.close()
                database?.close()
            }
            return vector
        }

    }
    fun getAllSecondaryUOMs(): HashMap<String, String> {


        synchronized(MyApplication.APP_DB_LOCK) {
            val hmSecondaryUoms = HashMap<String, String>()
            var cursor: Cursor? = null
            val query = "SELECT itemCode, SecondaryUOM FROM tblProducts"
            try {
                val mDatabase = DatabaseHelper.openDataBase()
                cursor = mDatabase.rawQuery(query, null)

                if (cursor.moveToFirst()) {
                    do {
                        val itemCode = cursor.getString(0)
                        val secondaryUOM = cursor.getString(1)
                        hmSecondaryUoms[itemCode] = secondaryUOM
                    } while (cursor.moveToNext())
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                cursor?.close()
            }
            return hmSecondaryUoms
        }


    }
     suspend fun getStoreCheckClassificationsNew(customer: Customer, categoryId: String, isYesOrNo : Boolean, isFrozen : Boolean,
                                                 vecMSLItems : Vector<String>?, vecFocusItems : Vector<String>?, vecNewItems : Vector<String>?,
                                                 isSCSubBrandgroup: Boolean, hashMapNPD : HashMap<String,ProductDO>,
                                                 isAlertNo: Boolean, storeCheckID : String) : Array<Any?>  = withContext(Dispatchers.IO){
        synchronized(MyApplication.APP_DB_LOCK){
            var objSqliteDB: SQLiteDatabase? = null
            var c: Cursor? = null
            val objectArray = arrayOfNulls<Any?>(6)
            var hmStoreCheckedItems = hashMapOf<String, ProductDO>()
            var hmArrUoms = HashMap<String,Vector<String>>()
            var vecUOMS : Vector<String>?;
            var hmUomDetails : HashMap<String,HashMap<String,UOMConversionFactorDO>> = HashMap()
            var hmTrxUoms : HashMap<String,UOMConversionFactorDO> = HashMap()
            var hmMaxUOM : HashMap<String,UOMConversionFactorDO> = HashMap()
            var hmMinUOM : HashMap<String,UOMConversionFactorDO> = HashMap()
            var vecTotalDistinctItesmInStore: Vector<String> = Vector()
            var vecAllProducts: ArrayList<ProductDO> = ArrayList()
           var vecAllItemCodes: Vector<String> = Vector()
            var vecClassifications : Vector<StoreCheckClassificationDO> = Vector()
            var vecBrands : Vector<BrandDO> = Vector()
            try {
                objSqliteDB = DatabaseHelper.openDataBase()

                var vecClassDetails  = SellingSKUClassificationsDA().getAllSellingSKUClassificationsForStoreCheck(objSqliteDB)
                val classificationDO = StoreCheckClassificationDO().apply {
                    sellingSKUClassificationId = 111
                    code ="100"
                    description ="ALL"
                    sequence = 100
                }
                val classificationDO1 = StoreCheckClassificationDO().apply {
                    sellingSKUClassificationId = 120
                    code ="120"
                    description ="NPD"
                    sequence = 120
                }
                var sellingSKUClassification  = SellingSKUClassificationsDA().getCustomerSellingSKUGroup(customer,objSqliteDB, userCode = customer.userID+"")
                if (vecClassDetails == null)
                    vecClassDetails = Vector()
                vecClassDetails.add(classificationDO)
                vecClassDetails.add(classificationDO1)

                objectArray[0] = vecClassifications
                hmStoreCheckedItems = CaptureInventryDA().getStoreCheckedItemsNew(customer.site+"",categoryId,null,objSqliteDB,isYesOrNo,isAlertNo,storeCheckID)
                objectArray[1] = hmStoreCheckedItems

                val uomQuery =
                    ("Select distinct TUF.ItemCode,TUF.UOM,TUF.Factor,TUF.EAConversion from "
                            + "tblUOMFactor TUF Where TUF.UOM!='SUF' AND TUF.UOM!='MSU' ORDER BY TUF.ItemCode,TUF.EAConversion")
                var cursorsUoms: Cursor? = null
                try {
                    cursorsUoms = objSqliteDB.rawQuery(uomQuery,null)
                    if (cursorsUoms.moveToFirst()){
                        do {
                            val uomConversionFactorDO  = UOMConversionFactorDO().apply {
                                ItemCode = cursorsUoms.getString(0)
                                UOM = cursorsUoms.getString(1)
                                factor = cursorsUoms.getFloat(2)
                                eaConversion = cursorsUoms.getFloat(3)
                            }
                            var  key = uomConversionFactorDO.ItemCode+uomConversionFactorDO.UOM
                            if (hmArrUoms.containsKey(uomConversionFactorDO.ItemCode))
                                vecUOMS = hmArrUoms.get(uomConversionFactorDO.ItemCode)
                            else
                                vecUOMS = Vector<String>()
                            hmTrxUoms =
                                if (hmUomDetails.containsKey(uomConversionFactorDO.ItemCode))
                                    hmUomDetails[uomConversionFactorDO.ItemCode]!!
                                else java.util.HashMap<String, UOMConversionFactorDO>()

                            if ((hmMaxUOM.containsKey(uomConversionFactorDO.ItemCode)
                                        && hmMaxUOM.get(uomConversionFactorDO.ItemCode)?.eaConversion!! < uomConversionFactorDO.eaConversion)
                                || !hmMaxUOM.containsKey(uomConversionFactorDO.ItemCode)
                            ) hmMaxUOM.put(uomConversionFactorDO.ItemCode, uomConversionFactorDO)

                            if ((hmMinUOM.containsKey(uomConversionFactorDO.ItemCode) && hmMinUOM.get(
                                    uomConversionFactorDO.ItemCode
                                )?.eaConversion!! > uomConversionFactorDO.eaConversion) || !hmMinUOM.containsKey(
                                    uomConversionFactorDO.ItemCode
                                )
                            ) hmMinUOM.put(uomConversionFactorDO.ItemCode, uomConversionFactorDO)

                            vecUOMS!!.add(uomConversionFactorDO.UOM)
                            hmArrUoms[uomConversionFactorDO.ItemCode] = vecUOMS!!
                            hmTrxUoms[key] = uomConversionFactorDO
                            hmUomDetails[uomConversionFactorDO.ItemCode] = hmTrxUoms

                        }while (cursorsUoms.moveToNext())
                    }


                }catch (e : Exception){
                    e.printStackTrace()
                }finally {
                    cursorsUoms?.close()
                }

                var arrDetails = CaptureInventryDA().getPreviousSalesDataForStoreCheck(customer.site+"",objSqliteDB)
                for (objSellingSKU_ in vecClassDetails){
                    var  objSellingSKU : StoreCheckClassificationDO = objSellingSKU_
                    var name = ""
                    var code = ""
                    if (sellingSKUClassification!=null){
                        name =sellingSKUClassification.name+""
                        code =sellingSKUClassification.code+""
                    }
                    var  vecAddedItems: Vector<String> = Vector()
                    var hashProductbyBrand = getBrandDetailsByCategory(objSellingSKU,customer,categoryId,hmStoreCheckedItems,vecTotalDistinctItesmInStore,name,code
                    ,hmUomDetails,hmArrUoms,objSqliteDB,hmMinUOM,hmMaxUOM,isFrozen,arrDetails,vecMSLItems,vecFocusItems,vecNewItems,vecAllProducts,vecAllItemCodes
                    ,isSCSubBrandgroup,hashMapNPD,vecAddedItems)

                    if (hashProductbyBrand != null && hashProductbyBrand.size>0){
                        objSellingSKU.hmProducts = hashProductbyBrand
                        if (objSellingSKU.description.equals("ALL", ignoreCase = true)
                            || objSellingSKU.description.equals("New", ignoreCase = true)
                            || objSellingSKU.description.equals("MSL", ignoreCase = true)
                        ) vecClassifications.add(objSellingSKU)

                        if (objSellingSKU.description.equals("ALL", ignoreCase = true) || objSellingSKU.description.equals(
                                "MSL", ignoreCase = true
                            )
                        ) {
                            for (brandDO in hashProductbyBrand.keys) {
                                vecBrands.add(brandDO)
                                //									Collections.sort(hashProductbyBrand.get(brandDO), new OrderComparator());
                            }

//								BrandDO brandDO = new BrandDO();
//								brandDO.brandId = "ALL";
//								brandDO.brandName = "All Brands";
//								vecBrands.add(0,brandDO);
                        }
                    }
                }

                val allDO = StoreCheckClassificationDO()
                allDO.code = "ALL"
                allDO.description = "ALL"
                val arrAllProducts = java.util.ArrayList<ProductDO>()
                for (storeCheckClassificationDO in vecClassifications) {
                    if (storeCheckClassificationDO.description.equals("ALL", ignoreCase = true)) {
                        storeCheckClassificationDO.code = "Other"
                        storeCheckClassificationDO.description = "Other"
                    }
                    if (storeCheckClassificationDO.hmProducts != null && storeCheckClassificationDO.hmProducts.size > 0) {
                        for ((brand,products) in storeCheckClassificationDO.hmProducts){
                            arrAllProducts.addAll(products)
                        }
                    }
                }
                if (arrAllProducts.size > 0) {
                    val objBrand = BrandDO()
                    objBrand.brandId = allDO.description
                    objBrand.brandName = "Al Rashed Food Products" //cursor.getString(1);
                    allDO.hmProducts[objBrand] = arrAllProducts
                    vecClassifications.add(0, allDO)
                }


            }catch (e : Exception){
                e.printStackTrace()
            }finally {
                if (vecTotalDistinctItesmInStore != null)
                    objectArray[2] = vecTotalDistinctItesmInStore.size
                objectArray[3] = vecBrands
                objectArray[4] = vecAllProducts
                objectArray[5] = vecAllItemCodes
            }
             return@withContext objectArray
        }
    }
     fun getBrandDetailsByCategory(
        objSellingSKU: StoreCheckClassificationDO,
        mallsDetails: Customer,
        categoryId: String,
        hmStoreCheckedItems: HashMap<String, ProductDO>,
        vecItems: Vector<String>,
        name: String,
        code: String,
        hmUomDetails: HashMap<String, HashMap<String, UOMConversionFactorDO>>,
        hmArrUoms: HashMap<String, Vector<String>>,
        database1: SQLiteDatabase,
        hmMinUOM: HashMap<String, UOMConversionFactorDO>,
        hmMaxUOM: HashMap<String, UOMConversionFactorDO>,
        isFrozen: Boolean,
        arrDetails: ArrayList<String>,
        vecMSLItems: Vector<String>?,
        vecFocusItems: Vector<String>?,
        vecNewItems: Vector<String>?,
        vecAllProducts: ArrayList<ProductDO>,
        vecAllItemCodes: Vector<String>,
        isSCSubBrandGroup: Boolean,
        hashMapNPD: HashMap<String, ProductDO>,
        vecAddedItems: Vector<String>
    ): LinkedHashMap<BrandDO, ArrayList<ProductDO>> {
        val hashPro = linkedMapOf<BrandDO, ArrayList<ProductDO>>()
        val hashMap = mutableMapOf<String, BrandDO>()
        var database =database1
        try {
            if (!database.isOpen) {
                database = DatabaseHelper.openDataBase()
            }

            // Build your query here based on the provided conditions and parameters
            var subQuery = ""

            if (!TextUtils.isEmpty(subQuery)) {
                subQuery = "AND (TC.CategoryId = '$categoryId' OR TC.CategoryName= '$categoryId') "
            }
            val query = buildQueryForBrandDetails(
                isSCSubBrandGroup,mallsDetails,objSellingSKU, subQuery,name, code)

            val cursor = database.rawQuery(query, null)
            cursor.use {
                if (cursor.moveToFirst()) {
                    processCursorData(
                        cursor, vecAddedItems, hashPro, hashMap, vecMSLItems, vecFocusItems, vecNewItems,
                        arrDetails, isSCSubBrandGroup, hashMapNPD, objSellingSKU.description,isSCSubBrandGroup
                        ,hmArrUoms,hmUomDetails,hmStoreCheckedItems,hmMaxUOM,hmMinUOM,isFrozen,vecItems,objSellingSKU
                        ,vecAllProducts,vecAllItemCodes
                    )
                }
            }
        } catch (e: Exception) {
            Log.e("DatabaseError", "Error fetching brand details", e)
        }

        return hashPro
    }
    fun buildQueryForBrandDetails(
        isSCSubBrandgroup: Boolean,
        mallsDetails: Customer,
        objSellingSKU: StoreCheckClassificationDO,
        subQuery: String,
        name: String = "",
        code: String = ""
    ): String {
        return if (isSCSubBrandgroup) {
            """
            SELECT DISTINCT TB.BrandId, TB.BrandName, TB.ParentCode, TP.ItemCode, TP.Description,
            TC.CategoryId, TC.CategoryName, TP.UnitBarCode, TP.Description2, 
            IFNULL(C.Sequence, '1000') displaySequence, IFNULL(C.Description, 'ALL') AS classification,
            CASE 
                WHEN IFNULL(ISG.ItemSubgroup_Display, '') = '' THEN TP.Description 
                ELSE TRIM(ISG.ItemSubgroup_Display) 
            END AS SubBrandGroup,
            CASE 
                WHEN IFNULL(S.GroupType, 'SubSubChannel') = 'Customer' COLLATE NOCASE THEN 'Green' 
                ELSE 'BLACK' 
            END AS Color,
            TP.Classifications,
            CASE 
                WHEN TP.Attribute15 = '' THEN 'DFL' 
                ELSE IFNULL(TP.Attribute15, 'DFL') 
            END, 
            TP.ChannelCodes
            FROM tblProducts TP
            INNER JOIN tblBrand TB ON TB.BrandId = TP.BRAND COLLATE NOCASE
            LEFT JOIN tblMaterialGroup TMG ON TP.CategoryLevel1 = TMG.MaterialGroupId
            INNER JOIN tblCategory TC ON TC.CategoryId = TP.Category
            INNER JOIN tblSellingSKU S ON S.ItemCode = TP.ItemCode COLLATE NOCASE
            AND (
                (S.GroupType = 'SubSubChannel' COLLATE NOCASE AND S.GroupCode = '${mallsDetails.subChannelCode}' COLLATE NOCASE) OR
                (S.GroupType = 'Route' COLLATE NOCASE AND S.GroupCode = '${mallsDetails.routeCode}' COLLATE NOCASE) OR
                (S.GroupType = 'Customer' COLLATE NOCASE AND S.GroupCode = '${mallsDetails.site}' COLLATE NOCASE) OR
                (S.GroupType = 'Channel' COLLATE NOCASE AND S.GroupCode = '${mallsDetails.channelCode}' COLLATE NOCASE) OR
                (S.GroupType = 'CustomerGroup' COLLATE NOCASE AND S.GroupCode = '${mallsDetails.customerGroupCode}' COLLATE NOCASE) OR
                (S.GroupType = 'SubChannel' COLLATE NOCASE AND S.GroupCode = '${mallsDetails.subChannelCode}' COLLATE NOCASE) OR
                (S.GroupType = 'CustomerClassification' COLLATE NOCASE AND S.GroupCode = '${mallsDetails.classification}' COLLATE NOCASE) OR
                (S.GroupType = 'Classification' COLLATE NOCASE AND S.GroupCode = '${mallsDetails.classification}' COLLATE NOCASE)
            )
            LEFT OUTER JOIN tblGroupSellingSKUClassification G ON G.SellingSKUId = S.SellingSKUId
            LEFT OUTER JOIN tblSellingSKUClassification C ON G.SellingSKUClassificationId = C.SellingSKUClassificationId
            LEFT JOIN tblItemSubGroups ISG ON TP.ItemCode = ISG.ItemCode COLLATE NOCASE
            AND ISG.GroupType = 'SUBCHANNEL' COLLATE NOCASE
            AND ISG.GroupCode = '${mallsDetails.subChannelCode}' COLLATE NOCASE
            WHERE TP.IsActive != 'False'
            AND (IFNULL(TP.IsDeList, '') = '' OR IFNULL(TP.IsDeList, '') = '0' COLLATE NOCASE)
            AND IFNULL(TP.ParentItemCode, '') != ''
            AND classification = '${objSellingSKU.description}' COLLATE NOCASE
            AND (IFNULL(ISG.IsSTockTakingItem, '') = '' OR IFNULL(ISG.IsSTockTakingItem, '') = '1' OR IFNULL(ISG.IsSTockTakingItem, '') = 'True' COLLATE NOCASE)
            $subQuery
            GROUP BY TP.ItemCode
            ORDER BY SubBrandGroup
        """.trimIndent()
        } else {
            """
            SELECT DISTINCT TB.BrandId, TB.BrandName, TB.ParentCode, TP.ItemCode, TP.Description,
            TC.CategoryId, TC.CategoryName, TP.UnitBarCode, TP.Description2, 
            IFNULL(C.Sequence, '1000') displaySequence, IFNULL(C.Description, 'ALL') AS classification, 
            '' AS SubBrandGroup, '' AS Color, TP.Classifications, 
            CASE 
                WHEN TP.Attribute15 = '' THEN 'DFL' 
                ELSE IFNULL(TP.Attribute15, 'DFL') 
            END, 
            TP.ChannelCodes
            FROM tblProducts TP
            INNER JOIN tblBrand TB ON TB.BrandId = TP.BRAND COLLATE NOCASE
            LEFT JOIN tblMaterialGroup TMG ON TP.CategoryLevel1 = TMG.MaterialGroupId
            INNER JOIN tblCategory TC ON TC.CategoryId = TP.Category
            INNER JOIN tblSellingSKU S ON S.ItemCode = TP.ItemCode
            AND S.GroupType = '$name' COLLATE NOCASE 
            AND S.GroupCode = '$code' COLLATE NOCASE
            LEFT OUTER JOIN tblGroupSellingSKUClassification G ON G.SellingSKUId = S.SellingSKUId
            LEFT OUTER JOIN tblSellingSKUClassification C ON G.SellingSKUClassificationId = C.SellingSKUClassificationId
            WHERE TP.IsActive != 'False'
            AND (IFNULL(TP.IsDeList, '') = '' OR IFNULL(TP.IsDeList, '') = '0' COLLATE NOCASE)
            AND IFNULL(TP.ParentItemCode, '') != ''
            $subQuery
            GROUP BY TP.ItemCode
            ORDER BY displaySequence
        """.trimIndent()
        }
    }

    private fun processCursorData(
        cursor: Cursor,
        vecAddedItems: Vector<String>,
        hashPro: LinkedHashMap<BrandDO, ArrayList<ProductDO>>,
        hashMap: MutableMap<String, BrandDO>,
        vecMSLItems: Vector<String>?,
        vecFocusItems: Vector<String>?,
        vecNewItems: Vector<String>?,
        arrDetails: ArrayList<String>,
        isSCSubBrandGroup: Boolean,
        hashMapNPD: HashMap<String, ProductDO>,
        description: String,isSCSubBrandgroup :Boolean,
        hmArrUoms: HashMap<String, Vector<String>>,
        hmUomDetails: HashMap<String, HashMap<String, UOMConversionFactorDO>>,
        hmStoreCheckedItems: HashMap<String, ProductDO>,
        hmMaxUOM: HashMap<String, UOMConversionFactorDO>,
        hmMinUOM: HashMap<String, UOMConversionFactorDO>,
        isFrozen : Boolean,vecItems: Vector<String>,
        objSellingSKU: StoreCheckClassificationDO,
        vecAllProducts: ArrayList<ProductDO>,
        vecAllItemCodes: Vector<String>
    ) {
        // Process the cursor row by row and populate the hashPro and related collections
        if (cursor.moveToFirst()) {
            var objBrand: BrandDO? = null
            var lastBrandId: String? = null
            do {
                var classification = cursor.getString(10)
                val itemCode = cursor.getString(3)
                if (vecAddedItems.contains(itemCode)) {
                    continue
                } else vecAddedItems.add(itemCode)
                if (classification.equals("ALL", ignoreCase = true))
                    classification = "Other"


                if (hashMap?.keys != null && hashMap.keys.size > 0) {
                    objBrand = hashMap[classification]
                    if (objBrand == null) {
                        lastBrandId = classification
                        if (TextUtils.isEmpty(classification)) {
                            classification = "ALL"
                        }
                        objBrand = BrandDO()
                        objBrand.brandId = classification
                        objBrand.brandName = "Al Rashed Food Products" //cursor.getString(1);
                        objBrand.agency = cursor.getString(2)
                        objBrand.categoryId = cursor.getString(6)
                        objBrand.categoryName = cursor.getString(7)
                        objBrand.parentCode = cursor.getString(13)
                    }
                } else {
                    lastBrandId = classification
                    objBrand = BrandDO()
                    objBrand.brandId = lastBrandId
                    objBrand.brandName = "Al Rashed Food Products" //cursor.getString(1);
                    objBrand.agency = cursor.getString(2)
                    objBrand.categoryId = cursor.getString(6)
                    objBrand.categoryName = cursor.getString(7)
                    objBrand.parentCode = cursor.getString(13)
                }
                objBrand.itemMode		 	 = cursor.getString(14);
                hashMap.put(classification,objBrand);

                var productDO = ProductDO()
                productDO.SKU			 = cursor.getString(3);
                if(isSCSubBrandgroup)
                    productDO.Description	 = cursor.getString(11);
                else
                    productDO.Description	 = cursor.getString(4);
                productDO.itemMode		 	 = cursor.getString(14);
                productDO.CategoryId	 = cursor.getString(5);
                productDO.categoryName	 = cursor.getString(6);
                productDO.UnitBarCode	 = cursor.getString(7);
                productDO.arabicDescription	 = cursor.getString(8);
                productDO.subCategoryName	 = cursor.getString(13);
                productDO.sequence	 = cursor.getInt(9);
                productDO.brand			 = ""+objBrand.brandName;
//						productDO.brandcode		 = objBrand.brandId;
                productDO.brand			 = cursor.getString(1);
                productDO.brandcode		 = cursor.getString(0);

                if (!TextUtils.isEmpty(cursor.getString(12)) && cursor.getString(12)
                        .equals("Green", ignoreCase = true)
                ) productDO.isCustomerMSL = true
                productDO.itemGroupLevel5 = cursor.getString(15);

                productDO.itemFlag			 = 0;
                productDO.itemClassification = 0;

//						productDO.isMustSell = objSellingSKU.Description.equalsIgnoreCase("MSL")||objSellingSKU.Description.equalsIgnoreCase("Must Sell");
                productDO.isMustSell = classification.equals(
                    "MSL",
                    ignoreCase = true
                ) || classification.equals("Must Sell", ignoreCase = true)
                productDO.isHistoryItem = false

                if (arrDetails.contains(productDO.SKU) && !productDO.isMustSell) {
                    productDO.isHistoryItem = true
                    productDO.historyItemStatus = -1
                    productDO.sequence = 500
                }
                if (classification.equals(
                        "Must Sell",
                        ignoreCase = true
                    ) || classification.equals("MSL", ignoreCase = true)
                ) {
                    if (isSCSubBrandgroup) {
                        if (productDO.isCustomerMSL) if (vecMSLItems != null) {
                            vecMSLItems.add(productDO.SKU)
                        }
                    } else if (vecMSLItems != null) {
                        vecMSLItems.add(productDO.SKU)
                    }

                    if (vecFocusItems != null) {
                        if (hashMapNPD != null && hashMapNPD.containsKey(cursor.getString(3)) && !vecFocusItems.contains(
                                productDO.SKU
                            )
                        ) { //180423
                            productDO.isNPDItem = true
                            productDO.sequence1 = 100
                            if (vecNewItems != null) {
                                vecNewItems.add(productDO.SKU)
                            }
                        }
                    }
                } else if (vecMSLItems != null) {
                    if (classification.contains("Focus") && !vecMSLItems.contains(productDO.SKU)) {
                        if (vecFocusItems != null) {
                            vecFocusItems.add(productDO.SKU)
                        }
                    } else if (vecMSLItems != null) {
                        if (vecFocusItems != null) {
                            if (hashMapNPD != null && hashMapNPD.containsKey(cursor.getString(3)) && !vecMSLItems.contains(
                                    productDO.SKU
                                ) && !vecFocusItems.contains(productDO.SKU)
                            ) {
                                productDO.isNPDItem = true
                                productDO.sequence1 = 100
                                if (vecNewItems != null) {
                                    vecNewItems.add(productDO.SKU)
                                }
                            } else {
                                productDO.sequence1 = 200
                            }
                        }
                    }
                }

                if (hmArrUoms.containsKey(productDO.SKU)) productDO.arrUoms =
                    hmArrUoms.get(productDO.SKU)

                if (hmUomDetails.containsKey(productDO.SKU)) productDO.hashArrUoms =
                    hmUomDetails.get(productDO.SKU)

                //==============================End fetching all available UOM's for this item======================
                var tempProductDO: ProductDO? = hmStoreCheckedItems.get(productDO.SKU)
                if (tempProductDO != null){
                    productDO.isCaptured = tempProductDO.isCaptured;

                    productDO.StoreUOM = tempProductDO.StoreUOM;
                    productDO.StoreQTY = tempProductDO.StoreQTY;
                    productDO.ShelfQTY = tempProductDO.ShelfQTY;
                    productDO.totalBSQty = tempProductDO.totalBSQty;
                    productDO.totalStoreQty = tempProductDO.totalStoreQty;
                    var eaConversion = 1
                    var maxUOM = productDO.StoreUOM
                    if (hmMaxUOM.containsKey(productDO.SKU)) {
                        maxUOM = hmMaxUOM.get(productDO.SKU)!!.UOM
                    }
                    if (productDO.hashArrUoms!!.containsKey(productDO.SKU + maxUOM)) {
                        eaConversion =
                            productDO.hashArrUoms!![productDO.SKU + maxUOM]!!.eaConversion.toInt()
                    }
                    if (eaConversion == 0) {
                        eaConversion = 1
                    }
                    var roundedQty = productDO.totalStoreQty / eaConversion
                    val remainder = productDO.totalStoreQty % eaConversion
                    if (StringUtils.roundDoubleNew(
                            (remainder.toDouble()) / eaConversion,
                            AppConstants.NUMBER_OF_DECIMALS_TO_ROUND
                        ) >= 0.5
                    ) {
                        roundedQty += 1
                    }
                    var roundedQtyWastage = productDO.totalBSQty / eaConversion
                    val remainderWastage = productDO.totalBSQty % eaConversion
                    if (StringUtils.roundDoubleNew(
                            (remainderWastage.toDouble()) / eaConversion,
                            AppConstants.NUMBER_OF_DECIMALS_TO_ROUND
                        ) >= 0.5
                    ) {
                        roundedQtyWastage += 1
                    }
                    productDO.totalStoreQtyHighUOM = roundedQty
                    productDO.totalBSQtyHighUOM = roundedQtyWastage

                    productDO.ShelfUOM = tempProductDO.ShelfUOM
                    productDO.reason = tempProductDO.reason
                    if (StringUtils.getInt(productDO.StoreQTY) + StringUtils.getInt(productDO.ShelfQTY) > 0) {
                        productDO.isAvailable = true
                    } else {
                        productDO.isAvailable = false
                    }

                    productDO.isAlreadyCaptured = tempProductDO.isAlreadyCaptured
                    productDO.isCaptureInventoryDone = tempProductDO.isCaptureInventoryDone
                }else{
                    if (hmMinUOM.containsKey(productDO.SKU)) productDO.ShelfUOM =
                        hmMinUOM[productDO.SKU]!!.UOM

                    if (hmMaxUOM.containsKey(productDO.SKU)) productDO.StoreUOM =
                        hmMaxUOM[productDO.SKU]!!.UOM

                    if (isFrozen) {
                        productDO.StoreUOM = ""
                    }
                }
                if (vecItems != null && vecItems.contains(productDO.SKU)) {
                } else productDO.itemClassification = objSellingSKU.sellingSKUClassificationId


//						if(!vecItems.contains(productDO.SKU) && !objSellingSKU.Description.equalsIgnoreCase("ALL"))
                if (!vecItems.contains(productDO.SKU) && !classification.equals(
                        "ALL",
                        ignoreCase = true
                    )
                ) vecItems.add(productDO.SKU)

                var vecProducts = hashPro[objBrand]
                if (vecProducts == null) {
                    vecProducts = java.util.ArrayList()
                    vecProducts.add(productDO)
                    hashPro[objBrand] = vecProducts
                } else vecProducts.add(productDO)

                vecAllProducts.add(productDO)
                vecAllItemCodes.add(productDO.SKU)


            }while (cursor.moveToNext())
        }
    }

}