package com.example.divarmvvm.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.divarmvvm.R;
import com.example.divarmvvm.databinding.ActivityDetailBinding;
import com.example.divarmvvm.databinding.ActivityEditBinding;
import com.example.divarmvvm.databinding.ActivityHomeBinding;
import com.example.divarmvvm.model.room.entity.Product;
import com.example.divarmvvm.viewModel.ProductViewModel;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class EditActivity extends AppCompatActivity {

    ActivityEditBinding binding;
    ProductViewModel viewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = ViewModelProviders.of(this).get(ProductViewModel.class);

        final Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String value = intent.getStringExtra("value");
        String time = intent.getStringExtra("time");
        String numberPhone = intent.getStringExtra("numberPhone");
        String details = intent.getStringExtra("details");
        String imgId = intent.getStringExtra("imgId");
        int id = intent.getIntExtra("id", 0);

        binding.etname.setText(name);
        binding.etdetails.setText(details);
        binding.etvalue.setText(value);
        binding.ettime.setText(time);
        binding.etnumberPhone.setText(numberPhone);

        binding.btnedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name1 = binding.etname.getText().toString();
                String value1 = binding.etvalue.getText().toString();
                String time1 = binding.ettime.getText().toString();
                String numberPhone1 = binding.etnumberPhone.getText().toString();
                String details1 = binding.etdetails.getText().toString();

                Product product = new Product(id,name1,value1,time1,imgId,details1,numberPhone1);

                viewModel.update(product).subscribeOn(Schedulers.io()).subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onComplete() {

                        startActivity(new Intent(EditActivity.this,HomeActivity.class));

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });
                Toast.makeText(EditActivity.this, "ویرایش مشخصات با موفقیت انجام شد", Toast.LENGTH_SHORT).show();

            }
        });
        binding.imgback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });




    }
}