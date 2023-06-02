package com.light.cryptocurrency.ui.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.light.cryptocurrency.converter.BottomSheetCoin
import com.light.cryptocurrency.util.CoinsFragmentFactory
import dagger.Binds
import dagger.Module
import com.light.cryptocurrency.ui.currency.CurrencyDialog

import dagger.multibindings.ClassKey

import dagger.multibindings.IntoMap

import com.light.cryptocurrency.converter.ConverterFragment
import com.light.cryptocurrency.ui.profile.ProfileFragment

import com.light.cryptocurrency.ui.wallets.WalletsFragment

import com.light.cryptocurrency.ui.rates.RatesFragment


@Module
abstract class MainModule {

    @Binds
    abstract fun fragmentFactory(impl: CoinsFragmentFactory): FragmentFactory

    @Binds
    @IntoMap
    @ClassKey(RatesFragment::class)
    abstract fun ratesFragment(impl: RatesFragment): Fragment

    @Binds
    @IntoMap
    @ClassKey(WalletsFragment::class)
    abstract fun walletsFragment(impl: WalletsFragment?): Fragment?

    @Binds
    @IntoMap
    @ClassKey(ConverterFragment::class)
    abstract fun converterFragment(impl: ConverterFragment?): Fragment

    @Binds
    @IntoMap
    @ClassKey(CurrencyDialog::class)
    abstract fun currencyDialog(impl: CurrencyDialog): Fragment

    @Binds
    @IntoMap
    @ClassKey(BottomSheetCoin::class)
    abstract fun bottomSheetCoin(impl: BottomSheetCoin): Fragment

    @Binds
    @IntoMap
    @ClassKey(ProfileFragment::class)
    abstract fun profileFragment(impl: ProfileFragment?): Fragment

}