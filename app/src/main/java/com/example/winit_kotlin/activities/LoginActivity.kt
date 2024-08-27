package com.example.winit_kotlin.activities

import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBar.LayoutParams
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.winit_kotlin.R
import com.example.winit_kotlin.common.AppConstants
import com.example.winit_kotlin.common.Preference
import com.example.winit_kotlin.dataaccesslayer.ClearDataDA
import com.example.winit_kotlin.dataaccesslayer.CommonDA
import com.example.winit_kotlin.dataaccesslayer.CustomerDA
import com.example.winit_kotlin.dataaccesslayer.CustomerDetailsDA
import com.example.winit_kotlin.dataaccesslayer.JourneyPlanDA
import com.example.winit_kotlin.dataaccesslayer.MasterDA
import com.example.winit_kotlin.dataaccesslayer.SettingsOrgDA
import com.example.winit_kotlin.dataaccesslayer.SynLogDA
import com.example.winit_kotlin.dataaccesslayer.TransactionsLogsDA
import com.example.winit_kotlin.dataaccesslayer.UserInfoDA
import com.example.winit_kotlin.databinding.ActivityLoginBinding
import com.example.winit_kotlin.databinding.CustomBuilderCellBinding
import com.example.winit_kotlin.dataobject.SettingsDO
import com.example.winit_kotlin.dataobject.SynLogDO
import com.example.winit_kotlin.dataobject.TrxLogHeaders
import com.example.winit_kotlin.dataobject.getEOTDateLatest
import com.example.winit_kotlin.dataobject.getEOTStatusForTodaysDate
import com.example.winit_kotlin.dataobject.getEOTStatusLatest
import com.example.winit_kotlin.interfaces.OnDialogListeners
import com.example.winit_kotlin.interfaces.VersionChangeListner
import com.example.winit_kotlin.parsers.BlaseUserDco
import com.example.winit_kotlin.parsers.BlaseUserResponseHandler
import com.example.winit_kotlin.parsers.CheckVersionDO
import com.example.winit_kotlin.parsers.DefaultHandler
import com.example.winit_kotlin.utilities.CustomDialog
import com.example.winit_kotlin.utilities.FileUtils
import com.example.winit_kotlin.utilities.FormatUtils
import com.example.winit_kotlin.utilities.StringUtils
import com.example.winit_kotlin.utils.CalendarUtils
import com.example.winit_kotlin.utils.LoaderUtils
import com.example.winit_kotlin.utils.getTripDateType
import com.example.winit_kotlin.utils.getUniqueID
import com.example.winit_kotlin.utils.getVersionName
import com.example.winit_kotlin.utils.hideKeyboard
import com.example.winit_kotlin.utils.hideLoader
import com.example.winit_kotlin.utils.isJourneyCurrentlyRunning
import com.example.winit_kotlin.utils.isNetworkConnectionAvailable
import com.example.winit_kotlin.utils.showCustomDialog
import com.example.winit_kotlin.utils.showLoader
import com.example.winit_kotlin.viewmodel.SyncViewModel
import com.example.winit_kotlin.webAccessLayer.BuildXMLRequest
import com.example.winit_kotlin.webAccessLayer.ConnectionHelper
import com.example.winit_kotlin.webAccessLayer.ServiceURLs
import com.example.winit_kotlin.workmanagers.UploadWorker
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.IOException
import java.util.Vector
import java.util.concurrent.TimeUnit

class LoginActivity : AppCompatActivity(), ConnectionHelper.ConnectionExceptionListener,OnDialogListeners,FileUtils.DownloadListner {
    private lateinit var binding : ActivityLoginBinding
    private lateinit var strusername : String
    private lateinit var strPassword : String
    private lateinit var preference: Preference

    private lateinit  var connectionHelper : ConnectionHelper
    private lateinit var gcmId : String
    private lateinit var onDialogListeners: OnDialogListeners
    private var isJourneyCurrentlyRunning : Boolean = false
    private var isDayStartedNew : Boolean = false
    private var isEOTDoneNew : Boolean = false
    private var isCanStartDayNew : Boolean = false
    private var isShortRoute : Boolean = false
    private var isPreviousDayEOTDone : Boolean = false
    private var isEOTDoneForTodaysDate : Boolean = false
    private var isEOTdoneForTodays : Boolean = false
    private lateinit var syncViewModel: SyncViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        preference = Preference(this)


        binding.btnLogin.setOnClickListener {

            hideKeyboard()

            strusername = binding.etUserId.text.toString()
            strPassword = binding.etPassword.text.toString()

            if (strusername.isBlank() && strPassword.isBlank()){
                binding.etUserId.setError(getString(R.string.Username_connot_be_empty))
                binding.etPassword.setError(getString(R.string.Password_connot_be_empty))
            } else if (strusername.isBlank()){
                binding.etUserId.setError(getString(R.string.Username_connot_be_empty))
            }else if (strPassword.isBlank()){
                binding.etPassword.setError(getString(R.string.Password_connot_be_empty))
            }else if (!isNetworkConnectionAvailable(this)){
                allowLoginWhenNoResponseinOfflinemode()
            }else{
                validateUser(strusername,strPassword)
            }
        }

        syncViewModel = ViewModelProvider(this).get(SyncViewModel::class.java)

