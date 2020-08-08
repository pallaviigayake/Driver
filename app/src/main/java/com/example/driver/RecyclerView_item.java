package com.example.driver;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerView_item extends RecyclerView.Adapter<RecyclerView_item.Viewholder_item> {

    @NonNull
    @Override
    public Viewholder_item onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder_item holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class Viewholder_item extends RecyclerView.ViewHolder {
        public Viewholder_item(@NonNull View itemView) {
            super(itemView);
        }
    }
}
