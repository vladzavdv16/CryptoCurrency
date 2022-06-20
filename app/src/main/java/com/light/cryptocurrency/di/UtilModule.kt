package com.light.cryptocurrency.di

import com.light.cryptocurrency.util.RxScheduler
import com.light.cryptocurrency.util.RxSchedulerImpl
import com.light.cryptocurrency.util.loader.ImageLoader
import com.light.cryptocurrency.util.loader.PicassoImageLoaderImpl
import dagger.Binds
import dagger.Module

@Module
abstract class UtilModule {

    @Binds
    abstract fun imageLoader(impl: PicassoImageLoaderImpl): ImageLoader

    @Binds
    abstract fun rxScheduler(impl: RxSchedulerImpl): RxScheduler
}