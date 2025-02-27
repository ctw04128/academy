package com.ctw.workstation.service;

import com.ctw.workstation.exception.*;
import com.ctw.workstation.repository.*;
import com.ctw.workstation.item.*;
import com.ctw.workstation.util.Message;
import com.ctw.workstation.util.Util;
import io.quarkus.logging.Log;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.transaction.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Singleton
public class RackService {

    @Inject
    RackRepository racks;
    @Inject
    RackAssetRepository assets;
    @Inject
    TeamRepository teams;
    @Inject
    TeamMemberRepository members;
    @Inject
    BookingRepository bookings;

    /**
     * Finds and returns a rack given an ID
     * @param id ID of the rack to search
     * @return Rack to find
     */
    public Rack findRack(Long id)  throws NotFoundException {
        try {
            return racks.findById(id);
        } catch (NullPointerException e) {
            throw new NotFoundException(DataType.RACK, id);
        }
    }

    public Team findTeam(Long id)  throws NotFoundException {
        Team t = teams.findById(id);
        if (t == null) {
            throw new NotFoundException(DataType.TEAM, id);
        }
        return t;
    }

    public TeamMember findMember(Long id)  throws NotFoundException {
        try {
            return members.findById(id);
        } catch (NullPointerException e) {
            throw new NotFoundException(DataType.TEAM_MEMBER, id);
        }
    }


    /**
     * Adds a new rack to the system with a sequencial ID.
     * @param serial Serial Number of the Rack
     * @return the generated ID for this Rack
     */
    @Transactional
    public Long addRack(String serial, Location location, String teamName) {
        Team team = teams.findByName(teamName);
        if (team == null)
            throw new NotFoundException(DataType.TEAM, teamName);
        Rack r = new Rack.Builder().setSerial(serial).setDefaultLocation(location).setStatus(RackStatus.AVAILABLE).setTeamID(team.getId()).build();
        racks.persist(r);
        return r.getId();
    }
/*
    public void updateRack(int id, String name) throws NotFoundException {
        Rack r = racks.find(id);
        if (r == null) throw new NotFoundException(DataType.RACK, id);
        r.setName(name);
    }*/

    @Transactional
    public Long addTeam(String name, String product, Location location) {
        Team t = new Team.Builder().setName(name).setProduct(product).setDefaultLocation(location).build();
        teams.persist(t);
        return t.getId();
    }

    @Transactional
    public Long addTeamMember(String name, String teamName, String ctwID) {
        Team t = teams.findByName(teamName);
        if (t == null)
            throw new NotFoundException(DataType.TEAM, teamName);
        TeamMember m = new TeamMember.Builder().setTeamId(t.getId()).setCtwId(ctwID).setName(name).build();
        members.persist(m);
        return m.getId();
    }

    @Transactional
    public Long addRackAsset(String serialNumber, String assetTag) {
        //RackAsset a = assets.findByAssetTag(assetTag);
        //if (a != null) throw new AlreadyExistsException(DataType.RACK_ASSET, assetTag);
        Rack r = racks.findBySerialNumber(serialNumber);
        if (r == null) throw new NotFoundException(DataType.RACK, serialNumber);
        RackAsset a = new RackAsset.Builder().setRackId(r.getId()).setAssetTag(assetTag).build();
        assets.persist(a);
        return a.getId();
    }

    @Transactional
    public Set<TeamMember> getTeamMembers() {
        Set<TeamMember> members = this.members.findAll().stream().collect(Collectors.toSet());
        if (members.isEmpty()) throw new EmptyException(DataType.TEAM_MEMBER);
        return members;
    }


    @Transactional
    public Set<TeamMember> getTeamMembersOfTeam(String teamName) {
        Team t = teams.findByName(teamName);
        if (t == null) throw new NotFoundException(DataType.TEAM, teamName);
        Set<TeamMember> members = this.members.findByTeamId(t.getId());
        if (members.isEmpty()) throw new EmptyException(DataType.TEAM_MEMBER);
        return members;
    }

    /**
     * Removes a rack from the system.
     * @param serial_number the ID of the rack to remove
     */
    @Transactional
    public void removeRack(String serial_number) {
        Rack r = racks.findBySerialNumber(serial_number);
        if (r == null) throw new NotFoundException(DataType.RACK, serial_number);
        racks.delete(r);
    }

    @Transactional
    public void removeRack(Long id) {
        Rack r = racks.findById(id);
        if (r == null) throw new NotFoundException(DataType.RACK, id);
        racks.delete(r);
    }

    @Transactional
    public void removeTeam(Long id) {
        Team t = teams.findById(id);
        if (t == null) throw new NotFoundException(DataType.TEAM, id);
        teams.delete(t);
    }
    /**
     * Lists all available racks
     * @return all the racks that are not booked or are not in use at the current moment.
     * @throws EmptyException If there are no available rack currently
     */
    public Set<Rack> findAllAvailableRacks() throws EmptyException{/*
        Set<Rack> availableRacks = findBookedRacksNotInUse();
        availableRacks.addAll(findNonBookedRacks());*/
        Set<Rack> available = racks.findAllByStatus(RackStatus.AVAILABLE);
        if (available.isEmpty()) return new HashSet<>();
        return available;
    }

    public Set<Team> getTeams() {
        Set<Team> teams = this.teams.findAll().stream().collect(Collectors.toSet());
        if (teams.isEmpty()) return new HashSet<>();
        return teams;
    }

