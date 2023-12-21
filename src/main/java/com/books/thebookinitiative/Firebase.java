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

//This uses the req API call functions.
//Acts as a middle layer between firebase and the java application
public class Firebase extends ApiRoot {

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

            //Create the json object
            Gson gson = new GsonBuilder().setPrettyPrinting().create();

            //Extract the json from the res and make a class from it.
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
        String textUrl = "https://books-initiative-default-rtdb.europe-west1.firebasedatabase.app/reviews/" + bookId + ".json";
        URL url = new URL(textUrl);

        List<Review> reviews = getReviewsByBookId(bookId);

        //If reviews is null (doesn't exist) then we make it an empty array
        if (reviews == null) {
            reviews = new ArrayList<>();
        }
        //Make a review object and set the content
        Review newReview = new Review();
        newReview.content = description;
        newReview.name = name;
        newReview.title = title;
        newReview.count = ratingNumber;
        reviews.add(newReview);
        JsonArray reviewArr = new JsonArray();

        reviews.forEach(review -> {
            //Translate the class into a JSON object
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
