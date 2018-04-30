package com.example.ahmedelbasha.quakereport;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class EarthquakeAdapter extends ArrayAdapter<Earthquake> {
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

        TextView magnitudeTextView = listItemView.findViewById(R.id.magnitude_text_view);
        magnitudeTextView.setText(String.valueOf(currentEarthquake.getEarthquakeMagnitude()));

        TextView locationTextView = listItemView.findViewById(R.id.location_text_view);
        locationTextView.setText(currentEarthquake.getEarthquakeLocation());

        TextView dateTextView = listItemView.findViewById(R.id.date_text_view);
        dateTextView.setText(currentEarthquake.getEarthquakeDate());

        return listItemView;
    }
}
