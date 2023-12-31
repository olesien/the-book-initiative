package com.books.thebookinitiative;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class ApiRoot {

    static String getResponse(InputStream stream) throws IOException {
        String buffer;
        StringBuilder response = new StringBuilder();
        BufferedReader res = new BufferedReader(new InputStreamReader(stream));
        while ((buffer = res.readLine()) != null)
            response.append(buffer);
        return response.toString();
    }

    public String get(URL url) throws IOException {
        String METHOD = "GET";
        String AUTH_STRING = "";

        String response;

        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        conn.setRequestMethod(METHOD);
        conn.setRequestProperty("Accept", "application/json");
        conn.setDoOutput(false);
        conn.setRequestProperty("Authorization", AUTH_STRING);
        System.out.println("Response Code: " + conn.getResponseCode());
        if (conn.getResponseCode() != 200)
            response = getResponse(conn.getErrorStream());
        else
            response = getResponse(conn.getInputStream());
        System.out.println("Response Body: " + response);

        return response;
    }

    public void put(URL url, String data) throws IOException {
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        conn.setRequestMethod("PUT");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("Accept", "application/json");
        conn.setDoOutput(true);

        try(OutputStream os = conn.getOutputStream()) {
            byte[] input = data.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }

        try(BufferedReader br = new BufferedReader(
                new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
            StringBuilder response = new StringBuilder();
            String responseLine;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
            System.out.println(response);
        }

    }
}
