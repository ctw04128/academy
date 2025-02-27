package com.ctw.workstation.service;

import com.ctw.workstation.core.Pair;
import com.ctw.workstation.exception.AlreadyBookedException;
import com.ctw.workstation.exception.NotTheLeaderException;
import com.ctw.workstation.util.Message;
import com.ctw.workstation.exception.EmptyException;
import com.ctw.workstation.exception.NotFoundException;
import com.ctw.workstation.item.*;
import com.ctw.workstation.util.Util;

import java.util.*;
import java.util.stream.Collectors;

public class RackService {





    private static RackService instance;
    private int numberOfRacks;
    private int numberOfTeams;
    private int numberOfMembers;
    private int numberOfBookings;
    private final Database<Rack> racks;
    private final Database<Team> teams;
    private final Database<TeamMember> members;
    private final Database<Booking> bookings;

    private RackService (){
        numberOfRacks = 0;
        numberOfTeams = 0;
        numberOfMembers = 0;
        numberOfBookings = 0;
        racks = new Database<>();
        teams = new Database<>();
        members = new Database<>();
        bookings = new Database<>();

    }

    /**
     * Finds and returns a rack given an ID
     * @param id ID of the rack to search
     * @return Rack to find
     */
    public Rack findRack(int id)  throws NotFoundException {
        try {
            return racks.find(id);
        } catch (NullPointerException e) {
            throw new NotFoundException(DataType.RACK, id);
        }
    }

    public Team findTeam(int id)  throws NotFoundException {
        try {
            return teams.find(id);
        } catch (NullPointerException e) {
            throw new NotFoundException(DataType.TEAM, id);
        }
    }

    public TeamMember findMember(int id)  throws NotFoundException {
        try {
            return members.find(id);
        } catch (NullPointerException e) {
            throw new NotFoundException(DataType.TEAM_MEMBER, id);
        }
    }


    /**
     * Adds a new rack to the system with a sequencial ID.
     * @param name Name of the Rack
     * @param serial Serial Number of the Rack
     * @return the generated ID for this Rack
     */
    public int addRack(String name, UUID serial) {
        Rack r = new Rack.Builder().setId(numberOfRacks++).setName(name).setSerial(serial).build();
        racks.add(r);
        return r.getId();
    }

    public void updateRack(int id, String name) throws NotFoundException {
        Rack r = racks.find(id);
        if (r == null) throw new NotFoundException(DataType.RACK, id);
        r.setName(name);
    }


    public int addTeam(String name, String product) {
        Team t = new Team.Builder().setId(numberOfTeams++).setName(name).setProduct(product).build();
        teams.add(t);
        return t.getId();
    }

    public int addTeamMember(String name) {
        TeamMember m = new TeamMember.Builder().setId(numberOfMembers++).setName(name).build();
        members.add(m);
        return m.getId();
    }

    public void setLeader(int teamID, int memberID) throws NotFoundException {
        Team t = findTeam(teamID);
        findMember(memberID);
        t.setTeamLeaderId(memberID);
    }

    /**
     * Removes a rack from the system.
     * @param id the ID of the rack to remove
     */
    public void removeRack(int id) {
        if (racks.find(id) == null) throw new NotFoundException(DataType.RACK, id);
        racks.remove(id);
    }

    /**
     * Lists all available racks
     * @return all the racks that are not booked or are not in use at the current moment.
     * @throws EmptyException If there are no available rack currently
     */
    public Set<Rack> findAllAvailableRacks() throws EmptyException{
        Set<Rack> availableRacks = findBookedRacksNotInUse();
        availableRacks.addAll(findNonBookedRacks());
        if (availableRacks.isEmpty()) throw new EmptyException(DataType.RACK);
        return availableRacks;
    }

    /**
     * Lists all bookings
     * @return all the bookings registered in the system
     * @throws EmptyException If there are no bookings in the system
     */
    public Set<Booking> findAllBookings() throws EmptyException {
        if (bookings.isEmpty()) throw new EmptyException(DataType.BOOKING);
        return bookings.asSet();
    }

