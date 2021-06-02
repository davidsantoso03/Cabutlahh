package com.bangkit.cabutlahapp.ui.restaurant

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.cabutlahapp.R
import com.bangkit.cabutlahapp.data.model.Hotel
import com.bangkit.cabutlahapp.data.model.Restaurant
import com.bangkit.cabutlahapp.databinding.ItemHotelBinding
import com.bangkit.cabutlahapp.databinding.ItemListBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class RestaurantAdapter(private val restaurantList : ArrayList<Restaurant>) : RecyclerView.Adapter<RestaurantAdapter.ResViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ResViewHolder {
        val itemRestaurantBinding = ItemListBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ResViewHolder(itemRestaurantBinding)
    }

    inner class ResViewHolder(private val binding: ItemListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(resto: Restaurant){
            with(binding){
                tvName.text =resto.nama
                Glide.with(itemView.context)
                    .load(resto.foto)
                    .apply(
                        RequestOptions.placeholderOf(R.drawable.ic_loading)
                            .error(R.drawable.ic_error)
                    )
                    .into(imgItem)
            }
        }
    }

    override fun onBindViewHolder(holder: ResViewHolder, position: Int) {
      val restaurants=  restaurantList[position]
      holder.bind(restaurants)

    }

    override fun getItemCount(): Int {
        return restaurantList.size
    }
}