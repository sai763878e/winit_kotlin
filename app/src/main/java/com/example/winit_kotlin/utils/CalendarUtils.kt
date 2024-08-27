package com.example.winit_kotlin.utils

import com.example.winit_kotlin.common.AppConstants
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class CalendarUtils {


    companion object{
        const val DATE_STD_PATTERN: String = "yyyy-MM-dd"


        const val DATE_STD_PATTERN_1: String = "yyyyMMdd"

        const val DATE_STD_PATTERN_PRINT: String = "dd/MM/yyyy"

        const val DATE_PATTERN: String = "yyyy-MM-dd'T'HH:mm:ss"

        const val DATE_PATTERN_AUDITOR: String = "yyMMddHHmmss"

        const val DATE_STD_PATTERN_MONTH: String = "yyyy-MM"

        const val DATE_STD_PATTERN_DAY: String = "dd"

        const val DATE_STD_PATTERN_CURRENT_MONTH: String = "MM"

        const val DATE_STD_PATTERN_YEAR: String = "yyyy"

        const val DATE_MMMM_YEAR: String = "MMMM - yyyy"

        const val DATE_STD_PATTERN_GET_CUURENT_MONTH: String = "MM"

        const val DATE_STD_PATTERN_GET_CUURENT_YEAR: String = "yyyy"

        const val DATE_STD_PATTERN_FULL: String = "EEEEE, MMM dd, yyyy"

        const val DATE_STD_PATTERN_ENROLL: String = "dd-MMM-yyyy"

        const val DATE_STD_PATTERN_PRINT_HIFEN: String = "dd-MM-yyyy"

        const val DATE_TIME_STD_PATTERN: String = "dd-MM-yyyy HH:mm:ss"

        const val DATE_TIME_STD_PATTERN_One: String = "MM/dd/yyyy HH:mm:ss"

        const val DATE_TIME_STD_PATTERN_On: String = "dd/MM/yy HH:mm"

        const val DATE_TIME_STD_PATTERN_Ons: String = "HH:mm dd/MM/yy"

        const val DATE_STD_PATTERN_PRINT_yy: String = "dd/MM/yy"

        const val TIME_STD_PATTERN: String = "HH:mm"

        const val TIME_STD_PATTERN11: String = "HH:mm a"

        const val inputFormat: String = "M/d/yyyy hh:mm:ss a"

        const val TIME_STD_PATTERN1: String = "HH:mma"

        const val DATE_STD_PATTERN_FLIGHT: String = "yyyy-MM-dd'T'kk:mm:ss"

        const val DATE_DT_PATTERN_SHOT: String = "EEE dd MMM yyyy"

        const  val TIME_PATTERN_FLIGHT_INFO: String = "kk:mm"

        const val TIME_PATTERN_FLIGHT_SEARCH: String = "EEE', 'kk:mm"

        const val TIME_PATTERN_FLIGHT_LIST: String = "dd MMM yyyy"

        const val PRINT_DATE_TIME_PATTERN_TIME: String = "hh:mm"
        //	public  final String DATE_PATTERN_FLIGHT_LIST = "EEE dd MMM yyyy";

        const val DATE_PATTERN_FLIGHT_LIST_NEW: String = "EEE, dd MMM yyyy"

        const val DATE_PATTERN_CURRENT_DATE_LOG: String = "yyyy-MM-dd'T'HH:mm:ss"

       const val WEEK_OF_MONTH: Int = 0

         var monthNames: Array<String> =
            arrayOf("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec")

        const val DATE_TIME_STD_PATTERN_DELIVERY: String = "MM/dd/yyyy"

        const val SYNCH_DATE_TIME_PATTERN: String = "dd/MM/yyyy hh:mm:ss a"

        const val SURVEY_PATTERN: String = "dd-MM-yyyy HH:mm:ss a"

        const val SEC_DATE_PATTERN: String = "yyyy-MM-dd'T'HH:mm:ss.SSS"

        const val dd_MM_yyyy_SEC_PATTERN: String = "dd/MM/yyyy 'at' hh:mm"


        const  val DATE_STD_PATTERN_FULL_NEW: String = "MMM dd, yyyy"


        const val ADD_CUSTOMER_DATE_TIME_PATTERN: String = "MM/dd/yyyy HH:mm:ss a"

        const val yesterday_SEC_DATE_PATTERN: String = "'yesterday at ' HH:mm"


        /** this method returns Current dateofJorney in string form  */

        fun getCurrentDateAsString(): String? {
            var date: String? = null

            val sdf = SimpleDateFormat(
                DATE_PATTERN,
                Locale.ENGLISH
            )
            date = sdf.format(Date())

            return date
        }
        fun getCurrentDate() : String{

            var date : String? = null
            var calendar : Calendar = Calendar.getInstance()
            var year : Int = calendar.get(Calendar.YEAR)
            var month : Int = calendar.get(Calendar.MONTH)
            var day : Int = calendar.get(Calendar.DATE)

            date = "$day  ${getMonthFromNumber(month)}$year"
            return date
        }
        fun getCurrentDateTime() : String{
            var datestr = ""
            var date = Date();
            val sdf = SimpleDateFormat(CalendarUtils.DATE_PATTERN,Locale.ENGLISH)
            return sdf.format(date)
        }


        fun getMonthFromNumber(intMonth : Int): String {
            var strMonth : String?=null
            when(intMonth){
                1 ->  return "Jan"
                2 ->  return "Feb"
                3 ->  return "Mar"
                4 ->  return "Apr"
                5 ->  return "May"
                6 ->  return "Jun"
                7 ->  return "Jul"
                8 ->  return "Agu"
                9 ->  return "Sep"
                10 -> return "Act"
                11 -> return "Nav"
                12 -> return  "Dec"
            }
            return "";

        }
        fun getTargetCurrentYear(): String {
            val sdf = SimpleDateFormat(DATE_STD_PATTERN_YEAR, Locale.ENGLISH)
            return sdf.format(Date())
        }
        fun getTargetCurrentMonth(): String {
            val sdf = SimpleDateFormat(DATE_STD_PATTERN_CURRENT_MONTH, Locale.ENGLISH)
            return sdf.format(Date())
        }

        fun getPrevYearTarget() : String{
            var calendar = Calendar.getInstance()
            calendar.add(Calendar.MONTH,-AppConstants.REC_ORDER_MONTHS)
            val sdf = SimpleDateFormat(DATE_STD_PATTERN_YEAR, Locale.ENGLISH)
            return sdf.format(calendar.timeInMillis)
        }
        fun getOrderPostDate(): String {
            val date = Date()
            val sdf = SimpleDateFormat(DATE_STD_PATTERN, Locale.ENGLISH)
            return sdf.format(date)
        }

        fun getDiffBtwDatesFromCurrentDate(startDate : String) : Int{
            return try {
                var calendar1 = Calendar.getInstance()
                var calendar2 = Calendar.getInstance()

                calendar1.time = CalendarUtils.getDateFromString(startDate, CalendarUtils.DATE_STD_PATTERN)

                var milliseconds1 = calendar1.timeInMillis
                var milliseconds2 = calendar2.timeInMillis
                var diff = milliseconds2 - milliseconds1
                var diffDays = (diff/(24*60*60*1000)).toInt()

                diffDays-1

            }catch (e : Exception){
                e.printStackTrace()
                0
            }
        }

        fun getDateFromString(date : String ,pattern : String) : Date{
            val dateobj = Date()
            val sdf = SimpleDateFormat(pattern,Locale.ENGLISH)
            return try {
                if (!date.isNullOrEmpty())sdf.parse(date) ?:dateobj else dateobj
            }catch (e : Exception){
                e.printStackTrace()
                dateobj
            }


        }
        fun getDateDifferenceInMinutes(dateStart : String ,dateStop : String) : Long{
            var diffMinutes : Long = 0;
            try {
                var calendar1 = Calendar.getInstance()
                var calendar2 = Calendar.getInstance()

                calendar1.time = CalendarUtils.getDateFromString(dateStart, DATE_PATTERN)
                calendar2.time = CalendarUtils.getDateFromString(dateStop, DATE_PATTERN)

                var milliseconds1 =calendar1.timeInMillis
                var milliseconds2 =calendar2.timeInMillis
                var diff = milliseconds2-milliseconds1
                diffMinutes = diff/(60*1000)

            }catch (e : Exception){
                e.printStackTrace()
            }
            return diffMinutes
        }
        fun getCurrentDateAsStringForJourneyPlan() : String{
            var sdf = SimpleDateFormat(DATE_PATTERN,Locale.ENGLISH)
            return sdf.format(Date())
        }

        fun getCurrentDateAsStringforStoreCheck() : String{
            var sdf = SimpleDateFormat(DATE_STD_PATTERN,Locale.ENGLISH)
            return sdf.format(Date())
        }

        fun getDateAsString(date: Date) : String{
            var sdf = SimpleDateFormat(DATE_STD_PATTERN,Locale.ENGLISH)
            return sdf.format(date)
        }

        fun getCurrenetDate(): String {
            val date = Date(System.currentTimeMillis())
            val sdf = SimpleDateFormat(DATE_STD_PATTERN, Locale.ENGLISH)
            return sdf.format(date)
        }
    }


    

    

  
}