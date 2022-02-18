package com.example.calculator;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

class CalculationViewHolder extends RecyclerView.ViewHolder {
    private final TextView calculationItemView;

    private CalculationViewHolder(View itemView) {
        super(itemView);
        calculationItemView = itemView.findViewById(R.id.textView);
    }

    public void bind(String text) {
        calculationItemView.setText(text);
    }

    static CalculationViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_item, parent, false);
        return new CalculationViewHolder(view);
    }
}