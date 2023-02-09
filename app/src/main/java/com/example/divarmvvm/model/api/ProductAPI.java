package com.example.divarmvvm.model.api;

import com.example.divarmvvm.model.room.entity.Product;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ProductAPI {

    String BASE_URL="https://run.mocky.io/";

    @GET("v3/1e062d3e-b53c-4edf-8732-dc5024bd952f")
//    Call<List<Product>> select();
    Call<List<Product>> insert();

}
