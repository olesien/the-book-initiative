package com.books.thebookinitiative;

import java.io.IOException;
import java.net.URL;

import com.books.thebookinitiative.openlibrary.BooksList;
import com.google.gson.Gson;

import static java.lang.String.format;

public class OpenLibrary {
    BooksList getBooksBySubject(String subject, int currentPage, int per) throws IOException {
        Req req = new Req();
        String res = req.get(new URL(format("https://openlibrary.org/subjects/%s.json?details=false&offset=%d&limit=%d", subject, currentPage * per, per)));

        Gson gson = new Gson();
        BooksList booksList= gson.fromJson(res, BooksList.class);

        System.out.println(booksList);
        return booksList;
    }
}

