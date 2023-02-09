package com.example.divarmvvm.viewModel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.divarmvvm.Const;
import com.example.divarmvvm.di.Component.DaggerProductRepositoryComponent;
import com.example.divarmvvm.di.Module.ProductRepositoryModule;
import com.example.divarmvvm.model.repository.ProductRepository;
import com.example.divarmvvm.model.room.entity.Product;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;

public class ProductViewModel extends AndroidViewModel {

    private ProductRepository repository;
    private LiveData<List<Product>> liveData;

    public ProductViewModel(Application application) {
        super(application);

        //repository = new ProductRepository(application);
        repository = DaggerProductRepositoryComponent
                .builder().productRepositoryModule(new ProductRepositoryModule(application))
                .build().getProductRepository();

    }

    public Completable insert(Product product){
        return repository.insert(product);
    }
    public Completable deleteById(int id){
        return repository.deleteById(id);
    }
    public Completable update(Product product){
        return repository.update(product);
    }
    public LiveData<List<Product>> select(){
        return repository.select();

    }

    public LiveData<List<Product>> getProductFromWebServerAndInsert(){
        return repository.getProductFromWebServerAndInsert();
    }

}
