package com.ctw.workstation.repository;

import com.ctw.workstation.item.Rack;
import com.ctw.workstation.item.RackStatus;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Set;
import java.util.stream.Collectors;

@ApplicationScoped
public class RackRepository implements PanacheRepository<Rack> {

    private static final String BY_SERIAL_NUMBER = "serial";
    public Rack findBySerialNumber(String serialNumber) {
        return find(BY_SERIAL_NUMBER, serialNumber).firstResult();
    }

    private static final String BY_STATUS = "status";
    public Set<Rack> findAllByStatus(RackStatus status) {
        return find(BY_STATUS, status).stream().collect(Collectors.toSet());
    }

}
