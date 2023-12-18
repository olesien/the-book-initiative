package com.books.thebookinitiative.openlibrary;

import com.google.gson.*;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class Book {
    public String key;
    public String title;

    public ArrayList<Author2> authors;

    //public LibraryKey type;

    //public ArrayList<String> subjects;

    //public ArrayList<Integer> covers;

    //public String first_publish_date;

    //Override the way to deal with description as it can return two different types (thanks OpenLibrary)
    @SerializedName("description")
    @JsonAdapter(DescriptionDeserializer.class)
    public String description;

    //public ArrayList<Link> links;


}

