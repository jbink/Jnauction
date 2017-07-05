package kr.co.hiowner.jnauction.mypage.bid;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import kr.co.hiowner.jnauction.R;
import kr.co.hiowner.jnauction.fragment.AuctionFragment;
import kr.co.hiowner.jnauction.fragment.MyPageFragment;
import kr.co.hiowner.jnauction.mypage.bid.fragment.TodayFragment;

/**
 * Created by user on 2017-07-05.
 */
public class MyBidActivity extends AppCompatActivity{

    Context mContext;

    LinearLayout[] mLayoutTab;
    TextView[] mTvTab;
    TextView mTvTodayCount, mTvEntireCount, mTvTop3Count;

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_bid);

        mContext = MyBidActivity.this;

        mLayoutTab = new LinearLayout[]{
                (LinearLayout)findViewById(R.id.my_bid_btn_layout_0),
                (LinearLayout)findViewById(R.id.my_bid_btn_layout_1),
                (LinearLayout)findViewById(R.id.my_bid_btn_layout_2)
        };
        mTvTab = new TextView[]{
                (TextView)findViewById(R.id.my_bid_btn_txt_0),
                (TextView)findViewById(R.id.my_bid_btn_txt_1),
                (TextView)findViewById(R.id.my_bid_btn_txt_2)
        };


        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.my_bid_view_pager);
        mViewPager.addOnPageChangeListener(mChangeListener);
        mViewPager.setAdapter(mSectionsPagerAdapter);
    }


    public void onClick(View view) {
        switch (view.getId()){
            case R.id.my_bid_btn_layout_0 : setViewPagerPage(0); mViewPager.setCurrentItem(0); break;
            case R.id.my_bid_btn_layout_1 : setViewPagerPage(1); mViewPager.setCurrentItem(1); break;
            case R.id.my_bid_btn_layout_2 : setViewPagerPage(2); mViewPager.setCurrentItem(2); break;
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
//                mTvMainUnderline_1.setVisibility(View.VISIBLE);
//                mTvMainUnderline_2.setVisibility(View.GONE);
//                mTvMainTxt_1.setTextColor(ContextCompat.getColor(mContext, R.color.WHITE));
//                mTvMainTxt_2.setTextColor(ContextCompat.getColor(mContext, R.color.text_color_99ffffff));
                break;
            case 1:
//                mTvMainUnderline_1.setVisibility(View.GONE);
//                mTvMainUnderline_2.setVisibility(View.VISIBLE);
//                mTvMainTxt_2.setTextColor(ContextCompat.getColor(mContext, R.color.WHITE));
//                mTvMainTxt_1.setTextColor(ContextCompat.getColor(mContext, R.color.text_color_99ffffff));
                break;
        }
    }



    class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    return new TodayFragment().newInstance();
                case 1:
                    return new TodayFragment().newInstance();
                case 3:
                    return new TodayFragment().newInstance();
            }
            return null;
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "SECTION 1";
                case 1:
                    return "SECTION 2";
                case 2:
                    return "SECTION 3";
            }
            return null;
        }
    }
}