    /**
     * Allows a team to book a rack for a given amount of time
     * @param name Name of the booking
     * @param startMonth starting month of the booking
     * @param startDay starting day of the booking
     * @param endMonth end month of the booking
     * @param endDay end day of the booking
     * @param teamID ID of the team booking it
     * @param memberID ID of the member trying to book it
     * @param rackID ID of the rack to be booked
     * @return the ID of the booking
     * @throws IllegalArgumentException If any of the provided arguments doesn't comply with its needs
     * @throws NotFoundException If either the team or the rack don't exist
     * @throws AlreadyBookedException If the rack is already booked for the given interval of time
     */
    public int bookRack(String name, int startMonth, int startDay, int endMonth, int endDay, int teamID, int memberID, int rackID) throws IllegalArgumentException, NotFoundException, AlreadyBookedException{
        // Check for invalid name
        verifyName(name);

        // Generate the date objects.
        // You can only book racks for the current year
        int year = Util.getCurrentYear();
        Date start = Util.generateDate(year, startMonth, startDay);
        Date end = Util.generateDate(year, endMonth, endDay);

        // Find the Rack, throws Not Found if it doesn't exist.
        Rack rack = findRack(rackID);

        // Find the Team
        Team team = findTeam(teamID);

        // Find the Member
        TeamMember member = findMember(memberID);

        if (!team.isTeamLeader(memberID)) throw new NotTheLeaderException(memberID);

        // Verify if rack isn't booked for the given interval
        bookings.asSet()
                .stream().filter(b -> b.getRackID() == rackID)
                .forEach(b -> {
            if (Util.areIntervalsConcurrent(new Pair<>(start, end), new Pair<>(b.getStartingDate(), b.getEndingDate())))
                throw new AlreadyBookedException(DataType.RACK, rackID);
        });

        // Create the booking
        Booking booking = new Booking.Builder()
                .setId(numberOfBookings++)
                .setName(name)
                .setFrom(start)
                .setTo(end)
                .setRequestingTeamID(teamID)
                .setRackID(rackID)
                .build();

        bookings.add(booking);
        return booking.getId();
    }

    /**
     * Cancels a booking given its ID
     * @param bookingID ID of the booking to cancel
     * @throws NotFoundException If the booking doesn't exist
     */
    public void cancelBooking(int bookingID) throws NotFoundException {
        if (bookings.find(bookingID) == null) throw new NotFoundException(DataType.BOOKING, bookingID);
        bookings.remove(bookingID);
    }

    /**
     * Finds all the teams registered in the system
     * @return a set with all the teams by order of insertion
     * @throws EmptyException If there are no teams registered
     */
    public Set<Team> findAllTeams() throws EmptyException {
        if (teams.isEmpty()) {
            throw new EmptyException(DataType.TEAM);
        }
        return teams.asSet();
    }

    /**
     * Finds all bookings for a specific team
     * @param teamID the ID of the team to search
     * @return all the bookings the given team has
     * @throws NotFoundException If the team provided doesn't exist
     * @throws EmptyException If there are no bookings for the given team.
     */
    public Set<Booking> findAllBookingsOfTeam(int teamID) throws NotFoundException, EmptyException {
        // Verifies if the given team exists
        findTeam(teamID);
        // Get all their bookings
        Set<Booking> bookings = this.bookings.asSet()
                .stream().filter(b -> b.getRequestingTeamID() == teamID).collect(Collectors.toSet());

        if (bookings.isEmpty())  throw new EmptyException(DataType.BOOKING);

        return bookings;
    }

    public Set<Booking> findAllBookingsOfTeamInInterval(int teamID, int startDay, int startMonth, int endDay, int endMonth) throws EmptyException, NotFoundException {
        // Verifies if the given team exists
        findTeam(teamID);

        // Generate dates
        int year = Util.getCurrentYear();
        Date start = Util.generateDate(year, startMonth, startDay);
        Date end = Util.generateDate(year, endMonth, endDay);

        Set<Booking> bookings = this.bookings.asSet()
                .stream().filter(b -> b.getRequestingTeamID() == teamID
                && Util.areIntervalsConcurrent(new Pair<>(start, end), b.getInterval())).collect(Collectors.toSet());

        if (bookings.isEmpty()) throw new EmptyException(DataType.BOOKING);
        return bookings;
    }

    private Set<Rack> findBookedRacksNotInUse() {
        return this.bookings.asSet().stream().filter(b -> !Util.areWeBetweenDates(b.getStartingDate(), b.getEndingDate())).map(booking -> racks.find(booking.getRackID())).collect(Collectors.toSet());
    }

    private Set<Rack> findNonBookedRacks() {
        Set<Rack> result = this.racks.asSet();
        result.removeAll(getAllRacksWithABooking());
        return result;
    }

    private Set<Rack> getAllRacksWithABooking() {
        return this.bookings.asSet().stream().map(b -> racks.find(b.getRackID())).collect(Collectors.toSet());
    }

    private static void verifyName(String name) throws IllegalArgumentException {
        if (Util.nullOrEmpty(name)) throw new IllegalArgumentException(Message.NAME_CANT_BE_EMPTY.toString());
    }

    public static RackService getInstance(){
        if (instance == null) instance = new RackService();
        return instance;
    }
}
