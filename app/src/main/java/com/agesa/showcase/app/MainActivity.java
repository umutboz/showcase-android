package com.agesa.showcase.app;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.widget.AppCompatImageButton;

import com.oneframe.android.showcase.ShowcaseTarget;
import com.oneframe.android.showcase.ShowcaseTargetSequence;

import java.util.ArrayList;
import java.util.List;

public final class MainActivity extends BaseActivity {

  private ViewPager mPager;
  private AppCompatImageButton mSearch;
  private AppCompatImageButton mBasket;
  private List<Discount> mDiscounts;

  @Override
  protected int getLayoutResId() {
    return R.layout.layout_main;
  }

  @Override
  public void onContentChanged() {
    super.onContentChanged();

    mPager = findViewById(R.id.main_pager);
    mSearch = findViewById(R.id.main_search);
    mBasket = findViewById(R.id.main_basket);
  }

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    mDiscounts = new ArrayList<>();
    mDiscounts.add(
        new Discount(
            "Upto 60% OFF", "Flights Upto 60% + 15% Instant discount on HDFC credit cards"));

    mDiscounts.add(
        new Discount(
            "Upto 60% OFF", "Flights Upto 60% + 15% Instant discount on HDFC credit cards"));

    mDiscounts.add(
        new Discount(
            "Upto 60% OFF", "Flights Upto 60% + 15% Instant discount on HDFC credit cards"));

    DiscountAdapter adapter = new DiscountAdapter(getSupportFragmentManager(), mDiscounts);

    mPager.setClipToPadding(false);
    mPager.setPadding(72, 0, 72, 0);
    mPager.setAdapter(adapter);

    new ShowcaseTargetSequence(this)
        .targets(
            ShowcaseTarget.forView(mSearch, "Search", "You can search bills from here")
                .outerCircleColor(R.color.colorAccent)
                .textColor(android.R.color.black)
                .drawShadow(true),
            ShowcaseTarget.forView(mBasket, "Basket", "Basket items appear here")
                .outerCircleColor(R.color.colorAccent)
                .textColor(android.R.color.black),
            ShowcaseTarget.forView(
                    findViewById(R.id.main_mobile), "Mobile", "This is your mobile bill shortcut")
                .outerCircleColor(R.color.colorAccent)
                .textColor(android.R.color.black)
                .tintTarget(false)
                .drawShadow(true))
        .listener(
            new ShowcaseTargetSequence.Listener() {
              @Override
              public void onSequenceFinish() {}

              @Override
              public void onSequenceStep(ShowcaseTarget lastTarget, boolean targetClicked) {}

              @Override
              public void onSequenceCanceled(ShowcaseTarget lastTarget) {}
            })
        .start();
  }
}
