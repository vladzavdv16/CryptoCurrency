package com.light.cryptocurrency.ui.rates

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.cryptocurrency.core.di.BaseComponent
import com.light.cryptocurrency.R
import com.light.cryptocurrency.databinding.FragmentRatesBinding
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject


class RatesFragment @Inject constructor(
    baseComponent: BaseComponent): Fragment() {

    private var binding: FragmentRatesBinding? = null
    private var adapter: RatesAdapter? = null
    private lateinit var viewModel: RatesViewModel
    private var component: RatesComponent = DaggerRatesComponent.builder()
        .baseComponent(baseComponent).build()

    private val disposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel =
            ViewModelProvider(requireParentFragment(), component.viewModelFactory()).get(RatesViewModel::class.java)
        adapter = component.ratesAdapter()
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHasOptionsMenu(true)
        binding = FragmentRatesBinding.bind(view)
        binding?.recycler?.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding?.recycler?.swapAdapter(adapter, false)
        binding?.recycler?.setHasFixedSize(true)
        binding?.refresher?.setOnRefreshListener(viewModel::refresh)
        disposable.add(viewModel.coins().subscribe({ coins ->
            adapter?.submitList(coins)
        }, {
           Toast.makeText(requireContext(), "$it", Toast.LENGTH_SHORT).show()
        }))
        disposable.add(viewModel.isRefreshing().subscribe{ isRefreshing ->
            binding?.refresher?.isRefreshing = isRefreshing })

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_rates, container, false)
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
        } else if (R.id.sort == item.itemId) {
            viewModel.switchOrderSorter()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroyView() {
        binding?.recycler?.swapAdapter(null, false)
        disposable.clear()
        super.onDestroyView()
    }

}