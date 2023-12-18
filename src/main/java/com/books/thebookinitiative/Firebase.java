package com.books.thebookinitiative;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static java.lang.String.format;

public class Firebase {
    Req req = new Req();

    public List<String> getCategories(URL url) {
        try {
            String res = req.get(url);

            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            return gson.fromJson(res, new TypeToken<List<String>>(){}.getType());

        }
        catch (IOException e) {e.printStackTrace();}
        return new ArrayList<>();
    }

    public List<Review> getReviewsByBookId(String bookId) {
        try {
            String res = req.get(new URL(format("https://books-initiative-default-rtdb.europe-west1.firebasedatabase.app/reviews/%s.json", bookId)));

            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            return gson.fromJson(res, new TypeToken<List<Review>>(){}.getType());

        }
        catch (IOException e) {e.printStackTrace();}
        return new ArrayList<>();
    }

    /*void setNote(String note) throws IOException {

        HttpURLConnection con = (HttpURLConnection)url.openConnection();
        con.setRequestMethod("PUT");
        con.setRequestProperty("Content-Type", "application/json");
        con.setRequestProperty("Accept", "application/json");
        con.setDoOutput(true);
        String jsonInputString = "{\"note\": \"" + note + "\"}";

        try(OutputStream os = con.getOutputStream()) {
            byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }

        try(BufferedReader br = new BufferedReader(
                new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8))) {
            StringBuilder response = new StringBuilder();
            String responseLine = null;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
            System.out.println(response.toString());
        }
    }*/
}
