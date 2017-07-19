package kr.co.hiowner.jnauction.car;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by user on 2017-06-24.
 */
public class CarData implements Parcelable {
    /**
     * auction_idx : 4
     * u_name : 김**
     * u_phone : 010********
     * c_brand : 기아
     * c_mname : 포르테 하이브리드 기본
     * c_myear : 2010
     * c_num : 65서6444
     * c_kms : 106661
     * c_trans : A
     * c_history : N
     * c_fuel : H
     * c_opt_bbox : N
     * c_opt_rcam : Y
     * c_opt_rsensor : Y
     * c_opt_as : N
     * c_opt_navi : Y
     * c_opt_4wd : N
     * c_opt_sroof : N
     * c_opt_skey : N
     * c_opt_heat : N
     * c_opt_fan : N
     * c_opt_alu : N
     * c_loc_addr : 인천시 연수
     * c_img_1 : http://image.theowner.co.kr/joongna/mycarsale/00/0009/905_img_1_7083.jpg
     * c_img_2 : http://image.theowner.co.kr/joongna/mycarsale/00/0009/905_img_2_8778.jpg
     * c_img_3 : http://image.theowner.co.kr/joongna/mycarsale/00/0009/905_img_3_5039.jpg
     * c_img_4 : http://image.theowner.co.kr/joongna/mycarsale/00/0009/905_img_4_8460.jpg
     * c_memo : 범퍼 앞쪽 깨짐 있습니다.
     * <p/>
     * a_status : 100
     * a_final_price : 0
     * a_final_user_idx : 0
     */
    private String auction_idx;
    private String u_name;
    private String u_phone;
    private String c_brand;
    private String c_mname;
    private String c_myear;
    private String c_num;
    private String c_kms;
    private String c_trans;
    private String c_history;
    private String c_fuel;
    private String c_opt_bbox;
    private String c_opt_rcam;
    private String c_opt_rsensor;
    private String c_opt_as;
    private String c_opt_navi;
    private String c_opt_4wd;
    private String c_opt_sroof;
    private String c_opt_skey;
    private String c_opt_heat;
    private String c_opt_fan;
    private String c_opt_alu;
    private String c_loc_addr;
    private String c_img_1;
    private String c_img_2;
    private String c_img_3;
    private String c_img_4;
    private String c_memo;
    private String a_status;
    private String a_final_price;
    private String a_final_user_idx;

    public CarData(String auction_idx, String u_name, String u_phone, String c_brand, String c_mname, String c_myear, String c_num, String c_kms,
                   String c_trans, String c_history, String c_fuel, String c_opt_bbox, String c_opt_rcam, String c_opt_rsensor, String c_opt_as,
                   String c_opt_navi, String c_opt_4wd, String c_opt_sroof, String c_opt_skey, String c_opt_heat, String c_opt_fan, String c_opt_alu,
                   String c_loc_addr, String c_img_1, String c_img_2, String c_img_3, String c_img_4, String c_memo, String a_status, String a_final_price, String a_final_user_idx) {
        this.auction_idx = auction_idx;
        this.u_name = u_name;
        this.u_phone = u_phone;
        this.c_brand = c_brand;
        this.c_mname = c_mname;
        this.c_myear = c_myear;
        this.c_num = c_num;
        this.c_kms = c_kms;
        this.c_trans = c_trans;
        this.c_history = c_history;
        this.c_fuel = c_fuel;
        this.c_opt_bbox = c_opt_bbox;
        this.c_opt_rcam = c_opt_rcam;
        this.c_opt_rsensor = c_opt_rsensor;
        this.c_opt_as = c_opt_as;
        this.c_opt_navi = c_opt_navi;
        this.c_opt_4wd = c_opt_4wd;
        this.c_opt_sroof = c_opt_sroof;
        this.c_opt_skey = c_opt_skey;
        this.c_opt_heat = c_opt_heat;
        this.c_opt_fan = c_opt_fan;
        this.c_opt_alu = c_opt_alu;
        this.c_loc_addr = c_loc_addr;
        this.c_img_1 = c_img_1;
        this.c_img_2 = c_img_2;
        this.c_img_3 = c_img_3;
        this.c_img_4 = c_img_4;
        this.c_memo = c_memo;
        this.a_status = a_status;
        this.a_final_price = a_final_price;
        this.a_final_user_idx = a_final_user_idx;
    }

