package com.light.cryptocurrency.ui.rates

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.light.cryptocurrency.data.Coin
import com.light.cryptocurrency.databinding.LiRatesAdapterBinding
import kotlin.math.roundToLong


internal class RatesAdapter(coins: List<Coin?>?) :
    RecyclerView.Adapter<RatesAdapter.ViewHolder>() {

    private lateinit var inflater: LayoutInflater
    private val coins: List<Coin?>?

    init {
        setHasStableIds(true)
        this.coins = coins
    }

    override fun getItemId(position: Int): Long {
        return coins?.get(position)?.id?.toLong()!!
    }

    @NonNull
    override fun onCreateViewHolder(@NonNull parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LiRatesAdapterBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(@NonNull holder: ViewHolder, position: Int) {
        holder.bind(coins?.get(position))
    }

    override fun getItemCount(): Int {
        return coins?.size!!
    }

    override fun onAttachedToRecyclerView(@NonNull recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        inflater = LayoutInflater.from(recyclerView.context)
    }

    class ViewHolder(@NonNull private val binding: LiRatesAdapterBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(coin: Coin?) {
            binding.symbol.text = coin!!.symbol
        }

    }
}