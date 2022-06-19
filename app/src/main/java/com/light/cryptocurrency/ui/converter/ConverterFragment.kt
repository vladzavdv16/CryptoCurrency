package com.light.cryptocurrency.ui.converter

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.light.cryptocurrency.R
import javax.inject.Inject


class ConverterFragment @Inject constructor() : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_converter, container, false)
    }

    override fun onResume() {
        super.onResume()

        println("Converter")
    }

}