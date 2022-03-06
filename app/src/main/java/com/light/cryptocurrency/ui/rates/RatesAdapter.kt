package com.light.cryptocurrency.ui.rates

import android.graphics.Color
import android.graphics.Outline
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewOutlineProvider
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.light.cryptocurrency.BuildConfig
import com.light.cryptocurrency.R
import com.light.cryptocurrency.data.Coin
import com.light.cryptocurrency.databinding.LiRatesBinding
import com.light.cryptocurrency.util.Formatter
import com.light.cryptocurrency.util.OutlineCircle
import com.light.cryptocurrency.util.PriceFormatter
import com.squareup.picasso.Picasso
import java.text.NumberFormat
import java.util.*


class RatesAdapter(private val priceFormatter: Formatter<Double>) :
    ListAdapter<Coin, RatesAdapter.ViewHolder>(DiffUtilCallBack()) {

    private var colorNegative = Color.RED
    private var colorPositive = Color.GREEN

    private var inflater: LayoutInflater? = null


    class DiffUtilCallBack : DiffUtil.ItemCallback<Coin>() {
        override fun areItemsTheSame(oldItem: Coin, newItem: Coin): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Coin, newItem: Coin): Boolean {
            return Objects.equals(oldItem, newItem)
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
//        holder.binding.price.text = priceFormatter.format(coin.price())
//        holder.binding.change.text = String.format(Locale.US, "%.2f%%", coin.change24h())
//        if (coin.change24h() > 0) {
//            holder.binding.change.setTextColor(colorPositive)
//        } else {
//            holder.binding.change.setTextColor(colorNegative)
//        }

        Picasso.get()
            .load(BuildConfig.IMG_ENDPOINT + coin.id + ".png")
            .into(holder.binding.logo)
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