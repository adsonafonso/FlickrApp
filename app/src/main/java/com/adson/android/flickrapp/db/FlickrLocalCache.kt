package com.adson.android.flickrapp.db

import androidx.paging.DataSource
import com.adson.android.flickrapp.models.FlickrPhoto
import java.util.concurrent.Executor

class FlickrLocalCache(
    private val flickrDao: FlickrDao,
    private val ioExecutor: Executor
) {

    /**
     * Insert a list of FlickrPhotos in the database, on a background thread.
     */
    fun insert(photos: List<FlickrPhoto>, insertFinished: () -> Unit) {
        ioExecutor.execute {
            flickrDao.insert(photos)
            insertFinished()
        }
    }

    /**
     * Request a LiveData<List<FlickrPhoto>> from the Dao, based on the query search.
     * @param query FlickrQuerySearch
     */
    fun photosByQuery(query: String): DataSource.Factory<Int, FlickrPhoto> {
        // appending '%' so we can allow other characters to be before and after the query string
        val query = "%${query.replace(' ', '%')}%"
        return flickrDao.photosByQuery(query)
    }
}