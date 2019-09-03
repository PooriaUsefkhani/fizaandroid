package com.dehqanis.messageforwarder;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface API {
    @GET("https://crm.fiza.ir/Utils/SMS/getSMS")
    Call<ResponseBody> sendSMS(@Query("from") String from,@Query("msg") String msg);
}
