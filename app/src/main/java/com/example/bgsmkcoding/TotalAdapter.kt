package com.example.bgsmkcoding

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.total_item.*

class TotalAdapter (private val context: Context, private val items: List<TotalDataItem>, private val listener: (TotalDataItem)->Unit):
    RecyclerView.Adapter<TotalAdapter.ViewHolder>(){
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    )= ViewHolder(
        context,
        LayoutInflater.from(context).inflate(
            R.layout.total_item,
            parent,
            false
        )
    )

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(items.get(position), listener)
    }

    class ViewHolder(val context: Context, override val containerView: View): RecyclerView.ViewHolder(containerView),
        LayoutContainer {
        fun bindItem(item: TotalDataItem, listener: (TotalDataItem) -> Unit){
            txtCountry.text = item.name
            txtConfirmed.text = item.positif.toString()
            txtRecovered.text = item.sembuh.toString()
            txtDeaths.text = item.meninggal.toString()

//            Glide.with(context).load("https://www.countryflags.io/"+item.iso2+"/flat/32.png").into(imgCountry)

            containerView.setOnClickListener{listener(item)}
        }
    }
}