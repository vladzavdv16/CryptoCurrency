package com.light.cryptocurrency.ui.wallets

import android.graphics.Color
import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.light.cryptocurrency.R
import com.light.cryptocurrency.data.model.Transaction
import com.light.cryptocurrency.data.model.Wallet
import com.light.cryptocurrency.databinding.LiTransactionBinding
import com.light.cryptocurrency.util.formatter.BalanceFormatter
import com.light.cryptocurrency.util.formatter.PriceFormatter
import com.light.cryptocurrency.util.loader.ImageLoader
import com.light.cryptocurrency.widget.OutlineCircle
import java.util.*
import javax.inject.Inject

class TransactionAdapter @Inject constructor(
    private val priceFormatter: PriceFormatter,
    private val imageLoader: ImageLoader,
    private val balanceFormatter: BalanceFormatter
): ListAdapter<Transaction, TransactionAdapter.ViewHolder>(DiffUtilCallBack()) {

    private lateinit var inflater: LayoutInflater

    class DiffUtilCallBack: DiffUtil.ItemCallback<Transaction>() {
        override fun areItemsTheSame(oldItem: Transaction, newItem: Transaction): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Transaction, newItem: Transaction): Boolean {
            return Objects.equals(oldItem, newItem)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LiTransactionBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val transaction = getItem(position)
        holder.binding.balance.text = priceFormatter.format(transaction.amount!!)
        val banknote = transaction.amount * transaction.coin.price
        val resultBanknote = priceFormatter.format(transaction.coin.currencyCode, banknote)
        holder.binding.banknote.text = resultBanknote
        holder.binding.tvDate.text = DateFormat.getDateFormat(inflater.context).format(transaction.date)

        if (transaction.coin.change24h > 0) {
            holder.binding.arrow.isEnabled = true
            holder.binding.banknote.setTextColor(Color.GREEN)
        } else {
            holder.binding.arrow.isEnabled = false
            holder.binding.banknote.setTextColor(Color.RED)
        }
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        inflater = LayoutInflater.from(recyclerView.context)
    }

    class ViewHolder(val binding: LiTransactionBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.clipToOutline = true
        }
    }
}