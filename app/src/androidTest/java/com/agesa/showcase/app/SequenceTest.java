package com.agesa.showcase.app;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class SequenceTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void sequenceTest() {
        ViewInteraction showcaseTargetView = onView(
                allOf(withClassName(is("com.oneframe.android.showcase.ShowcaseTargetView")), isDisplayed()));
        showcaseTargetView.perform(click());

        ViewInteraction showcaseTargetView2 = onView(
                allOf(withClassName(is("com.oneframe.android.showcase.ShowcaseTargetView")), isDisplayed()));
        showcaseTargetView2.perform(click());

        ViewInteraction showcaseTargetView3 = onView(
                allOf(withClassName(is("com.oneframe.android.showcase.ShowcaseTargetView")), isDisplayed()));
        showcaseTargetView3.perform(click());
    }
}
