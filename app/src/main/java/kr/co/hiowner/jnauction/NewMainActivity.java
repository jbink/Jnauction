package kr.co.hiowner.jnauction;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;

import kr.co.hiowner.jnauction.fragment.AuctionFragment;
import kr.co.hiowner.jnauction.fragment.MyPageFragmentNew;
import kr.co.hiowner.jnauction.util.SharedPreUtil;

/**
 * Created by user on 2017-06-26.
 */
public class NewMainActivity extends AppCompatActivity {

    Context mContext;
    String mStrPushToken;

    TextView mTvMainTxt_1, mTvMainTxt_2;
    ImageView mTvMainUnderline_1, mTvMainUnderline_2;

    private SectionsPagerAdapter mSectionsPagerAdapter;

    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_main);

        mContext = NewMainActivity.this;

        mStrPushToken = FirebaseInstanceId.getInstance().getToken();
        Log.d("where", "푸쉬토큰 : " + SharedPreUtil.getTokenID(mContext));

        mTvMainTxt_1 = (TextView)findViewById(R.id.main_btn_txt_1);
        mTvMainTxt_2 = (TextView)findViewById(R.id.main_btn_txt_2);
        mTvMainUnderline_1 = (ImageView)findViewById(R.id.main_btn_underline_1);
        mTvMainUnderline_2 = (ImageView)findViewById(R.id.main_btn_underline_2);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.main_view_pager);
        mViewPager.addOnPageChangeListener(mChangeListener);
        mViewPager.setAdapter(mSectionsPagerAdapter);
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

    public void onClick(View view) {
        switch (view.getId()){
            case R.id.main_btn_layout_1 : setViewPagerPage(0); mViewPager.setCurrentItem(0); break;
            case R.id.main_btn_layout_2 : setViewPagerPage(1); mViewPager.setCurrentItem(1); break;
        }
    }

    private void setViewPagerPage(int position){
        switch (position){
            case 0:
                mTvMainUnderline_1.setVisibility(View.VISIBLE);
                mTvMainUnderline_2.setVisibility(View.GONE);
                mTvMainTxt_1.setTextColor(ContextCompat.getColor(mContext, R.color.WHITE));
                mTvMainTxt_2.setTextColor(ContextCompat.getColor(mContext, R.color.text_color_99ffffff));
                break;
            case 1:
                mTvMainUnderline_1.setVisibility(View.GONE);
                mTvMainUnderline_2.setVisibility(View.VISIBLE);
                mTvMainTxt_2.setTextColor(ContextCompat.getColor(mContext, R.color.WHITE));
                mTvMainTxt_1.setTextColor(ContextCompat.getColor(mContext, R.color.text_color_99ffffff));
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }


    MyPageFragmentNew myPageFragment = new MyPageFragmentNew().newInstance();
    class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    return new AuctionFragment().newInstance();
                case 1:
                    return myPageFragment;
            }
            return null;
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
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

    public void refreshMyPageData(){
        myPageFragment.refreshData();
    }


    long pressedTime = 0;
    @Override
    public void onBackPressed() {//웹에서 뒤로가기 처리
        if (pressedTime == 0) {
            Toast.makeText(mContext, " 한 번 더 누르면 종료됩니다.", Toast.LENGTH_SHORT).show();
            pressedTime = System.currentTimeMillis();
        } else {
            int seconds = (int) (System.currentTimeMillis() - pressedTime);

            if (seconds > 2000) {
                Toast.makeText(mContext, " 한 번 더 누르면 종료됩니다.", Toast.LENGTH_SHORT).show();
                pressedTime = 0;
            } else {
                super.onBackPressed();
            }
        }
    }
}
