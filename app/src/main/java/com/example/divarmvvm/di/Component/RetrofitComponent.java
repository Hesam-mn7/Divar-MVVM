package com.example.divarmvvm.di.Component;

import com.example.divarmvvm.di.Module.RetrofitModule;

import dagger.Component;
import retrofit2.Retrofit;

@Component(modules = RetrofitModule.class)
public interface RetrofitComponent {

    Retrofit getRetrofit();
}
