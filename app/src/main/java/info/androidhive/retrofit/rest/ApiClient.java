package info.androidhive.retrofit.rest;

import android.util.Log;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;


public class ApiClient {

    public static final String BASE_URL = "http://data.xq.com.tw/jds/46/1/2330/TW/GetTAData4Unit.jdxml?SID=2330.TW&ST=1&a=10&b=10&c=0&f=0";
    private static Retrofit retrofit = null;
    private static OkHttpClient okClient = null;


    public static Retrofit getClient() {

        if(okClient == null)
            initOkHttp();
        if (retrofit==null) {
            String mUrl = BASE_URL+ "/";
            retrofit = new Retrofit.Builder()
                    .client(okClient)
                    .baseUrl("http://www.what.com/")
                    .addConverterFactory(SimpleXmlConverterFactory.create())
                    .build();
            Log.e("build Client","");

        }
        return retrofit;
    }

    private static void initOkHttp() {
        OkHttpClient.Builder httpClient = new OkHttpClient().newBuilder();

        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Interceptor.Chain chain) throws IOException {
                Request original = chain.request();
                Request.Builder requestBuilder = original.newBuilder()
                        .addHeader("JD-COOKIE", "DAS.af52533b:JOHNNYHSU:1,2,3,8,9,4,5,6,10,11,12,13,17,20,7.DJ,ET,IS,JN,SH,SZ,XN:66FC33062D89C48C");
                Request request = requestBuilder.build();

                return chain.proceed(request);
            }
        });
        Log.e("init Client","");

        okClient = httpClient.build();
    }

    public static void resetApiClient() {
        retrofit = null;
    }
}
