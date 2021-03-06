package kr.co.hiowner.jnauction.car.popup;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.Html;
import android.text.Selection;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.util.HashMap;

import kr.co.hiowner.jnauction.R;
import kr.co.hiowner.jnauction.api.API_Adapter;
import kr.co.hiowner.jnauction.api.ResponseBaseData;
import kr.co.hiowner.jnauction.util.GlobalValues;
import kr.co.hiowner.jnauction.util.SharedPreUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by user on 2017-07-03.
 */
public class TenderPopup2 extends AppCompatActivity{

    Context mContext;
    TextView mTvStr;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup_tender_2);

        mContext = TenderPopup2.this;//Html.fromHtml((getResources().getString(R.string.pager_txt_0)))

        mTvStr = (TextView)findViewById(R.id.tender_popup2_str);
        mTvStr.setText(GlobalValues.fromHtml((getResources().getString(R.string.str_popup_tender_2))));

    }

    public void onClick(View v){
        switch (v.getId()){
            case R.id.tender_popup2_btn_ok :
                setResult(RESULT_OK);
                finish();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_OK);
        finish();
    }
}
