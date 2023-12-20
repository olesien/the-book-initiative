package com.books.thebookinitiative;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import com.books.thebookinitiative.openlibrary.*;
import com.google.gson.Gson;

import static java.lang.String.format;

public class OpenLibrary {
    public BooksList getBooksBySubject(String subject, int currentPage, int per) throws IOException {
        Req req = new Req();
        String res = req.get(new URL(format("https://openlibrary.org/subjects/%s.json?details=false&offset=%d&limit=%d", subject, currentPage * per, per)));

        Gson gson = new Gson();
        BooksList booksList= gson.fromJson(res, BooksList.class);

        System.out.println(booksList);
        return booksList;
    }

    public BookSearch getBooksBySearch(String searchText, int currentPage, int per) throws IOException {
        String url = format("https://openlibrary.org/search.json?q=%s&offset=%d&limit=%d", searchText, currentPage * per, per);
        System.out.println(url);
        Req req = new Req();
        String res = req.get(new URL(url));

        Gson gson = new Gson();
        BookSearch booksList= gson.fromJson(res, BookSearch.class);

        System.out.println(booksList);
        return booksList;
    }

    public Book getBook (String key) throws IOException {
        String url = format("https://openlibrary.org%s", key);
        System.out.println(url);
        Req req = new Req();
        String res = req.get(new URL(url));

        Gson gson = new Gson();
        Book book = gson.fromJson(res, Book.class);
        return book;
    }

    public AuthorDetailed getAuthor (String key) throws IOException {
        String url = format("https://openlibrary.org/authors/%s", key);
        Req req = new Req();
        String res = req.get(new URL(url));

        Gson gson = new Gson();
        AuthorDetailed author = gson.fromJson(res, AuthorDetailed.class);
        return author;
    }

    public AuthorBooks booksByAuthor (String key) throws IOException {
        String url = format("https://openlibrary.org/authors/%s/works.json?limit=50", key);
        System.out.println(url);
        Req req = new Req();
        String res = req.get(new URL(url));

        Gson gson = new Gson();
        return gson.fromJson(res, AuthorBooks.class);
    }
}

