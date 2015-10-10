package com.cloudtv.hahong.mycontrolapp.adapter;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.cloudtv.hahong.mycontrolapp.model.MainListData;
import com.cloudtv.hahong.mycontrolapp.util.DisplayUtils;
import com.cloudtv.hahong.mycontrolapp.widget.AutoScrollViewPager;
import com.cloudtv.hahong.mycontrolapp.widget.HorizontalScrollImageView;
import com.cloudtv.hahong.mycontrolapp.R;
import java.util.ArrayList;
import java.util.List;


public class MainListAdapter extends BaseAdapter {

    private static final String TAG = "MainListAdapter";

    public static final int TYPE_VIDEO_PAGER = 0;
    public static final int TYPE_PIC_LIST = 1;

    private Context mContext;
    private MainListData mListData;
    public ViewHolderVideo mViewHolderVideo;

    public MainListAdapter(Context context, MainListData data) {
        mContext = context;
        mListData = data;
    }

    @Override
    public int getCount() {
        if (hasNoVideo()) {
            return mListData.getPicList().size();
        } else {
            return 1 + mListData.getPicList().size();
        }
    }

    @Override
    public Object getItem(int position) {
        if (hasNoVideo()) {
            return mListData.getPicList().get(position);
        } else {
            if (position == 0) {
                return mListData.getVideoList();
            } else {
                return mListData.getPicList().get(position - 1);
            }
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int type = getItemViewType(position);

        switch (type) {
            case TYPE_VIDEO_PAGER:
                ViewHolderVideo holderR;


                if (convertView == null) {
                    convertView = LayoutInflater.from(mContext).inflate(R.layout.fragment_main_video_pager, null);
                    holderR = new ViewHolderVideo();
                    holderR.viewPager = (AutoScrollViewPager) convertView.findViewById(R.id.view_pager);
                    holderR.indicatorContainer = (LinearLayout) convertView.findViewById(R.id.indicator_container);

                    holderR.initIndicators(mListData.getVideoList().size());

                    holderR.viewPager.setAdapter(new VideoPagerAdapter(mContext, mListData.getVideoList()).setInfiniteLoop(true));
                    holderR.viewPager.setOnPageChangeListener(new MyOnPageChangeListener(holderR));

                    holderR.viewPager.setInterval(2000);
                    holderR.viewPager.startAutoScroll();
                    holderR.viewPager.setCurrentItem(Integer.MAX_VALUE / 2);


                    convertView.setTag(holderR);
                } else {
                    holderR = (ViewHolderVideo) convertView.getTag();

                }



                mViewHolderVideo = holderR;

                break;

            case TYPE_PIC_LIST:
                ViewHolder holder;

                if (convertView == null) {
                    convertView = LayoutInflater.from(mContext).inflate(R.layout.item_main_list, null);
                    holder = new ViewHolder();
                    holder.images = (HorizontalScrollImageView) convertView.findViewById(R.id.images);
                    convertView.setTag(holder);
                } else {
                    holder = (ViewHolder) convertView.getTag();
                }

                List<MainListData.PicListEntity> list;
                if (hasNoVideo()) {
                    list = mListData.getPicList().get(position);
                } else {
                    list = mListData.getPicList().get(position - 1);
                }
                ArrayList<String> urls = new ArrayList<>();
                for (int i = 0; i < list.size(); i++) {
                    urls.add(list.get(i).getUrl());
                }
                holder.images.setImages(urls);
                break;

            default:
                break;
        }

        return convertView;
    }

    @Override
    public int getViewTypeCount() {
        return TYPE_PIC_LIST + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (hasNoVideo()) {
            return TYPE_PIC_LIST;
        } else {
            return position == 0 ? TYPE_VIDEO_PAGER : TYPE_PIC_LIST;
        }
    }

    public boolean hasNoVideo() {
        return (mListData.getVideoList() == null || mListData.getVideoList().size() == 0);
    }

    public class ViewHolderVideo {
        public AutoScrollViewPager viewPager;
        public LinearLayout indicatorContainer;
        public ArrayList<ImageView> indicators = new ArrayList<>();

        public void initIndicators(int length) {
            for (int i = 0; i < length; i++) {
                ImageView imageView = new ImageView(mContext);
                if (i == 0) {
                    imageView.setBackgroundResource(R.drawable.indicator_oval_selected);
                } else {
                    imageView.setBackgroundResource(R.drawable.indicator_oval);
                }
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(new ViewGroup.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                int margin = DisplayUtils.dp2px(mContext, 5);
                layoutParams.leftMargin = margin;
                layoutParams.rightMargin = margin;
                indicatorContainer.addView(imageView, layoutParams);
                indicators.add(imageView);
            }
        }

        public void selectIndicator(int index) {
            int len = indicators.size();
            for (int i = 0; i < len; i++) {
                if (i == index) {
                    indicators.get(i).setImageResource(R.drawable.indicator_oval_selected);
                } else {
                    indicators.get(i).setImageResource(R.drawable.indicator_oval);
                }
            }
        }

        public void startAutoScroll() {
            viewPager.startAutoScroll();
        }

        public void stopAutoScroll() {
            viewPager.stopAutoScroll();
        }
    }

    public class ViewHolder {
        public HorizontalScrollImageView images;
    }

    protected class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {
        private ViewHolderVideo holder;

        public MyOnPageChangeListener(ViewHolderVideo holder) {
            this.holder = holder;
        }

        @Override
        public void onPageSelected(int position) {

            int index = position % holder.indicators.size();
            holder.selectIndicator(index);
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        @Override
        public void onPageScrollStateChanged(int position) {
        }
    }
}
