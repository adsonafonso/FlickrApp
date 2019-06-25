package com.adson.android.flickrapp.ui;

import android.os.Bundle;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import com.adson.android.flickrapp.R;
import com.adson.android.flickrapp.api.FlickrServiceKt;
import com.adson.android.flickrapp.models.FlickrPhoto;
import com.squareup.picasso.Picasso;

import static com.adson.android.flickrapp.ui.MainActivityKt.PHOTO_TRANSFER;

public class PhotoDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_detail);

        ImageView image = findViewById(R.id.photo_image);
        FlickrPhoto photo = getIntent().getExtras().getParcelable(PHOTO_TRANSFER);

        Picasso.get().load(FlickrServiceKt.getPhotoUrl(photo.getFarm(), photo.getServer(), photo.getId(), photo.getSecret()))
                .error(R.drawable.placeholder)
                .placeholder(R.drawable.placeholder)
                .into(image);
    }
}
