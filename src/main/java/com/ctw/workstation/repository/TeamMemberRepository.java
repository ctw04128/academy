package com.ctw.workstation.repository;

import com.ctw.workstation.item.TeamMember;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Set;
import java.util.stream.Collectors;

@ApplicationScoped
public class TeamMemberRepository implements PanacheRepository<TeamMember> {

    private static final String BY_NAME = "name";
    public TeamMember findByName(String name) {
        return find(BY_NAME, name).firstResult();
    }

    private static final String BY_TEAM_ID = "teamId";
    public Set<TeamMember> findByTeamId(Long teamId) { return find(BY_TEAM_ID, teamId).stream().collect(Collectors.toSet()); }
}
