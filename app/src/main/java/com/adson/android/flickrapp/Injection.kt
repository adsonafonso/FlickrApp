package com.adson.android.flickrapp

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import com.adson.android.flickrapp.api.FlickrService
import com.adson.android.flickrapp.db.FlickrDatabase
import com.adson.android.flickrapp.db.FlickrLocalCache
import com.adson.android.flickrapp.repositories.FlickrRepository
import com.example.android.codelabs.paging.ui.ViewModelFactory
import java.util.concurrent.Executors

object Injection {
    /**
     * Creates an instance of [FlickrLocalCache] based on the database DAO.
     */
    private fun provideCache(context: Context): FlickrLocalCache {
        val database = FlickrDatabase.getDatabase(context)
        return FlickrLocalCache(database.mFlickrDao(), Executors.newSingleThreadExecutor())
    }

    /**
     * Creates an instance of [FlickrRepository] based on the [FlickrService] and a
     * [FlickrLocalCache]
     */
    private fun provideFlickrRepository(context: Context): FlickrRepository {
        return FlickrRepository(FlickrService.create(), provideCache(context))
    }

    /**
     * Provides the [ViewModelProvider.Factory] that is used to get a reference to
     * [FlickrViewModel] objects.
     */
    fun provideViewModelFactory(context: Context): ViewModelProvider.Factory {
        return ViewModelFactory(provideFlickrRepository(context))
    }
}