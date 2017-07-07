package kr.co.hiowner.jnauction.mypage.bid.fragment.top;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.HashMap;

import kr.co.hiowner.jnauction.R;
import kr.co.hiowner.jnauction.api.API_Adapter;
import kr.co.hiowner.jnauction.api.data.AuctionData;
import kr.co.hiowner.jnauction.api.data.ServerTimeData;
import kr.co.hiowner.jnauction.car.CarPictureActivity;
import kr.co.hiowner.jnauction.car.popup.TenderPopup1;
import kr.co.hiowner.jnauction.car.popup.TenderPopupRe;
import kr.co.hiowner.jnauction.util.GlobalValues;
import kr.co.hiowner.jnauction.util.SharedPreUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by user on 2017-06-21.
 */
public class TopDetailActivity extends AppCompatActivity {

    private final int PAGER_COUNT = 4;

    Context mContext;
    ViewPager mPager;
    LinearLayout mLayoutPagerIndex;
    ImageView[] mIvIndex;

    String mStrAuctionIdx = null;
    AuctionData mCarData;

    TextView mTvCarName, mTvCarYear, mTvCarKms, mTvCarLocAddr, mTvCarNumber,
            mTvCarOpsTrans, mTvCarOpsHistory, mTvCarOpsFuel, mTvCarOpsBbox, mTvCarOpsSroof, mTvCarOpsSkey,
            mTvCarOpsRsensor, mTvCarOpsRcam, mTvCarOpsHeat, mTvCarOpsFan,
            mTvCarOpsAlu, mTvCarOpsAs, mTvCarOpsNavi, mTvCarOps4wd, mTvCarOpsMemo;

    TextView mTvBidCount, mTvRemainTime, mTvAvgPrice;

    //나의 입찰가
    RelativeLayout mRLayoutMyPrice;
    TextView mTvMyPrice, mTvMyPriceDate;

    Button mBtnTender;

    //결과값 리턴을 위해서
    boolean mBoolResult = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_detail);

        mContext = TopDetailActivity.this;
//        mCarData = getIntent().getParcelableExtra("car_data");
        mStrAuctionIdx = getIntent().getStringExtra("auction_idx");

        mTvCarName = (TextView)findViewById(R.id.top_detail_txt_name);
        mTvCarYear = (TextView)findViewById(R.id.top_detail_txt_car_year);
        mTvCarKms = (TextView)findViewById(R.id.top_detail_txt_car_kms);
        mTvCarLocAddr = (TextView)findViewById(R.id.top_detail_txt_car_loc_addr);
        mTvCarNumber = (TextView)findViewById(R.id.top_detail_txt_car_number);
        mTvBidCount = (TextView)findViewById(R.id.top_detail_txt_bid_count);
        mTvRemainTime = (TextView)findViewById(R.id.top_detail_txt_remain_time);
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
        mBtnTender = (Button) findViewById(R.id.top_detail_btn_buy);
        mTvAvgPrice = (TextView) findViewById(R.id.top_detail_txt_car_avg_price);
        mRLayoutMyPrice = (RelativeLayout) findViewById(R.id.top_detail_layout_car_my);
        mTvMyPrice = (TextView) findViewById(R.id.top_detail_txt_car_my_price);
        mTvMyPriceDate = (TextView) findViewById(R.id.top_detail_txt_car_my_date);
