package com.example.calculator;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

class CalculationListAdapter extends ListAdapter<Calculation, CalculationViewHolder> {

    protected CalculationListAdapter(@NonNull DiffUtil.ItemCallback<Calculation> diffCallback) {
        super(diffCallback);
    }

    @Override
    public CalculationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return CalculationViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(CalculationViewHolder holder, int position) {
        Calculation current = getItem(position);
        holder.bind(current.getCalculation());
    }

    static class CalculationDiff extends DiffUtil.ItemCallback<Calculation> {

        @Override
        public boolean areItemsTheSame(@NonNull Calculation oldItem, @NonNull Calculation newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Calculation oldItem, @NonNull Calculation newItem) {
            return oldItem.getCalculation().equals(newItem.getCalculation());
        }
    }
}