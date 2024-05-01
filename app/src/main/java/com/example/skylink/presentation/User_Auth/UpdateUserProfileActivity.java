package com.example.skylink.presentation.User_Auth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.skylink.R;
import com.example.skylink.application.Services;
import com.example.skylink.business.validations.IValidateUserProperties;
import com.example.skylink.business.validations.ValidateUserProperties;
import com.example.skylink.presentation.Session;
import com.example.skylink.business.Implementations.UserHandler;
import com.example.skylink.business.Interface.IUserHandler;
import com.example.skylink.objects.Interfaces.IUserProperties;

import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.skylink.presentation.FlightSearching.FlightSearchP;

public class UpdateUserProfileActivity extends AppCompatActivity {

    private EditText address, city, province, phone, dateOfBirth, gender;
    private Button submit;
    IUserHandler handler;
    IUserProperties user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_user_profile);
        initializeViews();
        handler = new UserHandler(Services.getUserDatabase());
        fetchUserData();
        setupSubmitClickListener();

        submit = findViewById(R.id.btnSubmit);
    }

    private void setupSubmitClickListener() {
        submit.setOnClickListener(v -> {
            handleSubmitClick();
        });
    }


    private void handleSubmitClick() {
        String addressText = address.getText().toString();
        String cityText = city.getText().toString();
        String provinceText = province.getText().toString();
        String phoneText = phone.getText().toString();
        String dobText = dateOfBirth.getText().toString();
        String genderText = gender.getText().toString();

        try {
            user.setAddress(addressText + ", " + cityText + ", " + provinceText);
            user.setPhone(phoneText);
            user.setDateOfBirth(dobText);
            user.setGender(genderText);
            if(handler.updateUserProfile(user)) {
                Intent flightSearchIntent = new Intent(UpdateUserProfileActivity.this, FlightSearchP.class);
                startActivity(flightSearchIntent);
                Toast.makeText(UpdateUserProfileActivity.this, "Profile updated successfully", Toast.LENGTH_SHORT).show();
            }
        } catch (UserHandler.UserValidationException e) {
            showErrorMessage(e.getMessage());
        }
    }

    private void showErrorMessage(String message) {
        Toast.makeText(UpdateUserProfileActivity.this, message, Toast.LENGTH_SHORT).show();
    }

    private void initializeViews() {
        address = findViewById(R.id.etAddress);
        city = findViewById(R.id.etCity);
        province = findViewById(R.id.etProvince);
        phone = findViewById(R.id.etPhone);
        dateOfBirth = findViewById(R.id.etDoB);
        gender = findViewById(R.id.etGender);
        submit = findViewById(R.id.btnSubmit);
    }

    private void fetchUserData() {
        String userEmail = Session.getInstance().getUserProperties().getEmail();

        user = handler.getUserByEmail(userEmail);

        if (user != null) {
            updateWelcomeMessage(user.getFullName());

            if (user.getAddress() != null) {
                String[] addressDB = user.getAddress().split(",");
                address.setText(addressDB.length > 0 ? addressDB[0] : "");
            }

            phone.setText(user.getPhone() != null ? user.getPhone() : "");
            gender.setText(user.getGender() != null ? user.getGender() : "");
            dateOfBirth.setText(user.getDateOfBirth() != null ? user.getDateOfBirth() : "");
        } else {
            handleUserNotFound();
        }
    }
    private void updateWelcomeMessage(String userName) {
        TextView tvWelcomeTitle = findViewById(R.id.tvWelcomeTitle);
        tvWelcomeTitle.setText("Hello, " + userName);
    }

    private void handleUserNotFound() {
        Toast.makeText(this, "User not found", Toast.LENGTH_SHORT).show();
        Intent fallbackIntent = new Intent(this, SignUpActivity.class);
        startActivity(fallbackIntent);
    }

}