package kr.co.hiowner.jnauction;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import io.fabric.sdk.android.Fabric;
import com.crashlytics.android.Crashlytics;
import io.fabric.sdk.android.Fabric;
import java.util.ArrayList;
import java.util.UUID;

import jbink.appnapps.mylibrary.PermissionListener;
import jbink.appnapps.mylibrary.jbinkPermission;


/**
 * Created by user on 2017-02-06.
 */
public class PermissionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_permission);

        PackageInfo pi = null;
        try {
            pi = getPackageManager().getPackageInfo(getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
//        Toast.makeText(PermissionActivity.this, "VerSionCode : "+pi.versionCode ,Toast.LENGTH_SHORT).show();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            new jbinkPermission(PermissionActivity.this)
                    .setPermissionListener(permissionlistener)
                    .setRationaleMessage(R.string.permission_msg)
                    .setRationaleConfirmText("확인")
                    .setGotoSettingButtonText("설정")
                    .setDeniedCloseButtonText("취소")
                    .setDeniedMessage("[설정] > [권한] 에서 권한을 허용할 수 있습니다.")
                    .setPermission(
                            Manifest.permission.READ_PHONE_STATE
                    )
                    .check();
        }else{
//            if(chkGpsService() == true){
//                mHandler.sendEmptyMessageDelayed(0, 2000);
//            }
            Intent intent = new Intent(PermissionActivity.this, IntroActivity.class);
            startActivity(intent);
            finish();
        }
    }

    Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Intent intent = new Intent(PermissionActivity.this, IntroActivity.class);
            startActivity(intent);
            finish();
        }
    };

    PermissionListener permissionlistener = new PermissionListener() {
        @Override
        public void onPermissionGranted() {
//            if(chkGpsService() == true){
//                mHandler.sendEmptyMessageDelayed(0, 2000);
//            }
            Intent intent = new Intent(PermissionActivity.this, IntroActivity.class);
            startActivity(intent);
            finish();
        }

        @Override
        public void onPermissionDenied(ArrayList<String> deniedPermissions) {
            Toast.makeText(PermissionActivity.this, "권한을 수락해야 사용이 가능합니다.", Toast.LENGTH_SHORT).show();
            finish();
        }

    };


    //GPS 설정 체크
    private boolean chkGpsService() {

        String gps = android.provider.Settings.Secure.getString(getContentResolver(), android.provider.Settings.Secure.LOCATION_PROVIDERS_ALLOWED);

        if (!(gps.matches(".*gps.*") && gps.matches(".*network.*"))) {

            // GPS OFF 일때 Dialog 표시
            AlertDialog.Builder gsDialog = new AlertDialog.Builder(this);
            gsDialog.setTitle("위치 서비스 설정");
            gsDialog.setMessage("무선 네트워크 사용, GPS 위성 사용을 모두 체크하셔야 정확한 위치 서비스가 가능합니다.\n위치 서비스 기능을 설정하시겠습니까?");
            gsDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    // GPS설정 화면으로 이동
                    Intent intent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    intent.addCategory(Intent.CATEGORY_DEFAULT);
                    startActivityForResult(intent, 3333);
                }
            });
            gsDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
//                    Toast.makeText(PermissionActivity.this, "위치정보를 활성화하셔야 이용이 가능합니다.", Toast.LENGTH_SHORT).show();
//                    chkGpsService();
//                    return;
                    mHandler.sendEmptyMessage(0);
                }
            }).setCancelable(false).create().show();
            return false;

        } else {
            return true;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 3333){
            mHandler.sendEmptyMessage(0);
        }
    }
}
