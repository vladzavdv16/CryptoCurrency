package com.cryptocurrency.core

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * Created by Zavodov on 23.05.2023
 */
abstract class BaseViewModel: ViewModel() {

    val progressLiveData = MutableLiveData<Boolean>()


}