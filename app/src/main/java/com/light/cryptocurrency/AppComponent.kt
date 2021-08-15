package com.light.cryptocurrency

import android.app.Application
import android.content.Context
import android.provider.ContactsContract
import com.light.cryptocurrency.data.CoinsRepo
import com.light.cryptocurrency.data.CurrencyRepo
import com.light.cryptocurrency.data.DataModule
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [AppModule::class, DataModule::class]
)
abstract class AppComponent : BaseComponent {

    @Component.Builder
    abstract class Builder {
        @BindsInstance
        abstract fun application(app: Application): Builder

        abstract fun build(): AppComponent
    }
}