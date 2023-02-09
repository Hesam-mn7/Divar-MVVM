package com.example.divarmvvm.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.example.divarmvvm.R;
import com.example.divarmvvm.databinding.ActivityDetailBinding;
import com.example.divarmvvm.model.room.entity.Product;
import com.example.divarmvvm.viewModel.ProductViewModel;
import com.squareup.picasso.Picasso;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class DetailActivity extends AppCompatActivity {

    ActivityDetailBinding binding;

    ProductViewModel viewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailBinding.inflate(getLayoutInflater());
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

        binding.tvtitlename.setText(name);
        binding.tvnamedetail.setText(name);
        binding.tvvaluedetail.setText(value);
        binding.tvtimedetail.setText(time);
        binding.tvdetails.setText(details);

        Uri uri = Uri.parse(imgId);

        Picasso.get()
                .load(uri)
                .into(binding.imgdetail);

        binding.btncall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent();
                intent1.setAction(Intent.ACTION_VIEW);
                intent1.setData(Uri.parse("tel:+" + numberPhone));
                startActivity(intent1);
            }
        });

        binding.btnchat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent();
                intent1.setAction(Intent.ACTION_VIEW);
                intent1.setData(Uri.parse("sms:+" + numberPhone));
                startActivity(intent1);
            }
        });

        binding.imgdelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog alertDialog;
                final AlertDialog.Builder builder = new AlertDialog.Builder(DetailActivity.this);
                builder.setTitle("آیا مطمئنید که آگهی حذف شود ؟");
                builder.setPositiveButton("بله", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        viewModel.deleteById(id).subscribeOn(Schedulers.io()).subscribe(new CompletableObserver() {
                            @Override
                            public void onSubscribe(@NonNull Disposable d) {

                            }

                            @Override
                            public void onComplete() {
                                finish();

                            }

                            @Override
                            public void onError(@NonNull Throwable e) {

                            }
                        });
                    }
                });
                builder.setNegativeButton("خیر", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                alertDialog = builder.create();
                alertDialog.show();
            }
        });

        binding.imgedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailActivity.this,EditActivity.class);
                intent.putExtra("name", name);
                intent.putExtra("value", value);
                intent.putExtra("time", time);
                intent.putExtra("numberPhone", numberPhone);
                intent.putExtra("details", details);
                intent.putExtra("imgId", imgId);
                intent.putExtra("id", id);
                startActivity(intent);

            }
        });


    }
}