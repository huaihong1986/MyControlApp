package com.cloudtv.hahong.mycontrolapp.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.cloudtv.hahong.mycontrolapp.MainActivity;
import com.cloudtv.hahong.mycontrolapp.adapter.MainListAdapter;
import com.cloudtv.hahong.mycontrolapp.config.Config;
import com.cloudtv.hahong.mycontrolapp.model.MainListData;
import com.cloudtv.hahong.mycontrolapp.util.GsonUtils;
import com.cloudtv.hahong.mycontrolapp.volley.VolleyPostRequest;
import com.cloudtv.hahong.mycontrolapp.volley.VolleyResponseListener;
import com.cloudtv.hahong.mycontrolapp.volley.VolleyUtils;
import com.cloudtv.hahong.mycontrolapp.widget.DropDownListView;
import com.cloudtv.hahong.mycontrolapp.R;
import org.json.JSONObject;


public class MainListFragment extends Fragment implements VolleyResponseListener {
    private static final String ARG_SECTION_NUMBER = "section_number";
    private static final int GET_VIDEO_LIST_INFO = 1001;

    private DropDownListView mListView;
    private MainListAdapter mMainListAdapter;

    private MainListData mListData;
    private boolean mHasMore = false;

    public static MainListFragment newInstance(int sectionNumber) {
        MainListFragment fragment = new MainListFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public MainListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main_list, container, false);
        mListView = (DropDownListView) rootView.findViewById(R.id.list);

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        String listInfo = Config.getMainListInfo(getActivity());
//        if (!TextUtils.isEmpty(listInfo)) {
//            setupVideoList(listInfo);
//        }
        setupVideoList(listInfo);
        mListView.setOnDropDownListener(new DropDownListView.OnDropDownListener() {
            @Override
            public void onDropDown() {
                VolleyUtils.getJSON(Config.sWebHost + Config.API_MAIN_LIST_REQUEST, MainListFragment.this, GET_VIDEO_LIST_INFO);
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

        mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {
            }

            @Override
            public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (firstVisibleItem < 2) {
                    startAutoScroll();
                } else {
                    stopAutoScroll();
                }
            }
        });

        VolleyUtils.getJSON(Config.sWebHost + Config.API_MAIN_LIST_REQUEST, MainListFragment.this, GET_VIDEO_LIST_INFO);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MainActivity) activity).onSectionAttached(getArguments().getInt(ARG_SECTION_NUMBER));
    }

    @Override
    public void onPause() {
        super.onPause();
        stopAutoScroll();
    }

    @Override
    public void onResume() {
        super.onResume();
        startAutoScroll();
    }

    private void startAutoScroll() {
        if (mMainListAdapter != null && mMainListAdapter.mViewHolderVideo != null) {
            mMainListAdapter.mViewHolderVideo.startAutoScroll();
        }
    }

    private void stopAutoScroll() {
        if (mMainListAdapter != null && mMainListAdapter.mViewHolderVideo != null) {
            mMainListAdapter.mViewHolderVideo.stopAutoScroll();
        }
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
            case GET_VIDEO_LIST_INFO:
                Config.saveMainListInfo(getActivity(), json);
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
        String jsonlocal="{videoList :[{\"cont_pic\":\"http://img.taodiantong.cn/v55183/infoimg/2013-07/130720115322ky.jpg\"},{\"cont_pic\":\"http://pic30.nipic.com/20130626/8174275_085522448172_2.jpg\"},{\"cont_pic\":\"http://pic18.nipic.com/20111215/577405_080531548148_2.jpg\"},{\"cont_pic\":\"http://pic15.nipic.com/20110722/2912365_092519919000_2.jpg\"},{\"cont_pic\":\"http://down1.sucaitianxia.com/psd02/psd158/psds27988.jpg\"},{\"cont_pic\":\"http://pic2.ooopic.com/11/35/98/12bOOOPIC8f.jpg\"},{\"cont_pic\":\"http://down1.sucaitianxia.com/psd02/psd158/psds28266.jpg\"},{\"cont_pic\":\"http://pic02.sosucai.com/PSD/PSD_cd0267/b/PSD_cd0267_00016.jpg\"}], picList :[[{\"url\":\"http://pic.58pic.com/58pic/12/64/27/55U58PICrdX.jpg\"}]]}";
        mListData = GsonUtils.fromJson(jsonlocal, MainListData.class);


            mMainListAdapter = new MainListAdapter(getActivity(), mListData);
            mListView.setAdapter(mMainListAdapter);

    }

}
