package com.ozone.smart.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class fieldValidation {
    private Pattern p;
    private Matcher m;
    private boolean b = false;

    public String validationResponse;

    public String validateId(String newString) {
        validationResponse = "";

        p = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
        m = p.matcher(newString);
        b = m.find();

        if (b != true) {
            validationResponse = "success";
        } else {
            validationResponse = "field should not contain special characters";
        }

        if (newString.contains(" ")) {
            validationResponse = "field should not contain spaces";
        }

        if (newString.trim().equals("")) {
            validationResponse = "field should not be blank";
        }

        return validationResponse;
    }

    public String validateString(String newString) {
        validationResponse = "";

        p = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
        m = p.matcher(newString);
        b = m.find();

        if (b != true) {
            validationResponse = "success";
        } else {
            validationResponse = "field should not contain special characters";
        }

        if (newString.trim().equals("")) {
            validationResponse  ="field should not be blank";
        }

        return validationResponse;
    }

    public String validateNumber(String newInteger) {
        validationResponse = "";

        p = Pattern.compile("[^0-9 ]", Pattern.CASE_INSENSITIVE);
        m = p.matcher(newInteger);
        b = m.find();

        if (b != true) {
            validationResponse = "success";
        } else {
            validationResponse = "field should not contain special characters";
        }

        if (newInteger.trim().equals("")) {
            validationResponse  ="field should not be blank";
        }

        return validationResponse;
    }

    public String validateDate(String strDate) {
        validationResponse = "";

        //DateFormat format = new SimpleDateFormat("dd/MM/yyyy");        
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        
        //format.setLenient(false);
        try {
            format.parse(strDate);
            validationResponse  ="success";
        } catch (Exception e) {
            validationResponse= "Date format must be " + ((DateTimeFormatter) format).toString();
        }

        return validationResponse;
    }
    
    public String validateQDate(String strDate) {
        validationResponse = "";

        DateFormat format = new SimpleDateFormat("yyyy-mm-dd");
        format.setLenient(false);
        try {
            format.parse(strDate);
            validationResponse  ="success";
        } catch (ParseException e) {
            validationResponse= "Date format must be " + ((SimpleDateFormat) format).toPattern();
        }

        return validationResponse;
    }

}