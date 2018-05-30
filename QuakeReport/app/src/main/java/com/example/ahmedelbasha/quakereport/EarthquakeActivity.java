package com.example.ahmedelbasha.quakereport;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class EarthquakeActivity extends AppCompatActivity {

    private static final String LOG_TAG = EarthquakeActivity.class.getName();

        /** URL for earthquake data from the USGS dataset */
         private static final String USGS_REQUEST_URL =
                "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&orderby=time&minmag=5&limit=10";
    /** Adapter for the list of earthquakes */
    private EarthquakeAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_activity);




        // Find a reference to the {@link ListView} in the layout
        ListView earthquakeListView = findViewById(R.id.list);

        // Create a new {@link ArrayAdapter} of earthquakes
        mAdapter = new EarthquakeAdapter(this, new ArrayList<Earthquake>());

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        earthquakeListView.setAdapter(mAdapter);

        earthquakeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Earthquake currentEarthquake = mAdapter.getItem(position);
                String earthquakeDetailUrl = null;
                if (currentEarthquake != null) {
                    earthquakeDetailUrl = currentEarthquake.getEarthquakeDetailUrl();
                }

                showEarthquakeDetailUrl(earthquakeDetailUrl);
            }
        });

        // Start the AsyncTask to fetch the earthquake data
        EarthquakeAsyncTask task = new EarthquakeAsyncTask();
        task.execute(USGS_REQUEST_URL);
    }

    private void showEarthquakeDetailUrl(String earthquakeDetailUrl) {
        Uri earthquakeDetailURL = Uri.parse(earthquakeDetailUrl);
        Intent sendUserToEarthquakeDetailsPageIntent = new Intent(Intent.ACTION_VIEW, earthquakeDetailURL);
        if (sendUserToEarthquakeDetailsPageIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(sendUserToEarthquakeDetailsPageIntent);
        }
    }

    // navigating to parent activity with possibility to be navigating back from another app's activity.
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent upIntent = NavUtils.getParentActivityIntent(this);
                if (NavUtils.shouldUpRecreateTask(this, upIntent)) {
                    TaskStackBuilder.create(this).addNextIntentWithParentStack(upIntent).startActivities();
                } else {
                    NavUtils.navigateUpTo(this, upIntent);
                }
        }
        return super.onOptionsItemSelected(item);
    }

    private class EarthquakeAsyncTask extends AsyncTask<String, Void, List<Earthquake>> {

        @Override
        protected List<Earthquake> doInBackground(String... urls) {
            if (urls.length < 1 || urls[0] == null) {
                return null;
            }

            List<Earthquake> earthquakeList = QueryUtils.fetchEarthquakeData(urls[0]);
            return earthquakeList;
        }

        @Override
        protected void onPostExecute(List<Earthquake> data) {
            if (data != null || !data.isEmpty()) {
                mAdapter.addAll(data);
            }
            // Clear the adapter from previous earthquake data.
            mAdapter.clear();

            // If there is a valid list of {@link Earthquake}s, then add them to the adapter's
            // data set. This will trigger the ListView to update.
            if (data != null || !data.isEmpty()) {
                mAdapter.addAll(data);
            }
        }
    }
}
