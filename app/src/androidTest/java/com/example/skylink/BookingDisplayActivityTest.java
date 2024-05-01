package com.example.skylink;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.isChecked;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;

import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.contrib.DrawerActions;
import androidx.test.espresso.matcher.BoundedMatcher;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.filters.LargeTest;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.espresso.intent.Intents;

import com.example.skylink.Util.EspressoUtils;
import com.example.skylink.Util.UserInfoGenerator;
import com.example.skylink.presentation.Addons.Addons;
import com.example.skylink.presentation.Bookings.BookingsDisplay;
import com.example.skylink.presentation.FlightSearching.FlightDisplay;
import com.example.skylink.presentation.FlightSearching.FlightSearchP;
import com.example.skylink.presentation.Payment.CreditCardPaymentActivity;
import com.example.skylink.presentation.Payment.PaymentSuccessfulActivity;
import com.example.skylink.presentation.SeatSelect.Out_boundActivity;
import com.example.skylink.presentation.UserInfo.User_info;
import com.example.skylink.presentation.User_Auth.SignInActivity;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import com.example.skylink.presentation.User_Auth.UpdateUserProfileActivity;

@RunWith(AndroidJUnit4.class)
public class BookingDisplayActivityTest {

    @Rule
    public ActivityScenarioRule<SignInActivity> activityScenarioRule = new ActivityScenarioRule<>(SignInActivity.class);

    @Before
    public void setup() {
        Intents.release();
        Intents.init();
    }

