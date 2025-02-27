package com.ctw.workstation.util;

import com.ctw.workstation.core.Pair;
import com.ctw.workstation.item.Location;
import com.ctw.workstation.item.Team;

import java.util.*;
import java.util.stream.Collectors;

public final class Util {

    public static final int YEAR_OFFSET = 1900;
    public static final int MONTH_OFFSET = 1;
    public static final int DAY_OFFSET = 0;



    /**
     * Returns true if the current time is between the set interval
     * @param from the Date the interval starts on
     * @param to the Date the interval finishes
     * @return TRUE if we are in the given interval, FALSE otherwise
     */
    public static boolean areWeBetweenDates(Date from, Date to) {
        Date now = new Date();
        return now.before(to) && now.after(from);
    }

    /**
     * Returns true if the intervals are concurrent.
     * This happens when one of the intervals has some time concurrent to the other interval
     * @param first one of the intervals of time
     * @param second another interval of time
     * @return true if there is at least a bit of time concurrent between these intervals
     */
    public static boolean areIntervalsConcurrent(Pair<Date, Date> first, Pair<Date, Date> second) {
        return isIntervalPartiallyConcurrent(first, second) || isIntervalPartiallyConcurrent(second, first) ||
                isIntervalContained(first, second) || isIntervalContained(second, first) || first.equalContents(second);
    }

    public static boolean isInTheFuture(Date d) {
        return d.after(new Date());
    }

    /**
     * Generates a date object with the given Year, Month and Day
     * @param year Year of the date
     * @param month Month of the date
     * @param day Day of the date
     * @return the Date object
     */
    @SuppressWarnings({"deprecation"})
    public static Date generateDate(int year, int month, int day) throws IllegalArgumentException {
        if (year < YEAR_OFFSET) {
            throw new IllegalArgumentException(String.format(Message.INVALID_YEAR.toString(), YEAR_OFFSET));
        }
        if (month < MONTH_OFFSET || month > MONTH_OFFSET + 12) {
            throw new IllegalArgumentException(String.format(Message.INVALID_MONTH.toString(), MONTH_OFFSET));
        }
        if (day < DAY_OFFSET + 1 || day > DAY_OFFSET + 31) {
            throw new IllegalArgumentException(Message.INVALID_DAY.toString());
        }
        return new Date(year - YEAR_OFFSET, month - MONTH_OFFSET, day - DAY_OFFSET);
    }

    /**
     * Returns the current Year
     * Example of outcomes: 1999, 2025
     *
     * @return the year the system is currently on
     */
    @SuppressWarnings("deprecation")
    public static int getCurrentYear() {
        return new Date().getYear() + YEAR_OFFSET;
    }

    @SuppressWarnings("deprecation")
    public static String toString(Date date) {
        return (date.getDate() + DAY_OFFSET) + "-" + (date.getMonth() + MONTH_OFFSET) + "-" + (date.getYear() + YEAR_OFFSET);
    }

    /**
     * Verifies if a string is either null or empty
     * @param s the string to perform the check on
     * @return TRUE if s is either null or empty, FALSE otherwise
     */
    public static boolean nullOrEmpty(String s) {
        return s == null || s.isEmpty();
    }

    /**
     * Returns true if the second interval starts before the first one finishes, but the second is not totally contained within the first.
     * @param first the first time interval
     * @param second the second time interval
     * @return true if the first interval finishes before the second.
     */
    private static boolean isIntervalPartiallyConcurrent(Pair<Date, Date> first, Pair<Date, Date> second) {
        return first.left().before(second.left()) && first.right().after(second.left());
    }

    /**
     * Returns true if the second interval is totally contained within the first interval
     * @param first the first time interval
     * @param second the second time interval
     * @return true if the second interval is contained within the first one
     */
    private static boolean isIntervalContained(Pair<Date, Date> first, Pair<Date, Date> second) {
        return first.left().before(second.left()) && first.right().after(second.right());
    }
}
