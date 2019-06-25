package com.adson.android.flickrapp.models

import androidx.lifecycle.LiveData
import androidx.paging.PagedList

/**
 * class that is used by the UI to observe both search results data and network errors
 */
data class FlickrSearchResult(
    val data: LiveData<PagedList<FlickrPhoto>>,
    val networkErrors: LiveData<String>
)