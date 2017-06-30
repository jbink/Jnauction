package kr.co.hiowner.jnauction.api;

import java.util.HashMap;

import kr.co.hiowner.jnauction.api.data.AuctionsData;
import kr.co.hiowner.jnauction.api.data.ServerTimeData;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

/**
 * Created by user on 2017-06-27.
 */
public interface API_Interface {

    @GET("server")
    Call<ServerTimeData> ServerTime();

    @FormUrlEncoded
    @POST("duplicate.php")
    Call<String> Duplicate(@Field("id") String id);
//    Call<String> Duplicate(@FieldMap HashMap<String, String> fields);

    @FormUrlEncoded
    @PATCH("user")
    Call<ResponseBaseData> UserUpdate(@Field("token") String token, @Field("device_os") String device_os, @Field("device_model") String device_model, @Field("device_id") String device_id, @Field("push_id") String push_id);

    @GET("auctions")
    Call<AuctionsData> Auctions(@QueryMap HashMap<String, String> fields);
}
