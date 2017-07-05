package kr.co.hiowner.jnauction.api.data;

/**
 * Created by user on 2017-07-03.
 */
public class AuctionData {


    /**
     * status_code : 200
     * status_msg : SUCCESS
     * result : {"auction_idx":"12","u_name":"정**","u_phone":"010********","c_brand":"현대","c_mname":"투싼","c_myear":"14","c_num":"64오9312","c_kms":"72000","c_trans":"A","c_history":"Y","c_fuel":"D","c_opt_bbox":"Y","c_opt_rcam":"Y","c_opt_rsensor":"Y","c_opt_as":"Y","c_opt_navi":"Y","c_opt_4wd":"N","c_opt_sroof":"N","c_opt_skey":"Y","c_opt_heat":"Y","c_opt_fan":"N","c_opt_alu":"N","c_loc_addr":" 평택시 안중읍 현덕면 기산리","c_img_1":"http://image.theowner.co.kr/joongna/mycarsale/00/0010/1087_img_1_5977.jpg","c_img_2":"http://image.theowner.co.kr/joongna/mycarsale/00/0010/1087_img_2_2186.jpg","c_img_3":"http://image.theowner.co.kr/joongna/mycarsale/00/0010/1087_img_3_3636.jpg","c_img_4":"http://image.theowner.co.kr/joongna/mycarsale/00/0010/1087_img_4_2418.jpg","c_memo":"쇼바 디스크 타이어 등 소모품 최근에  교체 ","a_status":"300","a_avg_price":"0","a_max_price":"0","a_bid_count":"2","b_price":"1500","b_rank":"2","b_count":"0","b_upd_date":"2017-06-29 18:23:31","b_mybid":"Y"}
     */

    private String status_code;
    private String status_msg;
    /**
     * auction_idx : 12
     * u_name : 정**
     * u_phone : 010********
     * c_brand : 현대
     * c_mname : 투싼
     * c_myear : 14
     * c_num : 64오9312
     * c_kms : 72000
     * c_trans : A
     * c_history : Y
     * c_fuel : D
     * c_opt_bbox : Y
     * c_opt_rcam : Y
     * c_opt_rsensor : Y
     * c_opt_as : Y
     * c_opt_navi : Y
     * c_opt_4wd : N
     * c_opt_sroof : N
     * c_opt_skey : Y
     * c_opt_heat : Y
     * c_opt_fan : N
     * c_opt_alu : N
     * c_loc_addr :  평택시 안중읍 현덕면 기산리
     * c_img_1 : http://image.theowner.co.kr/joongna/mycarsale/00/0010/1087_img_1_5977.jpg
     * c_img_2 : http://image.theowner.co.kr/joongna/mycarsale/00/0010/1087_img_2_2186.jpg
     * c_img_3 : http://image.theowner.co.kr/joongna/mycarsale/00/0010/1087_img_3_3636.jpg
     * c_img_4 : http://image.theowner.co.kr/joongna/mycarsale/00/0010/1087_img_4_2418.jpg
     * c_memo : 쇼바 디스크 타이어 등 소모품 최근에  교체
     * a_status : 300
     * a_avg_price : 0
     * a_max_price : 0
     * a_bid_count : 2
     * b_price : 1500
     * b_rank : 2
     * b_count : 0
     * b_upd_date : 2017-06-29 18:23:31
     * b_mybid : Y
     */

    private Resultfdg result;

    public String getStatus_code() {
        return status_code;
    }

    public void setStatus_code(String status_code) {
        this.status_code = status_code;
    }

    public String getStatus_msg() {
        return status_msg;
    }

    public void setStatus_msg(String status_msg) {
        this.status_msg = status_msg;
    }

    public Resultfdg getResult() {
        return result;
    }

    public void setResult(Resultfdg result) {
        this.result = result;
    }

    public static class Resultfdg {
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
        private String a_avg_price;
        private String a_max_price;
        private String a_bid_count;
        private String b_price;
        private String b_rank;
        private String b_count;
        private String b_upd_date;
        private String b_mybid;

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

        public String getA_avg_price() {
            return a_avg_price;
        }

        public void setA_avg_price(String a_avg_price) {
            this.a_avg_price = a_avg_price;
        }

        public String getA_max_price() {
            return a_max_price;
        }

        public void setA_max_price(String a_max_price) {
            this.a_max_price = a_max_price;
        }

        public String getA_bid_count() {
            return a_bid_count;
        }

        public void setA_bid_count(String a_bid_count) {
            this.a_bid_count = a_bid_count;
        }

        public String getB_price() {
            return b_price;
        }

        public void setB_price(String b_price) {
            this.b_price = b_price;
        }

        public String getB_rank() {
            return b_rank;
        }

        public void setB_rank(String b_rank) {
            this.b_rank = b_rank;
        }

        public String getB_count() {
            return b_count;
        }

        public void setB_count(String b_count) {
            this.b_count = b_count;
        }

        public String getB_upd_date() {
            return b_upd_date;
        }

        public void setB_upd_date(String b_upd_date) {
            this.b_upd_date = b_upd_date;
        }

        public String getB_mybid() {
            return b_mybid;
        }

        public void setB_mybid(String b_mybid) {
            this.b_mybid = b_mybid;
        }
    }
}
