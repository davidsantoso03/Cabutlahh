package com.bangkit.cabutlahapp.ui.hotel

import android.content.Intent
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
import com.bangkit.cabutlahapp.ui.Detail.DetailActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class HotelAdapter(private val hotelList : ArrayList<Hotel>) : RecyclerView.Adapter<HotelAdapter.ResViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ResViewHolder {
       val itemsHotelBinding = ItemHotelBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ResViewHolder(itemsHotelBinding)
    }

    inner class ResViewHolder(private val binding: ItemHotelBinding) : RecyclerView.ViewHolder(binding.root) {
       fun bind(hotel:Hotel){
           with(binding){
               tvName.text =hotel.nama
               Glide.with(itemView.context)
                   .load(hotel.foto)
                   .apply(
                       RequestOptions.placeholderOf(R.drawable.ic_loading)
                           .error(R.drawable.ic_error)
                   )
                   .into(imgItem)

               itemView.setOnClickListener {
                   val intent = Intent(itemView.context, DetailActivity::class.java)
                   intent.putExtra(DetailActivity.EXTRA_TITLE, hotel.nama)
                   intent.putExtra(DetailActivity.EXTRA_TYPE, hotel.type)
                   itemView.context.startActivity(intent)
               }
           }
       }
    }

    override fun onBindViewHolder(holder: ResViewHolder, position: Int) {
        val hotels=  hotelList[position]
        holder.bind (hotels)


    }

    override fun getItemCount(): Int = hotelList.size
}