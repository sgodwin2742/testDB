package com.clas.testdb2;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface DaoAccess {
    @Insert
    void insertSingleExercise(Exercise ex);

    @Insert
    void insertExercises(List<Exercise> exList);

    @Query("SELECT * FROM Exercise")
    List<Exercise> fetchAllExercises();

    @Update
    void updateMovie (Exercise ex);
}
