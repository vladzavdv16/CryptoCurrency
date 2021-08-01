package com.light.cryptocurrency.ui.currency

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.light.cryptocurrency.R
import com.light.cryptocurrency.data.Currency
import com.light.cryptocurrency.data.CurrencyRepo
import com.light.cryptocurrency.data.CurrencyRepoImpl
import com.light.cryptocurrency.databinding.DialogCurrencyBinding

class CurrencyDialog : AppCompatDialogFragment() {

    private var binding: DialogCurrencyBinding? = null

    private var currencyRepo: CurrencyRepo? = null

    private var adapter: CurrencyAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        adapter = CurrencyAdapter()
        currencyRepo = CurrencyRepoImpl(requireActivity())
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = DialogCurrencyBinding.inflate(requireActivity().layoutInflater)
        return MaterialAlertDialogBuilder(requireActivity())
            .setTitle(R.string.currency_menu)
            .setView(binding!!.root)
            .create()
    }

    override fun onResume() {
        super.onResume()
        binding?.recycler?.layoutManager =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
        binding?.recycler?.adapter = adapter
        currencyRepo?.availableCurrencies()
            ?.observe(this, { currency: List<Currency> ->
                adapter?.submitList(currency)
            })
    }

    override fun onDestroyView() {
        binding?.recycler?.adapter = null
        super.onDestroyView()
    }

}