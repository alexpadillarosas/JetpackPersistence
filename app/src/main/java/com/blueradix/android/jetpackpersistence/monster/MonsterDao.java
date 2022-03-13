package com.blueradix.android.jetpackpersistence.monster;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface MonsterDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Monster monster);

    @Update
    void update(Monster monster);

    @Delete
    void delete(Monster monster);

    //
    @Query("SELECT * FROM MONSTER")
    LiveData<List<Monster>> findAll();

    @Query("SELECT * FROM MONSTER WHERE ID = :id")
    Monster findById(int id);

}

