package com.example.divarmvvm.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.example.divarmvvm.Const;
import com.example.divarmvvm.R;
import com.example.divarmvvm.databinding.ActivityHomeBinding;
import com.example.divarmvvm.di.Component.DaggerProductAdapterComponent;
import com.example.divarmvvm.di.Module.ProductAdapterModule;
import com.example.divarmvvm.model.room.entity.Product;
import com.example.divarmvvm.view.Adapter.ProductAdapter;
import com.example.divarmvvm.viewModel.ProductViewModel;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class HomeActivity extends AppCompatActivity {
    ActivityHomeBinding binding;

    ProductViewModel viewModel;

    @Inject
    ProductAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = ViewModelProviders.of(this).get(ProductViewModel.class);

        initRecycleView();
        GetProductFromWebServer();
        navigationClick();

    }

    private void initRecycleView() {
        DaggerProductAdapterComponent.builder().productAdapterModule(new ProductAdapterModule(viewModel, this)).build().inject(this);

        binding.recycleview.setLayoutManager(new LinearLayoutManager(this));
        binding.recycleview.setAdapter(adapter);

        viewModel.select().observe(this, new Observer<List<Product>>() {
            @Override
            public void onChanged(List<Product> products) {
                adapter.setList(products);
            }
        });
    }

    private void GetProductFromWebServer() {
        String result = getSharedPreference(Const.SHARED_PERF_KEY_REGISTER);
        if (result == null) {
            viewModel.getProductFromWebServerAndInsert().observe(this, new Observer<List<Product>>() {
                @Override
                public void onChanged(List<Product> products) {
                    for (Product item : products) {
                        Product product = new Product(item.getId(), item.getName(), item.getValue(),
                                item.getTime(), item.getImgId(), item.getDetails(), item.getNumberPhone());

                        viewModel.insert(product).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new CompletableObserver() {
                                    @Override
                                    public void onSubscribe(@NonNull Disposable d) {

                                    }

                                    @Override
                                    public void onComplete() {
                                        setPrefernce(Const.SHARED_PERF_KEY_REGISTER, "finish");

                                    }

                                    @Override
                                    public void onError(@NonNull Throwable e) {

                                    }
                                });
                    }
                }
            });
        }
    }

    public void setPrefernce(String key, String value) {
        SharedPreferences sharedPreferences = getSharedPreferences(Const.SHARED_PREF_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public String getSharedPreference(String key) {
        SharedPreferences sharedPreferences = getSharedPreferences(Const.SHARED_PREF_NAME, MODE_PRIVATE);
        if (!sharedPreferences.contains(key)) {
            return null;
        }
        return sharedPreferences.getString(key, null);
    }

    private void navigationClick() {
        binding.btsabt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, SabtAgahiActivity.class);
                startActivity(intent);
            }
        });
        binding.btchat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, "این قسمت در دست ساخت می باشد.", Snackbar.LENGTH_SHORT).show();

            }
        });
        binding.btdaste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, "این قسمت در دست ساخت می باشد.", Snackbar.LENGTH_SHORT).show();

            }
        });
        binding.btdaste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, "این قسمت در دست ساخت می باشد.", Snackbar.LENGTH_SHORT).show();

            }
        });
        binding.btfilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, "این قسمت در دست ساخت می باشد.", Snackbar.LENGTH_SHORT).show();

            }
        });
        binding.btdivareman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, "این قسمت در دست ساخت می باشد.", Snackbar.LENGTH_SHORT).show();

            }
        });

    }
}