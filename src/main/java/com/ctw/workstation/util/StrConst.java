package com.ctw.workstation.util;

public enum StrConst {
    DATE_FORMAT("yyyy-MM-dd"),
    EMPTY_STRING("");

    private final String constant;
    private StrConst(String constant) {
        this.constant = constant;
    }
    public String toString() {
        return constant;
    }
}
