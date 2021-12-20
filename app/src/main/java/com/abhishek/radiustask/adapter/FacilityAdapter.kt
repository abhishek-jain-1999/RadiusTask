package com.abhishek.radiustask.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.abhishek.radiustask.databinding.FacilityItemLayoutBinding
import com.abhishek.radiustask.pojos.Facilities
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent

class FacilityAdapter(
    private var context: Context,
    private var list: MutableList<Facilities> = ArrayList(),
    private var listener: (Int, Int) -> Unit,
) : RecyclerView.Adapter<FacilityAdapter.ViewHolder?>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(FacilityItemLayoutBinding.inflate(LayoutInflater.from(parent.context),
            parent,
            false))


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.facilitiesName.text = list[position].name
        holder.binding.optionRecyclerView.adapter =
            OptionAdapter(context, list[position].options!!) {
                listener.invoke(list[position].facility_id!!, it)
            }
        val flexboxLayoutManager = FlexboxLayoutManager(context)
        flexboxLayoutManager.flexDirection = FlexDirection.ROW
        flexboxLayoutManager.justifyContent = JustifyContent.SPACE_AROUND
        holder.binding.optionRecyclerView.layoutManager = flexboxLayoutManager
    }

    override fun getItemCount() = list.size

    fun setData(facilities: List<Facilities>?) {
        this.list = facilities as MutableList<Facilities>
        notifyDataSetChanged()
    }

    class ViewHolder(var binding: FacilityItemLayoutBinding) : RecyclerView.ViewHolder(binding.root)

}