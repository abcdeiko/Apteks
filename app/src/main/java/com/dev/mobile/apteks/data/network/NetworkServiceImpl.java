package com.dev.mobile.apteks.data.network;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Date;
import java.util.Locale;

public class NetworkServiceImpl implements NetworkService {

    private static String TAG = "NetworkServiceImpl";

    private String baseUrl;

    public NetworkServiceImpl(@NonNull String baseUrl, String method) {
        this.baseUrl = baseUrl;
    }

    private HttpURLConnection createConnection(@NonNull String address, String method) throws MalformedURLException, IOException {
        URL url = new URL(address);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setRequestMethod(method);
        connection.setReadTimeout(10000);

        connection.setRequestProperty("Accept-Encoding", "deflate");
        connection.setRequestProperty("Accept-Charset", "utf-8");
        connection.setRequestProperty("Content-Type", "text/html");
        connection.setRequestProperty("Connection", "Close");

        return connection;
    }

    private String sendRequest(String url, String method) throws IOException {
        HttpURLConnection connection = createConnection(url, "GET");

        //connection.setDoInput(true);
        try {
            ByteArrayOutputStream result = new ByteArrayOutputStream();

            byte[] buffer = new byte[1024];
            int length;

            while((length = connection.getInputStream().read(buffer)) != -1) {
                result.write(buffer, 0, length);
            }


            return result.toString("UTF-8");
        } finally {
            connection.disconnect();
        }
    }

    public String getDrugsResponse(@Nullable String searchText) throws IOException {

        try {
            searchText = URLEncoder.encode(searchText, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            Log.d(TAG, "Can't encode search string: " + e.toString());
        }


        String url = String.format(
                Locale.getDefault(),
                "%s/data.php?mode=sql&q=%s&limit=%d&timestamp=%d",
                baseUrl, searchText, 20, new Date().getTime()
        );

        return sendRequest(url, "GET");
    }

    public String getDrugHintsResponse(@Nullable String searchText) throws IOException {
        try {
            searchText = URLEncoder.encode(searchText, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            Log.d(TAG, "Can't encode search string: " + e.toString());
        }


        String url = String.format(
                Locale.getDefault(),
                "%s/data.php?mode=sql&q=%s&limit=%d&timestamp=%d",
                baseUrl, searchText, 20, new Date().getTime()
        );

        return sendRequest(url, "GET");
    }
}
