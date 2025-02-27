package com.ctw.workstation.exception;

import com.ctw.workstation.item.DataType;
import com.ctw.workstation.util.Message;
import jakarta.ws.rs.core.Response;

public abstract class DatabaseException extends GeneralException {
    public DatabaseException(Message message, DataType type, Response.Status status) {
        super(String.format(message.toString(), type), status);
    }
}
