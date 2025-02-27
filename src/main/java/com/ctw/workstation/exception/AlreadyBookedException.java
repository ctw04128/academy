package com.ctw.workstation.exception;

import com.ctw.workstation.util.Message;
import com.ctw.workstation.item.DataType;
import jakarta.ws.rs.core.Response;

public class AlreadyBookedException extends DataException {

    public AlreadyBookedException(final DataType type, final Long id) {
        super(Message.ALREADY_BOOKED, type, id, Response.Status.FORBIDDEN);
    }
}
