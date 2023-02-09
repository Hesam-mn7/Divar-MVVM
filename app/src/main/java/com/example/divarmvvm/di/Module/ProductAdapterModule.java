package com.example.divarmvvm.di.Module;

import android.app.Application;
import android.content.Context;

import com.example.divarmvvm.view.Adapter.ProductAdapter;
import com.example.divarmvvm.viewModel.ProductViewModel;

import dagger.Module;
import dagger.Provides;

@Module
public class ProductAdapterModule {

    private ProductViewModel viewModel;
    private Context context;

    public ProductAdapterModule(ProductViewModel viewModel, Context context) {
        this.viewModel = viewModel;
        this.context = context;
    }

    @Provides
    public ProductAdapter provideProductAdapter(){
        return new ProductAdapter(viewModel,context);
    }
}
