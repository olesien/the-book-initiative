package com.books.thebookinitiative;

import java.io.IOException;
import java.net.URL;
import com.books.thebookinitiative.openlibrary.*;
import com.google.gson.Gson;
import static java.lang.String.format;

//This uses the classes found in open library folder to match the JSON from the API
public class OpenLibrary extends Req {
    public BooksList getBooksBySubject(String subject, int currentPage, int per) throws IOException {
        String res = get(new URL(format("https://openlibrary.org/subjects/%s.json?details=false&offset=%d&limit=%d", subject, currentPage * per, per)));

        Gson gson = new Gson();
        BooksList booksList= gson.fromJson(res, BooksList.class);
        return booksList;
    }

    public BookSearch getBooksBySearch(String searchText, int currentPage, int per) throws IOException {
        String url = format("https://openlibrary.org/search.json?q=%s&offset=%d&limit=%d", searchText, currentPage * per, per);
        System.out.println(url);
        String res = get(new URL(url));

        Gson gson = new Gson();
        BookSearch booksList= gson.fromJson(res, BookSearch.class);

        System.out.println(booksList);
        return booksList;
    }

    public Book getBook (String key) throws IOException {
        String url = format("https://openlibrary.org%s", key);
        System.out.println(url);
        String res = get(new URL(url));

        Gson gson = new Gson();
        return gson.fromJson(res, Book.class);
    }

    public AuthorDetailed getAuthor (String key) throws IOException {
        String url = format("https://openlibrary.org/authors/%s", key);
        String res = get(new URL(url));

        Gson gson = new Gson();
        return gson.fromJson(res, AuthorDetailed.class);
    }

    public AuthorBooks booksByAuthor (String key) throws IOException {
        String url = format("https://openlibrary.org/authors/%s/works.json?limit=50", key);
        System.out.println(url);
        String res = get(new URL(url));

        Gson gson = new Gson();
        return gson.fromJson(res, AuthorBooks.class);
    }
}

