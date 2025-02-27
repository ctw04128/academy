package com.ctw.workstation.rest.resource;

import com.ctw.workstation.item.TeamMember;
import com.ctw.workstation.rest.dto.MemberDTO;
import com.ctw.workstation.rest.util.RestUtil;
import com.ctw.workstation.service.RackService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.Set;

@Path("/members")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MemberResource {

    @Inject
    RackService svc;

    @POST
    public Response createMember(MemberDTO member) {
        try {
            Long id = svc.addTeamMember(member.name(), member.teamName(), member.ctwID());
            return RestUtil.generateCreated(id);
        } catch (RuntimeException e) {
            return Response.serverError().entity(e.getMessage()).build();
        }
    }

    @GET
    public Response getAllMembers(@QueryParam("team-name") String teamName) {
        try {
            if (teamName == null || teamName.isEmpty()) {
                Set<TeamMember> members = svc.getTeamMembers();
                return Response.ok().entity(members).build();
            } else {
                Set<TeamMember> members = svc.getTeamMembersOfTeam(teamName);
                return Response.ok().entity(members).build();
            }
        } catch (RuntimeException e) {
            return Response.serverError().entity(e.getMessage()).build();
        }
    }
}
