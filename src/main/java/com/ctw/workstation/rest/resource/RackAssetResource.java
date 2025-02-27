package com.ctw.workstation.rest.resource;


import com.ctw.workstation.item.RackAsset;
import com.ctw.workstation.rest.dto.RackAssetDTO;
import com.ctw.workstation.rest.util.RestUtil;
import com.ctw.workstation.service.RackService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.Optional;
import java.util.Set;

@Path("/rack-assets")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class RackAssetResource {
    @Inject
    RackService svc;

    @POST
    public Response createRackAsset(RackAssetDTO rackAsset) {
        try {
            Long id = svc.addRackAsset(rackAsset.rackSerialNumber(), rackAsset.assetTag());
            RackAsset asset = svc.getRackAssetById(id);
            return Response.ok(asset).build();
        } catch (RuntimeException e) {
            return Response.serverError().entity(e.getMessage()).build();
        }
    }

    @GET
    public Response getRackAssets(@QueryParam("asset-tag") Optional<String> assetTag, @QueryParam("rack-id") Optional<Long> rackId) {
        try {
            if (assetTag.isEmpty() && rackId.isEmpty()) {
                Set<RackAsset> assets = svc.getAllRackAssets();
                return Response.ok(assets).build();
            } else if (assetTag.isPresent() && rackId.isPresent()) {
                throw new RuntimeException("Invalid query. Only one query param is allowed.");
            } else {
                if (rackId.isPresent()) {
                    RackAsset a = svc.getRackAssetByRackId(rackId.get());
                    return Response.ok(a).build();
                }
                //if (assetTag.isPresent())
                    RackAsset a = svc.getRackAssetByTag(assetTag.get());
                    return Response.ok(a).build();

            }
        } catch (RuntimeException e) {
            return Response.serverError().entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("/{id}")
    public Response getRackAsset(@PathParam("id") Long id) {
        try {
            RackAsset a = svc.getRackAssetById(id);
            return Response.ok(a).build();
        } catch (RuntimeException e) {
            return Response.serverError().entity(e.getMessage()).build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response deleteRackAsset(@PathParam("id") long id) {
        try {
            svc.removeRackAsset(id);
            return Response.noContent().build();
        } catch (RuntimeException e) {
            return Response.serverError().entity(e.getMessage()).build();
        }
    }
}
