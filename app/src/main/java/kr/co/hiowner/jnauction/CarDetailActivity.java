package kr.co.hiowner.jnauction;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.text.DecimalFormat;

import kr.co.hiowner.jnauction.car.CarData;

/**
 * Created by user on 2017-06-21.
 */
public class CarDetailActivity extends AppCompatActivity {

    private final int PAGER_COUNT = 4;

    Context mContext;
    ViewPager mPager;
    LinearLayout mLayoutPagerIndex;
    ImageView[] mIvIndex;

    CarData mCarData;

    TextView mTvCarName, mTvCarYear, mTvCarKms, mTvCarLocAddr, mTvCarNumber,
            mTvCarOpsTrans, mTvCarOpsHistory, mTvCarOpsFuel, mTvCarOpsBbox, mTvCarOpsSroof, mTvCarOpsSkey,
            mTvCarOpsRsensor, mTvCarOpsRcam, mTvCarOpsHeat, mTvCarOpsFan,
            mTvCarOpsAlu, mTvCarOpsAs, mTvCarOpsNavi, mTvCarOps4wd, mTvCarOpsMemo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_detail);

        mContext = CarDetailActivity.this;
        mCarData = getIntent().getParcelableExtra("car_data");

        mTvCarName = (TextView)findViewById(R.id.car_detail_txt_name);
        mTvCarYear = (TextView)findViewById(R.id.car_detail_txt_car_year);
        mTvCarKms = (TextView)findViewById(R.id.car_detail_txt_car_kms);
        mTvCarLocAddr = (TextView)findViewById(R.id.car_detail_txt_car_loc_addr);
        mTvCarNumber = (TextView)findViewById(R.id.car_detail_txt_car_number);
        mTvCarOpsTrans = (TextView)findViewById(R.id.cas_ops_trans);
        mTvCarOpsRsensor = (TextView)findViewById(R.id.cas_ops_rsensor);
        mTvCarOpsRcam = (TextView)findViewById(R.id.cas_ops_rcam);
        mTvCarOpsSroof = (TextView)findViewById(R.id.cas_ops_sroof);
        mTvCarOpsSkey = (TextView)findViewById(R.id.cas_ops_skey);
        mTvCarOpsHistory = (TextView)findViewById(R.id.cas_ops_history);
        mTvCarOpsHeat = (TextView)findViewById(R.id.cas_ops_heat);
        mTvCarOpsFuel = (TextView)findViewById(R.id.cas_ops_fuel);
        mTvCarOpsFan = (TextView)findViewById(R.id.cas_ops_fan);
        mTvCarOpsBbox = (TextView)findViewById(R.id.cas_ops_bbox);
        mTvCarOpsAlu = (TextView)findViewById(R.id.cas_ops_alu);
        mTvCarOpsAs = (TextView)findViewById(R.id.cas_ops_as);
        mTvCarOpsNavi = (TextView)findViewById(R.id.cas_ops_navi);
        mTvCarOps4wd = (TextView)findViewById(R.id.cas_ops_4wd);
