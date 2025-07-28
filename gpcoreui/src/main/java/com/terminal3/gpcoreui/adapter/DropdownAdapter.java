package com.terminal3.gpcoreui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.terminal3.gpcoreui.R;
import com.terminal3.gpcoreui.models.DropdownItem;

import java.util.List;

public class DropdownAdapter extends RecyclerView.Adapter<DropdownAdapter.ViewHolder> {

    private final List<DropdownItem> items;
    private final OnItemClickListener listener;

    public DropdownAdapter(List<DropdownItem> items, OnItemClickListener listener) {
        this.items = items;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_dropdown, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DropdownItem item = items.get(position);
        holder.bind(item, listener);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView icon;
        private final TextView text;

        ViewHolder(View itemView) {
            super(itemView);
            icon = itemView.findViewById(R.id.item_icon);
            text = itemView.findViewById(R.id.item_text);
        }

        void bind(DropdownItem item, OnItemClickListener listener) {
            // Handle icon visibility and content
            if (item.getIconResId() == -1 && !item.getPhotoUrl().isEmpty()) {
                // Show photo using Glide
                icon.setVisibility(View.VISIBLE);
                Glide.with(itemView.getContext())
                        .load(item.getPhotoUrl())
//                        .circleCrop() // Optional: if you want circular images
//                        .placeholder(R.drawable.ic_background_base) // Add a default placeholder
//                        .error(R.drawable.error_placeholder) // Add an error placeholder
                        .into(icon);
            } else if (item.getIconResId() > 0) {
                // Show icon resource
                icon.setVisibility(View.VISIBLE);
                icon.setImageResource(item.getIconResId());
            } else {
                // No icon or photo
                icon.setVisibility(View.GONE);
            }

            text.setText(item.getText());

            itemView.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onItemClick(item);
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(DropdownItem item);
    }
}