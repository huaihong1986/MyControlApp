package com.cloudtv.hahong.mycontrolapp.widget;


import android.content.Context;
import android.util.AttributeSet;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.cloudtv.hahong.mycontrolapp.util.DisplayUtils;
import com.cloudtv.hahong.mycontrolapp.volley.VolleyUtils;

import java.util.ArrayList;

public class HorizontalScrollImageView extends HorizontalScrollView {

    private Context mContext;
    private LinearLayout mLayout;

    public HorizontalScrollImageView(Context context) {
        super(context);
        init(context);
    }

    public HorizontalScrollImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public HorizontalScrollImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mContext = context;
        setHorizontalScrollBarEnabled(false);
        mLayout = new LinearLayout(context);
        mLayout.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        addView(mLayout, lp);
    }

    public void setImages(final ArrayList<String> urls) {
        if (urls == null) {
            return;
        }

        mLayout.removeAllViews();

        int screenWidth = DisplayUtils.getScreenWidth(mContext);
        int marginLR = DisplayUtils.dp2px(mContext, 16);
        int marginTB = DisplayUtils.dp2px(mContext, 8);
        int imgW = (screenWidth - 4 * marginLR - getPaddingLeft() - getPaddingRight()) / 2;
        int len = urls.size();

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(imgW, imgW);
        lp.setMargins(marginLR, marginTB, marginLR, marginTB);

        for (int i = 0; i < len; i++) {
            ImageView iv = new ImageView(mContext);
            iv.setMinimumWidth(imgW);
            iv.setMinimumHeight(imgW);
            iv.setMaxWidth(imgW);
            iv.setMaxHeight(imgW);
            iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
            //iv.setImageResource(R.mipmap.image_default);
            mLayout.addView(iv, lp);

            VolleyUtils.loadImage(urls.get(i), iv);
        }
    }

}