//        mTvCarOpsMemo = (TextView)findViewById(R.id.cas_ops_memo);

        mTvCarName.setText(mCarData.getC_brand() + " "+ mCarData.getC_mname());
        mTvCarYear.setText(mCarData.getC_myear()+"년");
        DecimalFormat df = new DecimalFormat("###,###");
        mTvCarKms.setText(df.format(Double.parseDouble(mCarData.getC_kms())) + "km");
        mTvCarLocAddr.setText(mCarData.getC_loc_addr());
        mTvCarNumber.setText(mCarData.getC_num());
        if("A".equals(mCarData.getC_trans()))
            mTvCarOpsTrans.setText("자동");
        else if ("M".equals(mCarData.getC_trans())){
            mTvCarOpsTrans.setText("수동");
        }else
            mTvCarOpsTrans.setText(mCarData.getC_trans());

        if("Y".equals(mCarData.getC_history()))
            mTvCarOpsHistory.setText("사고차량");
        else if ("N".equals(mCarData.getC_history())){
            mTvCarOpsHistory.setText("무사고차량");
        }else
            mTvCarOpsTrans.setText(mCarData.getC_history());

        if("G".equals(mCarData.getC_fuel()))
            mTvCarOpsFuel.setText("가솔린");
        else if ("D".equals(mCarData.getC_fuel()))
            mTvCarOpsFuel.setText("디젤");
        else if ("L".equals(mCarData.getC_fuel()))
            mTvCarOpsFuel.setText("LPG");
        else if ("C".equals(mCarData.getC_fuel()))
            mTvCarOpsFuel.setText("CNG");
        else if ("H".equals(mCarData.getC_fuel()))
            mTvCarOpsFuel.setText("하이브리드");
        else if ("E".equals(mCarData.getC_fuel()))
            mTvCarOpsFuel.setText("전기");
        else
            mTvCarOpsFuel.setText(mCarData.getC_fuel());

        if("Y".equals(mCarData.getC_opt_rsensor()))
            mTvCarOpsRsensor.setTextColor(ContextCompat.getColor(mContext, R.color.text_color_333333));
        else if ("N".equals(mCarData.getC_opt_rsensor()))
            mTvCarOpsRsensor.setTextColor(ContextCompat.getColor(mContext, R.color.text_color_999999));
        else
            mTvCarOpsRsensor.setTextColor(ContextCompat.getColor(mContext, R.color.RED));

        if("Y".equals(mCarData.getC_opt_rcam()))
            mTvCarOpsRcam.setTextColor(ContextCompat.getColor(mContext, R.color.text_color_333333));
        else if ("N".equals(mCarData.getC_opt_rcam()))
            mTvCarOpsRcam.setTextColor(ContextCompat.getColor(mContext, R.color.text_color_999999));
        else
            mTvCarOpsRcam.setTextColor(ContextCompat.getColor(mContext, R.color.RED));

        if("Y".equals(mCarData.getC_opt_sroof()))
            mTvCarOpsSroof.setTextColor(ContextCompat.getColor(mContext, R.color.text_color_333333));
        else if ("N".equals(mCarData.getC_opt_sroof()))
            mTvCarOpsSroof.setTextColor(ContextCompat.getColor(mContext, R.color.text_color_999999));
        else
            mTvCarOpsSroof.setTextColor(ContextCompat.getColor(mContext, R.color.RED));

        if("Y".equals(mCarData.getC_opt_skey()))
            mTvCarOpsSkey.setTextColor(ContextCompat.getColor(mContext, R.color.text_color_333333));
        else if ("N".equals(mCarData.getC_opt_skey()))
            mTvCarOpsSkey.setTextColor(ContextCompat.getColor(mContext, R.color.text_color_999999));
        else
            mTvCarOpsSkey.setTextColor(ContextCompat.getColor(mContext, R.color.RED));

        if("Y".equals(mCarData.getC_opt_heat()))
            mTvCarOpsHeat.setTextColor(ContextCompat.getColor(mContext, R.color.text_color_333333));
        else if ("N".equals(mCarData.getC_opt_heat()))
            mTvCarOpsHeat.setTextColor(ContextCompat.getColor(mContext, R.color.text_color_999999));
        else
            mTvCarOpsHeat.setTextColor(ContextCompat.getColor(mContext, R.color.RED));

        if("Y".equals(mCarData.getC_opt_fan()))
            mTvCarOpsFan.setTextColor(ContextCompat.getColor(mContext, R.color.text_color_333333));
        else if ("N".equals(mCarData.getC_opt_fan()))
            mTvCarOpsFan.setTextColor(ContextCompat.getColor(mContext, R.color.text_color_999999));
        else
            mTvCarOpsFan.setTextColor(ContextCompat.getColor(mContext, R.color.RED));

        if("Y".equals(mCarData.getC_opt_bbox()))
            mTvCarOpsBbox.setTextColor(ContextCompat.getColor(mContext, R.color.text_color_333333));
        else if ("N".equals(mCarData.getC_opt_bbox()))
            mTvCarOpsBbox.setTextColor(ContextCompat.getColor(mContext, R.color.text_color_999999));
        else
            mTvCarOpsBbox.setTextColor(ContextCompat.getColor(mContext, R.color.RED));

        if("Y".equals(mCarData.getC_opt_alu()))
            mTvCarOpsAlu.setTextColor(ContextCompat.getColor(mContext, R.color.text_color_333333));
        else if ("N".equals(mCarData.getC_opt_alu()))
            mTvCarOpsAlu.setTextColor(ContextCompat.getColor(mContext, R.color.text_color_999999));
        else
            mTvCarOpsAlu.setTextColor(ContextCompat.getColor(mContext, R.color.RED));

        if("Y".equals(mCarData.getC_opt_as()))
            mTvCarOpsAs.setTextColor(ContextCompat.getColor(mContext, R.color.text_color_333333));
        else if ("N".equals(mCarData.getC_opt_as()))
            mTvCarOpsAs.setTextColor(ContextCompat.getColor(mContext, R.color.text_color_999999));
        else
            mTvCarOpsAs.setTextColor(ContextCompat.getColor(mContext, R.color.RED));

        if("Y".equals(mCarData.getC_opt_navi()))
            mTvCarOpsNavi.setTextColor(ContextCompat.getColor(mContext, R.color.text_color_333333));
        else if ("N".equals(mCarData.getC_opt_navi()))
            mTvCarOpsNavi.setTextColor(ContextCompat.getColor(mContext, R.color.text_color_999999));
        else
            mTvCarOpsNavi.setTextColor(ContextCompat.getColor(mContext, R.color.RED));

        if("Y".equals(mCarData.getC_opt_4wd()))
            mTvCarOps4wd.setTextColor(ContextCompat.getColor(mContext, R.color.text_color_333333));
        else if ("N".equals(mCarData.getC_opt_4wd()))
            mTvCarOps4wd.setTextColor(ContextCompat.getColor(mContext, R.color.text_color_999999));
        else
            mTvCarOps4wd.setTextColor(ContextCompat.getColor(mContext, R.color.RED));





        mPager = (ViewPager)findViewById(R.id.car_detail_pager);
        mLayoutPagerIndex = (LinearLayout)findViewById(R.id.car_detail_layout_index);

        mIvIndex = new ImageView[PAGER_COUNT];
        for(int i=0 ; i<PAGER_COUNT ; i++){

            ImageView iv = new ImageView(this);
            iv.setScaleType(ImageView.ScaleType.FIT_XY);
            mPager.addView(iv);




            mIvIndex[i] = new ImageView(this);
            if(i == 0) {
                mIvIndex[i].setBackgroundResource(R.drawable.pager_index_on);
            }else {
                mIvIndex[i].setBackgroundResource(R.drawable.pager_index);
            }
//            ViewGroup.MarginLayoutParams margin = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            lp.setMargins(getDP(5),0,0,0);
//            ViewGroup.MarginLayoutParams margin = (ViewGroup.MarginLayoutParams) mIvIndex[i].getLayoutParams();
//            margin.leftMargin = getDP(50);
            mIvIndex[i].setLayoutParams(lp);
            mLayoutPagerIndex.addView(mIvIndex[i]);
        }

        mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                for(int i=0 ; i<mIvIndex.length ; i++){
                    mIvIndex[i].setBackgroundResource(R.drawable.pager_index);
                }
                mIvIndex[position].setBackgroundResource(R.drawable.pager_index_on);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mPager.setAdapter(new pagerAdapter(getLayoutInflater()));


    }

    public int getDP(int value){
        DisplayMetrics dm = getResources().getDisplayMetrics();
        return Math.round(value * dm.density);
    }










    public class pagerAdapter extends PagerAdapter {
        LayoutInflater inflater;

        public pagerAdapter(LayoutInflater inflate) {
            inflater = inflate;
        }

        @Override
        public int getCount() {
            return PAGER_COUNT;
        }
        //ViewPager가 현재 보여질 Item(View객체)를 생성할 필요가 있는 때 자동으로 호출
        //쉽게 말해, 스크롤을 통해 현재 보여져야 하는 View를 만들어냄.
        //첫번째 파라미터 : ViewPager
        //두번째 파라미터 : ViewPager가 보여줄 View의 위치(가장 처음부터 0,1,2,3...)
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = null;
            //새로운 View 객체를 Layoutinflater를 이용해서 생성
            //만들어질 View의 설계는 res폴더>>layout폴더>>viewpater_childview.xml 레이아웃 파일 사용
            view = inflater.inflate(R.layout.row_viewpager, null);

            //만들어진 View안에 있는 ImageView 객체 참조
            //위에서 inflated 되어 만들어진 view로부터 findViewById()를 해야 하는 것에 주의.
            ImageView img = (ImageView) view.findViewById(R.id.row_img_view_pager);
            img.setScaleType(ImageView.ScaleType.FIT_XY);

            //ImageView에 현재 position 번째에 해당하는 이미지를 보여주기 위한 작업
            //현재 position에 해당하는 이미지를 setting
//            int id = getResources().getIdentifier("pager_img_"+ position, "mipmap", "jbink.jbinsoft.joonggonara_dealer");
//            img.setBackgroundResource(id);

            if(position == 0)
                Glide.with(mContext).load(mCarData.getC_img_1()).into(img);
            else if(position == 1)
                Glide.with(mContext).load(mCarData.getC_img_2()).into(img);
            else if(position == 2)
                Glide.with(mContext).load(mCarData.getC_img_3()).into(img);
            else if(position == 3)
                Glide.with(mContext).load(mCarData.getC_img_4()).into(img);

//            int color = ContextCompat.getColor(mContext, R.color.border_line);
//            if(position == 0)
//                color = ContextCompat.getColor(mContext, R.color.BLACK);
//            else if(position == 1)
//                color = ContextCompat.getColor(mContext, R.color.colorPrimaryDark);
//            else if(position == 2)
//                color = ContextCompat.getColor(mContext, R.color.WHITE);
//            if(position == 4)
//                color = ContextCompat.getColor(mContext, R.color.BLACK);
//            else if(position == 5)
//                color = ContextCompat.getColor(mContext, R.color.colorPrimaryDark);
//            else if(position == 6)
//                color = ContextCompat.getColor(mContext, R.color.WHITE);
//            img.setBackgroundColor(color);
//
//            //ViewPager에 만들어 낸 View 추가
            container.addView(view);
            //Image가 세팅된 View를 리턴
            return view;

        }

        //화면에 보이지 않은 View는파쾨를 해서 메모리를 관리함.
        //첫번째 파라미터 : ViewPager
        //두번째 파라미터 : 파괴될 View의 인덱스(가장 처음부터 0,1,2,3...)
        //세번째 파라미터 : 파괴될 객체(더 이상 보이지 않은 View 객체)
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            //ViewPager에서 보이지 않는 View는 제거
            //세번째 파라미터가 View 객체 이지만 데이터 타입이 Object여서 형변환 실시
            container.removeView((View) object);
        }


        //instantiateItem() 메소드에서 리턴된 Ojbect가 View가  맞는지 확인하는 메소드
        @Override
        public boolean isViewFromObject(View v, Object obj) {
            return v == obj;
        }


    }
}
