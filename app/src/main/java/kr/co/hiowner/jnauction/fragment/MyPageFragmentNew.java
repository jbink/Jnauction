package kr.co.hiowner.jnauction.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import kr.co.hiowner.jnauction.LoginActivity;
import kr.co.hiowner.jnauction.R;
import kr.co.hiowner.jnauction.api.API_Adapter;
import kr.co.hiowner.jnauction.api.data.UserData;
import kr.co.hiowner.jnauction.mypage.MyPagePopup;
import kr.co.hiowner.jnauction.mypage.bid.MyBidActivity;
import kr.co.hiowner.jnauction.mypage.purchase.MyPurchaseListActivity;
import kr.co.hiowner.jnauction.mypage.success.MySuccessListActivity;
import kr.co.hiowner.jnauction.util.GlobalValues;
import kr.co.hiowner.jnauction.util.SharedPreUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by user on 2017-06-26.
 */
public class MyPageFragmentNew extends Fragment implements View.OnClickListener {

    TextView mTvUserBidSuccessToday, mTvUserBidSuccessEntire;
    TextView mTvUserBidMyToday, mTvUserBidMyEntire;
    TextView mTvUserBidCompleteToday, mTvUserBidCompleteEntire;
    TextView mTvMonthlyPoint, mTvExtraPoint, mTvExtraAddPoint, mTvExtraAddPointBase;
    SeekBar  mSeekBarBonus;
    TextView mTvBonusHelp;



    public MyPageFragmentNew newInstance(){
        MyPageFragmentNew fragment = new MyPageFragmentNew();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.frag_new_new_mypage, container, false);
        mTvUserBidSuccessToday = (TextView)rootView.findViewById(R.id.main_user_txt_bid_success_today);
        mTvUserBidSuccessEntire = (TextView)rootView.findViewById(R.id.main_user_txt_bid_success_entire);
        mTvUserBidMyToday = (TextView)rootView.findViewById(R.id.main_user_txt_bid_my_today);
        mTvUserBidMyEntire = (TextView)rootView.findViewById(R.id.main_user_txt_bid_my_entire);
        mTvUserBidCompleteToday = (TextView)rootView.findViewById(R.id.main_user_txt_bid_complete_today);
        mTvUserBidCompleteEntire = (TextView)rootView.findViewById(R.id.main_user_txt_bid_complete_entire);
        mTvExtraPoint = (TextView)rootView.findViewById(R.id.main_user_txt_extra_point);
        mTvExtraAddPoint = (TextView)rootView.findViewById(R.id.main_user_txt_monthly_addpoint);
        mTvExtraAddPointBase = (TextView)rootView.findViewById(R.id.main_user_txt_monthly_addpoint_base);
        mTvMonthlyPoint = (TextView)rootView.findViewById(R.id.main_user_txt_monthly_point);
        mSeekBarBonus = (SeekBar) rootView.findViewById(R.id.this_month_bonus_seekBar);
        mTvBonusHelp = (TextView)rootView.findViewById(R.id.bonus_help_txt);
        ((ImageButton)rootView.findViewById(R.id.main_user_btn_info)).setOnClickListener(this);
        ((ImageButton)rootView.findViewById(R.id.mypage_bonus_btn_info)).setOnClickListener(this);
        ((TextView)rootView.findViewById(R.id.main_user_btn_logout)).setOnClickListener(this);
        ((RelativeLayout)rootView.findViewById(R.id.main_user_btn_seccess)).setOnClickListener(this);
        ((RelativeLayout)rootView.findViewById(R.id.main_user_btn_purchase)).setOnClickListener(this);
        ((LinearLayout)rootView.findViewById(R.id.main_user_layout_info)).setOnClickListener(this);
        ((RelativeLayout)rootView.findViewById(R.id.main_user_layout_bid)).setOnClickListener(this);
        ((TextView)rootView.findViewById(R.id.main_user_txt_customer_service_phonenum)).setOnClickListener(this);
        mTvBonusHelp.setOnClickListener(this);

        mSeekBarBonus.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                return true;
            }
        });


        출처: http://itpangpang.xyz/212 [ITPangPang]

        new MyPageAsyncTask().execute();

        return rootView;
    }

    public void onClick(View v){
        Intent intent;
        switch (v.getId()){
            case R.id.main_user_layout_info :
            case R.id.main_user_btn_info :
                intent = new Intent(getActivity(), MyPagePopup.class);
                startActivity(intent);
                break;
            case R.id.main_user_btn_seccess :
                intent = new Intent(getActivity(), MySuccessListActivity.class);
                startActivity(intent);
                break;
            case R.id.main_user_layout_bid :
                intent = new Intent(getActivity(), MyBidActivity.class);
                startActivity(intent);
                break;
            case R.id.main_user_btn_purchase :
                intent = new Intent(getActivity(), MyPurchaseListActivity.class);
                startActivity(intent);
                break;
            case R.id.main_user_btn_logout :
                SharedPreUtil.setTokenID(getActivity(), "");
                intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                getActivity().finish();
                break;
            case R.id.main_user_txt_customer_service_phonenum :
                intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:070-4349-6021"));
                startActivity(intent);
                break;
            case R.id.mypage_bonus_btn_info :
            case R.id.bonus_help_txt :
                if(mTvBonusHelp.getVisibility()==View.INVISIBLE)
                    mTvBonusHelp.setVisibility(View.VISIBLE);
                else
                    mTvBonusHelp.setVisibility(View.INVISIBLE);
                break;
        }
    }

    public void refreshData(){
        new MyPageAsyncTask().execute();
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

                    if(!GlobalValues.SERVER_SUCCESS.equals(response.body().getStatus_code())){
                        Toast.makeText(getActivity(), response.body().getStatus_msg(), Toast.LENGTH_SHORT).show();
                        return;
                    }

                    UserData data = response.body();
                    mTvUserBidSuccessToday.setText(""+data.getResult().getCnt_success_today());
                    mTvUserBidSuccessEntire.setText(""+data.getResult().getCnt_success_total());
                    mTvUserBidMyToday.setText(""+data.getResult().getCnt_bid_today());
                    mTvUserBidMyEntire.setText(""+data.getResult().getCnt_bid_total());
                    mTvUserBidCompleteToday.setText(""+data.getResult().getCnt_sale_today());
                    mTvUserBidCompleteEntire.setText(""+data.getResult().getCnt_sale_total());
                    mTvMonthlyPoint.setText(""+data.getResult().getMonthly_points());
                    mTvExtraPoint.setText(""+data.getResult().getExtra_points());
                    mTvExtraAddPoint.setText("/"+data.getResult().getMonthly_add_points());
                    mTvExtraAddPointBase.setText("매달 기본 "+data.getResult().getMonthly_add_points()+"건 제공");

                    mSeekBarBonus.setMax(Integer.parseInt(data.getResult().getMonthly_add_points()));
                    mSeekBarBonus.setProgress(Integer.parseInt(data.getResult().getMonthly_points()));

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
