package com.manmeet.moviescentralbeta.Adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.manmeet.moviescentralbeta.Fragments.FragmentCast;
import com.manmeet.moviescentralbeta.Fragments.FragmentPhotos;
import com.manmeet.moviescentralbeta.Fragments.FragmentVideos;
import com.manmeet.moviescentralbeta.R;

public class CategoryAdapter extends FragmentPagerAdapter {

    private Context mContext;

    public CategoryAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new FragmentCast();
        } else if (position == 1) {
            return new FragmentPhotos();
        } else {
            return new FragmentVideos();
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0) {
            return mContext.getString(R.string.tools_movie_cast);
        } else if (position == 1) {
            return mContext.getString(R.string.tools_movie_photos);
        } else {
            return mContext.getString(R.string.tools_movie_videos);
        }
    }
}