package com.books.thebookinitiative.openlibrary;

import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class AuthorDetailed {
    public String title;
    public String death_date;

    public LibraryKey type;

    public String name;

    //This has the same issue as description does for books
    //So we can just use the same class to fix this issue.
    @SerializedName("bio")
    @JsonAdapter(DescriptionDeserializer.class)
    public String bio;

    public ArrayList<Integer> photos;

    public String location;

    public String birth_date;
}
