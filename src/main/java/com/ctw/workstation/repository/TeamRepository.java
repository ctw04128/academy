package com.ctw.workstation.repository;

import com.ctw.workstation.item.Location;
import com.ctw.workstation.item.Team;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Set;
import java.util.stream.Collectors;

@ApplicationScoped
public class TeamRepository implements PanacheRepository<Team> {

    private static final String BY_NAME = "name";
    public Team findByName(String name) {
        return find(BY_NAME, name).firstResult();
    }

    private static final String BY_LOCATION = "defaultLocation";
    public Set<Team> findByLocation(Location location) {
        return find(BY_LOCATION, location).stream().collect(Collectors.toSet());
    }
}
