package com.sports.cricket.validations;

import com.sports.cricket.model.UserLogin;
import com.sports.cricket.validator.EmailValidator;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class FormValidator implements Serializable {

    private static final String EMPTY_STRING ="";

    EmailValidator emailValidator = new EmailValidator();

    List<ErrorDetails> errorsList = null;

    public List<ErrorDetails> isLoginValid(UserLogin userLogin){

        errorsList = new ArrayList<>();
        ErrorDetails errorDetails;

        if(null == userLogin){
            errorDetails = new ErrorDetails();
            errorDetails.setErrorField("userLogin");
            errorDetails.setErrorMessage("User Login details are empty..!!");
            errorsList.add(errorDetails);
        }

        if(null != userLogin){
            if(null == userLogin.getEmail() || userLogin.getEmail().isEmpty()){
                errorDetails = new ErrorDetails();
                errorDetails.setErrorField("email");
                errorDetails.setErrorMessage("Dude, Email Id is required!");
                errorsList.add(errorDetails);
            }else if(null != userLogin.getEmail() && !userLogin.getEmail().isEmpty()){
                if(!emailValidator.valid(userLogin.getEmail())) {
                    errorDetails = new ErrorDetails();
                    errorDetails.setErrorField("email");
                    errorDetails.setErrorMessage("Invalid Email ID format bruh!");
                    errorsList.add(errorDetails);
                }
            }

            if(null == userLogin.getPassword() || userLogin.getPassword().isEmpty()){
                errorDetails = new ErrorDetails();
                errorDetails.setErrorField("password");
                errorDetails.setErrorMessage("Password dude?");
                errorsList.add(errorDetails);
            }
        }

        return errorsList;
    }
}
