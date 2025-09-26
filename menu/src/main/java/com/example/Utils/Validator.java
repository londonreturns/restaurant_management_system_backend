package com.example.Utils;

import com.example.Constants.Constants;

import javax.xml.bind.ValidationException;

public class Validator {

    public static void isValidName(String name, int minLength, int maxLength) throws ValidationException {
        if (name == null || name.trim().isEmpty()) {
            throw new ValidationException("Name is empty");
        }

        if (name.length() < minLength || name.length() > maxLength) {
            throw new ValidationException("Name length is not between " + minLength + " and " + maxLength);
        }

        String regex = "^[a-zA-Z\\s]+$";
        if (!name.matches(regex)) {
            throw new ValidationException("Name contains special characters");
        }
    }

    public static void isValidNameAndUnique(String name, int minLength, int maxLength, int nameCount) throws ValidationException {
        Validator.isValidName(name, minLength, maxLength);

        if (nameCount > Constants.ZERO) {
            throw new ValidationException("Name is not unique");
        }
    }
}
