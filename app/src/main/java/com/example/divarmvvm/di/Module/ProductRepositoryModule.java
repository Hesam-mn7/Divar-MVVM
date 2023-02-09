package com.example.divarmvvm.di.Module;

import android.app.Application;

import com.example.divarmvvm.model.repository.ProductRepository;

import dagger.Module;
import dagger.Provides;

@Module
public class ProductRepositoryModule {

    Application application;

    public ProductRepositoryModule(Application application) {
        this.application = application;
    }

    @Provides
    public ProductRepository provideProductRepository(){
        return new ProductRepository(application);
    }
}
