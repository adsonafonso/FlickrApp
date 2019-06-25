package com.adson.android.flickrapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.adson.android.flickrapp.models.FlickrPhoto
import com.adson.android.flickrapp.models.FlickrSearchResult
import com.adson.android.flickrapp.repositories.FlickrRepository


class FlickrViewModel(private val repository: FlickrRepository) : ViewModel() {

    companion object {
        private const val VISIBLE_THRESHOLD = 5
    }

    private val queryLiveData = MutableLiveData<String>()
    private val flickrResult: LiveData<FlickrSearchResult> = Transformations.map(queryLiveData) {
        repository.search(it)
    }

    val photos: LiveData<PagedList<FlickrPhoto>> = Transformations.switchMap(flickrResult) { it -> it.data }
    val networkErrors: LiveData<String> = Transformations.switchMap(flickrResult) { it ->
        it.networkErrors
    }

    /**
     * Search a repository based on a query string.
     */
    fun searchPhotos(queryString: String) {
        queryLiveData.postValue(queryString)
    }

    fun listScrolled(visibleItemCount: Int, lastVisibleItemPosition: Int, totalItemCount: Int) {
        if (visibleItemCount + lastVisibleItemPosition + VISIBLE_THRESHOLD >= totalItemCount) {
            val immutableQuery = lastQueryValue()
            if (immutableQuery != null) {
                repository.requestMore(immutableQuery)
            }
        }
    }

    fun lastQueryValue(): String? = queryLiveData.value
}