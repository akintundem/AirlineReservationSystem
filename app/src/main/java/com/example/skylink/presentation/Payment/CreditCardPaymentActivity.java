package com.example.skylink.presentation.Payment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.skylink.R;
import com.example.skylink.application.Services;
import com.example.skylink.business.Implementations.FlightBookingHandler;
import com.example.skylink.business.Interface.iFlightBookingHandler;
import com.example.skylink.presentation.Session;
import com.example.skylink.presentation.ISession;
import com.example.skylink.business.validations.IValidatePayment;
import com.example.skylink.business.validations.ValidatePayment;
import com.example.skylink.objects.Interfaces.iFlight;
import com.example.skylink.objects.Interfaces.iFlightSearch;
import com.google.android.material.card.MaterialCardView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class CreditCardPaymentActivity extends AppCompatActivity {

    private EditText cardNum, expiryDate, cvv, cardholderName, billingAddress;
    private ISession session = Session.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credit_card_payment);

        checkTripWay();

        updateReviewSection();

        cardNum = findViewById(R.id.etCreditCardNumber);
        expiryDate = findViewById(R.id.etExpirationDate);
        cvv = findViewById(R.id.etCVV);
        cardholderName = findViewById(R.id.etCardholderName);
        billingAddress = findViewById(R.id.etBillingAddress);


        Button btnPay = findViewById(R.id.btnPay);
        btnPay.setOnClickListener(v -> {

            if (isValid()) {
                addToSession();
                iFlightBookingHandler flightHandler = new FlightBookingHandler(Services.getFlightBookingDB(),Services.getBookDatabase(),Services.getFlightDatabase());
                String flightBooked = flightHandler.addConfirmBookings(session.getUserProperties().getUser_id(), session.getFlightInfoCompleted(), Session.getInstance().getBookingInfo().getAddonsPrice());
                if(!flightBooked.isEmpty()){
                    Intent intent = new Intent(CreditCardPaymentActivity.this, PaymentSuccessfulActivity.class);
                    startActivity(intent);
                }

            }
        });
    }

    private void checkTripWay() {
        iFlightSearch flightSearch = session.getFlightSearch();

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

        totalPrice = findViewById(R.id.totalPrice);

        MaterialCardView inboundCard = findViewById(R.id.inboundCard);

        setupCardView(departOriginCode, departTakeoffTime, departMidCode, departDestCode, departLandingTime, "Outbound");
        if (inboundCard.getVisibility() == View.VISIBLE) {
            setupCardView(returnOriginCode, returnTakeoffTime, returnMidCode, returnDestCode, returnLandingTime, "Inbound");
        }
        int total = Session.getInstance().getBookingInfo().getOutboundPrice() + Session.getInstance().getBookingInfo().getInboundPrice() + Session.getInstance().getBookingInfo().getAddonsPrice();
        totalPrice.setText("$" + total);
    }

    private void setupCardView(TextView originCode, TextView takeoffTime, TextView midCode, TextView destCode, TextView landingTime, String key) {
        HashMap<String, List<List<iFlight>>> selectedFlight = session.getSelectedFlights();
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

    private void addToSession() {
        Session.getInstance().getCreditCard().setCardNum(cardNum.getText().toString());
        Session.getInstance().getCreditCard().setExpiryDate(expiryDate.getText().toString());
        Session.getInstance().getCreditCard().setCvv(cvv.getText().toString());
        Session.getInstance().getCreditCard().setCardholderName(cardholderName.getText().toString());
        Session.getInstance().getCreditCard().setBillingAddress(billingAddress.getText().toString());
    }

//    Client Side Validation.
    private boolean isValid(){
        boolean isValid = true;

        IValidatePayment validatePayment = new ValidatePayment();
        String error = "";

        error = validatePayment.validCardNum(cardNum.getText().toString());
        if (!error.isEmpty()) {
            cardNum.setError(error);
            isValid = false;
        }

        error = validatePayment.validExpiryDate(expiryDate.getText().toString());
        if (!error.isEmpty()) {
            expiryDate.setError(error);
            isValid = false;
        }

        error = validatePayment.validCVV(cvv.getText().toString());
        if (!error.isEmpty()) {
            cvv.setError(error);
            isValid = false;
        }

        error = validatePayment.validCardholderName(cardholderName.getText().toString());
        if (!error.isEmpty()) {
            cardholderName.setError(error);
            isValid = false;
        }

        error = validatePayment.validBillingAddress(billingAddress.getText().toString());
        if (!error.isEmpty()) {
            billingAddress.setError(error);
            isValid = false;
        }

        return isValid;
    }
}

