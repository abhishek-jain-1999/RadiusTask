package com.abhishek.radiustask.adapter

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.abhishek.radiustask.pojos.Facilities

object BindingAdapter {
    @JvmStatic
    @BindingAdapter(value = ["facility_list"])
    fun bindFacilityList(recyclerView: RecyclerView,facilityList :List<Facilities>?) {
        val adapter = recyclerView.adapter ?: return
        if (facilityList == null) {
            return
        }
        if (adapter is FacilityAdapter) {
            adapter.setData(facilityList)
        }
    }
}