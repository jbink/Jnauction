package kr.co.hiowner.jnauction;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import jbink.appnapps.okhttplibrary.ApiCall;
import kr.co.hiowner.jnauction.api.API_Adapter;
import kr.co.hiowner.jnauction.api.ResponseBaseData;
import kr.co.hiowner.jnauction.car.CarData;
import kr.co.hiowner.jnauction.user.UserData;
import kr.co.hiowner.jnauction.util.GlobalValues;
import kr.co.hiowner.jnauction.util.SharedPreUtil;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by user on 2017-06-23.
 */
public class LoginActivity extends AppCompatActivity {

    private final String LOGIN_URL = "/user/token";
    private final String AUTH_URL = "/user/phone_cert";
    private final String USER_URL = "/user";

    Context mContext;
    EditText mEdtPhone, mEdtAuthNum;
    CheckBox mChkAutoLogin;

    String mStrUUID = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mContext = LoginActivity.this;

        mEdtPhone = (EditText)findViewById(R.id.login_edt_phone);
        mEdtAuthNum = (EditText)findViewById(R.id.login_edt_auth);
        mChkAutoLogin = (CheckBox)findViewById(R.id.login_chk_auto);

        Log.d("where", "서버토큰"+SharedPreUtil.getTokenID(mContext));
        if(!TextUtils.isEmpty(SharedPreUtil.getTokenID(mContext))){
            new LoginAsyncTask().execute(USER_URL, SharedPreUtil.getTokenID(mContext));
        }

//        mChkAutoLogin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                Log.d("where", "a : " + mChkAutoLogin.isChecked());
//                if(mChkAutoLogin.isChecked()){
//                    mChkAutoLogin.setChecked(false);
//                }else{
//                    mChkAutoLogin.setChecked(true);
//                }
//                Log.d("where", "b : " + mChkAutoLogin.isChecked());
//            }
//        });
    }

    public void onClick(View v){
        switch (v.getId()){
            case R.id.login_btn_login :
                if (TextUtils.isEmpty(mEdtPhone.getText().toString())){
                    Toast.makeText(LoginActivity.this, "휴대폰 번호를 입력하세요", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(mEdtAuthNum.getText().toString())){
                    Toast.makeText(LoginActivity.this, "인증 번호를 입력하세요", Toast.LENGTH_SHORT).show();
                    return;
                }
                new LoginAsyncTask().execute(LOGIN_URL, mEdtPhone.getText().toString(), mEdtAuthNum.getText().toString());
                break;
            case R.id.login_btn_request :
                if (TextUtils.isEmpty(mEdtPhone.getText().toString())){
                    Toast.makeText(LoginActivity.this, "휴대폰 번호를 입력하세요", Toast.LENGTH_SHORT).show();
                    return;
                }
                new LoginAsyncTask().execute(AUTH_URL, mEdtPhone.getText().toString());
                break;
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////////
    OkHttpClient client = new OkHttpClient();
    private class LoginAsyncTask extends AsyncTask<String, Void, String> {
        String response = null;
        String base_url = null;

        @Override
        protected String doInBackground(String... params) {
            base_url = params[0];
            String full_url = null;
            FormBody fb = null;
            if(params[0].equals(AUTH_URL)){
                full_url = GlobalValues.SERVER + params[0]+"?phone="+params[1];
//                fb = new FormBody.Builder()
//                        .add("phone", params[1])
//                        .build();
            }else if (params[0].equals(LOGIN_URL)){
                full_url = GlobalValues.SERVER + params[0]+"?phone="+params[1]+"&phone_cert="+ params[2];
//                fb = new FormBody.Builder()
//                        .add("phone", params[1])
//                        .add("phone_cert", params[2])
//                        .build();
            }else if (params[0].equals(USER_URL)){
                full_url = GlobalValues.SERVER + params[0]+"?token="+params[1];
            }else{
                return "ERROR!!";
            }
            try {
                response = ApiCall.GET(client, full_url);
//                response = ApiCall.POST(
//                        client,
//                        GlobalValues.SERVER + params[0],
//                        fb
//                );
            } catch (IOException e) {
                e.printStackTrace();
            }

            if(!TextUtils.isEmpty(response)) {
                response = response.replace("\r", "");
                response = response.replace("\n", "");
                response = response.replace("\t", "");
            }

            return response;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            if(TextUtils.isEmpty(result)){
                return;
            }
            if(TextUtils.isEmpty(base_url)){
                return;
            }



            String status_code, status_msg = null;
            if (base_url.equals(LOGIN_URL)){
                String token = null;
                try {
                    JSONObject object = new JSONObject(result);
                    status_code = object.getString("status_code");
                    status_msg = object.getString("status_msg");

                    if(!"200".equals(status_code)){
                        Toast.makeText(LoginActivity.this, status_msg, Toast.LENGTH_SHORT).show();
                        return;
                    }
                    JSONObject jsonObjet_result = object.getJSONObject("result");
                    token = jsonObjet_result.getString("token");
                    Log.d("where", token);

                    if(mChkAutoLogin.isChecked() == true) SharedPreUtil.setAutoLogin(mContext, true);
                    else SharedPreUtil.setAutoLogin(mContext, false);

                    SharedPreUtil.setTokenID(mContext, token);

                    new LoginAsyncTask().execute(USER_URL, SharedPreUtil.getTokenID(mContext));

                } catch (JSONException e) {
                    Log.e("where", ""+e.getMessage());
                    e.printStackTrace();
                }
            }else if(base_url.equals(USER_URL)){
                new UserUpdateAsyncTask().execute();
            }else if(base_url.equals(AUTH_URL)){
                try {
                    JSONObject object = new JSONObject(result);
                    status_code = object.getString("status_code");
                    status_msg = object.getString("status_msg");
                    if(!"200".equals(status_code)){
                        Toast.makeText(LoginActivity.this, status_msg, Toast.LENGTH_SHORT).show();
                        return;
                    }
                    Toast.makeText(LoginActivity.this, "SMS로 받으신 인증번호를 입력해주세요", Toast.LENGTH_SHORT).show();

                } catch (JSONException e) {
                    Log.e("where", ""+e.getMessage());
                    e.printStackTrace();
                }
            }


        }
    }


    //USER INFO UPDATE
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private class UserUpdateAsyncTask extends AsyncTask<Void, Void, Void> {

        public UserUpdateAsyncTask(){

        }

        @Override
        protected Void doInBackground(Void... params) {

            //POST
            Call<ResponseBaseData> data = API_Adapter.getInstance().UserUpdate(
                    SharedPreUtil.getTokenID(LoginActivity.this),
                    "A",
                    Build.MODEL,
                    mStrUUID,
                    SharedPreUtil.getPushToken(mContext)
            );
            data.enqueue(new Callback<ResponseBaseData>() {
                @Override
                public void onResponse(Call<ResponseBaseData> call, Response<ResponseBaseData> response) {
                    Intent intent = new Intent(mContext, NewMainActivity.class);
                    startActivity(intent);
                    finish();
                }

                @Override
                public void onFailure(Call<ResponseBaseData> call, Throwable t) {
                    Log.d("where", "fail : "+ t.getMessage());
                }
            });
            return  null;
        }
    }
}
