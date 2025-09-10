package com.oneframe.android.showcase;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.DimenRes;
import androidx.annotation.FloatRange;
import androidx.annotation.IdRes;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.appcompat.widget.Toolbar;
import android.view.View;

/**
 * Describes the properties and options for a {@link ShowcaseTargetView}.
 *
 * <p>Each tap target describes a target via a pair of bounds and icon. The bounds dictate the
 * location and touch area of the target, where the icon is what will be drawn within the center of
 * the bounds.
 *
 * <p>This class can be extended to support various target types.
 *
 * @see ViewShowcaseTarget ViewShowcaseTarget for targeting standard Android views
 */
public class ShowcaseTarget {

  final CharSequence title;
  @Nullable final CharSequence description;

  float outerCircleAlpha = 0.96f;
  int targetRadius = 44;

  Rect bounds;
  Drawable icon;
  Typeface titleTypeface;
  Typeface descriptionTypeface;
  int id = -1;
  boolean drawShadow = false;
  boolean cancelable = true;
  boolean tintTarget = true;
  boolean transparentTarget = false;
  float descriptionTextAlpha = 0.54f;
  @ColorRes private int outerCircleColorRes = -1;
  @ColorRes private int targetCircleColorRes = -1;
  @ColorRes private int dimColorRes = -1;
  @ColorRes private int titleTextColorRes = -1;
  @ColorRes private int descriptionTextColorRes = -1;
  private Integer outerCircleColor = null;
  private Integer targetCircleColor = null;
  private Integer dimColor = null;
  private Integer titleTextColor = null;
  private Integer descriptionTextColor = null;
  @DimenRes private int titleTextDimen = -1;
  @DimenRes private int descriptionTextDimen = -1;
  private int titleTextSize = 20;
  private int descriptionTextSize = 18;

  protected ShowcaseTarget(Rect bounds, CharSequence title, @Nullable CharSequence description) {
    this(title, description);
    if (bounds == null) {
      throw new IllegalArgumentException("Cannot pass null bounds or title");
    }

    this.bounds = bounds;
  }

  protected ShowcaseTarget(CharSequence title, @Nullable CharSequence description) {
    if (title == null) {
      throw new IllegalArgumentException("Cannot pass null title");
    }

    this.title = title;
    this.description = description;
  }

  /**
   * Return a tap target for the overflow button from the given toolbar
   *
   * <p><b>Note:</b> This is currently experimental, use at your own risk
   */
  public static ShowcaseTarget forToolbarOverflow(Toolbar toolbar, CharSequence title) {
    return forToolbarOverflow(toolbar, title, null);
  }

  /**
   * Return a tap target for the overflow button from the given toolbar
   *
   * <p><b>Note:</b> This is currently experimental, use at your own risk
   */
  public static ShowcaseTarget forToolbarOverflow(
      Toolbar toolbar, CharSequence title, @Nullable CharSequence description) {
    return new ToolbarShowcaseTarget(toolbar, false, title, description);
  }

  /**
   * Return a tap target for the overflow button from the given toolbar
   *
   * <p><b>Note:</b> This is currently experimental, use at your own risk
   */
  public static ShowcaseTarget forToolbarOverflow(
      android.widget.Toolbar toolbar, CharSequence title) {
    return forToolbarOverflow(toolbar, title, null);
  }

  /**
   * Return a tap target for the overflow button from the given toolbar
   *
   * <p><b>Note:</b> This is currently experimental, use at your own risk
   */
  public static ShowcaseTarget forToolbarOverflow(
      android.widget.Toolbar toolbar, CharSequence title, @Nullable CharSequence description) {
    return new ToolbarShowcaseTarget(toolbar, false, title, description);
  }

  /** Return a tap target for the navigation button (back, up, etc) from the given toolbar * */
  public static ShowcaseTarget forToolbarNavigationIcon(Toolbar toolbar, CharSequence title) {
    return forToolbarNavigationIcon(toolbar, title, null);
  }

