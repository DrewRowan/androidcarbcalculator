package com.example.calculator;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class CalculationViewModel extends AndroidViewModel {

    private CalculationRepository mRepository;

    private final LiveData<List<Calculation>> mAllCalculations;

    public CalculationViewModel (Application application) {
        super(application);
        mRepository = new CalculationRepository(application);
        mAllCalculations = mRepository.getAllCalculations();
    }

    LiveData<List<Calculation>> getAllCalculations() { return mAllCalculations; }

    public void insert(Calculation Calculation) { mRepository.insert(Calculation); }
}