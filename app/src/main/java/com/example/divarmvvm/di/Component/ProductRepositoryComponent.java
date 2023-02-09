package com.example.divarmvvm.di.Component;

import com.example.divarmvvm.di.Module.ProductRepositoryModule;
import com.example.divarmvvm.model.repository.ProductRepository;

import dagger.Component;

@Component(modules = ProductRepositoryModule.class)
public interface ProductRepositoryComponent {

    ProductRepository getProductRepository();

}
