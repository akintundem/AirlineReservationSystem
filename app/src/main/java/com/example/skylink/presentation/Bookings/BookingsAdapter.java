package com.example.skylink.presentation.Bookings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.skylink.R;
import com.example.skylink.objects.Interfaces.iFlight;
import com.example.skylink.objects.Interfaces.iFlightInfo;
import java.util.List;

public class BookingsAdapter extends RecyclerView.Adapter<BookingsAdapter.BookingViewHolder> {

    private List<iFlightInfo> bookingsList;

    public BookingsAdapter(List<iFlightInfo> bookingsList) {
        this.bookingsList = bookingsList;
    }

    @NonNull
    @Override
    public BookingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.booking_card, parent, false);
        return new BookingViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull BookingViewHolder holder, int position) {
        iFlightInfo booking = bookingsList.get(position);

        holder.textViewBookingNumber.setText(booking.getBookingNum().toString());
        holder.textViewEconOrBus.setText(booking.getEconOrBus().toString());
        holder.textViewOutboundOrInbound.setText(booking.getBound().toString());

        isBadgeVisible(holder.textViewWifiBadge, booking.getWifiOption());
        isBadgeVisible(holder.textViewAccessibilityBadge, booking.getWheelchairOption());

        holder.textViewPetsBadge.setText("Pets: " + booking.getPetCount());
        holder.textViewLuggageBadge.setText("Bags: " + booking.getBagCount());

        if (!booking.getFlight().isEmpty()) {
            iFlight flight = booking.getFlight().get(0);
            holder.textViewDepartureIcao.setText(flight.getDeparture_icao().toString());
            holder.textViewArrivalIcao.setText(flight.getArrival_icao().toString());
            holder.textViewDepartureTime.setText(flight.getFlight_dept_date_time().toString());
            holder.textViewArrivalTime.setText(flight.getFlight_arr_date_time().toString());
            holder.textViewFlightDetails.setText(flight.getAirCraft_Type() + " " + flight.getFlightNumber() + " " + flight.getArr_Gate());
        }

        boolean hasIndirectFlight = booking.getFlight().size() > 1;

        holder.layoutSecondFlightDetails.setVisibility(hasIndirectFlight ? View.VISIBLE : View.GONE);

        if (hasIndirectFlight) {
            iFlight flight = booking.getFlight().get(1);
            holder.textView2ndDepartureIcao.setText(flight.getDeparture_icao().toString());
            holder.textView2ndArrivalIcao.setText(flight.getArrival_icao().toString());
            holder.textView2ndDepartureTime.setText(flight.getFlight_dept_date_time().toString());
            holder.textView2ndArrivalTime.setText(flight.getFlight_arr_date_time().toString());
            holder.textView2ndFlightDetails.setText(flight.getAirCraft_Type() + " " + flight.getFlightNumber() + " " + flight.getArr_Gate());
        }
    }


    private void isBadgeVisible(TextView textView, int option) {
        if (option == 1) {
            textView.setVisibility(View.VISIBLE);
        } else {
            textView.setVisibility(View.GONE);
        }
    }   

    @Override
    public int getItemCount() {
        return bookingsList != null ? bookingsList.size() : 0;
    }

    public static class BookingViewHolder extends RecyclerView.ViewHolder {

        public TextView textViewBookingNumber, textViewEconOrBus, textViewDepartureIcao,
                textViewArrivalIcao, textViewDepartureTime, textViewArrivalTime, textViewFlightDetails,
                textViewWifiBadge, textViewAccessibilityBadge, textViewPetsBadge, textViewOutboundOrInbound,
                textViewLuggageBadge, textView2ndDepartureIcao, textView2ndArrivalIcao, textView2ndDepartureTime,
                textView2ndArrivalTime, textView2ndFlightDetails;

        public LinearLayout layoutSecondFlightDetails;

        public BookingViewHolder(View itemView) {
            super(itemView);
            textViewBookingNumber = itemView.findViewById(R.id.textViewBookingNumber);
            textViewEconOrBus = itemView.findViewById(R.id.textViewEconOrBus);
            textViewDepartureIcao = itemView.findViewById(R.id.textViewDepartureIcao);
            textViewArrivalIcao = itemView.findViewById(R.id.textViewArrivalIcao);
            textViewDepartureTime = itemView.findViewById(R.id.textViewDepartureTime);
            textViewArrivalTime = itemView.findViewById(R.id.textViewArrivalTime);
            textViewFlightDetails = itemView.findViewById(R.id.textViewFlightDetails);
            textViewWifiBadge = itemView.findViewById(R.id.textViewWifiBadge);
            textViewAccessibilityBadge = itemView.findViewById(R.id.textViewAccessibilityBadge);
            textViewPetsBadge = itemView.findViewById(R.id.textViewPetsBadge);
            textViewOutboundOrInbound = itemView.findViewById(R.id.textViewOutboundOrInbound);
            textViewLuggageBadge = itemView.findViewById(R.id.textViewLuggageBadge);
            textView2ndDepartureIcao = itemView.findViewById(R.id.textView2ndDepartureIcao);
            textView2ndArrivalIcao = itemView.findViewById(R.id.textView2ndArrivalIcao);
            textView2ndDepartureTime = itemView.findViewById(R.id.textView2ndDepartureTime);
            textView2ndArrivalTime = itemView.findViewById(R.id.textView2ndArrivalTime);
            textView2ndFlightDetails = itemView.findViewById(R.id.textView2ndFlightDetails);
            layoutSecondFlightDetails = itemView.findViewById(R.id.layoutSecondFlightDetails);

        }
    }
}
