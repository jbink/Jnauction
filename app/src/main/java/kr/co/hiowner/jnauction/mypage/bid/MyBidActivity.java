package kr.co.hiowner.jnauction.mypage.bid;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

import kr.co.hiowner.jnauction.R;
import kr.co.hiowner.jnauction.api.API_Adapter;
import kr.co.hiowner.jnauction.api.data.ServerTimeData;
import kr.co.hiowner.jnauction.mypage.bid.fragment.entire.EntireFragment;
import kr.co.hiowner.jnauction.mypage.bid.fragment.top.TopFragment;
import kr.co.hiowner.jnauction.util.GlobalValues;
import kr.co.hiowner.jnauction.util.TermSelectPopup;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by user on 2017-07-05.
 */
public class MyBidActivity extends AppCompatActivity{
    private final int REQUST_CODE_DATE = 5565;

    Context mContext;

    LinearLayout[] mLayoutTab;
    TextView mTvMainTxt_1, mTvMainTxt_2;
    LinearLayout mLayoutMainTxt_1, mLayoutMainTxt_2;
    TextView mTvEntireCount, mTvTop3Count;

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;

    TextView mTvTerm, mTvRemainTime;
    //날짜
    String mStrStartDay=null, mStrEndDay=null;
    SimpleDateFormat mFormatter;
    Calendar mCalendar;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_bid);

        mContext = MyBidActivity.this;

        mLayoutMainTxt_1 = (LinearLayout)findViewById(R.id.my_bid_btn_layout_0);
        mLayoutMainTxt_2 = (LinearLayout)findViewById(R.id.my_bid_btn_layout_1);
        mTvMainTxt_1 = (TextView)findViewById(R.id.my_bid_btn_txt_0);
        mTvMainTxt_2 = (TextView)findViewById(R.id.my_bid_btn_txt_1);

        mTvTerm = (TextView)findViewById(R.id.my_success_txt_term);
        mTvEntireCount = (TextView)findViewById(R.id.my_bid_btn_count_0);
        mTvTop3Count = (TextView)findViewById(R.id.my_bid_btn_count_1);
        mTvRemainTime = (TextView)findViewById(R.id.my_bid_txt_remain_time);


        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.my_bid_view_pager);
        mViewPager.addOnPageChangeListener(mChangeListener);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        mFormatter = new SimpleDateFormat( "yyyy-MM-dd", Locale.KOREA );
        mCalendar = new GregorianCalendar(Locale.KOREA);
        mStrStartDay = mFormatter.format(mCalendar.getTime());
        mCalendar.add(Calendar.DAY_OF_YEAR, 1); // 하루를 더한다
        mStrEndDay = mFormatter.format(mCalendar.getTime());
    }


    public void onClick(View view) {
        Intent intent;
        switch (view.getId()){
            case R.id.my_bid_list_layout_back :
            case R.id.my_bid_list_btn_back :
                finish();
                break;
            case R.id.mysuccess_layout_btn_term :
            case R.id.mysuccess_btn_term :
            case R.id.my_success_layout_term :
                intent = new Intent(mContext, TermSelectPopup.class);
                Log.d("where", mTvTerm.getText().toString());
                intent.putExtra("cur_term", mTvTerm.getText().toString());
                startActivityForResult(intent, REQUST_CODE_DATE);
                break;
            case R.id.my_bid_btn_layout_0 : setViewPagerPage(0); mViewPager.setCurrentItem(0); break;
            case R.id.my_bid_btn_layout_1 : setViewPagerPage(1); mViewPager.setCurrentItem(1); break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUST_CODE_DATE){
            if(resultCode == RESULT_OK){
                mStrStartDay = data.getStringExtra("start_day");
                mStrEndDay = data.getStringExtra("end_day");
                Toast.makeText(MyBidActivity.this, data.getStringExtra("start_day")+" - "+data.getStringExtra("end_day"), Toast.LENGTH_SHORT).show();
                mTvTerm.setText(data.getStringExtra("date"));
                setTabNane(data.getStringExtra("date"));
                entireFragment.dataReload();
                topFragment.dataReload();

//                new MyAuctionsAsyncTask().execute();
            }else{

            }
        }
    }


    ViewPager.OnPageChangeListener mChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }
        @Override
        public void onPageSelected(int position) {
            setViewPagerPage(position);
        }
        @Override
        public void onPageScrollStateChanged(int state) {
        }
    };



    private void setViewPagerPage(int position){
        switch (position){
            case 0:
                mLayoutMainTxt_1.setBackgroundColor(ContextCompat.getColor(mContext, R.color.WHITE));
                mLayoutMainTxt_2.setBackgroundColor(ContextCompat.getColor(mContext, R.color.default_background));
                mTvMainTxt_1.setTextColor(ContextCompat.getColor(mContext, R.color.text_color_333333));
                mTvMainTxt_2.setTextColor(ContextCompat.getColor(mContext, R.color.text_color_808080));
                break;
            case 1:
                mLayoutMainTxt_1.setBackgroundColor(ContextCompat.getColor(mContext, R.color.default_background));
                mLayoutMainTxt_2.setBackgroundColor(ContextCompat.getColor(mContext, R.color.WHITE));
                mTvMainTxt_1.setTextColor(ContextCompat.getColor(mContext, R.color.text_color_808080));
                mTvMainTxt_2.setTextColor(ContextCompat.getColor(mContext, R.color.text_color_333333));
                break;
        }
    }

    public void setEntireCount(String value){
        mTvEntireCount.setText(value);
    }
    public void setTabNane(String name) {
        switch (name) {
            case "오늘":
            case "전체":
            case "1주":
            case "2주":
            case "1개월":
                mTvMainTxt_1.setText(name);
                break;
            default:
                mTvMainTxt_1.setText("선택기간");
                break;
        }
    }

    public void setTopCount(String value){
        mTvTop3Count.setText(value);
    }


    EntireFragment entireFragment = new EntireFragment().newInstance();
    TopFragment topFragment = new TopFragment().newInstance();
    class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    return entireFragment;
                case 1:
                    return topFragment;
            }
            return null;
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "SECTION 1";
                case 1:
                    return "SECTION 2";
            }
            return null;
        }
    }


    public String getStrStartDay() {
        return mStrStartDay;
    }

    public void setStrStartDay(String mStrStartDay) {
        this.mStrStartDay = mStrStartDay;
    }

    public String getStrEndDay() {
        return mStrEndDay;
    }

    public void setStrEndDay(String mStrEndDay) {
        this.mStrEndDay = mStrEndDay;
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
    //시간 관련 AsyncTask
    private class TimeAsyncTask extends AsyncTask<Void, Void, Void> {
        ServerTimeData TimeData;

        @Override
        public Void doInBackground(Void... voids) {
            Call<ServerTimeData> time = API_Adapter.getInstance().ServerTime();

            time.enqueue(new Callback<ServerTimeData>() {
                @Override
                public void onResponse(Call<ServerTimeData> call, Response<ServerTimeData> response) {
                    if (!GlobalValues.SERVER_SUCCESS.equals(response.body().getStatus_code())){
                        Toast.makeText(mContext, response.body().getStatus_msg(), Toast.LENGTH_SHORT).show();
                        return;
                    }
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


                        if(startHandler == null){
                            startHandler = new StartTimerHandler();
                        }
                        startHandler.sendEmptyMessageDelayed(0, (TimeData.getResult().getAuction_next_open_seconds()*1000));

                    }else if("O".equals(TimeData.getResult().getAuction_status())){
                        setTimeLayout();
                    }else{
                        Toast.makeText(mContext, TimeData.getStatus_msg(), Toast.LENGTH_SHORT).show();
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
                Toast.makeText(mContext, TimeData.getStatus_msg(), Toast.LENGTH_SHORT).show();
                return;
            }

            Message msg = new Message();
            msg.arg1 = TimeData.getResult().getAuction_close_seconds();

            if(timeHandler == null){
                timeHandler = new TimerHandler();
            }

            timeHandler.sendMessageDelayed(msg, 1000);


        }
    }

    TimerHandler timeHandler = new TimerHandler();
    class TimerHandler extends Handler {
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
                timeHandler.removeMessages(0);
                timeHandler = null;
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
        }
    }

    private String setTimeAdd_0(long value){
        String returnValue = null;
        returnValue = String.valueOf(value);
        if(returnValue.length() < 2)
            returnValue = "0"+returnValue;
        return returnValue;
    }

}
