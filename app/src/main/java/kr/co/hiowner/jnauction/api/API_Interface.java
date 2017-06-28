package kr.co.hiowner.jnauction.api;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.PATCH;
import retrofit2.http.POST;

/**
 * Created by user on 2017-06-27.
 */
public interface API_Interface {

    @FormUrlEncoded
    @POST("duplicate.php")
    Call<String> Duplicate(@Field("id") String id);

    @FormUrlEncoded
    @PATCH("user")
    Call<ResponseBaseData> UserUpdate(@Field("token") String token, @Field("device_os") String device_os, @Field("device_model") String device_model, @Field("device_id") String device_id, @Field("push_id") String push_id);


}
