package kr.co.hiowner.jnauction;

import android.animation.ValueAnimator;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import kr.co.hiowner.jnauction.util.GlobalValues;

/**
 * Created by user on 2017-07-10.
 */
public class TestActivity extends AppCompatActivity {

    RelativeLayout mLayout;
    TextView mTv;

    ValueAnimator curTextAnimator;
    ValueAnimator curLayoutAnimator;
    ValueAnimator newTextAnimator;
    ValueAnimator newLayoutAnimator;

    Context mContext;

    int curHeight, newHeight;
    int curSize, newSize;
    int animationDuration = 600; // Animation duration in ms
    boolean mBoolZoom = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        mContext = TestActivity.this;

        mLayout = (RelativeLayout)findViewById(R.id.test_layout);
        mTv = (TextView)findViewById(R.id.test_tv);




//        curHeight =  GlobalValues.getDP(mContext, mLayout.getHeight());
//        newHeight = curHeight /2;
//        Log.d("where", "curHeight : " + curHeight);
//        curSize = GlobalValues.getDP(mContext, (int)mTv.getTextSize());

//        newSize = curSize /2;


        Log.d("where", "10px : " + GlobalValues.getDP(mContext, 10));


        curHeight = 900;
        newHeight = 300;

        curSize = 45;
        newSize = 15;

        curTextAnimator = ValueAnimator.ofInt(curSize, newSize);
        curTextAnimator.setDuration(animationDuration);
        curTextAnimator.addUpdateListener(a);

        curLayoutAnimator = ValueAnimator.ofInt(curHeight, newHeight);
        curLayoutAnimator.setDuration(animationDuration);
        curLayoutAnimator.addUpdateListener(s);

        newTextAnimator = ValueAnimator.ofInt(newSize, curSize);
        newTextAnimator.setDuration(animationDuration);
        newTextAnimator.addUpdateListener(a);

        newLayoutAnimator = ValueAnimator.ofInt(newHeight, curHeight);
        newLayoutAnimator.setDuration(animationDuration);
        newLayoutAnimator.addUpdateListener(s);
    }


    ValueAnimator.AnimatorUpdateListener a = new ValueAnimator.AnimatorUpdateListener() {

        @Override
        public void onAnimationUpdate(ValueAnimator valueAnimator) {
            Integer value = (Integer)valueAnimator.getAnimatedValue();
            mTv.setTextSize(value);
        }
    };
    ValueAnimator.AnimatorUpdateListener s = new ValueAnimator.AnimatorUpdateListener() {
        @Override
        public void onAnimationUpdate(ValueAnimator valueAnimator) {
            // get the value the interpolator is at
            Integer value = (Integer) valueAnimator.getAnimatedValue();
            // I'm going to set the layout's height 1:1 to the tick
            mLayout.getLayoutParams().height = value.intValue();
//                mLayout.setGravity(Gravity.CENTER);
            // force all layouts to see which ones are affected by
            // this layouts height change
            mLayout.requestLayout();
        }
    };


    public void onClick(View v){
        switch (v.getId()){
            case R.id.test_btn :
                if(mBoolZoom == false){
                    mBoolZoom = true;
                    curTextAnimator.start();
                    curLayoutAnimator.start();
                }else{
                    mBoolZoom = false;
                    newTextAnimator.start();
                    newLayoutAnimator.start();
                }
//                if(mBoolZoom == false){
//                    mBoolZoom = true;
//                    curLayoutAnimator = ValueAnimator
//                            .ofInt(curHeight , newHeight)
//                            .setDuration(animationDuration);
//                    curTextAnimator.ofInt(curSize, newSize);
//                }else{
//                    mBoolZoom = false;
//                    curLayoutAnimator = ValueAnimator
//                            .ofInt(newHeight, curHeight)
//                            .setDuration(animationDuration);
//                }
//                curTextAnimator.start();
//                curLayoutAnimator.start();
                break;
        }
    }
}