  /** Return a tap target for the navigation button (back, up, etc) from the given toolbar * */
  public static ShowcaseTarget forToolbarNavigationIcon(
      Toolbar toolbar, CharSequence title, @Nullable CharSequence description) {
    return new ToolbarShowcaseTarget(toolbar, true, title, description);
  }

  /** Return a tap target for the navigation button (back, up, etc) from the given toolbar * */
  public static ShowcaseTarget forToolbarNavigationIcon(
      android.widget.Toolbar toolbar, CharSequence title) {
    return forToolbarNavigationIcon(toolbar, title, null);
  }

  /** Return a tap target for the navigation button (back, up, etc) from the given toolbar * */
  public static ShowcaseTarget forToolbarNavigationIcon(
      android.widget.Toolbar toolbar, CharSequence title, @Nullable CharSequence description) {
    return new ToolbarShowcaseTarget(toolbar, true, title, description);
  }

  /** Return a tap target for the menu item from the given toolbar * */
  public static ShowcaseTarget forToolbarMenuItem(
      Toolbar toolbar, @IdRes int menuItemId, CharSequence title) {
    return forToolbarMenuItem(toolbar, menuItemId, title, null);
  }

  /** Return a tap target for the menu item from the given toolbar * */
  public static ShowcaseTarget forToolbarMenuItem(
      Toolbar toolbar,
      @IdRes int menuItemId,
      CharSequence title,
      @Nullable CharSequence description) {
    return new ToolbarShowcaseTarget(toolbar, menuItemId, title, description);
  }

  /** Return a tap target for the menu item from the given toolbar * */
  public static ShowcaseTarget forToolbarMenuItem(
      android.widget.Toolbar toolbar, @IdRes int menuItemId, CharSequence title) {
    return forToolbarMenuItem(toolbar, menuItemId, title, null);
  }

  /** Return a tap target for the menu item from the given toolbar * */
  public static ShowcaseTarget forToolbarMenuItem(
      android.widget.Toolbar toolbar,
      @IdRes int menuItemId,
      CharSequence title,
      @Nullable CharSequence description) {
    return new ToolbarShowcaseTarget(toolbar, menuItemId, title, description);
  }

  /** Return a tap target for the specified view * */
  public static ShowcaseTarget forView(View view, CharSequence title) {
    return forView(view, title, null);
  }

  /** Return a tap target for the specified view * */
  public static ShowcaseTarget forView(
      View view, CharSequence title, @Nullable CharSequence description) {
    return new ViewShowcaseTarget(view, title, description);
  }

  /** Return a tap target for the specified bounds * */
  public static ShowcaseTarget forBounds(Rect bounds, CharSequence title) {
    return forBounds(bounds, title, null);
  }

  /** Return a tap target for the specified bounds * */
  public static ShowcaseTarget forBounds(
      Rect bounds, CharSequence title, @Nullable CharSequence description) {
    return new ShowcaseTarget(bounds, title, description);
  }

  /** Specify whether the target should be transparent * */
  public ShowcaseTarget transparentTarget(boolean transparent) {
    this.transparentTarget = transparent;
    return this;
  }

  /** Specify the color resource for the outer circle * */
  public ShowcaseTarget outerCircleColor(@ColorRes int color) {
    this.outerCircleColorRes = color;
    return this;
  }

  /** Specify the color value for the outer circle * */
  // TODO(Hilal): In v2, this API should be cleaned up / torched
  public ShowcaseTarget outerCircleColorInt(@ColorInt int color) {
    this.outerCircleColor = color;
    return this;
  }

  /** Specify the alpha value [0.0, 1.0] of the outer circle * */
  public ShowcaseTarget outerCircleAlpha(@FloatRange(from = 0, to = 1f) float alpha) {
    if (alpha < 0.0f || alpha > 1.0f) {
      throw new IllegalArgumentException("Given an invalid alpha value: " + alpha);
    }
    this.outerCircleAlpha = alpha;
    return this;
  }

  /** Specify the color resource for the target circle * */
  public ShowcaseTarget targetCircleColor(@ColorRes int color) {
    this.targetCircleColorRes = color;
    return this;
  }