        // Observe sync errors
        syncViewModel.syncError.observe(this, { errorMessage ->
            errorMessage?.let {
                // Show alert dialog


                // Clear the error after showing the alert
                syncViewModel.clearSyncError()
            }
        })

    }
    private var listUsersInfo : List<BlaseUserDco>? = null
    private  var blaseUserDcos : BlaseUserDco? = null
    private fun validateUser(strusername : String,strPassword : String){
        showLoader(this,getString(R.string.validating))
        connectionHelper = ConnectionHelper(this)
        gcmId = preference.getStringFromPreference(Preference.GCM_ID, "");
        Thread{
            var blaseUserResponseHandler = BlaseUserResponseHandler()
            connectionHelper.sendRequestBulk(this,BuildXMLRequest.loginRequest(strusername,strPassword,gcmId,getUniqueID(),getVersionName()),blaseUserResponseHandler,
                ServiceURLs.LOGIN_METHOD,preference)



            if (!TextUtils.isEmpty(blaseUserResponseHandler.checkLoginResponse.checkLoginResult?.status) &&
                !blaseUserResponseHandler.checkLoginResponse.checkLoginResult?.status.equals("Failure")){
                listUsersInfo = blaseUserResponseHandler.getUserInfo()
                LoaderUtils.hideLoader()
                blaseUserResponseHandler.checkLoginResponse.saveToPreferences(this)
                blaseUserDcos = null;
                if (listUsersInfo != null && listUsersInfo!!.size>0){
                    if (listUsersInfo!!.size>1){
                        showUsersPopUp(listUsersInfo!!)
                    }else{
                        afterSuccessfullLogin();
                    }
                }

            }else if (blaseUserResponseHandler.checkLoginResponse.checkLoginResult?.status == null){
               LoaderUtils.hideLoader()

                showCustomDialog(
                    this,preference,
                    getString(R.string.warning),
                    "Login successfully",
                    getString(R.string.OK),
                    null,
                    "", this
                )
            }else{
                LoaderUtils.hideLoader()
//                if (preference.getStringFromPreference(Preference.HTTP_RESPONSE_METHOD, "")
//                        .equalsIgnoreCase(ServiceURLs.SOAPAction + ServiceURLs.LOGIN_METHOD)
//                    && preference.getIntFromPreference(
//                        Preference.HTTP_RESPONSE_CODE,
//                        0
//                    ) !== HttpURLConnection.HTTP_OK
//                ) {
//                    allowLoginWhenNoResponseinOfflinemode()
//                } else {
                    showCustomDialog(
                        this,preference,
                        getString(R.string.warning),
                        blaseUserResponseHandler.checkLoginResponse.checkLoginResult?.message.toString(),
                        getString(R.string.OK),
                        null,
                        "Raja", this
                    )
//                }

            }


        }.start()
    }

    fun showUsersPopUp(blaseUsers  :List<BlaseUserDco>){
        val inflater = LayoutInflater.from(this)
        var view : View = inflater.inflate(R.layout.login_user_popup,null)
        var customDialog = CustomDialog(this,view,preference.getIntFromPreference(AppConstants.Device_Display_Width, AppConstants.DEVICE_DISPLAY_WIDTH_DEFAULT)-150,LayoutParams.WRAP_CONTENT,false)

        var lvSelectUser = view.findViewById<View>(R.id.lvSelectUser) as ListView
        var btnOkPopup = view.findViewById<View>(R.id.btnOkPopup) as Button
        var btnCancelPopup = view.findViewById<View>(R.id.btnCancelPopup) as Button


        val adapter = LoginUserAdapter(blaseUsers, layoutInflater)
        lvSelectUser.adapter = adapter

    }

    override fun onConnectionException(msg: Any) {

    }

    override fun onButtonYesClick(from: String) {
        if (from.equals("raja",ignoreCase = true))
            Toast.makeText(this, "??", Toast.LENGTH_SHORT).show()
    }

    override fun onButtonYesClick(from: String, any: Any) {

    }

    override fun onButtonNoClick(from: String) {

    }

    override fun onButtonNoClick(from: String, any: Any) {
    }


    class LoginUserAdapter(private  var vecUserInfo : List<BlaseUserDco>,private var inflater: LayoutInflater) : BaseAdapter() {
        private var selecteduserinfo : BlaseUserDco? = null

        fun refresh(vecUserInfo: List<BlaseUserDco>) {
            this.vecUserInfo = vecUserInfo
            notifyDataSetChanged()
        }
        override fun getCount(): Int {
            return vecUserInfo.size
        }

        override fun getItem(position: Int): Any {
            return position
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getView(position: Int, convertView: View?, p2: ViewGroup?): View {

            val binding : CustomBuilderCellBinding
            val view : View

            if (convertView == null) {
                binding = CustomBuilderCellBinding.inflate(inflater,p2,false)
                view=binding.root
                view.tag=binding
            }else{
                view = convertView
                binding = view.tag as CustomBuilderCellBinding
            }

            var blaseUserDco = vecUserInfo[position]

            binding.tvSelectName.text = blaseUserDco.username
            if (blaseUserDco.isselected == true) {
                selecteduserinfo = blaseUserDco
                binding.ivSelected.setBackgroundResource(R.drawable.rbtn)
            } else binding.ivSelected.setBackgroundResource(R.drawable.rbtn_h)


            return view
        }
        fun getselectedObject() : BlaseUserDco? {
            return selecteduserinfo
        }

    }

    private fun afterSuccessfullLogin(){
        callCheckVersionWebService(object : VersionChangeListner{
            override fun onVersionChanged(status: Int) {
                when (status){
                    AppConstants.VER_CHANGED -> {
                        if (isNetworkConnectionAvailable(this@LoginActivity)) {
//                            if (!clearDataDA.isCanDoClearData()) {
//                                // UploadTransactions.resetTransactionProcessListner()
//                                // UploadTransactions.setTransactionProcessListner(this@LoginActivity)
//                                // uploadData()
//                            } else {
//                                goToPlayStoreOrDownloadAPKFile()
//                            }
                        } else {
                            showCustomDialog(this@LoginActivity,preference,
                                getString(R.string.warning),
                                getString(R.string.no_internet),
                                getString(R.string.OK),
                                "",
                                "",
                                true,this@LoginActivity)
                        }
                    }
                    AppConstants.VER_NO_BUTTON_CLICK -> afterVersionCheck()
                    AppConstants.VER_UNABLE_TO_UPGRADE -> showCustomDialog(this@LoginActivity,preference, getString(R.string.warning), "Unable to upgrade, please try again.", getString(R.string.OK), "", "", false,this@LoginActivity)
                    AppConstants.VER_NOT_CHANGED -> afterVersionCheck()

                }
            }
        },AppConstants.CALL_FROM_LOGIN)
    }

     fun callCheckVersionWebService(changeListner : VersionChangeListner,methodOrigin : Int){
         if (isNetworkConnectionAvailable(this)) {

             var blaseUserResponseHandler = DefaultHandler()
             connectionHelper.sendRequestBulkForAll(this,BuildXMLRequest.getVersionDetails("Android",getVersionName()),blaseUserResponseHandler,ServiceURLs.GetVersionDetails,preference)
            var checkVersionDO : CheckVersionDO? = blaseUserResponseHandler.response.body?.getVersionDetailsResponse?.checkVersionDO;
             if (checkVersionDO != null){
                 doVersionManagement(checkVersionDO,changeListner,methodOrigin)
             }else{
                 changeListner.onVersionChanged(AppConstants.VER_NOT_CHANGED)
             }
         } else {
             changeListner.onVersionChanged(AppConstants.VER_NOT_CHANGED)
         }
     }

    private var versionStatusCode = AppConstants.MAJOR_APP_UPDATE
    fun doVersionManagement(checkVersionDO: CheckVersionDO,changeListner : VersionChangeListner,methodOrigin :Int){
        var sampleUrl: String = checkVersionDO.apkFileName

        if (sampleUrl.contains(".apk"))
            sampleUrl = ServiceURLs.VERSION_MANAGEMENT + sampleUrl

        if (checkVersionDO.status === AppConstants.MINOR_APP_UPDATE || checkVersionDO.status === AppConstants.NORMAL_APP_UPDATE) {
            versionStatusCode = AppConstants.MINOR_APP_UPDATE
//            showDialogueForUpGrade(
//                "Please update the app.",
//                checkVersion.StatusCode,
//                sampleUrl,
//                changeListner
//            )
        } else if (checkVersionDO.status === AppConstants.MAJOR_APP_UPDATE) {
//            showDialogueForUpGrade(
//                "We have a Major Update release for you. It's Mandatory that you update the app to continue using.",
//                checkVersion.StatusCode,
//                sampleUrl,
//                changeListner
//            )
        } else {
            changeListner.onVersionChanged(AppConstants.VER_NOT_CHANGED)
        }
    }
    private
    val GET_MASTER_DATA_AFTER_UPLOAD_DATA: Int = 1
    private
    val GET_MASTER_DATA_WITHOUT_UPLOAD_DATA: Int = 2
    private fun afterVersionCheck(){
        var oldEmpNo : String = preference.getStringFromPreference(Preference.TEMP_EMP_NO,"")
        var newEmpNo : String = preference.getStringFromPreference(Preference.EMP_NO,"")

        if (!oldEmpNo.equals("", ignoreCase = true) && oldEmpNo.equals(
                newEmpNo,
                ignoreCase = true)
        ) {
            showLoader(this, getString(R.string.validating_please_wait))
            var masterHandler = DefaultHandler()
            connectionHelper.sendRequestBulkForAll(this,BuildXMLRequest.CheckMobilityStatus(preference.getStringFromPreference(Preference.USER_ID,"")),masterHandler,ServiceURLs.CHECK_MOBILITY_STATUS,preference)

            if (masterHandler.response.body?.checkMobilityStatusResponse?.status   == GET_MASTER_DATA_AFTER_UPLOAD_DATA){

                var isCanDoClearData = ClearDataDA().isCanDoClearData()
                if (isCanDoClearData){
                    clearApplicationData();
                    preference.clearPreferences();
                    runOnUiThread {
                        hideLoader()
                        binding.etUserId.setText("")
                        binding.etPassword.setText("")
                        Toast.makeText(this, "User data has been updated,Data got cleared please do re-login to use the app.", Toast.LENGTH_SHORT).show()
                        val intentBrObj = Intent()
                        intentBrObj.setAction(AppConstants.ACTION_LOGOUT)
                        sendBroadcast(intentBrObj)
                        finish()
                    }
                } else {

                    runOnUiThread {
                        //Need to do
//                        var intent = Intent(this,ManageSpaceActivity::class.java)
//                        startActivity(intent)
//                        finish()
                        hideLoader()
                    }
                }
            } else if (masterHandler.response.body?.checkMobilityStatusResponse?.status   == GET_MASTER_DATA_WITHOUT_UPLOAD_DATA){
                clearApplicationData();
                preference.clearPreferences();

                runOnUiThread {
                    hideLoader()
                    binding.etUserId.setText("")
                    binding.etPassword.setText("")
                    Toast.makeText(this, "User data has been updated,Data got cleared please do re-login to use the app.", Toast.LENGTH_SHORT).show()
                    val intentBrObj = Intent()
                    intentBrObj.setAction(AppConstants.ACTION_LOGOUT)
                    sendBroadcast(intentBrObj)
                    finish()
                }
            }else{

                runOnUiThread {
                    hideLoader()
                    goLogin(listUsersInfo!![0])
                }
            }

        }else {

            runOnUiThread {
                hideLoader()
                goLogin(listUsersInfo!![0])
            }
        }
    }
    var isFirstTimeLogin = false;

    private fun goLogin(blaseUserDco: BlaseUserDco){
        if (blaseUserDco != null){
            blaseUserDcos = blaseUserDco;

            preference.saveStringInPreference(Preference.USER_ID, blaseUserDcos!!.userId.toString());
            preference.saveStringInPreference(Preference.ROUTE_CODE, blaseUserDcos!!.routeCode.toString());
            preference.saveStringInPreference(Preference.ROUTE_TYPE, blaseUserDcos!!.username.toString());
            preference.saveStringInPreference(Preference.SALES_ORG_CODE, blaseUserDcos!!.salesOrgCode.toString());
            preference.saveStringInPreference(Preference.ROUTE_TYPE, blaseUserDcos!!.salesGroup.toString());
            var packageInfo : PackageInfo? = null
            try {
                packageInfo = packageManager.getPackageInfo(packageName, 0)
            } catch (e: PackageManager.NameNotFoundException) {
                e.printStackTrace()
            }

            if (!blaseUserDco.userType.equals(AppConstants.SALESMAN_COLLECTOR,ignoreCase = true)
                && !blaseUserDco.userType.equals(AppConstants.SALESMAN_PRESELLER)
                && !blaseUserDco.userType.equals(AppConstants.SALESMAN_VAN_SALES)
                && !blaseUserDco.userType.equals(AppConstants.SALESMAN_MERCHANDISER)){
                showCustomDialog(this,preference,getString(R.string.warning),getString(R.string.You_are_not_authorized),getString(R.string.OK),null,"",this)
            }else{
                var isIncrementalSync = false
                Thread{
                    var userloginparser = DefaultHandler()
                    connectionHelper.sendRequestBulkForAll(this,BuildXMLRequest.validateUserLogin(strusername,"LogIn",gcmId,getUniqueID()),userloginparser,ServiceURLs.VERIFY_LOGIN_METHOD,preference)

                    if (userloginparser.response.body?.postSignInSignOutLogResponse != null &&
                        userloginparser.response.body?.postSignInSignOutLogResponse!!.postSignInSignOutLogResult?.status.equals("Success",ignoreCase = true) )
                    {
                        userloginparser.response.body?.postSignInSignOutLogResponse!!.saveToPreferences(this)
                        preference.saveBooleanInPreference(Preference.IS_LOGIN_FIRST_TIME, true);
                        //				        	boolean isMasterDataToDownload = new CommonDA().getMasterDataDownloadStatus(preference.getStringFromPreference(Preference.ROUTE_CODE,""));
                        var isMasterDataToDownload = (blaseUserDco.salesOrgFax.equals("1",ignoreCase = true) || blaseUserDco.salesOrgFax.equals(
                                "True",ignoreCase = true
                        ))
                        val isCanDoClearData: Boolean = ClearDataDA().isCanDoClearDataNew()
//						DummyDA.updateData();
                        val oldEmpNo =
                            preference.getStringFromPreference(Preference.TEMP_EMP_NO, "")
                        val newEmpNo: String = blaseUserDco.userId.toString()
                        if (isMasterDataToDownload && !isCanDoClearData) {
                            runOnUiThread {
                                Toast.makeText(
                                    this,
                                    "Orders not posted",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                            isMasterDataToDownload = false
                        }

                        if (!isMasterDataToDownload && !TextUtils.isEmpty(oldEmpNo) && oldEmpNo.equals(newEmpNo, ignoreCase = true)) {
                            savePrefData(blaseUserDco)
                            isIncrementalSync = true
                            naviagateFromLogin(blaseUserDco, isIncrementalSync)
                        }else{
                            hideLoader()
                            var isDownloaded =loadMasterData(blaseUserDco.empNo,"Loading master data file...",blaseUserDco)
                            if (isDownloaded)
                                loadCPSMasterData(blaseUserDco.empNo)

                            if(!isDownloaded){
                                hideLoader()
                                onComplete()
                                showCustomDialog(
                                    this,preference,
                                    getString(R.string.warning),
                                    getString(R.string.Unable_to_download),
                                    getString(R.string.OK),
                                    getString(R.string.cancel),
                                    "",
                                    false,this
                                )
                                return@Thread
                            }else{


//									preference.getStringFromPreference(Preference.IS_FIRSTTIME_LAUNCH, true);
//									preference.commitPreference();
                                isFirstTimeLogin = true
                                val syncCompleteLogDO: SynLogDO? =
                                    SynLogDA().getSynchLog(ServiceURLs.GetCommonMasterDataSync)
                                if (syncCompleteLogDO != null) {
                                    syncCompleteLogDO.entity = ServiceURLs.GetPromotionsOnline
                                    SynLogDA().insertSynchLog(syncCompleteLogDO)
                                }

                                val settingsDO =
                                    MasterDA().getSettings(AppConstants.NUMBER_OF_DECIMALS)
                                var numberOfdecimals = 2
                                if (settingsDO != null) {
                                    numberOfdecimals = StringUtils.getInt(settingsDO.SettingValue)
                                }


                                //                                    if (curencyCode.equalsIgnoreCase("AED") || curencyCode.equalsIgnoreCase("SAR")) {
//                                        numberOfdecimals = 2;
//                                    } else if (curencyCode.equalsIgnoreCase("OMR")) {
//                                        numberOfdecimals = 3;
//                                    } else {
//                                        numberOfdecimals = 2;
//                                    }
                                if (numberOfdecimals == 0) numberOfdecimals = 2
                                preference.saveStringInPreference(
                                    Preference.NUMBER_OF_DECIMALS,
                                    "" + numberOfdecimals
                                )
                                preference.commitPreference()

                                isDayStartedNew = isJourneyCurrentlyRunning()
                                isShortRoute = getTripDateType().equals("E",ignoreCase = true)
//                                val eotDA: EOTDA = EOTDA()
                                val tripDate: String =
                                    JourneyPlanDA().getCurrentRunningTripDateOnly()
                                isPreviousDayEOTDone =
                                    TextUtils.isEmpty(tripDate) || tripDate.equals(
                                        CalendarUtils.getOrderPostDate(),
                                        ignoreCase = true
                                    )
                                if (isDayStartedNew) {
                                    isEOTDoneNew = false
                                    isCanStartDayNew = true
                                } else {
                                    isEOTDoneNew = getEOTStatusLatest()
                                    val eotDate: String = getEOTDateLatest()
                                    isCanStartDayNew = !eotDate.equals(
                                        CalendarUtils.getOrderPostDate(),
                                        ignoreCase = true
                                    )
                                }
                            }
                            naviagateFromLogin(blaseUserDco, isIncrementalSync)
                        }
                    }else{
                        var massage : String? = userloginparser.response.body?.postSignInSignOutLogResponse!!.postSignInSignOutLogResult?.message
                        showCustomDialog(this,preference,getString(R.string.warning),massage+"",getString(R.string.OK),null,"",this)
                        hideLoader()
                    }

                }.start()
            }
        }
    }

    private fun loadMasterData(strEmpNo : String? ,mgs : String,blaseUserDco: BlaseUserDco) : Boolean{
        try {
            showLoader(this,getString(R.string.Initializing))
            var defaultHandler = DefaultHandler()
            connectionHelper.sendRequestBulkForAll(this,BuildXMLRequest.getMasterDate(strEmpNo),defaultHandler,ServiceURLs.GetMasterDataFile,preference)
            var url: String? = defaultHandler.response.body?.getMasterDataFileResponse?.getMasterDataFileResult?.sqliteFileName
            if (url != null) {
                url = url.replace("@", "%40")
            }
            hideLoader()

            if (url != null && url.length >= 0) {
                val mainURL = ServiceURLs.MASTER_GLOBAL_URL
                url = String.format(url, mainURL)
            }
            showDownloadProgressBar()

            if (!downloadSQLITE(url, this)) {
                return false
            } else {
                savePrefData(blaseUserDco)
                preference.saveIntInPreference(Preference.SYNC_STATUS, 1)
                preference.saveStringInPreference(Preference.TEMP_EMP_NO, preference.getStringFromPreference(Preference.EMP_NO, ""))
                preference.saveStringInPreference(Preference.SQLITE_DATE, CalendarUtils.getOrderPostDate())
                preference.commitPreference()
            }
        }catch (e:Exception){
            e.printStackTrace()
            return false
        }
        return true
    }

   private fun loadCPSMasterData(usercode : String?) :Boolean{
       try {
           showLoader(this,getString(R.string.Initializing))
           var defaultHandler = DefaultHandler()
           connectionHelper.sendRequestBulkForAll(this,BuildXMLRequest.getCPSMasterDate(usercode),defaultHandler,ServiceURLs.GetMasterDataFileForCPSPromotion,preference)
           var url: String? = defaultHandler.response.body?.getMasterDataFileForCPSPromotionResponse?.getMasterDataFileForCPSPromotionResult?.sqliteFileName
           if (url != null) {
               url = url.replace("@", "%40")
           }

           hideLoader()

           if (url != null && url.length >= 0) {
//				String mainURL 	= 	ServiceURLs.IMAGE_GLOBAL_URL;
               val mainURL = ServiceURLs.MASTER_GLOBAL_URL
               url = String.format(url, mainURL)
           }
           showDownloadProgressBar()

           if (!downloadCPSSQLITE(url, this)) {
               return false
           } else {
               preference.saveIntInPreference(Preference.SYNC_STATUS, 1)
               preference.saveStringInPreference(
                   Preference.TEMP_EMP_NO,
                   preference.getStringFromPreference(Preference.EMP_NO, "")
               )
               preference.saveStringInPreference(
                   Preference.SQLITE_DATE,
                   CalendarUtils.getOrderPostDate()
               )
               preference.commitPreference()
           }
       }catch (e:Exception){
           e.printStackTrace()
           return false
       }
       return true
   }
//   private fun downloadSQLITE(downloadUrl: String, downloadListener: FileUtils.DownloadListner): Boolean {
//        val databasePath = "${application.filesDir}/"
//        var isSuccessful = false
//
//        lifecycleScope.launch(Dispatchers.IO) {
//            val strFile = FileUtils.downloadSQLITE(downloadUrl, databasePath, this,"salesman", downloadListener)
//            withContext(Dispatchers.Main) {
//                isSuccessful = if (TextUtils.isEmpty(strFile)) {
//                    downloadListener.onError()
//                    false
//                } else {
//                    downloadListener.onComplete()
//                    true
//                }
//            }
//        }
//
//        return isSuccessful
//    }

    fun downloadSQLITE(downloadUrl: String?, downloadListener: FileUtils.DownloadListner?): Boolean {
        val strFile = FileUtils.downloadSQLITE(
            downloadUrl,
            AppConstants.DATABASE_PATH,
            this,
            "salesman",
            downloadListener!!
        )

        return if (strFile.isNullOrEmpty()) {
            downloadListener?.onError()
            false
        } else {
            true
        }
    }

    fun downloadCPSSQLITE(downloadUrl: String?, downloadListener: FileUtils.DownloadListner?): Boolean {
        AppConstants.DATABASE_PATH_CPS = "${application.filesDir}/"
        val strFile = FileUtils.downloadSQLITECPS(
            downloadUrl,
            AppConstants.DATABASE_PATH,
            this,
            "salesmancpsdetails",
            downloadListener!!
        )

        return if (strFile.isNullOrEmpty()) {
            downloadListener?.onError()
            false
        } else {
            true
        }
    }

//    private fun downloadCPSSQLITE(downloadUrl: String?, downloadListener: FileUtils.DownloadListner): Boolean {
//        AppConstants.DATABASE_PATH_CPS = "${application.filesDir}/"
//        var isSuccessful = false
//
//        lifecycleScope.launch(Dispatchers.IO) {
//            val strFile = FileUtils.downloadSQLITECPS(downloadUrl, AppConstants.DATABASE_PATH_CPS,this, "salesmancpsdetails", downloadListener)
//            withContext(Dispatchers.Main) {
//                isSuccessful = if (TextUtils.isEmpty(strFile)) {
//                    downloadListener.onError()
//                    false
//                } else {
//                    downloadListener.onComplete()
//                    true
//                }
//            }
//        }
//
//        return isSuccessful
//    }

    private lateinit var progressBar: ProgressBar
    private lateinit var tvProgress: TextView
    private var dialogDownload: Dialog? = null
   private fun showDownloadProgressBar(){
       runOnUiThread {
           try {


           val inflater = LayoutInflater.from(this)
           val view = inflater.inflate(R.layout.progressdialog, null)

           progressBar = view.findViewById(R.id.progressBar)
           tvProgress = view.findViewById(R.id.tvProgress)

           if (dialogDownload == null) {
               dialogDownload = Dialog(this).apply {
                   window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                   requestWindowFeature(Window.FEATURE_NO_TITLE)
                   setCancelable(false)
                   setContentView(view)
                   window?.setLayout((resources.displayMetrics.widthPixels * 2 / 3), LayoutParams.WRAP_CONTENT)
                   window?.setGravity(Gravity.CENTER)
               }
           }

           progressBar.max = 100
           progressBar.progress = 0
           tvProgress.text = "0 %"
           dialogDownload?.show()
           }catch (e :Exception){
               e.printStackTrace()
           }
       }
   }

    private fun naviagateFromLogin(blaseUserDco: BlaseUserDco,isIncrementalSync : Boolean){
        AppConstants.Currency = CommonDA().getCurrencyCode(preference.getStringFromPreference(Preference.EMP_NO, ""))
        preference.saveStringInPreference(Preference.CURRENCY_CODE, AppConstants.Currency)
        val numberOfDecimals = 2 // Replace with your actual number of decimals
        FormatUtils.setDecimalFormat(AppConstants.Currency, numberOfDecimals)

        //		String tripDate = new CommonDA().getTripDate();
        var tripDate : String = JourneyPlanDA().getStartDayForCurrentRunningJourney()
        var daysAllowed: Int = SettingsOrgDA().getSettingValueBySettingName(SettingsDO.get_TRIP_DAYS_ALLOWED())
        if (daysAllowed <= 0)
            daysAllowed = 7
        if (tripDate.isNotEmpty() && CalendarUtils.getDiffBtwDatesFromCurrentDate(tripDate) > daysAllowed) {
            tripDate = ""
            CommonDA().updateTripDate()
        }

        if (TextUtils.isEmpty(tripDate)) preference.saveBooleanInPreference(
            Preference.IS_DAY_STARTED,
            false
        )
        else preference.saveBooleanInPreference(Preference.IS_DAY_STARTED, true)

        //		new CustomerDA().updateLastJourneyLog(preference.getStringFromPreference(Preference.VISIT_CODE, ""),"");
        CustomerDA().updateLastJourneyLogCheckIn(preference.getStringFromPreference(Preference.VISIT_CODE, "")
        )
        CustomerDA().updateCustomerVisit()


        val id = preference.getStringFromPreference(Preference.CustomerPerfectStoreMonthlyScoreUID, "")
        if (!TextUtils.isEmpty(id)) CustomerDA().updateLastJourneyPerfectStoreLogCheckOut(id)

        UserInfoDA().insertUpdateUserInfo(blaseUserDco)
//        drawTextToBitmap(getString(R.string.Address_Information))
        checkAndInsertForTodayLogReport()
        val settingsDO: SettingsDO =
            MasterDA().getSettings(SettingsDO.getGeofenceRadiusSettingsValue())
        var printerType: String =
            CommonDA().getPrinterType(preference.getStringFromPreference(Preference.ROUTE_CODE, ""))

        if (TextUtils.isEmpty(printerType))
            printerType = AppConstants.DOT_MATRIX
        printerType = AppConstants.THERMAL
        preference.saveStringInPreference(Preference.PRINTER_TYPE, printerType)
        preference.saveStringInPreference(Preference.CustomerPerfectStoreMonthlyScoreUID, "")
        preference.commitPreference()
        //		if(settingsDO!=null){
//			GEOFENCE_RADIUS_IN_METERS = StringUtils.getInt(settingsDO.SettingValue);
//		}
        isJourneyCurrentlyRunning = isJourneyCurrentlyRunning()
        isEOTDoneForTodaysDate = getEOTStatusForTodaysDate(CalendarUtils.getCurrenetDate()).also { isEOTdoneForTodays = it }

        if (isIncrementalSync){

//            scheduleDataSync(this,syncViewModel)
            moveToNextPage(blaseUserDco, 0)
        }else{

            runOnUiThread {
                moveToNextPage(blaseUserDco, 0)
            }
        }

    }
    private fun moveToNextPage(blaseUserDco: BlaseUserDco, isexpired: Int) {


        preference.saveBooleanInPreference(Preference.IS_CHECKED_IN, false)
        preference.saveStringInPreference(
            Preference.LAST_SYNC_TIME,
            CalendarUtils.getCurrentDateAsString()!!
        )
        preference.saveBooleanInPreference(
            Preference.IS_DATA_SYNCED_FOR_USER + blaseUserDco.salesmanCode,
            true
        )
        blaseUserDco.isOnlineOnly?.let {
            preference.saveBooleanInPreference(Preference.IS_ONLINE_ONLY,
                it
            )
        }
        preference.commitPreference()

        if (preference.getBooleanFromPreference(
                "isRememberChecked",
                false
            )
        ) preference.saveStringInPreference(Preference.PASSWORD, strPassword)
        else preference.saveStringInPreference(Preference.PASSWORD, "")

        preference.saveStringInPreference(Preference.EMP_NO, blaseUserDco.empNo)
        preference.saveStringInPreference(Preference.REGION, blaseUserDco.regionCode)
        preference.saveStringInPreference(Preference.USER_TYPE, blaseUserDco.role)
        preference.saveStringInPreference(Preference.PASSWORD, strPassword)
        preference.saveStringInPreference(Preference.SALES_GROUP, blaseUserDco.salesGroup)
        preference.saveStringInPreference(Preference.USERSUBTYPE, blaseUserDco.userSubType)
        preference.saveStringInPreference(Preference.ROUTE_CODE, blaseUserDco.routeCode)
        preference.commitPreference()
        hideLoader()
        scheduleBackgroundtask()

//        rootUserInfo = FirebaseDatabase.getInstance().getReference().child("UserInfo")
//        rootUserInfo.child(preference.getStringFromPreference(Preference.USER_ID, ""))
//            .setValue(preference.getStringFromPreference(Preference.GCM_ID, ""))


        if (preference.getStringFromPreference(Preference.SALESMAN_TYPE, "")
                .equals(AppConstants.SALESMAN_VAN_SALES)
        ) {
            if (!isCanStartDayNew || isDayStartedNew) {
                val intent: Intent = Intent(
                    this,
                    HomeActivity::class.java
                )
                intent.putExtra("isFirstTimeLogin", isFirstTimeLogin)
                intent.putExtra("isFromLogin", true)
                startActivity(intent)
            } else {
                val intent: Intent = Intent(
                    this,
                    HomeActivity::class.java
                )
                intent.putExtra(resources.getString(R.string.Latitude), 25.522)
                intent.putExtra(resources.getString(R.string.Longitude), 78.522)
                intent.putExtra("isCashierSettlementChecked", true)
                startActivity(intent)
            }
        } else {
            showCustomDialog(
                this,preference,
                getString(R.string.warning),
                getString(R.string.user_is_not_a_vansales_user),
                getString(R.string.OK),
                "",
                "",false,this
            )
        }
    }
    fun scheduleBackgroundtask() {
        try {

            // Create constraints
            val constraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED) // Only run when connected to the internet
                .build()

// Create a PeriodicWorkRequest with constraints
            val periodicWorkRequest = PeriodicWorkRequestBuilder<UploadWorker>(10, TimeUnit.MINUTES)
                .setConstraints(constraints)
                .build()

// Enqueue the periodic work
            WorkManager.getInstance(applicationContext).enqueue(periodicWorkRequest)

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun checkAndInsertForTodayLogReport() {
        val header: TrxLogHeaders = TrxLogHeaders()
        //		String userCode = preference.getStringFromPreference(Preference.EMP_NO, "");
        if (TransactionsLogsDA().getTransactionsLogCount(CalendarUtils.getOrderPostDate()) > 0) {
        } else {
            header.totalActualCalls = 0
            header.totalProductiveCalls = 0
            header.totalCollections = 0.0
            header.totalCreditNotes = 0.0
            header.totalSales = 0.0
            header.totalScheduledCalls = CustomerDetailsDA().getJourneyPlanCount()
            header.currentMonthlySales = 0.0
            header.trxDate = CalendarUtils.getOrderPostDate()
            val vecTrxLogHeaders: Vector<TrxLogHeaders> = Vector<TrxLogHeaders>()
            vecTrxLogHeaders.add(header)
            TransactionsLogsDA().insertTrxLogHeaders(vecTrxLogHeaders)
        }
    }
    private fun savePrefData(loginUserInfo: BlaseUserDco) {


        if (preference.getStringFromPreference(Preference.SALESMAN_TYPE, "Preseller").equals(AppConstants.SALESMAN_PRESELLER,ignoreCase = true))
            preference.saveIntInPreference(Preference.USER_TYPE_NEW, AppConstants.USER_PRESELER);
        else if (preference.getStringFromPreference(Preference.SALESMAN_TYPE, "Preseller").equals(AppConstants.SALESMAN_VAN_SALES,ignoreCase = true))
            preference.saveIntInPreference(Preference.USER_TYPE_NEW, AppConstants.USER_VAN_SALES);
        else
            preference.saveIntInPreference(Preference.USER_TYPE_NEW, AppConstants.USER_MERCHANDISER);

        loginUserInfo.empNo?.let { preference.saveStringInPreference(Preference.USER_ID, it) };
        loginUserInfo.username?.let { preference.saveStringInPreference(Preference.USER_NAME, it) };
        loginUserInfo.username?.let { preference.saveStringInPreference(Preference.USER_NAME_LOG, it) };
        preference.saveStringInPreference(Preference.PASSWORD, strPassword);
        loginUserInfo.routeCode?.let { preference.saveStringInPreference(Preference.ROUTE_CODE, it) };

//		//added by mahesh for currrency code
//		if(loginUserInfo.OrgName.equalsIgnoreCase("Arla UAE"))
//			preference.saveStringInPreference(Preference.CURRENCY_CODE,"AED");
//		else if(loginUserInfo.OrgName.equalsIgnoreCase("Arla KSA"))
//			preference.saveStringInPreference(Preference.CURRENCY_CODE,"KSA");
//		else if(loginUserInfo.OrgName.equalsIgnoreCase("Arla OMR"))
//			preference.saveStringInPreference(Preference.CURRENCY_CODE,"OMR");


//			preference.saveStringInPreference(Preference.USER_NAME, loginUserInfo.strUserName);
        loginUserInfo.role?.let { preference.saveStringInPreference(Preference.USER_TYPE, it) }
        loginUserInfo.token?.let { preference.saveStringInPreference("strToken", it) }
        loginUserInfo.empNo?.let { preference.saveStringInPreference(Preference.EMP_NO, it) }
        loginUserInfo.salesOrgCode?.let { preference.saveStringInPreference(Preference.ORG_CODE, it) }
        loginUserInfo.userType?.let { preference.saveStringInPreference(Preference.SALESMAN_TYPE, it) }

        //			preference.saveStringInPreference(Preference.SALESMAN_TYPE, AppConstants.SALESMAN_MERCHANDISER);
        preference.saveStringInPreference(Preference.INT_USER_ID, loginUserInfo.intUserId.toString())
        loginUserInfo.salesOrgDescription?.let { preference.saveStringInPreference(Preference.ORG_NAME, it) }
        loginUserInfo.salesOrgAddress?.let { preference.saveStringInPreference(Preference.ORG_ADD, it) }
        loginUserInfo.salesOrgFax?.let { preference.saveStringInPreference(Preference.ORG_FAX, it) }
        loginUserInfo.serverName?.let { preference.saveStringInPreference(Preference.SERVER_NAME, it) }
        preference.commitPreference()

        preference.removeFromPreference("lastservedcustomer");
    }

    fun clearApplicationData() {
        val cache = cacheDir
        val appDir = File(cache.parent!!)
        if (appDir.exists()) {
            val children = appDir.list()
            children?.forEach { s ->
                if (s != "lib") {
                    deleteDir(File(appDir, s))
                    Log.i("TAG", "File /data/data/APP_PACKAGE/$s DELETED")
                }
            }
        }
    }
    fun deleteDir(dir: File): Boolean {
        if (dir.isDirectory) {
            val children = dir.list()
            children?.forEach { child ->
                if (!deleteDir(File(dir, child))) {
                    return false
                }
            }
        }
        return dir.delete()
    }

    override fun onProgress(count: Int) {
        runOnUiThread {
            if (dialogDownload != null) {
                progressBar.progress = count
                tvProgress.text = "$count %"
            }
        }
    }

    override fun onComplete() {
        runOnUiThread {
            try {
                if (!isFinishing && dialogDownload != null && dialogDownload!!.isShowing) {
                    dialogDownload!!.dismiss()
                }
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
            }
        }
    }

    override fun onError() {
        runOnUiThread {
            try {
                if (!isFinishing && dialogDownload != null && dialogDownload!!.isShowing) {
                    dialogDownload!!.dismiss()
                }
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
            }
        }
    }

    fun allowLoginWhenNoResponseinOfflinemode(){
        CustomerDA().updateLastJourneyLogCheckIn(preference.getStringFromPreference(Preference.VISIT_CODE, ""))
        val tripDate = JourneyPlanDA().getStartDayForCurrentRunningJourney()
        isJourneyCurrentlyRunning = isJourneyCurrentlyRunning()
        isEOTDoneForTodaysDate = getEOTStatusForTodaysDate(CalendarUtils.getCurrenetDate()).also { isEOTdoneForTodays = it }

        if (TextUtils.isEmpty(tripDate)) preference.saveBooleanInPreference(
            Preference.IS_DAY_STARTED,
            false
        )
        else preference.saveBooleanInPreference(Preference.IS_DAY_STARTED, true)

        preference.saveBooleanInPreference(Preference.IS_CHECKED_IN, false)
        preference.saveStringInPreference(Preference.HTTP_RESPONSE_METHOD, "")
        preference.saveIntInPreference(Preference.HTTP_RESPONSE_CODE, 0)
        preference.commitPreference()

        val strLastUserName = preference.getStringFromPreference(Preference.USER_ID, "")
        val strPassword = preference.getStringFromPreference(Preference.PASSWORD, "")


        //		        	String date            = preference.getStringFromPreference(Preference.SQLITE_DATE, "");
        val crashLog = preference.getStringFromPreference(Preference.CRASH_REPORT, "")
        if (strLastUserName.isNotEmpty() && strPassword.isNotEmpty() &&
            strLastUserName.equals(binding.etUserId.getText().toString(),ignoreCase = true)
            && strPassword.equals(binding.etPassword.getText().toString())
        ) {
            showLoader(this,getString(R.string.please_wait))
            lifecycleScope.launch {
                try {

                    val id = preference.getStringFromPreference(
                        Preference.CustomerPerfectStoreMonthlyScoreUID,
                        ""
                    )
                    if (!TextUtils.isEmpty(id)) CustomerDA().updateLastJourneyPerfectStoreLogCheckOut(
                        id
                    )
                    if (!TextUtils.isEmpty(crashLog)) {
                        try {
                            ConnectionHelper.writeIntoCrashLog(crashLog)
                        } catch (e: IOException) {
                            // TODO Auto-generated catch block
                            e.printStackTrace()
                        }
                    }

                    withContext(Dispatchers.Main){
                        hideLoader()
                        preference.saveStringInPreference(
                            Preference.CustomerPerfectStoreMonthlyScoreUID,
                            ""
                        )
                        preference.saveStringInPreference(Preference.CRASH_REPORT, "")
                        preference.commitPreference()

                        if (preference.getStringFromPreference(Preference.SALESMAN_TYPE, "")
                                .equals(AppConstants.SALESMAN_VAN_SALES)
                        ) {
                            if (!isCanStartDayNew || isDayStartedNew) {
                                val intent: Intent = Intent(
                                    this@LoginActivity,
                                    HomeActivity::class.java
                                )
                                intent.putExtra("isFirstTimeLogin", isFirstTimeLogin)
                                intent.putExtra("isFromLogin", true)
                                startActivity(intent)
                            } else {
                                val intent: Intent = Intent(this@LoginActivity, HomeActivity::class.java)
                                intent.putExtra(resources.getString(R.string.Latitude), 25.522)
                                intent.putExtra(resources.getString(R.string.Longitude), 78.522)
                                intent.putExtra("isCashierSettlementChecked", true)
//                                intent.putExtra("mallsDetails", mallsDetails)
                                startActivity(intent)
                            }
                        } else {
                            showCustomDialog(this@LoginActivity,preference,getString(R.string.alert),getString(R.string.internetconnection_is_not_available),getString(R.string.OK),null,"",this@LoginActivity)
                        }
                    }

                }catch (e:Exception){
                    e.printStackTrace()
                }


            }

        }else
            showCustomDialog(this,preference,getString(R.string.alert),getString(R.string.internetconnection_is_not_available),getString(R.string.OK),null,"",this)

    }



}