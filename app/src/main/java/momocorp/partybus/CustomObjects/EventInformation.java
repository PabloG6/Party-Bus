package momocorp.partybus.CustomObjects;

import android.os.Parcel;
import android.os.Parcelable;

import java.lang.reflect.ParameterizedType;

/**
 * Created by Pablo on 10/24/2016.
 */
public class EventInformation implements Parcelable{
    private double latitude;
    private double longitude;
    private String address;
    private String title;
    String pushID;
    private int age;


    protected EventInformation(Parcel in) {
        latitude = in.readDouble();
        longitude = in.readDouble();
        address = in.readString();
        title = in.readString();
        pushID = in.readString();
        age = in.readInt();
        price = in.readDouble();
    }

    public static final Creator<EventInformation> CREATOR = new Creator<EventInformation>() {
        @Override
        public EventInformation createFromParcel(Parcel in) {
            return new EventInformation(in);
        }

        @Override
        public EventInformation[] newArray(int size) {
            return new EventInformation[size];
        }
    };

    public int getAge() {
        return age;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    private double price;


    // TODO: 1/11/2017 store latitude and longitude as a latlng object
    public String getPushID() {
        return pushID;
    }

    public void setPushID(String pushID) {
        this.pushID = pushID;
    }

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeDouble(latitude);
        parcel.writeDouble(longitude);
        parcel.writeString(address);
        parcel.writeString(title);
        parcel.writeString(pushID);
        parcel.writeInt(age);
        parcel.writeDouble(price);
    }
    //interface for EventInformation
    public interface Interface {
        void setEventInformation(EventInformation eventInformation);
    }

    public static class Date {
        private int startMonth;
        private int startDay;
        private int startYear;
        public void setStartMonth(int startMonth) {
            this.startMonth = startMonth;
        }
        public static class DBuilder {

        }
        public void setStartDay(int startDay) {
            this.startDay = startDay;
        }

        public int getStartMonth() {
            return startMonth;
        }

        public int getStartDay() {
            return startDay;
        }

        public int getStartYear() {
            return startYear;
        }

        public void setStartYear(int startYear) {

            this.startYear = startYear;
        }

    }

    public void setAge(int age) {
        this.age = age;
    }

}
