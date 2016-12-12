package com.xlythe.calculator.holo;


import android.app.Activity;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class CalculatorTest {

    @Rule
    public ActivityTestRule<Calculator> activityTestRule = new ActivityTestRule<>(Calculator.class, false, false);

    @Test
    public void calculatorTest() {

        // Reset prererences
        File root = InstrumentationRegistry.getTargetContext().getFilesDir().getParentFile();
        String[] sharedPreferencesFileNames = new File(root, "shared_prefs").list();
        for (String fileName : sharedPreferencesFileNames) {
            InstrumentationRegistry.getTargetContext().getSharedPreferences(fileName.replace(".xml", ""), Context.MODE_PRIVATE).edit().clear().commit();
        }


        activityTestRule.launchActivity(null);


        ViewInteraction button = onView(
                allOf(withId(R.id.cling_dismiss), withText("OK"),
                        withParent(withId(R.id.simple_cling)),
                        isDisplayed()));
        button.perform(click());

        ViewInteraction colorButton2 = onView(
                allOf(withId(R.id.digit1), withText("1"), isDisplayed()));
        colorButton2.perform(click());


        ViewInteraction colorButton3 = onView(
                allOf(withId(R.id.plus), withText("+"), isDisplayed()));
        colorButton3.perform(click());

        ViewInteraction colorButton4 = onView(
                allOf(withId(R.id.digit1), withText("1"), isDisplayed()));
        colorButton4.perform(click());

        ViewInteraction colorButton5 = onView(
                allOf(withId(R.id.equal), withText("="), isDisplayed()));
        colorButton5.perform(click());

        ViewInteraction editText = onView(
                allOf(withText("2"),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.widget.HorizontalScrollView.class),
                                        0),
                                0),
                        isDisplayed()));
        editText.check(matches(withText("2")));

        ViewInteraction colorButton = onView(
                allOf(withId(R.id.clear), withText("CLEAR"), isDisplayed()));
        colorButton.perform(click());


    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
