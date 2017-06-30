package kr.co.hiowner.jnauction.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import jbink.appnapps.okhttplibrary.ApiCall;
import kr.co.hiowner.jnauction.CarDetailActivity;
import kr.co.hiowner.jnauction.R;
import kr.co.hiowner.jnauction.car.CarData;
import kr.co.hiowner.jnauction.car.CarListAdapter;
import kr.co.hiowner.jnauction.user.UserData;
import kr.co.hiowner.jnauction.util.SharedPreUtil;
import okhttp3.OkHttpClient;

/**
 * Created by user on 2017-06-26.
 */
public class AutionFragment extends Fragment {
    private final int LISTVIEW_CUR_FULL = 1100;
    private final int LISTVIEW_CUR_MY = 2200;
//    Context mContext;

    Spinner mSpinSort;



    TextView mTvUserName, mTvUSerPhone, mTvUserLicenseStart;



    //차량 List
    List<CarData> mDataCar_Full;
    List<CarData> mDataCar_My;
    ListView mListCar;
    CarListAdapter mAdapterCar;

    //전체차량, 내입찰차량
    CheckBox mChkMyAuctions;
    private int mCurListViewPage;

    //list의 Index 관리
    TextView mTvTotalCount_Full, mTvTotalCount_My ;
    private int mIntOffSet_Full = 1;
    private int mIntLimit_Full = 3;
    private int mIntTotal_Full = 0;
    private int mIntOffSet_My = 1;
    private int mIntLimit_My = 3;
    private int mIntTotal_My = 0;

    //화면에 리스트의 마지막 아이템이 보여지는지 체크
    boolean lastItemVisibleFlag = false;

