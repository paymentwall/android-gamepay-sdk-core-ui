package com.terminal3.gpcoreui.views;

import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

public class PollingAnimatedProcessingView extends View {

    private static final float CONTAINER_SIZE_DP = 96f;
    private static final float CIRCLE_RADIUS_DP = 36f;
    private static final float STROKE_WIDTH_DP = 8f;
    private static final float CHEVRON_SIZE_DP = 10f;

    private float containerSize;
    private float circleRadius;
    private float strokeWidth;
    private float chevronSize;

    private Paint circlePaint;
    private Paint chevronPaint;
    private Path chevronPath;

    private float[] chevronPositions = {26.4473f, 41.6523f, 56.8574f};
    private float[] chevronOpacities = {0.2f, 0.2f, 0.2f};

    private AnimatorSet animatorSet;
    private boolean isAnimating = false;

    public PollingAnimatedProcessingView(Context context) {
        super(context);
        init();
    }

    public PollingAnimatedProcessingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PollingAnimatedProcessingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        // Convert DP to pixels
        float density = getResources().getDisplayMetrics().density;
        containerSize = CONTAINER_SIZE_DP * density;
        circleRadius = CIRCLE_RADIUS_DP * density;
        strokeWidth = STROKE_WIDTH_DP * density;
        chevronSize = CHEVRON_SIZE_DP * density;

        // Convert chevron positions to pixels
        for (int i = 0; i < chevronPositions.length; i++) {
            chevronPositions[i] = chevronPositions[i] * density;
        }

        setupPaints();
        chevronPath = new Path();
    }

    private void setupPaints() {
        // Circle paint
        circlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        circlePaint.setStyle(Paint.Style.STROKE);
        circlePaint.setStrokeWidth(strokeWidth);
        circlePaint.setStrokeCap(Paint.Cap.ROUND);
        circlePaint.setColor(Color.argb(255, 6, 11, 20)); // RGB(0.024, 0.043, 0.078)

        // Chevron paint
        chevronPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        chevronPaint.setStyle(Paint.Style.STROKE);
        chevronPaint.setStrokeWidth(strokeWidth);
        chevronPaint.setColor(Color.argb(255, 6, 11, 20)); // RGB(0.024, 0.043, 0.078)
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int size = (int) containerSize;
        setMeasuredDimension(size, size);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float centerX = containerSize / 2f;
        float centerY = containerSize / 2f;

        // Draw circle
        canvas.drawCircle(centerX, centerY, circleRadius, circlePaint);

        // Draw chevrons
        for (int i = 0; i < chevronPositions.length; i++) {
            drawChevron(canvas, chevronPositions[i], centerY, chevronOpacities[i]);
        }
    }

    private void drawChevron(Canvas canvas, float x, float y, float opacity) {
        chevronPath.reset();

        // Create chevron path (right-pointing arrow)
        chevronPath.moveTo(x, y - chevronSize);
        chevronPath.lineTo(x + chevronSize, y);
        chevronPath.lineTo(x, y + chevronSize);

        // Set opacity
        int alpha = (int) (opacity * 255);
        chevronPaint.setAlpha(alpha);

        canvas.drawPath(chevronPath, chevronPaint);
    }

    public void startAnimation() {
        if (isAnimating) {
            stopAnimation();
        }

        isAnimating = true;
        animatorSet = new AnimatorSet();

        long animationDuration = 1500; // 1.5 seconds
        long[] delays = {0, 300, 600}; // 0.0, 0.3, 0.6 seconds

        ValueAnimator[] animators = new ValueAnimator[chevronPositions.length];

        for (int i = 0; i < chevronPositions.length; i++) {
            final int index = i;

            ValueAnimator opacityAnimator = ValueAnimator.ofFloat(0.2f, 1.0f, 0.2f);
            opacityAnimator.setDuration(animationDuration);
            opacityAnimator.setStartDelay(delays[i]);
            opacityAnimator.setRepeatCount(ValueAnimator.INFINITE);
            opacityAnimator.setInterpolator(new AccelerateDecelerateInterpolator());

            opacityAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    chevronOpacities[index] = (Float) animation.getAnimatedValue();
                    invalidate();
                }
            });

            animators[i] = opacityAnimator;
        }

        animatorSet.playTogether(animators);
        animatorSet.start();
    }

    public void stopAnimation() {
        if (animatorSet != null) {
            animatorSet.cancel();
            animatorSet = null;
        }

        // Reset opacities to default
        for (int i = 0; i < chevronOpacities.length; i++) {
            chevronOpacities[i] = 0.2f;
        }

        isAnimating = false;
        invalidate();
    }

    public boolean isAnimating() {
        return isAnimating;
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        stopAnimation();
    }
}
