package com.pals.cyborg.Interface;

import com.pals.cyborg.Utils.Constants;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface NetworkInterface  {

     @POST("/data")
     Call<ResponseBody> getData (@Body RequestBody data);
}
