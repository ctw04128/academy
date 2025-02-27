package com.ctw.workstation.item;

import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Collectors;

public class TeamTest {

    public static final List<Team> teams = new ArrayList<Team>();

    @Test
    public void test() {
        Set<Team.Builder> builders = new HashSet<Team.Builder>();
        builders.add(new Team.Builder().setName("Benfica").setDefaultLocation(Location.LISBON));
        builders.add(new Team.Builder().setName("Sporting").setDefaultLocation(Location.LISBON));
        builders.add(new Team.Builder().setName("Porto").setDefaultLocation(Location.PORTO));
        builders.add(new Team.Builder().setName("Braga").setDefaultLocation(Location.BRAGA));
        builders.add(new Team.Builder().setName("Boavista").setDefaultLocation(Location.PORTO));
        Set<Team> teams = builders.parallelStream().map(b -> b.setProduct("Football").build()).collect(Collectors.toSet());
        teams.stream().sorted(Comparator.comparing(Team::getDefaultLocation).thenComparing(Team::getName)).forEach(System.out::println);
        Map<Location, List<Team>> teamsByLocation = teams.stream().collect(Collectors.groupingBy(Team::getDefaultLocation));
        teamsByLocation.entrySet().stream().sorted(Map.Entry.comparingByKey()).forEach(e -> {
            System.out.print(e.getKey().toString() + ": ");
            System.out.println(e.getValue().stream().sorted(Comparator.comparing(Team::getName)).map(Team::getName).collect(Collectors.joining(", ")));
        });
    }
}
