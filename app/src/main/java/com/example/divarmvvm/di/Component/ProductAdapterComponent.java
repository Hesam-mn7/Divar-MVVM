package com.example.divarmvvm.di.Component;

import com.example.divarmvvm.di.Module.ProductAdapterModule;
import com.example.divarmvvm.view.HomeActivity;
import com.example.divarmvvm.viewModel.ProductViewModel;

import dagger.Component;

@Component(modules = ProductAdapterModule.class )
public interface ProductAdapterComponent {

    void inject(HomeActivity homeActivity);
}
