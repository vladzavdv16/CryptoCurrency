package com.light.cryptocurrency.converter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.cryptocurrency.core.data.mapper.EntityCoin
import com.cryptocurrency.core.util.Constants
import com.cryptocurrency.core.util.Constants.IMG_ENDPOINT
import com.cryptocurrency.core.util.ImageLoader
import com.cryptocurrency.core.widget.OutlineCircle
import com.light.cryptocurrency.converter.databinding.LiBottomSheetCoinBinding
import java.util.*
import javax.inject.Inject

/**
 * Created by Zavodov on 25.09.2022
 */
class CoinsSheetAdapter @Inject constructor(
    val imageLoader: ImageLoader
): ListAdapter<EntityCoin, CoinsSheetAdapter.ViewHolder>(DiffUtilCallBack()) {

    private lateinit var inflater: LayoutInflater

    class DiffUtilCallBack : DiffUtil.ItemCallback<EntityCoin>() {
        override fun areItemsTheSame(oldItem: EntityCoin, newItem: EntityCoin): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: EntityCoin, newItem: EntityCoin): Boolean {
            return Objects.equals(oldItem, newItem)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LiBottomSheetCoinBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val coin = getItem(position)
        holder.binding.name.text = coin.symbol + " | " + coin.name

        imageLoader
            .load(IMG_ENDPOINT + coin.id + ".png")
            .into(holder.binding.logo)
    }

    public override fun getItem(position: Int): EntityCoin {
        return super.getItem(position)
    }

    class ViewHolder(val binding: LiBottomSheetCoinBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            OutlineCircle.apply(binding.logo)
        }
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        inflater = LayoutInflater.from(recyclerView.context)
    }
}