package com.example.calculator;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Calculation.class}, version = 1, exportSchema = false)
public abstract class CalculationRoomDatabase extends RoomDatabase {

    public abstract CalculationDao CalculationDao();

    private static volatile CalculationRoomDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static CalculationRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (CalculationRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            CalculationRoomDatabase.class, "calculation_database")
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            // If you want to keep data through app restarts,
            // comment out the following block
            databaseWriteExecutor.execute(() -> {
                // Populate the database in the background.
                // If you want to start with more calculations, just add them.
                CalculationDao dao = INSTANCE.CalculationDao();
                dao.deleteAll();

                Calculation calculation = new Calculation();
                calculation.setCalculation("1", "2", "3");
                dao.insert(calculation);
                calculation = new Calculation();
                calculation.setCalculation("4", "5", "67");
                dao.insert(calculation);
            });
        }
    };

}