    public String getAuction_idx() {
        return auction_idx;
    }

    public void setAuction_idx(String auction_idx) {
        this.auction_idx = auction_idx;
    }

    public String getU_name() {
        return u_name;
    }

    public void setU_name(String u_name) {
        this.u_name = u_name;
    }

    public String getU_phone() {
        return u_phone;
    }

    public void setU_phone(String u_phone) {
        this.u_phone = u_phone;
    }

    public String getC_brand() {
        return c_brand;
    }

    public void setC_brand(String c_brand) {
        this.c_brand = c_brand;
    }

    public String getC_mname() {
        return c_mname;
    }

    public void setC_mname(String c_mname) {
        this.c_mname = c_mname;
    }

    public String getC_myear() {
        return c_myear;
    }

    public void setC_myear(String c_myear) {
        this.c_myear = c_myear;
    }

    public String getC_num() {
        return c_num;
    }

    public void setC_num(String c_num) {
        this.c_num = c_num;
    }

    public String getC_kms() {
        return c_kms;
    }

    public void setC_kms(String c_kms) {
        this.c_kms = c_kms;
    }

    public String getC_trans() {
        return c_trans;
    }

    public void setC_trans(String c_trans) {
        this.c_trans = c_trans;
    }

    public String getC_history() {
        return c_history;
    }

    public void setC_history(String c_history) {
        this.c_history = c_history;
    }

    public String getC_fuel() {
        return c_fuel;
    }

    public void setC_fuel(String c_fuel) {
        this.c_fuel = c_fuel;
    }

    public String getC_opt_bbox() {
        return c_opt_bbox;
    }

    public void setC_opt_bbox(String c_opt_bbox) {
        this.c_opt_bbox = c_opt_bbox;
    }

    public String getC_opt_rcam() {
        return c_opt_rcam;
    }

    public void setC_opt_rcam(String c_opt_rcam) {
        this.c_opt_rcam = c_opt_rcam;
    }

    public String getC_opt_rsensor() {
        return c_opt_rsensor;
    }

    public void setC_opt_rsensor(String c_opt_rsensor) {
        this.c_opt_rsensor = c_opt_rsensor;
    }

    public String getC_opt_as() {
        return c_opt_as;
    }

    public void setC_opt_as(String c_opt_as) {
        this.c_opt_as = c_opt_as;
    }

    public String getC_opt_navi() {
        return c_opt_navi;
    }

    public void setC_opt_navi(String c_opt_navi) {
        this.c_opt_navi = c_opt_navi;
    }

    public String getC_opt_4wd() {
        return c_opt_4wd;
    }

    public void setC_opt_4wd(String c_opt_4wd) {
        this.c_opt_4wd = c_opt_4wd;
    }

    public String getC_opt_sroof() {
        return c_opt_sroof;
    }

    public void setC_opt_sroof(String c_opt_sroof) {
        this.c_opt_sroof = c_opt_sroof;
    }

    public String getC_opt_skey() {
        return c_opt_skey;
    }

    public void setC_opt_skey(String c_opt_skey) {
        this.c_opt_skey = c_opt_skey;
    }

    public String getC_opt_heat() {
        return c_opt_heat;
    }

    public void setC_opt_heat(String c_opt_heat) {
        this.c_opt_heat = c_opt_heat;
    }

    public String getC_opt_fan() {
        return c_opt_fan;
    }

    public void setC_opt_fan(String c_opt_fan) {
        this.c_opt_fan = c_opt_fan;
    }

    public String getC_opt_alu() {
        return c_opt_alu;
    }

    public void setC_opt_alu(String c_opt_alu) {
        this.c_opt_alu = c_opt_alu;
    }

    public String getC_loc_addr() {
        return c_loc_addr;
    }

    public void setC_loc_addr(String c_loc_addr) {
        this.c_loc_addr = c_loc_addr;
    }

    public String getC_img_1() {
        return c_img_1;
    }

