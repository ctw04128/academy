package com.ctw.workstation.rest.dto;


import java.util.Date;

public record BookingDTO(Date from, Date to, long teamMemberID, long rackID) {
}
