package com.ctw.workstation.rest.resource;

import com.ctw.workstation.item.Booking;
import com.ctw.workstation.rest.util.RestUtil;
import com.ctw.workstation.rest.dto.BookingDTO;
import com.ctw.workstation.service.RackService;
import com.ctw.workstation.util.FunctionUtil;
import com.ctw.workstation.util.Message;
import com.ctw.workstation.util.StrConst;
import io.quarkus.logging.Log;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.function.Function;

import org.jboss.logging.MDC;

@Path("/bookings")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BookingResource {
    @Inject
    RackService svc;

    private static final Function<Optional<?>, String> OPTIONAL_TO_STRING = FunctionUtil.OPTIONAL_TO_STRING;

    private static final DateFormat DATE_FORMAT = new SimpleDateFormat(StrConst.DATE_FORMAT.toString());


    @POST
    public Response createBooking(BookingDTO booking) {
        try {
            long id = svc.bookRack(booking.from(), booking.to(), booking.teamMemberID(), booking.rackID());
            return RestUtil.generateCreated(id);
        } catch (RuntimeException e) {
            return Response.serverError().entity(e.getMessage()).build();
        }
    }

    @GET
    public Response getBookings(@QueryParam("rackID") Optional<Long> rackID, @QueryParam("requesterID") Optional<Long> requesterID, @QueryParam("teamID") Optional<Long> teamID, @QueryParam("from") Optional<String> from, @QueryParam("to") Optional<String> to) {
        MDC.put("request.type", "GET");
        MDC.put("request.path", "/bookings");
        MDC.put("request.id", UUID.randomUUID().toString());
        try {
            Optional<Date> fromDate = parseDate(from);
            Optional<Date> toDate = parseDate(to);
            if (!isDateInputValid(fromDate, toDate))
                throw new IllegalArgumentException(Message.INVALID_DATES.toString());
            Set<Booking> bs;
            if (rackID.isPresent() && requesterID.isEmpty() && fromDate.isPresent() && toDate.isPresent() && teamID.isEmpty()) {
                Log.tracev("Getting bookings of rack {0} in interval between {1} and {2}.", rackID.get(), fromDate.get(), toDate.get());
                bs = svc.getBookingsOfRackInInterval(rackID.get(), fromDate.get(), toDate.get());
            } else if (rackID.isPresent() && requesterID.isEmpty() && fromDate.isEmpty() && toDate.isEmpty() && teamID.isEmpty()) {
                Log.tracev("Getting bookings of rack {0}", rackID.get());
                bs = svc.getBookingsOfRack(rackID.get());
            } else if (rackID.isEmpty() && requesterID.isEmpty() && fromDate.isPresent() && toDate.isPresent() && teamID.isEmpty()) {
                Log.tracev("Getting bookings in interval between {0} and {1}.", fromDate.get(), toDate.get());
                bs = svc.getBookingsInInterval(fromDate.get(), toDate.get());
            } else if (rackID.isEmpty() && requesterID.isPresent() && fromDate.isEmpty() && toDate.isEmpty() && teamID.isEmpty()) {
                Log.tracev("Getting bookings of requester {0}.", requesterID);
                bs = svc.getBookingsOfRequester(requesterID.get());
            } else if (rackID.isEmpty() && requesterID.isEmpty() && fromDate.isEmpty() && toDate.isEmpty() && teamID.isPresent()) {
                Log.tracev("Getting bookings of team {0}.", teamID);
                bs = svc.getBookingsOfTeam(teamID.get());
            } else if (rackID.isEmpty() && requesterID.isEmpty() && fromDate.isEmpty() && toDate.isEmpty()) {
                Log.tracev("Getting all bookings in the system.");
                bs = svc.getAllBookings();
            }
            else {
                Log.tracev("Tried to use getBookings but used an invalid query: {0}", rackID.get());
                throw new IllegalArgumentException("Invalid query.");
            }
            return Response.ok(bs).build();
        } catch (RuntimeException e) {
            return Response.serverError().entity(e.getMessage()).build();
        }
    }

    /**
     * Transforms a string containing a date into an actual date
     * @param dateAsString date represented by a string
     * @return the date object
     * @throws IllegalArgumentException if the date string does not follow an actual date format
     */
    private static Optional<Date> parseDate(Optional<String> dateAsString) throws IllegalArgumentException {
        try {
            return (dateAsString.isPresent() ? Optional.of(DATE_FORMAT.parse(dateAsString.get())) : Optional.empty());
        } catch (ParseException e) {
            throw new IllegalArgumentException(Message.INVALID_DATE_FORMAT.toString());
        }
    }

    private static boolean isDateInputValid(Optional<Date> from, Optional<Date> to) {
        return from.isPresent() && to.isPresent() && from.get().before(to.get())
                || from.isEmpty() && to.isEmpty();
    }

/*
    @GET
    public Response getAllBookings(@DefaultValue("-1") @QueryParam("team") int teamID, @QueryParam("from") String from, @QueryParam("to") String to) {
        try {
            Set<Booking> bookings;
            if (teamID==-1)
                bookings = RackService.getInstance().findAllBookings();
            else if (from==null && to==null)
                bookings = RackService.getInstance().findAllBookingsOfTeam(teamID);
            else {
                LocalDate fromDate = LocalDate.parse(from);
                LocalDate toDate = LocalDate.parse(to);
                bookings = RackService.getInstance().findAllBookingsOfTeamInInterval(teamID, fromDate.getDayOfMonth(), fromDate.getMonthValue(), toDate.getDayOfMonth(), toDate.getMonthValue());
            }
            return Response.ok(bookings).build();
        } catch (RuntimeException e) {
            return Response.serverError().entity(e.getMessage()).build();
        }
    }

    /*@GET
    public Response getAllBookingsOfTeam(@QueryParam("team") int teamID, @QueryParam("from") Date from, @QueryParam("to") Date to) {
        try {
            Set<Booking> bookings = RackService.getInstance().findAllBookingsOfTeam(teamID);
            return Response.ok(bookings).build();
        } catch (RuntimeException e) {
            return Response.serverError().entity(e.getMessage()).build();
        }
    }

    @GET
    public Response getAllBookingsOfTeamInInterval(@QueryParam("team") int teamID, IntervalDTO interval) {
        try {
            Set<Booking> bookings = RackService.getInstance().findAllBookingsOfTeamInInterval(teamID, interval.startDay(), interval.startMonth(), interval.endDay(), interval.endMonth());
            return Response.ok(bookings).build();
        } catch (RuntimeException e) {
            return Response.serverError().entity(e.getMessage()).build();
        }
    }*/
}
