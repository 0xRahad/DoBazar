package com.androidafe.dobazar.room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface CartsDao {

    @Insert
    void insertCartsData(Carts carts);

    @Query("SELECT * FROM carts")
    List<Carts> getCarts();


    @Delete
    void deleteCartItem(Carts cart);


}
