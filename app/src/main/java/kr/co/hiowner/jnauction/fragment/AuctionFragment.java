package kr.co.hiowner.jnauction.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import kr.co.hiowner.jnauction.NewMainActivity;
import kr.co.hiowner.jnauction.car.CarDetailActivity;
import kr.co.hiowner.jnauction.R;
import kr.co.hiowner.jnauction.api.API_Adapter;
import kr.co.hiowner.jnauction.api.data.AuctionsData;
import kr.co.hiowner.jnauction.api.data.ServerTimeData;
import kr.co.hiowner.jnauction.car.CarListAdapter;
import kr.co.hiowner.jnauction.util.GlobalValues;
import kr.co.hiowner.jnauction.util.SharedPreUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by user on 2017-06-29.
 */
public class AuctionFragment extends Fragment implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {

    private final int LISTVIEW_CUR_FULL = 1100;
    private final int LISTVIEW_CUR_MY = 2200;

    private final int LIST_FULL = 3201;
    private final int LIST_MY = 3202;
    private final int LIST_FREE_MY = 3203;
    private final int LIST_FREE_FULL = 3204;

    NewMainActivity mActivity;

    Spinner mSpinSort;
    String mStrSpinValue;

    //입찰 남은시간
    TextView mTvRemainTime, mTvEndDate;


    //차량 List
    List<AuctionsData.Resultfdg.Auctionsfdg> mDataCar_Full;
    ListView mListViewFullCar;
    CarListAdapter mAdapterFullCar;

    List<AuctionsData.Resultfdg.Auctionsfdg> mDataCar_My;
    ListView mListViewMyCar;
    CarListAdapter mAdapterMyCar;

    //전체차량, 내입찰차량
    CheckBox mChkMyAuctions;
    private int mCurListViewPage;

    //list의 Index 관리 - FULL
    TextView mTvTotalCount_Full, mTvTotalCount_My ;
    private int mIntOffSet_Full = 0;
    private int mIntLimit_Full = 0;

    //list의 Index 관리 - MY
    private int mIntOffSet_My = 0;
    private int mIntLimit_My = 0;

    //LIST의 결과값 총 갯수
    private int mIntTotal_Full = 0;
    private int mIntTotal_My = 0;

    //화면에 리스트의 마지막 아이템이 보여지는지 체크
    boolean mLastItemFullVisibleFlag = false;
    boolean mLastItemMyVisibleFlag = false;

    //다음 겸매오픈 시간 알림
    RelativeLayout mLayoutNext;
    TextView mTvNext;

    //Pull to refresh
    SwipeRefreshLayout mPulltoRefresh_Full, mPulltoRefresh_My;


    //status_min과 status_max 값 변수
    //시간에 따라 변화한다
    private int mIntStatus_min=0, mIntStatus_max=0;


    //Free추가로 인한 변수(View) 추가
    CheckBox mChkFree;

    List<AuctionsData.Resultfdg.Auctionsfdg> mDataCar_Free_Full;
    ListView mListViewFreeFull;
    CarListAdapter mAdapterFreeFull;
    SwipeRefreshLayout mPulltoRefresh_Free_Full;

    List<AuctionsData.Resultfdg.Auctionsfdg> mDataCar_Free_My;
    ListView mListViewFreeMy;
    CarListAdapter mAdapterFreeMy;
    SwipeRefreshLayout mPulltoRefresh_Free_My;

    //2시간 전이나 종료 후 2시간에 관한 VIew
    LinearLayout m2hourLayout;//main_2hour_layout
    TextView mTv2HourTitle, mTv2HourTime,mTv2HourCount;

    //서버에서 Time을 받은 후에 Data를 받기위해 한번만 쓰이는 변수
    private boolean mBoolFirstCheck = true;
    //초기에 한번 동작하는 Spinner의 리스너를 막기 위한 변수
    private boolean mBoolSpinnerFirst = true;

    //Free의 info 정보를 보여주기 위한 View
    ImageView mIvFreeInfo;

    //오늘 날짜를 저장하기 위한 변수
    String mStrServerCurrentDate = null;

    public AuctionFragment() {
    }

    public AuctionFragment newInstance() {
        AuctionFragment fragment = new AuctionFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mIntLimit_Full = GlobalValues.LIMIT;
        mIntLimit_My = GlobalValues.LIMIT;

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (NewMainActivity)context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mActivity = null;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.frag_main_aution, container, false);


        mSpinSort = (Spinner)rootView.findViewById(R.id.main_spinner_sort);

        mLayoutNext = (RelativeLayout)rootView.findViewById(R.id.main_auction_layout_next);
        mTvNext = (TextView)rootView.findViewById(R.id.main_auction_txt_next);

