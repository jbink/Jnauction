package kr.co.hiowner.jnauction.mypage.purchase;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import kr.co.hiowner.jnauction.R;
import kr.co.hiowner.jnauction.api.API_Adapter;
import kr.co.hiowner.jnauction.api.data.SalesData;
import kr.co.hiowner.jnauction.util.GlobalValues;
import kr.co.hiowner.jnauction.util.SharedPreUtil;
import kr.co.hiowner.jnauction.util.TermSelectPopup;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by user on 2017-07-04.
 */
public class MyPurchaseListActivity extends AppCompatActivity{

    private final int REQUST_CODE_DATE = 9123;
    private final int REQUST_CODE_DETAIL = 9124;

    Context mContext;

    TextView mTvTerm, mTvTotalCount;

    List<SalesData.ResultObject.SalesObject> mDataCar_My;
    ListView mListViewMyCar;
    MyPurchaseListAdapter mAdapterMyCar;


    //LIST의 결과값 총 갯수
    private int mIntTotal_My = 0;

    //화면에 리스트의 마지막 아이템이 보여지는지 체크
    boolean mLastItemMyVisibleFlag = false;

    //날짜
    String mStrStartDay=null, mStrEndDay=null;
    SimpleDateFormat mFormatter;
    Calendar mCalendar;

    TextView mTvEmpty;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage_purchase_list);

        mContext = MyPurchaseListActivity.this;//
        mTvTerm = (TextView)findViewById(R.id.my_purchase_txt_term);
        mTvTotalCount = (TextView)findViewById(R.id.my_purchase_txt_total);
        mTvEmpty = (TextView)findViewById(R.id.my_purchase_txt_empty);
        mListViewMyCar = (ListView) findViewById(R.id.my_purchase_listview);
        mAdapterMyCar = new MyPurchaseListAdapter(mContext);
        mListViewMyCar.setAdapter(mAdapterMyCar);
        mListViewMyCar.setOnItemClickListener(mItemClickMyListener);
//        mListViewMyCar.setOnScrollListener(mOnScrollMyListener);

        mFormatter = new SimpleDateFormat( "yyyy-MM-dd", Locale.KOREA );
        mCalendar = new GregorianCalendar(Locale.KOREA);

        //jslee 0719++ 나의 입찰차량 default 날짜를 "오늘"에서 "전체"로 변경

        //mStrStartDay = mFormatter.format(mCalendar.getTime());
        //mCalendar.add(Calendar.DAY_OF_YEAR, 1); // 하루를 더한다
        //mStrEndDay = mFormatter.format(mCalendar.getTime());

        mStrStartDay = "2000-01-01";
        mCalendar.add(Calendar.DAY_OF_YEAR, 1); // 하루를 더한다
        mStrEndDay = mFormatter.format(mCalendar.getTime());

        new MyAuctionsAsyncTask().execute();
    }

    AdapterView.OnItemClickListener mItemClickMyListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            SalesData.ResultObject.SalesObject data = (SalesData.ResultObject.SalesObject) adapterView.getAdapter().getItem(i);
            Intent intent = new Intent(mContext, MyPurchaseDetailActivity.class);
            intent.putExtra("auction_idx",  data.getAuction_idx());
            startActivityForResult(intent, REQUST_CODE_DETAIL);
        }
    };

//    AbsListView.OnScrollListener mOnScrollMyListener = new AbsListView.OnScrollListener() {
//        @Override
//        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
//            //현재 화면에 보이는 첫번째 리스트 아이템의 번호(firstVisibleItem) + 현재 화면에 보이는 리스트 아이템의 갯수(visibleItemCount)가 리스트 전체의 갯수(totalItemCount) -1 보다 크거나 같을때
//            mLastItemMyVisibleFlag = (totalItemCount > 0) && (firstVisibleItem + visibleItemCount >= totalItemCount);
//        }
//
//        @Override
//        public void onScrollStateChanged(AbsListView view, int scrollState) {
//            //OnScrollListener.SCROLL_STATE_IDLE은 스크롤이 이동하다가 멈추었을때 발생되는 스크롤 상태입니다.
//            //즉 스크롤이 바닦에 닿아 멈춘 상태에 처리를 하겠다는 뜻
//            if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE && mLastItemMyVisibleFlag) {
//                Log.d("where", " 내 입찰 바닥");
//                if(mIntOffSet_My < mIntTotal_My) {
//                    new MyAuctionsAsyncTask().execute();
//                    mIntOffSet_My += mIntLimit_My;
//                }
//                else{
//                    Log.d("where", "그만 받아와");
//                }
//
//            }
//        }
//    };

    public void onClick(View v){
        Intent intent;
        switch (v.getId()){
            case R.id.my_purchase_list_btn_back :
            case R.id.my_purchase_list_layout_back :
                finish();
                break;
            case R.id.my_purchase_btn_term :
            case R.id.my_purchase_layout_btn_term :
                intent = new Intent(mContext, TermSelectPopup.class);
                Log.d("where", mTvTerm.getText().toString());
                intent.putExtra("cur_term", mTvTerm.getText().toString());

                //jslee++ 0719 시작일과 마지막일도 같이 넘겨준다. 팝업을 띄웠을 때, 아무것도 하지 않고 확인을 누르면
                //기존 세팅대로 적용을 시키기 위해서입니다. ("날짜를 지정해주세요" 라고 뜨는게 이상해서입니다.)
                intent.putExtra("start_term", mStrStartDay);
                intent.putExtra("end_term", mStrEndDay);

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
                Toast.makeText(MyPurchaseListActivity.this, data.getStringExtra("start_day")+" - "+data.getStringExtra("end_day"), Toast.LENGTH_SHORT).show();
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
//            map.put("mybid", "Y");
//            map.put("order", "reg_desc");
//            map.put("limit", ""+mIntLimit_My);
//            //            map.put("limit", "10");
//            map.put("offset", ""+mIntOffSet_My);
//            map.put("status_min", "300");
//            map.put("status_max", "399");
            map.put("reg_date_min", mStrStartDay);
            map.put("reg_date_max", mStrEndDay);
//            map.put("mybid_rank_min", "1");
//            map.put("mybid_rank_max", "1");
            Call<SalesData> sales = API_Adapter.getInstance().Sales(map);
            sales.enqueue(new Callback<SalesData>() {
                @Override
                public void onResponse(Call<SalesData> call, Response<SalesData> response) {
                    if(!GlobalValues.SERVER_SUCCESS.equals(response.body().getStatus_code())){
                        Toast.makeText(mContext, response.body().getStatus_msg(), Toast.LENGTH_SHORT).show();
                        return;
                    }
                    mDataCar_My = new ArrayList<SalesData.ResultObject.SalesObject>();
                    mIntTotal_My = response.body().getResult().getSales().size();
                    mDataCar_My = response.body().getResult().getSales();
//                    mAdapterMyCar.addItems(mDataCar_My);
                    mAdapterMyCar.refreshItems(mDataCar_My);
                    mTvTotalCount.setText(""+ GlobalValues.getWonFormat(String.valueOf(mIntTotal_My))+"대");

                    if(mIntTotal_My == 0){
                        mTvEmpty.setVisibility(View.VISIBLE);
                    }else{
                        mTvEmpty.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onFailure(Call<SalesData> call, Throwable t) {

                }
            });
            return null;
        }
    }
}
