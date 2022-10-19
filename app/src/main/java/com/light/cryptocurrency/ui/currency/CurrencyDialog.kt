package com.light.cryptocurrency.ui.currency

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.cryptocurrency.core.di.BaseComponent
import com.light.cryptocurrency.R
import com.light.cryptocurrency.converter.databinding.DialogCurrencyBinding
import com.cryptocurrency.core.widget.OnItemClick
import javax.inject.Inject

class CurrencyDialog @Inject constructor(
    baseComponent: BaseComponent): AppCompatDialogFragment() {

    private var binding: DialogCurrencyBinding? = null

    private var adapter: CurrencyAdapter? = null

    private lateinit var viewModel: CurrencyViewModel

    private var onItemClick: OnItemClick? = null

    private var component = DaggerCurrencyComponent.builder()
        .baseComponent(baseComponent).build()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel =
            ViewModelProvider(this, component.viewModelFactory()).get(CurrencyViewModel::class.java)

        adapter = CurrencyAdapter()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = DialogCurrencyBinding.inflate(layoutInflater)
        return MaterialAlertDialogBuilder(requireActivity())
            .setTitle(R.string.currency_menu)
            .setView(binding!!.root)
            .create()
    }


    @SuppressLint("MissingSuperCall")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding?.recycler?.layoutManager =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
        binding?.recycler?.adapter = adapter
        viewModel.allCurrency().observe(this, { currency ->
            adapter?.submitList(currency)
        })
        onItemClick = OnItemClick { view ->
            val viewHolder = binding!!.recycler.findContainingViewHolder(view)
            if (viewHolder != null) {
                viewModel.updateCurrency(adapter!!.getItem(viewHolder.adapterPosition))
            }
            dismissAllowingStateLoss()
        }
        binding!!.recycler.addOnItemTouchListener(onItemClick!!)
    }

    override fun onDestroyView() {
        binding!!.recycler.removeOnItemTouchListener(onItemClick!!)
        binding?.recycler?.adapter = null
        super.onDestroyView()
    }

}