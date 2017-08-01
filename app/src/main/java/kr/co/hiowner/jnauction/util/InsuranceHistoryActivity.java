package kr.co.hiowner.jnauction.util;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;

import kr.co.hiowner.jnauction.R;

/**
 * Created by user on 2017-07-31.
 */
public class InsuranceHistoryActivity extends AppCompatActivity{
    Context mContext;

    ProgressBar mProgress;
    WebView mWebWiew;

    String mStrStarturl = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insurance_history);
        mContext = InsuranceHistoryActivity.this;


        mStrStarturl = getIntent().getStringExtra("url");

        mProgress = (ProgressBar)findViewById(R.id.progress);
        mWebWiew = (WebView)findViewById(R.id.insurance_history_webview);


        Context mContext = getApplicationContext();
//        LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(LAYOUT_INFLATER_SERVICE);

        mWebWiew.setWebViewClient(new myWebClient());
        mWebWiew.setWebChromeClient(new ChromeClient());
        mWebWiew.getSettings().setBuiltInZoomControls(true);
        mWebWiew.getSettings().setSupportZoom(true);
        mWebWiew.getSettings().setDisplayZoomControls(false);
        mWebWiew.getSettings().setLoadWithOverviewMode(true);
        mWebWiew.getSettings().setUseWideViewPort(true);
        mWebWiew.getSettings().setPluginState(WebSettings.PluginState.ON);
        mWebWiew.getSettings().setSupportMultipleWindows(true);
        mWebWiew.getSettings().setJavaScriptEnabled(true);
        mWebWiew.getSettings().setDomStorageEnabled(true);;


//        String useragent = mWebWiew.getSettings().getUserAgentString();
////        mWebWiew.getSettings().setUserAgentString(useragent+"/sapzil/app");
//
//
//        mWebWiew.getSettings().setAllowFileAccess(true);
//        mWebWiew.getSettings().setLoadWithOverviewMode(true);
//
//
//        if (18 < Build.VERSION.SDK_INT ) {
//            //18 = JellyBean MR2, KITKAT=19
//            mWebWiew.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
//        }
//        if (Build.VERSION.SDK_INT >= 19) {
//            mWebWiew.setLayerType(View.LAYER_TYPE_HARDWARE, null);
//        }
//
//        if(android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
//            mWebWiew.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
//            CookieManager cookieManager = CookieManager.getInstance();
//            cookieManager.setAcceptCookie(true);
//            cookieManager.setAcceptThirdPartyCookies(mWebWiew, true);
//        }

//        mWebWiew.postUrl(starturl, EncodingUtils.getBytes(postData, "UTF-8"));
//        mWebWiew.postUrl("http://appnapps.com/jbink/post_test.php", EncodingUtils.getBytes(postData, "UTF-8"));
//        mWebWiew.loadUrl("https://dev.smart-one.co.kr/smartpass_mngr/m/tAuthApp");

        mWebWiew.loadUrl(mStrStarturl);
    }

    public void onClick(View v){
        switch (v.getId()){
            case R.id.insurance_history_btn_back :
            case R.id.insurance_history_layout_back :
                finish();
                break;
        }
    }



    long pressedTime = 0;
    @Override
    public void onBackPressed() {//웹에서 뒤로가기 처리
        if(mWebWiew == null){
            super.onBackPressed();
        }
        if (mWebWiew.canGoBack()) {
            mWebWiew.goBack();
        } else {
            finish();
        }
    }
    Handler mHandler = new Handler();



    public class myWebClient extends WebViewClient {
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            mProgress.setVisibility(view.VISIBLE);
        }

        public void onPageFinished(WebView view, String url) {
            mProgress.setVisibility(view.GONE);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            Log.d("where", "url ->  " + url);
            if (url != null && url.contains("http://dx.doi.org/")) {
            }else {//
                return super.shouldOverrideUrlLoading(view, url);
            }
            return true;
        }
    }

    public final class ChromeClient extends WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            mProgress.setProgress(newProgress);
            if(newProgress == 100){
                mProgress.setVisibility(View.GONE);
            }
        }
    }
}
