package com.abhishek.radiustask

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.abhishek.radiustask.api.ApiClient
import com.abhishek.radiustask.pojos.*
import com.abhishek.radiustask.room.AppDao
import com.abhishek.radiustask.room.AppDatabase
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import java.util.function.Consumer


class MainViewModel(application: Application) : AndroidViewModel(application) {


    private val mDao: AppDao = AppDatabase.getInstance(application)!!.dao()

    val liveFacilityList: MutableLiveData<List<Facilities>> = MutableLiveData()
    var exclusionsList: MutableList<String> = ArrayList()
    var indexMap: MutableMap<String, Int> = HashMap()
    private var facilityList: List<Facilities>? = null
    val errorType: MutableLiveData<Int> = MutableLiveData(0)
    val progressLayoutVisibility = MutableLiveData<Boolean>()

    val listener = { facilityId: Int, optionId: Int ->
        onFacilityClicked(facilityId, optionId)
    }

    companion object {
        private val TAG = MainViewModel::class.java.simpleName
    }

    init {
        getAllData()
    }

    private fun fetchData() {
        ApiClient.apiInterface.data().enqueue(object : Callback<ResponseData> {
            override fun onResponse(call: Call<ResponseData>?, response: Response<ResponseData>) {
                val responseData: ResponseData? = response.body()

                if (responseData != null) {
                    //makeAllList(responseData)
                    AppDatabase.databaseWriteExecutor.execute {
                        mDao.deleteAllExclusionsData()
                        mDao.insertAllExclusionsData(ExclusionsList.toObject(responseData.exclusions!!))

                    }
                    AppDatabase.databaseWriteExecutor.execute {
                        mDao.deleteAllFacilitiesData()
                        mDao.insertAllFacilitiesData(responseData.facilities!!)
                    }

                } else {
                    errorType.value = 2
                }
            }

            override fun onFailure(call: Call<ResponseData>?, t: Throwable?) {
                errorType.value = 2
            }
        })
    }

    private fun makeAllList(responseData: ResponseData) {
        //makeExclusionList(responseData.exclusions!!)
        //liveFacilityList.value = responseData.facilities!!
        //makeIndexMap(responseData.facilities!!)
    }

    private fun makeExclusionList(exclusions: List<List<Exclusions>>) {
        for (list in exclusions) {
            var temp = ""
            for (exclusion in list) {
                temp = temp + exclusion.facility_id
                    .toString() + "," + exclusion.options_id.toString() + ":"
            }
            exclusionsList.add(temp)
        }
    }

    private fun onFacilityClicked(facilityId: Int, optionId: Int) {
        facilityList = liveFacilityList.value
        updateNewList(facilityId, optionId)
        liveFacilityList.value = facilityList
    }

    private fun updateNewList(facilityId: Int, optionId: Int) {
        val selectType = getSelectType(facilityId, optionId)
        if (selectType == 0) {
            for (o in facilityList!![getIndex(facilityId, false)].options!!) {
                if (o.selectType == 1) {
                    updateNewList(facilityId, o.id!!)
                    break
                }
            }
            setSelectType(facilityId, optionId, 1)
            updateExcludedOption(facilityId, optionId, 2)
        } else if (selectType == 1) {
            setSelectType(facilityId, optionId, 0)
            updateExcludedOption(facilityId, optionId, 0)
        } else {
            errorType.value = 1
        }
    }

    private fun updateExcludedOption(facilityId: Int, optionId: Int, selectType: Int) {
        exclusionsList.forEach(Consumer { s: String ->
            if (s.contains("$facilityId,$optionId")) {
                val arr = s.replace("$facilityId,$optionId", "")
                    .replace(":", "")
                    .split(",".toRegex())
                setSelectType(arr[0].toInt(), arr[1].toInt(), selectType)
            }
        })
    }

    private fun makeIndexMap(facilities: List<Facilities>) {
        for (i in facilities.indices) {
            val facility: Facilities = facilities[i]
            indexMap["f" + facility.facility_id] = i
            val optionsList: List<Options> = facility.options!!
            for (j in optionsList.indices) {
                indexMap["o" + optionsList[j].id] = j
            }
        }
    }

    private fun getSelectType(facilityId: Int, optionId: Int): Int {
        return facilityList!![getIndex(facilityId, false)]
            .options!![getIndex(optionId, true)]
            .selectType
    }

    private fun setSelectType(facilityId: Int, optionId: Int, selectType: Int) {
        val temp: Options = facilityList!![getIndex(facilityId, false)]
            .options!![getIndex(optionId, true)]
        if (selectType == 2 || selectType == 1) {
            temp.selectType = selectType
            if (selectType == 2) {
                temp.incClickCount()
            }
        } else if (selectType == 0) {
            if (temp.selectType == 2) {
                temp.decClickCount()
            }
            if (temp.clickCount <= 0) {
                temp.selectType = 0
            }
        }
    }

    private fun getIndex(id: Int, isOption: Boolean): Int {
        return (if (isOption) indexMap["o$id"] else indexMap["f$id"]) as Int
    }

    fun onErrorShown() {
        errorType.value = 0
    }

    fun getAllData() {
        progressLayoutVisibility.value = true
        mDao.getAllExclusionsData().observeForever {
            if (it.isNotEmpty()) {
                makeExclusionList(ExclusionsList.toList(it))
                if (liveFacilityList.value != null && liveFacilityList.value!!.isNotEmpty()) {
                    progressLayoutVisibility.value = false
                }
            } else {
                fetchData()
            }
        }
        mDao.getAllFacilitiesData().observeForever {
            if (it.isNotEmpty()) {
                makeIndexMap(it)
                liveFacilityList.value = it
                if (exclusionsList.isNotEmpty()) {
                    progressLayoutVisibility.value = false
                }
            }
        }
    }

}