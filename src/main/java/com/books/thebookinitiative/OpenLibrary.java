package com.books.thebookinitiative;

import java.io.IOException;
import java.net.URL;

import com.books.thebookinitiative.openlibrary.BooksList;
import com.google.gson.Gson;

public class OpenLibrary {
    BooksList getBooksBySubject(String subject) throws IOException {
        Req req = new Req<BooksList>();
        String res = req.get(new URL("https://openlibrary.org/subjects/fiction.json?details=false"));

        Gson gson = new Gson();
        BooksList booksList= gson.fromJson(res, BooksList.class);

        System.out.println(booksList);
        return booksList;
    }
}

