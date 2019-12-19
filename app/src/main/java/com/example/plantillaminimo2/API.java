package com.example.plantillaminimo2;

import com.example.plantillaminimo2.models.Museums;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface API {

    @GET("pag-ini/{pag-ini}/pag-fi/{pag-fi}")
    Call<Museums> getMuseums(@Path("pag-ini") int pagini, @Path("pag-fi") int pagfi);

    public static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://do.diba.cat/api/dataset/museus/format/json/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

}