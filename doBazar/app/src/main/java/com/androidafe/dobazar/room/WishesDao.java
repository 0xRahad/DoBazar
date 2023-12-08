package com.androidafe.dobazar.room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface WishesDao {

    @Insert
    void insertWishData(Wishes wishes);

    @Query("SELECT * FROM wishes")
    List<Wishes> getWishes();


    @Delete
    void deleteWishes(Wishes wishes);


}
