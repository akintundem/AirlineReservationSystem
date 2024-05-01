package com.example.skylink.presentation.UserInfo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.skylink.R;
import com.example.skylink.application.Services;
import com.example.skylink.presentation.Session;
import com.example.skylink.business.Interface.iPassengerDataManager;
import com.example.skylink.business.validations.IValidateUserProperties;
import com.example.skylink.business.validations.ValidateUserProperties;
import com.example.skylink.business.Implementations.PassengerDataManager;
import com.example.skylink.objects.Interfaces.iPassengerData;
import com.example.skylink.presentation.SeatSelect.Out_boundActivity;

import java.util.ArrayList;
import java.util.List;
public class User_info extends AppCompatActivity {

    private iPassengerDataManager passengerDataManager;
    private CustomUserFormAdapter userFormAdapter;
    private ListView userFormList;
    private Button submitBtn;
    private List<iPassengerData> passengers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        passengerDataManager = new PassengerDataManager(Services.getBookDatabase());
        passengers = new ArrayList<>();
        initializeViews();
        initializeListeners();

    }

    private void initializeViews() {
        userFormList = findViewById(R.id.lvUserForms);
        submitBtn = findViewById(R.id.submitBtn);

        userFormList.setFastScrollEnabled(false);
        userFormAdapter = new CustomUserFormAdapter(getApplicationContext(), Session.getInstance().getFlightSearch());
        userFormList.setAdapter(userFormAdapter);
    }

    private void initializeListeners() {
        submitBtn.setOnClickListener(v -> handleSubmitButtonClick());
    }

    private void handleSubmitButtonClick() {
        boolean allValidForm = true;

        for (int i = 0; i < userFormList.getChildCount(); i++) {
            View innerForm = userFormList.getChildAt(i);
            if (!handleFormValidation(innerForm)) {
                allValidForm = false;
            }
        }

        if (allValidForm) {
            processValidForms();
        } else {
            Toast.makeText(User_info.this, "Passenger data invalid", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean handleFormValidation(View innerForm) {
        EditText titleEditText = innerForm.findViewById(R.id.etTitle);
        EditText firstNameEditText = innerForm.findViewById(R.id.etFirstName);
        EditText lastNameEditText = innerForm.findViewById(R.id.etLastName);
        EditText phoneNumberEditText = innerForm.findViewById(R.id.etTelephoneNumber);
        EditText emailEditText = innerForm.findViewById(R.id.etEmailAddress);

        String title = titleEditText.getText().toString();
        String firstname = firstNameEditText.getText().toString();
        String lastname = lastNameEditText.getText().toString();
        String email = emailEditText.getText().toString();
        String phoneNum = phoneNumberEditText.getText().toString();

        IValidateUserProperties validator = new ValidateUserProperties();

        boolean success = true;

        String error = validator.validTitle(title);
        if (!error.isEmpty()) {
            titleEditText.setError(error);
            success = false;
        }
        error = validator.validFirstname(firstname);
        if (!error.isEmpty()) {
            firstNameEditText.setError(error);
            success = false;
        }
        error = validator.validLastname(lastname);
        if (!error.isEmpty()) {
            lastNameEditText.setError(error);
            success = false;
        }
        error = validator.validEmail(email);
        if (!error.isEmpty()) {
            emailEditText.setError(error);
            success = false;
        }
        error = validator.validPhone(phoneNum);
        if (!error.isEmpty()) {
            phoneNumberEditText.setError(error);
            success = false;
        }

        return success;
    }

    private void processValidForms() {
        for (int i = 0; i < userFormList.getChildCount(); i++) {
            View innerForm = userFormList.getChildAt(i);

            iPassengerData newPassenger = processValidForm(innerForm);
            if (newPassenger != null) {
                passengers.add(newPassenger);
            }
        }

        Toast.makeText(User_info.this, "Passenger Data Added Successfully", Toast.LENGTH_SHORT).show();

        Session.getInstance().setPassengerData(passengers);

        // Pass the list to the next activity
        Intent nextActivityIntent = new Intent(this, Out_boundActivity.class);
        startActivity(nextActivityIntent);
    }

    private iPassengerData processValidForm(View innerForm) {
        EditText titleEditText = innerForm.findViewById(R.id.etTitle);
        EditText firstNameEditText = innerForm.findViewById(R.id.etFirstName);
        EditText lastNameEditText = innerForm.findViewById(R.id.etLastName);
        EditText phoneNumberEditText = innerForm.findViewById(R.id.etTelephoneNumber);
        EditText emailEditText = innerForm.findViewById(R.id.etEmailAddress);

        String title = titleEditText.getText().toString();
        String firstname = firstNameEditText.getText().toString();
        String lastname = lastNameEditText.getText().toString();
        String email = emailEditText.getText().toString();
        String phoneNum = phoneNumberEditText.getText().toString();

        return passengerDataManager.addBooking(title, firstname, lastname, phoneNum, email,Session.getInstance().getUserProperties().getUser_id());
    }
}