    public AutionFragment newInstance() {
        AutionFragment fragment = new AutionFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        mContext = MainActivity.this;
//
//        mSpinSort = (Spinner)findViewById(R.id.main_spinner_sort);
//
//        mLayoutMain_1 = (LinearLayout)findViewById(R.id.main_layout_lin_1);
//        mLayoutMain_2 = (ScrollView)findViewById(R.id.main_layout_scr_2);
//        mTvMainTxt_1 = (TextView)findViewById(R.id.main_btn_txt_1);
//        mTvMainTxt_2 = (TextView)findViewById(R.id.main_btn_txt_2);
//        mTvTotalCount_Full = (TextView)findViewById(R.id.main_car_total_count_full);
//        mTvTotalCount_My = (TextView)findViewById(R.id.main_car_total_count_my);
//        mTvUserName = (TextView)findViewById(R.id.main_user_txt_name);
//        mTvUSerPhone = (TextView)findViewById(R.id.main_user_txt_phone);
//        mTvUserLicenseStart = (TextView)findViewById(R.id.main_user_txt_license_start);
//        mTvMainUnderline_1 = (ImageView)findViewById(R.id.main_btn_underline_1);
//        mTvMainUnderline_2 = (ImageView)findViewById(R.id.main_btn_underline_2);
//        mListCar = (ListView)findViewById(R.id.main_car_listview);
//        mChkMyAuctions = (CheckBox)findViewById(R.id.main_chk_my_product);
//
//        mCurListViewPage = LISTVIEW_CUR_FULL;
//
//        mTvUserName.setText(UserData.getInstance().getName());
//        mTvUSerPhone.setText(UserData.getInstance().getPhone());
//        mTvUserLicenseStart.setText("서비스 시작일 ["+UserData.getInstance().getLicense_start_date()+"]");
//
//        mListCar.setOnScrollListener(new AbsListView.OnScrollListener() {
//            @Override
//            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
//                //현재 화면에 보이는 첫번째 리스트 아이템의 번호(firstVisibleItem) + 현재 화면에 보이는 리스트 아이템의 갯수(visibleItemCount)가 리스트 전체의 갯수(totalItemCount) -1 보다 크거나 같을때
//                lastItemVisibleFlag = (totalItemCount > 0) && (firstVisibleItem + visibleItemCount >= totalItemCount);
//            }
//            @Override
//            public void onScrollStateChanged(AbsListView view, int scrollState) {
//                //OnScrollListener.SCROLL_STATE_IDLE은 스크롤이 이동하다가 멈추었을때 발생되는 스크롤 상태입니다.
//                //즉 스크롤이 바닦에 닿아 멈춘 상태에 처리를 하겠다는 뜻
//                if(scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE && lastItemVisibleFlag) {
//                    //TODO 화면이 바닦에 닿을때 처리
////                    if(mIntTotal > mIntOffSet) {
//                    Log.d("where", "받아와");
//                    mIntOffSet_Full += mIntLimit_Full;
//
//                    String url = "http://joongna.hiowner.co.kr/api/auctions"
//                            + "?token=" + SharedPreUtil.getTokenID(mContext)
//                            + "&mybid=" + "N"
//                            + "&order=reg_desc"
//                            + "&limit=" + mIntLimit_Full
//                            + "&offset=" + mIntOffSet_Full
//                            + "&min_status=100"
//                            + "&max_status=499";
//
//                    new MainAsyncTask().execute(url);
////                    }
////                    else{
////                        Log.d("where", "그만그만");
////                    }
//                }
//            }
//        });
//
//
//        mSpinSort.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                Toast.makeText(mContext, ""+adapterView.getItemAtPosition(i), Toast.LENGTH_SHORT).show();
//            }
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//            }
//        });
//
//        /*
//        유저 토큰 값 (32자리)
//        'Y': 내가입찰한 차량만, 'N': 전체 차량
//        limit (페이지네이션 때 사용)
//        offset (페이지네이션 때 사용)
//        reg_desc : 최근등록 내림차순
//        bid_desc: 입찰수 내림차순
//        min_status   100-199		입찰대기
//        max_status   200-299		입찰중
//                        300-399		입찰완료
//                        400-499		매입완료
//        */
////        String url = "http://joongna.hiowner.co.kr/api/auctions?token="+"2edd9b04b8aa6288b867dcc253e16f06"+"&mybid=N&order=bid_desc&limit=100&offset=1&min_status=100&max_status=299";
//        String url = "http://joongna.hiowner.co.kr/api/auctions"
//                +"?token="+SharedPreUtil.getTokenID(mContext)
//                +"&mybid="+"N"
//                +"&order=reg_desc"
//                +"&limit=" + mIntLimit_Full
//                +"&offset=" + mIntOffSet_Full
//                +"&min_status=100"
//                +"&max_status=499";
//
//        new MainAsyncTask().execute(url);
//
//        mAdapterCar = new CarListAdapter(mContext);
//        mListCar.setOnItemClickListener(mItemClickListener);
//        mListCar.setAdapter(mAdapterCar);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.frag_main_aution, container, false);


        mSpinSort = (Spinner)rootView.findViewById(R.id.main_spinner_sort);

//        mLayoutMain_1 = (LinearLayout)rootView.findViewById(R.id.main_layout_lin_1);
//        mLayoutMain_2 = (ScrollView)rootView.findViewById(R.id.main_layout_scr_2);
//        mTvMainTxt_1 = (TextView)rootView.findViewById(R.id.main_btn_txt_1);
//        mTvMainTxt_2 = (TextView)rootView.findViewById(R.id.main_btn_txt_2);
        mTvTotalCount_Full = (TextView)rootView.findViewById(R.id.main_car_total_count_full);
        mTvTotalCount_My = (TextView)rootView.findViewById(R.id.main_car_total_count_my);
//        mTvUserName = (TextView)rootView.findViewById(R.id.main_user_txt_name);
//        mTvUSerPhone = (TextView)rootView.findViewById(R.id.main_user_txt_phone);
//        mTvUserLicenseStart = (TextView)rootView.findViewById(R.id.main_user_txt_license_start);
//        mTvMainUnderline_1 = (ImageView)rootView.findViewById(R.id.main_btn_underline_1);
//        mTvMainUnderline_2 = (ImageView)rootView.findViewById(R.id.main_btn_underline_2);
        mListCar = (ListView)rootView.findViewById(R.id.main_car_listview);
        mChkMyAuctions = (CheckBox)rootView.findViewById(R.id.main_chk_my_product);

        mCurListViewPage = LISTVIEW_CUR_FULL;

//        mTvUserName.setText(UserData.getInstance().getName());
//        mTvUSerPhone.setText(UserData.getInstance().getPhone());
//        mTvUserLicenseStart.setText("서비스 시작일 ["+UserData.getInstance().getLicense_start_date()+"]");



        mListCar.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                //현재 화면에 보이는 첫번째 리스트 아이템의 번호(firstVisibleItem) + 현재 화면에 보이는 리스트 아이템의 갯수(visibleItemCount)가 리스트 전체의 갯수(totalItemCount) -1 보다 크거나 같을때
                lastItemVisibleFlag = (totalItemCount > 0) && (firstVisibleItem + visibleItemCount >= totalItemCount);
            }
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                //OnScrollListener.SCROLL_STATE_IDLE은 스크롤이 이동하다가 멈추었을때 발생되는 스크롤 상태입니다.
                //즉 스크롤이 바닦에 닿아 멈춘 상태에 처리를 하겠다는 뜻
                if(scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE && lastItemVisibleFlag) {
                    //TODO 화면이 바닦에 닿을때 처리
//                    if(mIntTotal > mIntOffSet) {
                    mIntOffSet_Full += mIntLimit_Full;

                    String url = "http://joongna.hiowner.co.kr/api/auctions"
                            + "?token=" + SharedPreUtil.getTokenID(getActivity())
                            + "&mybid=" + "N"
                            + "&order=reg_desc"
                            + "&limit=" + mIntLimit_Full
                            + "&offset=" + mIntOffSet_Full
                            + "&min_status=100"
                            + "&max_status=499";

                    new MainAsyncTask().execute(url);
//                    }
//                    else{
//                        Log.d("where", "그만그만");
//                    }
                }
            }
        });


        mSpinSort.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getActivity(), ""+adapterView.getItemAtPosition(i), Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

