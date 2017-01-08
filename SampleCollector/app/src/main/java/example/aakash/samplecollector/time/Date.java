package example.aakash.samplecollector.time;

/**
 * Created by Dellpc on 06-Jan-17.
 */

public class Date {

    private int year, day, month;

    public Date() {


    }

    public Date(int d, int m, int y) {
        year = y;
        day = d;
        month = m;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }
}
