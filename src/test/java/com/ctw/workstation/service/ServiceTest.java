package com.ctw.workstation.service;

import com.ctw.workstation.exception.AlreadyBookedException;
import com.ctw.workstation.exception.NotFoundException;
import com.ctw.workstation.item.Booking;
import com.ctw.workstation.item.Location;
import com.ctw.workstation.item.Rack;
import com.ctw.workstation.item.Team;
import jakarta.inject.Inject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.Set;
import java.util.UUID;

public class ServiceTest {
    @Inject
    public RackService srv;


    @Test
    public void testAddAndFindRack() {/*
        String uuid = "0001";
        Long id1 = srv.addRack(uuid, Location.LISBON, "Team Example 01");
        String uuid2 = "0002";
        Long id2 = srv.addRack(uuid2, Location.PORTO, "Team Example 02");
        String uuid3 = "0003";
        Long id3 = srv.addRack(uuid3, Location.PORTO, "Team Example 03");
        String uuid4 = "0004";
        Long id4 = srv.addRack(uuid4, Location.BRAGA, "Team Example 04");
        Rack r1 = srv.findRack(id1);
        System.out.println(r1);
        Rack r2 = srv.findRack(id2);
        System.out.println(r2);
        Rack r3 = srv.findRack(id3);
        System.out.println(r3);
        Rack r4 = srv.findRack(id4);
        System.out.println(r4);
        assert r1.getId() == id1;
        assert r2.getId() == id2;
        assert r3.getId() == id3;
        assert r4.getId() == id4;
        assert r1.getSerial().equals(uuid);
        assert r2.getSerial().equals(uuid2);
        assert r3.getSerial().equals(uuid3);
        assert r4.getSerial().equals(uuid4);*/
    }

    @Test
    public void bookRack() {/*
        UUID uuid = UUID.randomUUID();
        int rackID = srv.addRack("Table1", uuid);
        int teamID = srv.addTeam("Portas", "Door");
        int memberID = srv.addTeamMember("João");
        srv.setLeader(teamID, memberID);
        int bookingID = srv.bookRack("Arranjo de portas", 5, 1, 5, 5, teamID, memberID, rackID);
        System.out.println(bookingID);
        int booking2ID = srv.bookRack("Arranjo de portas", 5, 1, 5, 5, teamID, memberID, rackID);
        System.out.println(booking2ID);*/
    }

    /*@Test
    public void testAddAndFindBooking() {
        UUID u1 = UUID.randomUUID();
        int idr1 = srv.addRack("Table1", u1);
        UUID u2 = UUID.randomUUID();
        int idr2 =srv.addRack("Table2", u2);
        UUID u3 = UUID.randomUUID();
        int idr3 = srv.addRack("Table3", u3);
        UUID u4 = UUID.randomUUID();
        int idr4 = srv.addRack("Table4", u4);
        int idb1 = srv.bookRack("Window Testing", 3, 10, 3, 15, "Team1", "Car Window", idr1);
        int idb2 = srv.bookRack("Door Testing", 3, 5, 3, 9, "Team2", "Car Door", idr1);
        int idb3 = srv.bookRack("Screen Testing", 2, 10, 3, 1, "Team3", "Car Screen", idr3);
        int idb4 = srv.bookRack("Website Testing", 2, 5, 3, 1, "Team4", "Website", idr1);
        int idb5 = srv.bookRack("Door Handle Testing", 4, 1, 4, 5, "Team2", "Car Door", idr3);
        try {
            int idb6 = srv.bookRack("Music Display Testing", 3, 9, 3, 12, "Team3", "Car Screen", idr1);
        } catch (AlreadyBookedException e) {
            System.out.println(e.getMessage());
        }
        try {
            int idb7 = srv.bookRack("App Testing", 1, 20, 1, 30, "Team4", "Website", 10);
            srv.findAllBookingsOfTeam(3).iterator().forEachRemaining(System.out::println);
        } catch (NotFoundException e) {
            System.out.println(e.getMessage());
        }
        try {
            int idb8 = srv.bookRack("", 1, 20, 1, 30, "Team4", "Website", idr3);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
        try {
            int idb9 = srv.bookRack("App Testing", 1, 20, 1, 30, "", "Website", idr3);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
        try {
            int idb10 = srv.bookRack("App Testing", 1, 20, 1, 30, "Team4", "", idr3);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
        Set<Booking> bookings = srv.findAllBookings();
        Iterator<Booking> it = bookings.iterator();
        Booking b1 = it.next();
        System.out.println(b1);
        Booking b2 = it.next();
        System.out.println(b2);
        Booking b3 = it.next();
        System.out.println(b3);
        Booking b4 = it.next();
        System.out.println(b4);
        Booking b5 = it.next();
        System.out.println(b5);
        assert idb1 == 0;
        assert idb2 == 1;
        assert idb3 == 2;
        assert idb4 == 3;

    }*/

}
