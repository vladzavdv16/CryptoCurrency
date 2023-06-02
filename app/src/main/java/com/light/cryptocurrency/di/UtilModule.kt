package com.light.cryptocurrency.di

import com.cryptocurrency.core.util.RxScheduler
import com.light.cryptocurrency.util.RxSchedulerImpl
import com.cryptocurrency.core.util.ImageLoader
import com.light.cryptocurrency.util.loader.PicassoImageLoaderImpl
import com.cryptocurrency.core.util.Notifier
import com.light.cryptocurrency.util.notifier.NotifierImpl
import dagger.Binds
import dagger.Module

@Module
abstract class UtilModule {

    @Binds
    abstract fun imageLoader(impl: PicassoImageLoaderImpl): ImageLoader

    @Binds
    abstract fun rxScheduler(impl: RxSchedulerImpl): RxScheduler

    @Binds
    abstract fun notifier(impl: NotifierImpl): Notifier
}