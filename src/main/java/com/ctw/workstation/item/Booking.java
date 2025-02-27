package com.ctw.workstation.item;

import com.ctw.workstation.core.Interval;
import com.ctw.workstation.core.Pair;
import com.ctw.workstation.util.Util;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Entity
@Table(name = "T_BOOKING")
public class Booking implements Comparable<Booking> {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "bookingIdGenerator")
    @SequenceGenerator(name = "bookingIdGenerator", sequenceName = "SEQ_BOOKING_ID")
    private Long id;
    @Temporal(TemporalType.DATE)
    @Column(name = "BOOK_FROM")
    private Date from;
    @Temporal(TemporalType.DATE)
    @Column(name = "BOOK_TO")
    private Date to;
    @Column(name = "REQUESTER_ID")
    private Long requesterID;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "REQUESTER_ID", updatable = false, insertable = false, nullable = false)
    private TeamMember requester;
    @Column(name = "RACK_ID")
    private Long rackID;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RACK_ID", updatable = false, insertable = false, nullable = false)
    private Rack rack;
    @CreationTimestamp
    @Temporal(TemporalType.DATE)
    @Column(name = "CREATED_AT")
    private Date createdAt;
    @UpdateTimestamp
    @Temporal(TemporalType.DATE)
    @Column(name = "MODIFIED_AT")
    private Date modifiedAt;

    protected Booking() {}

    private Booking(Date from, Date to, Long requesterID, Long rackID) {
        this.from = from;
        this.to = to;
        this.requesterID = requesterID;
        this.rackID = rackID;
    }

    public long getId() {
        return id;
    }

    /**
     * Returns the Date this booking starts
     * @return the date this booking starts
     */
    protected Date getFrom() {
        return from;
    }

    /**
     * Returns the Date this booking ends
     * @return the date this booking ends
     */
    protected Date getTo() {
        return to;
    }

    /**
     * Returns the interval in which this booking happens
     * @return the interval in which this booking happens
     */
    public Interval<Date> getDates() {
        return new Interval<Date>(from, to);
    }

    /**
     * Returns the time interval for this booking
     * @return the time interval of this booking
     */
    private Pair<Date, Date> getInterval() {
        return new Pair<>(from, to);
    }

    /**
     * Returns the ID of the team on this booking
     * @return the ID of the requesting team
     */
    public Long getRequesterID() {
        return requesterID;
    }

    /**
     * Returns the ID of the booked Rack
     * @return the rack's ID
     */
    public Long getRackID() {
        return rackID;
    }

    @Override
    public int compareTo(Booking booking) {
        return this.from.compareTo(booking.from);
    }

    @Override
    public String toString() {
        return "Booking [id=" + id +
                ", from=" + Util.toString(from) +
                ", to=" + Util.toString(to) +
                ", requesterID=" + requesterID + ", rackID=" + rackID + "]";
    }


    public static class Builder {
        private Date from;
        private Date to;
        private Long requesterID;
        private Long rackID;

        public Builder() {
            from = null;
            to = null;
            requesterID = null;
            rackID = null;
        }

        public Builder setFrom(Date from) {
            this.from = from;
            return this;
        }

        public Builder setTo(Date to) {
            this.to = to;
            return this;
        }

        public Builder setRequesterID(Long requesterID) {
            this.requesterID = requesterID;
            return this;
        }

        public Builder setRackID(Long rackID) {
            this.rackID = rackID;
            return this;
        }

        public Booking build() {
            return new Booking(from, to, requesterID, rackID);
        }
    }
}
