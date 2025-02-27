package com.ctw.workstation.exception;

import com.ctw.workstation.util.Message;
import com.ctw.workstation.item.DataType;
import io.quarkus.logging.Log;
import jakarta.ws.rs.core.Response;

public class EmptyException extends DatabaseException {

    public EmptyException(DataType type) {
        super(Message.EMPTY, type, Response.Status.OK);
        Log.tracev("{0}", getMessage() + " NO CONTENT");
    }
}
