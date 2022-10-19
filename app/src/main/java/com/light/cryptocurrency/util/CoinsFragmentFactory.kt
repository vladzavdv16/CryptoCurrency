package com.light.cryptocurrency.util

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import androidx.lifecycle.ViewModel
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Provider

class CoinsFragmentFactory @Inject constructor(
    private val providers: Map<Class<*>, @JvmSuppressWildcards Provider<Fragment>>)
    : FragmentFactory() {


    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {

        try {
            val classKey: Class<*> = Class.forName(className)
            val provider: @JvmSuppressWildcards Provider<Fragment>? = providers[classKey]
            if (provider != null) return provider.get()
        } catch (e: ClassNotFoundException) {
            Timber.e(e)
        }
        return super.instantiate(classLoader, className)


    }
}