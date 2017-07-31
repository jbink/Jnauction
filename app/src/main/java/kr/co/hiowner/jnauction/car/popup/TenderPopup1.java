package kr.co.hiowner.jnauction.car.popup;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.Selection;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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
public class TenderPopup1 extends AppCompatActivity{

    Context mContext;
    EditText mEdtCost;

    TextView mTvBrandName, mTvYear, mTvKms, mTvAddr, mTvPrice;

    String strCollectAmount ="";
    String mStrAuctionIdx ="";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup_tender_1);

        mContext = TenderPopup1.this;

        mTvBrandName = (TextView)findViewById(R.id.tender_popup1_txt_brand_name);
        mTvBrandName.setText(getIntent().getStringExtra("car_brand")+"/"+getIntent().getStringExtra("car_name"));
        mTvYear = (TextView)findViewById(R.id.tender_popup1_txt_year);
        mTvYear.setText(getIntent().getStringExtra("car_year")+"년");
        mTvKms = (TextView)findViewById(R.id.tender_popup1_txt_kms);
        mTvKms.setText(GlobalValues.getWonFormat(getIntent().getStringExtra("car_kms"))+"Km");
        mTvAddr = (TextView)findViewById(R.id.tender_popup1_txt_addr);
        mTvAddr.setText(getIntent().getStringExtra("car_addr"));
        mTvPrice = (TextView)findViewById(R.id.tender_popup1_txt_price);
        if("0".equals(getIntent().getStringExtra("car_price"))){
            mTvPrice.setText("3명 이상 입찰 시 노출");
        }else{
            mTvPrice.setText(GlobalValues.getWonFormat(getIntent().getStringExtra("car_price"))+"만원");
        }
        mStrAuctionIdx = getIntent().getStringExtra("car_auction_idx");

        ((TextView)findViewById(R.id.tender_popup1_txt_txt_1)).setText(GlobalValues.fromHtml((getResources().getString(R.string.str_popup_tender_1_1))));
        ((TextView)findViewById(R.id.tender_popup1_txt_txt_2)).setText(GlobalValues.fromHtml((getResources().getString(R.string.str_popup_tender_1_2))));

        mEdtCost = (EditText)findViewById(R.id.tender_popup1_edit_cost);
        mEdtCost.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // 숫자가 추가되었을때에 Comma를 추가해준다.
                if (!s.toString().equals(strCollectAmount)) {
                    // 숫자에 Comma를 추가해주는 메소드 호출
                    String input = s.toString().replace(",","");
                    input = input.replace("만원","");
                    strCollectAmount = makeStringWithComma(input, true)+"만원";
                    mEdtCost.setText(strCollectAmount);
                    Editable e = mEdtCost.getText();
                    // 커서의 위치가 현재 입력된 위치의 끝쪽에 가게 해야 한다.
                    Selection.setSelection(e, strCollectAmount.length()-2);
                }
            }
            @Override
            public void afterTextChanged(Editable editable) {
            }

        });
    }

    public void onClick(View v){
        switch (v.getId()){
            case R.id.tender_popup1_btn_x :
            case R.id.tender_popup1_btn_close :                 //jslee 0731++ 닫기버튼 하단추가
                finish();
                break;
            case R.id.tender_popup1_btn_ok :
                new BidAsyncTask().execute();
                break;
        }
    }

    public String makeStringWithComma(String string, boolean ignoreZero) {
        if (string.length() == 0) {
            return "";
        }
        try {
            if (string.indexOf(".") >= 0) {
                double value = Double.parseDouble(string);
                if (ignoreZero && value == 0) {
                    return "";
                }
                DecimalFormat format = new DecimalFormat("###,##0.00");
                return format.format(value);
            } else {
                long value = Long.parseLong(string);
                if (ignoreZero && value == 0) {
                    return "";
                }
                DecimalFormat format = new DecimalFormat("###,###");
                return format.format(value);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return string;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 7878){
            if(resultCode == RESULT_OK){
                setResult(RESULT_OK);
                finish();
            }
        }
    }

    //상품관련 AsyncTask
    private class BidAsyncTask extends AsyncTask<Void, Void, Void> {

        String strPrice = "";

        public BidAsyncTask() {
            strPrice = mEdtCost.getText().toString().replace(",", "").replace("만원", "");
        }

        @Override
        protected Void doInBackground(Void... voids) {

            HashMap<String, String> map = new HashMap<>();
            map.put("token", SharedPreUtil.getTokenID(mContext));
            map.put("auction_idx", mStrAuctionIdx);
            map.put("price", strPrice);

            Call<ResponseBaseData> auction = API_Adapter.getInstance().Tender(map);
            auction.enqueue(new Callback<ResponseBaseData>() {

                @Override
                public void onResponse(Call<ResponseBaseData> call, Response<ResponseBaseData> response) {

                    if (GlobalValues.SERVER_SUCCESS.equals(response.body().getStatus_code())){
                        //완료팝업호출
                        Intent intent = new Intent(mContext, TenderPopup2.class);
                        startActivityForResult(intent, 7878);
                    }else{
                        Toast.makeText(TenderPopup1.this, response.body().getStatus_msg(), Toast.LENGTH_SHORT).show();
                    }
//                    mCarData.setStatus_code(response.body().getStatus_code());
//                    mCarData.setStatus_msg(response.body().getStatus_msg());
//                    mCarData.setResult(response.body().getResult());
                }

                @Override
                public void onFailure(Call<ResponseBaseData> call, Throwable t) {

                }

            });
            return null;
        }
    }
}
