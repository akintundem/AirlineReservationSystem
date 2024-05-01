package com.example.skylink.presentation.User_Auth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.skylink.R;
import com.example.skylink.application.Services;
import com.example.skylink.presentation.Session;
import com.example.skylink.business.Interface.IUserHandler;
import com.example.skylink.business.Implementations.UserHandler;
import com.example.skylink.objects.Interfaces.IUserProperties;
import com.example.skylink.objects.Implementations.UserProperties;

public class SignUpActivity extends AppCompatActivity {

    private EditText fullname, email, password, retypePassword;
    private TextView signIn;
    private Button signUp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        initializeViews();
        setupSignInClickListener();
        setupSignUpClickListener();
    }

    private void initializeViews() {
        fullname = findViewById(R.id.etFullname);
        email = findViewById(R.id.etEmail);
        password = findViewById(R.id.etPassword);
        retypePassword = findViewById(R.id.etRePassword);
    }

    private void setupSignInClickListener() {
        signIn = findViewById(R.id.tvSignInClick);
        signIn.setOnClickListener(v -> {
            navigateToSignInActivity();
        });
    }

    private void navigateToSignInActivity() {
        Intent intent = new Intent(SignUpActivity.this, SignInActivity.class);
        startActivity(intent);
    }

    private void setupSignUpClickListener() {
        signUp = findViewById(R.id.btnSignUp);
        signUp.setOnClickListener(v -> {
            attemptSignUp();
        });
    }

    private void attemptSignUp() {

            String userFullname = fullname.getText().toString();
            String userEmail = email.getText().toString();
            String userPassword = password.getText().toString();
            String userRePassword = retypePassword.getText().toString();

            IUserProperties user = new UserProperties(userFullname, userEmail, userPassword);
            IUserHandler handler = new UserHandler(Services.getUserDatabase());

            try {
                handler.createUser(user, userRePassword);
                Session.getInstance().getUserProperties().setEmail(userEmail);
                navigateToUpdateUserProfileActivity();
            } catch (UserHandler.UserValidationException e) {
                showErrorMessage(e.getMessage());
            }
    }

    private void navigateToUpdateUserProfileActivity() {
        Intent intent = new Intent(SignUpActivity.this, UpdateUserProfileActivity.class);
        startActivity(intent);
    }

    private void showErrorMessage(String message) {
        Toast.makeText(SignUpActivity.this, message, Toast.LENGTH_SHORT).show();
    }
}