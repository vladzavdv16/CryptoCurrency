package com.light.cryptocurrency.ui.welcome

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.light.cryptocurrency.R
import com.light.cryptocurrency.databinding.WelcomePageBinding

class WelcomeAdapter : RecyclerView.Adapter<WelcomeAdapter.ViewHolder>() {

    private lateinit var inflater: LayoutInflater

    private val title = arrayOf(
        (R.string.welcome_page_1_title),
        (R.string.welcome_page_2_title),
        (R.string.welcome_page_3_title)
    )
    private val subTitle = arrayOf(
        R.string.welcome_page_1_subtitle,
        R.string.welcome_page_2_subtitle,
        R.string.welcome_page_3_subtitle
    )
    private val image = arrayOf(
        R.drawable.welcome_page_1,
        R.drawable.welcome_page_2,
        R.drawable.welcome_page_3
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(WelcomePageBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.tvTitle.setText(title[position])
        holder.binding.tvSubTitle.setText(subTitle[position])
        holder.binding.imageView.setImageResource(image[position])
    }

    override fun getItemCount(): Int {
        return image.size
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)

        inflater = LayoutInflater.from(recyclerView.context)
    }


    class ViewHolder(val binding: WelcomePageBinding) :
        RecyclerView.ViewHolder(binding.root) {
    }


}
