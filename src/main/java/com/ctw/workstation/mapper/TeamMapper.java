package com.ctw.workstation.mapper;

import com.ctw.workstation.rest.dto.TeamDTO;
import com.ctw.workstation.item.Team;

public class TeamMapper implements Mapper<Team, TeamDTO> {

    public TeamMapper() {}

    @Override
    public Team toEntity(TeamDTO dto) {
        return new Team.Builder().setName(dto.name()).setDefaultLocation(dto.location()).setProduct(dto.product()).build();
    }

    @Override
    public TeamDTO toDto(Team entity) {
        return new TeamDTO(entity.getName(), entity.getProduct(), entity.getDefaultLocation());
    }
}
