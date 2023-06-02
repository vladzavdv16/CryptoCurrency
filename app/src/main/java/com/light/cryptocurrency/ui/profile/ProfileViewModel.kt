package com.light.cryptocurrency.ui.profile

import android.util.Log
import androidx.lifecycle.ViewModel
import javax.inject.Inject
import com.cryptocurrency.core.data.repository.ProfileRepo
import java.lang.Exception


class ProfileViewModel @Inject constructor(
    private val profileRepo: ProfileRepo
) : ViewModel() {

    fun initDatabase(email: String, userFullName: String, onSuccess: ()-> Unit, onFailure: (String) -> Unit) {
        try {
            profileRepo.connectToDatabase(email, userFullName, { onSuccess() }, {
                Log.d(
                    "Zavodov",
                    "ProfileViewModel initDatabase: $it"
                )
            })
        }catch (e: Exception) {
            onFailure(e.message.toString())
            Log.d("Zavodov", "ProfileViewModel initDatabase: ${e.message}")
        }
    }

}