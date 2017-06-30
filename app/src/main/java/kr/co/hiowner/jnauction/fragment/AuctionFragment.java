package kr.co.hiowner.jnauction.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;

import kr.co.hiowner.jnauction.R;
import kr.co.hiowner.jnauction.api.API_Adapter;
import kr.co.hiowner.jnauction.api.API_Interface;
import kr.co.hiowner.jnauction.api.data.AuctionsData;
import kr.co.hiowner.jnauction.api.data.ServerTimeData;
import kr.co.hiowner.jnauction.car.CarData;
import kr.co.hiowner.jnauction.car.CarListAdapter;
import kr.co.hiowner.jnauction.util.GlobalValues;
import kr.co.hiowner.jnauction.util.SharedPreUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by user on 2017-06-29.
 */
public class AuctionFragment extends Fragment {

    private final int LISTVIEW_CUR_FULL = 1100;
    private final int LISTVIEW_CUR_MY = 2200;
//    Context mContext;

    Spinner mSpinSort;

    //입찰 남은시간
    TextView mTvRemainTime, mTvEndDate;


    //차량 List
    List<AuctionsData.ResultObject.AuctionsObject> mDataCar_Full;
    List<CarData> mDataCar_My;
    ListView mListViewCar;
    CarListAdapter mAdapterCar;

    //전체차량, 내입찰차량
    CheckBox mChkMyAuctions;
    private int mCurListViewPage;

    //list의 Index 관리
    TextView mTvTotalCount_Full, mTvTotalCount_My ;
    private int mIntOffSet_Full = 1;
    private int mIntLimit_Full = 30;
    private int mIntTotal_Full = 0;
    private int mIntOffSet_My = 1;
    private int mIntLimit_My = 3;
    private int mIntTotal_My = 0;

    //화면에 리스트의 마지막 아이템이 보여지는지 체크
    boolean lastItemVisibleFlag = false;

    public AuctionFragment() {
    }

    public AuctionFragment newInstance() {
        AuctionFragment fragment = new AuctionFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.frag_main_aution, container, false);


        mSpinSort = (Spinner)rootView.findViewById(R.id.main_spinner_sort);

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
        mListViewCar = (ListView)rootView.findViewById(R.id.main_car_listview);
        mChkMyAuctions = (CheckBox)rootView.findViewById(R.id.main_chk_my_product);

        mCurListViewPage = LISTVIEW_CUR_FULL;

        mAdapterCar = new CarListAdapter(getActivity());
//        mListViewCar.setOnItemClickListener(mItemClickListener);
        mListViewCar.setAdapter(mAdapterCar);
        mListViewCar.setOnScrollListener(mOnScrollListener);
        mSpinSort.setOnItemSelectedListener(mSpinSelect);

