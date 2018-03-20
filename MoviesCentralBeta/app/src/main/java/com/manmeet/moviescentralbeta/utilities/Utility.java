package com.manmeet.moviescentralbeta.utilities;

import android.content.Context;
import android.util.DisplayMetrics;

/**
 * Created by Manmeet on 24-Oct-17.
 */

public class Utility {
    public static int calculateNoOfColumns(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        int noOfColumns = (int) (dpWidth / 180);
        return noOfColumns;
    }}
