package com.example.divarmvvm.model.room.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.divarmvvm.model.room.entity.Product;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;

@Dao
public interface ProductDao {

    @Insert
    void insert(Product product);

    @Query("select * from Product order by id desc")
    LiveData<List<Product>> select();

    @Query("DELETE FROM product WHERE id = :id")
    void deleteById(int id);

    @Update
    void update(Product product);
}
