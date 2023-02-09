package com.example.divarmvvm.model.room;

import android.app.Application;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.divarmvvm.model.room.dao.ProductDao;
import com.example.divarmvvm.model.room.entity.Product;

@Database(entities = Product.class , version = 1)
public abstract class Appdatabase extends RoomDatabase {

    public abstract ProductDao getProductDao();

    private static Appdatabase instance;

    public static Appdatabase getInstance(Application application) {
        if (instance == null) {
            instance = Room.databaseBuilder(application, Appdatabase.class, "DivarMVM.DB").build();

        }
        return instance;
    }
}
