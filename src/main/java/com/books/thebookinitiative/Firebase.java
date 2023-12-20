package com.books.thebookinitiative;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static java.lang.String.format;

public class Firebase extends Req {

    public List<String> getCategories(URL url) {
        try {
            String res = get(url);

            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            return gson.fromJson(res, new TypeToken<List<String>>(){}.getType());

        }
        catch (IOException e) {e.printStackTrace();}
        return new ArrayList<>();
    }

    public List<Review> getReviewsByBookId(String bookId) {
        try {
            String res = get(new URL(format("https://books-initiative-default-rtdb.europe-west1.firebasedatabase.app/reviews/%s.json", bookId)));

            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            return gson.fromJson(res, new TypeToken<List<Review>>(){}.getType());

        }
        catch (IOException e) {e.printStackTrace();}
        return new ArrayList<>();
    }

    public HashMap<String, List<Review>> getAllReviews() {
        try {
            String res = get(new URL("https://books-initiative-default-rtdb.europe-west1.firebasedatabase.app/reviews.json"));

            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            return gson.fromJson(res, new TypeToken<HashMap<String, List<Review>>>(){}.getType());

        }
        catch (IOException e) {e.printStackTrace();}
        return new HashMap<>();
    }

    public void addReview(String bookId, int ratingNumber, String name, String title, String description) throws IOException {
        System.out.println(bookId);
        System.out.println(ratingNumber);
        System.out.println(name);
        System.out.println(title);
        System.out.println(description);

        String textUrl = "https://books-initiative-default-rtdb.europe-west1.firebasedatabase.app/reviews/" + bookId + ".json";
        System.out.println(textUrl);
        URL url = new URL(textUrl);

        List<Review> reviews = getReviewsByBookId(bookId);

        if (reviews == null) {
            reviews = new ArrayList<>();
        }
        Review newReview = new Review();
        newReview.content = description;
        newReview.name = name;
        newReview.title = title;
        newReview.count = ratingNumber;
        reviews.add(newReview);
        JsonArray reviewArr = new JsonArray();

        reviews.forEach(review -> {
            JsonObject reviewObject = new JsonObject();
            reviewObject.addProperty("content", review.content);
            reviewObject.addProperty("count", review.count);
            reviewObject.addProperty("name", review.name);
            reviewObject.addProperty("title", review.title);
            reviewArr.add(reviewObject);
        });
        System.out.println(reviewArr);
        put(url, reviewArr.toString());

    }
}
