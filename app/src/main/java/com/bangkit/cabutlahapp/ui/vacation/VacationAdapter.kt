package com.bangkit.cabutlahapp.ui.restaurant

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.cabutlahapp.R
import com.bangkit.cabutlahapp.data.model.Restaurant
import com.bangkit.cabutlahapp.data.model.Vacation
import com.bangkit.cabutlahapp.databinding.ItemListBinding
import com.bangkit.cabutlahapp.databinding.ItemVacayBinding
import com.bangkit.cabutlahapp.ui.Detail.DetailActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class VacationAdapter(private val vacayList : ArrayList<Vacation>) : RecyclerView.Adapter<VacationAdapter.ResViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ResViewHolder {
        val itemVacayBinding = ItemVacayBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ResViewHolder(itemVacayBinding)
    }

    inner class ResViewHolder(private val binding: ItemVacayBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(vacay: Vacation){
            with(binding){
                tvName.text =vacay.nama
                Glide.with(itemView.context)
                    .load(vacay.foto)
                    .apply(
                        RequestOptions.placeholderOf(R.drawable.ic_loading)
                            .error(R.drawable.ic_error)
                    )
                    .into(imgItem)

                itemView.setOnClickListener {
                    val intent = Intent(itemView.context, DetailActivity::class.java)
                    intent.putExtra(DetailActivity.EXTRA_TITLE, vacay.nama)
                    intent.putExtra(DetailActivity.EXTRA_TYPE, vacay.type)
                    itemView.context.startActivity(intent)
                }
            }
        }
    }

    override fun onBindViewHolder(holder: ResViewHolder, position: Int) {
        val data=  vacayList[position]
        holder.bind(data)

    }

    override fun getItemCount(): Int {
        return vacayList.size
    }
}