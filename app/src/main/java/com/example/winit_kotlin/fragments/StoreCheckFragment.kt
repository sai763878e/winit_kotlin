package com.example.winit_kotlin.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.winit_kotlin.R
import com.example.winit_kotlin.activities.HomeActivity
import com.example.winit_kotlin.common.AppConstants
import com.example.winit_kotlin.common.Preference
import com.example.winit_kotlin.dataaccesslayer.CaptureInventryDA
import com.example.winit_kotlin.dataaccesslayer.CategoriesDA
import com.example.winit_kotlin.dataaccesslayer.CommonDA
import com.example.winit_kotlin.dataaccesslayer.CustomerDA
import com.example.winit_kotlin.dataaccesslayer.CustomerPricingDA
import com.example.winit_kotlin.dataaccesslayer.InventoryDA
import com.example.winit_kotlin.dataaccesslayer.SettingsOrgDA
import com.example.winit_kotlin.dataaccesslayer.StatusDA
import com.example.winit_kotlin.dataaccesslayer.StoreCheckDA
import com.example.winit_kotlin.databinding.FragmentStoreCheckBinding
import com.example.winit_kotlin.dataobject.CategoryDO
import com.example.winit_kotlin.dataobject.Customer
import com.example.winit_kotlin.dataobject.KPIDA
import com.example.winit_kotlin.dataobject.ProductDO
import com.example.winit_kotlin.dataobject.SettingsDO
import com.example.winit_kotlin.dataobject.StoreCheckClassificationDO
import com.example.winit_kotlin.dataobject.UOMConversionFactorDO
import com.example.winit_kotlin.utilities.StringUtils
import com.example.winit_kotlin.utils.CalendarUtils
import com.example.winit_kotlin.utils.hideLoader
import com.example.winit_kotlin.utils.showLoader
import com.example.winit_kotlin.viewmodel.SharedViewModel
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Vector

