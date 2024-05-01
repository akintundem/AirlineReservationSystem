package com.example.skylink;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.filters.LargeTest;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.skylink.Util.EspressoUtils;
import com.example.skylink.Util.UserInfoGenerator;
import com.example.skylink.presentation.FlightSearching.FlightSearchP;
import com.example.skylink.presentation.User_Auth.SignInActivity;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import com.example.skylink.presentation.User_Auth.UpdateUserProfileActivity;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class SignInActivityTest {

    @Rule
    public ActivityScenarioRule<SignInActivity> activityScenarioRule = new ActivityScenarioRule<>(SignInActivity.class);

    @Test
    public void signIn() {
        String[] userInfo = UserInfoGenerator.generateUserInfo();
        EspressoUtils.signUp(userInfo[0], userInfo[1], userInfo[2]);

        EspressoUtils.verifyActivity(UpdateUserProfileActivity.class);

        EspressoUtils.updateUserInfo(userInfo[3], userInfo[4], userInfo[5], userInfo[6], userInfo[7], userInfo[8]);

        EspressoUtils.verifyActivity(FlightSearchP.class);

        EspressoUtils.clickLogOut();

        EspressoUtils.signIn(userInfo[1],userInfo[2]);

        EspressoUtils.verifyActivity(FlightSearchP.class);
    }
}
