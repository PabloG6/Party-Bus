package momocorp.partybus.CustomObjects;

/**
 * Created by Pablo on 10/24/2016.
 */
public class EventInformation {
    private double latitude;
    private double longitude;
    private String address;
    private String title;

    public EventInformation(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public EventInformation() {
    }

    public EventInformation(double latitude, double longitude, String address, String title) {

        this.latitude = latitude;
        this.longitude = longitude;
        this.address = address;
        this.title = title;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAddress() {
        return address;
    }

    public String getTitle() {
        return title;
    }
}
