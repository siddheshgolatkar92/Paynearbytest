package com.siddhesh.paynearbytest.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;


@Dao
public interface ProductDao {

    @Insert
    void insertOfflineData(ProductDetails productDetails);

    @Insert
    void insertFavorite(Favorite favorite);

    @Query("select * from productDetails")
    List<ProductDetails> getAllOfflineData();

    @Query("select * from productDetails order by id desc limit 1")
    List<ProductDetails> getLastInsertedRecord();

    @Query("select * from favorite")
    List<Favorite> getAllFavoriteData();

    @Query("DELETE FROM favorite WHERE combinations = :combi")
    abstract void deleteFavorite(String combi);



}
