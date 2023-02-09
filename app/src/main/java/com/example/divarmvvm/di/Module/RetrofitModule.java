package com.example.divarmvvm.di.Module;

import com.example.divarmvvm.model.api.ProductAPI;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class RetrofitModule {

    @Provides
    public Retrofit provideRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(ProductAPI.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

    }
}
