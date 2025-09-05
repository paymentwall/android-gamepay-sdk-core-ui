package com.terminal3.gpcoreui.utils.transformation;
import android.graphics.*;
import androidx.annotation.NonNull;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import java.security.MessageDigest;

public class GPRoundedCornersWithBorderTransformation extends BitmapTransformation {
    private static final String ID = "com.terminal3.gpcoreui.utils.transformation.GPRoundedCornersWithBorderTransformation";
    private static final byte[] ID_BYTES = ID.getBytes(CHARSET);

    private final int cornerRadius;
    private final int borderWidth;
    private final int borderColor;

    public GPRoundedCornersWithBorderTransformation(int cornerRadius, int borderWidth, int borderColor) {
        this.cornerRadius = cornerRadius;
        this.borderWidth = borderWidth;
        this.borderColor = borderColor;
    }

    @Override
    protected Bitmap transform(@NonNull BitmapPool pool, @NonNull Bitmap source, int outWidth, int outHeight) {
        int width = source.getWidth();
        int height = source.getHeight();

        Bitmap result = pool.get(width, height, Bitmap.Config.ARGB_8888);
        if (result == null) {
            result = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(result);
        Paint paint = new Paint();
        paint.setAntiAlias(true);

        // Draw rounded image
        RectF rect = new RectF(borderWidth, borderWidth, width - borderWidth, height - borderWidth);
        canvas.drawRoundRect(rect, cornerRadius, cornerRadius, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(source, borderWidth, borderWidth, paint);

        // Draw border
        Paint borderPaint = new Paint();
        borderPaint.setAntiAlias(true);
        borderPaint.setStyle(Paint.Style.STROKE);
        borderPaint.setColor(borderColor);
        borderPaint.setStrokeWidth(borderWidth);

        RectF borderRect = new RectF(borderWidth/2f, borderWidth/2f, width - borderWidth/2f, height - borderWidth/2f);
        canvas.drawRoundRect(borderRect, cornerRadius, cornerRadius, borderPaint);

        return result;
    }

    @Override
    public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {
        messageDigest.update(ID_BYTES);
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof GPRoundedCornersWithBorderTransformation;
    }

    @Override
    public int hashCode() {
        return ID.hashCode();
    }
}
