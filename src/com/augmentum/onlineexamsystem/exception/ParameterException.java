package com.augmentum.onlineexamsystem.exception;

import java.util.HashMap;
import java.util.Map;

public class ParameterException extends Exception {

    private static final long serialVersionUID = -2394352420142453995L;
    private Map<String, String> errors = new HashMap<String, String>();

    public Map<String, String> getErrors() {
        return errors;
    }

    public void setErrors(Map<String, String> errors) {
        this.errors = errors;
    }

    public void addErrors(String errorKey, String errorValue) {
        errors.put(errorKey, errorValue);
    }

    public boolean isError() {
        return !errors.isEmpty();
    }
}
