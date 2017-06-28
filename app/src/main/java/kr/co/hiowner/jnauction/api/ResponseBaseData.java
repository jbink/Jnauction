package kr.co.hiowner.jnauction.api;

/**
 * Created by user on 2017-06-27.
 */
public class ResponseBaseData {

    private String status_code;
    private String status_msg;

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
}
