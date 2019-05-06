package com.mmall.exception;

public class ParamExcepiton extends RuntimeException{

    public ParamExcepiton() {
        super();
    }

    public ParamExcepiton(String message) {
        super(message);
    }

    public ParamExcepiton(String message, Throwable cause) {
        super(message, cause);
    }

    public ParamExcepiton(Throwable cause) {
        super(cause);
    }

    protected ParamExcepiton(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
