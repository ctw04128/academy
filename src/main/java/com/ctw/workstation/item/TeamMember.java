package com.ctw.workstation.item;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Entity
@Table(name = "T_TEAM_MEMBER")
public class TeamMember  {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "teamMemberIdGenerator")
    @SequenceGenerator(name = "teamMemberIdGenerator", sequenceName = "SEQ_TEAM_MEMBER_ID")
    private Long id;
    @Column(name = "NAME", length = 20, nullable = false)
    private String name;
    @Column(name = "CTW_ID", length = 8, nullable = false)
    private String ctwId;
    @Column(name = "TEAM_ID")
    private Long teamId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TEAM_ID", updatable = false, insertable = false, nullable = false)
    private Team team;
    @CreationTimestamp
    @Temporal(TemporalType.DATE)
    @Column(name = "CREATED_AT")
    private Date createdAt;
    @UpdateTimestamp
    @Temporal(TemporalType.DATE)
    @Column(name = "MODIFIED_AT")
    private Date modifiedAt;

    protected TeamMember() {}

    protected TeamMember(String name, String ctwId, Long teamId) {
        this.name = name;
        this.ctwId = ctwId;
        this.teamId = teamId;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCtwId() {
        return ctwId;
    }

    public long getTeamId() {
        return teamId;
    }

    public static class Builder {
        private String name;
        private String ctwId;
        private Long teamId;

        public Builder() {
            name = null;
            ctwId = null;
            teamId = null;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }
        public Builder setCtwId(String ctwId) {
            this.ctwId = ctwId;
            return this;
        }
        public Builder setTeamId(Long teamId) {
            this.teamId = teamId;
            return this;
        }

        public TeamMember build() {
            return new TeamMember(name, ctwId, teamId);
        }
    }
}
