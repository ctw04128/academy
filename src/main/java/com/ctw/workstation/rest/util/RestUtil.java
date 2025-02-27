package com.ctw.workstation.rest.util;

import com.ctw.workstation.item.Rack;
import com.ctw.workstation.rest.dto.IdentifierDTO;
import jakarta.ws.rs.core.Response;

public final class RestUtil {
    public static Response generateCreated(Long id) {
        return Response.status(Response.Status.CREATED).entity(new IdentifierDTO(id)).build();
    }

    public static Response generateCreated(Object obj) {
        return Response.status(Response.Status.CREATED).entity(obj).build();
    }
}
