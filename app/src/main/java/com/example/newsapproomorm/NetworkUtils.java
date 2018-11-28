package com.example.newsapproomorm;

import android.net.Uri;

import com.example.newsapproomorm.data.APIkey;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class NetworkUtils {
    final static String NEWSAPP_BASE_URL =
            "https://newsapi.org/v1/articles";
    final static String PARAM_SOURCE = "source";
    final static String SOURCE = "the-next-web";
    final static String PARAM_SORT = "sortBy";
    final static String sortBy = "latest";
    final static String API_KEY = "apiKey";
    final static String apiKey = APIkey.getApiKey();

    public static URL buildUrl() {
        Uri builtUri = Uri.parse(NEWSAPP_BASE_URL).buildUpon()
                .appendQueryParameter(PARAM_SOURCE, SOURCE)
                .appendQueryParameter(PARAM_SORT, sortBy)
                .appendQueryParameter(API_KEY, apiKey)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }
}
