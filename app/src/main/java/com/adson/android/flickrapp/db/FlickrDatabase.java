package com.adson.android.flickrapp.db;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import com.adson.android.flickrapp.models.FlickrPhoto;


@Database(entities = {FlickrPhoto.class}, version = 1)
public abstract class FlickrDatabase extends RoomDatabase {

    public abstract FlickrDao mFlickrDao();

    private static FlickrDatabase sInstance;

    /**
     * Make it a singleton to prevent having multiple instances of the database opened.
     * @param context
     * @return
     */
    public static FlickrDatabase getDatabase(final Context context) {
        if(sInstance == null) {
            synchronized ((FlickrDatabase.class)) {
                if(sInstance == null) {
                    sInstance = Room.databaseBuilder(context.getApplicationContext(),
                            FlickrDatabase.class, "flicrk_database")
                            .build();
                }
            }
        }
        return sInstance;
    }

}
