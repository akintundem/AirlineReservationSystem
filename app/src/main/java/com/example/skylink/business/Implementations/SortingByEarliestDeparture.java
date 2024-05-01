package com.example.skylink.business.Implementations;

import com.example.skylink.business.Interface.ISortingOption;
import com.example.skylink.objects.Interfaces.iFlight;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class SortingByEarliestDeparture implements ISortingOption {
    @Override
    public int compare(List<List<iFlight>> flightList1, List<List<iFlight>> flightList2) {
        long earliestDeparture1 = getEarliestDeparture(flightList1);
        long earliestDeparture2 = getEarliestDeparture(flightList2);
        return Long.compare(earliestDeparture1, earliestDeparture2);
    }

    private long getEarliestDeparture(List<List<iFlight>> flightList) {
        long earliestDeparture = Long.MAX_VALUE;
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");

        if (!flightList.isEmpty() && !flightList.get(0).isEmpty()) {
            String flightDeparture = flightList.get(0).get(0).getFlight_dept_date_time();

            try {
                Date departureDateTime = dateFormat.parse(flightDeparture);
                long departureTime = departureDateTime.getTime();
                earliestDeparture = departureTime;
            } catch (ParseException e) {
                // if try fails, earliestDeparture will have Max Value
                e.printStackTrace();
                return earliestDeparture;
            }
        }

        return earliestDeparture;
    }
}