//        mTvCarOpsMemo = (TextView)findViewById(R.id.cas_ops_memo);



        mPager = (ViewPager)findViewById(R.id.top_detail_pager);
        mLayoutPagerIndex = (LinearLayout)findViewById(R.id.top_detail_layout_index);

        mIvIndex = new ImageView[PAGER_COUNT];
        for(int i=0 ; i<PAGER_COUNT ; i++){

            ImageView iv = new ImageView(this);
            iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
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

        new AuctionIdxAsyncTask().execute();
        new TimeAsyncTask().execute();
    }


    public void onClick(View v){
        Intent intent;
        switch (v.getId()){
            case R.id.top_detail_btn_back :
            case R.id.top_detail_layout_back :
                if(mBoolResult == true){
                    setResult(RESULT_OK);
                    finish();
                }else{
                    setResult(RESULT_CANCELED);
                    finish();
                }
                break;
            case R.id.top_detail_btn_buy:
                intent = new Intent(mContext, TenderPopup1.class);
                intent.putExtra("car_brand", mCarData.getResult().getC_brand());
                intent.putExtra("car_name", mCarData.getResult().getC_mname());
                intent.putExtra("car_year", mCarData.getResult().getC_myear());
                intent.putExtra("car_kms", mCarData.getResult().getC_kms());
                intent.putExtra("car_addr", mCarData.getResult().getC_loc_addr());
                intent.putExtra("car_price", mCarData.getResult().getA_avg_price());
                intent.putExtra("car_auction_idx", mCarData.getResult().getAuction_idx());
                startActivityForResult(intent, 4454);
                break;
            case R.id.top_detail_btn_car_my_retry ://재입찰
                intent = new Intent(mContext, TenderPopupRe.class);
                intent.putExtra("car_brand", mCarData.getResult().getC_brand());
                intent.putExtra("car_name", mCarData.getResult().getC_mname());
                intent.putExtra("car_year", mCarData.getResult().getC_myear());
                intent.putExtra("car_kms", mCarData.getResult().getC_kms());
                intent.putExtra("car_addr", mCarData.getResult().getC_loc_addr());
                intent.putExtra("car_price", mCarData.getResult().getA_avg_price());
                intent.putExtra("car_my_price", mCarData.getResult().getB_price());
                intent.putExtra("car_auction_idx", mCarData.getResult().getAuction_idx());
                startActivityForResult(intent, 4455);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if(mBoolResult == true){
            setResult(RESULT_OK);
            finish();
        }else{
            setResult(RESULT_CANCELED);
            finish();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 4454) {
            if (resultCode == RESULT_OK) {
                mBoolResult = true;
                new AuctionIdxAsyncTask().execute();
//                setResult(RESULT_OK);
//                finish();
            } else if (resultCode == RESULT_CANCELED) {
            }
        }
        if(requestCode == 4455) {
            if (resultCode == RESULT_OK) {
                new AuctionIdxAsyncTask().execute();
            } else if (resultCode == RESULT_CANCELED) {
            }
        }
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
            img.setScaleType(ImageView.ScaleType.CENTER_CROP);

            //ImageView에 현재 position 번째에 해당하는 이미지를 보여주기 위한 작업
            //현재 position에 해당하는 이미지를 setting
//            int id = getResources().getIdentifier("pager_img_"+ position, "mipmap", "jbink.jbinsoft.joonggonara_dealer");
//            img.setBackgroundResource(id);

            if(position == 0) {
                Glide.with(mContext).load(mCarData.getResult().getC_img_1()).into(img);
            }else if(position == 1){
                Glide.with(mContext).load(mCarData.getResult().getC_img_2()).into(img);
            } else if (position == 2) {
                Glide.with(mContext).load(mCarData.getResult().getC_img_3()).into(img);
            } else if (position == 3) {
                Glide.with(mContext).load(mCarData.getResult().getC_img_4()).into(img);
            }


            img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, CarPictureActivity.class);
                    intent.putExtra("pic1", mCarData.getResult().getC_img_1());
                    intent.putExtra("pic2", mCarData.getResult().getC_img_2());
                    intent.putExtra("pic3", mCarData.getResult().getC_img_3());
                    intent.putExtra("pic4", mCarData.getResult().getC_img_4());
                    startActivity(intent);
                }
            });

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


    //상품관련 AsyncTask
    private class AuctionIdxAsyncTask extends AsyncTask<Void, Void, Void> {

        public AuctionIdxAsyncTask() {
        }

        @Override
        protected Void doInBackground(Void... voids) {


            HashMap<String, String> map = new HashMap<>();
            map.put("token", SharedPreUtil.getTokenID(mContext));
            map.put("auction_idx", mStrAuctionIdx);
            Call<AuctionData> auction = API_Adapter.getInstance().AuctionIdx(map);
            auction.enqueue(new Callback<AuctionData>() {

                @Override
                public void onResponse(Call<AuctionData> call, Response<AuctionData> response) {
                    if (!GlobalValues.SERVER_SUCCESS.equals(response.body().getStatus_code())){
                        Toast.makeText(mContext, response.body().getStatus_msg(), Toast.LENGTH_SHORT).show();
                        return;
                    }
                    mCarData = new AuctionData();

                    mCarData.setStatus_code(response.body().getStatus_code());
                    mCarData.setStatus_msg(response.body().getStatus_msg());
                    mCarData.setResult(response.body().getResult());
                    setDetailView();
                }

                @Override
                public void onFailure(Call<AuctionData> call, Throwable t) {

                }
            });
            return null;
        }
    }

    public void setDetailView(){
        mTvCarName.setText(mCarData.getResult().getC_brand() + " "+ mCarData.getResult().getC_mname());
        mTvCarYear.setText(mCarData.getResult().getC_myear()+"년");
        mTvCarKms.setText(GlobalValues.getWonFormat(mCarData.getResult().getC_kms()) + "km");
        mTvCarLocAddr.setText(mCarData.getResult().getC_loc_addr());
        mTvCarNumber.setText(mCarData.getResult().getC_num());
        mTvBidCount.setText(mCarData.getResult().getA_bid_count()+"명 입찰중");
        if("A".equals(mCarData.getResult().getC_trans()))
            mTvCarOpsTrans.setText("자동");
        else if ("M".equals(mCarData.getResult().getC_trans())){
            mTvCarOpsTrans.setText("수동");
        }else
            mTvCarOpsTrans.setText(mCarData.getResult().getC_trans());

        if("Y".equals(mCarData.getResult().getC_history()))
            mTvCarOpsHistory.setText("사고차량");
        else if ("N".equals(mCarData.getResult().getC_history())){
            mTvCarOpsHistory.setText("무사고차량");
        }else
            mTvCarOpsTrans.setText(mCarData.getResult().getC_history());

        if("G".equals(mCarData.getResult().getC_fuel()))
            mTvCarOpsFuel.setText("가솔린");
        else if ("D".equals(mCarData.getResult().getC_fuel()))
            mTvCarOpsFuel.setText("디젤");
        else if ("L".equals(mCarData.getResult().getC_fuel()))
            mTvCarOpsFuel.setText("LPG");
        else if ("C".equals(mCarData.getResult().getC_fuel()))
            mTvCarOpsFuel.setText("CNG");
        else if ("H".equals(mCarData.getResult().getC_fuel()))
            mTvCarOpsFuel.setText("하이브리드");
        else if ("E".equals(mCarData.getResult().getC_fuel()))
            mTvCarOpsFuel.setText("전기");
        else
            mTvCarOpsFuel.setText(mCarData.getResult().getC_fuel());

        if("Y".equals(mCarData.getResult().getC_opt_rsensor()))
            mTvCarOpsRsensor.setTextColor(ContextCompat.getColor(mContext, R.color.colorPrimary));
        else if ("N".equals(mCarData.getResult().getC_opt_rsensor()))
            mTvCarOpsRsensor.setTextColor(ContextCompat.getColor(mContext, R.color.text_color_999999));
        else
            mTvCarOpsRsensor.setTextColor(ContextCompat.getColor(mContext, R.color.RED));

        if("Y".equals(mCarData.getResult().getC_opt_rcam()))
            mTvCarOpsRcam.setTextColor(ContextCompat.getColor(mContext, R.color.colorPrimary));
        else if ("N".equals(mCarData.getResult().getC_opt_rcam()))
            mTvCarOpsRcam.setTextColor(ContextCompat.getColor(mContext, R.color.text_color_999999));
        else
            mTvCarOpsRcam.setTextColor(ContextCompat.getColor(mContext, R.color.RED));

        if("Y".equals(mCarData.getResult().getC_opt_sroof()))
            mTvCarOpsSroof.setTextColor(ContextCompat.getColor(mContext, R.color.colorPrimary));
        else if ("N".equals(mCarData.getResult().getC_opt_sroof()))
            mTvCarOpsSroof.setTextColor(ContextCompat.getColor(mContext, R.color.text_color_999999));
        else
            mTvCarOpsSroof.setTextColor(ContextCompat.getColor(mContext, R.color.RED));

        if("Y".equals(mCarData.getResult().getC_opt_skey()))
            mTvCarOpsSkey.setTextColor(ContextCompat.getColor(mContext, R.color.colorPrimary));
        else if ("N".equals(mCarData.getResult().getC_opt_skey()))
            mTvCarOpsSkey.setTextColor(ContextCompat.getColor(mContext, R.color.text_color_999999));
        else
            mTvCarOpsSkey.setTextColor(ContextCompat.getColor(mContext, R.color.RED));

        if("Y".equals(mCarData.getResult().getC_opt_heat()))
            mTvCarOpsHeat.setTextColor(ContextCompat.getColor(mContext, R.color.colorPrimary));
        else if ("N".equals(mCarData.getResult().getC_opt_heat()))
            mTvCarOpsHeat.setTextColor(ContextCompat.getColor(mContext, R.color.text_color_999999));
        else
            mTvCarOpsHeat.setTextColor(ContextCompat.getColor(mContext, R.color.RED));

        if("Y".equals(mCarData.getResult().getC_opt_fan()))
            mTvCarOpsFan.setTextColor(ContextCompat.getColor(mContext, R.color.colorPrimary));
        else if ("N".equals(mCarData.getResult().getC_opt_fan()))
            mTvCarOpsFan.setTextColor(ContextCompat.getColor(mContext, R.color.text_color_999999));
        else
            mTvCarOpsFan.setTextColor(ContextCompat.getColor(mContext, R.color.RED));

        if("Y".equals(mCarData.getResult().getC_opt_bbox()))
            mTvCarOpsBbox.setTextColor(ContextCompat.getColor(mContext, R.color.colorPrimary));
        else if ("N".equals(mCarData.getResult().getC_opt_bbox()))
            mTvCarOpsBbox.setTextColor(ContextCompat.getColor(mContext, R.color.text_color_999999));
        else
            mTvCarOpsBbox.setTextColor(ContextCompat.getColor(mContext, R.color.RED));

        if("Y".equals(mCarData.getResult().getC_opt_alu()))
            mTvCarOpsAlu.setTextColor(ContextCompat.getColor(mContext, R.color.colorPrimary));
        else if ("N".equals(mCarData.getResult().getC_opt_alu()))
            mTvCarOpsAlu.setTextColor(ContextCompat.getColor(mContext, R.color.text_color_999999));
        else
            mTvCarOpsAlu.setTextColor(ContextCompat.getColor(mContext, R.color.RED));

        if("Y".equals(mCarData.getResult().getC_opt_as()))
            mTvCarOpsAs.setTextColor(ContextCompat.getColor(mContext, R.color.colorPrimary));
        else if ("N".equals(mCarData.getResult().getC_opt_as()))
            mTvCarOpsAs.setTextColor(ContextCompat.getColor(mContext, R.color.text_color_999999));
        else
            mTvCarOpsAs.setTextColor(ContextCompat.getColor(mContext, R.color.RED));

        if("Y".equals(mCarData.getResult().getC_opt_navi()))
            mTvCarOpsNavi.setTextColor(ContextCompat.getColor(mContext, R.color.colorPrimary));
        else if ("N".equals(mCarData.getResult().getC_opt_navi()))
            mTvCarOpsNavi.setTextColor(ContextCompat.getColor(mContext, R.color.text_color_999999));
        else
            mTvCarOpsNavi.setTextColor(ContextCompat.getColor(mContext, R.color.RED));

        if("Y".equals(mCarData.getResult().getC_opt_4wd()))
            mTvCarOps4wd.setTextColor(ContextCompat.getColor(mContext, R.color.colorPrimary));
        else if ("N".equals(mCarData.getResult().getC_opt_4wd()))
            mTvCarOps4wd.setTextColor(ContextCompat.getColor(mContext, R.color.text_color_999999));
        else
            mTvCarOps4wd.setTextColor(ContextCompat.getColor(mContext, R.color.RED));

        //평균가격표시
//        int price = Integer.parseInt(mCarData.getResult().getA_avg_price());
//        if(price == 0) {
//            mTvAvgPrice.setTextSize(getDP(6));
//            mTvAvgPrice.setText("평균가는 입찰인이 3명 이상일 경우에 공개됩니다.");
//        }
//        else {
//            mTvAvgPrice.setTextSize(getDP(12));
//            mTvAvgPrice.setText(GlobalValues.getWonFormat(mCarData.getResult().getA_avg_price()) + "만원");
//        }

        try{
            mTvAvgPrice.setText(GlobalValues.getWonFormat(mCarData.getResult().getA_max_price()) + "만원");
        }catch (Exception e){
            mTvAvgPrice.setText(mCarData.getResult().getA_max_price() + "만원");
        }

        //나의 입찰가격
//        if("Y".equals(mCarData.getResult().getB_mybid())){
//            mRLayoutMyPrice.setVisibility(View.VISIBLE);
//            mTvMyPrice.setText(GlobalValues.getWonFormat(mCarData.getResult().getB_price()) +"만원");
//            mTvMyPriceDate.setText("최종입찰일 " + mCarData.getResult().getB_upd_date());
//        }else{
//            mRLayoutMyPrice.setVisibility(View.GONE);
//        }


        //입찰여부
        int status = Integer.parseInt(mCarData.getResult().getA_status());

        if(status >= 100 && status < 200){//입찰대기
        }else if (status >= 200 && status < 300){//입찰중
            if ("Y".equals(mCarData.getResult().getB_mybid())){
                mBtnTender.setBackgroundColor(ContextCompat.getColor(mContext, R.color.background_color_e0e0e0));
                mBtnTender.setText("입찰 완료된 차량입니다.");
                mBtnTender.setTextColor(ContextCompat.getColor(mContext, R.color.text_color_b3b3b3));
                mBtnTender.setEnabled(false);
            }else{
//                mBtnTender.setBackgroundColor(ContextCompat.getColor(mContext, R.color.background_color_e0e0e0));
//                mBtnTender.setText("입찰 완료된 차량입니다.");
//                mBtnTender.setTextColor(ContextCompat.getColor(mContext, R.color.text_color_b3b3b3));
            }
        }else if (status >= 300 && status < 400){//입찰완료
            mBtnTender.setBackgroundColor(ContextCompat.getColor(mContext, R.color.background_color_e0e0e0));
            mBtnTender.setText("경매 종료된 차량입니다.");
            mBtnTender.setTextColor(ContextCompat.getColor(mContext, R.color.text_color_b3b3b3));
            mBtnTender.setEnabled(false);
        }else if (status >= 400 && status < 500){//매입완료
        }


        mPager.setAdapter(new pagerAdapter(getLayoutInflater()));
    }





    /*************************************************************************************************************************************************/
    //시간 관련 AsyncTask
    private class TimeAsyncTask extends AsyncTask<Void, Void, Void>{
        ServerTimeData TimeData;

        @Override
        protected Void doInBackground(Void... voids) {
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
//            long server_time = Long.parseLong(TimeData.getResult().getAuction_close_seconds());

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

            mTvRemainTime.setText(""+GlobalValues.setTextAdd_0(hrs)+":"+GlobalValues.setTextAdd_0(min)+":"+GlobalValues.setTextAdd_0(sec));


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



}
