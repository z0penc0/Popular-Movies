package ar.com.mobiledieguinho.popularmovies;

import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v4.util.Pair;
import android.text.format.Time;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by Dieguinho on 10/07/2015.
 */
public class WebProxy {
    private static final String TAG = "WebProxy";

    public final static String PARAM_KEY = "api_key";
    public final static String PARAM_SORT = "sort_by";


    public String fetchJSONData(String baseUrl, List<Pair<String, String>> params){
        Log.d(TAG, "FETCHING DATA FROM SERVER");
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        // Will contain the raw JSON response as a string.
        String jsonString;
        try {
            Uri.Builder builder = Uri.parse(baseUrl).buildUpon();
            for(Pair param : params){
                builder.appendQueryParameter((String) param.first, (String) param.second);
            }
            Uri uri = builder.build();

            Log.d(TAG, "URL: " + uri.toString());
            URL url = new URL(uri.toString());

            // Create the request to TheMovieDB, and open the connection
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // Read the input stream into a String
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                // Nothing to do.
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                // But it does make debugging a *lot* easier if you print out the completed
                // buffer for debugging.
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                // Stream was empty.  No point in parsing.
                return null;
            }
            jsonString = buffer.toString();

        } catch (IOException e) {
            Log.e(TAG, "Error ", e);
            // If the code didn't successfully get the weather data, there's no point in attemping
            // to parse it.
            return null;
        } finally{
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e(TAG, "Error closing stream", e);
                }
            }
        }

        return jsonString;
    }
}
