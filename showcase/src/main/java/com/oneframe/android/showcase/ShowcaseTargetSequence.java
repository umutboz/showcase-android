package com.oneframe.android.showcase;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.UiThread;
import android.text.TextUtils;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Queue;

/**
 * Displays a sequence of {@link ShowcaseTargetView}s.
 *
 * <p>Internally, a FIFO queue is held to dictate which {@link ShowcaseTarget} will be shown.
 */
public class ShowcaseTargetSequence {

  private final @Nullable Activity activity;
  private final @Nullable Dialog dialog;
  private final Queue<ShowcaseTarget> targets;
  private Listener listener;
  private boolean considerOuterCircleCanceled;
  private boolean continueOnCancel;
  private boolean active;
  private @Nullable String key;

  @Nullable private ShowcaseTargetView currentView;
  private final ShowcaseTargetView.Listener tapTargetListener =
      new ShowcaseTargetView.Listener() {
        @Override
        public void onTargetClick(ShowcaseTargetView view) {
          super.onTargetClick(view);
          if (listener != null) {
            listener.onSequenceStep(view.target, true);
          }
          showNext();
        }

        @Override
        public void onOuterCircleClick(ShowcaseTargetView view) {
          if (considerOuterCircleCanceled) {
            onTargetCancel(view);
          }
        }

        @Override
        public void onTargetCancel(ShowcaseTargetView view) {
          super.onTargetCancel(view);
          if (continueOnCancel) {
            if (listener != null) {
              listener.onSequenceStep(view.target, false);
            }
            showNext();
          } else {
            if (listener != null) {
              listener.onSequenceCanceled(view.target);
            }
          }
        }
      };

  @SuppressWarnings("ConstantConditions")
  public ShowcaseTargetSequence(@NonNull Activity activity) {
    if (activity == null) throw new IllegalArgumentException("Activity is null");
    this.activity = activity;
    this.dialog = null;
    this.targets = new LinkedList<>();
  }

  @SuppressWarnings("ConstantConditions")
  public ShowcaseTargetSequence(@NonNull Dialog dialog) {
    if (dialog == null) throw new IllegalArgumentException("Given null Dialog");
    this.dialog = dialog;
    this.activity = null;
    this.targets = new LinkedList<>();
  }

  /** Adds the given targets, in order, to the pending queue of {@link ShowcaseTarget}s */
  public ShowcaseTargetSequence targets(List<ShowcaseTarget> targets) {
    this.targets.addAll(targets);
    return this;
  }

  /** Adds the given targets, in order, to the pending queue of {@link ShowcaseTarget}s */
  public ShowcaseTargetSequence targets(ShowcaseTarget... targets) {
    Collections.addAll(this.targets, targets);
    return this;
  }

  /** Adds the given target to the pending queue of {@link ShowcaseTarget}s */
  public ShowcaseTargetSequence target(ShowcaseTarget target) {
    this.targets.add(target);
    return this;
  }

  /** Whether or not to continue the sequence when a {@link ShowcaseTarget} is canceled * */
  public ShowcaseTargetSequence continueOnCancel(boolean status) {
    this.continueOnCancel = status;
    return this;
  }

  /** Whether or not to consider taps on the outer circle as a cancellation * */
  public ShowcaseTargetSequence considerOuterCircleCanceled(boolean status) {
    this.considerOuterCircleCanceled = status;
    return this;
  }

  /** Makes unique for this sequence * */
  public ShowcaseTargetSequence key(@Nullable String key) {
    this.key = key;
    return this;
  }

  /** Specify the listener for this sequence * */
  public ShowcaseTargetSequence listener(Listener listener) {
    this.listener = listener;
    return this;
  }

  /** Immediately starts the sequence and displays the first target from the queue * */
  @UiThread
  public void start() {
    if (targets.isEmpty() || active) {
      return;
    }

    if (isShown()) {
      return;
    }

    active = true;
    showNext();
  }

  /** Immediately starts the sequence from the given targetId's position in the queue */
  public void startWith(int targetId) {
    if (active) {
      return;
    }

    if (isShown()) {
      return;
    }

    while (targets.peek() != null && targets.peek().id() != targetId) {
      targets.poll();
    }

    ShowcaseTarget peekedTarget = targets.peek();
    if (peekedTarget == null || peekedTarget.id() != targetId) {
      throw new IllegalStateException("Given target " + targetId + " not in sequence");
    }

    start();
  }

  /** Immediately starts the sequence at the specified zero-based index in the queue */
  public void startAt(@IntRange(from = 0) int index) {
    if (active) {
      return;
    }

    if (isShown()) {
      return;
    }

    if (index < 0 || index >= targets.size()) {
      throw new IllegalArgumentException("Given invalid index " + index);
    }

    final int expectedSize = targets.size() - index;
    while (targets.peek() != null && targets.size() != expectedSize) {
      targets.poll();
    }

    if (targets.size() != expectedSize) {
      throw new IllegalStateException("Given index " + index + " not in sequence");
    }

    start();
  }

  /**
   * Cancels the sequence, if the current target is cancelable. When the sequence is canceled, the
   * current target is dismissed and the remaining targets are removed from the sequence.
   *
   * @return whether the sequence was canceled or not
   */
  @UiThread
  public boolean cancel() {
    if (!active || currentView == null || !currentView.cancelable) {
      return false;
    }
    currentView.dismiss(false);
    active = false;
    targets.clear();
    if (listener != null) {
      listener.onSequenceCanceled(currentView.target);
    }
    return true;
  }

  private void showNext() {
    try {
      ShowcaseTarget tapTarget = targets.remove();
      if (activity != null) {
        currentView = ShowcaseTargetView.showFor(activity, tapTarget, null, tapTargetListener);
      } else {
        currentView = ShowcaseTargetView.showFor(dialog, tapTarget, null, tapTargetListener);
      }
    } catch (NoSuchElementException e) {
      currentView = null;
      shown();
      // No more targets
      if (listener != null) {
        listener.onSequenceFinish();
      }
    }
  }

  private void shown() {
    if (!TextUtils.isEmpty(key)) {
      return;
    }

    Context context = getContext();
    if (context != null) {
      KeyUtil.save(context, key);
    }
  }

  private boolean isShown() {
    if (TextUtils.isEmpty(key)) {
      return false;
    }

    Context context = getContext();
    if (context != null) {
      return KeyUtil.exists(context, key);
    }

    return false;
  }

  @Nullable
  private Context getContext() {
    Context context = null;

    if (activity != null) {
      context = activity.getBaseContext();
    } else if (dialog != null) {
      context = dialog.getContext();
    }

    return context;
  }

  public interface Listener {
    /** Called when there are no more tap targets to display */
    void onSequenceFinish();

    /**
     * Called when moving onto the next tap target.
     *
     * @param lastTarget The last displayed target
     * @param targetClicked Whether the last displayed target was clicked (this will always be true
     *     unless you have set {@link #continueOnCancel(boolean)} and the user clicks outside of the
     *     target
     */
    void onSequenceStep(ShowcaseTarget lastTarget, boolean targetClicked);

    /**
     * Called when the user taps outside of the current target, the target is cancelable, and {@link
     * #continueOnCancel(boolean)} is not set.
     *
     * @param lastTarget The last displayed target
     */
    void onSequenceCanceled(ShowcaseTarget lastTarget);
  }
}
