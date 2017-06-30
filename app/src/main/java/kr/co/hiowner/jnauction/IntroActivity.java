package kr.co.hiowner.jnauction;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;

import java.util.UUID;

import kr.co.hiowner.jnauction.api.API_Adapter;
import kr.co.hiowner.jnauction.api.ResponseBaseData;
import kr.co.hiowner.jnauction.util.SharedPreUtil;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by user on 2017-06-27.
 */
public class IntroActivity extends AppCompatActivity{

    Context mContext;
    String mStrPushToken = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        mContext = IntroActivity.this;
        Log.d("where", "토큰" + SharedPreUtil.getTokenID(mContext));

        mStrPushToken = FirebaseInstanceId.getInstance().getToken();
        if(TextUtils.isEmpty(mStrPushToken)){
            mHandler.sendEmptyMessageDelayed(0, 500);
        }else{
            delayHandler.sendEmptyMessageDelayed(0, 500);
        }
    }

    Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            mStrPushToken = FirebaseInstanceId.getInstance().getToken();
            if(TextUtils.isEmpty(mStrPushToken)){
                mHandler.sendEmptyMessageDelayed(0, 500);
            }else{
                NextStep();
            }
        }
    };

    Handler delayHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            NextStep();
        }
    };

    private void NextStep(){
        Intent intent;

        if(TextUtils.isEmpty(SharedPreUtil.getTokenID(mContext))){
            SharedPreUtil.setPushToken(mContext, mStrPushToken);
            //서버토큰이 없는 경우
            intent = new Intent(mContext, LoginActivity.class);
            intent.putExtra("uuid", GetDevicesUUID(mContext));
            intent.putExtra("device_name", Build.MODEL);
            Log.d("where", "UUID : "  + GetDevicesUUID(mContext));
            Log.d("where", "DEVICE : "+ Build.MODEL);
            startActivity(intent);
            finish();
        }else{
            if(SharedPreUtil.getAutoLogin(mContext) == false){
                //서버토큰이 있지만 자동로그인이 체크되어 있지 않음
                intent = new Intent(mContext, LoginActivity.class);
                startActivity(intent);
                finish();
            }else{
                //서버토큰이 있지만 자동로그인이 체크
                if(!mStrPushToken.equals(SharedPreUtil.getPushToken(mContext))){
                    SharedPreUtil.setPushToken(mContext, mStrPushToken);
                    new UserUpdateAsyncTask().execute();
                }

                intent = new Intent(mContext, NewMainActivity.class);
                startActivity(intent);
                finish();
            }
        }
    }

    @Override
    protected void onDestroy() {
        mHandler = null;
        super.onDestroy();
    }

    private class UserUpdateAsyncTask extends AsyncTask<Void, Void, Void> {

        public UserUpdateAsyncTask(){

        }

        @Override
        protected Void doInBackground(Void... params) {

            //POST
            Call<ResponseBaseData> data = API_Adapter.getInstance().UserUpdate(
                    SharedPreUtil.getTokenID(IntroActivity.this),
                    "A",
                    Build.MODEL,
                    GetDevicesUUID(mContext),
                    mStrPushToken
            );
            data.enqueue(new Callback<ResponseBaseData>() {
                @Override
                public void onResponse(Call<ResponseBaseData> call, Response<ResponseBaseData> response) {
                    Log.d("where", response.message());
                    Log.d("where", response.body().getStatus_code());
                    Log.d("where", response.body().getStatus_msg());

                }

                @Override
                public void onFailure(Call<ResponseBaseData> call, Throwable t) {
                    Log.d("where", "fail : "+ t.getMessage());
                }
            });
            return  null;
        }
    }


    private String GetDevicesUUID(Context mContext) {


        final TelephonyManager tm = (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);


        final String tmDevice, tmSerial, androidId;

        tmDevice = "" + tm.getDeviceId();
        tmSerial = "" + tm.getSimSerialNumber();
        androidId = "" + android.provider.Settings.Secure.getString(getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);

        UUID deviceUuid = new UUID(androidId.hashCode(), ((long) tmDevice.hashCode() << 32) | tmSerial.hashCode());
        String deviceId = deviceUuid.toString();

        return deviceId;
    }

}
