package com.terminal3.gpcoreui.utils;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;

public class GPViewAnimationHelper {

    public interface AnimationListener {
        void onAnimationStart();
        void onAnimationEnd();
    }

    /**
     * Expands a view with height animation
     */
    public static void expandView(final View view, int duration, final AnimationListener listener) {
        if (view.getVisibility() == View.VISIBLE) {
            return;
        }

        // Measure the view's original height
        view.measure(
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
        );
        final int originalHeight = view.getMeasuredHeight();

        // Set initial height to 0
        view.getLayoutParams().height = 0;
        view.setVisibility(View.VISIBLE);

        if (listener != null) {
            listener.onAnimationStart();
        }

        ValueAnimator animator = ValueAnimator.ofInt(0, originalHeight);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int val = (Integer) valueAnimator.getAnimatedValue();
                ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
                layoutParams.height = val;
                view.setLayoutParams(layoutParams);
            }
        });
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                // Restore original height for proper layout
                ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
                layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                view.setLayoutParams(layoutParams);

                if (listener != null) {
                    listener.onAnimationEnd();
                }
            }
        });
        animator.setDuration(duration);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.start();
    }

    /**
     * Collapses a view with height animation
     */
    public static void collapseView(final View view, int duration, final AnimationListener listener) {
        if (view.getVisibility() == View.GONE) {
            return;
        }

        final int originalHeight = view.getMeasuredHeight();

        if (listener != null) {
            listener.onAnimationStart();
        }

        ValueAnimator animator = ValueAnimator.ofInt(originalHeight, 0);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int val = (Integer) valueAnimator.getAnimatedValue();
                ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
                layoutParams.height = val;
                view.setLayoutParams(layoutParams);
            }
        });
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                view.setVisibility(View.GONE);
                // Restore original height for next expansion
                ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
                layoutParams.height = originalHeight;
                view.setLayoutParams(layoutParams);

                if (listener != null) {
                    listener.onAnimationEnd();
                }
            }
        });
        animator.setDuration(duration);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.start();
    }

    /**
     * Quick expand with default duration (300ms)
     */
    public static void expandView(View view) {
        expandView(view, 300, null);
    }

    /**
     * Quick collapse with default duration (300ms)
     */
    public static void collapseView(View view) {
        collapseView(view, 300, null);
    }
}
