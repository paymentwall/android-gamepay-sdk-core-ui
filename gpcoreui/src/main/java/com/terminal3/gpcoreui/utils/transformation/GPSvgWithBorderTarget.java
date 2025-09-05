package com.terminal3.gpcoreui.utils.transformation;
import android.graphics.*;
import android.graphics.drawable.Drawable;
import com.bumptech.glide.request.transition.Transition;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.request.target.CustomTarget;
import com.caverock.androidsvg.SVG;
import com.caverock.androidsvg.SVGParseException;
import java.io.InputStream;

public class GPSvgWithBorderTarget extends CustomTarget<SVG> {
    private final ImageView imageView;
    private final int cornerRadius;
    private final int borderWidth;
    private final int borderColor;

    public GPSvgWithBorderTarget(ImageView imageView, int cornerRadius, int borderWidth, int borderColor) {
        this.imageView = imageView;
        this.cornerRadius = cornerRadius;
        this.borderWidth = borderWidth;
        this.borderColor = borderColor;
    }


    @Override
    public void onResourceReady(@NonNull SVG resource, @Nullable Transition<? super SVG> transition) {
        try {
            // Create a bitmap from SVG
            Bitmap bitmap = Bitmap.createBitmap(
                    imageView.getWidth() > 0 ? imageView.getWidth() : 100,
                    imageView.getHeight() > 0 ? imageView.getHeight() : 100,
                    Bitmap.Config.ARGB_8888
            );

            Canvas canvas = new Canvas(bitmap);
            resource.renderToCanvas(canvas);

            // Apply rounded corners and border
            Bitmap finalBitmap = applyRoundedCornersWithBorder(bitmap);
            imageView.setImageBitmap(finalBitmap);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Bitmap applyRoundedCornersWithBorder(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        Bitmap output = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        Paint paint = new Paint();
        paint.setAntiAlias(true);

        // Draw rounded rectangle
        RectF rect = new RectF(borderWidth, borderWidth, width - borderWidth, height - borderWidth);
        canvas.drawRoundRect(rect, cornerRadius, cornerRadius, paint);

        // Draw the bitmap inside the rounded rectangle
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, borderWidth, borderWidth, paint);

        // Draw border
        Paint borderPaint = new Paint();
        borderPaint.setAntiAlias(true);
        borderPaint.setStyle(Paint.Style.STROKE);
        borderPaint.setColor(borderColor);
        borderPaint.setStrokeWidth(borderWidth);

        RectF borderRect = new RectF(borderWidth/2f, borderWidth/2f, width - borderWidth/2f, height - borderWidth/2f);
        canvas.drawRoundRect(borderRect, cornerRadius, cornerRadius, borderPaint);

        return output;
    }

    @Override
    public void onLoadCleared(@Nullable Drawable placeholder) {
        imageView.setImageDrawable(placeholder);
    }
}
