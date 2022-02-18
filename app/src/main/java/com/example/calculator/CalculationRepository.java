package com.example.calculator;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

class CalculationRepository {

    private CalculationDao mCalculationDao;
    private LiveData<List<Calculation>> mAllCalculations;

    // Note that in order to unit test the CalculationRepository, you have to remove the Application
    // dependency. This adds complexity and much more code, and this sample is not about testing.
    // See the BasicSample in the android-architecture-components repository at
    // https://github.com/googlesamples
    CalculationRepository(Application application) {
        CalculationRoomDatabase db = CalculationRoomDatabase.getDatabase(application);
        mCalculationDao = db.CalculationDao();
        mAllCalculations = mCalculationDao.getSortedCalculations();
    }

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    LiveData<List<Calculation>> getAllCalculations() {
        return mAllCalculations;
    }

    // You must call this on a non-UI thread or your app will throw an exception. Room ensures
    // that you're not doing any long running operations on the main thread, blocking the UI.
    void insert(Calculation Calculation) {
        CalculationRoomDatabase.databaseWriteExecutor.execute(() -> {
            mCalculationDao.insert(Calculation);
        });
    }
}