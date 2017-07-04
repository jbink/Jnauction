package kr.co.hiowner.jnauction.mypage;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import kr.co.hiowner.jnauction.R;

/**
 * Created by user on 2017-07-03.
 */
public class MyPagePopup extends AppCompatActivity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup_mypage);
    }

    public void onClick(View v){
        switch (v.getId()){
            case R.id.popup_mypage_btn_x :
                finish();
                break;
        }
    }
}
