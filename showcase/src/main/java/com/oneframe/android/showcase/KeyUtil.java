package com.oneframe.android.showcase;

import android.content.Context;
import android.content.SharedPreferences;

final class KeyUtil {

  private static final String NAME = "SHOWCASE";

  KeyUtil() {}

  static boolean save(Context context, String key) {
    SharedPreferences preferences = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
    return preferences.edit().putBoolean(key, true).commit();
  }

  static boolean exists(Context context, String key) {
    SharedPreferences preferences = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
    return preferences.contains(key);
  }

  static boolean delete(Context context, String key) {
    SharedPreferences preferences = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
    return preferences.edit().remove(key).commit();
  }
}
