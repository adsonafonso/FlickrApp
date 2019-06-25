package com.adson.android.flickrapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;
import com.adson.android.flickrapp.R;
import com.adson.android.flickrapp.api.FlickrServiceKt;
import com.adson.android.flickrapp.models.FlickrPhoto;
import com.squareup.picasso.Picasso;

public class PhotosAdapter extends PagedListAdapter<FlickrPhoto, PhotosAdapter.PhotoViewHolder> {

    public PhotosAdapter() {
        super(DIFF_CALLBACK);
    }

    @NonNull
    @Override
    public PhotoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.photo_row_item, parent, false);
        return new PhotoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PhotoViewHolder holder, int position) {
        FlickrPhoto photo = getItem(position);
        if (photo != null) {
            holder.title.setText(photo.getTitle());

            Picasso.get()
                    .load(FlickrServiceKt.getPhotoUrl(photo.getFarm(), photo.getServer(), photo.getId(), photo.getSecret()))
                    .fit()
                    .centerCrop()
                    .error(R.drawable.placeholder)
                    .placeholder(R.drawable.placeholder)
                    .into(holder.imageView);
        }
    }

    private static DiffUtil.ItemCallback<FlickrPhoto> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<FlickrPhoto>() {
                @Override
                public boolean areItemsTheSame(FlickrPhoto oldPhoto, FlickrPhoto newPhoto) {
                    return oldPhoto.getId().equals(newPhoto.getId());
                }

                @Override
                public boolean areContentsTheSame(FlickrPhoto oldPhoto,
                                                  FlickrPhoto newPhoto) {
                    return oldPhoto.equals(newPhoto);
                }
            };

    public class PhotoViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        ImageView imageView;

        public PhotoViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            imageView = itemView.findViewById(R.id.thumbnail);
        }
    }
}
