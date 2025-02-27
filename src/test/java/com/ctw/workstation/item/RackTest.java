package com.ctw.workstation.item;

import org.junit.jupiter.api.Test;

import java.util.UUID;

public class RackTest {
    @Test
    public void test() {
        String uuid1 = "0001";
        Rack r1 = new Rack.Builder()
                .setSerial(uuid1).build();
        assert r1.getSerial() == uuid1;

        String uuid2 = "0002";
        Rack r2 = new Rack.Builder()
                .setSerial(uuid2).build();
        assert r2.getSerial() == uuid2;

    }
}
