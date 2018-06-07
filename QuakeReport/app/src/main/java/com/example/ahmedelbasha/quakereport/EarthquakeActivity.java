package com.example.ahmedelbasha.quakereport;

import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;

import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class EarthquakeActivity extends AppCompatActivity implements LoaderCallbacks<List<Earthquake>> {

    private static final String USGS_REQUEST_URL =
            "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&orderby=time&minmag=5&limit=10";
    private static final int EARTHQUAKE_LOADER_ID = 1;
    private EarthquakeAdapter mAdapter;
    private static final String LOG_TAG = EarthquakeActivity.class.getName();
    private TextView mEmptyStateTextView;
    private ProgressBar loadingIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_activity);

        mEmptyStateTextView = findViewById(R.id.empty_state_text);

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

        LoaderManager loaderManager = getLoaderManager();
        loaderManager.initLoader(EARTHQUAKE_LOADER_ID, null, this);

        earthquakeListView.setEmptyView(mEmptyStateTextView);

        Log.d(LOG_TAG, "initLoader() is called");

        loadingIndicator = findViewById(R.id.loading_idicator);
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

    @NonNull
    @Override
    public Loader<List<Earthquake>> onCreateLoader(int id, @Nullable Bundle args) {
        Log.d(LOG_TAG, "onCreateLoader() is called.");
        return new EarthquakeLoader(this, USGS_REQUEST_URL);
    }

    @Override
    public void onLoadFinished(android.content.Loader<List<Earthquake>> loader, List<Earthquake> earthquakes) {
        Log.d(LOG_TAG, "onLoadFinished() is called.");
        mAdapter.clear();
        // If there is a valid list of {@link Earthquake}s, then add them to the adapter's
        // data set. This will trigger the ListView to update.

        if (earthquakes != null && !earthquakes.isEmpty()) {
            mAdapter.addAll(earthquakes);

            if (!(earthquakes.size() != 0)) {
                loadingIndicator.setVisibility(View.GONE);
            } else {
                mEmptyStateTextView.setText(R.string.no_earthquakes);
                loadingIndicator.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void onLoaderReset(android.content.Loader<List<Earthquake>> loader) {
        Log.d(LOG_TAG, "onLoaderReset() is called.");
        mAdapter.clear();
    }

}
