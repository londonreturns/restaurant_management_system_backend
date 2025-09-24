package com.example.Utils;

import javax.xml.bind.ValidationException;

public class Validator {
    public static void isValidName(String name, int minLength, int maxLength) throws ValidationException {
        if (name == null || name.trim().isEmpty()) {
            throw new ValidationException("Name is empty");
        }
        if (name.length() < minLength || name.length() > maxLength) {
            throw new ValidationException("Name is not between " + minLength + " and " + maxLength);
        }
        String regex = "^[a-zA-Z]+$";
        if (!name.matches(regex)) {
            throw new ValidationException("Name contains special characters");
        }
    }
}
