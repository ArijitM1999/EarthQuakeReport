package com.example.thomashelby.earthquakereport;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Helper methods related to requesting and receiving earthquake data from USGS.
 */
public final class QueryUtils {

    public static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e("THIS IS SPARTA", "Problem building the URL ", e);
        }
        return url;
    }

    /**
     * Make an HTTP request to the given URL and return a String as the response.
     */
    public static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        // If the URL is null, then return early.
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e("THIS IS SPARTA", "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e("THIS IS SPARTA", "Problem retrieving the earthquake JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                // Closing the input stream could throw an IOException, which is why
                // the makeHttpRequest(URL url) method signature specifies than an IOException
                // could be thrown.
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    /**
     * Convert the {@link InputStream} into a String which contains the
     * whole JSON response from the server.
     */
    public static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }
    public static List<Word> extractFeatureFromJson(String SAMPLE_JSON_RESPONSE) {
        // If the JSON string is empty or null, then return early.
        if (TextUtils.isEmpty(SAMPLE_JSON_RESPONSE)) {
            return null;
        }
        List<Word> places= new ArrayList<>();
        try {

            JSONObject root = new JSONObject(SAMPLE_JSON_RESPONSE);
            JSONArray features = root.getJSONArray("features");
            for (int i = 0; i < features.length(); i++) {
                JSONObject properties = features.getJSONObject(i);
                JSONObject prop = properties.getJSONObject("properties");
                double magnitude = prop.getDouble("mag");
                String web=prop.getString("url");
                String place = prop.getString("place");
                String[] arrOfStr = place.split("of", 2);
                String z1="";
                String z2="";
                for (int j=0;j<arrOfStr.length;j++){
                    if(j==0){
                        z1=arrOfStr[j];
                    }
                    else{
                        z2=arrOfStr[j].substring(1,arrOfStr[j].length());
                    }

                }
                long time = prop.getLong("time");

                Date dateObject = new Date(time);
                SimpleDateFormat dateFormatter = new SimpleDateFormat("dd MMMM yyyy");
                String dateToDisplay = dateFormatter.format(dateObject);

                if(z2!="") {
                    places.add(new Word(magnitude, z1+"of",z2, dateToDisplay,web));
                }
                else{
                    places.add(new Word(magnitude,"Near The", z1, dateToDisplay,web));
                }
            }
        }catch (JSONException e) {
            Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
        } ;
        return places;
    }
    public static List<Word> fetchEarthquakeData(String requestUrl) {
        // Create URL object
        URL url = createUrl(requestUrl);

        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e("YO", "Problem making the HTTP request.", e);
        }

        // Extract relevant fields from the JSON response and create a list of {@link Earthquake}s
        List<Word> earthquakes = extractFeatureFromJson(jsonResponse);

        // Return the list of {@link Earthquake}s
        return earthquakes;
    }

}