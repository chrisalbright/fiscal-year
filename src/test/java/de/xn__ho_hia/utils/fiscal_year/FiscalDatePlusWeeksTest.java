/*
 * This file is part of fiscal-year. It is subject to the license terms in the LICENSE file found in the top-level
 * directory of this distribution and at http://creativecommons.org/publicdomain/zero/1.0/. No part of fiscal-year,
 * including this file, may be copied, modified, propagated, or distributed except according to the terms contained
 * in the LICENSE file.
 */
package de.xn__ho_hia.utils.fiscal_year;

import java.time.LocalDate;
import java.time.Month;
import java.util.Random;

import org.junit.Assert;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

import de.xn__ho_hia.quality.suppression.CompilerWarnings;

/**
 * Test cases for {@link FiscalDate#plusWeeks(long)}.
 */
@RunWith(Theories.class)
@SuppressWarnings(CompilerWarnings.STATIC_METHOD)
public class FiscalDatePlusWeeksTest {

    /** @see TestObjects#supportedMonths() */
    @DataPoints
    public static final Month[]     START_DATES        = TestObjects.supportedMonths();

    /** @see TestObjects#startDates() */
    @DataPoints
    public static final LocalDate[] MONTH_START_DATES  = TestObjects.startDates();

    /** @see TestObjects#middleDates() */
    @DataPoints
    public static final LocalDate[] MONTH_MIDDLE_DATES = TestObjects.middleDates();

    /** The amount of weeks to add a given date */
    @DataPoints
    public static final int[]       ADDITIONAL_WEEKS   = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 25, 50, 100, 5000 };

    private static final Random     RANDOM             = new Random();

    /** The amount of random weeks to add a given date */
    @DataPoints
    public static final int[]       RANDOM_WEEKS       = RANDOM.ints(100, -292275054, 292278993).toArray();

    /**
     * Ensures that for any given date a number of weeks can be added in an early fiscal year.
     *
     * @param startDate
     *            The start date of the fiscal year.
     * @param currentDate
     *            The current date in a calendar year.
     * @param additionalWeeks
     *            The amounts of weeks to add.
     */
    @Theory
    public void shouldAddWeeksInEarlyFiscalYear(final Month startDate, final LocalDate currentDate,
            final int additionalWeeks) {
        // Given
        final FiscalDate fiscalDate = FiscalYears.earlyFiscalYear(startDate.getValue()).createFromCalendarDate(
                currentDate);

        // When
        final FiscalDate newDate = fiscalDate.plusWeeks(additionalWeeks);

        // Then
        Assert.assertEquals(
                fiscalDate.asLocalDate().plusWeeks(additionalWeeks),
                newDate.asLocalDate());
    }

    /**
     * Ensures that for any given date a number of weeks can be added in a late fiscal year.
     *
     * @param startDate
     *            The start date of the fiscal year.
     * @param currentDate
     *            The current date in a calendar year.
     * @param additionalWeeks
     *            The amounts of weeks to add.
     */
    @Theory
    public void shouldAddWeeksInLateFiscalYear(final Month startDate, final LocalDate currentDate,
            final int additionalWeeks) {
        // Given
        final FiscalDate fiscalDate = FiscalYears.lateFiscalYear(startDate.getValue()).createFromCalendarDate(
                currentDate);

        // When
        final FiscalDate newDate = fiscalDate.plusWeeks(additionalWeeks);

        // Then
        Assert.assertEquals(
                fiscalDate.asLocalDate().plusWeeks(additionalWeeks),
                newDate.asLocalDate());
    }

}
