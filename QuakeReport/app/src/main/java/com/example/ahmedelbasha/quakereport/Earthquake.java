package com.example.ahmedelbasha.quakereport;

public class Earthquake {

    private double mEarthquakeMagnitude;
    private String mEarthquakeLocation;
    private long mEarthquakeDate;
    private String mEarthquakeDetailUrl;

    public Earthquake(double earthquakeMagnitude, String earthquakeLocation, long earthquakeDate, String earthquakeDetailUrl) {
        mEarthquakeMagnitude = earthquakeMagnitude;
        mEarthquakeLocation = earthquakeLocation;
        mEarthquakeDate = earthquakeDate;
        mEarthquakeDetailUrl = earthquakeDetailUrl;
    }

    public Earthquake() {
        mEarthquakeMagnitude = 0.0;
        mEarthquakeLocation = "";
        mEarthquakeDate = 11111111;
        mEarthquakeDetailUrl = "";
    }

    public void setEarthquakeMagnitude(double earthquakeMagnitude) {
        this.mEarthquakeMagnitude = earthquakeMagnitude;
    }

    public void setEarthquakeLocation(String earthquakeLocation) {
        this.mEarthquakeLocation = earthquakeLocation;
    }

    public void setEarthquakeDate(long earthquakeDate) {
        this.mEarthquakeDate = earthquakeDate;
    }

    public void setEarthquakeDetailUrl(String earthquakeDetailUrl) {
        mEarthquakeDetailUrl = earthquakeDetailUrl;
    }

    public double getEarthquakeMagnitude() {
        return mEarthquakeMagnitude;
    }

    public String getEarthquakeLocation() {
        return mEarthquakeLocation;
    }

    public long getEarthquakeDate() {
        return mEarthquakeDate;
    }

    public String getEarthquakeDetailUrl() {
        return mEarthquakeDetailUrl;
    }

    @Override
    public String toString() {
        return "Earthquake{" +
                "mEarthquakeMagnitude=" + mEarthquakeMagnitude +
                ", mEarthquakeLocation='" + mEarthquakeLocation + '\'' +
                ", mEarthquakeDate='" + mEarthquakeDate + '\'' +
                '}';
    }
}
