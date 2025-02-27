package com.ctw.workstation.data;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;

import java.util.UUID;

public class Rack {
    private int id;
    private String name;
    private UUID serial;

    public Rack(int id, String name, UUID serial) {
        this.id = id;
        this.name = name;
        this.serial = serial;
    }

    public Rack(JsonObject json) {
        this.id = json.getInt("id");
        this.name = json.getString("name");
        this.serial = UUID.fromString(json.getString("serial"));
    }

    public JsonObject toJson() {
        JsonObjectBuilder b = Json.createObjectBuilder();
        b.add("id", id);
        b.add("name", name);
        b.add("serial", serial.toString());
        return b.build();
    }



}
