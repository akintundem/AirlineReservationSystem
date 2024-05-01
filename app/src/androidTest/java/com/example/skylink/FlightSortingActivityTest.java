package com.example.skylink;


import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;

import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.matcher.BoundedMatcher;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.skylink.Util.EspressoUtils;
import com.example.skylink.Util.UserInfoGenerator;
import com.example.skylink.presentation.FlightSearching.FlightDisplay;
import com.example.skylink.presentation.FlightSearching.FlightSearchP;
import com.example.skylink.presentation.User_Auth.SignInActivity;
import com.example.skylink.presentation.User_Auth.UpdateUserProfileActivity;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class FlightSortingActivityTest {

    @Rule
    public ActivityScenarioRule<SignInActivity> activityScenarioRule = new ActivityScenarioRule<>(SignInActivity.class);

    @Before
    public void setup() {
        // Initialize Espresso Intents before the test starts
        Intents.init();
        String[] userInfo = UserInfoGenerator.generateUserInfo();
        EspressoUtils.signUp(userInfo[0], userInfo[1], userInfo[2]);

        EspressoUtils.verifyActivity(UpdateUserProfileActivity.class);

        EspressoUtils.updateUserInfo(userInfo[3], userInfo[4], userInfo[5], userInfo[6], userInfo[7], userInfo[8]);

        EspressoUtils.verifyActivity(FlightSearchP.class);
    }

    @Test
    public void testFlightSorting() {
        String flyFrom = "Toronto - YYZ";
        String flyTo = "Hamilton - YHM";

        /* Search flight from YYZ to YHM on April 6, 2024 */

        // call a method to perform flight searching test with valid search data.
        EspressoUtils.performFlightSearch(flyFrom, flyTo, 2024, 5, 6);


        // --- Flight display page

        // Verify that the expected intent was sent
        intended(hasComponent(FlightDisplay.class.getName()));

        onView(withId(R.id.flightListView)).check(matches(isDisplayed()));      // check if flights are shown

        // Select Direct flight from the spinner list
        Espresso.onView(withId(R.id.sortingListOption)).perform(click());       // Perform click on the spinner
        onData(allOf(is(instanceOf(String.class)), is("Direct flight"))).perform(click());

        // checks if first flight does not have a mid airport
        Espresso.onView(withId(R.id.flightListView))
                .check(matches(atPosition(0, withText(""))));


        // Select Earliest departure from the spinner list
        Espresso.onView(withId(R.id.sortingListOption)).perform(click());       // Perform click on the spinner
        onData(allOf(is(instanceOf(String.class)), is("Earliest departure"))).perform(click());

        // checks if first flight has 'YOW' as mid airport
        Espresso.onView(withId(R.id.flightListView))
                .check(matches(atPosition(0, withText("YOW "))));


        // Select Lowest price from the spinner list
        Espresso.onView(withId(R.id.sortingListOption)).perform(click());       // Perform click on the spinner
        onData(allOf(is(instanceOf(String.class)), is("Lowest price"))).perform(click());

        // checks if first flight has 'YOW' as mid airport
        Espresso.onView(withId(R.id.flightListView))
                .check(matches(atPosition(0, withText("YOW "))));

    }


    // Custom matcher to match the midAirport text of the item at a specific position in the ListView
    public static Matcher<View> atPosition(final int position, final Matcher<View> itemMatcher) {
        return new BoundedMatcher<View, ListView>(ListView.class) {
            @Override
            public void describeTo(Description description) {
                description.appendText("has item at position " + position + ": ");
                itemMatcher.describeTo(description);
            }

            @Override
            protected boolean matchesSafely(ListView listView) {
                // Check if the ListView contains an item at the given position
                int itemCount = listView.getAdapter().getCount();
                if (position < 0 || position >= itemCount) {
                    return false;
                }

                // Get the view for the item at the given position
                View itemView = listView.getChildAt(position - listView.getFirstVisiblePosition());
                if (itemView == null) {
                    return false;
                }

                TextView midAirport = itemView.findViewById(R.id.midCodeTV);

                // Check if the midAirport in the item matches the given matcher
                return itemMatcher.matches(midAirport);
            }
        };
    }
}