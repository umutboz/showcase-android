package com.agesa.showcase.app;

import android.graphics.Rect;
import android.os.Bundle;
import android.text.Layout;

import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.widget.AppCompatImageButton;

import com.agesa.showcase.ShowcaseTarget;
import com.agesa.showcase.ShowcaseTargetSequence;

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
    mPager.setPadding(48, 0, 48, 0);
    mPager.setAdapter(adapter);
    Rect rectA = new Rect(0, 10, 50, 50);
    ShowcaseTarget target1 = ShowcaseTarget.forView(mSearch, "", "Sağlık Poliçen burada")
            .outerCircleColor(R.color.colorAccent)
            .textColor(android.R.color.black).targetRadius(50).
            drawShadow(true);

    // Metin: sağ hizala, sağa yaklaştır + biraz aşağı indir
    target1.textAlignment(Layout.Alignment.ALIGN_OPPOSITE)     // sağ hizalama
            .instructionHorizontalMarginsDp(24, 0)              // sağ marjı küçült
            .instructionXOffsetDp(50)                           // biraz daha sağa
            .instructionYOffsetDp(-32);                          // biraz aşağı

  // Dış çember: biraz küçült + padding azalt
    target1.outerCircleScale(0.45f)
            .outerCirclePaddingDp(24);

      new ShowcaseTargetSequence(this)
        .targets(
          target1,
                   // .dimenOrSize(getApplicationContext(),200),
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
