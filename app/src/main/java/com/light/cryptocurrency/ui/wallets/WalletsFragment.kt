package com.light.cryptocurrency.ui.wallets

import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import com.light.cryptocurrency.R
import com.light.cryptocurrency.databinding.FragmentWalletsBinding


class WalletsFragment : Fragment() {

    private lateinit var helper: SnapHelper

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_wallets, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentWalletsBinding.bind(view)

        val value = TypedValue()
        view.context.theme.resolveAttribute(R.attr.walletCardWidth, value, true)
        val displayMetrics = view.context.resources.displayMetrics
        val padding = (displayMetrics.widthPixels - value.getDimension(displayMetrics)).toInt() / 2
        binding.recycler.setPadding(padding, 0, padding, 0)
        binding.recycler.clipToPadding = false

        binding.recycler.addOnScrollListener(CarouselScroller())

        binding.recycler.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.recycler.adapter = WalletsAdapter()

        binding.card.visibility = View.GONE

        helper = PagerSnapHelper()
        helper.attachToRecyclerView(binding.recycler)
    }

    override fun onDestroyView() {
        super.onDestroyView()

        helper.attachToRecyclerView(null)
    }

    private class CarouselScroller : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            val centerX = (recyclerView.left + recyclerView.right) / 2
            for (i in 0 until recyclerView.childCount) {
                val child = recyclerView.getChildAt(i)
                val childCenterX = (child.left + child.right) / 2
                val childOffset = Math.abs(centerX - childCenterX) / centerX.toFloat()
                val factor = Math.pow(0.85, childOffset.toDouble()).toFloat()
                child.scaleX = factor
                child.scaleY = factor
            }
        }
    }

}