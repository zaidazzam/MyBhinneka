package com.d3if3055.assesment2.ui.suku

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.d3if3055.assesment2.R
import com.d3if3055.assesment2.databinding.ListItemBinding
import com.d3if3055.assesment2.model.Suku
import com.d3if3055.assesment2.network.ServiceAPI


class SukuAdapter : RecyclerView.Adapter<SukuAdapter.ViewHolder>(){

    private val data = mutableListOf<Suku>()

    fun updateData(newData: List<Suku>){
        data.clear()
        data.addAll(newData)
        notifyDataSetChanged()
    }
    class ViewHolder(private val binding: ListItemBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(suku : Suku) = with(binding){
            judulTextView.text = suku.judul
            tglTextView.text = suku.tgl
            Glide.with(imageView.context)
                .load(ServiceAPI.getSukuUrl(suku.imageId))
                .error(R.drawable.baseline_broken_image_24)
                .into(imageView)
            tempatTextView.text = suku.tempat
            deskripsiTextView.text = suku.deskripsi


            root.setOnClickListener{
                val message = root.context.getString(R.string.message, suku.judul)
                Toast.makeText(root.context, message, Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
    }
}