  /** Specify the color value for the target circle * */
  public ShowcaseTarget targetCircleColorInt(@ColorInt int color) {
    this.targetCircleColor = color;
    return this;
  }

  /** Specify the color resource for all text * */
  public ShowcaseTarget textColor(@ColorRes int color) {
    this.titleTextColorRes = color;
    this.descriptionTextColorRes = color;
    return this;
  }

  /** Specify the color value for all text * */
  public ShowcaseTarget textColorInt(@ColorInt int color) {
    this.titleTextColor = color;
    this.descriptionTextColor = color;
    return this;
  }

  /** Specify the color resource for the title text * */
  public ShowcaseTarget titleTextColor(@ColorRes int color) {
    this.titleTextColorRes = color;
    return this;
  }

  /** Specify the color value for the title text * */
  public ShowcaseTarget titleTextColorInt(@ColorInt int color) {
    this.titleTextColor = color;
    return this;
  }

  /** Specify the color resource for the description text * */
  public ShowcaseTarget descriptionTextColor(@ColorRes int color) {
    this.descriptionTextColorRes = color;
    return this;
  }

  /** Specify the color value for the description text * */
  public ShowcaseTarget descriptionTextColorInt(@ColorInt int color) {
    this.descriptionTextColor = color;
    return this;
  }

  /** Specify the typeface for all text * */
  public ShowcaseTarget textTypeface(Typeface typeface) {
    if (typeface == null) throw new IllegalArgumentException("Cannot use a null typeface");
    titleTypeface = typeface;
    descriptionTypeface = typeface;
    return this;
  }

  /** Specify the typeface for title text * */
  public ShowcaseTarget titleTypeface(Typeface titleTypeface) {
    if (titleTypeface == null) throw new IllegalArgumentException("Cannot use a null typeface");
    this.titleTypeface = titleTypeface;
    return this;
  }

  /** Specify the typeface for description text * */
  public ShowcaseTarget descriptionTypeface(Typeface descriptionTypeface) {
    if (descriptionTypeface == null)
      throw new IllegalArgumentException("Cannot use a null typeface");
    this.descriptionTypeface = descriptionTypeface;
    return this;
  }

  /** Specify the text size for the title in SP * */
  public ShowcaseTarget titleTextSize(int sp) {
    if (sp < 0) throw new IllegalArgumentException("Given negative text size");
    this.titleTextSize = sp;
    return this;
  }

  /** Specify the text size for the description in SP * */
  public ShowcaseTarget descriptionTextSize(int sp) {
    if (sp < 0) throw new IllegalArgumentException("Given negative text size");
    this.descriptionTextSize = sp;
    return this;
  }

  /**
   * Specify the text size for the title via a dimen resource
   *
   * <p>Note: If set, this value will take precedence over the specified sp size
   */
  public ShowcaseTarget titleTextDimen(@DimenRes int dimen) {
    this.titleTextDimen = dimen;
    return this;
  }

  /** Specify the alpha value [0.0, 1.0] of the description text * */
  public ShowcaseTarget descriptionTextAlpha(float descriptionTextAlpha) {
    if (descriptionTextAlpha < 0 || descriptionTextAlpha > 1f) {
      throw new IllegalArgumentException("Given an invalid alpha value: " + descriptionTextAlpha);
    }
    this.descriptionTextAlpha = descriptionTextAlpha;
    return this;
  }

  /**
   * Specify the text size for the description via a dimen resource
   *
   * <p>Note: If set, this value will take precedence over the specified sp size
   */
  public ShowcaseTarget descriptionTextDimen(@DimenRes int dimen) {
    this.descriptionTextDimen = dimen;
    return this;
  }

  /**
   * Specify the color resource to use as a dim effect
   *
   * <p><b>Note:</b> The given color will have its opacity modified to 30% automatically
   */
  public ShowcaseTarget dimColor(@ColorRes int color) {
    this.dimColorRes = color;
    return this;
  }

