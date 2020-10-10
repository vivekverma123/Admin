package com.example.model;

import java.util.GregorianCalendar;

public class Helper
{

    private static int arr[] = {0,31,28,31,30,31,30,31,31,30,31,30,31};
    private static int arr2[] = {0,31,29,31,30,31,30,31,31,30,31,30,31};
    private static String[] monthName = {"", "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};

    public static String getLastDate()
    {
        GregorianCalendar Calendar = new GregorianCalendar();
        int curr_month = Calendar.getInstance().get(Calendar.MONTH) + 1;
        int prev_month;
        int prev_year;

        if(curr_month==1)
        {
            prev_month = 12;
            prev_year = Calendar.getInstance().get(Calendar.YEAR) - 1;
        }
        else
        {
            prev_month = curr_month - 1;
            prev_year = Calendar.getInstance().get(Calendar.YEAR);
        }

        String date;

        if(Calendar.isLeapYear(prev_year))
        {
            date = monthName[prev_month]+ " " + arr2[prev_month] + ", " + prev_year;
        }
        else
        {
            date = monthName[prev_month] + " " + arr[prev_month] + ", " + prev_year;
        }

        return date;
    }
}
