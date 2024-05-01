package com.example.skylink;

import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.filters.LargeTest;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.skylink.Util.EspressoUtils;
import com.example.skylink.Util.UserInfoGenerator;
import com.example.skylink.presentation.Addons.Addons;
import com.example.skylink.presentation.FlightSearching.FlightDisplay;
import com.example.skylink.presentation.FlightSearching.FlightSearchP;
import com.example.skylink.presentation.Payment.CreditCardPaymentActivity;
import com.example.skylink.presentation.SeatSelect.Out_boundActivity;
import com.example.skylink.presentation.UserInfo.User_info;
import com.example.skylink.presentation.User_Auth.SignInActivity;
import com.example.skylink.presentation.User_Auth.UpdateUserProfileActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.isChecked;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;


@LargeTest
@RunWith(AndroidJUnit4.class)
public class AddonsActivityTest {

    @Rule
    public ActivityScenarioRule<SignInActivity> activityScenarioRule = new ActivityScenarioRule<>(SignInActivity.class);

    @Before
    public void setUp() {
        Intents.release();
        Intents.init();

    }

    @Test
    public void testAddonsPage() {
        String flyFrom = "Vancouver - YVR";
        String flyTo = "Hamilton - YHM";

        // used for generation random user info
        String[] userInfo = UserInfoGenerator.generateUserInfo();

// --- Sign-up page

        // call a method to perform signup test with valid inputs
        EspressoUtils.signUp(userInfo[0], userInfo[1], userInfo[2]);


// --- Update user profile page

        // Verify that the expected intent was sent
        intended(hasComponent(UpdateUserProfileActivity.class.getName()));

        // call a method to perform update user info test with valid user profile info
        EspressoUtils.updateUserInfo(userInfo[3], userInfo[4], userInfo[5], userInfo[6], userInfo[7], userInfo[8]);


// --- Flight search page

        // Verify that the expected intent was sent
        intended(hasComponent(FlightSearchP.class.getName()));

        /* Search flight from YVR to YHM on April 8, 2024 */

        // call a method to perform flight searching test with valid search data.
        EspressoUtils.performFlightSearch(flyFrom, flyTo, 2024, 5, 8);


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
        EspressoUtils.performUserInfo("Mr.", "Yiming", "Zang", "1234567890", "yiming@gmail.com");

// --- Seat selection page

        // Verify that the expected intent was sent
        intended(hasComponent(Out_boundActivity.class.getName()));

        onView(withId(R.id.confirmButton)).perform(click());    // perform auto seat select

// --- Add-ons page

        // Verify that the expected intent was sent
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

// --- Payment page

        // Verify that the expected intent was sent
        intended(hasComponent(CreditCardPaymentActivity.class.getName()));
    }

}