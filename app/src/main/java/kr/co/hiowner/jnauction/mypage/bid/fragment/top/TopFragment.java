package kr.co.hiowner.jnauction.mypage.bid.fragment.top;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import kr.co.hiowner.jnauction.R;
import kr.co.hiowner.jnauction.api.API_Adapter;
import kr.co.hiowner.jnauction.api.data.AuctionsData;
import kr.co.hiowner.jnauction.car.CarDetailActivity;
import kr.co.hiowner.jnauction.mypage.bid.MyBidActivity;
import kr.co.hiowner.jnauction.mypage.bid.fragment.entire.EntireListAdapter;
import kr.co.hiowner.jnauction.util.GlobalValues;
import kr.co.hiowner.jnauction.util.SharedPreUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by user on 2017-07-06.
 */
public class TopFragment extends Fragment{

    private final int REQUST_CODE_DETAIL = 2275;

    MyBidActivity mActivity;

    //list의 Index 관리
//    TextView mTvTotalCount_My ;
    private int mIntOffSet_My = 0;
    private int mIntLimit_My = 0;
    private int mIntTotal_My = 0;


    List<AuctionsData.ResultObject.AuctionsObject> mDataCar_My;
    ListView mListViewMyCar;
    TopListAdapter mAdapterMyCar;

    //화면에 리스트의 마지막 아이템이 보여지는지 체크
    boolean mLastItemMyVisibleFlag = false;

    LinearLayout mLayoutNext;

    public TopFragment newInstance() {
        TopFragment fragment = new TopFragment();
        return fragment;
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (MyBidActivity)context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mActivity = null;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mIntLimit_My = GlobalValues.LIMIT;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.frag_my_bid_entire, container, false);

        mListViewMyCar = (ListView)rootView.findViewById(R.id.frag_bid_listview);
        mLayoutNext = (LinearLayout)rootView.findViewById(R.id.frag_bid_layout_next);
        mAdapterMyCar = new TopListAdapter(getActivity());
        mListViewMyCar.setAdapter(mAdapterMyCar);
        mListViewMyCar.setOnItemClickListener(mItemClickMyListener);
        mListViewMyCar.setOnScrollListener(mOnScrollMyListener);

        new MyAuctionsAsyncTask().execute();


        return rootView;
    }

    AdapterView.OnItemClickListener mItemClickMyListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            AuctionsData.ResultObject.AuctionsObject data = (AuctionsData.ResultObject.AuctionsObject) adapterView.getAdapter().getItem(i);
            Intent intent = new Intent(getActivity(), TopDetailActivity.class);
            intent.putExtra("auction_idx",  data.getAuction_idx());
            startActivityForResult(intent, REQUST_CODE_DETAIL);
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
                    mIntOffSet_My += mIntLimit_My;
                    new MyAuctionsAsyncTask().execute();
                }
                else{
                    Log.d("where", "그만 받아와");
                }

            }
        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        new MyAuctionsRefreshAsyncTask().execute();
    }


    public void onClick(View v){
        switch (v.getId()){
        }
    }

    public void dataReload(){
        mIntOffSet_My = 0;
        mAdapterMyCar.removeAllData();
        new MyAuctionsAsyncTask().execute();
    }



    /*************************************************************************************************************************************************/
    //내입찰상품관련 AsyncTask
    private class MyAuctionsAsyncTask extends AsyncTask<Void, Void, Void> {

        public MyAuctionsAsyncTask() {
        }

        @Override
        public Void doInBackground(Void... voids) {

            HashMap<String, String> map = new HashMap<>();
            map.put("token", SharedPreUtil.getTokenID(getActivity()));
            map.put("mybid", "Y");
            map.put("order", "reg_desc");
            map.put("limit", ""+mIntLimit_My);
            //            map.put("limit", "10");
            map.put("offset", ""+mIntOffSet_My);
            map.put("status_min", "300");
            map.put("status_max", "399");
            map.put("reg_date_min", mActivity.getStrStartDay());
            map.put("reg_date_max", mActivity.getStrEndDay());
            map.put("mybid_rank_min", "1");
            map.put("mybid_rank_max", "3");
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

                    mActivity.setTopCount(""+mIntTotal_My);
                    if(mIntTotal_My <= 0){
                        mLayoutNext.setVisibility(View.VISIBLE);
                    }else{
                        mLayoutNext.setVisibility(View.GONE);
                    }
                    mAdapterMyCar.addItems(mDataCar_My);
                    //                    holder.car_kms.setText(df.format(Double.parseDouble(data.getC_kms())) + "km");
//                    try{
//                        mTvTotalCount_My.setText(""+df.format(mIntTotal_My)+"대");
//                    }catch (Exception e){
//                        mTvTotalCount_My.setText(""+mIntTotal_My+"대");
//                    }

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
            map.put("order", "reg_desc");
            map.put("limit", ""+refreshOffset);//+mIntLimit_My);
            map.put("offset", "0");
            map.put("status_min", "200");
            map.put("status_max", "299");
            map.put("reg_date_min", mActivity.getStrStartDay());
            map.put("reg_date_max", mActivity.getStrEndDay());
            Call<AuctionsData> auctions = API_Adapter.getInstance().Auctions(map);
            auctions.enqueue(new Callback<AuctionsData>() {
                @Override
                public void onResponse(Call<AuctionsData> call, Response<AuctionsData> response) {
                    if(!GlobalValues.SERVER_SUCCESS.equals(response.body().getStatus_code())){
                        Toast.makeText(getActivity(), response.body().getStatus_msg(), Toast.LENGTH_SHORT).show();
                        return;
                    }
                    mDataCar_My = new ArrayList<AuctionsData.ResultObject.AuctionsObject>();
                    mIntTotal_My = response.body().getResult().getTotal_count();
                    mDataCar_My = response.body().getResult().getAuctions();
                    mAdapterMyCar.changeItem(mDataCar_My);

                    mActivity.setEntireCount(""+mIntTotal_My);
                    if(mIntTotal_My <= 0){
                        mLayoutNext.setVisibility(View.VISIBLE);
                    }else{
                        mLayoutNext.setVisibility(View.GONE);
                    }
//                    mTvTotalCount_My.setText(""+df.format(mIntTotal_My)+"대");

                    mListViewMyCar.setSelection(mIntListViewCurPosition);
                }

                @Override
                public void onFailure(Call<AuctionsData> call, Throwable t) {

                }
            });
            return null;
        }
    }


}
