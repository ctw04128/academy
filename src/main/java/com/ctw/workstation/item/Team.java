package com.ctw.workstation.item;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Entity
@Table(name = "T_TEAM")
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "teamIdGenerator")
    @SequenceGenerator(name = "teamIdGenerator", sequenceName = "SEQ_TEAM_ID")
    private Long id;
    @Column(name = "NAME", length = 255, nullable = false)
    private String name;
    @Column(name = "PRODUCT", length = 20, nullable = false)
    private  String product;
    @Enumerated(EnumType.STRING)
    @Column(name = "DEFAULT_LOCATION")
    private Location defaultLocation;
    @CreationTimestamp
    @Temporal(TemporalType.DATE)
    @Column(name = "CREATED_AT")
    private Date createdAt;
    @UpdateTimestamp
    @Temporal(TemporalType.DATE)
    @Column(name = "MODIFIED_AT")
    private Date modifiedAt;

    private Team(String name, String product, Location defaultLocation) {
        this.name = name;
        this.product = product;
        this.defaultLocation = defaultLocation;
    }

    protected Team() {}

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    /**
     * Returns the product this team is working on
     * @return the product of the team
     */
    public String getProduct() {
        return product;
    }

    public Location getDefaultLocation() {
        return defaultLocation;
    }

    @Override
    public String toString() {
        return "Team [id=" + id + ", name=" + name + ", product=" + product + ", defaultLocation=" + defaultLocation + "]";
    }

    public static class Builder {
        private String name;
        private String product;
        private Location defaultLocation;
        public Builder() {
            name = null;
            product = null;
            defaultLocation = null;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setProduct(String product) {
            this.product = product;
            return this;
        }

        public Builder setDefaultLocation(Location defaultLocation) {
            this.defaultLocation = defaultLocation;
            return this;
        }

        public Team build() {
            return new Team(name, product, defaultLocation);
        }

    }
}
