package com.example.quakereport;

import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.content.AsyncTaskLoader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
//mport androidx.loader.content.AsyncTaskLoader;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderCallbacks<List<Earthquake>> {
    private static final String USGS_URL = " https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&orderby=time&minmag=5&limit=10";
    /**
     * Constant value for the earthquake loader ID. We can choose any integer.
     * This really only comes into play if you're using multiple loaders.
     */
    private static final int EARTHQUAKE_LOADER_ID = 1;
    private CustomAdapter adapter;

    public String LOG_TAG = "checking";

    public TextView mEmptyTextView;  //this will be set as listviews empty state view when theres no earthquake to show this will appear


    @Override
    public Loader<List<Earthquake>> onCreateLoader(int id, Bundle args) {
        Log.e(LOG_TAG, " onCreateLoader called : ");
        // Create a new loader for the given URL
        return new EarthQuakeLoader(this, USGS_URL);
    }


    @Override
    public void onLoadFinished(Loader<List<Earthquake>> loader, List<Earthquake> data) {
      View loadingIndicator = findViewById(R.id.loading_indicator);
      loadingIndicator.setVisibility(View.GONE);
        if (data == null) return;
        Log.e(LOG_TAG, "  onLoadFinished called : ");

        // If there is a valid list of {@link Earthquake}s, then add them to the adapter's
        // data set. This will trigger the ListView to update.
        updateUI(data);
        // Set empty state text to display "No earthquakes found."
        mEmptyTextView.setText(R.string.no_earthquakes);

    }

    @Override
    public void onLoaderReset(Loader<List<Earthquake>> loader) {
        Log.e(LOG_TAG, "  onLoadReset called : ");

        // Loader reset, so we can clear out our existing data.
        adapter.clear();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.e(LOG_TAG, " onCreate Main  called : " );


// Get a reference to the ConnectivityManager to check state of network connectivity
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);

        // Get details on the currently active default data network
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        // If there is a network connection, fetch data
        if (networkInfo != null && networkInfo.isConnected()) {

            // Get a reference to the LoaderManager, in order to interact with loaders.
            LoaderManager loaderManager = getLoaderManager();
            Log.e(LOG_TAG, "initLoader called : ");
            // Initialize the loader. Pass in the int ID constant defined above and pass in null for
            // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
            // because this activity implements the LoaderCallbacks interface).
            loaderManager.initLoader(EARTHQUAKE_LOADER_ID, null, this);

        }else{
// Otherwise, display error
            // First, hide loading indicator so error message will be visible
            View loadingIndicator = findViewById(R.id.loading_indicator);
            loadingIndicator.setVisibility(View.GONE);

            // Update empty state with no connection error message
            TextView mEmptyStateTextView = findViewById(R.id.empty_view);
           mEmptyStateTextView.setText(R.string.no_internet_connection);
        }

//       Work work = new Work();
//       work.execute(USGS_URL);
//       earthquakes.add(new Earthquake("4.7", "Los Angeles", "February 13 2004"));
//        earthquakes.add(new Earthquake("6.7", "San Francisco", "May 13 2005"));
//        earthquakes.add(new Earthquake("5.0", "London", "January 24 2010"));
//        earthquakes.add(new Earthquake("4.4", "Tokyo", "June 30 2005"));
//        earthquakes.add(new Earthquake("3.2", "Rio De Janario", "April 28 2014"));
//        earthquakes.add(new Earthquake("5.8", "California", "February 13 2017"));


        //Then we can get the URL from the current Earthquake object. But then how do we open the website? What if the user has multiple web browsers installed?
        // Which app should open up the earthquake website? It would be better to use the defaults the user has already chosen, or give them the option. We can do this by creating an implicit intent.
        //Instead of deciding exactly what activity we want to launch with an intent, we can instead specify what action we want to perform, without giving any option on what activity should actually handle that action.
        // In this case, we'll create an intent with the action of viewing something. What do we want to view? Well this Intent constructor also accepts a URI for the data resource we want to view, and Android will sort out the best app to handle this sort of content.
        // For instance, if the URI represented a location, Android would open up a mapping app. In this case, the resource is an HTTP URL, so Android will usually open up a browser.

    }

    public void updateUI(List<Earthquake> earthquakes) {

        ListView listView = findViewById(R.id.listView);
       adapter = new CustomAdapter(this, earthquakes);
        listView.setAdapter(adapter);
        mEmptyTextView = (TextView) findViewById(R.id.empty_view);
        listView.setEmptyView(mEmptyTextView);

        //OnItemClickListener is an interface, which contains a single method onItemClick(). We declare an anonymous class that implements this interface, and provides customized logic for what should happen in the onItemClick() method.
        // Remember that the onItemClick() method is a callback triggered by the Android system when the user clicks on a list item.
        //Note that we also had to add the “final” modifier on the EarthquakeAdapter local variable, so that we could access the adapter variable within the OnItemClickListener.
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Earthquake currentEarthquake = adapter.getItem(position);

                //The Intent constructor (that we want to use) requires a Uri object, so we need to convert our URL (in the form of a String) into a URI.
                // We know that our earthquake URL is a more specific form of a URI, so we can use the Uri.parse method
                Uri earthquakeUri = Uri.parse(currentEarthquake.getMurl());
                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, earthquakeUri);

                startActivity(websiteIntent);
            }
        });
    }

//    public class Work extends AsyncTask<String, Void , ArrayList<Earthquake>>{
//
//        @Override
//        protected ArrayList<Earthquake> doInBackground(String... urls){
//            if(urls.length==0 || urls[0]==null)return null;
//          return  QueryUtils.extractEarthquakes(urls[0]);
//        }
//
//        @Override
//        protected void onPostExecute(ArrayList<Earthquake> earthquakes) {
//            if(earthquakes == null)return;
//            updateUI(earthquakes);
//        }
//    }


}

 class EarthQuakeLoader extends AsyncTaskLoader<List<Earthquake>> {

    String murl;



    //Constructs a new loader
    public EarthQuakeLoader(Context context, String url) {
        super(context);
        murl = url;

    }

    //required step to actually trigger the loadInBackground() method to execute.
    @Override
    protected void onStartLoading() {
        Log.i("checking", "OnStartLoading called : ");
        forceLoad();
    }
    //This is on background thread
    @Nullable
    @Override
    public List<Earthquake> loadInBackground() {
        Log.e("checking", "loadInBackground called : ");
        if (murl == null) return null;

        List<Earthquake> earthquakes = QueryUtils.extractEarthquakes(murl);
        return earthquakes;
    }
}
