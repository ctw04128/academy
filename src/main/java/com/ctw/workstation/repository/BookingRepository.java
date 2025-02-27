package com.ctw.workstation.repository;

import com.ctw.workstation.item.Booking;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

@ApplicationScoped
public class BookingRepository implements PanacheRepository<Booking> {

    private static final String BOOKINGS_OF_RACK_IN_INTERVAL = "rackID = ?1 and ((from <= ?2 and to >= ?2) or (from between ?2 and ?3))";
    public Set<Booking> findBookingsOfRackInInterval(long rackId, Date from, Date to) {
        return find(BOOKINGS_OF_RACK_IN_INTERVAL, rackId, from, to).stream().collect(Collectors.toSet());
    }

    private static final String BY_RACK = "rackID";
    public Set<Booking> findByRackId(long rackId) {
        return find(BY_RACK, rackId).stream().collect(Collectors.toSet());
    }

    private static final String BY_REQUESTER = "requesterID";
    public Set<Booking> findByRequesterId(long requesterId) {
        return find(BY_REQUESTER, requesterId).stream().collect(Collectors.toSet());
    }

    private static final String BOOKINGS_IN_INTERVAL = "(from <= ?1 and to >= ?1) or (from between ?1 and ?2)";
    public Set<Booking> findBookingsInInterval(Date from, Date to) {
        return find(BOOKINGS_IN_INTERVAL, from, to).stream().collect(Collectors.toSet());
    }

    private static final String BOOKINGS_OF_TEAM = "select b from Booking b join TeamMember m on b.requesterID = m.id where m.teamId = ?1";
    public Set<Booking> findBookingsOfTeam(long teamId) {
        return find(BOOKINGS_OF_TEAM, teamId).stream().collect(Collectors.toSet());
    }
}
