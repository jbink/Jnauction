package kr.co.hiowner.jnauction.mypage.bid.fragment.top;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
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
public class TopListAdapter extends BaseAdapter {
    LayoutInflater mInflater;
    List<AuctionsData.ResultObject.AuctionsObject> mRowList;
    Context mContext;

    public TopListAdapter(Context context) {
//    public CarListAdapter(Context context, List<CarData> mRowList) {
        super();
        mContext = context;
        this.mInflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mRowList = new ArrayList<AuctionsData.ResultObject.AuctionsObject>();
//        this.mRowList = mRowList;
    }

    public void addItems(List<AuctionsData.ResultObject.AuctionsObject> items){
        mRowList.addAll(items);
        notifyDataSetChanged();
    }
    public void refreshItems(List<AuctionsData.ResultObject.AuctionsObject> items){
        mRowList = new ArrayList<AuctionsData.ResultObject.AuctionsObject>();
        mRowList.addAll(items);
        notifyDataSetChanged();
    }

    public void changeItem(List<AuctionsData.ResultObject.AuctionsObject> items) {
        for (int i=0 ; i<items.size() ; i++){
            mRowList.set(i, items.get(i));
        }
        notifyDataSetChanged();
    }

    public void addOneItem(AuctionsData.ResultObject.AuctionsObject item){
        mRowList.add(item);
    }

    public void removeAllData(){
        mRowList = new ArrayList<AuctionsData.ResultObject.AuctionsObject>();
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        if(mRowList == null)
            return 0;
        return mRowList.size();
    }

    @Override
    public AuctionsData.ResultObject.AuctionsObject getItem(int position) {
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

        final AuctionsData.ResultObject.AuctionsObject data = mRowList.get(position);

        if(convertView == null){

            convertView = mInflater.inflate(R.layout.row_frag_my_bid_top, parent, false);
            holder = new ViewHolder();

            holder.car_image = (ImageView)convertView.findViewById(R.id.row_bid_top_img_thumbnail);
            holder.car_name = (TextView)convertView.findViewById(R.id.row_bid_top_txt_name);
            holder.car_loc_addr = (TextView)convertView.findViewById(R.id.row_bid_top_txt_loc_addr);
            holder.car_year = (TextView)convertView.findViewById(R.id.row_bid_top_txt_year);
            holder.car_kms = (TextView)convertView.findViewById(R.id.row_bid_top_txt_kms);
            holder.car_status = (LinearLayout)convertView.findViewById(R.id.row_bid_top_layout_status);
            holder.car_status_person = (TextView)convertView.findViewById(R.id.row_bid_top_layout_status_person);
            holder.car_price = (TextView)convertView.findViewById(R.id.row_bid_top_txt_price);
            holder.car_price_ = (TextView)convertView.findViewById(R.id.row_bid_top_txt_price_);
            holder.car_rank = (ImageView) convertView.findViewById(R.id.row_bid_top_img_rank);

            convertView.setTag(holder);
        }
        else{
            holder = (ViewHolder)convertView.getTag();
        }

        Glide.with(mContext).load(data.getC_img_1()).into(holder.car_image);
        holder.car_loc_addr.setText(data.getC_loc_addr());
        holder.car_year.setText(data.getC_myear()+"년식");
        holder.car_name.setText(data.getC_brand() +" "+ data.getC_mname());
        holder.car_status_person.setText("["+data.getA_bid_count()+"명 입찰중]");

        holder.car_kms.setText(GlobalValues.getWonFormat(data.getC_kms()) + "km");
        holder.car_price.setText(GlobalValues.getWonFormat(data.getB_price())+"만원");

        try{
            int rank = Integer.parseInt(data.getB_rank());
            if(rank == 1){
                holder.car_rank.setBackgroundResource(R.drawable.ic_gold);
                holder.car_price.setTextColor(ContextCompat.getColor(mContext, R.color.colorPrimary));
                holder.car_price_.setTextColor(ContextCompat.getColor(mContext, R.color.colorPrimary));
            }else if(rank == 2){
                holder.car_rank.setBackgroundResource(R.drawable.ic_silver);
                holder.car_price.setTextColor(ContextCompat.getColor(mContext, R.color.text_color_333333));
                holder.car_price_.setTextColor(ContextCompat.getColor(mContext, R.color.text_color_333333));
            }else if(rank == 3){
                holder.car_rank.setBackgroundResource(R.drawable.ic_bronze);
                holder.car_price.setTextColor(ContextCompat.getColor(mContext, R.color.text_color_333333));
                holder.car_price_.setTextColor(ContextCompat.getColor(mContext, R.color.text_color_333333));
            }else{
                holder.car_rank.setVisibility(View.GONE);
                holder.car_price.setTextColor(ContextCompat.getColor(mContext, R.color.text_color_333333));
                holder.car_price_.setTextColor(ContextCompat.getColor(mContext, R.color.text_color_333333));
            }

            }catch (Exception e){
                Toast.makeText(mContext, "잘못된 rank 값 : " + data.getB_rank(), Toast.LENGTH_SHORT).show();
            }
        return convertView;
    }

    private class ViewHolder{
        ImageView car_image;
        TextView car_name;
        TextView car_loc_addr;
        TextView car_year;
        TextView car_kms;
        LinearLayout car_status;
        TextView car_status_person;
        ImageView car_rank;
        TextView car_price;
        TextView car_price_;
    }
}
