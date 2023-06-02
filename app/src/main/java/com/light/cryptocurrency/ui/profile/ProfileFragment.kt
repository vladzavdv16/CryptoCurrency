package com.light.cryptocurrency.ui.profile

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.cryptocurrency.core.di.BaseComponent
import com.light.cryptocurrency.R
import com.light.cryptocurrency.databinding.FragmentProfileBinding
import com.light.cryptocurrency.databinding.FragmentWalletsBinding
import com.light.cryptocurrency.ui.wallets.DaggerWalletsComponent
import com.light.cryptocurrency.ui.wallets.WalletsViewModel
import javax.inject.Inject
import kotlin.system.measureTimeMillis


class ProfileFragment @Inject constructor(
    baseComponent: BaseComponent): Fragment(R.layout.fragment_profile) {

    private var binding: FragmentProfileBinding? = null

    private var viewModel: ProfileViewModel? = null

    private val component by lazy { DaggerProfileComponent.builder()
        .baseComponent(baseComponent).build() }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel =
            ViewModelProvider(this, component.viewModelFactory()).get(ProfileViewModel::class.java)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentProfileBinding.bind(view)

        binding?.btnNext?.setOnClickListener {
            validateFields()
        }
    }


    private fun validateFields() {

        val email = binding?.email?.text.toString()

        val name = binding?.parol?.text.toString()

        if (email.isNotEmpty() && name.isNotEmpty()) {

            viewModel?.initDatabase(email, name, {
                Toast.makeText(requireContext(), "INIT OK", Toast.LENGTH_SHORT).show()
            }, {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            })
        }
    }

}