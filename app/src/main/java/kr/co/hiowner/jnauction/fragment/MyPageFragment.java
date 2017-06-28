package kr.co.hiowner.jnauction.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import kr.co.hiowner.jnauction.R;
import kr.co.hiowner.jnauction.user.UserData;

/**
 * Created by user on 2017-06-26.
 */
public class MyPageFragment extends Fragment {

    TextView mTvUserName, mTvUSerPhone, mTvUserLicenseStart;

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
        mTvUSerPhone = (TextView)rootView.findViewById(R.id.main_user_txt_phone);
        mTvUserLicenseStart = (TextView)rootView.findViewById(R.id.main_user_txt_license_start);

        mTvUserName.setText(UserData.getInstance().getName());
        mTvUSerPhone.setText(UserData.getInstance().getPhone());
        mTvUserLicenseStart.setText("서비스 시작일 ["+UserData.getInstance().getLicense_start_date()+"]");

        return rootView;
    }
}