//        String url = "http://joongna.hiowner.co.kr/api/auctions?token="+"2edd9b04b8aa6288b867dcc253e16f06"+"&mybid=N&order=bid_desc&limit=100&offset=1&min_status=100&max_status=299";
        String url = "http://joongna.hiowner.co.kr/api/auctions"
                +"?token="+SharedPreUtil.getTokenID(getActivity())
                +"&mybid="+"N"
                +"&order=reg_desc"
                +"&limit=" + mIntLimit_Full
                +"&offset=" + mIntOffSet_Full
                +"&min_status=100"
                +"&max_status=499";

        new MainAsyncTask().execute(url);

        mAdapterCar = new CarListAdapter(getActivity());
        mListCar.setOnItemClickListener(mItemClickListener);
        mListCar.setAdapter(mAdapterCar);

        return rootView;
    }

    AdapterView.OnItemClickListener mItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            CarData data = (CarData) adapterView.getAdapter().getItem(i);
            Intent intent = new Intent(getActivity(), CarDetailActivity.class);
            intent.putExtra("car_data", data);
            startActivity(intent);
        }
    };



    public void onClick(View v){
        switch (v.getId()){
            case R.id.main_chk_my_product :
                if (mChkMyAuctions.isChecked() == true){
//                    mCurListViewPage = LISTVIEW_CUR_MY;
//                    String url = "http://joongna.hiowner.co.kr/api/auctions"
//                            +"?token="+SharedPreUtil.getTokenID(getActivity())
//                            +"&mybid="+"Y"
//                            +"&order=reg_desc"
//                            +"&limit=" + mIntLimit_My
//                            +"&offset=" + mIntOffSet_My
//                            +"&min_status=100"
//                            +"&max_status=499";
//
//                    new MainAsyncTask().execute(url);
                }else{
//                    mCurListViewPage = LISTVIEW_CUR_FULL;
                }
                break;
        }
    }




    ///////////////////////////////////////////////////////////////////////////////////////////
    OkHttpClient client = new OkHttpClient();
    private class MainAsyncTask extends AsyncTask<String, Void, String> {
        String response = null;

        @Override
        protected String doInBackground(String... params) {
            String url = params[0];
//            if(params[0].equals(AUTH_URL)){
//                url = GlobalValues.SERVER + params[0]+"?phone="+params[1];
////                fb = new FormBody.Builder()
////                        .add("phone", params[1])
////                        .build();
//            }else if (params[0].equals(LOGIN_URL)){
//                url = GlobalValues.SERVER + params[0]+"?phone="+params[1]+"&phone_cert="+ params[2];
//                fb = new FormBody.Builder()
//                        .add("phone", params[1])
//                        .add("phone_cert", params[2])
//                        .build();
//            }else{
//                return "ERROR!!";
//            }
            try {
                response = ApiCall.GET(client, url);
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

//            Log.d("where", ""+result);
            if(TextUtils.isEmpty(result)){
                return;
            }

            if(jsonParsing(result) == true){
                if(mCurListViewPage == LISTVIEW_CUR_FULL) {
//                    mAdapterCar.addItems(mDataCar_Full);
//                    mAdapterCar.notifyDataSetChanged();
                }else if(mCurListViewPage == LISTVIEW_CUR_MY) {
//                    mAdapterCar.addItems(mDataCar_My);
//                    mAdapterCar.notifyDataSetChanged();
                }

            }

        }

        private boolean jsonParsing(String result){
            String status_code, status_msg, count = null;
            String auction_idx, u_name, u_phone, c_brand,
                    c_mname, c_myear, c_num, c_kms,
                    c_trans, c_history, c_fuel, c_opt_bbox,
                    c_opt_rcam, c_opt_rsensor, c_opt_as, c_opt_navi,
                    c_opt_4wd, c_opt_sroof, c_opt_skey, c_opt_heat,
                    c_opt_fan, c_opt_alu, c_loc_addr, c_img_1,
                    c_img_2, c_img_3, c_img_4, c_memo,
                    a_status, a_final_price, a_final_user_idx = null;
            try {
                Log.d("where", "result-> "+result);
                if(TextUtils.isEmpty(result)){
                    Log.d("where", "서버 결과값 없음!");
                    return false;
                }
                JSONObject object = new JSONObject(result);
                status_code = object.getString("status_code");
                status_msg = object.getString("status_msg");

                if(!"200".equals(status_code)){
                    return false;
                }
//                mTvTotalCount.setText("매물수 "+ count + "대");
                mDataCar_Full = new ArrayList<CarData>();
//                mDataCar_My = new ArrayList<CarData>();
                JSONObject jsonObject_result = object.getJSONObject("result");
                count = jsonObject_result.getString("count");
                DecimalFormat df = new DecimalFormat("###,###");
//                mIntTotal_Full = Integer.parseInt(count);
//                mTvTotalCount.setText("매물수 "+ df.format(mIntTotal_Full) + "대");
                JSONArray jsonArray_auctions = jsonObject_result.getJSONArray("auctions");
                JSONObject jsonObject_auctions;
                for (int i = 0; i <jsonArray_auctions.length() ; i++) {
                    jsonObject_auctions = jsonArray_auctions.getJSONObject(i);
                    auction_idx = jsonObject_auctions.optString("auction_idx", null);
                    u_name = jsonObject_auctions.optString("u_name", null);
                    u_phone = jsonObject_auctions.optString("u_phone", null);
                    c_brand = jsonObject_auctions.optString("c_brand", null);
                    c_mname = jsonObject_auctions.optString("c_mname", null);
                    c_myear = jsonObject_auctions.optString("c_myear", null);
                    c_num = jsonObject_auctions.optString("c_num", null);
                    c_kms = jsonObject_auctions.optString("c_kms", null);
                    c_trans = jsonObject_auctions.optString("c_trans", null);
                    c_history = jsonObject_auctions.optString("c_history", null);
                    c_fuel = jsonObject_auctions.optString("c_fuel", null);
                    c_opt_bbox = jsonObject_auctions.optString("c_opt_bbox", null);
                    c_opt_rcam = jsonObject_auctions.optString("c_opt_rcam", null);
                    c_opt_rsensor = jsonObject_auctions.optString("c_opt_rsensor", null);
                    c_opt_as = jsonObject_auctions.optString("c_opt_as", null);
                    c_opt_navi = jsonObject_auctions.optString("c_opt_navi", null);
                    c_opt_4wd = jsonObject_auctions.optString("c_opt_4wd", null);
                    c_opt_sroof = jsonObject_auctions.optString("c_opt_sroof", null);
                    c_opt_skey = jsonObject_auctions.optString("c_opt_skey", null);
                    c_opt_heat = jsonObject_auctions.optString("c_opt_heat", null);
                    c_opt_fan = jsonObject_auctions.optString("c_opt_fan", null);
                    c_opt_alu = jsonObject_auctions.optString("c_opt_alu", null);
                    c_loc_addr = jsonObject_auctions.optString("c_loc_addr", null);
                    c_img_1 = jsonObject_auctions.optString("c_img_1", null);
                    c_img_2 = jsonObject_auctions.optString("c_img_2", null);
                    c_img_3 = jsonObject_auctions.optString("c_img_3", null);
                    c_img_4 = jsonObject_auctions.optString("c_img_4", null);
                    c_memo = jsonObject_auctions.optString("c_memo", null);
                    a_status = jsonObject_auctions.optString("a_status", null);
                    a_final_price = jsonObject_auctions.optString("a_final_price", null);
                    a_final_user_idx = jsonObject_auctions.optString("a_final_user_idx", null);

                    if(mCurListViewPage == LISTVIEW_CUR_FULL) {
//                        mDataCar_Full = new ArrayList<CarData>();
                        mIntTotal_Full = Integer.parseInt(count);
                        mTvTotalCount_Full.setText("매물수 "+ df.format(mIntTotal_Full) + "대");

                        mDataCar_Full.add(new CarData(auction_idx, u_name, u_phone, c_brand,
                                c_mname, c_myear, c_num, c_kms,
                                c_trans, c_history, c_fuel, c_opt_bbox,
                                c_opt_rcam, c_opt_rsensor, c_opt_as, c_opt_navi,
                                c_opt_4wd, c_opt_sroof, c_opt_skey, c_opt_heat,
                                c_opt_fan, c_opt_alu, c_loc_addr, c_img_1,
                                c_img_2, c_img_3, c_img_4, c_memo,
                                a_status, a_final_price, a_final_user_idx));
                    }
                    else if(mCurListViewPage == LISTVIEW_CUR_MY){
//                        mDataCar_My = new ArrayList<CarData>();
                        mIntTotal_My = Integer.parseInt(count);
                        mTvTotalCount_My.setText(df.format(mIntTotal_Full) + "대");

                        mDataCar_My.add(new CarData(auction_idx, u_name, u_phone, c_brand,
                                c_mname, c_myear, c_num, c_kms,
                                c_trans, c_history, c_fuel, c_opt_bbox,
                                c_opt_rcam, c_opt_rsensor, c_opt_as, c_opt_navi,
                                c_opt_4wd, c_opt_sroof, c_opt_skey, c_opt_heat,
                                c_opt_fan, c_opt_alu, c_loc_addr, c_img_1,
                                c_img_2, c_img_3, c_img_4, c_memo,
                                a_status, a_final_price, a_final_user_idx));
                    }

//                    mDataCar_Full.add(new CarData(auction_idx, u_name, u_phone, c_brand,
//                            c_mname, c_myear, c_num, c_kms,
//                            c_trans, c_history, c_fuel, c_opt_bbox,
//                            c_opt_rcam, c_opt_rsensor, c_opt_as, c_opt_navi,
//                            c_opt_4wd, c_opt_sroof, c_opt_skey, c_opt_heat,
//                            c_opt_fan, c_opt_alu, c_loc_addr, c_img_1,
//                            c_img_2, c_img_3, c_img_4, c_memo,
//                            a_status, a_final_price, a_final_user_idx));
                }

            } catch (JSONException e) {
                e.printStackTrace();
                Log.e("where","EXCEPTION : "+e.getMessage());
                return false;
            }
            return true;
        }
    }
}