    @Test
    public void testBookingDisplay() {

        // 1. Create a new account
        String[] userInfo = UserInfoGenerator.generateUserInfo();
        EspressoUtils.signUp(userInfo[0], userInfo[1], userInfo[2]);
        EspressoUtils.verifyActivity(UpdateUserProfileActivity.class);
        EspressoUtils.updateUserInfo(userInfo[3], userInfo[4], userInfo[5], userInfo[6], userInfo[7], userInfo[8]);
        EspressoUtils.clickLogOut();
        EspressoUtils.signIn(userInfo[1],userInfo[2]);
        EspressoUtils.verifyActivity(FlightSearchP.class);

        // 2. Search for a flight
        String flyFrom = "Vancouver - YVR";
        String flyTo = "Hamilton - YHM";
        int year = 2024;
        int monthOfYear = 5;
        int dayOfMonth = 8;

        /* Search flight from YVR to YHM on May 8, 2024 */

        // call a method to perform flight searching test with valid search data.
        EspressoUtils.performFlightSearch(flyFrom, flyTo, year, monthOfYear, dayOfMonth);


// --- Flight display page

        // Verify that the expected intent was sent
        intended(hasComponent(FlightDisplay.class.getName()));

        onView(withId(R.id.flightListView)).check(matches(isDisplayed()));      // check if flights are shown

        // Click the button for economy class
        onView(withId(R.id.econPriceBtn)).perform(click());

// --- User info page

        // Verify that the expected intent was sent
        intended(hasComponent(User_info.class.getName()));

        // Fill in passenger information
        EspressoUtils.performUserInfo("Mr.", "Dilawer", "Hussain", "1234567890", "dilawer@email.com");

// --- Seat selection page

        // Verify that the expected intent was sent
        intended(hasComponent(Out_boundActivity.class.getName()));

        onView(withId(R.id.confirmButton)).perform(click());    // perform auto seat select

        // --- Add-ons page

        intended(hasComponent(Addons.class.getName()));

        String expectedBagCount = "2";
        String expectedBagFee = "$50";

        String expectedPetCountFirst = "2";
        String expectedPetFeeFirst = "$120";

        String expectedPetCountSecond = "1";
        String expectedPetFeeSecond = "$60";

        // add a bag => $50
        onView(withId(R.id.bag_btn_increment)).perform(click());           // find and click add button to add a bag

        onView(withId(R.id.totalBags)).check(matches(withText(expectedBagCount)));     // check if number of bag is incremented
        onView(withId(R.id.bagFees)).check(matches(withText(expectedBagFee)));         // check if bag's fee is changed
        onView(withId(R.id.totalPriceTV)).check(matches(withText("$899")));         // check if bag's fee is changed in total price field

        // add two pet => $120
        onView(withId(R.id.pet_btn_increment)).perform(ViewActions.click());           // find and click add button to add one pet
        onView(withId(R.id.pet_btn_increment)).perform(ViewActions.click());           // find and click add button to add second pet
        onView(withId(R.id.totalPetSeat)).check(matches(withText(expectedPetCountFirst)));     // check if number of pet is incremented
        onView(withId(R.id.petSeatFees)).check(matches(withText(expectedPetFeeFirst)));         // check if pet's fee is changed
        onView(withId(R.id.totalPriceTV)).check(matches(withText("$1019")));         // check if pet's fee is changed in total price field

        // remove one pet => $60
        onView(withId(R.id.pet_btn_decrement)).perform(ViewActions.click());           // find and click subtract button to remove the pet
        onView(withId(R.id.totalPetSeat)).check(matches(withText(expectedPetCountSecond)));     // check if number of pet is incremented
        onView(withId(R.id.petSeatFees)).check(matches(withText(expectedPetFeeSecond)));         // check if pet's fee is changed
        onView(withId(R.id.totalPriceTV)).check(matches(withText("$959")));         // check if pet's fee is changed in total price field


        // add wifi => $50
        onView(withId(R.id.radioButtonIncludeWifi)).perform(ViewActions.click());           // find and click radio button to add wifi
        onView(withId(R.id.radioButtonIncludeWifi)).check(matches(isChecked()));             // check if wifi button is selected
        onView(withId(R.id.totalPriceTV)).check(matches(withText("$1009")));         // check if wifi fee is changed in total price field

        // add wheelchair => $0
        onView(withId(R.id.radioButtonIncludeWheelchair)).perform(ViewActions.click());     // find and click radio button to add wheelchair
        onView(withId(R.id.radioButtonIncludeWheelchair)).check(matches(isChecked()));     // check if wheelchair button is selected
        onView(withId(R.id.totalPriceTV)).check(matches(withText("$1009")));                // check if wheelchair's fee is changed in total price field

        // remove wheelchair => $0
        onView(withId(R.id.radioButtonNoWheelchair)).perform(ViewActions.click());          // find and click radio button to remove wheelchair
        onView(withId(R.id.radioButtonNoWheelchair)).check(matches(isChecked()));     // check if wheelchair button is selected
        onView(withId(R.id.totalPriceTV)).check(matches(withText("$1009")));         // check if selected addons fee is changed in total price field
        onView(withId(R.id.btnConfirmExtra)).perform(click());      // click confirm to go to next activity

        intended(hasComponent(CreditCardPaymentActivity.class.getName()));

        onView(ViewMatchers.withId(R.id.etCreditCardNumber))
                .perform(ViewActions.typeText("1234567890123456"), ViewActions.closeSoftKeyboard());

        onView(ViewMatchers.withId(R.id.etExpirationDate))
                .perform(ViewActions.typeText("1230"), ViewActions.closeSoftKeyboard());

        onView(ViewMatchers.withId(R.id.etCVV))
                .perform(ViewActions.typeText("123"), ViewActions.closeSoftKeyboard());

        onView(ViewMatchers.withId(R.id.etCardholderName))
                .perform(ViewActions.typeText(" Dilawer Hussain"), ViewActions.closeSoftKeyboard());

        onView(ViewMatchers.withId(R.id.etBillingAddress))
                .perform(ViewActions.typeText("123 Main St, Winnipeg, MB"), ViewActions.closeSoftKeyboard());

        onView(withId(R.id.btnPay)).perform(click());

        intended(hasComponent(PaymentSuccessfulActivity.class.getName()));

        onView(withId(R.id.buttonMainMenu)).perform(click());

        onView(withId(R.id.drawer_layout)).perform(DrawerActions.open());

        onView(withId(R.id.bookings)).perform(ViewActions.click());

        intended(hasComponent(BookingsDisplay.class.getName()));

        onView(withId(R.id.bookingsRecyclerView)).check(matches(isDisplayed()));        // check if flights are shown

        onView(withId(R.id.textViewDepartureIcao)).check(matches(withText("YVR")));
        onView(withId(R.id.textViewArrivalIcao)).check(matches(withText("YHM")));

    }

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
