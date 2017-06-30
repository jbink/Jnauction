package kr.co.hiowner.jnauction.api.data;

/**
 * Created by user on 2017-06-29.
 */
public class ServerTimeData {

    /**
     * status_code : 200
     * status_msg : SUCCESS
     * result : {"server_current_date":"2017-06-29 09:19:49","auction_status":"O","auction_open_date":"2017-06-29 09:00:00","auction_close_date":"2017-06-29 09:30:00","auction_close_seconds":611,"auction_next_open_date":"2017-06-29 10:00:00","auction_next_open_seconds":2411}
     */

    private String status_code;
    private String status_msg;
    /**
     * server_current_date : 2017-06-29 09:19:49
     * auction_status : O
     * auction_open_date : 2017-06-29 09:00:00
     * auction_close_date : 2017-06-29 09:30:00
     * auction_close_seconds : 611
     * auction_next_open_date : 2017-06-29 10:00:00
     * auction_next_open_seconds : 2411
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
        private String server_current_date;
        private String auction_status;
        private String auction_open_date;
        private String auction_close_date;
        private int auction_close_seconds;
        private String auction_next_open_date;
        private int auction_next_open_seconds;

        public String getServer_current_date() {
            return server_current_date;
        }

        public void setServer_current_date(String server_current_date) {
            this.server_current_date = server_current_date;
        }

        public String getAuction_status() {
            return auction_status;
        }

        public void setAuction_status(String auction_status) {
            this.auction_status = auction_status;
        }

        public String getAuction_open_date() {
            return auction_open_date;
        }

        public void setAuction_open_date(String auction_open_date) {
            this.auction_open_date = auction_open_date;
        }

        public String getAuction_close_date() {
            return auction_close_date;
        }

        public void setAuction_close_date(String auction_close_date) {
            this.auction_close_date = auction_close_date;
        }

        public int getAuction_close_seconds() {
            return auction_close_seconds;
        }

        public void setAuction_close_seconds(int auction_close_seconds) {
            this.auction_close_seconds = auction_close_seconds;
        }

        public String getAuction_next_open_date() {
            return auction_next_open_date;
        }

        public void setAuction_next_open_date(String auction_next_open_date) {
            this.auction_next_open_date = auction_next_open_date;
        }

        public int getAuction_next_open_seconds() {
            return auction_next_open_seconds;
        }

        public void setAuction_next_open_seconds(int auction_next_open_seconds) {
            this.auction_next_open_seconds = auction_next_open_seconds;
        }
    }
}
