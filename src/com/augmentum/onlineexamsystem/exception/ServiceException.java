package com.augmentum.onlineexamsystem.exception;

public class ServiceException extends Exception {

    private static final long serialVersionUID = 112994178291824829L;
    private int code;
    private String message;

    public ServiceException(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
