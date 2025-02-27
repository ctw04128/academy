package com.ctw.workstation.rest.resource;

import com.ctw.workstation.exception.GeneralException;
import com.ctw.workstation.item.Location;
import com.ctw.workstation.item.Team;
import com.ctw.workstation.rest.util.RestUtil;
import com.ctw.workstation.rest.dto.TeamDTO;
import com.ctw.workstation.service.RackService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.Optional;
import java.util.Set;

@Path("/teams")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class TeamResource {
    @Inject
    RackService svc;

    @POST
    public Response createTeam(TeamDTO team) {
        try {
            Long id = svc.addTeam(team.name(), team.product(), team.location());
            return RestUtil.generateCreated(id);
        } catch (GeneralException e) {
            return Response.status(e.getStatus()).entity(e.getMessage()).build();
        }
    }

    @GET
    public Response getTeams(@QueryParam("location") Optional<Location> location) {
        try {
            if (location.isEmpty()) {
                Set<Team> teams = svc.getTeams();
                return Response.ok(teams).build();
            } else {
                Set<Team> teams = svc.getTeamsByLocation(location.get());
                return Response.ok(teams).build();
            }
        } catch (GeneralException e) {
            return Response.status(e.getStatus()).entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("/{id}")
    public Response getTeam(@PathParam("id") long id) {
        try {
            Team t = svc.findTeam(id);
            return Response.ok(t).build();
        } catch (GeneralException e) {
            return Response.status(e.getStatus()).entity(e.getMessage()).build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response deleteTeam(@PathParam("id") long id) {
        try {
            svc.removeTeam(id);
            return Response.noContent().build();
        } catch (GeneralException e) {
            return Response.status(e.getStatus()).entity(e.getMessage()).build();
        }
    }
}
