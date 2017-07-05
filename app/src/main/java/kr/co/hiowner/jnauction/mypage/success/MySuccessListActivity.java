package kr.co.hiowner.jnauction.mypage.success;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import kr.co.hiowner.jnauction.R;
import kr.co.hiowner.jnauction.api.API_Adapter;
import kr.co.hiowner.jnauction.api.data.AuctionsData;
import kr.co.hiowner.jnauction.car.CarDetailActivity;
import kr.co.hiowner.jnauction.car.CarListAdapter;
import kr.co.hiowner.jnauction.util.SharedPreUtil;
import kr.co.hiowner.jnauction.util.TermSelectPopup;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by user on 2017-07-04.
 */
public class MySuccessListActivity extends AppCompatActivity{

    private final int REQUST_CODE_DATE = 9123;
    private final int REQUST_CODE_DETAIL = 9124;

    Context mContext;

    TextView mTvTerm, mTvTotalCount;

    List<AuctionsData.ResultObject.AuctionsObject> mDataCar_My;
    ListView mListViewMyCar;
    MySuccessListAdapter mAdapterMyCar;

    //list의 Index 관리 - MY
    private int mIntOffSet_My = 0;
    private int mIntLimit_My = 10;

    //LIST의 결과값 총 갯수
    private int mIntTotal_My = 0;

    //화면에 리스트의 마지막 아이템이 보여지는지 체크
    boolean mLastItemMyVisibleFlag = false;

    //날짜
    String mStrStartDay=null, mStrEndDay=null;
    SimpleDateFormat mFormatter;
    Calendar mCalendar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage_success_list);

        mContext = MySuccessListActivity.this;//
        mTvTerm = (TextView)findViewById(R.id.my_success_txt_term);
        mTvTotalCount = (TextView)findViewById(R.id.my_success_txt_total);
        mListViewMyCar = (ListView) findViewById(R.id.my_success_listview);
        mAdapterMyCar = new MySuccessListAdapter(mContext);
        mListViewMyCar.setAdapter(mAdapterMyCar);
        mListViewMyCar.setOnItemClickListener(mItemClickMyListener);
        mListViewMyCar.setOnScrollListener(mOnScrollMyListener);

        mFormatter = new SimpleDateFormat( "yyyy-MM-dd", Locale.KOREA );
        mCalendar = new GregorianCalendar(Locale.KOREA);
        mStrStartDay = mFormatter.format(mCalendar.getTime());
        mCalendar.add(Calendar.DAY_OF_YEAR, 1); // 하루를 더한다
        mStrEndDay = mFormatter.format(mCalendar.getTime());

        new MyAuctionsAsyncTask().execute();
    }

    AdapterView.OnItemClickListener mItemClickMyListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            AuctionsData.ResultObject.AuctionsObject data = (AuctionsData.ResultObject.AuctionsObject) adapterView.getAdapter().getItem(i);
            Intent intent = new Intent(mContext, MySuccessDetailActivity.class);
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
                Log.d("where", " 내 입찰 바닥");
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

    public void onClick(View v){
        Intent intent;
        switch (v.getId()){
            case R.id.mysuccess_layout_btn_term :
            case R.id.mysuccess_btn_term :
                intent = new Intent(mContext, TermSelectPopup.class);
                Log.d("where", mTvTerm.getText().toString());
                intent.putExtra("cur_term", mTvTerm.getText().toString());
                startActivityForResult(intent, REQUST_CODE_DATE);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUST_CODE_DATE){
            if(resultCode == RESULT_OK){
                mStrStartDay = data.getStringExtra("start_day");
                mStrEndDay = data.getStringExtra("end_day");
                Toast.makeText(MySuccessListActivity.this, data.getStringExtra("start_day")+" - "+data.getStringExtra("end_day"), Toast.LENGTH_SHORT).show();
                mTvTerm.setText(data.getStringExtra("date"));

                new MyAuctionsAsyncTask().execute();
            }else{

            }
        }
        if(requestCode == REQUST_CODE_DETAIL){
            if(resultCode == RESULT_OK){
            }else{
            }
        }
    }



    //내입찰상품관련 AsyncTask
    private class MyAuctionsAsyncTask extends AsyncTask<Void, Void, Void> {

        public MyAuctionsAsyncTask() {
        }

        @Override
        protected Void doInBackground(Void... voids) {

            HashMap<String, String> map = new HashMap<>();
            map.put("token", SharedPreUtil.getTokenID(mContext));
            map.put("mybid", "Y");
            map.put("order", "reg_desc");
            map.put("limit", ""+mIntLimit_My);
            //            map.put("limit", "10");
            map.put("offset", ""+mIntOffSet_My);
            map.put("status_min", "300");
            map.put("status_max", "399");
            map.put("reg_date_min", mStrStartDay);
            map.put("reg_date_max", mStrEndDay);
            map.put("mybid_rank_min", "1");
            map.put("mybid_rank_max", "1");
            Call<AuctionsData> auctions = API_Adapter.getInstance().Auctions(map);
            auctions.enqueue(new Callback<AuctionsData>() {
                @Override
                public void onResponse(Call<AuctionsData> call, Response<AuctionsData> response) {
                    if(!"200".equals(response.body().getStatus_code())){
                        Toast.makeText(mContext, response.body().getStatus_msg(), Toast.LENGTH_SHORT).show();
                        return;
                    }
                    mDataCar_My = new ArrayList<AuctionsData.ResultObject.AuctionsObject>();
                    mIntTotal_My = response.body().getResult().getTotal_count();
                    mDataCar_My = response.body().getResult().getAuctions();
//                    mAdapterMyCar.addItems(mDataCar_My);
                    mAdapterMyCar.refreshItems(mDataCar_My);
                    DecimalFormat df = new DecimalFormat("###,###");
                    //                    holder.car_kms.setText(df.format(Double.parseDouble(data.getC_kms())) + "km");
                    mTvTotalCount.setText(""+df.format(mIntTotal_My)+"대");
                }

                @Override
                public void onFailure(Call<AuctionsData> call, Throwable t) {

                }
            });
            return null;
        }
    }
}
