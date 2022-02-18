package com.example.calculator;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Calendar;
import java.util.Date;

@Entity(tableName = "calculation_table")
public class Calculation {

    @ColumnInfo
    @PrimaryKey(autoGenerate=true)
    Long id;

    String bloodGlucose;

    @ColumnInfo
    String carbohydrates;

    @ColumnInfo
    String calculation;

    public void setCalculation(String bloodGlucose, String carbohydrates, String calculation) {
        this.bloodGlucose = bloodGlucose;
        this.carbohydrates = carbohydrates;
        this.calculation = calculation;

    }

    public String getCalculation() {
        return this.calculation;
    }
}