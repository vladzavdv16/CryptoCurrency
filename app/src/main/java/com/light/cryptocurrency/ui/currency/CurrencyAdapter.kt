package com.light.cryptocurrency.ui.currency

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

import android.view.LayoutInflater
import com.cryprocurrency.data.model.Currency
import com.light.cryptocurrency.databinding.LiCurrencyBinding
import java.util.*


class CurrencyAdapter : ListAdapter<Currency, CurrencyAdapter.ViewHolder?>(DiffUtilCallback()) {

    class DiffUtilCallback : DiffUtil.ItemCallback<Currency>() {
        override fun areItemsTheSame(oldItem: Currency, newItem: Currency): Boolean {
            return Objects.equals(oldItem, newItem)
        }

        override fun areContentsTheSame(oldItem: Currency, newItem: Currency): Boolean {
            return Objects.equals(oldItem, newItem)
        }

    }

    private var inflater: LayoutInflater? = null
    public override fun getItem(position: Int): Currency {
        return super.getItem(position)
    }

    override fun onCreateViewHolder( parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LiCurrencyBinding.inflate(inflater!!, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currency = getItem(position)
        holder.binding.title.text = currency.name
        holder.binding.symbol.text = currency.symbol
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        inflater = LayoutInflater.from(recyclerView.context)
    }

    class ViewHolder(val binding: LiCurrencyBinding) :
        RecyclerView.ViewHolder(binding.root) {
    }
}