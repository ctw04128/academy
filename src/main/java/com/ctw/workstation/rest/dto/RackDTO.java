package com.ctw.workstation.rest.dto;

import com.ctw.workstation.item.Location;

public record RackDTO(String serial, Location location, String teamName) {

}
