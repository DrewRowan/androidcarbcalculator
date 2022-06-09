package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public static final int NEW_CALCULATION_ACTIVITY_REQUEST_CODE = 1;
    
    private EditText opr1;
    private EditText opr2;
    private TextView txtresult;
    private float valInsulinPerCarbs = 0.5F;
    private int valCorrectionDose = 1;
    private CalculationViewModel mCalculationViewModel;
    private Spinner spinner;

    public double calculateRangeCorrection(double currentBloodGlucose) {
        // adjust for range e.g. 5-7 = 0 correction, 7-9 = 1, etc
        double rangeCorrection = adjustForRange((currentBloodGlucose - 5) / 2);
        if (rangeCorrection < 0) {
            rangeCorrection = 0;
        }
        rangeCorrection = rangeCorrection * this.valCorrectionDose;
        return rangeCorrection;
    }

    public static double roundToHalf(double d) {
        return Math.round(d * 2) / 2.0;
    }

    // this function adjusts for correction ranges
    // e.g. 5-7 = 0 correction, 7-9 = 1, etc
    public double adjustForRange(double num) {
        return Math.floor(num*2)/2;
    }

    public void runCalculation() {
        if((opr1.getText().length()>0) && (opr2.getText().length()>0))
        {
            double oper1 = Double.parseDouble(opr1.getText().toString());
            double oper2 = Double.parseDouble(opr2.getText().toString());
            valInsulinPerCarbs = Float.parseFloat(spinner.getSelectedItem().toString());
            double result = valInsulinPerCarbs *  Math.round(oper2/10);
            double rangeCorrection = calculateRangeCorrection(oper1);
            double roundedToHalf = roundToHalf(result + rangeCorrection);
            txtresult.setText(Double.toString(roundedToHalf));

            Calculation calculation = new Calculation();
            calculation.setCalculation(opr1.getText().toString(), opr2.getText().toString(), Double.toString(result + rangeCorrection ));
            
            mCalculationViewModel.insert(calculation);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        opr1 = (EditText) findViewById(R.id.editOp1);
        opr2 = (EditText) findViewById(R.id.editOp2);
        txtresult= (TextView) findViewById(R.id.result);

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        final CalculationListAdapter adapter = new CalculationListAdapter(new CalculationListAdapter.CalculationDiff());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mCalculationViewModel = new ViewModelProvider(this).get(CalculationViewModel.class);

        mCalculationViewModel.getAllCalculations().observe(this, calculations -> {
            // Update the cached copy of the calculations in the adapter.
            adapter.submitList(calculations);
        });

        // add dropdown contents
        spinner = (Spinner) findViewById(R.id.spinner);
        // Create an ArrayAdapter using the string array and a default spinne   r layout
        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(this, R.array.adjustmentdose_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(arrayAdapter);

        // calculate
        opr2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                runCalculation();
            }

            @Override
            public void afterTextChanged(Editable s) { }
        });

        opr1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                runCalculation();
            }

            @Override
            public void afterTextChanged(Editable s) { }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                runCalculation();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

    }
}