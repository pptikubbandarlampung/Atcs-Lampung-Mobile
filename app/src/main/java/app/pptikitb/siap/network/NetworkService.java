package app.pptikitb.siap.network;


import android.database.Observable;

import java.util.List;

import app.pptikitb.siap.features.cctv.model.Cctvs;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by github.com/adip28 on 7/17/2018.
 */
public interface NetworkService {


    @GET("cctv")
    Call<List<Cctvs>> getCctvList();

    @POST("login")
    Observable<Response> login();

}