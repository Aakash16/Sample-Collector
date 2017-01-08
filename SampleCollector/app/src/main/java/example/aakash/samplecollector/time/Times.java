package example.aakash.samplecollector.time;

/**
 * Created by Dellpc on 06-Jan-17.
 */

public class Times {

    public int hour;
    public int min;
    public String type;


    public Times() {

    }

    public Times(int h, int m, String t) {
        hour = h;
        min = m;
        type = t;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