    public Set<Team> getTeamsByLocation(Location location) {
        Set<Team> teams = this.teams.findByLocation(location);
        if (teams.isEmpty()) throw new EmptyException(DataType.TEAM);
        return teams;
    }

    public Set<RackAsset> getAllRackAssets() {
        Set<RackAsset> assets = this.assets.findAll().stream().collect(Collectors.toSet());
        if (assets.isEmpty()) return new HashSet<>();
        return assets;
    }

    public RackAsset getRackAssetById(long id) {
        RackAsset a = this.assets.findById(id);
        if (a == null) throw new NotFoundException(DataType.RACK_ASSET, id);
        return a;
    }

    public RackAsset getRackAssetByTag(String assetTag) {
        RackAsset a = this.assets.findByAssetTag(assetTag);
        if (a == null) throw new NotFoundException(DataType.RACK_ASSET, assetTag);
        return a;
    }

    @Transactional
    public Long bookRack(Date from, Date to, long requesterId, long rackId) {
        checkDates(from, to); // THROW EXCEPTION WITH MESSAGE IN CASE OF INVALID ARGS
        TeamMember m = members.findById(requesterId);
        if (m == null) throw new NotFoundException(DataType.TEAM_MEMBER, requesterId);
        Rack r = racks.findById(rackId);
        if (r == null) throw new NotFoundException(DataType.RACK, rackId);
        Set<Booking> bookedInTheInterval = bookings.findBookingsOfRackInInterval(rackId, from, to);
        if (!bookedInTheInterval.isEmpty()) throw new AlreadyBookedException(DataType.RACK, rackId);
        Booking b = new Booking.Builder().setFrom(from).setTo(to).setRequesterID(requesterId).setRackID(rackId).build();
        bookings.persist(b);
        return b.getId();
    }

    private void checkDates(Date from, Date to) {
        if ((from == null || to == null))
            throw new IllegalArgumentException("Dates introduced are invalid.");
        if (!Util.isInTheFuture(from) || !Util.isInTheFuture(to))
            throw new IllegalArgumentException("You can only book racks for future dates.");
        if (from.after(to))
            throw new IllegalArgumentException("The starting date must not come after the ending date.");
    }

    public Set<Booking> getBookingsOfRack(long rackId) {
        Set<Booking> bs = bookings.findByRackId(rackId);
        if (bs.isEmpty()) throw new EmptyException(DataType.BOOKING);
        return bs;
    }

    public Set<Booking> getBookingsOfRequester(long requesterId) {
        Set<Booking> bs = bookings.findByRequesterId(requesterId);
        if (bs.isEmpty()) throw new EmptyException(DataType.BOOKING);
        return bs;
    }

    public Set<Booking> getBookingsOfTeam(long teamId) {
        Set<Booking> bs = bookings.findBookingsOfTeam(teamId);
        if (bs.isEmpty()) throw new EmptyException(DataType.BOOKING);
        return bs;
    }

    public Set<Booking> getBookingsOfRackInInterval(long rackId, Date from, Date to) {
        Set<Booking> bs = bookings.findBookingsOfRackInInterval(rackId, from, to);
        if (bs.isEmpty()) throw new EmptyException(DataType.BOOKING);
        return bs;
    }

    public Set<Booking> getBookingsInInterval(Date from, Date to) throws EmptyException{
        Set<Booking> bs = bookings.findBookingsInInterval(from, to);
        if (bs.isEmpty()) throw new EmptyException(DataType.BOOKING);
        return bs;
    }

    public Set<Booking> getAllBookings() throws EmptyException {
        Set<Booking> bs = bookings.findAll().stream().collect(Collectors.toSet());
        if (bs.isEmpty()) throw new EmptyException(DataType.BOOKING);
        return bs;
    }

    public RackAsset getRackAssetByRackId(Long aLong) {
        return null; // TODO!
    }

    @Transactional
    public void removeRackAsset(Long id) {
        RackAsset ra = assets.findById(id);
        if (ra == null) throw new NotFoundException(DataType.RACK_ASSET, id);
        assets.delete(ra);
    }

    /**
     * Lists all bookings
     * @return all the bookings registered in the system
     * @throws EmptyException If there are no bookings in the system
     *//*
    public Set<Booking> findAllBookings() throws EmptyException {
        if (bookings.isEmpty()) throw new EmptyException(DataType.BOOKING);
        return bookings.asSet();
    }*/

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
     *//*
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
     *//*
    public void cancelBooking(int bookingID) throws NotFoundException {
        if (bookings.find(bookingID) == null) throw new NotFoundException(DataType.BOOKING, bookingID);
        bookings.remove(bookingID);
    }*/

    /**
     * Finds all the teams registered in the system
     * @return a set with all the teams by order of insertion
     * @throws EmptyException If there are no teams registered
     *//*
    public Set<Team> findAllTeams() throws EmptyException {
        if (teams.isEmpty()) {
            throw new EmptyException(DataType.TEAM);
        }
        return teams.asSet();
    }*/

    /**
     * Finds all bookings for a specific team
     * @param teamID the ID of the team to search
     * @return all the bookings the given team has
     * @throws NotFoundException If the team provided doesn't exist
     * @throws EmptyException If there are no bookings for the given team.
     *//*
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
    }*/

}
