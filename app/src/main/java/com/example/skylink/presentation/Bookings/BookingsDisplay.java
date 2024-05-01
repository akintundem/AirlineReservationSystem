package com.example.skylink.presentation.Bookings;

import static com.example.skylink.R.*;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.skylink.R;
import com.example.skylink.business.Implementations.FlightBookingHandler;
import com.example.skylink.objects.Interfaces.iFlightInfo;
import com.example.skylink.presentation.Session;
import java.util.List;

public class BookingsDisplay extends AppCompatActivity {

    private RecyclerView bookingsRecyclerView;
    private TextView noBookedFlightTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.bookings_view);

        noBookedFlightTV = findViewById(id.noBookedFlightTextV);

        bookingsRecyclerView = findViewById(id.bookingsRecyclerView);
        bookingsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        long userid = Session.getInstance().getUserProperties().getUser_id();
        FlightBookingHandler flightBookingHandler = new FlightBookingHandler(true);
        List<iFlightInfo> bookings = flightBookingHandler.getBookingDetails(userid);

        if (bookings != null && !bookings.isEmpty()) {

            noBookedFlightTV.setVisibility(View.GONE);
            bookingsRecyclerView.setVisibility(View.VISIBLE);

            BookingsAdapter bookingsAdapter = new BookingsAdapter(bookings);
            bookingsRecyclerView.setAdapter(bookingsAdapter); // Set the adapter to the RecyclerView

        } else {

            noBookedFlightTV.setVisibility(View.VISIBLE);
            bookingsRecyclerView.setVisibility(View.GONE);

        }

    }
}
