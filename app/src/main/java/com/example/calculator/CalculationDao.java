package com.example.calculator;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface CalculationDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Calculation calculation);

    @Query("DELETE FROM calculation_table")
    void deleteAll();

    @Query("SELECT * FROM calculation_table ORDER BY id DESC")
    LiveData<List<Calculation>> getSortedCalculations();
}