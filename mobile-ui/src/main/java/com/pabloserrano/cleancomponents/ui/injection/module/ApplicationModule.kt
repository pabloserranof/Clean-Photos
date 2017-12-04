package com.pabloserrano.cleancomponents.ui.injection.module

import android.app.Application
import android.arch.lifecycle.ViewModelProvider
import android.arch.persistence.room.Room
import android.content.Context
import com.pabloserrano.cleancomponents.ui.BuildConfig
import dagger.Module
import dagger.Provides
import com.pabloserrano.cleancomponents.cache.PhotosCacheImpl
import com.pabloserrano.cleancomponents.cache.PreferencesHelper
import com.pabloserrano.cleancomponents.cache.db.PhotosDatabase
import com.pabloserrano.cleancomponents.data.PhotoDataRepository
import com.pabloserrano.cleancomponents.data.executor.JobExecutor
import com.pabloserrano.cleancomponents.data.repository.PhotosCache
import com.pabloserrano.cleancomponents.data.repository.PhotosRemote
import com.pabloserrano.cleancomponents.data.source.PhotoDataStoreFactory
import com.pabloserrano.cleancomponents.domain.executor.PostExecutionThread
import com.pabloserrano.cleancomponents.domain.executor.ThreadExecutor
import com.pabloserrano.cleancomponents.domain.repository.PhotoRepository
import com.pabloserrano.cleancomponents.remote.PhotosRemoteImpl
import com.pabloserrano.cleancomponents.remote.PhotoService
import com.pabloserrano.cleancomponents.remote.PhotoServiceFactory
import com.pabloserrano.cleancomponents.cache.mapper.PhotoEntityMapper
import com.pabloserrano.cleancomponents.data.mapper.PhotoMapper
import com.pabloserrano.cleancomponents.ui.UiThread
import com.pabloserrano.cleancomponents.ui.injection.scopes.PerApplication

/**
 * Module used to provide dependencies at an application-level.
 */
@Module
open class ApplicationModule {

    @Provides
    @PerApplication
    fun provideContext(application: Application): Context {
        return application
    }

    @Provides
    @PerApplication
    internal fun providePreferencesHelper(context: Context): PreferencesHelper {
        return PreferencesHelper(context)
    }


    @Provides
    @PerApplication
    internal fun providePhotoRepository(factory: PhotoDataStoreFactory,
                                           mapper: PhotoMapper): PhotoRepository {
        return PhotoDataRepository(factory, mapper)
    }

    @Provides
    @PerApplication
    internal fun providePhotoCache(database: PhotosDatabase,
                                      photoEntityMapper: PhotoEntityMapper,
                                      helper: PreferencesHelper): PhotosCache {
        return PhotosCacheImpl(database, photoEntityMapper, helper)
    }

    @Provides
    @PerApplication
    internal fun providePhotoRemote(service: PhotoService,
                                    factory: com.pabloserrano.cleancomponents.remote.mapper.PhotoEntityMapper): PhotosRemote {
        return PhotosRemoteImpl(service, factory)
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
        return PhotoServiceFactory.makePhotoService(BuildConfig.DEBUG)
    }

    @Provides
    @PerApplication
    internal fun provideViewModelFactory(): ViewModelProvider.Factory {
        return ViewModelProvider.NewInstanceFactory()
    }

    @Provides
    @PerApplication
    internal fun providePhotosDatabase(application: Application): PhotosDatabase {
        return Room.databaseBuilder(application.applicationContext,
                PhotosDatabase::class.java, "photos.db")
                .build()
    }

}
