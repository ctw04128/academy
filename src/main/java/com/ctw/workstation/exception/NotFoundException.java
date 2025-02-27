package com.ctw.workstation.exception;

import com.ctw.workstation.util.Message;
import com.ctw.workstation.item.DataType;
import io.quarkus.logging.Log;
import jakarta.ws.rs.core.Response;

public class NotFoundException extends DataException {

    public NotFoundException(DataType type, Long id) {
        super(Message.NOT_FOUND, type, id, Response.Status.NOT_FOUND);
        Log.tracev("{0}", getMessage());
    }

    public NotFoundException(DataType type, String name) {
        super(Message.NOT_FOUND_BY_NAME, type, name, Response.Status.NOT_FOUND);
        Log.tracev("{0}", getMessage());
    }



}
