package com.light.cryptocurrency.ui.wallets

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.light.cryptocurrency.data.model.Wallet
import com.light.cryptocurrency.databinding.LiWalletBinding
import com.light.cryptocurrency.util.formatter.PriceFormatter
import java.util.*
import javax.inject.Inject

class WalletsAdapter @Inject constructor(
    private val priceFormatter: PriceFormatter
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
        holder.binding.balance.text = wallet.balance?.let { priceFormatter.format(it) }
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        inflater = LayoutInflater.from(recyclerView.context)
    }

    class ViewHolder(val binding: LiWalletBinding) : RecyclerView.ViewHolder(binding.root) {

    }
}