package com.example.divarmvvm.view;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.ViewModelProviders;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.divarmvvm.Const;
import com.example.divarmvvm.R;
import com.example.divarmvvm.databinding.ActivitySabtAgahiBinding;
import com.example.divarmvvm.model.room.entity.Product;
import com.example.divarmvvm.viewModel.ProductViewModel;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SabtAgahiActivity extends AppCompatActivity {

    ActivitySabtAgahiBinding binding;

    ProductViewModel viewModel;
    private static final int SELECT_PHOTO = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySabtAgahiBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = ViewModelProviders.of(this).get(ProductViewModel.class);

        binding.imgafzodan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, SELECT_PHOTO);

            }
        });

        binding.btnsabt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = binding.etname.getText().toString();
                String value = binding.etvalue.getText().toString();
                String time = binding.ettime.getText().toString();
                String numberPhone = binding.etnumberPhone.getText().toString();
                String details = binding.etdetails.getText().toString();
                String imgId = binding.etgetimg.getText().toString();
                if (name.length() < 3) {
                    binding.etname.setError("طول عنوان آگهی باید حداقل 3 حرف باشد.");
                } else if (value.isEmpty()) {
                    binding.etvalue.setError("قیمت را وارد کنید.");
                } else if (time.isEmpty()) {
                    binding.ettime.setError("نام محله را وارد کنید.");
                } else if ((numberPhone.length() != 11) || !numberPhone.startsWith("0")) {
                    binding.etnumberPhone.setError("شماره موبایل را صحیح وارد کنید." + "\n" + "مانند: 09121234567");
                } else if (details.length() < 10) {
                    binding.etdetails.setError("طول جزئیات آگهی باید حداقل 10 حرف باشد.");
                } else {
                    Product product = new Product(0, name, value, time, imgId, details, numberPhone);

                    viewModel.insert(product)
                            .subscribeOn(Schedulers.io())
                            .subscribe(new CompletableObserver() {
                                @Override
                                public void onSubscribe(@NonNull Disposable d) {

                                }

                                @Override
                                public void onComplete() {
                                    Intent intent = new Intent(SabtAgahiActivity.this, HomeActivity.class);
                                    startActivity(intent);

                                }

                                @Override
                                public void onError(@NonNull Throwable e) {

                                }
                            });
                    Toast.makeText(SabtAgahiActivity.this, "آگهی شما با موفقیت ثبت شد.", Toast.LENGTH_SHORT).show();

                }


            }
        });
        binding.imgback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        switch(requestCode) {
            case SELECT_PHOTO:
                if(resultCode == RESULT_OK){
                    Uri selectedImage = imageReturnedIntent.getData();
                    InputStream imageStream = null;
                    try {
                        imageStream = getContentResolver().openInputStream(selectedImage);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    Bitmap yourSelectedImage = BitmapFactory.decodeStream(imageStream);
                    binding.imgafzodan.setImageBitmap(yourSelectedImage);

                    storeImage(yourSelectedImage);

                }
        }
    }

    private void storeImage(Bitmap image) {
        File pictureFile = getOutputMediaFile();
        if (pictureFile == null) {
            Log.e("Const.TAG",
                    "Error creating media file, check storage permissions: ");// e.getMessage());
            return;
        }
        try {
            FileOutputStream fos = new FileOutputStream(pictureFile);
            image.compress(Bitmap.CompressFormat.PNG, 90, fos);
            fos.close();
        } catch (FileNotFoundException e) {
            Log.e("Const.TAG", "File not found: " + e.getMessage());
        } catch (IOException e) {
            Log.e("Const.TAG", "Error accessing file: " + e.getMessage());
        }
        binding.etgetimg.setText(pictureFile.toURI().toString());
    }
    /** Create a File for saving an image or video */
    private File getOutputMediaFile(){
        File file = ActivityCompat.getDataDir(this);

        // Create a media file name
        String timeStamp = new SimpleDateFormat("ddMMyyyy_HHmm").format(new Date());
        File mediaFile;
        String mImageName="MI_"+ timeStamp +".jpg";

        String finalPath = file.toString() +"/" +mImageName;
        mediaFile = new File(finalPath);
        return mediaFile;
    }


}