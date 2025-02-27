package com.ctw.workstation.item;

import org.junit.jupiter.api.Test;

import java.util.Date;

public class BookingTest {

    @Test
    public void test() {
        Date from1 = new Date();
        Date to1 = new Date();
        Booking b1 = new Booking.Builder()
                .setRequesterID(4L)
                .setRackID(1L)
                .setFrom(from1)
                .setTo(to1)
                .build();
        assert b1.getRackID() == 23;
        assert b1.getRequesterID() == 4;
        assert b1.getFrom() == from1;
        assert b1.getTo() == to1;

        Date from2 = new Date();
        Date to2 = new Date();
        Booking b2 = new Booking.Builder()
                .setRackID(1293L)
                .setRequesterID(10L)
                .setFrom(from2)
                .setTo(to2)
                .build();
        assert b2.getRackID() == 1293;
        assert b2.getRequesterID() == 10;
        assert b2.getFrom() == from2;
        assert b2.getTo() == to2;

    }
}
