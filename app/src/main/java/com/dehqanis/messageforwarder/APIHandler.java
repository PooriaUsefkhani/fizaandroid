package com.dehqanis.messageforwarder;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.security.cert.CertificateException;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.Credentials;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIHandler {
//    public static final String BASE_URL = "https://fiza.ir/wp-json/wc/v2/";

    public static final String BASE_URL = "https://crm.fiza.ir/";
//    private static Retrofit retrofit;
//    public static String cookie="";
//    public static String token="";

    public APIHandler(){

    }
    public static API getAPI(){
//        if(retrofit==null) {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(getUnsafeOkHttpClient())
                .build();
//        }
        return retrofit.create(API.class);
    }

    public static OkHttpClient getUnsafeOkHttpClient() {

        try {
            // Create a trust manager that does not validate certificate chains
            final TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
                @Override
                public void checkClientTrusted(
                        java.security.cert.X509Certificate[] chain,
                        String authType) throws CertificateException {
                }

                @Override
                public void checkServerTrusted(
                        java.security.cert.X509Certificate[] chain,
                        String authType) throws CertificateException {
                }

                @Override
                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                    return new java.security.cert.X509Certificate[0];
                }
            } };

            // Install the all-trusting trust manager
            final SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, trustAllCerts,
                    new java.security.SecureRandom());
            // Create an ssl socket factory with our all-trusting manager
            final SSLSocketFactory sslSocketFactory = sslContext
                    .getSocketFactory();

            OkHttpClient okHttpClient = new OkHttpClient();

            okHttpClient = okHttpClient.newBuilder()
                    .sslSocketFactory(sslSocketFactory).addInterceptor(new Interceptor() {
                        @Override
                        public okhttp3.Response intercept(Chain chain) throws IOException {
                            final Request original = chain.request();
                            final Request authorized = original.newBuilder().build();
                            return chain.proceed(authorized);
                        }
                    })
                    .connectTimeout(120, TimeUnit.SECONDS)
                    .readTimeout(120,TimeUnit.SECONDS)
                    .writeTimeout(120,TimeUnit.SECONDS)
                    .build();

            return okHttpClient;
        } catch (Exception e) {
            //throw new RuntimeException(e);
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }
}