        mTvRemainTime = (TextView)rootView.findViewById(R.id.main_txt_remain_time);
        mTvEndDate = (TextView)rootView.findViewById(R.id.main_txt_end_date);

//        mLayoutMain_1 = (LinearLayout)rootView.findViewById(R.id.main_layout_lin_1);
//        mLayoutMain_2 = (ScrollView)rootView.findViewById(R.id.main_layout_scr_2);
//        mTvMainTxt_1 = (TextView)rootView.findViewById(R.id.main_btn_txt_1);
//        mTvMainTxt_2 = (TextView)rootView.findViewById(R.id.main_btn_txt_2);
        mTvTotalCount_Full = (TextView)rootView.findViewById(R.id.main_car_total_count_full);
        mTvTotalCount_My = (TextView)rootView.findViewById(R.id.main_car_total_count_my);
//        mTvUserName = (TextView)rootView.findViewById(R.id.main_user_txt_name);
//        mTvUSerPhone = (TextView)rootView.findViewById(R.id.main_user_txt_phone);
//        mTvUserLicenseStart = (TextView)rootView.findViewById(R.id.main_user_txt_license_start);
//        mTvMainUnderline_1 = (ImageView)rootView.findViewById(R.id.main_btn_underline_1);
//        mTvMainUnderline_2 = (ImageView)rootView.findViewById(R.id.main_btn_underline_2);
        mChkMyAuctions = (CheckBox)rootView.findViewById(R.id.main_chk_my_product);
        mChkMyAuctions.setOnClickListener(this);
        mChkFree = (CheckBox)rootView.findViewById(R.id.main_chk_free);
        mChkFree.setOnClickListener(this);

        mCurListViewPage = LISTVIEW_CUR_FULL;

        mListViewFullCar = (ListView)rootView.findViewById(R.id.main_car_full_listview);
        mAdapterFullCar = new CarListAdapter(getActivity());
        mListViewFullCar.setAdapter(mAdapterFullCar);
        mListViewFullCar.setOnItemClickListener(mItemClickFullListener);
        mListViewFullCar.setOnScrollListener(mOnScrollFullListener);
        mPulltoRefresh_Full = (SwipeRefreshLayout)rootView.findViewById(R.id.main_car_full_pullto) ;
        mPulltoRefresh_Full.setOnRefreshListener(this);
        ((ImageButton)rootView.findViewById(R.id.main_btn_info)).setOnClickListener(this);


        mListViewMyCar = (ListView)rootView.findViewById(R.id.main_car_my_listview);
        mAdapterMyCar = new CarListAdapter(getActivity());
        mListViewMyCar.setAdapter(mAdapterMyCar);
        mListViewMyCar.setOnItemClickListener(mItemClickMyListener);
        mListViewMyCar.setOnScrollListener(mOnScrollMyListener);
        mPulltoRefresh_My = (SwipeRefreshLayout)rootView.findViewById(R.id.main_car_my_pullto) ;
        mPulltoRefresh_My.setOnRefreshListener(this);

        //무료 - 전체
        mListViewFreeFull = (ListView)rootView.findViewById(R.id.main_car_free_full_listview);
        mAdapterFreeFull = new CarListAdapter(getActivity());
        mListViewFreeFull.setAdapter(mAdapterFreeFull);
        mListViewFreeFull.setOnItemClickListener(mItemClickFullListener);
        mPulltoRefresh_Free_Full = (SwipeRefreshLayout)rootView.findViewById(R.id.main_car_free_full_pullto) ;
        mPulltoRefresh_Free_Full.setOnRefreshListener(this);

        //무료 - My
        mListViewFreeMy = (ListView)rootView.findViewById(R.id.main_car_free_my_listview);
        mAdapterFreeMy = new CarListAdapter(getActivity());
        mListViewFreeMy.setAdapter(mAdapterFreeMy);
        mListViewFreeMy.setOnItemClickListener(mItemClickFullListener);
        mPulltoRefresh_Free_My = (SwipeRefreshLayout)rootView.findViewById(R.id.main_car_free_my_pullto) ;
        mPulltoRefresh_Free_My.setOnRefreshListener(this);

        //2시간에 관한
        m2hourLayout = (LinearLayout)rootView.findViewById(R.id.main_2hour_layout);
        mTv2HourTitle = (TextView)rootView.findViewById(R.id.main_2hour_txt_title);
        mTv2HourTime = (TextView)rootView.findViewById(R.id.main_2hour_txt_time);
        mTv2HourCount = (TextView)rootView.findViewById(R.id.main_2hour_txt_count);

        mIvFreeInfo = (ImageView)rootView.findViewById(R.id.main_free_img_info);
        mIvFreeInfo.setOnClickListener(this);

        mSpinSort.setOnItemSelectedListener(mSpinSelect);
        mStrSpinValue = "reg_desc";

