package com.ctw.workstation.item;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "T_RACK")
public class Rack {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "rackIdGenerator")
    @SequenceGenerator(name = "rackIdGenerator", sequenceName = "SEQ_RACK_ID")
    private Long id;
    @Column(name = "SERIAL_NUMBER", length = 20, nullable = false)
    private String serial;
    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS")
    private RackStatus status;
    @Enumerated(EnumType.STRING)
    @Column(name = "DEFAULT_LOCATION")
    private Location defaultLocation;
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

    protected Rack() {}

    private Rack(String serial, RackStatus status, Location defaultLocation, Long teamId) {
        this.serial = serial;
        this.status = status;
        this.defaultLocation = defaultLocation;
        this.teamId = teamId;
    }

    public long getId() {
        return id;
    }

    /**
     * Returns the serial number of this Rack
     * @return the serial number
     */
    public String getSerial() {
        return serial;
    }

    public RackStatus getStatus() {
        return status;
    }

    public Location getDefaultLocation() {
        return defaultLocation;
    }



    @Override
    public String toString() {
        return "Rack [id=" + id + ", serial=" + serial + ", status=" + status + ", defaultLocation=" + defaultLocation + "]";
    }

    public static class Builder {
        private String serial;
        private RackStatus status;
        private Location defaultLocation;
        private Long teamId;
        public Builder() {
            serial = null;
            status = RackStatus.AVAILABLE;
            defaultLocation = null;
            teamId = null;
        }

        public Builder setStatus(RackStatus status) {
            this.status = status;
            return this;
        }

        public Builder setDefaultLocation(Location defaultLocation) {
            this.defaultLocation = defaultLocation;
            return this;
        }

        public Builder setSerial(String serial) {
            this.serial = serial;
            return this;
        }

        public Builder setTeamID(Long id) {
            this.teamId = id;
            return this;
        }

        public Rack build() {
            return new Rack(serial, status, defaultLocation, teamId);
        }


    }
}
