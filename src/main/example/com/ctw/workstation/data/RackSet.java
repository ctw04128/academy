package com.ctw.workstation.data;

import jakarta.annotation.Nonnull;
import jakarta.json.*;

import java.util.HashSet;

public class RackSet extends HashSet<Rack> {
    public RackSet() {
        super();
    }


    public RackSet(@Nonnull JsonArray array) {
        super(array.size());
        for (JsonValue value : array) {
            JsonObject jsonObject = (JsonObject) value;
            add(new Rack(jsonObject));
        }
    }

    public JsonArray toJson() {
        JsonArrayBuilder builder = Json.createArrayBuilder();
        for (Rack rack : this) {
            builder.add(rack.toJson());
        }
        return builder.build();
    }





}
