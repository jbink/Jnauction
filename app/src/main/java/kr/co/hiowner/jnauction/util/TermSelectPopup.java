package kr.co.hiowner.jnauction.util;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import kr.co.hiowner.jnauction.R;

/**
 * Created by user on 2017-07-04.
 */
public class TermSelectPopup extends AppCompatActivity {

    private final int REQUST_CODE_START = 9124;
    private final int REQUST_CODE_END = 9125;

    Context mContext;
    TextView[] mTvTerm;
    TextView mTvStartDay, mTvEndDay;

    String mStrTermValue = null;
    String mStrServerTermValue = null;
    String mStrStartDay=null, mStrEndDay=null;
    String mStrStartPickerDay=null, mStrEndPickerDay=null;

    boolean mBoolSelectStartDay = false;

    SimpleDateFormat mFormatter;
    Calendar mCalendar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup_set_term);

        mContext = TermSelectPopup.this;

        mTvTerm = new TextView[]{
                (TextView)findViewById(R.id.date_sel_0_today),
                (TextView)findViewById(R.id.date_sel_1_entire),
                (TextView)findViewById(R.id.date_sel_2_one_week),
                (TextView)findViewById(R.id.date_sel_3_two_week),
                (TextView)findViewById(R.id.date_sel_4_one_month)
        };

        mTvStartDay = (TextView)findViewById(R.id.date_sel_5_start_day);
        mTvEndDay = (TextView)findViewById(R.id.date_sel_6_end_day);

        mStrTermValue = getIntent().getStringExtra("cur_term");

        //jslee++ 0719 시작일과 마지막일도 우선 넘겨받은대로 지정한다. 팝업을 띄웠을 때, 아무것도 하지 않고 확인을 누르면
        //기존 세팅대로 적용을 시키기 위해서입니다. ("날짜를 지정해주세요" 라고 뜨는게 이상해서입니다.)
        mStrStartDay = getIntent().getStringExtra("start_term");
        mStrEndDay = getIntent().getStringExtra("end_term");

        mFormatter = new SimpleDateFormat( "yyyy-MM-dd", Locale.KOREA );
        mCalendar = new GregorianCalendar(Locale.KOREA);

        switch (mStrTermValue){
            case "오늘" :
                setTermTvBack(0);
                break;
            case "전체" :
                setTermTvBack(1);
                break;
            case "1주" :
                setTermTvBack(2);break;
            case "2주" :
                setTermTvBack(3);break;
            case "1개월" :
                setTermTvBack(4);break;
            default:
                setTermTvBack(-1);
                String[] date = mStrTermValue.split(" - ");
                mTvStartDay.setTextColor(ContextCompat.getColor(mContext, R.color.colorPrimary));
                mTvStartDay.setText(date[0]);
                mTvEndDay.setTextColor(ContextCompat.getColor(mContext, R.color.colorPrimary));
                mTvEndDay.setText(date[1]);//ERROR
                break;
        }
    }



    public class DayComparisonTest {
        public void main(String args[]) {
            Calendar cal = Calendar.getInstance();
            SimpleDateFormat dateForm = new SimpleDateFormat("yyyy-MM-dd");
            Calendar aDate = Calendar.getInstance();

            // 비교하고자 하는 임의의 날짜
            aDate.set(2001, 0, 1);
            // 시스템 일시
            Calendar bDate = Calendar.getInstance();
            // 여기에 시,분,초를 0으로 세팅해야 before, after를 제대로 비교함
            aDate.set(Calendar.HOUR_OF_DAY, 0);
            aDate.set(Calendar.MINUTE, 0);
            aDate.set(Calendar.SECOND, 0);
            aDate.set(Calendar.MILLISECOND, 0);
            bDate.set(Calendar.HOUR_OF_DAY, 0);
            bDate.set(Calendar.MINUTE, 0);
            bDate.set(Calendar.SECOND, 0);
            bDate.set(Calendar.MILLISECOND, 0);
            if (aDate.after(bDate))
                // aDate가 bDate보다 클 경우 출력
                System.out.println("시스템 날짜보다 뒤일 경우 aDate = " + dateForm.format(aDate.getTime()));
            else if (aDate.before(bDate))
                // aDate가 bDate보다 작을 경우 출력
                System.out.println("시스템 날짜보다 앞일 경우 aDate = " + dateForm.format(aDate.getTime()));
            else
                // aDate = bDate인 경우
                System.out.println("같은 날이구만");
        }
    }

    public void onClick(View v){
        Intent intent;
        switch (v.getId()){
            case R.id.date_sel_btn_x :
            case R.id.date_sel_btn_close :                  //jslee 0731++ 하단 닫기 버튼
                setResult(RESULT_CANCELED);
                finish();
                break;
            case R.id.date_sel_btn_ok :
                if(TextUtils.isEmpty(mStrStartDay) || TextUtils.isEmpty(mStrEndDay) ){
                    Toast.makeText(TermSelectPopup.this, "날짜를 지정해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!TextUtils.isEmpty(mStrStartPickerDay) && !TextUtils.isEmpty(mStrEndPickerDay)){
                    mStrTermValue = mStrStartPickerDay + " - " + mStrEndPickerDay;
                }
                mStrServerTermValue = mStrStartDay+" - "+mStrEndDay;
                intent = new Intent();
                intent.putExtra("start_day", mStrStartDay);
                intent.putExtra("end_day", mStrEndDay);
                intent.putExtra("date", mStrTermValue);
                intent.putExtra("server_date", mStrServerTermValue);
                setResult(RESULT_OK, intent);
                finish();
                break;
            case R.id.date_sel_0_today :
                setTermTvBack(0);
                mCalendar.setTime(new Date());
                mStrTermValue = "오늘";
                mStrStartDay = mFormatter.format(mCalendar.getTime());
                mCalendar.add(Calendar.DAY_OF_YEAR, 1); // 하루를 더한다
                mStrEndDay = mFormatter.format(mCalendar.getTime());
                Log.d("where", mStrStartDay+" - "+mStrEndDay);
                break;
            case R.id.date_sel_1_entire :
                setTermTvBack(1);
                mStrTermValue = "전체";
                mCalendar.setTime(new Date());
                mCalendar.add(Calendar.DAY_OF_YEAR, 1); // 하루를 더한다
                mStrEndDay = mFormatter.format(mCalendar.getTime());
                mStrStartDay = "2000-01-01";
                break;
            case R.id.date_sel_2_one_week :
                setTermTvBack(2);
                mStrTermValue = "1주";
                mCalendar.setTime(new Date());
                mCalendar.add(Calendar.DAY_OF_YEAR, 1); // 하루를 더한다
                mStrEndDay = mFormatter.format(mCalendar.getTime());
                mCalendar.add(Calendar.DAY_OF_YEAR, -7); // 하루를 더한다.
                mStrStartDay = mFormatter.format(mCalendar.getTime());
                Log.d("where", mStrStartDay+" - "+mStrEndDay);
                break;
            case R.id.date_sel_3_two_week :
                setTermTvBack(3);
                mStrTermValue = "2주";
                mCalendar.setTime(new Date());
                mCalendar.add(Calendar.DAY_OF_YEAR, 1); // 하루를 더한다
                mStrEndDay = mFormatter.format(mCalendar.getTime());
                mCalendar.add(Calendar.DAY_OF_YEAR, -14); // 하루를 더한다.
                mStrStartDay = mFormatter.format(mCalendar.getTime());
                Log.d("where", mStrStartDay+" - "+mStrEndDay);
                break;
            case R.id.date_sel_4_one_month :
                setTermTvBack(4);
                mStrTermValue = "1개월";
                mCalendar.setTime(new Date());
                mCalendar.add(Calendar.DAY_OF_YEAR, 1); // 하루를 더한다
                mStrEndDay = mFormatter.format(mCalendar.getTime());
                mCalendar.add(Calendar.MONTH, -1); // 한달 전
                mStrStartDay = mFormatter.format(mCalendar.getTime());
                Log.d("where", mStrStartDay+" - "+mStrEndDay);
                break;
            case R.id.date_sel_5_start_day :
                setTermTvBack(-1);
                intent = new Intent(mContext, DatePickerActivity.class);
                startActivityForResult(intent, REQUST_CODE_START);
                break;
            case R.id.date_sel_6_end_day :
                if (mBoolSelectStartDay == false){
                    Toast.makeText(mContext, "시작일을 먼저 설정해주시기 바랍니다.", Toast.LENGTH_SHORT).show();
                    return;
                }
                setTermTvBack(-1);
                intent = new Intent(mContext, DatePickerActivity.class);
                startActivityForResult(intent, REQUST_CODE_END);
                break;
        }
    }

    private void setTermTvBack(int position){
        for (int i=0 ; i<mTvTerm.length ; i++){
            mTvTerm[i].setBackground(ContextCompat.getDrawable(mContext, R.drawable.border_rect_gray));
            mTvTerm[i].setTextColor(ContextCompat.getColor(mContext, R.color.text_color_333333));
        }
        mTvStartDay.setTextColor(ContextCompat.getColor(mContext, R.color.text_color_bfbfbf));
        mTvEndDay.setTextColor(ContextCompat.getColor(mContext, R.color.text_color_bfbfbf));
        if (position == -1){
            mTvStartDay.setTextColor(ContextCompat.getColor(mContext, R.color.colorPrimary));
            mTvEndDay.setTextColor(ContextCompat.getColor(mContext, R.color.colorPrimary));
        }else{
            mTvTerm[position].setBackgroundColor(ContextCompat.getColor(mContext, R.color.colorPrimary));
            mTvTerm[position].setTextColor(ContextCompat.getColor(mContext, R.color.WHITE));
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUST_CODE_START){
            if(resultCode == RESULT_OK){
                mBoolSelectStartDay = true;
                mTvStartDay.setTextColor(ContextCompat.getColor(mContext, R.color.colorPrimary));
                mStrStartPickerDay = data.getStringExtra("str_date");
                mTvStartDay.setText(mStrStartPickerDay);
                mStrStartDay = data.getStringExtra("server_date");

                if(!TextUtils.isEmpty(mStrEndDay)) {
                    try {
                        Date start = mFormatter.parse(mStrStartDay);
                        Date end = mFormatter.parse(mStrEndDay);
                        if (start.after(end) == true) {//3.after(5) = false
                            Toast.makeText(TermSelectPopup.this, "시작일보다 더 앞선 날을 선택하였습니다.", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(mContext, DatePickerActivity.class);
                            startActivityForResult(intent, REQUST_CODE_START);
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }else{
                //RESULT_CANCLE
            }
        }
        if(requestCode == REQUST_CODE_END){
            if(resultCode == RESULT_OK){
                mTvEndDay.setTextColor(ContextCompat.getColor(mContext, R.color.colorPrimary));
                mStrEndPickerDay = data.getStringExtra("str_date");
                mTvEndDay.setText(mStrEndPickerDay);
                mStrEndDay = data.getStringExtra("server_date");
                try {
                    Date start = mFormatter.parse(mStrStartDay);
                    Date end = mFormatter.parse(mStrEndDay);
                    if (start.after(end) == true){
                        Toast.makeText(TermSelectPopup.this, "시작일보다 더 앞선 날을 선택하였습니다.", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(mContext, DatePickerActivity.class);
                        startActivityForResult(intent, REQUST_CODE_END);
                    }else{
                    }

                } catch (ParseException e) {
                        e.printStackTrace();
                }

            }else{
                //RESULT_CANCLE
            }
        }
    }
}
