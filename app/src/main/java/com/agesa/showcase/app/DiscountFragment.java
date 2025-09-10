package com.agesa.showcase.app;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import android.view.View;



public final class DiscountFragment extends BaseFragment {

  private static final String KEY_DISCOUNT = "discount";

  private AppCompatTextView mTitle;
  private AppCompatTextView mDescription;

  public static DiscountFragment newInstance(@NonNull Discount discount) {
    Bundle args = new Bundle();
    args.putSerializable(KEY_DISCOUNT, discount);

    DiscountFragment fragment = new DiscountFragment();
    fragment.setArguments(args);
    return fragment;
  }

  @Override
  protected int getLayoutResId() {
    return R.layout.cell_discount;
  }

  @Override
  protected void onViewInflated(@NonNull View cell) {
    super.onViewInflated(cell);

    mTitle = cell.findViewById(R.id.discount_title);
    mDescription = cell.findViewById(R.id.discount_description);
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    Bundle arguments = getArguments();
    if (arguments != null && arguments.containsKey(KEY_DISCOUNT)) {
      Discount discount = (Discount) arguments.getSerializable(KEY_DISCOUNT);
      if (discount != null) {
        mTitle.setText(discount.title);
        mDescription.setText(discount.description);
      }
    }
  }
}
