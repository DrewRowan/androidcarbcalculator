package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private EditText opr1;
    private EditText opr2;
    private Button btnadd;
    private Button btnsub;
    private Button btnmul;
    private Button btncalculate;
    private Button btnclr;
    private TextView txtresult;
    private float valInsulinPerCarbs = 0.5F;
    private int valCorrectionDose = 1;

    public double calculateRangeCorrection(double currentBloodGlucose) {
        // adjust for range e.g. 5-7 = 0 correction, 7-9 = 1, etc
        double rangeCorrection = adjustForRange((currentBloodGlucose - 5) / 2);
        if (rangeCorrection < 0) {
            rangeCorrection = 0;
        }
        rangeCorrection = rangeCorrection * this.valCorrectionDose;
        return rangeCorrection;
    }

    // this function adjusts for correction ranges
    // e.g. 5-7 = 0 correction, 7-9 = 1, etc
    public double adjustForRange(double num) {
        return Math.floor(num*2)/2;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        opr1 = (EditText) findViewById(R.id.editOp1);
        opr2 = (EditText) findViewById(R.id.editOp2);
        btncalculate = (Button) findViewById(R.id.btncalculate);
        btnclr = (Button) findViewById(R.id.btnclr);
        txtresult= (TextView) findViewById(R.id.result);
        // calculate
        btncalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if((opr1.getText().length()>0) && (opr2.getText().length()>0))
                {
                    double oper1 = Double.parseDouble(opr1.getText().toString());
                    double oper2 = Double.parseDouble(opr2.getText().toString());
                    double result = valInsulinPerCarbs *  Math.round(oper2/10);
                    double rangeCorrection = calculateRangeCorrection(oper1);
                    txtresult.setText(Double.toString(result + rangeCorrection ));
                }
                else{
                    Toast toast= Toast.makeText(MainActivity.this,"Enter The Required Numbers",Toast.LENGTH_LONG);
                    toast.show();
                }
            }
        });
// Reset Fields
        btnclr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                opr1.setText("");
                opr2.setText("");
                txtresult.setText("0.00");
                opr1.requestFocus();
            }
        });

    }
}