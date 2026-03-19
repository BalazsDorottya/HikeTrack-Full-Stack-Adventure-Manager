package edu.hike.idde.myproject.spring.service.exception;

public class ServiceNotFoundException extends RuntimeException {
    public ServiceNotFoundException(String s) {
        super(s);
    }

    public ServiceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
