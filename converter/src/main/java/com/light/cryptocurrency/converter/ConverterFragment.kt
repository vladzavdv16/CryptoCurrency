package com.light.cryptocurrency.converter

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.cryptocurrency.core.di.BaseComponent
import com.jakewharton.rxbinding2.widget.RxTextView
import com.light.cryptocurrency.converter.BottomSheetCoin.Companion.KEY_MODE
import com.light.cryptocurrency.converter.BottomSheetCoin.Companion.MODE_FROM
import com.light.cryptocurrency.converter.BottomSheetCoin.Companion.MODE_TO
import com.light.cryptocurrency.converter.databinding.FragmentConverterBinding
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class ConverterFragment @Inject constructor(
    baseComponent: BaseComponent
): Fragment() {

    private var viewModel: ConverterViewModel? = null
    private var binding: FragmentConverterBinding? = null

    private val disposable = CompositeDisposable()

    private val component: ConverterComponent =
        DaggerConverterComponent.builder().baseComponent(baseComponent).build()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(
            this,
            component.viewModelFactory()
        ).get(ConverterViewModel::class.java)
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_converter, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentConverterBinding.bind(view)

        disposable.add(viewModel?.topCoins()!!.subscribe())

        binding?.fromCoin?.setOnClickListener {
            val args = Bundle()
            args.getInt(KEY_MODE, MODE_FROM)
            NavHostFragment.findNavController(this).navigate(R.id.bottomSheetCoin, args)
        }

        binding?.toCoin?.setOnClickListener {
            val args = Bundle()
            args.getInt(KEY_MODE, MODE_TO)
            NavHostFragment.findNavController(this).navigate(R.id.bottomSheetCoin, args)
        }

        disposable.add(RxTextView.textChanges(binding!!.from).subscribe {
            viewModel!!.fromValue(it)
        })
        disposable.add(RxTextView.textChanges(binding!!.to).subscribe {
            viewModel!!.toValue(it)
        })

        disposable.add(viewModel!!.fromValue()!!
            .distinctUntilChanged()
            .subscribe { text ->
                binding!!.from.setText(text)
                binding!!.from.setSelection(text!!.length)
            })
        disposable.add(viewModel!!.toValue()!!
            .distinctUntilChanged()
            .subscribe { text ->
                binding!!.to.setText(text)
                binding!!.to.setSelection(text.length)
            })
    }

    override fun onResume() {

        disposable.add(
            viewModel!!.fromCoin()!!.subscribe { coin ->
                binding!!.fromCoin.text = coin.symbol
            })

        disposable.add(
            viewModel!!.toCoin()!!.subscribe { coin ->
                binding!!.toCoin.text = coin.symbol
            })
        super.onResume()
    }
}