       mSpinSort.setAdapter(new ArrayAdapter<String>(getActivity(), R.layout.spinner_text, getResources().getStringArray(R.array.spinner_item)));

        return rootView;
    }
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    AdapterView.OnItemClickListener mItemClickFullListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            AuctionsData.Resultfdg.Auctionsfdg data = (AuctionsData.Resultfdg.Auctionsfdg) adapterView.getAdapter().getItem(i);
            Intent intent = new Intent(getActivity(), CarDetailActivity.class);
            intent.putExtra("auction_idx",  data.getAuction_idx());
            intent.putExtra("status",  mIntStatus_min);
            startActivityForResult(intent, 4444);
        }
    };

    AbsListView.OnScrollListener mOnScrollFullListener = new AbsListView.OnScrollListener() {
        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            //현재 화면에 보이는 첫번째 리스트 아이템의 번호(firstVisibleItem) + 현재 화면에 보이는 리스트 아이템의 갯수(visibleItemCount)가 리스트 전체의 갯수(totalItemCount) -1 보다 크거나 같을때
            mLastItemFullVisibleFlag = (totalItemCount > 0) && (firstVisibleItem + visibleItemCount >= totalItemCount);
        }

        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {
            //OnScrollListener.SCROLL_STATE_IDLE은 스크롤이 이동하다가 멈추었을때 발생되는 스크롤 상태입니다.
            //즉 스크롤이 바닦에 닿아 멈춘 상태에 처리를 하겠다는 뜻
            if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE && mLastItemFullVisibleFlag) {
                if(mIntOffSet_Full < mIntTotal_Full) {
                    new AuctionsAsyncTask().execute();
                    mIntOffSet_Full += mIntLimit_Full;
                }
                else{
                }
            }
        }
    };
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    AdapterView.OnItemClickListener mItemClickMyListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            AuctionsData.Resultfdg.Auctionsfdg data = (AuctionsData.Resultfdg.Auctionsfdg) adapterView.getAdapter().getItem(i);
            Intent intent = new Intent(getActivity(), CarDetailActivity.class);
            intent.putExtra("auction_idx",  data.getAuction_idx());
            startActivityForResult(intent, 7777);
        }
    };

    AbsListView.OnScrollListener mOnScrollMyListener = new AbsListView.OnScrollListener() {
        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            //현재 화면에 보이는 첫번째 리스트 아이템의 번호(firstVisibleItem) + 현재 화면에 보이는 리스트 아이템의 갯수(visibleItemCount)가 리스트 전체의 갯수(totalItemCount) -1 보다 크거나 같을때
            mLastItemMyVisibleFlag = (totalItemCount > 0) && (firstVisibleItem + visibleItemCount >= totalItemCount);
        }

        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {
            //OnScrollListener.SCROLL_STATE_IDLE은 스크롤이 이동하다가 멈추었을때 발생되는 스크롤 상태입니다.
            //즉 스크롤이 바닦에 닿아 멈춘 상태에 처리를 하겠다는 뜻
            if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE && mLastItemMyVisibleFlag) {
                Log.d("where", "  바닥 ");
                if(mIntOffSet_My < mIntTotal_My) {
                    new MyAuctionsAsyncTask().execute();
                    mIntOffSet_My += mIntLimit_My;
                }
                else{
                    Log.d("where", "그만 받아와");
                }

            }
        }
    };
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    AdapterView.OnItemSelectedListener mSpinSelect = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//            mStrSpinValue = adapterView.getItemAtPosition(i).toString();

            if(mBoolSpinnerFirst == true){
                mBoolSpinnerFirst = false;
                return;
            }

            if(i == 0) {
                mStrSpinValue = "reg_desc";
                listInIt();
            }
            else if(i == 1) {
                mStrSpinValue = "bid_desc";
                listInIt();
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        new AuctionsRefreshAsyncTask().execute();
        if (requestCode == 4444){
            if (resultCode == getActivity().RESULT_OK){
                mAdapterMyCar.addOneItem(new AuctionsData.Resultfdg.Auctionsfdg());
                mActivity.refreshMyPageData();
            }
        }
        new MyAuctionsRefreshAsyncTask().execute();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.main_chk_free :
            case R.id.main_chk_my_product :
                if(mChkMyAuctions.isChecked()){
                    if(mChkFree.isChecked()){//내입찰 O , Free O
                        setVisibleListView(LIST_FREE_MY);
                    }else{//내입찰 O , Free X
                        setVisibleListView(LIST_MY);
                    }
                }else{
                    if(mChkFree.isChecked()){//내입찰 X , Free O
                        setVisibleListView(LIST_FREE_FULL);
                    }else{//내입찰 X , Free X
                        setVisibleListView(LIST_FULL);
                    }
                }
                break;
            case R.id.main_btn_info :
                if(mIvFreeInfo.getVisibility() == View.GONE){
                    mIvFreeInfo.setVisibility(View.VISIBLE);
                }else{
                    mIvFreeInfo.setVisibility(View.GONE);
                }
//                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//                builder.setMessage("입찰권 차감없이 자유롭게\n" +"입찰 할수  있는 차량")
//                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int id) {
//                                // FIRE ZE MISSILES!
//                            }
//                        });
//                builder.create();
//                builder.show();
                break;
            case R.id.main_free_img_info:
                mIvFreeInfo.setVisibility(View.GONE);
                break;
        }
    }

    private void setVisibleListView(int value){
        switch (value){
            case LIST_FULL :
                mPulltoRefresh_Full.setVisibility(View.VISIBLE);
                mListViewFullCar.setVisibility(View.VISIBLE);

                mPulltoRefresh_My.setVisibility(View.GONE);
                mListViewMyCar.setVisibility(View.GONE);
                mPulltoRefresh_Free_My.setVisibility(View.GONE);
                mListViewFreeMy.setVisibility(View.GONE);
                mPulltoRefresh_Free_Full.setVisibility(View.GONE);
                mListViewFreeFull.setVisibility(View.GONE);
                break;
            case LIST_FREE_FULL :
                mPulltoRefresh_Free_Full.setVisibility(View.VISIBLE);
                mListViewFreeFull.setVisibility(View.VISIBLE);

                mPulltoRefresh_Free_My.setVisibility(View.GONE);
                mListViewFreeMy.setVisibility(View.GONE);
                mPulltoRefresh_Full.setVisibility(View.GONE);
                mListViewFullCar.setVisibility(View.GONE);
                mPulltoRefresh_My.setVisibility(View.GONE);
                mListViewMyCar.setVisibility(View.GONE);
                break;
            case LIST_MY :
                mPulltoRefresh_My.setVisibility(View.VISIBLE);
                mListViewMyCar.setVisibility(View.VISIBLE);

                mPulltoRefresh_Free_My.setVisibility(View.GONE);
                mListViewFreeMy.setVisibility(View.GONE);
                mPulltoRefresh_Free_Full.setVisibility(View.GONE);
                mListViewFreeFull.setVisibility(View.GONE);
                mPulltoRefresh_Full.setVisibility(View.GONE);
                mListViewFullCar.setVisibility(View.GONE);
                break;
            case LIST_FREE_MY :
                mPulltoRefresh_Free_My.setVisibility(View.VISIBLE);
                mListViewFreeMy.setVisibility(View.VISIBLE);

                mPulltoRefresh_Free_Full.setVisibility(View.GONE);
                mListViewFreeFull.setVisibility(View.GONE);
                mPulltoRefresh_Full.setVisibility(View.GONE);
                mListViewFullCar.setVisibility(View.GONE);
                mPulltoRefresh_My.setVisibility(View.GONE);
                mListViewMyCar.setVisibility(View.GONE);
                break;
        }
    }

    @Override
    public void onRefresh() {
        new MyAuctionsRefreshAsyncTask().execute();
        new AuctionsRefreshAsyncTask().execute();
    }

    /*************************************************************************************************************************************************/
    //전체상품관련 AsyncTask
    private class AuctionsAsyncTask extends AsyncTask<Void, Void, Void>{


        public AuctionsAsyncTask() {
        }

        @Override
        public Void doInBackground(Void... voids) {

            HashMap<String, String> map = new HashMap<>();
            map.put("token", SharedPreUtil.getTokenID(getActivity()));
            map.put("mybid", "N");
            map.put("order", mStrSpinValue);
            map.put("limit", ""+mIntLimit_Full);
//            map.put("limit", "10");
            map.put("offset", ""+mIntOffSet_Full);
            map.put("status_min", ""+mIntStatus_min);
            map.put("status_max", ""+mIntStatus_max);
            map.put("reg_date_min", mStrServerCurrentDate + " 00:00:00");
            map.put("reg_date_max", mStrServerCurrentDate + " 23:59:59");
            Call<AuctionsData> auctions = API_Adapter.getInstance().Auctions(map);
            auctions.enqueue(new Callback<AuctionsData>() {
                @Override
                public void onResponse(Call<AuctionsData> call, Response<AuctionsData> response) {
//                    Log.d("where",response.body().getStatus_code());
                    if(!GlobalValues.SERVER_SUCCESS.equals(response.body().getStatus_code())){
                        Toast.makeText(getActivity(), response.body().getStatus_msg(), Toast.LENGTH_SHORT).show();
                        return;
                    }
//                    Log.d("where",""+response.body().getResult().getTotal_count());
                    mIntTotal_Full = response.body().getResult().getTotal_count();
                    mDataCar_Full = response.body().getResult().getAuctions();
                    mAdapterFullCar.addItems(mDataCar_Full);

                    mTvTotalCount_Full.setText("매물수 "+GlobalValues.getWonFormat(String.valueOf(mIntTotal_Full))+"대");
                    if(mIntStatus_min == 100){
                        mTv2HourCount.setText("금일 입찰 대기 매물 " + mIntTotal_Full);
                    }else if(mIntStatus_min == 300){
                        mTv2HourCount.setText("금일 입찰 종료 매물 " + mIntTotal_Full);
                    }

                }

                @Override
                public void onFailure(Call<AuctionsData> call, Throwable t) {

                }
            });
            return null;
        }
    }
    //전체상품관련 AsyncTask REFRESH
    private class AuctionsRefreshAsyncTask extends AsyncTask<Void, Void, Void>{

        int mIntListViewCurPosition;

        public AuctionsRefreshAsyncTask() {
            mIntListViewCurPosition = mListViewFullCar.getFirstVisiblePosition();
        }

        @Override
        public Void doInBackground(Void... voids) {
            int refreshOffset = 0;

            //리스트를 한번도 더 받아온 경우가 없을때
            if(mIntOffSet_Full < mIntLimit_Full)
                refreshOffset = mIntLimit_Full;
            else
                refreshOffset = mIntOffSet_Full-1;

            HashMap<String, String> map = new HashMap<>();
            map.put("token", SharedPreUtil.getTokenID(getActivity()));
            map.put("mybid", "N");
            map.put("order", mStrSpinValue);
            map.put("limit", ""+refreshOffset);//+mIntLimit_Full);
            map.put("offset", "0");
            map.put("status_min", ""+mIntStatus_min);
            map.put("status_max", ""+mIntStatus_max);
            map.put("reg_date_min", mStrServerCurrentDate + " 00:00:00");
            map.put("reg_date_max", mStrServerCurrentDate + " 23:59:59");
            Call<AuctionsData> auctions = API_Adapter.getInstance().Auctions(map);
            auctions.enqueue(new Callback<AuctionsData>() {
                @Override
                public void onResponse(Call<AuctionsData> call, Response<AuctionsData> response) {
                    if(!GlobalValues.SERVER_SUCCESS.equals(response.body().getStatus_code())){
                        Toast.makeText(getActivity(), response.body().getStatus_msg(), Toast.LENGTH_SHORT).show();
                        return;
                    }
                    mDataCar_Full = new ArrayList<AuctionsData.Resultfdg.Auctionsfdg>();
                    mIntTotal_Full = response.body().getResult().getTotal_count();
                    mDataCar_Full = response.body().getResult().getAuctions();
                    mAdapterFullCar.changeItem(mDataCar_Full);
//                    holder.car_kms.setText(df.format(Double.parseDouble(data.getC_kms())) + "km");
                    mTvTotalCount_Full.setText("매물수 "+GlobalValues.getWonFormat(String.valueOf(mIntTotal_Full))+"대");

                    mListViewFullCar.setSelection(mIntListViewCurPosition);
                }

                @Override
                public void onFailure(Call<AuctionsData> call, Throwable t) {

                }
            });
            return null;
        }
    }
