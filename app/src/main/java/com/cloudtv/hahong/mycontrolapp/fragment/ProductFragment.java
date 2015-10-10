package com.cloudtv.hahong.mycontrolapp.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.cloudtv.hahong.mycontrolapp.MainActivity;
import com.cloudtv.hahong.mycontrolapp.adapter.ProductListAdapter;
import com.cloudtv.hahong.mycontrolapp.config.Config;
import com.cloudtv.hahong.mycontrolapp.model.CatalogProductListData;
import com.cloudtv.hahong.mycontrolapp.util.GsonUtils;
import com.cloudtv.hahong.mycontrolapp.volley.VolleyPostRequest;
import com.cloudtv.hahong.mycontrolapp.volley.VolleyResponseListener;
import com.cloudtv.hahong.mycontrolapp.volley.VolleyUtils;
import com.cloudtv.hahong.mycontrolapp.widget.DropDownListView;
import com.cloudtv.hahong.mycontrolapp.R;
import org.json.JSONObject;


public class ProductFragment extends Fragment implements VolleyResponseListener {
    private static final String TAG = "MainFragment2";

    private static final String ARG_SECTION_NUMBER = "section_number";
    private static final int GET_CATALOG_PRODUCT_LIST_INFO = 1001;

    private DropDownListView mListView;
    private ProductListAdapter mMainListAdapter;

    private CatalogProductListData mData;
    private boolean mHasMore = false;

    public static ProductFragment newInstance(int sectionNumber) {
        ProductFragment fragment = new ProductFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public ProductFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_product, container, false);
        mListView = (DropDownListView) rootView.findViewById(R.id.list);

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        String listInfo = Config.getCatalogProductListInfo(getActivity());
        if (!TextUtils.isEmpty(listInfo)) {
            setupVideoList(listInfo);
        }

        mListView.setOnDropDownListener(new DropDownListView.OnDropDownListener() {
            @Override
            public void onDropDown() {
                VolleyUtils.getJSON(Config.sWebHost + Config.API_CATALOG_PRODUCT_LIST_REQUEST, ProductFragment.this, GET_CATALOG_PRODUCT_LIST_INFO);
            }
        });

        mListView.setHasMore(mHasMore);
        mListView.setOnBottomListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            }
        });

        VolleyUtils.getJSON(Config.sWebHost + Config.API_CATALOG_PRODUCT_LIST_REQUEST, ProductFragment.this, GET_CATALOG_PRODUCT_LIST_INFO);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MainActivity) activity).onSectionAttached(getArguments().getInt(ARG_SECTION_NUMBER));
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDataStartGetRequest(JsonObjectRequest request) {

    }

    @Override
    public void onDataStartPostRequest(VolleyPostRequest request) {

    }

    @Override
    public void onDataSuccessResponse(JSONObject response, int requestCode) {
        String json = response.toString();

        switch (requestCode) {
            case GET_CATALOG_PRODUCT_LIST_INFO:
                Config.saveCatalogProductListInfo(getActivity(), json);
                setupVideoList(json);
                break;

            default:
                break;
        }

        mListView.onDropDownComplete();
        mListView.setHasMore(mHasMore);
        mListView.onBottomComplete();
    }

    @Override
    public void onDataErrorResponse(VolleyError error, int requestCode) {

    }

    private void setupVideoList(String json) {
        mData = GsonUtils.fromJson(json, CatalogProductListData.class);
        if (mData != null && mData.getData() != null) {
            mMainListAdapter = new ProductListAdapter(getActivity(), mData);
            mListView.setAdapter(mMainListAdapter);
        } else {
            mListView.setAdapter(null);
        }
    }

}
