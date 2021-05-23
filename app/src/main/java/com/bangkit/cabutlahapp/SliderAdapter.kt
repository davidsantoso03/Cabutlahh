package com.bangkit.cabutlahapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.makeramen.roundedimageview.RoundedImageView

class SliderAdapter(sliderItems: MutableList<Sliderdata>, viewPager2: ViewPager2) : RecyclerView.Adapter<SliderAdapter.SliderViewHolder>() {
    private val sliderItems: List<Sliderdata>
    private val viewPager2: ViewPager2

    init {
        this.sliderItems = sliderItems
        this.viewPager2 = viewPager2
    }

    class SliderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        private val imageView: RoundedImageView = itemView.findViewById(R.id.imageSlide)

        fun image(sliderItem: Sliderdata){
            imageView.setImageResource(sliderItem.image)
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SliderViewHolder {
        return SliderViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_slide,parent, false))
    }



    override fun getItemCount(): Int {
        return sliderItems.size
    }
    private val runnable = Runnable {
        sliderItems.addAll(sliderItems)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: SliderViewHolder, position: Int) {
        holder.image(sliderItems[position])
        if (position == sliderItems.size - 2){
            viewPager2.post(runnable)
        }
    }
}