/*************************************************************************************************************************************************/
    //내입찰상품관련 AsyncTask
    private class MyAuctionsAsyncTask extends AsyncTask<Void, Void, Void>{

        public MyAuctionsAsyncTask() {
            Log.d("where", "요청 : ");
        }

        @Override
        public Void doInBackground(Void... voids) {

            HashMap<String, String> map = new HashMap<>();
            map.put("token", SharedPreUtil.getTokenID(getActivity()));
            map.put("mybid", "Y");
            map.put("order", mStrSpinValue);
            map.put("limit", ""+mIntLimit_My);
    //            map.put("limit", "10");
            map.put("offset", ""+mIntOffSet_My);
            map.put("status_min", ""+mIntStatus_min);
            map.put("status_max", ""+mIntStatus_max);
            map.put("reg_date_min", mStrServerCurrentDate + " 00:00:00");
            map.put("reg_date_max", mStrServerCurrentDate + " 23:59:59");
            Call<AuctionsData> auctions = API_Adapter.getInstance().Auctions(map);
            auctions.enqueue(new Callback<AuctionsData>() {
                @Override
                public void onResponse(Call<AuctionsData> call, Response<AuctionsData> response) {
                    if(!GlobalValues.SERVER_SUCCESS.equals(response.body().getStatus_code())){
                        Toast.makeText(getActivity(), response.body().getStatus_msg(), Toast.LENGTH_SHORT).show();
                        return;
                    }
                    mIntTotal_My = response.body().getResult().getTotal_count();
                    mDataCar_My = response.body().getResult().getAuctions();
                    Log.d("where", "SIZE : "+mDataCar_My.size());
                    mAdapterMyCar.addItems(mDataCar_My);
    //                    holder.car_kms.setText(df.format(Double.parseDouble(data.getC_kms())) + "km");
                    mTvTotalCount_My.setText(""+GlobalValues.getWonFormat(String.valueOf(mIntTotal_My))+"대");
                }

                @Override
                public void onFailure(Call<AuctionsData> call, Throwable t) {

                }
            });
            return null;
        }
    }

    //내입찰상품관련 AsyncTask REFRESH
    private class MyAuctionsRefreshAsyncTask extends AsyncTask<Void, Void, Void>{

        int mIntListViewCurPosition;

        public MyAuctionsRefreshAsyncTask() {
            mIntListViewCurPosition = mListViewMyCar.getFirstVisiblePosition();
        }

        @Override
        public Void doInBackground(Void... voids) {
            int refreshOffset = 0;

            //리스트를 한번도 더 받아온 경우가 없을때
            if(mIntOffSet_My < mIntLimit_My)
                refreshOffset = mIntLimit_My;
            else
                refreshOffset = mIntOffSet_My-1;

            HashMap<String, String> map = new HashMap<>();
            map.put("token", SharedPreUtil.getTokenID(getActivity()));
            map.put("mybid", "Y");
            map.put("order", mStrSpinValue);
            map.put("limit", ""+refreshOffset);//+mIntLimit_My);
            map.put("offset", "0");
            map.put("status_min", ""+mIntStatus_min);
            map.put("status_max", ""+mIntStatus_max);
            map.put("reg_date_min", mStrServerCurrentDate + " 00:00:00");
            map.put("reg_date_max", mStrServerCurrentDate + " 23:59:59");
            Call<AuctionsData> auctions = API_Adapter.getInstance().Auctions(map);
            auctions.enqueue(new Callback<AuctionsData>() {
                @Override
                public void onResponse(Call<AuctionsData> call, Response<AuctionsData> response) {
                    if(!GlobalValues.SERVER_SUCCESS.equals(response.body().getStatus_code())){
                        Toast.makeText(getActivity(), response.body().getStatus_msg(), Toast.LENGTH_SHORT).show();
                        return;
                    }
                    mDataCar_My = new ArrayList<AuctionsData.Resultfdg.Auctionsfdg>();
                    mIntTotal_My = response.body().getResult().getTotal_count();
                    mDataCar_My = response.body().getResult().getAuctions();
                    mAdapterMyCar.changeItem(mDataCar_My);
//                    holder.car_kms.setText(df.format(Double.parseDouble(data.getC_kms())) + "km");
                    mTvTotalCount_My.setText(""+GlobalValues.getWonFormat(String.valueOf(mIntTotal_My))+"대");

                    mListViewMyCar.setSelection(mIntListViewCurPosition);
                }

                @Override
                public void onFailure(Call<AuctionsData> call, Throwable t) {

                }
            });
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            mPulltoRefresh_Full.setRefreshing(false);
            mPulltoRefresh_My.setRefreshing(false);
        }
    }
