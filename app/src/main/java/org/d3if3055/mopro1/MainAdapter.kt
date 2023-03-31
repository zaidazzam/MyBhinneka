package org.d3if3055.mopro1

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import org.d3if3055.mopro1.databinding.ListItemBudayaBinding

class MainAdapter(private val data: List<Budaya>) : RecyclerView.Adapter<MainAdapter.ViewHolder>() {
    class ViewHolder(private val binding: ListItemBudayaBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(budaya: Budaya) = with(binding) {
            namaTextView.text = budaya.nama
            latinTextView.text = budaya.namaLatin
            imageView.setImageResource(budaya.imageResId)

            root.setOnClickListener {
                val message = root.context.getString(R.string.message, budaya.nama)
                Toast.makeText(root.context, message, Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemBudayaBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
    }
}