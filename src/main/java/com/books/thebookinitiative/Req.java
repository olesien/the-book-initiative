package com.books.thebookinitiative;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

public class Req<T> {

    static String getResponse(InputStream stream) throws IOException {
        String buffer;
        StringBuffer response = new StringBuffer();
        BufferedReader res = new BufferedReader(new InputStreamReader(stream));
        while ((buffer = res.readLine()) != null)
            response.append(buffer);
        return response.toString();
    }

    public T get(URL url) throws IOException {
        String METHOD = "GET";
        String AUTH_STRING = ""; // Set this as required or comment it out.

        String response;

        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        conn.setRequestMethod(METHOD);
        conn.setDoOutput(false);
        conn.setRequestProperty("Authorization", AUTH_STRING);
        System.out.println("Response Code: " + conn.getResponseCode());
        if (conn.getResponseCode() != 200)
            response = getResponse(conn.getErrorStream());
        else
            response = getResponse(conn.getInputStream());
        System.out.println("Response Body: " + response);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        T thing = gson.fromJson(response, new TypeToken<T>(){}.getType());
        return thing;
    }
}
