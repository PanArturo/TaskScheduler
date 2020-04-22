/**
 * A class to represent a specific date (day, month, year).
 */
public class Date implements Comparable<Date>
{
    private static final String[] MONTHS = {"January", "February", "March", "April", "May", "June",
                                            "July", "August", "September", "October", "November", "December"};

    private int day, month, year;
    private Weekday weekday;

    /**
     * Initializes the date.
     * @param month An integer ranging from 1 to 12 to represent the month.
     * @param day An integer to represent the day of the month.
     * @param year An integer to represent the year.
     * @throws InvalidDateException Throws an exception if the provided date is invalid.
     */
    public Date(int month, int day, int year) throws InvalidDateException
    {
        this.day = day;
        this.month = month;
        this.year = year;
        if (!isValidDate(month, day, year))
            throw new InvalidDateException(month, day, year);
        recalculateWeekday();
    }

    /**
     * Initializes the date using an existing date to prevent unwanted changes. (copy constructor)
     * @param date The existing date used to initialize this date.
     */
    public Date(Date date)
    {
        day = date.day;
        month = date.month;
        year = date.year;
        weekday = date.weekday;
    }

    /**
     * Sets the day.
     * @throws InvalidDateException Throws an exception if the provided date is invalid.
     */
    public void setDay(int day) throws InvalidDateException
    {
        this.day = day;
        if (!isValidDate(month, day, year))
            throw new InvalidDateException(month, day, year);
        recalculateWeekday();
    }

    /**
     * Sets the month.
     * @throws InvalidDateException Throws an exception if the provided date is invalid.
     */
    public void setMonth(int month) throws InvalidDateException
    {
        this.month = month;
        if (!isValidDate(month, day, year))
            throw new InvalidDateException(month, day, year);
        recalculateWeekday();
    }

    /**
     * Sets the year.
     * @throws InvalidDateException Throws an exception if the provided date is invalid.
     */
    public void setYear(int year) throws InvalidDateException
    {
        this.year = year;
        if (!isValidDate(month, day, year))
            throw new InvalidDateException(month, day, year);
        recalculateWeekday();
    }

    /**
     * Gets the day.
     * @return
     */
    public int getDay()
    {
        return day;
    }

    /**
     * Gets the month.
     * @return
     */
    public int getMonth()
    {
        return month;
    }

    /**
     * Gets the year.
     * @return
     */
    public int getYear()
    {
        return year;
    }

    /**
     * Checks whether the provided date is valid or not.
     * @param month An integer ranging from 1 to 12 to represent the month.
     * @param day An integer to represent the day of the month.
     * @param year An integer to represent the year.
     * @return True if the date is valid, false otherwise.
     */
    private boolean isValidDate(int month, int day, int year)
    {
        if (month < 1 || month > 12)
            return false;
        int maxDay;
        switch (month)
        {
            case 1: case 3: case 5: case 7: case 8: case 10: case 12:
                maxDay = 31;
                break;
            case 4: case 6: case 9: case 11:
                maxDay = 30;
                break;
            default:
                // Handle Leap Years
                if (year % 4 == 0 && ((year % 100 != 0) || (year % 400 == 0)))
                    maxDay = 29;
                else
                    maxDay = 28;
                break;
        }
        if (day < 1 || day > maxDay)
            return false;
        return true;
    }

    /**
     * Determines which day of the week this date falls on using Zeller's congruence.
     * @return A weekday constant from the Weekday enum.
     */
    private void recalculateWeekday()
    {
        int calcYear = (month == 1 || month == 2) ? (year - 1) : year;
        int calcMonth = (month == 1 || month == 2) ? (12 + month) : month;
        int weekdayIndex = (day + (13 * (calcMonth + 1) / 5)
                + calcYear + (calcYear / 4)
                - (calcYear / 100) + (calcYear / 400) + 5) % 7;
        switch (weekdayIndex)
        {
            case 0:
                weekday = Weekday.MONDAY;
                break;
            case 1:
                weekday = Weekday.TUESDAY;
                break;
            case 2:
                weekday = Weekday.WEDNESDAY;
                break;
            case 3:
                weekday = Weekday.THURSDAY;
                break;
            case 4:
                weekday = Weekday.FRIDAY;
                break;
            case 5:
                weekday = Weekday.SATURDAY;
                break;
            default:
                weekday = Weekday.SUNDAY;
                break;
        }
    }

    /**
     * Gives the weekday this date falls on.
     * @return The weekday this date falls on.
     */
    public Weekday getWeekday()
    {
        return weekday;
    }

    /**
     * Gives the name of the month this date falls on.
     * @return A string representing the name of the month this date falls under.
     */
    public String getMonthName()
    {
        return MONTHS[month - 1];
    }

    /**
     * Gives the date in the format YYYYMMDD.
     * @return A date in the format YYYYMMDD.
     */
    public int getConcatenatedDate()
    {
        return Integer.parseInt(String.format("%4d%2d%2d", month, day, year));
    }

    /**
     * Checks to see whether two dates are equivalent.
     * @param object The date object to compare to this one.
     * @return True if the dates are equivalent, false otherwise.
     */
    @Override
    public boolean equals(Object object)
    {
        if (!(object instanceof Date))
            return false;
        Date d = (Date) object;
        return month == d.month && day == d.day && year == d.year;
    }

    /**
     * Returns a value unique to this date suitable for use as a hash code.
     * @return A value unique to this date.
     */
    @Override
    public int hashCode()
    {
        return getConcatenatedDate();
    }

    /**
     * Checks whether a date is before, after, or equivalent to this date.
     * @param date The date object to compare to this one.
     * @return 1 if this date is after the given one,
     *         0 if the dates are equivalent,
     *         or -1 if this date is before the given one.
     */
    @Override
    public int compareTo(Date date)
    {
        if (year == date.year)
        {
            if (month == date.month)
            {
                if (day == date.day)
                    return 0;
                else if (day > date.day)
                    return 1;
            }
            else if (month > date.month)
                return 1;
        }
        else if (year > date.year)
            return 1;
        return -1;
    }
}
