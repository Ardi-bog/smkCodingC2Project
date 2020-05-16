package com.example.bgsmkcoding

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.news_item.*

class NewsAdapter(private val context: Context, private val items: List<DataProvItem>, private val listener: (DataProvItem)->Unit):
    RecyclerView.Adapter<NewsAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    )= ViewHolder(
        context,
        LayoutInflater.from(context).inflate(
            R.layout.news_item,
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
        fun bindItem(item: DataProvItem, listener: (DataProvItem) -> Unit){
            txtProvinsi.text = item.attributes.provinsi
            txtConfirmed.text = item.attributes.kasusPosi.toString()
            txtRecovered.text = item.attributes.kasusSemb.toString()
            txtDeaths.text = item.attributes.kasusMeni.toString()

//            Glide.with(context).load("https://www.countryflags.io/"+item.iso2+"/flat/32.png").into(imgCountry)

            containerView.setOnClickListener{listener(item)}
        }
    }
}