    public void setC_img_1(String c_img_1) {
        this.c_img_1 = c_img_1;
    }

    public String getC_img_2() {
        return c_img_2;
    }

    public void setC_img_2(String c_img_2) {
        this.c_img_2 = c_img_2;
    }

    public String getC_img_3() {
        return c_img_3;
    }

    public void setC_img_3(String c_img_3) {
        this.c_img_3 = c_img_3;
    }

    public String getC_img_4() {
        return c_img_4;
    }

    public void setC_img_4(String c_img_4) {
        this.c_img_4 = c_img_4;
    }

    public String getC_memo() {
        return c_memo;
    }

    public void setC_memo(String c_memo) {
        this.c_memo = c_memo;
    }

    public String getA_status() {
        return a_status;
    }

    public void setA_status(String a_status) {
        this.a_status = a_status;
    }

    public String getA_final_price() {
        return a_final_price;
    }

    public void setA_final_price(String a_final_price) {
        this.a_final_price = a_final_price;
    }

    public String getA_final_user_idx() {
        return a_final_user_idx;
    }

    public void setA_final_user_idx(String a_final_user_idx) {
        this.a_final_user_idx = a_final_user_idx;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.auction_idx);
        dest.writeString(this.u_name);
        dest.writeString(this.u_phone);
        dest.writeString(this.c_brand);
        dest.writeString(this.c_mname);
        dest.writeString(this.c_myear);
        dest.writeString(this.c_num);
        dest.writeString(this.c_kms);
        dest.writeString(this.c_trans);
        dest.writeString(this.c_history);
        dest.writeString(this.c_fuel);
        dest.writeString(this.c_opt_bbox);
        dest.writeString(this.c_opt_rcam);
        dest.writeString(this.c_opt_rsensor);
        dest.writeString(this.c_opt_as);
        dest.writeString(this.c_opt_navi);
        dest.writeString(this.c_opt_4wd);
        dest.writeString(this.c_opt_sroof);
        dest.writeString(this.c_opt_skey);
        dest.writeString(this.c_opt_heat);
        dest.writeString(this.c_opt_fan);
        dest.writeString(this.c_opt_alu);
        dest.writeString(this.c_loc_addr);
        dest.writeString(this.c_img_1);
        dest.writeString(this.c_img_2);
        dest.writeString(this.c_img_3);
        dest.writeString(this.c_img_4);
        dest.writeString(this.c_memo);
        dest.writeString(this.a_status);
        dest.writeString(this.a_final_price);
        dest.writeString(this.a_final_user_idx);
    }

    public CarData() {
    }

    protected CarData(Parcel in) {
        this.auction_idx = in.readString();
        this.u_name = in.readString();
        this.u_phone = in.readString();
        this.c_brand = in.readString();
        this.c_mname = in.readString();
        this.c_myear = in.readString();
        this.c_num = in.readString();
        this.c_kms = in.readString();
        this.c_trans = in.readString();
        this.c_history = in.readString();
        this.c_fuel = in.readString();
        this.c_opt_bbox = in.readString();
        this.c_opt_rcam = in.readString();
        this.c_opt_rsensor = in.readString();
        this.c_opt_as = in.readString();
        this.c_opt_navi = in.readString();
        this.c_opt_4wd = in.readString();
        this.c_opt_sroof = in.readString();
        this.c_opt_skey = in.readString();
        this.c_opt_heat = in.readString();
        this.c_opt_fan = in.readString();
        this.c_opt_alu = in.readString();
        this.c_loc_addr = in.readString();
        this.c_img_1 = in.readString();
        this.c_img_2 = in.readString();
        this.c_img_3 = in.readString();
        this.c_img_4 = in.readString();
        this.c_memo = in.readString();
        this.a_status = in.readString();
        this.a_final_price = in.readString();
        this.a_final_user_idx = in.readString();
    }

    public static final Parcelable.Creator<CarData> CREATOR = new Parcelable.Creator<CarData>() {
        @Override
        public CarData createFromParcel(Parcel source) {
            return new CarData(source);
        }

        @Override
        public CarData[] newArray(int size) {
            return new CarData[size];
        }
    };
}
