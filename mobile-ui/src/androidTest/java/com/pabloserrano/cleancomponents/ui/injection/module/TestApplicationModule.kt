package com.pabloserrano.cleancomponents.ui.injection.module

import android.app.Application
import android.content.Context
import com.nhaarman.mockito_kotlin.mock
import dagger.Module
import dagger.Provides
import com.pabloserrano.cleancomponents.cache.PreferencesHelper
import com.pabloserrano.cleancomponents.data.executor.JobExecutor
import com.pabloserrano.cleancomponents.data.repository.PhotosCache
import com.pabloserrano.cleancomponents.data.repository.PhotosRemote
import com.pabloserrano.cleancomponents.domain.executor.PostExecutionThread
import com.pabloserrano.cleancomponents.domain.executor.ThreadExecutor
import com.pabloserrano.cleancomponents.domain.repository.PhotoRepository
import com.pabloserrano.cleancomponents.remote.PhotoService
import com.pabloserrano.cleancomponents.ui.UiThread
import com.pabloserrano.cleancomponents.ui.injection.scopes.PerApplication

@Module
class TestApplicationModule {

    @Provides
    @PerApplication
    fun provideContext(application: Application): Context {
        return application
    }

    @Provides
    @PerApplication
    internal fun providePreferencesHelper(): PreferencesHelper {
        return mock()
    }

    @Provides
    @PerApplication
    internal fun providePhotoRepository(): PhotoRepository {
        return mock()
    }

    @Provides
    @PerApplication
    internal fun providePhotoCache(): PhotosCache {
        return mock()
    }

    @Provides
    @PerApplication
    internal fun providePhotoRemote(): PhotosRemote {
        return mock()
    }

    @Provides
    @PerApplication
    internal fun provideThreadExecutor(jobExecutor: JobExecutor): ThreadExecutor {
        return jobExecutor
    }

    @Provides
    @PerApplication
    internal fun providePostExecutionThread(uiThread: UiThread): PostExecutionThread {
        return uiThread
    }

    @Provides
    @PerApplication
    internal fun providePhotoService(): PhotoService {
        return mock()
    }

}