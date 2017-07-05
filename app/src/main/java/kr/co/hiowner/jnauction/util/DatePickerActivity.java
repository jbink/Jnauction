package kr.co.hiowner.jnauction.util;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;

import kr.co.hiowner.jnauction.R;


/**
 * Created by user on 2016-11-23.
 */
public class DatePickerActivity extends AppCompatActivity{
    DatePicker mDatePicker;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datepicker);

        mDatePicker = (DatePicker)findViewById(R.id.date_picker);
    }

    public void onClick(View v){
        switch (v.getId()){
            case R.id.date_picker_btn_ok :
                Intent intent = new Intent();
                intent.putExtra("server_date",""+mDatePicker.getYear() +"-"+ (GlobalValues.setTextAdd_0(mDatePicker.getMonth()+1))+"-"+ GlobalValues.setTextAdd_0(mDatePicker.getDayOfMonth()));
                intent.putExtra("str_date","" + (mDatePicker.getMonth()+1)+"월 "+ mDatePicker.getDayOfMonth()+"일");
                setResult(RESULT_OK, intent);
                finish();
                break;

        }
    }
}
