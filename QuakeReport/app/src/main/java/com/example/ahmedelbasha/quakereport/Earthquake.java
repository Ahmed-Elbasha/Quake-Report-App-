package com.example.ahmedelbasha.quakereport;

public class Earthquake {

    private double mEarthquakeMagnitude;
    private String mEarthquakeLocation;
    private long mEarthquakeDate;

    public Earthquake(double earthquakeMagnitude, String earthquakeLocation, long earthquakeDate) {
        this.mEarthquakeMagnitude = earthquakeMagnitude;
        this.mEarthquakeLocation = earthquakeLocation;
        this.mEarthquakeDate = earthquakeDate;
    }

    public Earthquake() {
        this.mEarthquakeMagnitude = 0.0;
        this.mEarthquakeLocation = "";
        this.mEarthquakeDate = 11111111;
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

    public double getEarthquakeMagnitude() {
        return mEarthquakeMagnitude;
    }

    public String getEarthquakeLocation() {
        return mEarthquakeLocation;
    }

    public long getEarthquakeDate() {
        return mEarthquakeDate;
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
