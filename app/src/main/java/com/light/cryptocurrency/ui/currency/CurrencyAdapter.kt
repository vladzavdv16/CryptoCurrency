package com.light.cryptocurrency.ui.currency

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.cryptocurrency.core.data.model.Currency
import androidx.recyclerview.widget.RecyclerView

import android.view.LayoutInflater
import androidx.annotation.NonNull
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

    @NonNull
    override fun onCreateViewHolder(@NonNull parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LiCurrencyBinding.inflate(inflater!!, parent, false))
    }

    override fun onBindViewHolder(@NonNull holder: ViewHolder, position: Int) {
        val currency = getItem(position)
        holder.binding.title.text = currency.name
        holder.binding.symbol.text = currency.symbol
    }

    override fun onAttachedToRecyclerView(@NonNull recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        inflater = LayoutInflater.from(recyclerView.context)
    }

    class ViewHolder(@NonNull val binding: LiCurrencyBinding) :
        RecyclerView.ViewHolder(binding.root) {
    }
}