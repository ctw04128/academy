package com.ctw.workstation.util;

import com.ctw.workstation.core.Pair;
import org.junit.jupiter.api.Test;

import java.util.Date;

public final class DateTest {

    @Test
    public void testIntervalsConcurrent() {
        //They touch in one side
        assert Util.areIntervalsConcurrent(new Pair<>(new Date(10), new Date(20)), new Pair<>(new Date(15), new Date(25)));
        assert Util.areIntervalsConcurrent(new Pair<>(new Date(15), new Date(25)), new Pair<>(new Date(10), new Date(20)));
        //They are fully contained
        assert Util.areIntervalsConcurrent(new Pair<>(new Date(15), new Date(25)), new Pair<>(new Date(16), new Date(24)));
        assert Util.areIntervalsConcurrent(new Pair<>(new Date(15), new Date(25)), new Pair<>(new Date(14), new Date(26)));
        // They are not contained
        assert !Util.areIntervalsConcurrent(new Pair<>(new Date(15), new Date(25)), new Pair<>(new Date(30), new Date(40)));
        // They are the same
        assert Util.areIntervalsConcurrent(new Pair<>(new Date(10), new Date(20)), new Pair<>(new Date(10), new Date(20)));
    }
}
