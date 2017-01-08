package example.aakash.samplecollector;

/**
 * Created by Dellpc on 09-Jan-17.
 */

public class ProjectFormat {

    private String name, description, location;
    private int samples;
    private String date;


    public ProjectFormat() {
        name = "";
        description = "";
        date = "";
        location = "";
        samples = 0;
    }

    public ProjectFormat(String name, String description, String date, String location, int samples) {
        this.name = name;
        this.description = description;
        this.date = date;
        this.location = location;
        this.samples = samples;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getSamples() {
        return samples;
    }

    public void setSamples(int samples) {
        this.samples = samples;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
