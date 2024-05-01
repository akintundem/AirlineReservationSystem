package com.example.skylink.presentation.Addons;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.skylink.R;
import com.example.skylink.business.Implementations.FlightBookingHandler;
import com.example.skylink.business.Interface.iFlightBookingHandler;
import com.example.skylink.objects.Interfaces.iFlight;
import com.example.skylink.objects.Interfaces.iFlightInfo;
import com.example.skylink.objects.Interfaces.iFlightSearch;
import com.example.skylink.presentation.Payment.CreditCardPaymentActivity;
import com.example.skylink.presentation.ISession;
import com.example.skylink.presentation.Session;
import com.google.android.material.card.MaterialCardView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class Addons extends AppCompatActivity {

    private final int MAX_BAG_COUNT = 2;
    private final int MIN_BAG_COUNT = 1;
    private final int BAG_FEE = 50;

    private final int MAX_PET_SEAT_COUNT = 2;
    private final int MIN_PET_SEAT_COUNT = 0;
    private final int PET_FEE = 60;
    private final int WIFI_FEE = 50;


    private TextView totalBagTV, totalBagFeeTV;
    private TextView totalPetSeatTV, totalPetSeatFeeTV;
    private Button bagIncrementBtn, bagDecrementBtn;
    private Button petSeatIncrementBtn, petSeatDecrementBtn;

    private RadioGroup radioGroupWifiOption;
    private RadioGroup radioGroupWheelchairOption;
    private TextView totalPriceTV;
    private Button confirmBtn;

    private int currentBagCount = MIN_BAG_COUNT;
    private int currentPetSeatCount = MIN_PET_SEAT_COUNT;
    private int wifiSelected = 0;
    private int wheelChairSelected = 0;
    private int addonTotalFee = 0;
    private int flightTotalFee =  Session.getInstance().getBookingInfo().getInboundPrice() + Session.getInstance().getBookingInfo().getOutboundPrice();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addons);

        totalPriceTV = findViewById(R.id.totalPriceTV);
        radioGroupWifiOption = findViewById(R.id.radioGroupWifiOption);
        radioGroupWheelchairOption = findViewById(R.id.radioGroupWheelchairOption);

        checkTripWay();

        updateReviewSection();

        setupBagCountButtons();
        setupPetSeatCountButtons();

        setupWifiOptionButton();
        setupWheelchairOptionButton();

        setupConfirmButton();

    }


    private void setupBagCountButtons() {
        totalBagTV = findViewById(R.id.totalBags);
        totalBagFeeTV = findViewById(R.id.bagFees);

        bagIncrementBtn = findViewById(R.id.bag_btn_increment);
        bagDecrementBtn = findViewById(R.id.bag_btn_decrement);


        bagIncrementBtn.setOnClickListener(v -> bagIncrementBtn());
        bagDecrementBtn.setOnClickListener(v -> bagDecrementBtn());

    }

    private void setupPetSeatCountButtons() {
        totalPetSeatTV = findViewById(R.id.totalPetSeat);
        totalPetSeatFeeTV = findViewById(R.id.petSeatFees);

        petSeatIncrementBtn = findViewById(R.id.pet_btn_increment);
        petSeatDecrementBtn = findViewById(R.id.pet_btn_decrement);


        petSeatIncrementBtn.setOnClickListener(v -> petSeatIncrementBtn());
        petSeatDecrementBtn.setOnClickListener(v -> petSeatDecrementBtn());

    }

    private void bagIncrementBtn() {
        if (currentBagCount < MAX_BAG_COUNT) {
            currentBagCount++;
            addonTotalFee += BAG_FEE;
            updateBagTextCountField();
        }
    }

    private void bagDecrementBtn() {
        if (currentBagCount > MIN_BAG_COUNT) {
            currentBagCount--;
            addonTotalFee -= BAG_FEE;
            updateBagTextCountField();
        }

    }

    private void updateBagTextCountField() {
        totalBagTV.setText("" + currentBagCount);

        totalBagFeeTV.setText("$" + (currentBagCount-1) * BAG_FEE);
        totalPriceTV.setText("$" + (flightTotalFee + addonTotalFee));

        bagIncrementBtn.setEnabled(currentBagCount != MAX_BAG_COUNT);
        bagDecrementBtn.setEnabled(currentBagCount != MIN_BAG_COUNT);

    }

    private void petSeatIncrementBtn() {
        if (currentPetSeatCount < MAX_PET_SEAT_COUNT) {
            currentPetSeatCount++;
            addonTotalFee += PET_FEE;
            updatePetSeatTextCountField();
        }
    }

    private void petSeatDecrementBtn() {
        if (currentPetSeatCount > MIN_PET_SEAT_COUNT) {
            currentPetSeatCount--;
            addonTotalFee -= PET_FEE;
            updatePetSeatTextCountField();
        }

    }

    private void updatePetSeatTextCountField() {
        totalPetSeatTV.setText("" + currentPetSeatCount);

        totalPetSeatFeeTV.setText("$" + (currentPetSeatCount * PET_FEE));
        totalPriceTV.setText("$" + (flightTotalFee + addonTotalFee));

        petSeatIncrementBtn.setEnabled(currentPetSeatCount != MAX_PET_SEAT_COUNT);
        petSeatDecrementBtn.setEnabled(currentPetSeatCount != MIN_PET_SEAT_COUNT);
    }

    private void setupWifiOptionButton() {
        radioGroupWifiOption.setOnCheckedChangeListener(((group, checkedId) -> {
            RadioButton radioButton = findViewById(checkedId);
            String selectedWifiOption = radioButton.getText().toString();

            if (selectedWifiOption.equals("No")) {
                if(wifiSelected != 0) {
                    wifiSelected = 0;
                    addonTotalFee -= WIFI_FEE;
                }
            } else {
                if (wifiSelected != 1) {
                    wifiSelected = 1;
                    addonTotalFee += WIFI_FEE;

                }
            }

            totalPriceTV.setText("$" + (flightTotalFee + addonTotalFee));

        }));
    }

    private void setupWheelchairOptionButton() {
        radioGroupWheelchairOption.setOnCheckedChangeListener(((group, checkedId) -> {
            RadioButton radioButton = findViewById(checkedId);
            String selectedWheelchairOption = radioButton.getText().toString();

            if (selectedWheelchairOption.equals("No")) {
                if(wheelChairSelected != 0) {
                    wheelChairSelected = 0;
                }
            } else {
                if (wheelChairSelected != 1) {
                    wheelChairSelected = 1;
                }
            }

        }));
    }


    private void setupConfirmButton() {
        confirmBtn = findViewById(R.id.btnConfirmExtra);
        iFlightBookingHandler flightBookingHandler = new FlightBookingHandler();

        confirmBtn.setOnClickListener(v -> {
            Session.getInstance().getBookingInfo().setAddonsPrice(addonTotalFee);

            List<iFlightInfo> flightInfoList =  Session.getInstance().getFlightInfoCompleted();

            flightBookingHandler.storeAddons(currentBagCount, currentPetSeatCount, wifiSelected, wheelChairSelected, flightInfoList);

            Intent intent = new Intent(Addons.this, CreditCardPaymentActivity.class);
            startActivity(intent);
        });


    }




    // Checks if user has selected oneway or round trip and hides card-view accordingly
    private void checkTripWay() {
        iFlightSearch flightSearch =  Session.getInstance().getFlightSearch();

        if (flightSearch != null) {
            boolean isOneWay = flightSearch.isOneWay();

            MaterialCardView inboundCard = findViewById(R.id.inboundCard);

            if (isOneWay) {
                inboundCard.setVisibility(View.GONE);
            } else {
                inboundCard.setVisibility(View.VISIBLE);
            }

        }
    }

    // update card-view values with selected flight info
    private void updateReviewSection() {

        TextView departOriginCode, departTakeoffTime, departMidCode, departDestCode, departLandingTime;
        TextView returnOriginCode, returnTakeoffTime, returnMidCode, returnDestCode, returnLandingTime;
        TextView totalPrice;

        departOriginCode = findViewById(R.id.departOrgCodeTV);
        departTakeoffTime = findViewById(R.id.departTakeoffTV);
        departMidCode = findViewById(R.id.departMidCodeTV);
        departDestCode = findViewById(R.id.departDestCodeTV);
        departLandingTime = findViewById(R.id.departLandingTimeTV);

        returnOriginCode = findViewById(R.id.returnOrgCodeTV);
        returnTakeoffTime = findViewById(R.id.returnTakeoffTV);
        returnMidCode = findViewById(R.id.returnMidCodeTV);
        returnDestCode = findViewById(R.id.returnDestCodeTV);
        returnLandingTime = findViewById(R.id.returnLandingTimeTV);

        totalPrice = findViewById(R.id.totalPriceTV);

        MaterialCardView inboundCard = findViewById(R.id.inboundCard);

        setupCardView(departOriginCode, departTakeoffTime, departMidCode, departDestCode, departLandingTime, "Outbound");
        if (inboundCard.getVisibility() == View.VISIBLE) {
            setupCardView(returnOriginCode, returnTakeoffTime, returnMidCode, returnDestCode, returnLandingTime, "Inbound");
        }

        totalPrice.setText("$" + flightTotalFee);
    }


    // helper method to setup card-view
    private void setupCardView(TextView originCode, TextView takeoffTime, TextView midCode, TextView destCode, TextView landingTime, String key) {
        HashMap<String, List<List<iFlight>>> selectedFlight = Session.getInstance().getSelectedFlights();
        if (selectedFlight != null && selectedFlight.containsKey(key)) {
            List<List<iFlight>> flights = selectedFlight.get(key);

            if (flights != null) {
                iFlight firstFlight = flights.get(0).get(0);

                originCode.setText(firstFlight.getDeparture_icao());
                takeoffTime.setText(parseTime(firstFlight.getFlight_dept_date_time()));

                if (flights.size() > 1) {
                    String middleAirports = "";

                    middleAirports += firstFlight.getArrival_icao();
                    midCode.setText(middleAirports);

                    iFlight lastFlight = flights.get(1).get(0);

                    destCode.setText(lastFlight.getArrival_icao());
                    String getLandingTime = parseTime(lastFlight.getFlight_arr_date_time());

                    if (getLandingTime != null) {
                        landingTime.setText(getLandingTime);
                    }

                } else {
                    midCode.setText("");

                    destCode.setText(firstFlight.getArrival_icao());

                    String getLandingTime = parseTime(firstFlight.getFlight_arr_date_time());
                    if (getLandingTime != null) {
                        landingTime.setText(getLandingTime);
                    }
                }
            }
        }
    }


    private String parseTime (String dateTime) {
        String timeOnly = null;
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            // Parse the string to obtain a Date object
            Date date = dateFormat.parse(dateTime);

            // Define the format for extracting time
            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");

            // Format the Date object to extract time only
            if (date != null) {
                timeOnly = timeFormat.format(date);
            }

        } catch (ParseException e) {
            // Print an error message if parsing fails
            System.err.println("Error parsing date: " + e.getMessage());
        }

        return timeOnly;

    }
}
