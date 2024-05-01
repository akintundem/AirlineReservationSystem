package com.example.skylink.presentation.SeatSelect;

import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.skylink.R;
import com.example.skylink.objects.Interfaces.iPassengerData;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

public class InboundActivity extends AppCompatActivity {

    private AtomicReference<iPassengerData> selectedPassenger = new AtomicReference<>(null);
    private TextView departingAirportTextView, arrivingAirportTextView, departureTimeTextView, arrivalTimeTextView;
    private HashMap<iPassengerData, String> seatMap = new HashMap<>();
    private Map<String, SeatStatus> seatStatusMap = new HashMap<>();
    private TextView selectedPassengerTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inboundlayout);

        departingAirportTextView = findViewById(R.id.departingAirportTextView);
        arrivingAirportTextView = findViewById(R.id.arrivingAirportTextView);
        departureTimeTextView = findViewById(R.id.departureTimeTextView);
        arrivalTimeTextView = findViewById(R.id.arrivalTimeTextView);
        SeatSelectionUtils.setupFlightInformation("Inbound", departingAirportTextView, arrivingAirportTextView, departureTimeTextView, arrivalTimeTextView);
        SeatSelectionUtils.setupPassengerSpinner(findViewById(R.id.namesSpinner),seatMap, this,selectedPassenger);

        LinearLayout seatLayout = SeatSelectionUtils.setupSeatsLayout("Inbound",this, seatStatusMap, seatMap, selectedPassenger);

        // Assuming you have a LinearLayout in your layout with ID "seatLayoutContainer"
        LinearLayout seatLayoutContainer = findViewById(R.id.Flight_Layout);
        seatLayoutContainer.addView(seatLayout);

        Button confirmButton = findViewById(R.id.confirmButton);

        SeatSelectionUtils.setupConfirmButton(this, confirmButton, this, seatMap,true, "Inbound");



    }



}
