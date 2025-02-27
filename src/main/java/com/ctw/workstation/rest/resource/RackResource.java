package com.ctw.workstation.rest.resource;

import com.ctw.workstation.exception.EmptyException;
import com.ctw.workstation.exception.GeneralException;
import com.ctw.workstation.item.Rack;
import com.ctw.workstation.rest.util.RestUtil;
import com.ctw.workstation.rest.dto.RackDTO;
import com.ctw.workstation.service.RackService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.Set;

@Path("/racks")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RackResource {
    @Inject
    RackService svc;

    @POST
    public Response createRack(RackDTO rack) {
        try {
            Long id = svc.addRack(rack.serial(), rack.location(), rack.teamName());
            Rack r = svc.findRack(id);
            return RestUtil.generateCreated(r);
        } catch (RuntimeException e) {
            return Response.serverError().entity(e.getMessage()).build();
        }
    }

    @GET
    public Response getAllAvailableRacks() {
        try {
            Set<Rack> racks = svc.findAllAvailableRacks();
            return Response.ok(racks).build();
        } catch (GeneralException e) {
            return Response.status(e.getStatus()).entity(e.getMessage()).build();
        } catch (RuntimeException e) {
            return Response.serverError().entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("/{id}")
    public Response getRackById(@PathParam("id") long id) {
        try {
            Rack r = svc.findRack(id);
            return Response.ok(r).build();
        } catch (RuntimeException e) {
            return Response.serverError().entity(e.getMessage()).build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response deleteRack(@PathParam("id") long id) {
        try {
            svc.removeRack(id);
            return Response.noContent().build();
        } catch (RuntimeException e) {
            return Response.serverError().entity(e.getMessage()).build();
        }
    }
/*
    @PUT
    @Path("/{id}")
    public Response updateRack(@PathParam("id") int id, NameDTO rack) {
        try {
            RackService.getInstance().updateRack(id, rack.name());
            return Response.ok().build();
        } catch (RuntimeException e) {
            return Response.serverError().entity(e.getMessage()).build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response deleteRack(@PathParam("id") int id) {
        try {
            RackService.getInstance().removeRack(id);
            return Response.ok().build();
        } catch (RuntimeException e) {
            return Response.serverError().entity(e.getMessage()).build();
        }
    }
*/

}
