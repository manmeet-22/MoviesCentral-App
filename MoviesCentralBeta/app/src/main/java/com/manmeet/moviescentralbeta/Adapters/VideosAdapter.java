package com.manmeet.moviescentralbeta.Adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.manmeet.moviescentralbeta.R;
import com.manmeet.moviescentralbeta.Pojos.movie_images.Backdrops;
import com.manmeet.moviescentralbeta.Pojos.movie_videos.VideoResults;
import com.squareup.picasso.Picasso;

import java.util.List;

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
        int pos = position;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            holder.videoView.setImageAlpha(120);
        }
        try {
            Picasso.with(mContext).load("https://image.tmdb.org/t/p/w500" + mPhotosList.get(mPhotosList.size() - position - 1).getFilePath()).into(holder.videoView);
        } catch (Exception e){
            Picasso.with(mContext).load("https://image.tmdb.org/t/p/w500" + mPhotosList.get(mPhotosList.size() - pos - 1).getFilePath()).into(holder.videoView);
        }

    }

    @Override
    public int getItemCount() {
        if (mVideoList != null)
            return mVideoList.size();
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
