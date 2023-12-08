package com.androidafe.dobazar.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Wishes.class}, version = 1)
public abstract class WishDB extends RoomDatabase {
    public abstract WishesDao wishesDao();
}