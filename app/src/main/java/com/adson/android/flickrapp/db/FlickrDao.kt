package com.adson.android.flickrapp.db

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.adson.android.flickrapp.models.FlickrPhoto

/**
 * Room data access object for accessing the [FlickrPhoto] table.
 * The database is the main source of truth for the UI.
 * It represents the source for the PagedList.
 */
@Dao
interface FlickrDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(posts: List<FlickrPhoto>)

    // Does a similar query as the search API:
    // Look for photos that contain the querystring in the querySearchStr column
    @Query("SELECT * FROM photos WHERE (querySearchStr LIKE :queryString)")
    fun photosByQuery(queryString: String): DataSource.Factory<Int, FlickrPhoto>
}