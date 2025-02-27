package com.ctw.workstation.example;

import com.ctw.workstation.data.Rack;
import com.ctw.workstation.data.RackSet;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.util.Random;
import java.util.UUID;

@Path("/rack")
public class RackResource {

    @Path("/example")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public JsonObject getRack() {
        return new Rack(10, "Table 1", UUID.randomUUID()).toJson();
    }

    @Path("/all")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public JsonArray getAllRackS() {
        RackSet racks = new RackSet();
        Random rand = new Random();
        int count = 0;
        for (int i = 0; i < 100; i++)
            racks.add(new Rack(rand.nextInt(100000), "Table " + count++, UUID.randomUUID()));
        return racks.toJson();
    }
}
