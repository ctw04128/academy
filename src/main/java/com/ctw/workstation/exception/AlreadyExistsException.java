package com.ctw.workstation.exception;

import com.ctw.workstation.item.DataType;
import com.ctw.workstation.util.Message;
import jakarta.ws.rs.core.Response;

public class AlreadyExistsException extends DataException {
    public AlreadyExistsException(final DataType type, final String name) {
        super(Message.ALREADY_EXISTS, type, name, Response.Status.FORBIDDEN);
    }
}
