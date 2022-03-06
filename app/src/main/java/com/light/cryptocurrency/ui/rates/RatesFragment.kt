package com.light.cryptocurrency.ui.rates

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.light.cryptocurrency.BaseComponent
import com.light.cryptocurrency.R
import com.light.cryptocurrency.data.Coin
import com.light.cryptocurrency.databinding.FragmentRatesBinding
import com.light.cryptocurrency.util.PriceFormatter
import timber.log.Timber
import javax.inject.Inject


class RatesFragment @Inject constructor(baseComponent: BaseComponent) : Fragment() {

    private var binding: FragmentRatesBinding? = null
    private var adapter: RatesAdapter? = null
    private lateinit var viewModel: RatesViewModel
    private var component: RatesComponent = DaggerRatesComponent.builder()
        .baseComponent(baseComponent).build()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel =
            ViewModelProvider(this, component.viewModelFactory()).get(RatesViewModel::class.java)
        adapter = RatesAdapter(PriceFormatter<Double>())

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_rates, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHasOptionsMenu(true)
        binding = FragmentRatesBinding.bind(view)
        binding!!.recycler.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding!!.recycler.swapAdapter(adapter, false)
        viewModel.coins().observe(viewLifecycleOwner,
            { coins: List<Coin?>? -> adapter?.submitList(coins) })
        viewModel.isRefreshing().observe(viewLifecycleOwner, { isRefreshing ->
            binding!!.refresher.isRefreshing = isRefreshing
        })

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.rates, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (R.id.currency_dialog == item.itemId) {
            NavHostFragment.findNavController(this)
                .navigate(R.id.currencyDialog)
            return true
        }
        return super.onOptionsItemSelected(item)


    }

    override fun onDestroyView() {
        binding!!.recycler.swapAdapter(null, false)
        super.onDestroyView()
    }

}