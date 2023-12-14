package com.books.thebookinitiative;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class BooksController {

    Firebase firebase = new Firebase();
    @FXML
    private TextField search;

    @FXML
    private ComboBox category;

    @FXML
    private VBox list;

    BooksController() {
        System.out.println("START");
    }


    public void init()
    {
        //Run on start
        System.out.println("Started");

        /*try {
            List<String> categories = firebase.getCategories(new URL("https://books-initiative-default-rtdb.europe-west1.firebasedatabase.app/categories.json"));
            System.out.println(categories);
            category.setItems(FXCollections.observableList(categories));
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }*/
    }

}