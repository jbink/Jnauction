package kr.co.hiowner.jnauction.car;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import kr.co.hiowner.jnauction.R;
import kr.co.hiowner.jnauction.util.GlobalValues;

/**
 * Created by user on 2017-06-24.
 */
public class CarListAdapter extends BaseAdapter {
    LayoutInflater mInflater;
    List<CarData> mRowList;
    Context mContext;

    public CarListAdapter(Context context) {
//    public CarListAdapter(Context context, List<CarData> mRowList) {
        super();
        mContext = context;
        this.mInflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mRowList = new ArrayList<CarData>();
//        this.mRowList = mRowList;
    }

    public void addItems(List<CarData> items){
        mRowList.addAll(items);
    }

    public void addOneItem(CarData item){
        mRowList.add(item);
    }

    public void removeAllData(){
        mRowList = new ArrayList<CarData>();
    }

    @Override
    public int getCount() {
        if(mRowList == null)
            return 0;
        return mRowList.size();
    }

    @Override
    public CarData getItem(int position) {
        if(position >= 0 && position < mRowList.size())
            return mRowList.get(position);
        return null;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;

        final CarData data = mRowList.get(position);

        if(convertView == null){

            convertView = mInflater.inflate(R.layout.row_main_buy_list, parent, false);
            holder = new ViewHolder();

            holder.car_image = (ImageView)convertView.findViewById(R.id.row_main_img_thumbnail);
            holder.car_name = (TextView)convertView.findViewById(R.id.row_main_txt_name);
            holder.car_loc_addr = (TextView)convertView.findViewById(R.id.row_main_txt_name);
            holder.car_year = (TextView)convertView.findViewById(R.id.row_main_txt_year);
            holder.car_kms = (TextView)convertView.findViewById(R.id.row_main_txt_kms);
            holder.car_status_200 = (LinearLayout)convertView.findViewById(R.id.row_main_layout_status_200);
            holder.car_status_200_person = (TextView)convertView.findViewById(R.id.row_main_layout_status_200_person);
            holder.car_status_300 = (LinearLayout)convertView.findViewById(R.id.row_main_layout_status_300);
            holder.car_status_300_person = (TextView)convertView.findViewById(R.id.row_main_layout_status_300_person);

            convertView.setTag(holder);
        }
        else{
            holder = (ViewHolder)convertView.getTag();
        }

        Glide.with(mContext).load(data.getC_img_1()).into(holder.car_image);
        holder.car_loc_addr.setText(data.getC_loc_addr());
        holder.car_year.setText(data.getC_myear()+"년식");
        holder.car_name.setText(data.getC_brand() +" "+ data.getC_mname());

        DecimalFormat df = new DecimalFormat("###,###");
        holder.car_kms.setText(df.format(Double.parseDouble(data.getC_kms())) + "km");

        try{
            int status = Integer.parseInt(data.getA_status());
            if(status >= 100 && status < 200){//입찰대기
                holder.car_status_200.setVisibility(View.GONE);
                holder.car_status_300.setVisibility(View.GONE);
            }else if (status >= 200 && status < 300){//입찰중
                holder.car_status_200.setVisibility(View.VISIBLE);
                holder.car_status_300.setVisibility(View.GONE);
            }else if (status >= 300 && status < 400){//입찰완료
                holder.car_status_200.setVisibility(View.GONE);
                holder.car_status_300.setVisibility(View.VISIBLE);
            }else if (status >= 400 && status < 500){//매입완료
                holder.car_status_200.setVisibility(View.VISIBLE);
                holder.car_status_300.setVisibility(View.VISIBLE);
            }
        }catch (Exception e){
            Toast.makeText(mContext, "STATUS 오류", Toast.LENGTH_SHORT).show();
        }




        return convertView;
    }

    private class ViewHolder{
        ImageView car_image;
        TextView car_name;
        TextView car_loc_addr;
        TextView car_year;
        TextView car_kms;
        LinearLayout car_status_300;
        TextView car_status_300_person;
        LinearLayout car_status_200;
        TextView car_status_200_person;

    }
}
