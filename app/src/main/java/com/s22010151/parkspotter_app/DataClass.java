
package com.s22010151.parkspotter_app;

public class DataClass {
    private String place;
    private String charge;

    private String arrival;

    private String available;
    private String imageURL;
    private String key;



    public String getPlace() {
        return place;
    }


    public String getImageURL() {
        return imageURL;
    }

    public String getCharge() {
        return charge;
    }

    public String getArrival() {
        return arrival;
    }

    public String getAvailable() {
        return available;
    }


    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public DataClass(String place, String charge, String arrival, String available, String imageURL) {
        this.place = place;
        this.charge = charge;
        this.arrival = arrival;
        this.available = available;
        this.imageURL = imageURL;

    }

    public DataClass() {

    }


}
