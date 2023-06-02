package com.light.cryptocurrency.ui.wallets

import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import com.light.cryptocurrency.R
import com.light.cryptocurrency.databinding.FragmentWalletsBinding
import com.cryptocurrency.core.di.BaseComponent
import com.cryptocurrency.core.util.onSnap
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject


class WalletsFragment @Inject constructor(
    baseComponent: BaseComponent
) : Fragment() {

    private var walletsAdapter: WalletsAdapter? = null
    private var transactionAdapter: TransactionAdapter? = null
    private var viewModel: WalletsViewModel? = null

    private lateinit var walletsSnapHelper: SnapHelper

    private var binding: FragmentWalletsBinding? = null

    private val disposable = CompositeDisposable()

    private val component = DaggerWalletsComponent.builder()
        .baseComponent(baseComponent).build()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel =
            ViewModelProvider(this, component.viewModelFactory()).get(WalletsViewModel::class.java)
        walletsAdapter = component.walletsAdapter()
        transactionAdapter = component.transactionsAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_wallets, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentWalletsBinding.bind(view)
        walletsSnapHelper = PagerSnapHelper()
        walletsSnapHelper.attachToRecyclerView(binding!!.recycler)

        val value = TypedValue()
        view.context.theme.resolveAttribute(R.attr.walletCardWidth, value, true)
        val displayMetrics = view.context.resources.displayMetrics
        val padding = (displayMetrics.widthPixels - value.getDimension(displayMetrics)).toInt() / 2
        binding!!.recycler.setPadding(padding, 0, padding, 0)
        binding!!.recycler.clipToPadding = false
        binding!!.recycler.addOnScrollListener(CarouselScroller())

        disposable.add(onSnap
            (binding!!.recycler, walletsSnapHelper)
            .subscribe { position ->
                viewModel?.changeWallet(position)
            })
        binding!!.recycler.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding!!.recycler.adapter = walletsAdapter
        viewModel?.wallets()?.subscribe { wallets ->
            walletsAdapter?.submitList(wallets) }?.let {
            disposable.add(it)
        }
        disposable.add(viewModel!!.wallets().map { wallets -> wallets.isEmpty() }
            .subscribe { isEmpty ->
                if (isEmpty) {
                    binding!!.card.visibility = View.VISIBLE
                    binding!!.recycler.visibility = View.GONE
                } else {
                    binding!!.card.visibility = View.GONE
                    binding!!.recycler.visibility = View.VISIBLE
                }
            })
        binding!!.transactions.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding!!.transactions.adapter = transactionAdapter
        disposable
            .add(viewModel?.transactions()!!.subscribe { transactions ->
                transactionAdapter!!.submitList(transactions)
            })
    }

    override fun onDestroyView() {
        binding?.recycler?.adapter = null
        super.onDestroyView()
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