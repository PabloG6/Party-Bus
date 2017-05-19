package momocorp.partybus.CustomObjects;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;

/**
 * Created by Pablo on 10/24/2016.
 */
public class EventInformation implements Parcelable {
    private double latitude;
    private double longitude;
    private String address;
    private String title;
    String pushID;
    Date date;
    Time time;
    private String placeId;

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public Date getDate() {
        return date;
    }

    private int age;


    public void setDrinks(boolean drinks) {
        isDrinks = drinks;
    }


    public boolean isDrinks() {
        return isDrinks;
    }

    private boolean isDrinks;


    protected EventInformation(Parcel in) {
        latitude = in.readDouble();
        longitude = in.readDouble();
        address = in.readString();
        title = in.readString();
        pushID = in.readString();
        age = in.readInt();
        price = in.readString();
        isDrinks = in.readByte() != 0;

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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    private String price;


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
        parcel.writeString(price);
        parcel.writeByte((byte) (isDrinks ? 1 : 0));

    }

    public void isDrinks(boolean isDrinks) {
        this.isDrinks = isDrinks;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    public String getPlaceId() {
        return placeId;
    }


    //interface for EventInformation
    public interface Interface {
        void setEventInformation(EventInformation eventInformation);
    }

    public static class Date {
        private int startMonth;
        private int startDay;
        private int startYear;
    public Date() {

    }
        public Date(int startMonth, int startDay, int startYear) {
            this.startMonth = startMonth;
            this.startDay = startDay;
            this.startYear = startYear;
        }

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

    //push information from object itself
    public void pushInformation() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.push().setValue(EventInformation.this);
    }


    public static class Time {
        private int minute;
        private int hour;
        public Time() {

        }
        public Time(int hour, int minute) {
            this.minute = minute;
            this.hour = hour;
        }

        public int getMinute() {
            return minute;
        }

        public void setMinute(int minute) {
            this.minute = minute;
        }

        public int getHour() {
            return hour;
        }

        public void setHour(int hour) {
            this.hour = hour;
        }
    }
}
