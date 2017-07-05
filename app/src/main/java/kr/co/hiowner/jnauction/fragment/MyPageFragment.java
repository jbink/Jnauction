package kr.co.hiowner.jnauction.fragment;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.HashMap;

import kr.co.hiowner.jnauction.R;
import kr.co.hiowner.jnauction.api.API_Adapter;
import kr.co.hiowner.jnauction.api.data.AuctionsData;
import kr.co.hiowner.jnauction.mypage.MyPagePopup;
import kr.co.hiowner.jnauction.mypage.bid.MyBidActivity;
import kr.co.hiowner.jnauction.mypage.purchase.MyPurchaseListActivity;
import kr.co.hiowner.jnauction.mypage.success.MySuccessListActivity;
import kr.co.hiowner.jnauction.util.SharedPreUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import kr.co.hiowner.jnauction.api.data.UserData;
/**
 * Created by user on 2017-06-26.
 */
public class MyPageFragment extends Fragment implements View.OnClickListener {

    TextView mTvUserName, mTvUserPhone, mTvUserLicenseStart;
    TextView mTvUserBidSuccessToday, mTvUserBidSuccessEntire;
    TextView mTvUserBidMyToday, mTvUserBidMyEntire;
    TextView mTvUserBidCompleteToday, mTvUserBidCompleteEntire;
    TextView mTvMonthlyPoint, mTvExtraPoint;

    public MyPageFragment newInstance(){
        MyPageFragment fragment = new MyPageFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.frag_new_mypage, container, false);
        mTvUserName = (TextView)rootView.findViewById(R.id.main_user_txt_name);
        mTvUserPhone = (TextView)rootView.findViewById(R.id.main_user_txt_phone);
        mTvUserLicenseStart = (TextView)rootView.findViewById(R.id.main_user_txt_license_start);
        mTvUserBidSuccessToday = (TextView)rootView.findViewById(R.id.main_user_txt_bid_success_today);
        mTvUserBidSuccessEntire = (TextView)rootView.findViewById(R.id.main_user_txt_bid_success_entire);
        mTvUserBidMyToday = (TextView)rootView.findViewById(R.id.main_user_txt_bid_my_today);
        mTvUserBidMyEntire = (TextView)rootView.findViewById(R.id.main_user_txt_bid_my_entire);
        mTvUserBidCompleteToday = (TextView)rootView.findViewById(R.id.main_user_txt_bid_complete_today);
        mTvUserBidCompleteEntire = (TextView)rootView.findViewById(R.id.main_user_txt_bid_complete_entire);
        mTvExtraPoint = (TextView)rootView.findViewById(R.id.main_user_txt_extra_point);
        mTvMonthlyPoint = (TextView)rootView.findViewById(R.id.main_user_txt_monthly_point);
        ((ImageButton)rootView.findViewById(R.id.main_user_btn_bid_info)).setOnClickListener(this);
        ((RelativeLayout)rootView.findViewById(R.id.main_user_btn_seccess)).setOnClickListener(this);
        ((RelativeLayout)rootView.findViewById(R.id.main_user_btn_purchase)).setOnClickListener(this);
        ((LinearLayout)rootView.findViewById(R.id.main_user_layout_bid_info)).setOnClickListener(this);

//        mTvUserName.setText(UserData.getInstance().getName());
//        mTvUSerPhone.setText(UserData.getInstance().getPhone());
//        mTvUserLicenseStart.setText("서비스 시작일 ["+UserData.getInstance().getLicense_start_date()+"]");

        new MyPageAsyncTask().execute();

        return rootView;
    }

    public void onClick(View v){
        Intent intent;
        switch (v.getId()){
            case R.id.main_user_layout_bid_info :
            case R.id.main_user_btn_bid_info :
                intent = new Intent(getActivity(), MyPagePopup.class);
                startActivity(intent);
                break;
            case R.id.main_user_btn_seccess :
                intent = new Intent(getActivity(), MySuccessListActivity.class);
                startActivity(intent);
                break;
            case R.id.main_user_btn_bid :
                Toast.makeText(getActivity(), "작업중입니다.", Toast.LENGTH_SHORT).show();
//                intent = new Intent(getActivity(), MyBidActivity.class);
//                startActivity(intent);
                break;
            case R.id.main_user_btn_purchase :
                intent = new Intent(getActivity(), MyPurchaseListActivity.class);
                startActivity(intent);
                break;
        }
    }



    //전체상품관련 AsyncTask
    private class MyPageAsyncTask extends AsyncTask<Void, Void, Void> {


        public MyPageAsyncTask() {
        }

        @Override
        protected Void doInBackground(Void... voids) {

            Call<UserData> auctions = API_Adapter.getInstance().User(
                    SharedPreUtil.getTokenID(getActivity())
            );
            auctions.enqueue(new Callback<UserData>() {
                @Override
                public void onResponse(Call<UserData> call, Response<UserData> response) {

                    if(!"200".equals(response.body().getStatus_code())){
                        Toast.makeText(getActivity(), response.body().getStatus_msg(), Toast.LENGTH_SHORT).show();
                        return;
                    }

                    UserData data = response.body();
                    mTvUserName.setText(data.getResult().getName());
                    mTvUserPhone.setText(data.getResult().getPhone());
                    mTvUserLicenseStart.setText("서비스 시작일 ["+data.getResult().getLicense_start_date()+"]");
                    mTvUserBidSuccessToday.setText(""+data.getResult().getCnt_success_today());
                    mTvUserBidSuccessEntire.setText(""+data.getResult().getCnt_success_total());
                    mTvUserBidMyToday.setText(""+data.getResult().getCnt_bid_today());
                    mTvUserBidMyEntire.setText(""+data.getResult().getCnt_bid_total());
                    mTvUserBidCompleteToday.setText(""+data.getResult().getCnt_sale_today());
                    mTvUserBidCompleteEntire.setText(""+data.getResult().getCnt_sale_total());
                    mTvMonthlyPoint.setText(""+data.getResult().getMonthly_points());
                    mTvExtraPoint.setText(""+data.getResult().getExtra_points());

//        mTvUserName.setText(UserData.getInstance().getName());
//        mTvUSerPhone.setText(UserData.getInstance().getPhone());
//        mTvUserLicenseStart.setText("서비스 시작일 ["+UserData.getInstance().getLicense_start_date()+"]");
                }

                @Override
                public void onFailure(Call<UserData> call, Throwable t) {

                }
            });
            return null;
        }
    }
}
