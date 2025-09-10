package com.agesa.showcase.app;

import android.os.Bundle;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

abstract class BaseFragment extends Fragment {

  @Nullable
  @Override
  public View onCreateView(
      @NonNull LayoutInflater inflater,
      @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    View cell = inflater.inflate(getLayoutResId(), container, false);
    onViewInflated(cell);

    return cell;
  }

  protected abstract @LayoutRes int getLayoutResId();

  protected void onViewInflated(@NonNull View cell) {}
}
