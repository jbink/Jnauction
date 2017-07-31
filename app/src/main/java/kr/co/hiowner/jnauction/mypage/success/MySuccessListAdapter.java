package kr.co.hiowner.jnauction.mypage.success;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import kr.co.hiowner.jnauction.R;
import kr.co.hiowner.jnauction.api.data.AuctionsData;
import kr.co.hiowner.jnauction.util.GlobalValues;

/**
 * Created by user on 2017-06-24.
 */
public class MySuccessListAdapter extends BaseAdapter {
    LayoutInflater mInflater;
    List<AuctionsData.Resultfdg.Auctionsfdg> mRowList;
    Context mContext;

    public MySuccessListAdapter(Context context) {
//    public CarListAdapter(Context context, List<CarData> mRowList) {
        super();
        mContext = context;
        this.mInflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mRowList = new ArrayList<AuctionsData.Resultfdg.Auctionsfdg>();
//        this.mRowList = mRowList;
    }

    public void addItems(List<AuctionsData.Resultfdg.Auctionsfdg> items){
        mRowList.addAll(items);
        notifyDataSetChanged();
    }
    public void refreshItems(List<AuctionsData.Resultfdg.Auctionsfdg> items){
        mRowList = new ArrayList<AuctionsData.Resultfdg.Auctionsfdg>();
        mRowList.addAll(items);
        notifyDataSetChanged();
    }

    public void changeItem(List<AuctionsData.Resultfdg.Auctionsfdg> items) {
        for (int i=0 ; i<items.size() ; i++){
            mRowList.set(i, items.get(i));
        }
        notifyDataSetChanged();
    }

    public void addOneItem(AuctionsData.Resultfdg.Auctionsfdg item){
        mRowList.add(item);
    }

    public void removeAllData(){
        mRowList = new ArrayList<AuctionsData.Resultfdg.Auctionsfdg>();
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        if(mRowList == null)
            return 0;
        return mRowList.size();
    }

    @Override
    public AuctionsData.Resultfdg.Auctionsfdg getItem(int position) {
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

        final AuctionsData.Resultfdg.Auctionsfdg data = mRowList.get(position);

        if(convertView == null){

            convertView = mInflater.inflate(R.layout.row_success_list, parent, false);
            holder = new ViewHolder();

            holder.car_image = (ImageView)convertView.findViewById(R.id.row_success_img_thumbnail);
            holder.car_name = (TextView)convertView.findViewById(R.id.row_success_txt_name);
            holder.car_loc_addr = (TextView)convertView.findViewById(R.id.row_success_txt_loc_addr);
            holder.car_year = (TextView)convertView.findViewById(R.id.row_success_txt_year);
            holder.car_kms = (TextView)convertView.findViewById(R.id.row_success_txt_kms);
            holder.car_date = (TextView)convertView.findViewById(R.id.row_success_txt_date);

            convertView.setTag(holder);
        }
        else{
            holder = (ViewHolder)convertView.getTag();
        }

        Glide.with(mContext).load(data.getC_img_1()).into(holder.car_image);
        holder.car_loc_addr.setText(data.getC_loc_addr());
        holder.car_year.setText(data.getC_myear()+"년식");
        holder.car_name.setText(data.getC_brand() +" "+ data.getC_mname());
        String date = data.getB_upd_date().substring(5, 16);
        holder.car_date.setText("["+date+"]");

        holder.car_kms.setText(GlobalValues.getWonFormat(data.getC_kms()) + "km");

//        try{
//            int status = Integer.parseInt(data.getA_status());
//            if(status >= 100 && status < 200){//입찰대기
//                holder.car_status_200.setVisibility(View.GONE);
//                holder.car_status_300.setVisibility(View.GONE);
//            }else if (status >= 200 && status < 300){//입찰중
//                if ("Y".equals(data.getB_mybid())){
//                    holder.car_status_300.setVisibility(View.VISIBLE);
//                    holder.car_status_200.setVisibility(View.GONE);
//                }else{
//                    holder.car_status_300.setVisibility(View.GONE);
//                    holder.car_status_200.setVisibility(View.VISIBLE);
//                }
//            }
//        }catch (Exception e){
//            Toast.makeText(mContext, "STATUS 오류", Toast.LENGTH_SHORT).show();
//        }




        return convertView;
    }

    private class ViewHolder{
        ImageView car_image;
        TextView car_name;
        TextView car_loc_addr;
        TextView car_year;
        TextView car_kms;
        TextView car_date;
    }
}
