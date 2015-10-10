package com.cloudtv.hahong.mycontrolapp.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.cloudtv.hahong.mycontrolapp.R;
import com.cloudtv.hahong.mycontrolapp.model.CatalogProductListData;
import com.cloudtv.hahong.mycontrolapp.volley.VolleyUtils;


public class ProductListAdapter extends BaseAdapter {

    private static final String TAG = "ProductListAdapter";

    private Context mContext;
    private CatalogProductListData mData;

    public ProductListAdapter(Context context, CatalogProductListData data) {
        mContext = context;
        mData = data;
    }

    @Override
    public int getCount() {
        return mData.getData().size();
    }

    @Override
    public Object getItem(int position) {
        return mData.getData().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_product_list, null);
            holder = new ViewHolder();
            holder.image = (ImageView) convertView.findViewById(R.id.thumb);
            holder.title = (TextView) convertView.findViewById(R.id.title);
            holder.desc = (TextView) convertView.findViewById(R.id.desc);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        CatalogProductListData.DataEntity item = mData.getData().get(position);

        holder.title.setText(item.getName());
        holder.desc.setText(item.getNowPrice());

        //holder.image.setImageResource(R.mipmap.image_default);
        if (!TextUtils.isEmpty(item.getPicture())) {
            VolleyUtils.loadImage(item.getPicture(), holder.image);
        }

        return convertView;
    }

    public class ViewHolder {
        public ImageView image;
        public TextView title;
        public TextView desc;
    }

}
