package com.ctw.workstation.exception;

import com.ctw.workstation.item.DataType;
import com.ctw.workstation.util.Message;
import jakarta.ws.rs.core.Response;

public abstract class DataException extends GeneralException {
    public DataException(Message message, DataType type, Long id, Response.Status status) {
        super(String.format(message.toString(), type, id), status);
    }
    public DataException(Message message, DataType type, String name, Response.Status status) { super(String.format(message.toString(), type, name), status);}

}
