package kr.co.hiowner.jnauction.api.data;

/**
 * Created by user on 2017-07-03.
 */
public class UserData {


    /**
     * status_code : 200
     * status_msg : SUCCESS
     * result : {"name":"앱앤앱스1","phone":"01090379408","profile_img":"http://image.theowner.co.kr/joongna/user_profile/default.jpg","license_start_date":"2017-05-29 00:00:00","license_end_date":"2018-06-29 00:00:00","monthly_add_points":"50","monthly_points":"33","extra_points":"0","cnt_success_today":0,"cnt_success_total":6,"cnt_bid_today":0,"cnt_bid_total":12,"cnt_sale_today":0,"cnt_sale_total":3}
     */

    private String status_code;
    private String status_msg;
    /**
     * name : 앱앤앱스1
     * phone : 01090379408
     * profile_img : http://image.theowner.co.kr/joongna/user_profile/default.jpg
     * license_start_date : 2017-05-29 00:00:00
     * license_end_date : 2018-06-29 00:00:00
     * monthly_add_points : 50
     * monthly_points : 33
     * extra_points : 0
     * cnt_success_today : 0
     * cnt_success_total : 6
     * cnt_bid_today : 0
     * cnt_bid_total : 12
     * cnt_sale_today : 0
     * cnt_sale_total : 3
     */

    private ResultObject result;

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

    public ResultObject getResult() {
        return result;
    }

    public void setResult(ResultObject result) {
        this.result = result;
    }

    public static class ResultObject {
        private String name;
        private String phone;
        private String profile_img;
        private String license_start_date;
        private String license_end_date;
        private String monthly_add_points;
        private String monthly_points;
        private String extra_points;
        private int cnt_success_today;
        private int cnt_success_total;
        private int cnt_bid_today;
        private int cnt_bid_total;
        private int cnt_sale_today;
        private int cnt_sale_total;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getProfile_img() {
            return profile_img;
        }

        public void setProfile_img(String profile_img) {
            this.profile_img = profile_img;
        }

        public String getLicense_start_date() {
            return license_start_date;
        }

        public void setLicense_start_date(String license_start_date) {
            this.license_start_date = license_start_date;
        }

        public String getLicense_end_date() {
            return license_end_date;
        }

        public void setLicense_end_date(String license_end_date) {
            this.license_end_date = license_end_date;
        }

        public String getMonthly_add_points() {
            return monthly_add_points;
        }

        public void setMonthly_add_points(String monthly_add_points) {
            this.monthly_add_points = monthly_add_points;
        }

        public String getMonthly_points() {
            return monthly_points;
        }

        public void setMonthly_points(String monthly_points) {
            this.monthly_points = monthly_points;
        }

        public String getExtra_points() {
            return extra_points;
        }

        public void setExtra_points(String extra_points) {
            this.extra_points = extra_points;
        }

        public int getCnt_success_today() {
            return cnt_success_today;
        }

        public void setCnt_success_today(int cnt_success_today) {
            this.cnt_success_today = cnt_success_today;
        }

        public int getCnt_success_total() {
            return cnt_success_total;
        }

        public void setCnt_success_total(int cnt_success_total) {
            this.cnt_success_total = cnt_success_total;
        }

        public int getCnt_bid_today() {
            return cnt_bid_today;
        }

        public void setCnt_bid_today(int cnt_bid_today) {
            this.cnt_bid_today = cnt_bid_today;
        }

        public int getCnt_bid_total() {
            return cnt_bid_total;
        }

        public void setCnt_bid_total(int cnt_bid_total) {
            this.cnt_bid_total = cnt_bid_total;
        }

        public int getCnt_sale_today() {
            return cnt_sale_today;
        }

        public void setCnt_sale_today(int cnt_sale_today) {
            this.cnt_sale_today = cnt_sale_today;
        }

        public int getCnt_sale_total() {
            return cnt_sale_total;
        }

        public void setCnt_sale_total(int cnt_sale_total) {
            this.cnt_sale_total = cnt_sale_total;
        }
    }
}
