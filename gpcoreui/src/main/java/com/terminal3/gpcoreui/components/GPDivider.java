package com.terminal3.gpcoreui.components;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.terminal3.gpcoreui.R;

public class GPDivider extends LinearLayout {

    public GPDivider(Context context) {
        super(context);
        init(context);
    }

    public GPDivider(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public GPDivider(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        setOrientation(LinearLayout.HORIZONTAL);
        
        View dividerLine = new View(context);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                (int) (1 * getResources().getDisplayMetrics().density) // 1dp converted to px
        );
        dividerLine.setLayoutParams(params);
        dividerLine.setBackground(ContextCompat.getDrawable(context, R.color.gp_border_subtle));
        
        addView(dividerLine);
    }
}