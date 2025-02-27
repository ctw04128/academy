package com.ctw.workstation.service;

import com.ctw.workstation.config.CtwAcademyResource;
import com.ctw.workstation.config.CtwAcademyTestProfile;
import com.ctw.workstation.item.Location;
import com.ctw.workstation.item.Team;
import com.ctw.workstation.mapper.TeamMapper;
import com.ctw.workstation.repository.TeamRepository;
import com.ctw.workstation.rest.dto.TeamDTO;
import io.quarkus.test.InjectMock;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusMock;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.TestProfile;
import io.quarkus.test.junit.mockito.InjectSpy;
import jakarta.inject.Inject;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@QuarkusTest
@TestProfile(CtwAcademyTestProfile.class)
public class RackServiceTest {

    @Inject
    RackService rackService;

    @InjectSpy
    TeamRepository teamRepository;

    @Test
    @Order(2)
    @DisplayName("Create a team with a mockito")
    public void create_a_team_with_mockito() {
        Mockito.doThrow(new IllegalArgumentException("PODE NAO MEU"))
                .when(teamRepository)
                .persist(Mockito.any(Team.class));

        // Given
        TeamDTO teamDTO = new TeamDTO("Benfica", "Football", Location.LISBON);
        // When
        Long l = rackService.addTeam(teamDTO.name(), teamDTO.product(), teamDTO.location());
        Team team = rackService.findTeam(l);
        TeamDTO result = new TeamMapper().toDto(team);
        // Then
        assertThatThrownBy(() -> rackService.addTeam("Benfica", "Football", Location.LISBON))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("PODE NAO MEU");
    }

    @Test
    @Order(1)
    @DisplayName("Create a team")
    public void create_a_team() {
        // Given
        TeamDTO teamDTO = new TeamDTO("Benfica", "Football", Location.LISBON);
        // When
        Long id = rackService.addTeam(teamDTO.name(), teamDTO.product(), teamDTO.location());
        Team team = rackService.findTeam(id);
        TeamDTO result = new TeamMapper().toDto(team);
        // Then
        assertThat(teamDTO)
                .hasNoNullFieldsOrProperties()
                .usingRecursiveComparison()
                .isEqualTo(result);
        assertThat(team).isNotNull();
    }
}
