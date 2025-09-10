package com.oneframe.android.showcase;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;

/** A small wrapper around {@link ValueAnimator} to provide a builder-like interface */
class FloatValueAnimatorBuilder {

  private final ValueAnimator animator;
  private EndListener endListener;

  FloatValueAnimatorBuilder() {
    this(false);
  }

  FloatValueAnimatorBuilder(boolean reverse) {
    if (reverse) {
      this.animator = ValueAnimator.ofFloat(1.0f, 0.0f);
    } else {
      this.animator = ValueAnimator.ofFloat(0.0f, 1.0f);
    }
  }

  FloatValueAnimatorBuilder delayBy(long millis) {
    animator.setStartDelay(millis);
    return this;
  }

  FloatValueAnimatorBuilder duration(long millis) {
    animator.setDuration(millis);
    return this;
  }

  FloatValueAnimatorBuilder interpolator(TimeInterpolator lerper) {
    animator.setInterpolator(lerper);
    return this;
  }

  FloatValueAnimatorBuilder repeat(int times) {
    animator.setRepeatCount(times);
    return this;
  }

  FloatValueAnimatorBuilder onUpdate(final UpdateListener listener) {
    animator.addUpdateListener(
        new ValueAnimator.AnimatorUpdateListener() {
          @Override
          public void onAnimationUpdate(ValueAnimator animation) {
            listener.onUpdate((float) animation.getAnimatedValue());
          }
        });
    return this;
  }

  FloatValueAnimatorBuilder onEnd(final EndListener listener) {
    this.endListener = listener;
    return this;
  }

  ValueAnimator build() {
    if (endListener != null) {
      animator.addListener(
          new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
              endListener.onEnd();
            }
          });
    }

    return animator;
  }

  interface UpdateListener {
    void onUpdate(float lerpTime);
  }

  interface EndListener {
    void onEnd();
  }
}