/*************************************************************************************************************************************************/
    //시간 관련 AsyncTask
    private class TimeAsyncTask extends AsyncTask<Void, Void, Void>{
        ServerTimeData TimeData;

        @Override
        public Void doInBackground(Void... voids) {
            Call<ServerTimeData> time = API_Adapter.getInstance().ServerTime();

            time.enqueue(new Callback<ServerTimeData>() {
                @Override
                public void onResponse(Call<ServerTimeData> call, Response<ServerTimeData> response) {
                    if (!GlobalValues.SERVER_SUCCESS.equals(response.body().getStatus_code())){
                        Toast.makeText(getActivity(), response.body().getStatus_msg(), Toast.LENGTH_SHORT).show();
                        return;
                    }
                    TimeData = new ServerTimeData();
                    TimeData.setStatus_code(response.body().getStatus_code());
                    TimeData.setStatus_msg(response.body().getStatus_msg());
                    TimeData.setResult(response.body().getResult());
                    mStrServerCurrentDate = (TimeData.getResult().getServer_current_date()).substring(0, 10);

                    //경매 상태 (O:열림, C:닫힘)
                    if ("C".equals(TimeData.getResult().getAuction_status())){
                        String month, day = null;
                        month = (TimeData.getResult().getAuction_next_open_date()).substring(5, 7);
                        day = (TimeData.getResult().getAuction_next_open_date()).substring(8, 10);
                        mTvEndDate.setText(month + "월 "+day+"일 | ");
                        int nextOpenTime = TimeData.getResult().getAuction_next_open_seconds();
                        int closeTime = TimeData.getResult().getAuction_close_seconds();

                        if(nextOpenTime >1 && nextOpenTime <7200){//경매오픈 2시간 전
                            Log.d("where", "경매오픈 2시간 전");
                            mTvRemainTime.setText("입찰 오픈 전");
                            m2hourLayout.setVisibility(View.VISIBLE);
                            mTv2HourTitle.setText("BEFORE OPENING");
                            mTv2HourTime.setText(GlobalValues.getDetailDay("yyyy-MM-dd hh:mm:ss", TimeData.getResult().getAuction_next_open_date(), 0) + " 오픈 예정입니다.");

                            mIntStatus_min = 100;
                            mIntStatus_max = 199;
                        }else if(closeTime < -1 && closeTime > -7200){//경매마감 후 2시간
                            Log.d("where", "경매마감 후 2시간");
                            mTvRemainTime.setText("입찰 종료");
                            m2hourLayout.setVisibility(View.VISIBLE);
                            mTv2HourTitle.setText("NEXT OPEN");
                            mTv2HourTime.setText(GlobalValues.getDetailDay("yyyy-MM-dd hh:mm:ss", TimeData.getResult().getAuction_next_open_date(), 0) + " 오픈 예정입니다.");
                            mIntStatus_min = 300;
                            mIntStatus_max = 399;
                        }else{//나머지
                            Log.d("where", "나머지");
                            mTvRemainTime.setText("입찰 오픈 전");
                            mIntStatus_min = 0;
                            mIntStatus_max = 0;
                            m2hourLayout.setVisibility(View.GONE);
                            mLayoutNext.setVisibility(View.VISIBLE);
                            mTvNext.setText(GlobalValues.getDetailDay("yyyy-MM-dd hh:mm:ss", TimeData.getResult().getAuction_next_open_date(), 0) + " 금일 오픈 전입니다.");
                        }

                        listInIt();

                        if(startHandler == null){
                            startHandler = new StartTimerHandler();
                        }
                        startHandler.sendEmptyMessageDelayed(0, (TimeData.getResult().getAuction_next_open_seconds()*1000));

                    }else if("O".equals(TimeData.getResult().getAuction_status())){
                        if(mBoolFirstCheck == true){
                            mBoolFirstCheck = false;
                            listInIt();
                        }
                        mIntStatus_min = 200;
                        mIntStatus_max = 299;
                        m2hourLayout.setVisibility(View.GONE);
                        mLayoutNext.setVisibility(View.GONE);
                        setTimeLayout();
                    }else{
                        Toast.makeText(getActivity(), TimeData.getStatus_msg(), Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onFailure(Call<ServerTimeData> call, Throwable t) {
                    Log.d("where","ERROR : "+t.getMessage());
                }
            });
            return null;
        }

        private void setTimeLayout(){
            if (!GlobalValues.SERVER_SUCCESS.equals(TimeData.getStatus_code())){
                Toast.makeText(getActivity(), TimeData.getStatus_msg(), Toast.LENGTH_SHORT).show();
                return;
            }
//            long server_time = Long.parseLong(TimeData.getResult().getAuction_close_seconds());


            String month, day;
            month = (TimeData.getResult().getAuction_close_date()).substring(5, 7);
            day = (TimeData.getResult().getAuction_close_date()).substring(8, 10);
            mTvEndDate.setText(month + "월 "+day+"일 | ");

            Message msg = new Message();
            msg.arg1 = TimeData.getResult().getAuction_close_seconds();

            if(timeHandler == null){
                timeHandler = new TimerHandler();
            }

            timeHandler.sendMessageDelayed(msg, 1000);


        }
    }

    TimerHandler timeHandler = new TimerHandler();
    class TimerHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            int server_time = msg.arg1;
            int remain_time = server_time-1;

            long sec = (server_time >= 60 ? server_time % 60 : server_time);
            long min = (server_time = (server_time / 60)) >= 60 ? server_time % 60 : server_time;
            long hrs = (server_time = (server_time / 60)) >= 24 ? server_time % 24 : server_time;

            mTvRemainTime.setText(""+setTimeAdd_0(hrs)+":"+setTimeAdd_0(min)+":"+setTimeAdd_0(sec));


            Message send_msg = new Message();
            send_msg.arg1 = remain_time;
            if(remain_time <= 0){
                new TimeAsyncTask().execute();
            }else{
                if(timeHandler != null)
                    timeHandler.sendMessageDelayed(send_msg, 1000);
            }
        }
    }

//    Handler timeHandler = new Handler(){
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//
//
//        }
//    };

//    Handler startHandler = new Handler(){
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//            new TimeAsyncTask().execute();
//        }
//    };

    StartTimerHandler startHandler = new StartTimerHandler();
    class StartTimerHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            new TimeAsyncTask().execute();
            listInIt();
        }
    }

    private void listInIt(){
        mIntOffSet_Full = 0;
        mIntOffSet_My = 0;
        mAdapterFullCar.removeAllData();
        mAdapterMyCar.removeAllData();
        new AuctionsAsyncTask().execute();
        new MyAuctionsAsyncTask().execute();
        new FreeAuctionsAsyncTask().execute();
        new FreeMyAsyncTask().execute();
    }

    private String setTimeAdd_0(long value){
        String returnValue = null;
        returnValue = String.valueOf(value);
        if(returnValue.length() < 2)
            returnValue = "0"+returnValue;
        return returnValue;
    }


