package com.ctw.workstation.util;

public enum Message {

    // DATA EXCEPTIONS
    ALREADY_EXISTS("%s - %s already exists."),
    ALREADY_BOOKED("%s with id %s is already booked."),
    NOT_FOUND("%s with id %s not found."),
    NOT_FOUND_BY_NAME("No %s - %s was found."),
    NOT_THE_LEADER("%s with id %s is not the leader of such team."),

    // DATABASE EXCEPTIONS
    EMPTY("No %ss were found!"),

    // ILLEGAL ARGUMENTS EXCEPTIONS
    NAME_CANT_BE_EMPTY ("Name can't be empty"),
    INVALID_YEAR("Only years after %d are accepted!"),
    INVALID_MONTH("Month must be between %d and 12!"),
    INVALID_DAY("Day must be between 1 and 31!"),
    INVALID_DATES("Dates are invalid: Either none or both dates must be given. <from> must come before <to>."),
    INVALID_DATE_FORMAT("Invalid date format. Use " + StrConst.DATE_FORMAT + ".");

    private final String message;

    Message(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return message;
    }
}
