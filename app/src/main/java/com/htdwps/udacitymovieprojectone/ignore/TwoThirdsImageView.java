package com.htdwps.udacitymovieprojectone.ignore;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

/**
 * Created by HTDWPS on 7/31/18.
 */
public class TwoThirdsImageView extends android.support.v7.widget.AppCompatImageView {
    public TwoThirdsImageView(Context context) {
        super(context);
    }

    public TwoThirdsImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TwoThirdsImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // Lesson 3 - Bold Graphic Design - Video 23
        // Takes a 2/3 aspect ratio against the width
        int threeTwoHeight = MeasureSpec.getSize(widthMeasureSpec) * 2/3;
        int threeTwoHeightSpec = MeasureSpec.makeMeasureSpec(threeTwoHeight, MeasureSpec.EXACTLY);

        super.onMeasure(widthMeasureSpec, threeTwoHeightSpec);

    }
}
