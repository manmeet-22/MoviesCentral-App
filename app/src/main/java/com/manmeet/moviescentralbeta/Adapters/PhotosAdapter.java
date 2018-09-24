package com.manmeet.moviescentralbeta.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.manmeet.moviescentralbeta.R;
import com.manmeet.moviescentralbeta.Pojos.movie_images.Backdrops;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by manmeet on 28/3/18.
 */

public class PhotosAdapter extends RecyclerView.Adapter<PhotosAdapter.PhotoHolder> {

    private Context mContext;
    private List<Backdrops> mList;

    public PhotosAdapter(Context context, List<Backdrops> list) {
        mList = list;
        mContext = context;
    }

    @Override
    public PhotoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new PhotoHolder(LayoutInflater.from(mContext).inflate(R.layout.photo_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(PhotoHolder holder, int position) {
        Backdrops image = mList.get(position);
        Picasso.with(mContext).load("https://image.tmdb.org/t/p/w500" + image.getFilePath()).into(holder.photoView);
    }

    @Override
    public int getItemCount() {
        if (mList != null)
            return mList.size();
        else
            return 0;
    }

    public class PhotoHolder extends RecyclerView.ViewHolder {
        ImageView photoView;

        public PhotoHolder(View itemView) {
            super(itemView);
            photoView = itemView.findViewById(R.id.imageView_detaill_movie_listItem);
        }
    }

}
