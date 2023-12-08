package com.androidafe.dobazar.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Carts.class}, version = 1)
public abstract class CartDB extends RoomDatabase {
    public abstract CartsDao userDao();
}