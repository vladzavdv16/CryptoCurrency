package com.light.cryptocurrency.ui.wallets

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.light.cryptocurrency.databinding.LiWalletBinding

class WalletsAdapter : RecyclerView.Adapter<WalletsAdapter.ViewHolder>() {

    private lateinit var inflater: LayoutInflater


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WalletsAdapter.ViewHolder {
        return ViewHolder(LiWalletBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: WalletsAdapter.ViewHolder, position: Int) {
    }

    override fun getItemCount(): Int {
       return  7
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        inflater = LayoutInflater.from(recyclerView.context)
    }

    class ViewHolder(val binding: LiWalletBinding) : RecyclerView.ViewHolder(binding.root) {

    }
}