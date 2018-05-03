package com.example.ahmedelbasha.quakereport;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class EarthquakeAdapter extends ArrayAdapter<Earthquake> {
    private static final String LOCATION_SEPARATOR = " of ";
    /**
     * Constructor
     *
     * @param context  The current context.
     * @param earthquakes  The objects to represent in the ListView.
     */
    public EarthquakeAdapter(@NonNull Context context, @NonNull ArrayList<Earthquake> earthquakes) {
        super(context, 0, earthquakes);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;

        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }

        Earthquake currentEarthquake = getItem(position);

        double earthquakeMagnitude = currentEarthquake.getEarthquakeMagnitude();
        String formattedMagnitude = formatMagnitude(earthquakeMagnitude);

        TextView magnitudeTextView = listItemView.findViewById(R.id.magnitude_text_view);
        magnitudeTextView.setText(formattedMagnitude);

        String earthquakeOriginalLocation = currentEarthquake.getEarthquakeLocation();

        // this code is corresponding to my solution.
        TextView locationOffsetTextView = listItemView.findViewById(R.id.location_offset_text_view);
        locationOffsetTextView.setText(setLocationOffsetValue(earthquakeOriginalLocation));

        TextView locationTextView = listItemView.findViewById(R.id.primary_location_text_view);
        locationTextView.setText(setPrimaryLocationValue(earthquakeOriginalLocation));

        // and this is how Google Developers fixed the issue.

        String primaryLocation = "";
        String locationOffset = "";

        if (earthquakeOriginalLocation.contains(LOCATION_SEPARATOR)) {
            String[] parts = earthquakeOriginalLocation.split(LOCATION_SEPARATOR);
            locationOffset = parts[0] + LOCATION_SEPARATOR;
            primaryLocation = parts[1];
            locationOffsetTextView.setText(locationOffset);
            locationTextView.setText(primaryLocation);
        } else {
            locationOffset = getContext().getString(R.string.near_the);
            primaryLocation = earthquakeOriginalLocation;
            locationOffsetTextView.setText(locationOffset);
            locationTextView.setText(primaryLocation);
        }

        long dateInMilliseconds = currentEarthquake.getEarthquakeDate();
        Date dateObject = new Date(dateInMilliseconds);

        String formattedDate = formatDate(dateObject);

        TextView dateTextView = listItemView.findViewById(R.id.date_text_view);
        dateTextView.setText(formattedDate);

        String formattedTime = formatTime(dateObject);

        TextView timeTextView = listItemView.findViewById(R.id.time_text_view);
        timeTextView.setText(formattedTime);

        return listItemView;
    }

    /**
     * Return the formatted date string (i.e. "Mar 3, 1984") from a Date object.
     */
    private String formatDate(Date dateObject) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("LLL, dd, yyyy");
        return dateFormat.format(dateObject);
    }

    /**
     * Return the formatted date string (i.e. "4:30 PM") from a Date object.
     */
    private String formatTime(Date dateObject) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");
        return timeFormat.format(dateObject);
    }

    // This is my Code and thinking of how to solve the issue.
    private String setLocationOffsetValue(String originalLocation) {
        String locationOffsetvalue = "";
        if (originalLocation.contains("of")) {
            int ofWordPosition = originalLocation.indexOf("of");
            if (ofWordPosition != -1) {
                int targetedStartIndex = ofWordPosition + 2;
               locationOffsetvalue += originalLocation.substring(0, targetedStartIndex);
            }
        } else {
            //locationOffsetvalue = "Near the";
            locationOffsetvalue = getContext().getString(R.string.near_the);
        }
        return  locationOffsetvalue;
    }

    private String setPrimaryLocationValue(String originalLocation) {
        String primaryLocationvalue = "";
        if (originalLocation.contains("of")) {
            int ofWordPosition = originalLocation.indexOf("of");
            if (ofWordPosition != -1) {
                int targetedStartIndex = ofWordPosition + 3;
                primaryLocationvalue += originalLocation.substring(targetedStartIndex, originalLocation.length());
            }
        } else {
            primaryLocationvalue = originalLocation;
        }
        return  primaryLocationvalue;
    }

    private String formatMagnitude(Double doubleValue) {
        DecimalFormat decimalFormat = new DecimalFormat("0.0");
        return  decimalFormat.format(doubleValue);
    }
}
