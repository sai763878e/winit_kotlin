package com.example.winit_kotlin.dataaccesslayer

import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.text.TextUtils
import com.example.winit_kotlin.common.AppConstants
import com.example.winit_kotlin.common.MyApplication
import com.example.winit_kotlin.databaseaccess.DatabaseHelper
import com.example.winit_kotlin.dataobject.ChannelDO
import com.example.winit_kotlin.dataobject.Customer
import com.example.winit_kotlin.dataobject.MapDO
import com.example.winit_kotlin.utilities.StringUtils
import com.example.winit_kotlin.utils.CalendarUtils

class CustomerDetailsDA {

    fun getJourneyPlanCount(): Int {
        synchronized(MyApplication.APP_DB_LOCK) {
            var objSqliteDB: SQLiteDatabase? = null
            var count = 0
            var c: Cursor? = null
            try {
                objSqliteDB = DatabaseHelper.openDataBase()
                val query = """
                SELECT COUNT(DISTINCT TDP.ClientCode) 
                FROM tblDailyJourneyPlan TDP 
                INNER JOIN tblCustomer C on TDP.ClientCode=C.Site  
                WHERE TDP.IsDeleted != 'true' COLLATE NOCASE 
                AND C.CustomerStatus = 'True' COLLATE NOCASE   
                AND TDP.JourneyDate like '%${CalendarUtils.getCurrentDateAsStringforStoreCheck()}%'
            """.trimIndent()

                c = objSqliteDB.rawQuery(query, null)
                if (c.moveToFirst()) {
                    count = c.getInt(0)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                c?.close()
                objSqliteDB?.close()
            }
            return count
        }
    }

    fun getJourneyPlan(
        todayTimeStamp: Long,
        date: Int,
        day: String?,
        presellerId: String,
        orgcode: String,
        hmCreditLimit: HashMap<String, String>?,
        strDate: String,
        hmVisits: HashMap<String, Int>?
    ): Array<Any?> {
        synchronized(MyApplication.APP_DB_LOCK) {
            var objSqliteDB: SQLiteDatabase? = null
            var c: Cursor? = null
            val hmChannelCodes = mutableMapOf<String, MutableList<String>>()
            val hmCustomerGroupCode = mutableMapOf<String, String>()
            var arrChannelCodes = mutableListOf<String>()
            var arrBlockCustomer = mutableListOf<Customer>()

            val hmChannelList = mutableMapOf<String, Boolean>()
            val hmJPCustomerList = mutableMapOf<String, Boolean>()
            val arJourneyPlan = mutableListOf<Customer>()
            val arrMapLocation = mutableListOf<MapDO>()
            val objectArray = arrayOfNulls<Any?>(8)
            var hasTimeInJourneyPlan = true

            try {
                objSqliteDB = DatabaseHelper.openDataBase()

                val query = if (day.isNullOrEmpty()) {
                    "SELECT DISTINCT '0','0' as lifeCycle,C.id,C.Site,C.SiteName,\n" +
                            "                        C.CustomerId,C.CustomerStatus,C.CustAcctCreationDate,C.PartyName,\n" +
                            "                        C.ChannelCode,C.SubChannelCode,C.RegionCode,C.CountryCode,\n" +
                            "                        C.Category,C.Address1,C.Address2,C.Address3,C.Address4,\n" +
                            "                        C.PoNumber,C.City,C.PaymentType,C.PaymentTermCode,\n" +
                            "                        C.CreditLimit,C.GeoCodeX,C.GeoCodeY,C.PASSCODE,C.Email,\n" +
                            "                        C.ContactPersonName,C.PhoneNumber,C.AppCustomerId,C.MobileNumber1,\n" +
                            "                        C.MobileNumber2,C.Website,C.CustomerType,C.CreatedBy,C.ModifiedBy,\n" +
                            "                        C.Source,C.CustomerCategory,C.CustomerSubCategory,C.CustomerGroupCode,\n" +
                            "                        C.ModifiedDate,C.ModifiedTime,\n" +
                            "                        C.CurrencyCode,C.StoreGrowth,C.PriceList,C.SalesPerson,\n" +
                            "                        C.PaymentMode,C.SalesOrgCode,C.SalesOfficeCode,C.DivisionCode,\n" +
                            "                        C.Attribute1,C.Attribute2,C.Attribute3,C.Attribute4,C.Attribute5,C.Attribute6,C.Attribute7,\n" +
                            "                        C.Attribute8,C.Attribute9,C.Attribute10,C.BlockedStatus,C.StoreGrowthYTD, '','',C.Site as ClientCode,\n" +
                            "                        UT.UserName,C.CreditLimit,C.CreditDays,C.NoofOutstandingInvoices,C.IsAllowCashOnCreditExceed,\n" +
                            "                        C.DistributionKey,C.SubSubChannelCode,C.CustomerArabicName,C.ArabicAddress,C.ParentCode,C.Attribute10,C.Address3 \n" +
                            "                        ,'','','','','','','','','',C.RTV,C.IsNegativeStockAllowed,C.MinimumInvoiceValue,C.MaximumInvoiceValue,C.Attribute14 \n" +
                            "                        ,Classification,DivisionCode,C.Attribute16,C.Attribute17,C.Attribute18,C.Attribute19,C.Attribute20,\n" +
                            "                        C.Attribute21,C.Attribute22,C.Attribute23,C.Attribute24,C.Attribute25,C.VisitFrequency,C.ModeOfDelivery,\n" +
                            "                        C.RouteDay,C.WeekDay,C.ZipCode,C.OutletName,C.VATNo,C.VATExpiryDate,C.CRNo,C.CRExpiryDate,C.MunicipalityNo,\n" +
                            "                        C.MunicipalityExpiryDate,C.IqamaNo,C.IqamaExpiryDate,C.OutletSize,C.NoOfCashier,IFNULL(C.VisitingDays,''),\n" +
                            "                        IFNULL(C.DeliveryDays,''),IFNULL(C.DropsPerWeek,''),C.IsCreditLimitExceeded,IFNULL(C.IsWarehouse,'0') \n" +
//                            "                        IFNULL(C.FTI_Level4,''),IFNULL(C.FTI_Level5,''),IFNULL(C.FTI_Level6,'')\n" +
//                            "                        ,Address_StreetName,Address_AdditionalStreetName,Address_AdditionalStreet_Arabic,Address_BuildingNumber,Address_CityName\n" +
//                            "                        ,Address_COuntrySubdivisionName,Address_COuntrySubdivisionName_Arabic,Address_PlotIdentification,Address_District,Address_District_Arabic \n" +
                            "                        FROM tblCustomer C \n" +
                            "                        LEFT JOIN tblUsers UT ON UT.UserCode = C.SalesPerson \n" +
                            "                        WHERE C.CustomerStatus != 'False' COLLATE NOCASE AND C.Site != '$presellerId' COLLATE NOCASE AND C.PaymentType IN ('${AppConstants.CUSTOMER_TYPE_CREDIT}','${AppConstants.CUSTOMER_TYPE_CASH}') AND C.IsDeleted != 'true' COLLATE NOCASE"
                } else {
                    "SELECT DISTINCT RC.Sequence as Sequence,'0' as lifeCycle,C.id,C.Site,C.SiteName,\n" +
                            "                        C.CustomerId,C.CustomerStatus,C.CustAcctCreationDate,C.PartyName,\n" +
                            "                        C.ChannelCode,C.SubChannelCode,C.RegionCode,C.CountryCode,\n" +
                            "                        C.Category,C.Address1,C.Address2,C.Address3,C.Address4,\n" +
                            "                        C.PoNumber,C.City,C.PaymentType,C.PaymentTermCode,\n" +
                            "                        C.CreditLimit,C.GeoCodeX,C.GeoCodeY,C.PASSCODE,C.Email,\n" +
                            "                        C.ContactPersonName,C.PhoneNumber,C.AppCustomerId,C.MobileNumber1,\n" +
                            "                        C.MobileNumber2,C.Website,C.CustomerType,C.CreatedBy,C.ModifiedBy,\n" +
                            "                        C.Source,C.CustomerCategory,C.CustomerSubCategory,C.CustomerGroupCode,\n" +
                            "                        C.ModifiedDate,C.ModifiedTime,\n" +
                            "                        C.CurrencyCode,C.StoreGrowth,C.PriceList,C.SalesPerson,\n" +
                            "                        C.PaymentMode,C.SalesOrgCode,C.SalesOfficeCode,C.DivisionCode,\n" +
                            "                        C.Attribute1,C.Attribute2,C.Attribute3,C.Attribute4,C.Attribute5,C.Attribute6,C.Attribute7,\n" +
                            "                        C.Attribute8,C.Attribute9,C.Attribute10,C.BlockedStatus,C.StoreGrowthYTD, RC.EndTime as TimeOut,\n" +
                            "                        RC.StartTime as TimeIn,C.Site as ClientCode,UT.UserName,C.CreditLimit,C.CreditDays,C.NoofOutstandingInvoices,\n" +
                            "                        C.IsAllowCashOnCreditExceed,C.DistributionKey,C.SubSubChannelCode,C.CustomerArabicName,C.ArabicAddress,C.ParentCode,\n" +
                            "                        C.Attribute10,C.Address3 \n" +
                            "                        ,RC.IsGreen,RC.TargetValue,RC.TargetVolume,RC.TargetLines,RC.ActualValue,RC.ActualVolume,RC.ActualLines,RC.JPStatus,RC.JourneyPlanId ,C.RTV,C.IsNegativeStockAllowed,C.MinimumInvoiceValue,C.MaximumInvoiceValue,C.Attribute14\n" +
                            "                        ,Classification,DivisionCode,C.Attribute16,C.Attribute17,C.Attribute18,C.Attribute19,C.Attribute20,\n" +
                            "                        C.Attribute21,C.Attribute22,C.Attribute23,C.Attribute24,C.Attribute25,C.VisitFrequency,C.ModeOfDelivery,\n" +
                            "                        C.RouteDay,C.WeekDay,C.ZipCode,C.OutletName,C.VATNo,C.VATExpiryDate,C.CRNo,C.CRExpiryDate,C.MunicipalityNo,\n" +
                            "                        C.MunicipalityExpiryDate,C.IqamaNo,C.IqamaExpiryDate,C.OutletSize,C.NoOfCashier,IFNULL(C.VisitingDays,''),\n" +
                            "                        IFNULL(C.DeliveryDays,''),IFNULL(C.DropsPerWeek,''),C.IsCreditLimitExceeded,IFNULL(C.IsWarehouse,'0') \n" +
//                            "                        IFNULL(C.FTI_Level4,''),IFNULL(C.FTI_Level5,''),IFNULL(C.FTI_Level6,'')\n" +
//                            "                        ,Address_StreetName,Address_AdditionalStreetName,Address_AdditionalStreet_Arabic,Address_BuildingNumber,Address_CityName\n" +
//                            "                        ,Address_COuntrySubdivisionName,Address_COuntrySubdivisionName_Arabic,Address_PlotIdentification,Address_District,Address_District_Arabic \n" +
                            "                        FROM tblCustomer C \n" +
                            "                        INNER JOIN tblDailyJourneyPlan RC ON C.Site = RC.ClientCode \n" +
                            "                        LEFT JOIN tblUsers UT ON UT.UserCode = C.SalesPerson \n" +
                            "                        WHERE C.CustomerStatus != 'False' COLLATE NOCASE AND C.Site != '$presellerId' COLLATE NOCASE AND C.PaymentType IN ('${AppConstants.CUSTOMER_TYPE_CREDIT}','${AppConstants.CUSTOMER_TYPE_CASH}') AND C.IsDeleted != 'true' COLLATE NOCASE AND RC.IsDeleted != 'true' COLLATE NOCASE AND JourneyDate LIKE '%$strDate%' Order by Sequence"
                }

                c = objSqliteDB.rawQuery(query, null)
                if (c.moveToFirst()) {
                    do {


                        var j = Customer().apply {
                            sequence = c.getInt(0)
                            lifeCycle = c.getString(1)
                            id = c.getString(2)
                            site = c.getString(3)
                            SiteName = c.getString(4).trim()
                            CustomerId = c.getString(5)
                            CustomerStatus = c.getString(6)
                            CustAcctCreationDate = c.getString(7)
                            partyName = c.getString(8)
                            channelCode = c.getString(9)
                            subChannelCode = c.getString(10)
                            regionCode = c.getString(11)
                            countryCode = c.getString(12)
                            category = c.getString(13)
                            address1 = c.getString(14)
                            address2 = c.getString(15)
                            address3 = c.getString(16)
                            address4 = c.getString(17)
                            poNumber = c.getString(18)
                            city = c.getString(19)
                            paymentType = c.getString(20)
                            paymentTermCode = c.getString(21)
                            creditLimit = StringUtils.getFloat(c.getString(22))
                            geoCodeX = c.getDouble(23)
                            geoCodeY = c.getDouble(24)


                            PASSCODE = c.getString(25)
                            email = c.getString(26)
                            contactPersonName = c.getString(27).let {
                                if (it.isEmpty()) "N/A" else it
                            }
                            phoneNumber = c.getString(28)
                            appCustomerId = c.getString(29)
                            mobileNumber1 = c.getString(30)
                            mobileNumber2 = c.getString(31)
                            website = c.getString(32)
                            customerType = c.getString(33)
                            createdBy = c.getString(34)
                            modifiedBy = c.getString(35)
                            source = c.getString(36)
                            customerCategory = c.getString(37)
                            customerSubCategory = c.getString(38)
                            customerGroupCode = c.getString(39)
                            modifiedDate = c.getString(40)
                            modifiedTime = c.getString(41)
                            currencyCode = c.getString(42)
                            storeGrowth = c.getString(43)
                            priceList = c.getString(44)
                            salesPerson = c.getString(45)
                            paymentMode = c.getString(46)

                            salesOrgCode = c.getString(47)
                            salesOfficeCode = c.getString(48)
                            divisionCode = c.getString(49)
                            Attribute1 = c.getString(50)
                            Attribute2 = c.getString(51)
                            Attribute3 = c.getString(52)
                            Attribute4 = c.getString(53)
                            Attribute5 = c.getString(54)
                            Attribute6 = c.getString(55)
                            Attribute7 = c.getString(56)
                            Attribute8 = c.getString(57)
                            Attribute9 = c.getString(58)
                            Attribute10 = c.getString(59)

                            blockedStatus = c.getString(60)
                            storeGrowthYTD = c.getString(61)
                            timeOut = c.getString(62);
                            timeIn = c.getString(63);
                            clientCode = c.getString(64)
                            salesPerson = c.getString(65)
                            if (hmCreditLimit != null) {
                                customerCreditLimit =
                                    "" + (if (hmCreditLimit.containsKey(site)) hmCreditLimit[site] else 0)
                            };
                            creditLimit = StringUtils.getFloat(customerCreditLimit)
                            creditDays = c.getInt(67)
                            if (timeIn == null || timeIn.equals("" ,ignoreCase = true)) hasTimeInJourneyPlan =
                                false
                            userID = presellerId;
                            dateOfJourny = day;

                            noofOutstandingInvoices = c.getInt(68)
                            if (!c.getString(67).isNullOrEmpty())
                                isAllowCashOnCreditExceed = if (c.getString(69).equals("true",ignoreCase = true)) true else false

                            distributionKey = c.getString(70)
                            subSubChannelCode = c.getString(71)
                            customerArabicName = c.getString(72)
                            arabicAddress = c.getString(73)
                            parentCode = c.getString(74)
                            parentName = c.getString(75)
                            arabicParentName = c.getString(76)

                            printPaymentType ="CASH NORMAL";
                            if (!TextUtils.isEmpty(customerType) && customerType.equals("Cash",ignoreCase = true))
                                printPaymentType = "CASH NORMAL"
                            else if (!TextUtils.isEmpty(customerType) && customerType.equals("General Credit",ignoreCase = true))
                                printPaymentType = "CASH CHECK GC"
                            else if (!TextUtils.isEmpty(customerType) && customerType.equals("Temporary Credit",ignoreCase = true))
                                printPaymentType = "CASH CHECK TC"
                            //						j.callHistoryDO=hashMap.get(j.site)!=null?hashMap.get(j.site):null;

                            IsGreen =c.getInt(77);

                            TargetValue = if (!c.getString(78).isNullOrEmpty()) c.getString(78) else "500"
                            TargetVolume = if (!c.getString(79).isNullOrEmpty()) c.getString(79) else "0"
                            TargetLines = if (!c.getString(80).isNullOrEmpty()) c.getString(80) else "4"
                            if (subSubChannelCode.isNullOrEmpty())
                                subSubChannelCode = "5"

                            ActualValue = c.getString(81)
                            ActualVolume = c.getString(82)
                            ActualLines = c.getString(83)
                            JPStatus = c.getString(84)
                            JourneyPlanId=c.getString(85);
                            RTV=c.getInt(86);
                            isNegativeStockAllowed = StringUtils.getBoolean(c.getString(87))
                            minimumInvoicevarue = c.getDouble(88)
                            maximumInvoicevarue = c.getDouble(89)
                            Attribute14 = c.getString(90)
                            classification = c.getString(91)
                            divisionCode = c.getString(92)
                            Attribute16 = c.getString(93)
                            Attribute17 = c.getString(94)
                            Attribute18 = c.getString(95)
                            Attribute19 = c.getString(96)
                            Attribute20 = c.getString(97)
                            Attribute21 = c.getString(98)
                            Attribute22 = c.getString(99)
                            Attribute23 = c.getString(100)
                            Attribute24 = c.getString(101)
                            Attribute25 = c.getString(102)
                            visitFrequency = c.getString(103)
                            modeOfDelivery = c.getString(104)
                            routeDay = c.getString(105)
                            weekDay = c.getString(106)
                            zipCode = c.getString(107)
                            outletName = c.getString(108)
                            VATNo = c.getString(109)
                            VATExpiryDate = c.getString(110)
                            CRNo = c.getString(111)
                            CRExpiryDate = c.getString(112)
                            municipalityNo = c.getString(113)
                            municipalityExpiryDate = c.getString(114)
                            iqamaNo = c.getString(115)
                            iqamaExpiryDate = c.getString(116)
                            outletSize = c.getInt(117)
                            noOfCashier = c.getInt(118)
                            visitingDays = c.getString(119)
                            deliveryDays = c.getString(120)
                            dropsPerWeek = c.getString(121)
                            isCreditLimitExceeded = StringUtils.getInt(c.getString(122))
                            isWarehouse = StringUtils.getInt(c.getString(123))
//                            FTI_Level4 = c.getString(124)
//                            FTI_Level5 = c.getString(125)
//                            FTI_Level6 = c.getString(126)
//                            address_StreetName = c.getString(127)
//                            address_AdditionalStreetName = c.getString(116)
//                            address_AdditionalStreet_Arabic = c.getString(117)
//                            address_BuildingNumber = c.getString(118)
//                            address_CityName = c.getString(119)
//                            address_CountrySubdivisionName = c.getString(120)
//                            address_CountrySubdivisionName_Arabic = c.getString(121)
//                            address_PlotIdentification = c.getString(122)
//                            address_District = c.getString(123)
//                            address_District_Arabic = c.getString(124)
//                            isGreen = c.getString(125)
//                            targetValue = c.getString(126)
//                            targetVolume = c.getString(127)
//                            targetLines = c.getString(128)
//                            actualValue = c.getString(129)
//                            actualVolume = c.getString(130)
//                            actualLines = c.getString(131)
//                            jpStatus = c.getString(132)
//                            journeyPlanId = c.getString(133)
                        }

                        if(!hmChannelCodes.containsKey(j.site))
                            arrChannelCodes = mutableListOf<String>();
                        else
                            arrChannelCodes = hmChannelCodes.get(j.site)!!;

                        if (!hmChannelCodes.containsKey(j.site))
                            arJourneyPlan.add(j)
                        val channelKey = j.site + j.channelCode
                        var channelDO = ChannelDO().apply {
                            code = j.channelCode;
                            isBloacked = StringUtils.getBoolean(j.blockedStatus);
                        }
                        if (channelDO.isBloacked)
                            arrBlockCustomer.add(j)

                        arJourneyPlan.add(j)
                        var mapDO = MapDO().apply {
                            site = j.site
                            siteName = j.SiteName
                            geoCodeX = j.geoCodeX
                            geoCodeY = j.geoCodeY
                            sequence = j.sequence
                            if (hmVisits != null) {
                                if (!hmVisits.isEmpty() && hmVisits.containsKey(site))
                                    isVisited = true
                                else
                                    isVisited = false
                            }
                            isJP = true
                        }
                        arrMapLocation.add(mapDO)
                        hmJPCustomerList[j.site+""] = true
                        arrChannelCodes.add(j.channelCode+"")
                        hmChannelCodes[j.site+""] = arrChannelCodes
                        hmCustomerGroupCode[j.site + j.channelCode] = j.Attribute8+""
                        hmChannelList[channelKey] = StringUtils.getBoolean(j.blockedStatus)

                        //						j.FTI_Level4   = c.getString(124);
//						j.FTI_Level5   = c.getString(125);
//						j.FTI_Level6   = c.getString(126);
//
//						//added by sai
//						j.Address_StreetName = c.getString(127);
//						j.Address_AdditionalStreetName = c.getString(128);
//						j.Address_AdditionalStreetName_Arabic = c.getString(129);
//						j.Address_BuildingNumber = c.getString(130);
//						j.Address_CityName = c.getString(131);
//						j.Address_CountrySubdivisionName = c.getString(132);
//						j.Address_CountrySubdivisionName_Arabic = c.getString(133);
//						j.Address_PlotIdentification = c.getString(134);
//						j.Address_District = c.getString(135);
//						j.Address_District_Arabic = c.getString(136);


                    } while (c.moveToNext())
                }

                objectArray[0] = arJourneyPlan
                objectArray[1] = hasTimeInJourneyPlan
                objectArray[2] = hmChannelCodes
                objectArray[3] = hmCustomerGroupCode
                objectArray[4] = hmChannelList
                objectArray[5] = arrMapLocation
                objectArray[6] = arrBlockCustomer
                objectArray[7] = hmJPCustomerList


            } catch (e: Exception) {
                e.printStackTrace()
            }finally {
                c?.close()
                objSqliteDB?.close()
            }

            return objectArray
        }
    }

}
