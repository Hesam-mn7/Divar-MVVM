package com.example.divarmvvm.model.repository;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.divarmvvm.Const;
import com.example.divarmvvm.di.Component.DaggerProductRepositoryComponent;
import com.example.divarmvvm.di.Component.DaggerRetrofitComponent;
import com.example.divarmvvm.model.api.ProductAPI;
import com.example.divarmvvm.model.room.Appdatabase;
import com.example.divarmvvm.model.room.dao.ProductDao;
import com.example.divarmvvm.model.room.entity.Product;
import com.example.divarmvvm.viewModel.ProductViewModel;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.CompletableObserver;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProductRepository {

    private ProductDao productDao;
    private LiveData<List<Product>> liveData;

    private ProductAPI api;

    public ProductRepository(Application application) {
        this.productDao = Appdatabase.getInstance(application).getProductDao();

        liveData = select();

        Retrofit retrofit = DaggerRetrofitComponent.create().getRetrofit();
        api = retrofit.create(ProductAPI.class);

    }

    public Completable insert(Product product) {
        Completable completable = new Completable() {
            @Override
            protected void subscribeActual(@NonNull CompletableObserver observer) {
                productDao.insert(product);
                observer.onComplete();
            }
        };
        return completable;
    }

    public LiveData<List<Product>> getProductFromWebServerAndInsert(){

        LiveData<List<Product>> liveData1 = new LiveData<List<Product>>() {
            @Override
            public void observe(@androidx.annotation.NonNull LifecycleOwner owner, @androidx.annotation.NonNull Observer<? super List<Product>> observer) {
                super.observe(owner, observer);
                api.insert().enqueue(new Callback<List<Product>>() {
                    @Override
                    public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                        if (response.code() == 200) {
                            observer.onChanged(response.body());
                        } else {
                            observer.onChanged(null);
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Product>> call, Throwable t) {
                        observer.onChanged(null);
                    }
                });
            }
        };

        return liveData1;

    }

    public Completable deleteById(int id) {
        Completable completable = new Completable() {
            @Override
            protected void subscribeActual(@NonNull CompletableObserver observer) {
                productDao.deleteById(id);
                observer.onComplete();
            }
        };
        return completable;
    }

    public Completable update(Product product) {
        Completable completable = new Completable() {
            @Override
            protected void subscribeActual(@NonNull CompletableObserver observer) {
                productDao.update(product);
                observer.onComplete();
            }
        };
        return completable;
    }

    public LiveData<List<Product>> select() {
        return productDao.select();

    }

//    public void cheak() {
//        String result = view.getSharedPreference(Const.SHARED_PERF_KEY_REGISTER);
//    }
}
