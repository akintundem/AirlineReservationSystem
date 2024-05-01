package com.example.skylink.business.Implementations;

import com.example.skylink.application.Services;
import com.example.skylink.business.Interface.IUserHandler;
import com.example.skylink.business.validations.IValidateUserProperties;
import com.example.skylink.business.validations.ValidateUserProperties;
import com.example.skylink.objects.Interfaces.IUserProperties;
import com.example.skylink.presentation.Session;
import com.example.skylink.persistence.Interfaces.IUserDB;
import org.mindrot.jbcrypt.BCrypt;

public class UserHandler implements IUserHandler {
    private IUserDB userDB;

    public UserHandler(boolean forProduction){
        this.userDB = Services.getUserDatabase();
    }

    public UserHandler(IUserDB userDB) {
        this.userDB = userDB;
    }

    public void createUser(IUserProperties userProperties, String rePassword) throws UserValidationException {

        String validationMessage = isValidUserPropertiesForCreation(userProperties);
        if (!validationMessage.isEmpty()) {
            throw new UserValidationException(validationMessage);
        }

        if (!userProperties.getPassword().equals(rePassword)) {
            throw new UserValidationException("Passwords do not match");
        }

        String hashedRePassword = BCrypt.hashpw(rePassword, BCrypt.gensalt());
        userProperties.setPassword(hashedRePassword);

        long userId = userDB.addUser_Auth(userProperties);

        if (userId == -1) {
            throw new UserValidationException("Unable to create new user");
        }

        Session.getInstance().getUserProperties().setUser_id(userId);
    }

    public String isValidUserPropertiesForCreation(IUserProperties userProperties) {
        IValidateUserProperties validator = new ValidateUserProperties();

        String fullNameValidation = validator.validFullname(userProperties.getFullName());
        if (!fullNameValidation.isEmpty()) {
            return fullNameValidation;
        }

        String emailValidation = validator.validEmail(userProperties.getEmail());
        if (!emailValidation.isEmpty()) {
            return emailValidation;
        }

        String passwordValidation = validator.validPassword(userProperties.getPassword());
        if (!passwordValidation.isEmpty()) {
            return passwordValidation;
        }
        
        return "";
    }

    public String isValidUserPropertiesForUpdate(IUserProperties userProperties) {

        IValidateUserProperties validator = new ValidateUserProperties();

        String baseUserPropertiesValidation = isValidUserPropertiesForCreation(userProperties);
        if (!baseUserPropertiesValidation.isEmpty()) {
            return baseUserPropertiesValidation;
        }

        // Validate address
        String addressValidation = validator.validAddress(userProperties.getAddress());
        if (!addressValidation.isEmpty()) {
            return addressValidation;
        }

        // Validate phone
        String phoneValidation = validator.validPhone(userProperties.getPhone());
        if (!phoneValidation.isEmpty()) {
            return phoneValidation;
        }

        // Validate gender
        String genderValidation = validator.validGender(userProperties.getGender());
        if (!genderValidation.isEmpty()) {
            return genderValidation;
        }

        // Validate date of birth
        String dobValidation = validator.validDOB(userProperties.getDateOfBirth());
        if (!dobValidation.isEmpty()) {
            return dobValidation;
        }

        return "";
    }

    public boolean updateUserProfile(IUserProperties userProperties) throws UserValidationException {

        IValidateUserProperties validator = new ValidateUserProperties();

        if (userProperties == null) {
            return false;
        }

        // We've already made sure the users basic info is valid but it doesn't hurt
        String validationMessage = isValidUserPropertiesForUpdate(userProperties);
        if (!validationMessage.isEmpty()) {
            throw new UserValidationException(validationMessage);
        }

        String validateAddress = validator.validAddress(userProperties.getAddress());
        if(!validateAddress.isEmpty()){
            throw new UserValidationException(validateAddress);
        }

        String validateDateOfBirth = validator.validDOB(userProperties.getDateOfBirth());
        if(!validateDateOfBirth.isEmpty()) {
            throw new UserValidationException(validateDateOfBirth);
        }

        long user_id = Session.getInstance().getUserProperties().getUser_id();

        try {
            return userDB.update_user_info(user_id, userProperties);
        } catch (Exception e) {
            throw new UserValidationException(e.getMessage());
        }
    }

    public boolean signinUser(IUserProperties userProperties) throws UserValidationException {
        IValidateUserProperties validator = new ValidateUserProperties();

        String email = userProperties.getEmail();
        String emailValidation = validator.validEmail(userProperties.getEmail());
        if (!emailValidation.isEmpty()) {
            throw new UserValidationException(emailValidation);
        }

        String passwordValidation = validator.validPassword(userProperties.getPassword());
        if (!passwordValidation.isEmpty()) {
            throw new UserValidationException(passwordValidation);
        }

        String providedPassword = userProperties.getPassword();

        try {
            String password_db = userDB.findPassword(email);
            if (BCrypt.checkpw(providedPassword, password_db)) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public long getUserIdByEmail(String email) {

        try {
            long userId = userDB.getUserId(email);
            return userId;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return -1;
    }

    @Override
    public IUserProperties getUserByEmail(String email) {
        return userDB.getUserByEmail(email);
    }

    public class UserValidationException extends Exception {
        public UserValidationException(String message) {
            super(message);
        }
    }
}