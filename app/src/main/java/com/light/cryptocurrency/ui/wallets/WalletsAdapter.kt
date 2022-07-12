package com.light.cryptocurrency.ui.wallets

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.light.cryptocurrency.BuildConfig
import com.light.cryptocurrency.data.model.Wallet
import com.light.cryptocurrency.databinding.LiWalletBinding
import com.light.cryptocurrency.util.formatter.BalanceFormatter
import com.light.cryptocurrency.util.formatter.PriceFormatter
import com.light.cryptocurrency.util.loader.ImageLoader
import com.light.cryptocurrency.widget.OutlineCircle
import java.util.*
import javax.inject.Inject

class WalletsAdapter @Inject constructor(
    private val priceFormatter: PriceFormatter,
    private val imageLoader: ImageLoader,
    private val balanceFormatter: BalanceFormatter
): ListAdapter<Wallet,WalletsAdapter.ViewHolder>(DiffUtilCallBack()) {

    private lateinit var inflater: LayoutInflater

    class DiffUtilCallBack : DiffUtil.ItemCallback<Wallet>() {
        override fun areItemsTheSame(oldItem: Wallet, newItem: Wallet): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Wallet, newItem: Wallet): Boolean {
            return Objects.equals(oldItem, newItem)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LiWalletBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val wallet = getItem(position)
        holder.binding.symbol.text = wallet.coin.symbol
        holder.binding.balance.text = balanceFormatter.format(wallet)
        val balance = wallet.balance!! * wallet.coin.price
        holder.binding.banknote.text = priceFormatter.format(wallet.coin.currencyCode, balance)
        imageLoader.load(BuildConfig.IMG_ENDPOINT + wallet.coin.id + ".png")
            .into(holder.binding.logo)
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        inflater = LayoutInflater.from(recyclerView.context)
    }

    class ViewHolder(val binding: LiWalletBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            OutlineCircle.apply(binding.logo)
        }
    }
}