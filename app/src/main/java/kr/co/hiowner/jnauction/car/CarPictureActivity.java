package kr.co.hiowner.jnauction.car;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;

import kr.co.hiowner.jnauction.R;

/**
 * Created by user on 2017-07-07.
 */
public class CarPictureActivity extends AppCompatActivity {

    private final int PAGER_COUNT = 4;

    Context mContext;
    ViewPager mPager;
    LinearLayout mLayoutPagerIndex;
    ImageView[] mIvIndex;

    String mStrPic1, mStrPic2, mStrPic3, mStrPic4;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_picture);

        mContext = CarPictureActivity.this;

        mStrPic1 = getIntent().getStringExtra("pic1");
        mStrPic2 = getIntent().getStringExtra("pic2");
        mStrPic3 = getIntent().getStringExtra("pic3");
        mStrPic4 = getIntent().getStringExtra("pic4");
//        Glide.with(mContext).load(getIntent().getStringExtra("pic1")).into(mIvPicture);


        mPager = (ViewPager)findViewById(R.id.car_picture_pager);
        mLayoutPagerIndex = (LinearLayout)findViewById(R.id.car_picture_layout_index);

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

        mPager.setAdapter(new pagerAdapter(getLayoutInflater()));

    }

    public void onClick(View v){
        switch (v.getId()){
            case R.id.car_picture_btn_back :
                finish();
                break;
        }
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
//            img.setScaleType(ImageView.ScaleType.CENTER_CROP);


            //ImageView에 현재 position 번째에 해당하는 이미지를 보여주기 위한 작업
            //현재 position에 해당하는 이미지를 setting
//            int id = getResources().getIdentifier("pager_img_"+ position, "mipmap", "jbink.jbinsoft.joonggonara_dealer");
//            img.setBackgroundResource(id);


            if(position == 0) {
                Glide.with(mContext).load(mStrPic1).into(img);
            }else if(position == 1){
                Glide.with(mContext).load(mStrPic2).into(img);
            } else if (position == 2) {
                Glide.with(mContext).load(mStrPic3).into(img);
            } else if (position == 3) {
                Glide.with(mContext).load(mStrPic4).into(img);
            }


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

    public int getDP(int value){
        DisplayMetrics dm = getResources().getDisplayMetrics();
        return Math.round(value * dm.density);
    }
}