class StoreCheckFragment : Fragment() {
   private lateinit var binding : FragmentStoreCheckBinding
   private lateinit var customer : Customer
   private lateinit var sharedViewModel: SharedViewModel
   private lateinit var preference: Preference
    private lateinit var activity : HomeActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentStoreCheckBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    companion object {

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        preference = Preference(requireContext())
        activity = requireContext() as HomeActivity
        sharedViewModel = ViewModelProvider(requireActivity())[SharedViewModel::class.java]
        sharedViewModel.customerData.observe(viewLifecycleOwner){ customerData ->
            customer = customerData;
            if (customer != null){
                loadData()
            }
            Toast.makeText(requireContext(), "${customerData.site}", Toast.LENGTH_SHORT).show()
        }

        setupViewPagerWithTabs()
    }
    private var list = listOf("Item1", "Item2", "Item3")
    private var list1 = listOf("SOS1", "SOS2", "SOS3")
    private var list2 = listOf("MSL1", "MSL2", "MSL3")
    private var list3 = listOf("PROMO1", "PROMO2", "PROMO3")
    private fun setupViewPagerWithTabs() {

        var arrayList : ArrayList<List<String>> = ArrayList<List<String>>()
        arrayList.add(list)
        arrayList.add(list1)
        arrayList.add(list2)
        arrayList.add(list3)
        val titles = listOf("ALL", "SOS", "MSL", "PROMO")

        val adapter = ViewPagerAdapter(this, arrayList)
        binding.viewpager.adapter = adapter

        TabLayoutMediator(binding.tab, binding.viewpager) { tab, position ->
            tab.text = titles[position]
        }.attach()
    }
    class ViewPagerAdapter(
        fragment: Fragment,
        private val arraylist: ArrayList<List<String>>
    ) : FragmentStateAdapter(fragment) {
        override fun getItemCount() = arraylist.size
        override fun createFragment(position: Int): Fragment {

            return ListBrandFragment.newInstance(arraylist.get(position))
        }
    }
    private var isGoodReturnEnabled: Boolean = false
    private var isBadReturnEnabled: Boolean = false
    private var isMandatoryStoreCheck: Boolean = false
    private var isSKUQtyNeedtoCheck: Boolean = false
    var hmItemMaxUOM: HashMap<String, String>? = null
    private var CustomerPerfectStoreMonthlyScoreId : String? = ""
    private var returnOrderAmount : String? = ""
    private var vecMSLItems : Vector<String>? = null
    private var vecFocusItems : Vector<String>? = null
    private var vecNewItems : Vector<String>? = null
    private var hmUOMFactor = hashMapOf<String, UOMConversionFactorDO>()
    private var vecSellableReasons = Vector<String>()
    private var arrCategories = Vector<CategoryDO>()
    private var arrSubCategories = Vector<CategoryDO>()
    private var routeTypeTemp : String = "";
    private var storeCheckID :String = ""
    private var vecStatusDO = Vector<String>()
    private var isSCSubBrandgroup : Boolean = false
    private var hmSecondaryUoms = HashMap<String, String>()
    private  var hashMapPricing : HashMap<String, HashMap<String, Double>> = HashMap()
    private var isBackStore : Boolean = false;
    var hashMapNPD = hashMapOf<String, ProductDO>()
    private var isAlertNo : Boolean = false
    private var objectArray = arrayOfNulls<Any?>(6)
    private var vecClassifications = Vector<StoreCheckClassificationDO>()
    private fun loadData(){
        showLoader(getString(R.string.loading_data))
        lifecycleScope.launch {
            try {
//                val result_1 = withContext(Dispatchers.IO) {
//                    val isGoodReturnEnabledDeferred = async { CustomerDA().isGoodReturnEnabled(customerId =customer.site+"" ) }
//                    val isBadReturnEnabledDeferred = async { CustomerDA().isBadReturnEnabled(customer.site+"") }
//                    val hmUomFactorDeferred = async { InventoryDA().getUomDetails() }
//                    val hmItemMaxUOMDeferred = async { CommonDA().getItemMaxUOM() }
//                    val CustomerPerfectStoreMonthlyScoreIdDeferred = async { KPIDA().getCustomerPerfectStoreMonthlyScoreId(customer.site+"",
//                        CalendarUtils.getTargetCurrentYear(),CalendarUtils.getTargetCurrentMonth()) }
//                    val returnOrderAmountDeferred = async { SettingsOrgDA().getSettingValueStringByName(SettingsDO.get_ReturnOrderApprovalRequired()) }
//                    val preparepricingDataDeferred = async { loadPricingData() }
//                    val vecSellableReasonsDeferred = async { CategoriesDA().getReturnReasons("","Good Return Reason") }
//                    val arrCategoriesDeferred = async { CategoriesDA().getAllCategory() }
//                    val arrSubCategoriesDeferred = async { CategoriesDA().getAllSubCategory() }
//                    val isMandatoryStoreCheckDeferred = async { CustomerDA().hasMandatoryStoreCheck(preference.getStringFromPreference(Preference.ROUTE_CODE,"")) }
//                    val isSKUQtyNeedtoCheckDeferred = async { CustomerDA().isSKUQtyNeedtoCheck(preference.getStringFromPreference(Preference.ROUTE_CODE,"")) }
//                    val storeCheckID = async { StoreCheckDA().getStoreCheckID(customer.site+"") }
//                    val vecStatusDO = async { StatusDA().getCompletedOptionsStatus(customer.site+"", AppConstants.Action_CheckIn) }
//                    val isSCSubBrandgroup = async { SettingsOrgDA().getSettingValue(SettingsDO.get_SUB_BRAND_GROUP_REQUIRED()) }
//                    val hmSecondaryUoms = async { CategoriesDA().getAllSecondaryUOMs() }
//                    val hashMapNPD = async { KPIDA().getNPDItems(preference.getStringFromPreference(Preference.ORG_CODE,"")) }
//
//                    if (vecMSLItems == null)
//                        vecMSLItems = Vector<String>()
//                    else if (vecMSLItems!!.size > 0)
//                        vecMSLItems!!.clear()
//
//                    if (vecFocusItems == null) vecFocusItems = Vector<String>()
//                    else if (vecFocusItems!!.size > 0) vecFocusItems!!.clear()
//
//                    if (vecNewItems == null) vecNewItems = Vector<String>()
//                    else if (vecNewItems!!.size > 0) vecNewItems!!.clear()
//
//
//                    awaitAll(isGoodReturnEnabledDeferred,//0
//                        isBadReturnEnabledDeferred,//1
//                        hmUomFactorDeferred,//2
//                        hmItemMaxUOMDeferred,//3
//                        CustomerPerfectStoreMonthlyScoreIdDeferred,//4
//                        returnOrderAmountDeferred,//5
//                        preparepricingDataDeferred,//6
//                        vecSellableReasonsDeferred//7
//                        ,arrCategoriesDeferred//8
//                        ,arrSubCategoriesDeferred//9
//                        ,isMandatoryStoreCheckDeferred//10
//                        ,isSKUQtyNeedtoCheckDeferred//11
//                        ,storeCheckID//12
//                        ,vecStatusDO//13
//                        ,isSCSubBrandgroup//14
//                        ,hmSecondaryUoms//15
//                        ,hashMapNPD//16
//                    )
//                }

                val result_1 = withContext(Dispatchers.IO) {
                    val isGoodReturnEnabled = CustomerDA().isGoodReturnEnabled(customer.site ?: "")
                    val isBadReturnEnabled = CustomerDA().isBadReturnEnabled(customer.site ?: "")
                    val hmUomFactor = InventoryDA().getUomDetails()
                    val hmItemMaxUOM = CommonDA().getItemMaxUOM()
                    val customerPerfectStoreMonthlyScoreId = KPIDA().getCustomerPerfectStoreMonthlyScoreId(
                        customer.site ?: "", CalendarUtils.getTargetCurrentYear(), CalendarUtils.getTargetCurrentMonth()
                    )
                    val returnOrderAmount = SettingsOrgDA().getSettingValueStringByName(SettingsDO.get_ReturnOrderApprovalRequired())
                    val preparePricingData = loadPricingData()
                    val vecSellableReasons = CategoriesDA().getReturnReasons("", "Good Return Reason")
                    val arrCategories = CategoriesDA().getAllCategory()
                    val arrSubCategories = CategoriesDA().getAllSubCategory()
                    val isMandatoryStoreCheck = CustomerDA().hasMandatoryStoreCheck(preference.getStringFromPreference(Preference.ROUTE_CODE, ""))
                    val isSKUQtyNeedtoCheck = CustomerDA().isSKUQtyNeedtoCheck(preference.getStringFromPreference(Preference.ROUTE_CODE, ""))
                    var storeCheckID = StoreCheckDA().getStoreCheckID(customer.site ?: "")
                    val vecStatusDO = StatusDA().getCompletedOptionsStatus(customer.site ?: "", AppConstants.Action_CheckIn)
                    val isSCSubBrandgroup = SettingsOrgDA().getSettingValue(SettingsDO.get_SUB_BRAND_GROUP_REQUIRED())
                    val hmSecondaryUoms = CategoriesDA().getAllSecondaryUOMs()
                    val hashMapNPD = KPIDA().getNPDItems(preference.getStringFromPreference(Preference.ORG_CODE, ""))

                    // Initialize or clear the vectors
                    vecMSLItems = vecMSLItems ?: Vector()
                    vecMSLItems?.clear()

                    vecFocusItems = vecFocusItems ?: Vector()
                    vecFocusItems?.clear()

                    vecNewItems = vecNewItems ?: Vector()
                    vecNewItems?.clear()

                    // Return all results in a list for easy assignment
                    listOf(
                        isGoodReturnEnabled,
                        isBadReturnEnabled,
                        hmUomFactor,
                        hmItemMaxUOM,
                        customerPerfectStoreMonthlyScoreId,
                        returnOrderAmount,
                        preparePricingData,
                        vecSellableReasons,
                        arrCategories,
                        arrSubCategories,
                        isMandatoryStoreCheck,
                        isSKUQtyNeedtoCheck,
                        storeCheckID,
                        vecStatusDO,
                        isSCSubBrandgroup,
                        hmSecondaryUoms,
                        hashMapNPD
                    )
                }

                isGoodReturnEnabled = result_1[0] as Boolean
                isBadReturnEnabled = result_1[1] as Boolean
                hmUOMFactor = result_1[2] as HashMap<String, UOMConversionFactorDO>
                hmItemMaxUOM = result_1[3] as HashMap<String, String>
                CustomerPerfectStoreMonthlyScoreId = result_1[4] as String
                returnOrderAmount = result_1[5] as String
                vecSellableReasons = result_1[7] as Vector<String>
                arrCategories = result_1[8] as Vector<CategoryDO>
                arrSubCategories = result_1[9] as Vector<CategoryDO>
                isMandatoryStoreCheck = result_1[10] as Boolean
                isSKUQtyNeedtoCheck = result_1[11] as Boolean
                storeCheckID = result_1[12] as String
                vecStatusDO = result_1[13] as Vector<String>
                isSCSubBrandgroup = result_1[14] as Boolean
                hmSecondaryUoms = result_1[15] as HashMap<String, String>
                hashMapNPD = result_1[16] as HashMap<String, ProductDO>

                if (storeCheckID.equals("", ignoreCase = true))
                    storeCheckID = StringUtils.getUniqueUUID()

                customer.userID = preference.getStringFromPreference(Preference.USER_ID,"");

                if (isSKUQtyNeedtoCheck)
                    routeTypeTemp = AppConstants.ROUTE_TYPE_AMBIENT
                else
                    routeTypeTemp = preference.getStringFromPreference(Preference.ROUTE_TYPE, "");



//                val result_2 = withContext(Dispatchers.IO) {
//                   val hashMapPricing = async { CaptureInventryDA().getOnlinePricing() }
//                    val objectArray = async { CategoriesDA().getStoreCheckClassificationsNew(customer, categoryId = "",isBackStore,routeTypeTemp.equals(AppConstants.ROUTE_TYPE_FROZEN),vecMSLItems,vecFocusItems,vecNewItems,isSCSubBrandgroup
//                    ,hashMapNPD,isAlertNo,storeCheckID) }
//
//                    awaitAll(hashMapPricing//1
//                        ,objectArray//2
//                    )
//                }
                val result_2 = withContext(Dispatchers.IO) {
                    val hashMapPricing = CaptureInventryDA().getOnlinePricing()
                    val objectArray = CategoriesDA().getStoreCheckClassificationsNew(
                        customer, "", isBackStore, routeTypeTemp == AppConstants.ROUTE_TYPE_FROZEN,
                        vecMSLItems, vecFocusItems, vecNewItems, isSCSubBrandgroup, hashMapNPD, isAlertNo, storeCheckID
                    )
                    listOf(hashMapPricing, objectArray)
                }
                hashMapPricing = result_2[0] as  HashMap<String, HashMap<String, Double>>
                objectArray = result_2[1] as Array<Any?>

                vecClassifications = objectArray[0] as Vector<StoreCheckClassificationDO>


            }catch (e : Exception){
                e.printStackTrace()
            }finally {
                hideLoader()
            }
        }
    }
    private fun loadPricingData() {
        try {
            CustomerPricingDA().getApplicablePricing_(customer.site+"", preference.getStringFromPreference(Preference.USER_ID,""),
                preference.getStringFromPreference(Preference.ROUTE_CODE,""), false)
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }
}