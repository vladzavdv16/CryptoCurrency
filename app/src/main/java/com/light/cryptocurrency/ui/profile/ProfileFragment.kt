package com.light.cryptocurrency.ui.profile

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.cryprocurrency.data.di.BaseComponent
import com.light.cryptocurrency.R
import com.light.cryptocurrency.databinding.FragmentProfileBinding
import javax.inject.Inject


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
                initOkDatabase(true)
            }, {
                initOkDatabase()
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            })
        }
    }

    private fun initOkDatabase(isSignOut: Boolean = false) {
        if (isSignOut) {
            binding?.email?.visibility = View.GONE
            binding?.parol?.visibility = View.GONE

            binding?.btnNext?.text = "Выход"
        } else {
            binding?.email?.visibility = View.VISIBLE
            binding?.parol?.visibility = View.VISIBLE

            binding?.btnNext?.text = "Продолжить"
        }
    }
}