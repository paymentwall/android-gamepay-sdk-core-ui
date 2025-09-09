package com.terminal3.gpcoreui.adapter;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou;
import com.terminal3.gpcoreui.R;
import com.terminal3.gpcoreui.models.DropdownItem;
import com.terminal3.gpcoreui.utils.transformation.GPRoundedCornersWithBorderTransformation;
import com.terminal3.gpcoreui.utils.transformation.GPSvgWithBorderTarget;
import com.terminal3.gpcoreui.utils.transformation.SvgLoaderWithBorder;

import java.util.List;

public class DropdownAdapter extends RecyclerView.Adapter<DropdownAdapter.ViewHolder> {

    private List<DropdownItem> items;
    private final OnItemClickListener listener;

    public DropdownAdapter(List<DropdownItem> items, OnItemClickListener listener) {
        this.items = items;
        this.listener = listener;
    }

    public void updateItems(List<DropdownItem> newItems) {
        this.items = newItems;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.gp_item_dropdown, parent, false);
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

        int cornerRadius = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                4,
                itemView.getContext().getResources().getDisplayMetrics()
        );

        int borderWidth = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                0,
                itemView.getContext().getResources().getDisplayMetrics()
        );

        int borderColor = R.color.gp_border_primary;

        ViewHolder(View itemView) {
            super(itemView);
            icon = itemView.findViewById(R.id.item_icon);
            text = itemView.findViewById(R.id.item_text);
        }

        void bind(DropdownItem item, OnItemClickListener listener) {
            // Handle icon visibility and content
            if (item.getIconResId() == -1 && !item.getPhotoUrl().isEmpty()) {
                // Show photo using appropriate loader based on file extension
                icon.setVisibility(View.VISIBLE);
                
                if (item.getPhotoUrl().toLowerCase().endsWith(".svg")) {
                    // Use SvgLoaderWithBorder for SVG files
                    SvgLoaderWithBorder.loadSvgWithBorder(
                            itemView.getContext(),
                            item.getPhotoUrl(),
                            new SvgLoaderWithBorder.SvgLoadCallback() {
                                @Override
                                public void onSuccess(Drawable drawable) {
                                    icon.setImageDrawable(drawable);
                                }

                                @Override
                                public void onError(Throwable throwable) {
                                    throwable.printStackTrace();
                                }
                            }
                    );
                } else {
                    // Use Glide for other image formats (PNG, JPG, etc.)
                    Glide.with(itemView.getContext())
                            .load(item.getPhotoUrl())
                            .transform(new GPRoundedCornersWithBorderTransformation(cornerRadius, borderWidth, itemView.getContext().getColor(borderColor)))
                            .into(icon);
                }

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
                    listener.onItemClick(item, icon.getDrawable());
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(DropdownItem item, @Nullable Drawable itemDrawable);
    }
}