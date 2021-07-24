package com.light.cryptocurrency.ui.rates

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.light.cryptocurrency.R
import com.light.cryptocurrency.data.Coin
import com.light.cryptocurrency.databinding.FragmentRatesBinding


class RatesFragment : Fragment(), RatesView {

    private var binding: FragmentRatesBinding? = null
    private var presenter: RatesPresenter? = null

    override fun onCreate(@Nullable savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter = RatesPresenter()
    }

    @Nullable
    override fun onCreateView(
        @NonNull inflater: LayoutInflater,
        @Nullable container: ViewGroup?,
        @Nullable savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_rates, container, false)
    }

    override fun onViewCreated(@NonNull view: View, @Nullable savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentRatesBinding.bind(view)
        binding!!.recycler.layoutManager = LinearLayoutManager(view.context)
        binding!!.recycler.setHasFixedSize(true)
        presenter?.attach(this)
    }

    override fun onDestroyView() {
        binding?.recycler?.adapter = null
        presenter?.detach(this)
        super.onDestroyView()
    }

    override fun showCoins(@NonNull coins: List<Coin?>?) {
        println("showcoins")
        binding?.recycler?.adapter = RatesAdapter(coins)
    }

    override fun showError(@NonNull error: String?) {}
}