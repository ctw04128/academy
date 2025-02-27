package com.ctw.workstation.core;

import java.io.Serializable;

public record Interval<D>(D from, D to) implements Serializable {

    @Override
    public String toString() {
        return "from " + from + " to " + to;
    }

}
