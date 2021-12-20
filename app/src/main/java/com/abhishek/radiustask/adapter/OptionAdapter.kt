package com.abhishek.radiustask.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.abhishek.radiustask.R
import com.abhishek.radiustask.databinding.OptionItemLayoutBinding
import com.abhishek.radiustask.pojos.Options

class OptionAdapter(
    private var context: Context,
    private var list: List<Options>,
    private var listener: (Int) -> Unit,
) :
    RecyclerView.Adapter<OptionAdapter.ViewHolder?>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(OptionItemLayoutBinding.inflate(LayoutInflater.from(parent.context),
            parent,
            false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.optionName.text = list[position].name
        holder.setStyle(list[position].selectType, context)
        holder.setImage(list[position].icon!!)
        holder.binding.optionName.setOnClickListener {
            listener.invoke(list[position].id!!)
        }
    }

    override fun getItemCount() = list.size

    class ViewHolder(var binding: OptionItemLayoutBinding) : RecyclerView.ViewHolder(binding.root) {

        fun setStyle(selectType: Int, context: Context?) {
            when (selectType) {
                1 -> {
                    binding.optionLayout.setBackgroundResource(R.drawable.option_background_selected)
                    binding.optionName.setTextColor(Color.WHITE)
                }
                2 -> {
                    binding.optionLayout.startAnimation(AnimationUtils.loadAnimation(context,
                        R.anim.shake_animation))
                    binding.optionName.setTextColor(Color.WHITE)
                    binding.optionLayout.setBackgroundResource(R.drawable.option_background_cannot_selected)
                }
                else -> {
                    binding.optionLayout.setBackgroundResource(R.drawable.option_background_not_selected)
                    binding.optionName.setTextColor(Color.parseColor("#273a84"))
                }
            }
        }

        fun setImage(image: String) {
            when (image) {
                "apartment" -> binding.optionIcon.setImageResource(R.drawable.apartment)
                "condo" -> binding.optionIcon.setImageResource(R.drawable.condo)
                "boat" -> binding.optionIcon.setImageResource(R.drawable.boat)
                "land" -> binding.optionIcon.setImageResource(R.drawable.land)
                "rooms" -> binding.optionIcon.setImageResource(R.drawable.rooms)
                "no-room" -> binding.optionIcon.setImageResource(R.drawable.no_room)
                "swimming" -> binding.optionIcon.setImageResource(R.drawable.swimming)
                "garden" -> binding.optionIcon.setImageResource(R.drawable.garden)
                "garage" -> binding.optionIcon.setImageResource(R.drawable.garage)
            }
        }

    }
}