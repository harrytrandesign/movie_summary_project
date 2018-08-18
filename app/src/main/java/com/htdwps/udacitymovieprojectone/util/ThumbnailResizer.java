package com.htdwps.udacitymovieprojectone.util;

import android.content.Context;
import android.util.DisplayMetrics;

/**
 * Created by HTDWPS on 8/17/18.
 */
public class ThumbnailResizer {

    public static int calculateNoOfColumns(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        int scalingFactor = 200;
        int noOfColumns = (int) (dpWidth / scalingFactor);
        if(noOfColumns < 2)
            noOfColumns = 2;
        return noOfColumns;
    }

}
