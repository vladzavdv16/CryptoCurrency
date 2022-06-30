package com.light.cryptocurrency.ui.rates

import android.graphics.Color
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.light.cryptocurrency.BuildConfig
import com.light.cryptocurrency.R
import com.light.cryptocurrency.data.mapper.EntityCoin
import com.light.cryptocurrency.databinding.LiRatesBinding
import com.light.cryptocurrency.util.loader.ImageLoader
import com.light.cryptocurrency.widget.OutlineCircle
import com.light.cryptocurrency.util.formatter.PercentFormatter
import com.light.cryptocurrency.util.formatter.PriceFormatter
import java.util.*
import javax.inject.Inject


class RatesAdapter @Inject constructor(
    private val priceFormatter: PriceFormatter,
    private val percentFormatter: PercentFormatter,
    private val imageLoader: ImageLoader
): ListAdapter<EntityCoin, RatesAdapter.ViewHolder>(DiffUtilCallBack()) {

    private var colorNegative = Color.RED
    private var colorPositive = Color.GREEN

    private var inflater: LayoutInflater? = null


    class DiffUtilCallBack : DiffUtil.ItemCallback<EntityCoin>() {
        override fun areItemsTheSame(oldItem: EntityCoin, newItem: EntityCoin): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: EntityCoin, newItem: EntityCoin): Boolean {
            return Objects.equals(oldItem, newItem)
        }

        override fun getChangePayload(oldItem: EntityCoin, newItem: EntityCoin): Any {
            return newItem
        }
    }

    override fun getItemId(position: Int): Long {
        return getItem(position).id.toLong()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LiRatesBinding.inflate(inflater!!, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val coin = getItem(position)
        holder.binding.symbol.text = coin.symbol
        holder.binding.price.text = priceFormatter.format(coin.currencyCode, coin.price)
        holder.binding.change.text = percentFormatter.format(coin.change24h)
        if (coin.change24h > 0) {
            holder.binding.change.setTextColor(colorPositive)
        } else {
            holder.binding.change.setTextColor(colorNegative)
        }

        imageLoader
            .load(BuildConfig.IMG_ENDPOINT + coin.id + ".png")
            .into(holder.binding.logo)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int, payloads: MutableList<Any>) {
        if (payloads.isEmpty()) {
            onBindViewHolder(holder, position)
        } else {
            val coin = payloads.get(0) as EntityCoin
            holder.binding.price.text = priceFormatter.format(coin.currencyCode, coin.price)
            holder.binding.change.text = percentFormatter.format(coin.change24h)
        }
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        val context = recyclerView.context
        inflater = LayoutInflater.from(context)
        val v = TypedValue()
        context.theme.resolveAttribute(R.attr.textPositive, v, true)
        colorPositive = v.data
        context.theme.resolveAttribute(R.attr.textNegative, v, true)
        colorNegative = v.data

    }

    class ViewHolder(val binding: LiRatesBinding) : RecyclerView.ViewHolder(binding.root) {

        init {
            OutlineCircle.apply(binding.logo)
        }
    }
}