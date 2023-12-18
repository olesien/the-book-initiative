package com.books.thebookinitiative.controllers;


import com.books.thebookinitiative.OpenLibrary;
import com.books.thebookinitiative.openlibrary.Author;
import com.books.thebookinitiative.openlibrary.Book;
import com.books.thebookinitiative.openlibrary.BooksList;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

import java.io.IOException;

public class BookController {
    OpenLibrary openLibraryAPI = new OpenLibrary();

    @FXML
    Text title;

    @FXML
    Text author;

    @FXML
    Text description;

    public void init(String key, Author authorObject)
    {
        try {
            Book book = openLibraryAPI.getBook(key);
            System.out.println(book);
            title.setText(book.title);
            author.setText(authorObject.name);

            description.setText(book.description);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}