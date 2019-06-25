package com.adson.android.flickrapp.models
import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "photos")
data class FlickrPhoto(
        @PrimaryKey @field:SerializedName("id")val id: String,
        @field:SerializedName("owner")val owner: String,
        @field:SerializedName("secret")val secret: String,
        @field:SerializedName("server")val server: String,
        @field:SerializedName("farm")val farm: Int,
        @field:SerializedName("title")val title: String,
        @field:SerializedName("ispublic")val ispublic: Int,
        @field:SerializedName("isfriend")val isfriend: Int,
        @field:SerializedName("isfamily")val isfamily: Int,
        var querySearchStr: String
) : Parcelable {
        constructor(parcel: Parcel) : this(
                parcel.readString(),
                parcel.readString(),
                parcel.readString(),
                parcel.readString(),
                parcel.readInt(),
                parcel.readString(),
                parcel.readInt(),
                parcel.readInt(),
                parcel.readInt(),
                parcel.readString()
        ) {
        }

        override fun writeToParcel(parcel: Parcel, flags: Int) {
                parcel.writeString(id)
                parcel.writeString(owner)
                parcel.writeString(secret)
                parcel.writeString(server)
                parcel.writeInt(farm)
                parcel.writeString(title)
                parcel.writeInt(ispublic)
                parcel.writeInt(isfriend)
                parcel.writeInt(isfamily)
                parcel.writeString(querySearchStr)
        }

        override fun describeContents(): Int {
                return 0
        }

        companion object CREATOR : Parcelable.Creator<FlickrPhoto> {
                override fun createFromParcel(parcel: Parcel): FlickrPhoto {
                        return FlickrPhoto(parcel)
                }

                override fun newArray(size: Int): Array<FlickrPhoto?> {
                        return arrayOfNulls(size)
                }
        }

}
