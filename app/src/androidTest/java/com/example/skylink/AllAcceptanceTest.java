package com.example.skylink;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        SignUpActivityTest.class,
        SignInActivityTest.class,
        FlightSearchActivityTest.class,
        FlightSortingActivityTest.class,
        UserInfoActivityTest.class,
        OutBoundActivityTest.class,
        AddonsActivityTest.class,
        PaymentSystemTest.class,
        BookingDisplayActivityTest.class
})

public class AllAcceptanceTest {
    //  This class remains empty
}
