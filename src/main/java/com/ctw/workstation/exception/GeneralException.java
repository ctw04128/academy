package com.ctw.workstation.exception;

import jakarta.ws.rs.core.Response;

public class GeneralException extends RuntimeException {
    private final Response.Status status;

    public GeneralException(String message, Response.Status status) {
        super(message);
        this.status = status;
    }

    public Response.Status getStatus() {
        return status;
    }
}
