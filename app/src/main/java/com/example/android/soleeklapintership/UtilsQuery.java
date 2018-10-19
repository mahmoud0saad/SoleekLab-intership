package com.example.android.soleeklapintership;

import android.graphics.Bitmap;
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
import java.util.ArrayList;
import java.util.List;

public class UtilsQuery {
    public List<String> fetchDataFromURL(String url) {
        Log.i("test", "fetchDataFromURL: start");
        List<String> reportList = null;
        String jsonString = null;
        if (url == null || url.isEmpty()) {
            return null;
        }
        URL urlObject = makeURLObject(url);
        try {
            jsonString = makeHTTPConnect(urlObject);
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("UtilsQuery", "fetchDataFromURL: ", e);
        }
        Log.e("util", "the response json is : " + jsonString);
        reportList = extraReportsFromJson(jsonString);
        return reportList;

    }
    public URL makeURLObject(String url) {
        URL urlObject = null;
        if (url == null) {
            return urlObject;
        }
        try {
            urlObject = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            Log.e("UtilsQuery", "makeURLObject:  error in make url", e);
        }
        return urlObject;
    }
    public String makeHTTPConnect(URL url) throws IOException {
        String jsonString = null;
        InputStream inputStream = null;
        HttpURLConnection urlConnection = null;
        if (url == null) {
            return jsonString;
        }
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(20000);
            urlConnection.setConnectTimeout(25000);
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonString = extraFromInputstream(inputStream);
                Log.i("test", "makeHTTPConnect: " + jsonString);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
        return jsonString;
    }
    public String extraFromInputstream(InputStream inputStream) {
        StringBuilder builderJson = new StringBuilder();
        if (inputStream == null) {
            return null;
        }
        try {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                builderJson.append(line);
                line = reader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("UtilsQuery", "extraStringFromInputstream:  error in read from bufferd reader first line", e);
        } finally {

        }

        return builderJson.toString();
    }


    public List<String> extraReportsFromJson(String jsonString) {
        if (jsonString == null || jsonString.isEmpty()) {
            return null;
        }
        List<String> reportList = new ArrayList<>();
        try {
            JSONObject root = new JSONObject(jsonString);
            JSONArray resultArray = root.getJSONArray("result");

            for (int i = 0; i < resultArray.length(); i++) {
                JSONObject reportObject = resultArray.getJSONObject(i);

                String title = reportObject.optString("name");
                reportList.add(title);
            }
        } catch (JSONException e) {
            e.printStackTrace();

            Log.e("UtilsQuery", "extraReportsFromJson: error in parsing json to list of report", e);
        }
        return reportList;
    }

}
