package kr.co.hiowner.jnauction.mypage.bid.fragment.entire;

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
public class EntireListAdapter extends BaseAdapter {
    LayoutInflater mInflater;
    List<AuctionsData.ResultObject.AuctionsObject> mRowList;
    Context mContext;

    public EntireListAdapter(Context context) {
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

            convertView = mInflater.inflate(R.layout.row_frag_my_bid_entire, parent, false);
            holder = new ViewHolder();

            holder.car_image = (ImageView)convertView.findViewById(R.id.row_bid_entire_img_thumbnail);
            holder.car_name = (TextView)convertView.findViewById(R.id.row_bid_entire_txt_name);
            holder.car_loc_addr = (TextView)convertView.findViewById(R.id.row_bid_entire_txt_loc_addr);
            holder.car_year = (TextView)convertView.findViewById(R.id.row_bid_entire_txt_year);
            holder.car_kms = (TextView)convertView.findViewById(R.id.row_bid_entire_txt_kms);
            holder.car_status = (LinearLayout)convertView.findViewById(R.id.row_frag_my_bid_layout_status);
            holder.car_status_person = (TextView)convertView.findViewById(R.id.row_frag_my_bid_layout_status_person);
            holder.car_price = (TextView)convertView.findViewById(R.id.row_bid_entire_txt_price);
            holder.car_txt_price = (TextView)convertView.findViewById(R.id.row_bid_entire_txt_price_);

            convertView.setTag(holder);
        }
        else{
            holder = (ViewHolder)convertView.getTag();
        }

        Glide.with(mContext).load(data.getC_img_1()).into(holder.car_image);
        holder.car_loc_addr.setText(data.getC_loc_addr());
        holder.car_year.setText(data.getC_myear()+"년식");
        holder.car_name.setText(data.getC_brand() +" "+ data.getC_mname());
        holder.car_status_person.setText("입찰 참여자 "+data.getA_bid_count()+"명");

        holder.car_kms.setText(GlobalValues.getWonFormat(data.getC_kms()) + "km");
        try{
            int status = Integer.parseInt(data.getA_status());
            if(status >= 300 && status < 400){
                holder.car_price.setText(GlobalValues.getWonFormat(data.getA_max_price())+"만원");
                holder.car_txt_price.setText("최고 입찰가 ");
            }else{
//                if("0".equals(data.getA_avg_price())){
//                    holder.car_price.setText("미공개");
//                }else{
//                    holder.car_price.setText(GlobalValues.getWonFormat(data.getA_avg_price()));
//                }
                holder.car_price.setText(GlobalValues.getWonFormat(data.getB_price())+"만원");
                holder.car_txt_price.setText("나의 입찰가 ");
            }
        }catch (Exception e) {
            holder.car_price.setText(data.getB_price());
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
        TextView car_txt_price;
        TextView car_price;

    }
}
