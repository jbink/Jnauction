package kr.co.hiowner.jnauction.api;

import java.io.IOException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import kr.co.hiowner.jnauction.util.GlobalValues;
import okhttp3.Interceptor;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
/**
 * Created by user on 2017-06-27.
 */
public class API_Adapter {

    public static final int CONNECT_TIMEOUT = 15;
    public static final int WRITE_TIMEOUT = 15;
    public static final int READ_TIMEOUT = 15;

    private static OkHttpClient client;
    private static API_Interface Interface;

    public synchronized static API_Interface getInstance(){
        if (Interface == null){
            HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            client = configureClient(new OkHttpClient().newBuilder())
                    .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS) //연결 타임아웃 시간 설정
                    .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS) //쓰기 타임아웃 시간 설정
                    .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS) //읽기 타임아웃 시간 설정
                    .addInterceptor(httpLoggingInterceptor) //http 로그 확인
                    .addInterceptor(new Interceptor(){//헤더를 추가하고 싶을 때
                        @Override
                        public Response intercept(Interceptor.Chain chain) throws IOException {
                            Request original = chain.request();
                            Request request = original.newBuilder()
//                                    .header("Accept", "application/sp.app-v1+json")//헤더의 내용  PATCH -> body -> x-www-form-urlencoded 로 쏴주세요
                                    .header("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8")//헤더의 내용
                                    .method(original.method(), original.body())
                                    .build();
                            return chain.proceed(request);
                        }
                    }) //http 로그 확인
//                  .cookieJar(new JavaNetCookieJar(cookieManager)) //쿠키메니져 설정
                    .build();

//            Gson gson = new GsonBuilder()
//                    .setLenient()
//                    .create();

            Interface = new Retrofit.Builder()
                    .baseUrl(GlobalValues.SERVER1)
                    .client(client)
//                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create()) //Rxandroid를 사용하기 위해 추가(옵션)
                    .addConverterFactory(GsonConverterFactory.create()) //Json Parser 추가
                    .build().create(API_Interface.class); //인터페이스 연결



        }
        return Interface;
    }


    //인증서를 무시하고 통과 되도록 변경
    public static OkHttpClient.Builder configureClient(final OkHttpClient.Builder builder) {
        final TrustManager[] certs = new TrustManager[]{new X509TrustManager() {

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return null;
            }
            @Override
            public void checkServerTrusted(final X509Certificate[] chain, final String authType) throws CertificateException {
            }

            @Override
            public void checkClientTrusted(final X509Certificate[] chain, final String authType) throws CertificateException {
            }
        }};

        SSLContext ctx = null;
        try {
            ctx = SSLContext.getInstance("TLS");
            ctx.init(null, certs, new SecureRandom());
        } catch (final java.security.GeneralSecurityException ex) {
        }

        try {
            final HostnameVerifier hostnameVerifier = new HostnameVerifier() {
                @Override
                public boolean verify(final String hostname, final SSLSession session) {
                    return true;
                }
            };
            builder.sslSocketFactory(ctx.getSocketFactory()).hostnameVerifier(hostnameVerifier);
        } catch (final Exception e) {
        }

        return builder;
    }
}
