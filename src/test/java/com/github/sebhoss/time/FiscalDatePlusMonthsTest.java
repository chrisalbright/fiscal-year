/*
 * Copyright © 2013 Sebastian Hoß <mail@shoss.de>
 * This work is free. You can redistribute it and/or modify it under the
 * terms of the Do What The Fuck You Want To Public License, Version 2,
 * as published by Sam Hocevar. See http://www.wtfpl.net/ for more details.
 */
/*
 * This program is free software. It comes without any warranty, to
 * the extent permitted by applicable law. You can redistribute it
 * and/or modify it under the terms of the Do What The Fuck You Want
 * To Public License, Version 2, as published by Sam Hocevar. See
 * http://www.wtfpl.net/ for more details.
 */
package com.github.sebhoss.time;

import java.time.LocalDate;
import java.time.Month;
import java.time.Period;
import java.util.Random;

import com.github.sebhoss.warnings.CompilerWarnings;

import org.junit.Assert;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

/**
 * Test cases for {@link FiscalDate#plusMonths(long)}.
 */
@RunWith(Theories.class)
@SuppressWarnings(CompilerWarnings.STATIC_METHOD)
public class FiscalDatePlusMonthsTest {

    /** @see TestObjects#supportedMonths() */
    @DataPoints
    public static final Month[]     START_DATES        = TestObjects.supportedMonths();

    /** @see TestObjects#startDates() */
    @DataPoints
    public static final LocalDate[] MONTH_START_DATES  = TestObjects.startDates();

    /** @see TestObjects#middleDates() */
    @DataPoints
    public static final LocalDate[] MONTH_MIDDLE_DATES = TestObjects.middleDates();

    /** The amount of months to add a given date */
    @DataPoints
    public static final int[]       ADDITIONAL_MONTHS  = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 25, 50, 100, 5000 };

    private static final Random     RANDOM             = new Random();

    /** The amount of random months to add a given date */
    @DataPoints
    public static final int[]       RANDOM_MONTHS      = RANDOM.ints(100, -292275054, 292278993).toArray();

    /**
     * Ensures that for any given date a number of years can be added in an early fiscal year.
     *
     * @param startDate
     *            The start date of the fiscal year.
     * @param currentDate
     *            The current date in a calendar year.
     * @param additionalMonths
     *            The amounts of months to add.
     */
    @Theory
    public void shouldAddMonthsInEarlyFiscalYear(final Month startDate, final LocalDate currentDate,
            final int additionalMonths) {
        // Given
        final FiscalDate fiscalDate = FiscalYears.earlyFiscalYear(startDate.getValue()).createFromCalendarDate(
                currentDate);

        // When
        final FiscalDate newDate = fiscalDate.plusMonths(additionalMonths);

        // Then
        Assert.assertEquals(
                additionalMonths,
                Period.between(FiscalDatePlusMonthsTest.toLocalDate(fiscalDate),
                        FiscalDatePlusMonthsTest.toLocalDate(newDate)).toTotalMonths());
    }

    /**
     * Ensures that for any given date a number of years can be added in an early fiscal year.
     *
     * @param startDate
     *            The start date of the fiscal year.
     * @param currentDate
     *            The current date in a calendar year.
     * @param additionalMonths
     *            The amounts of months to add.
     */
    @Theory
    public void shouldAddMonthsInLateFiscalYear(final Month startDate, final LocalDate currentDate,
            final int additionalMonths) {
        // Given
        final FiscalDate fiscalDate = FiscalYears.lateFiscalYear(startDate.getValue()).createFromCalendarDate(
                currentDate);

        // When
        final FiscalDate newDate = fiscalDate.plusMonths(additionalMonths);

        // Then
        Assert.assertEquals(
                additionalMonths,
                Period.between(FiscalDatePlusMonthsTest.toLocalDate(fiscalDate),
                        FiscalDatePlusMonthsTest.toLocalDate(newDate)).toTotalMonths());
    }

    private static LocalDate toLocalDate(final FiscalDate fiscalDate) {
        return LocalDate.of((int) fiscalDate.getCalendarYear(), (int) fiscalDate.getCalendarMonth(),
                (int) fiscalDate.getCalendarDayOfMonth());
    }

}
