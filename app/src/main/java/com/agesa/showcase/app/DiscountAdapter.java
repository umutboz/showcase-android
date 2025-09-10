package com.agesa.showcase.app;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

final class DiscountAdapter extends FragmentPagerAdapter {

  private final List<Discount> mDiscounts;

  public DiscountAdapter(FragmentManager fm, List<Discount> discounts) {
    super(fm);

    mDiscounts = discounts;
  }

  @Override
  public Fragment getItem(int position) {
    Discount discount = mDiscounts.get(position);
    return DiscountFragment.newInstance(discount);
  }

  @Override
  public int getCount() {
    return mDiscounts.size();
  }
}
