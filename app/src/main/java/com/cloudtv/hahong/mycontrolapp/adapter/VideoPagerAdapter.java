package com.cloudtv.hahong.mycontrolapp.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.cloudtv.hahong.mycontrolapp.model.MainListData;
import com.cloudtv.hahong.mycontrolapp.util.ListUtils;
import com.cloudtv.hahong.mycontrolapp.volley.VolleyUtils;

import java.util.List;


public class VideoPagerAdapter extends RecyclingPagerAdapter {

    private Context context;
    private List<MainListData.VideoListEntity> list;

    private int size;
    private boolean isInfiniteLoop;

    public VideoPagerAdapter(Context context, List<MainListData.VideoListEntity> list) {
        this.context = context;
        this.list = list;
        this.size = ListUtils.getSize(list);
        isInfiniteLoop = false;
    }

    @Override
    public int getCount() {
        return isInfiniteLoop ? Integer.MAX_VALUE : ListUtils.getSize(list);
    }

    private int getPosition(int position) {
        return isInfiniteLoop ? position % size : position;
    }

    @Override
    public View getView(int position, View view, ViewGroup container) {
        ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = holder.imageView = new ImageView(context);
            holder.imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        VolleyUtils.loadImage(list.get(getPosition(position)).getCont_pic(), holder.imageView);
        final int pos = position;
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });

        return view;
    }

    private static class ViewHolder {
        ImageView imageView;
    }

    public boolean isInfiniteLoop() {
        return isInfiniteLoop;
    }

    public VideoPagerAdapter setInfiniteLoop(boolean isInfiniteLoop) {
        this.isInfiniteLoop = isInfiniteLoop;
        return this;
    }
}
