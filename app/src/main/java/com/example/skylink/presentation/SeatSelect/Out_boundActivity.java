package com.example.skylink.presentation.SeatSelect;

import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.skylink.R;
import com.example.skylink.presentation.Session;
import com.example.skylink.objects.Interfaces.iPassengerData;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

public class Out_boundActivity extends AppCompatActivity {

    private AtomicReference<iPassengerData> selectedPassenger = new AtomicReference<>(null);
    private TextView departingAirportTextView, arrivingAirportTextView, departureTimeTextView, arrivalTimeTextView;
    private HashMap<iPassengerData, String> seatMap = new HashMap<>();
    private Map<String, SeatStatus> seatStatusMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.outbound_layout); // Make sure to use the correct layout file

        departingAirportTextView = findViewById(R.id.departingAirportTextView);
        arrivingAirportTextView = findViewById(R.id.arrivingAirportTextView);
        departureTimeTextView = findViewById(R.id.departureTimeTextView);
        arrivalTimeTextView = findViewById(R.id.arrivalTimeTextView);
        SeatSelectionUtils.setupFlightInformation("Outbound", departingAirportTextView, arrivingAirportTextView, departureTimeTextView, arrivalTimeTextView);
        SeatSelectionUtils.setupPassengerSpinner(findViewById(R.id.namesSpinner), seatMap, this, selectedPassenger);

        LinearLayout seatLayout = SeatSelectionUtils.setupSeatsLayout("Outbound",this, seatStatusMap, seatMap, selectedPassenger);

        LinearLayout seatLayoutContainer = findViewById(R.id.Flight_Layout);
        seatLayoutContainer.addView(seatLayout);

        Button confirmButton = findViewById(R.id.confirmButton);
        SeatSelectionUtils.setupConfirmButton(this, confirmButton, this, seatMap,Session.getInstance().getFlightSearch().isOneWay(), "Outbound");
    }
}
