package kr.co.hiowner.jnauction.mypage.bid.fragment;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import jbink.appnapps.okhttplibrary.ApiCall;
import kr.co.hiowner.jnauction.R;
import kr.co.hiowner.jnauction.car.CarData;
import kr.co.hiowner.jnauction.car.CarDetailActivity;
import kr.co.hiowner.jnauction.car.CarListAdapter;
import kr.co.hiowner.jnauction.util.SharedPreUtil;
import okhttp3.OkHttpClient;

/**
 * Created by user on 2017-06-26.
 */
public class TodayFragment extends Fragment {
    private final int LISTVIEW_CUR_FULL = 1100;
    private final int LISTVIEW_CUR_MY = 2200;
//    Context mContext;

    Spinner mSpinSort;



    TextView mTvUserName, mTvUSerPhone, mTvUserLicenseStart;



    //차량 List
    List<CarData> mDataCar_Full;
    List<CarData> mDataCar_My;
    ListView mListCar;
    CarListAdapter mAdapterCar;

    //전체차량, 내입찰차량
    CheckBox mChkMyAuctions;
    private int mCurListViewPage;

    //list의 Index 관리
    TextView mTvTotalCount_Full, mTvTotalCount_My ;
    private int mIntOffSet_Full = 0;
    private int mIntLimit_Full = 0;
    private int mIntTotal_Full = 0;
    private int mIntOffSet_My = 0;
    private int mIntLimit_My = 0;
    private int mIntTotal_My = 0;

    //화면에 리스트의 마지막 아이템이 보여지는지 체크
    boolean lastItemVisibleFlag = false;

    public TodayFragment newInstance() {
        TodayFragment fragment = new TodayFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        View rootView = inflater.inflate(R.layout.frag_main_aution, container, false);


//        mSpinSort = (Spinner)rootView.findViewById(R.id.main_spinner_sort);
//
////        mLayoutMain_1 = (LinearLayout)rootView.findViewById(R.id.main_layout_lin_1);
////        mLayoutMain_2 = (ScrollView)rootView.findViewById(R.id.main_layout_scr_2);
////        mTvMainTxt_1 = (TextView)rootView.findViewById(R.id.main_btn_txt_1);
////        mTvMainTxt_2 = (TextView)rootView.findViewById(R.id.main_btn_txt_2);
//        mTvTotalCount_Full = (TextView)rootView.findViewById(R.id.main_car_total_count_full);
//        mTvTotalCount_My = (TextView)rootView.findViewById(R.id.main_car_total_count_my);
////        mTvUserName = (TextView)rootView.findViewById(R.id.main_user_txt_name);
////        mTvUSerPhone = (TextView)rootView.findViewById(R.id.main_user_txt_phone);
////        mTvUserLicenseStart = (TextView)rootView.findViewById(R.id.main_user_txt_license_start);
////        mTvMainUnderline_1 = (ImageView)rootView.findViewById(R.id.main_btn_underline_1);
////        mTvMainUnderline_2 = (ImageView)rootView.findViewById(R.id.main_btn_underline_2);
//        mListCar = (ListView)rootView.findViewById(R.id.main_car_listview);
//        mChkMyAuctions = (CheckBox)rootView.findViewById(R.id.main_chk_my_product);
//
//        mCurListViewPage = LISTVIEW_CUR_FULL;

//        mTvUserName.setText(UserData.getInstance().getName());
//        mTvUSerPhone.setText(UserData.getInstance().getPhone());
//        mTvUserLicenseStart.setText("서비스 시작일 ["+UserData.getInstance().getLicense_start_date()+"]");



//        mListCar.setOnScrollListener(new AbsListView.OnScrollListener() {
//            @Override
//            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
//                //현재 화면에 보이는 첫번째 리스트 아이템의 번호(firstVisibleItem) + 현재 화면에 보이는 리스트 아이템의 갯수(visibleItemCount)가 리스트 전체의 갯수(totalItemCount) -1 보다 크거나 같을때
//                lastItemVisibleFlag = (totalItemCount > 0) && (firstVisibleItem + visibleItemCount >= totalItemCount);
//            }
//            @Override
//            public void onScrollStateChanged(AbsListView view, int scrollState) {
//                //OnScrollListener.SCROLL_STATE_IDLE은 스크롤이 이동하다가 멈추었을때 발생되는 스크롤 상태입니다.
//                //즉 스크롤이 바닦에 닿아 멈춘 상태에 처리를 하겠다는 뜻
//                if(scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE && lastItemVisibleFlag) {
//                    //TODO 화면이 바닦에 닿을때 처리
////                    if(mIntTotal > mIntOffSet) {
//                    mIntOffSet_Full += mIntLimit_Full;
//
////                    }
////                    else{
////                        Log.d("where", "그만그만");
////                    }
//                }
//            }
//        });


//        mSpinSort.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                Toast.makeText(getActivity(), ""+adapterView.getItemAtPosition(i), Toast.LENGTH_SHORT).show();
//            }
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//            }
//        });


//        mAdapterCar = new CarListAdapter(getActivity());
//        mListCar.setOnItemClickListener(mItemClickListener);
//        mListCar.setAdapter(mAdapterCar);

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    AdapterView.OnItemClickListener mItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        }
    };



    public void onClick(View v){
        switch (v.getId()){
        }
    }



}