        return rootView;
    }

    @Override
    public void onResume() {
        new TimeAsyncTask().execute();
        new AuctionsAsyncTask("reg_desc").execute();
        super.onResume();
    }

    AbsListView.OnScrollListener mOnScrollListener = new AbsListView.OnScrollListener() {
        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            //현재 화면에 보이는 첫번째 리스트 아이템의 번호(firstVisibleItem) + 현재 화면에 보이는 리스트 아이템의 갯수(visibleItemCount)가 리스트 전체의 갯수(totalItemCount) -1 보다 크거나 같을때
            lastItemVisibleFlag = (totalItemCount > 0) && (firstVisibleItem + visibleItemCount >= totalItemCount);
        }

        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {
            //OnScrollListener.SCROLL_STATE_IDLE은 스크롤이 이동하다가 멈추었을때 발생되는 스크롤 상태입니다.
            //즉 스크롤이 바닦에 닿아 멈춘 상태에 처리를 하겠다는 뜻
            if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE && lastItemVisibleFlag) {
                //TODO 화면이 바닦에 닿을때 처리
//                    if(mIntTotal > mIntOffSet) {
                mIntOffSet_Full += mIntLimit_Full;
            }
        }
    };

    AdapterView.OnItemSelectedListener mSpinSelect = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            Toast.makeText(getActivity(), ""+adapterView.getItemAtPosition(i), Toast.LENGTH_SHORT).show();
            if(i == 0)
                new AuctionsAsyncTask("reg_desc").execute();
            else if(i == 1)
                new AuctionsAsyncTask("bid_desc").execute();
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    };



    //상품관련 AsyncTask
    private class AuctionsAsyncTask extends AsyncTask<Void, Void, Void>{

        String mStrSort;
        int mIntListViewCurPosition;

        public AuctionsAsyncTask(String sort) {
            mIntListViewCurPosition = mListViewCar.getFirstVisiblePosition();
            mStrSort = sort;
        }

        @Override
        protected Void doInBackground(Void... voids) {


            HashMap<String, String> map = new HashMap<>();
            map.put("token", SharedPreUtil.getTokenID(getActivity()));
            map.put("mybid", "N");
            map.put("order", mStrSort);
            map.put("limit", "30");
            map.put("offset", "1");
            map.put("status_min", "100");
            map.put("status_max", "499");
            Call<AuctionsData> auctions = API_Adapter.getInstance().Auctions(map);
            auctions.enqueue(new Callback<AuctionsData>() {
                @Override
                public void onResponse(Call<AuctionsData> call, Response<AuctionsData> response) {
                    Log.d("where",response.body().getStatus_code());
                    Log.d("where",""+response.body().getResult().getTotal_count());
                    mIntTotal_Full = response.body().getResult().getTotal_count();

                    mDataCar_Full = response.body().getResult().getAuctions();
                    mAdapterCar.addItems(mDataCar_Full);
                    DecimalFormat df = new DecimalFormat("###,###");
//                    holder.car_kms.setText(df.format(Double.parseDouble(data.getC_kms())) + "km");
                    mTvTotalCount_Full.setText("매물수 "+df.format(mIntTotal_Full)+"대");

                    mListViewCar.setSelection(mIntListViewCurPosition);
                }

                @Override
                public void onFailure(Call<AuctionsData> call, Throwable t) {

                }
            });
            return null;
        }
    }

    //시간 관련 AsyncTask
    private class TimeAsyncTask extends AsyncTask<Void, Void, Void>{
        ServerTimeData TimeData;

        @Override
        protected Void doInBackground(Void... voids) {
            Call<ServerTimeData> time = API_Adapter.getInstance().ServerTime();

            time.enqueue(new Callback<ServerTimeData>() {
                @Override
                public void onResponse(Call<ServerTimeData> call, Response<ServerTimeData> response) {
                    TimeData = new ServerTimeData();
                    TimeData.setStatus_code(response.body().getStatus_code());
                    TimeData.setStatus_msg(response.body().getStatus_msg());
                    TimeData.setResult(response.body().getResult());

                    //경매 상태 (O:열림, C:닫힘)
                    if ("C".equals(TimeData.getResult().getAuction_status())){
                        mTvRemainTime.setText("금일 입찰 종료");

                        String month, day = null;
                        month = (TimeData.getResult().getAuction_close_date()).substring(5, 7);
                        day = (TimeData.getResult().getAuction_close_date()).substring(8, 10);
                        mTvEndDate.setText(month + "월 "+day+"일 입찰마감");

                        startHandler.sendEmptyMessageDelayed(0, (TimeData.getResult().getAuction_next_open_seconds()*1000));

                    }else if("O".equals(TimeData.getResult().getAuction_status())){
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
            mTvEndDate.setText(month + "월 "+day+"일 입찰마감 시간");

            Message msg = new Message();
            msg.arg1 = TimeData.getResult().getAuction_close_seconds();

            timeHandler.sendMessageDelayed(msg, 1000);


        }
    }

    Handler timeHandler = new Handler(){
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
            if(remain_time == 0){
                new TimeAsyncTask().execute();
            }else{
                if(timeHandler != null)
                    timeHandler.sendMessageDelayed(send_msg, 1000);
            }
        }
    };

    Handler startHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            new TimeAsyncTask().execute();
        }
    };

    private String setTimeAdd_0(long value){
        String returnValue = null;
        returnValue = String.valueOf(value);
        if(returnValue.length() < 2)
            returnValue = "0"+returnValue;
        return returnValue;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (startHandler != null)
            startHandler.removeMessages(0);
        if (timeHandler != null) {
            timeHandler.removeMessages(0);
            timeHandler = null;
        }
    }
}
