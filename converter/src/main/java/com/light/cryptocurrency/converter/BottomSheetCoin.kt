package com.light.cryptocurrency.converter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.cryprocurrency.data.di.BaseComponent
import com.cryptocurrency.core.util.onClick
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.light.cryptocurrency.converter.databinding.DialogCurrencyBinding
import com.light.cryptocurrency.converter.di.ConverterComponent
import com.light.cryptocurrency.converter.di.DaggerConverterComponent
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class BottomSheetCoin @Inject constructor(
    baseComponent: BaseComponent
): BottomSheetDialogFragment() {

    companion object {
        const val KEY_MODE = "mode"
        const val MODE_FROM = 1
        const val MODE_TO = 2
    }

    private var viewModel: ConverterViewModel? = null
    private var binding: DialogCurrencyBinding? = null
    private var adapter: CoinsSheetAdapter? = null

    private var mode = 0

    private val disposable = CompositeDisposable()

    private val component: ConverterComponent =
        DaggerConverterComponent.builder().baseComponent(baseComponent).build()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(
            requireActivity(),
            component.viewModelFactory()
        ).get(ConverterViewModel::class.java)

        adapter = component.coinSheetAdapter()

        mode = requireArguments().getInt(KEY_MODE, MODE_FROM)
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_currency, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = DialogCurrencyBinding.bind(view)

        binding?.recycler?.layoutManager = LinearLayoutManager(view.context)
        binding?.recycler?.adapter = adapter
        disposable.add(viewModel?.topCoins()?.subscribe { adapter?.submitList(it) } ?: disposable)
        disposable.add(onClick(binding!!.recycler).map { position ->
            adapter?.getItem(position)
        }.subscribe {
            if (mode == MODE_FROM) {
                viewModel?.fromCoin(it!!)
            } else {
                viewModel?.toCoin(it!!)
            }
            dismissAllowingStateLoss()
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable.dispose()
    }
}