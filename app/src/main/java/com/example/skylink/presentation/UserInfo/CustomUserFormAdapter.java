package com.example.skylink.presentation.UserInfo;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.skylink.R;
import com.example.skylink.objects.Interfaces.iFlightSearch;

public class CustomUserFormAdapter extends BaseAdapter {

    private final Context mContext;
    private final iFlightSearch userInput;

    public CustomUserFormAdapter(Context mContext, iFlightSearch userInput) {
        this.mContext = mContext;
        this.userInput = userInput;
    }

    @Override
    public int getCount() {
        if (userInput != null) {
            return userInput.getTotalPassengers();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(mContext);
        View listItemView;

        if (convertView == null) {
            listItemView = inflater.inflate(R.layout.user_info_form_list_item, parent, false);
        } else {
            listItemView = convertView;
        }

        TextView passengerNum = listItemView.findViewById(R.id.tvPassengerNum);

        passengerNum.setText("Passenger #" + (position+1));

        return listItemView;
    }
}
