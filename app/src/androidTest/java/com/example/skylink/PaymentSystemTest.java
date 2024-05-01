package com.example.skylink;

import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

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

import com.example.skylink.Util.EspressoUtils;
import com.example.skylink.Util.UserInfoGenerator;
import com.example.skylink.presentation.Addons.Addons;
import com.example.skylink.presentation.FlightSearching.FlightDisplay;
import com.example.skylink.presentation.FlightSearching.FlightSearchP;
import com.example.skylink.presentation.Payment.PaymentSuccessfulActivity;
import com.example.skylink.presentation.SeatSelect.Out_boundActivity;
import com.example.skylink.presentation.UserInfo.User_info;
import com.example.skylink.presentation.User_Auth.SignInActivity;
import com.example.skylink.presentation.User_Auth.UpdateUserProfileActivity;

@RunWith(AndroidJUnit4.class)
public class PaymentSystemTest {

    @Rule
    public ActivityScenarioRule<SignInActivity> activityScenarioRule = new ActivityScenarioRule<>(SignInActivity.class);

    @Before
    public void setUp() {
        Intents.release();
        Intents.init();

        // Perform setup tasks here
        String flyFrom = "Vancouver - YVR";
        String flyTo = "Hamilton - YHM";
        String[] userInfo = UserInfoGenerator.generateUserInfo();

        // --- Sign-up page ---
        EspressoUtils.signUp(userInfo[0], userInfo[1], userInfo[2]);
        intended(hasComponent(UpdateUserProfileActivity.class.getName()));

        // --- Update user profile page ---
        EspressoUtils.updateUserInfo(userInfo[3], userInfo[4], userInfo[5], userInfo[6], userInfo[7], userInfo[8]);
        intended(hasComponent(FlightSearchP.class.getName()));

        // --- Flight search page ---
        EspressoUtils.performFlightSearch(flyFrom, flyTo, 2024, 5, 8);
        intended(hasComponent(FlightDisplay.class.getName()));
        onView(withId(R.id.flightListView)).check(matches(isDisplayed()));
        onView(withId(R.id.econPriceBtn)).perform(click());

        // --- User info page ---
        intended(hasComponent(User_info.class.getName()));
        EspressoUtils.performUserInfo("Mr.", "Yiming", "Zang", "1234567890", "yiming@gmail.com");

        // --- Seat selection page ---
        intended(hasComponent(Out_boundActivity.class.getName()));
        onView(withId(R.id.confirmButton)).perform(click());

        // --- Add-ons page ---
        intended(hasComponent(Addons.class.getName()));
        String expectedBagCount = "2";
        String expectedBagFee = "$50";
        String expectedPetCountFirst = "2";
        String expectedPetFeeFirst = "$120";
        String expectedPetCountSecond = "1";
        String expectedPetFeeSecond = "$60";

        onView(withId(R.id.bag_btn_increment)).perform(click());
        onView(withId(R.id.totalBags)).check(matches(withText(expectedBagCount)));
        onView(withId(R.id.bagFees)).check(matches(withText(expectedBagFee)));
        onView(withId(R.id.totalPriceTV)).check(matches(withText("$899")));

        onView(withId(R.id.pet_btn_increment)).perform(click());
        onView(withId(R.id.pet_btn_increment)).perform(click());
        onView(withId(R.id.totalPetSeat)).check(matches(withText(expectedPetCountFirst)));
        onView(withId(R.id.petSeatFees)).check(matches(withText(expectedPetFeeFirst)));
        onView(withId(R.id.totalPriceTV)).check(matches(withText("$1019")));

        onView(withId(R.id.pet_btn_decrement)).perform(click());
        onView(withId(R.id.totalPetSeat)).check(matches(withText(expectedPetCountSecond)));
        onView(withId(R.id.petSeatFees)).check(matches(withText(expectedPetFeeSecond)));
        onView(withId(R.id.totalPriceTV)).check(matches(withText("$959")));

        onView(withId(R.id.radioButtonIncludeWifi)).perform(click());
        onView(withId(R.id.radioButtonIncludeWifi)).check(matches(isChecked()));
        onView(withId(R.id.totalPriceTV)).check(matches(withText("$1009")));

        onView(withId(R.id.radioButtonIncludeWheelchair)).perform(click());
        onView(withId(R.id.radioButtonIncludeWheelchair)).check(matches(isChecked()));
        onView(withId(R.id.totalPriceTV)).check(matches(withText("$1009")));

        onView(withId(R.id.radioButtonNoWheelchair)).perform(click());
        onView(withId(R.id.radioButtonNoWheelchair)).check(matches(isChecked()));
        onView(withId(R.id.totalPriceTV)).check(matches(withText("$1009")));

        onView(withId(R.id.btnConfirmExtra)).perform(click());
    }

    @Test
    public void testPaymentProcess() {
        // Test payment process
        onView(ViewMatchers.withId(R.id.etCreditCardNumber))
                .perform(ViewActions.typeText("1234567890123456"), ViewActions.closeSoftKeyboard());

        onView(ViewMatchers.withId(R.id.etExpirationDate))
                .perform(ViewActions.typeText("1230"), ViewActions.closeSoftKeyboard());

        onView(ViewMatchers.withId(R.id.etCVV))
                .perform(ViewActions.typeText("123"), ViewActions.closeSoftKeyboard());

        onView(ViewMatchers.withId(R.id.etCardholderName))
                .perform(ViewActions.typeText(" Yiming Zang "), ViewActions.closeSoftKeyboard());

        onView(ViewMatchers.withId(R.id.etBillingAddress))
                .perform(ViewActions.typeText("123 Main St, Winnipeg, MB"), ViewActions.closeSoftKeyboard());

        onView(withId(R.id.btnPay)).perform(click());

        intended(hasComponent(PaymentSuccessfulActivity.class.getName()));

    }
}