//    //내 입찰차량
//    public void setMyBidCarData(ArrayList<AuctionsData.Resultfdg.Auctionsfdg> data){
//        for(int i=0 ; i< data.size() ; i++){
//            if("Y".equals(data.get(i).getB_mybid())){
//                mDataCar_My.add(data.get(i));
//            }
//        }
//
//    }


    @Override
    public void onDestroy() {
        Log.d("value" , "onPause");
        super.onDestroy();


    }

    Handler testHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            mTvRemainTime.setText("00:00:00");
        }
    };

    @Override
    public void onPause() {
        super.onPause();
        testHandler.sendEmptyMessageDelayed(0, 1000);
//        mTvRemainTime.setText("00:00:00");
    }

    @Override
    public void onResume() {
        new TimeAsyncTask().execute();
        super.onResume();
    }

    @Override
    public void onStop() {
        if (startHandler != null)
            startHandler.removeMessages(0);
        if (timeHandler != null) {
            timeHandler.removeMessages(0);
            timeHandler = null;
        }

        super.onStop();
    }




    /*************************************************************************************************************************************************/
    //무료상품관련 AsyncTask
    private class FreeAuctionsAsyncTask extends AsyncTask<Void, Void, Void>{


        public FreeAuctionsAsyncTask() {
        }

        @Override
        public Void doInBackground(Void... voids) {

            HashMap<String, String> map = new HashMap<>();
            map.put("token", SharedPreUtil.getTokenID(getActivity()));
            map.put("mybid", "N");
            map.put("order", mStrSpinValue);
            map.put("limit", "1000");
//            map.put("limit", "10");
            map.put("offset", "0");
            map.put("status_min", ""+mIntStatus_min);
            map.put("status_max", ""+mIntStatus_max);
            map.put("free", "Y");
            map.put("reg_date_min", mStrServerCurrentDate + " 00:00:00");
            map.put("reg_date_max", mStrServerCurrentDate + " 23:59:59");
            Call<AuctionsData> auctions = API_Adapter.getInstance().Auctions(map);
            auctions.enqueue(new Callback<AuctionsData>() {
                @Override
                public void onResponse(Call<AuctionsData> call, Response<AuctionsData> response) {
                    if(!GlobalValues.SERVER_SUCCESS.equals(response.body().getStatus_code())){
                        Toast.makeText(getActivity(), response.body().getStatus_msg(), Toast.LENGTH_SHORT).show();
                        return;
                    }
                    mDataCar_Free_Full = response.body().getResult().getAuctions();
                    mAdapterFreeFull.addItems(mDataCar_Free_Full);
                }

                @Override
                public void onFailure(Call<AuctionsData> call, Throwable t) {

                }
            });
            return null;
        }
    }

    //무료 내 입찰 상품관련 AsyncTask
    private class FreeMyAsyncTask extends AsyncTask<Void, Void, Void>{


        public FreeMyAsyncTask() {
        }

        @Override
        public Void doInBackground(Void... voids) {

            HashMap<String, String> map = new HashMap<>();
            map.put("token", SharedPreUtil.getTokenID(getActivity()));
            map.put("mybid", "Y");
            map.put("order", mStrSpinValue);
            map.put("limit", "1000");
//            map.put("limit", "10");
            map.put("offset", "0");
            map.put("status_min", ""+mIntStatus_min);
            map.put("status_max", ""+mIntStatus_max);
            map.put("free", "Y");
            map.put("reg_date_min", mStrServerCurrentDate + " 00:00:00");
            map.put("reg_date_max", mStrServerCurrentDate + " 23:59:59");
            Call<AuctionsData> auctions = API_Adapter.getInstance().Auctions(map);
            auctions.enqueue(new Callback<AuctionsData>() {
                @Override
                public void onResponse(Call<AuctionsData> call, Response<AuctionsData> response) {
                    if(!GlobalValues.SERVER_SUCCESS.equals(response.body().getStatus_code())){
                        Toast.makeText(getActivity(), response.body().getStatus_msg(), Toast.LENGTH_SHORT).show();
                        return;
                    }
                    mDataCar_Free_My = response.body().getResult().getAuctions();
                    mAdapterFreeMy.addItems(mDataCar_Free_My);
                }

                @Override
                public void onFailure(Call<AuctionsData> call, Throwable t) {

                }
            });
            return null;
        }
    }

}
