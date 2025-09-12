package com.terminal3.gpcoreui.utils.transformation;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.TypedValue;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.caverock.androidsvg.SVG;
import com.terminal3.gpcoreui.R;

public class SvgLoaderWithBorder {

    public interface SvgLoadCallback {
        void onSuccess(Drawable drawable);
        void onError(Throwable throwable);
    }

    // Default target size in dp
    public static final float TARGET_SIZE_DP = 96f;
    private static final int borderColor = R.color.gp_border_subtle;

    public static void loadSvgWithBorder(
            Context context,
            String url,
            SvgLoadCallback callback,
            float cornerRadiusDp,
            float borderWidthDp
    ) {
        // Convert all dp values to pixels
        float targetSizePx = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                TARGET_SIZE_DP,
                context.getResources().getDisplayMetrics()
        );

        float cornerRadiusPx = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                cornerRadiusDp,
                context.getResources().getDisplayMetrics()
        );

        float borderWidthPx = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                borderWidthDp,
                context.getResources().getDisplayMetrics()
        );

        Glide.with(context)
                .as(SVG.class)
                .load(url)
                .into(new CustomTarget<SVG>() {
                    @Override
                    public void onResourceReady(@NonNull SVG svg, @Nullable Transition<? super SVG> transition) {
                        try {
                            // Scale down the SVG to target size before processing
                            Bitmap scaledBitmap = createScaledBitmapFromSvg(svg, targetSizePx);

                            // Apply corner radius and border to the scaled bitmap
                            Drawable resultDrawable = createRoundedBorderDrawable(
                                    context,
                                    scaledBitmap,
                                    cornerRadiusPx,
                                    borderWidthPx
                            );

                            callback.onSuccess(resultDrawable);
                        } catch (Exception e) {
                            callback.onError(e);
                        }
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {
                        // Not needed for this implementation
                    }

                    @Override
                    public void onLoadFailed(@Nullable Drawable errorDrawable) {
                        callback.onError(new RuntimeException("Failed to load SVG"));
                    }
                });
    }

    // Overload with default values
    public static void loadSvgWithBorder(
            Context context,
            String url,
            SvgLoadCallback callback
    ) {
        loadSvgWithBorder(context, url, callback, 8f, 4f);
    }

    private static Bitmap createScaledBitmapFromSvg(SVG svg, float targetSizePx) {
        // Get original SVG dimensions
        float originalWidth = svg.getDocumentWidth();
        float originalHeight = svg.getDocumentHeight();

        // Use defaults if dimensions not specified
        if (originalWidth <= 0) originalWidth = 100f;
        if (originalHeight <= 0) originalHeight = 100f;

        // Calculate scale factor to fit within target size while maintaining aspect ratio
        float scaleFactor = Math.min(
                targetSizePx / originalWidth,
                targetSizePx / originalHeight
        );

        int scaledWidth = (int) (originalWidth * scaleFactor);
        int scaledHeight = (int) (originalHeight * scaleFactor);

        // Create scaled bitmap
        Bitmap bitmap = Bitmap.createBitmap(
                scaledWidth,
                scaledHeight,
                Bitmap.Config.ARGB_8888
        );

        // Render SVG to bitmap with scaling
        Canvas canvas = new Canvas(bitmap);

        // Apply scaling transformation
        canvas.scale(scaleFactor, scaleFactor);
        svg.renderToCanvas(canvas);

        return bitmap;
    }

    private static Drawable createRoundedBorderDrawable(
            Context context,
            Bitmap originalBitmap,
            float cornerRadiusPx,
            float borderWidthPx
    ) {
        // Calculate dimensions for the final bitmap with border
        int borderedWidth = originalBitmap.getWidth() + (int) (borderWidthPx * 2);
        int borderedHeight = originalBitmap.getHeight() + (int) (borderWidthPx * 2);

        // Create final bitmap with border
        Bitmap finalBitmap = Bitmap.createBitmap(
                borderedWidth,
                borderedHeight,
                Bitmap.Config.ARGB_8888
        );

        Canvas canvas = new Canvas(finalBitmap);

        // Draw black border (background) - this should be larger than the image
        Paint borderPaint = new Paint();
        borderPaint.setColor(context.getColor(borderColor));
        borderPaint.setStyle(Paint.Style.FILL);
        borderPaint.setAntiAlias(true);

        // Draw the border covering the entire bitmap
        RectF borderRect = new RectF(
                0,
                0,
                borderedWidth,
                borderedHeight
        );

        // Use larger corner radius for the border to match the inner image
        float borderCornerRadius = cornerRadiusPx + borderWidthPx;
        canvas.drawRoundRect(borderRect, borderCornerRadius, borderCornerRadius, borderPaint);

        // Create rounded version of the original bitmap
        Bitmap roundedBitmap = getRoundedCornerBitmap(originalBitmap, cornerRadiusPx);

        // Create a mask for the inner part (where the image will go)
        Paint clearPaint = new Paint();
        clearPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        clearPaint.setAntiAlias(true);

        // Cut out the inner part to create the border effect
        RectF innerRect = new RectF(
                borderWidthPx,
                borderWidthPx,
                borderedWidth - borderWidthPx,
                borderedHeight - borderWidthPx
        );
        canvas.drawRoundRect(innerRect, cornerRadiusPx, cornerRadiusPx, clearPaint);

        // Draw the rounded image in the cut-out area
        canvas.drawBitmap(
                roundedBitmap,
                borderWidthPx,
                borderWidthPx,
                null
        );

        return new BitmapDrawable(context.getResources(), finalBitmap);
    }

    private static Bitmap getRoundedCornerBitmap(Bitmap bitmap, float cornerRadiusPx) {
        Bitmap output = Bitmap.createBitmap(
                bitmap.getWidth(),
                bitmap.getHeight(),
                Bitmap.Config.ARGB_8888
        );

        Canvas canvas = new Canvas(output);

        Paint paint = new Paint();
        paint.setAntiAlias(true);

        Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        RectF rectF = new RectF(rect);

        // Draw rounded rectangle as mask
        canvas.drawRoundRect(rectF, cornerRadiusPx, cornerRadiusPx, paint);

        // Use PorterDuff to apply the rounded corners to the original bitmap
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        return output;
    }

    // Alternative simpler approach without using CLEAR mode
    private static Drawable createRoundedBorderDrawableAlternative(
            Context context,
            Bitmap originalBitmap,
            float cornerRadiusPx,
            float borderWidthPx
    ) {
        int borderedWidth = originalBitmap.getWidth() + (int) (borderWidthPx * 2);
        int borderedHeight = originalBitmap.getHeight() + (int) (borderWidthPx * 2);

        Bitmap finalBitmap = Bitmap.createBitmap(borderedWidth, borderedHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(finalBitmap);

        // Draw border first
        Paint borderPaint = new Paint();
        borderPaint.setColor(Color.BLACK);
        borderPaint.setStyle(Paint.Style.FILL);
        borderPaint.setAntiAlias(true);

        RectF borderRect = new RectF(0, 0, borderedWidth, borderedHeight);
        float borderCornerRadius = cornerRadiusPx + borderWidthPx;
        canvas.drawRoundRect(borderRect, borderCornerRadius, borderCornerRadius, borderPaint);

        // Draw the rounded image on top (this will cover the center part of the border)
        Bitmap roundedBitmap = getRoundedCornerBitmap(originalBitmap, cornerRadiusPx);
        canvas.drawBitmap(roundedBitmap, borderWidthPx, borderWidthPx, null);

        return new BitmapDrawable(context.getResources(), finalBitmap);
    }
}
