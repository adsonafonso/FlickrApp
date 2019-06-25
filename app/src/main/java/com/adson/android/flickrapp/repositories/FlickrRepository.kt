package com.adson.android.flickrapp.repositories

import androidx.lifecycle.MutableLiveData
import androidx.paging.LivePagedListBuilder
import com.adson.android.flickrapp.api.FlickrService
import com.adson.android.flickrapp.api.searchFlickr
import com.adson.android.flickrapp.db.FlickrLocalCache
import com.adson.android.flickrapp.models.FlickrSearchResult

class FlickrRepository (
    private val service: FlickrService,
    private val cache: FlickrLocalCache
) {

    // Last requested Page. If request is successful increase it.
    private var lastRequestedPage = 1

    // LiveData of network errors.
    private val networkErrors = MutableLiveData<String>()

    // avoid triggering multiple requests in the same time
    private var isRequestInProgress = false


    /**
     * Search FLickrApi for photos whose names match the query.
     */
    fun search(query: String): FlickrSearchResult {
        lastRequestedPage = 1
        requestAndSaveData(query)

        val dataSourceFactory = cache.photosByQuery(query)

        val data = LivePagedListBuilder(dataSourceFactory, 25).build() //only display 25 items at time

        return FlickrSearchResult(data, networkErrors)
    }

    fun requestMore(query: String) {
        requestAndSaveData(query)
    }

    private fun requestAndSaveData(query: String) {
        if (isRequestInProgress) return

        isRequestInProgress = true
        searchFlickr(service, query, ITEMS_PER_PAGE, lastRequestedPage, { photos ->
                cache.insert(photos) {
                    lastRequestedPage++
                    isRequestInProgress = false
                }
        }, { error ->
            networkErrors.postValue(error)
            isRequestInProgress = false
        })
    }

    companion object {
        private const val ITEMS_PER_PAGE = 30 //request 30 items per page
    }
}