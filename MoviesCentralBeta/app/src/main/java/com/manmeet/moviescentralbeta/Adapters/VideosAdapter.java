package com.manmeet.moviescentralbeta.Adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.manmeet.moviescentralbeta.Pojos.movie_images.Backdrops;
import com.manmeet.moviescentralbeta.Pojos.movie_videos.VideoResults;
import com.manmeet.moviescentralbeta.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Created by manmeet on 28/3/18.
 */

public class VideosAdapter extends RecyclerView.Adapter<VideosAdapter.VideoHolder> {

    private Context mContext;
    private List<VideoResults> mVideoList;
    private List<Backdrops> mPhotosList;

    public VideosAdapter(Context context, List<VideoResults> list, List<Backdrops> photosList) {
        mVideoList = list;
        mPhotosList = photosList;
        mContext = context;
    }

    @Override
    public VideoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new VideoHolder(LayoutInflater.from(mContext).inflate(R.layout.video_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(VideoHolder holder, int position) {
        final VideoResults videoResult = mVideoList.get(position);
        int pos = 0;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            holder.videoView.setImageAlpha(120);
        }
        //As there were noi thumbnails to the videos I decided to use the images that we get through backdrops as the thumbnails
        // for the videos but in reverse order.
        if ((mPhotosList.size() - position - 1) > -1) {
            //Log.d(TAG, "onBindViewHolder: Image Position - " + position + " == Pos - " + pos);
            Picasso.with(mContext).load("https://image.tmdb.org/t/p/w500" + mPhotosList.get(mPhotosList.size() - position - 1).getFilePath()).into(holder.videoView);
        }
    }

    @Override
    public int getItemCount() {
        // Normally we return size of the mVideoList, but there are some movies in which,
        // number of images are less than number of videos.
        // This causes the app to crash. Tried other ways but none worked.
        if (mPhotosList != null)
            return mPhotosList.size();
        else
            return 0;
    }

    public class VideoHolder extends RecyclerView.ViewHolder implements RecyclerView.OnClickListener {
        ImageView videoView;
        RelativeLayout layout;

        public VideoHolder(View itemView) {
            super(itemView);
            videoView = itemView.findViewById(R.id.imageView_listItem_videos);
            layout = itemView.findViewById(R.id.relativeLayout_video);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            VideoResults currVideo = mVideoList.get(getAdapterPosition());
            String key = currVideo.getKey();
            final String url = "https://www.youtube.com/watch?v=" + key;
            Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            v.getContext().startActivity(i);
        }
    }

}