  /**
   * Specify the color value to use as a dim effect
   *
   * <p><b>Note:</b> The given color will have its opacity modified to 30% automatically
   */
  public ShowcaseTarget dimColorInt(@ColorInt int color) {
    this.dimColor = color;
    return this;
  }

  /** Specify whether or not to draw a drop shadow around the outer circle * */
  public ShowcaseTarget drawShadow(boolean draw) {
    this.drawShadow = draw;
    return this;
  }

  /** Specify whether or not the target should be cancelable * */
  public ShowcaseTarget cancelable(boolean status) {
    this.cancelable = status;
    return this;
  }

  /** Specify whether to tint the target's icon with the outer circle's color * */
  public ShowcaseTarget tintTarget(boolean tint) {
    this.tintTarget = tint;
    return this;
  }

  /** Specify the icon that will be drawn in the center of the target bounds * */
  public ShowcaseTarget icon(Drawable icon) {
    return icon(icon, false);
  }

  /**
   * Specify the icon that will be drawn in the center of the target bounds
   *
   * @param hasSetBounds Whether the drawable already has its bounds correctly set. If the drawable
   *     does not have its bounds set, then the following bounds will be applied: <br>
   *     <code>(0, 0, intrinsic-width, intrinsic-height)</code>
   */
  public ShowcaseTarget icon(Drawable icon, boolean hasSetBounds) {
    if (icon == null) throw new IllegalArgumentException("Cannot use null drawable");
    this.icon = icon;

    if (!hasSetBounds) {
      this.icon.setBounds(
          new Rect(0, 0, this.icon.getIntrinsicWidth(), this.icon.getIntrinsicHeight()));
    }

    return this;
  }

  /** Specify a unique identifier for this target. * */
  public ShowcaseTarget id(int id) {
    this.id = id;
    return this;
  }

  /** Specify the target radius in dp. * */
  public ShowcaseTarget targetRadius(int targetRadius) {
    this.targetRadius = targetRadius;
    return this;
  }

  /** Return the id associated with this tap target * */
  public int id() {
    return id;
  }

  /**
   * In case your target needs time to be ready (laid out in your view, not created, etc), the
   * runnable passed here will be invoked when the target is ready.
   */
  public void onReady(Runnable runnable) {
    runnable.run();
  }

  /**
   * Returns the target bounds. Throws an exception if they are not set (target may not be ready)
   *
   * <p>This will only be called internally when {@link #onReady(Runnable)} invokes its runnable
   */
  public Rect bounds() {
    if (bounds == null) {
      throw new IllegalStateException(
          "Requesting bounds that are not set! Make sure your target is ready");
    }
    return bounds;
  }

  @Nullable
  Integer outerCircleColorInt(Context context) {
    return colorResOrInt(context, outerCircleColor, outerCircleColorRes);
  }

  @Nullable
  Integer targetCircleColorInt(Context context) {
    return colorResOrInt(context, targetCircleColor, targetCircleColorRes);
  }

  @Nullable
  Integer dimColorInt(Context context) {
    return colorResOrInt(context, dimColor, dimColorRes);
  }

  @Nullable
  Integer titleTextColorInt(Context context) {
    return colorResOrInt(context, titleTextColor, titleTextColorRes);
  }

  @Nullable
  Integer descriptionTextColorInt(Context context) {
    return colorResOrInt(context, descriptionTextColor, descriptionTextColorRes);
  }

  int titleTextSizePx(Context context) {
    return dimenOrSize(context, titleTextSize, titleTextDimen);
  }

  int descriptionTextSizePx(Context context) {
    return dimenOrSize(context, descriptionTextSize, descriptionTextDimen);
  }

  @Nullable
  private Integer colorResOrInt(Context context, @Nullable Integer value, @ColorRes int resource) {
    if (resource != -1) {
      return ContextCompat.getColor(context, resource);
    }

    return value;
  }

  private int dimenOrSize(Context context, int size, @DimenRes int dimen) {
    if (dimen != -1) {
      return context.getResources().getDimensionPixelSize(dimen);
    }

    return UiUtil.sp(context, size);
  }
}
