package com.books.thebookinitiative.controllers;


import com.books.thebookinitiative.Firebase;
import com.books.thebookinitiative.OpenLibrary;
import com.books.thebookinitiative.Review;
import com.books.thebookinitiative.openlibrary.Author;
import com.books.thebookinitiative.openlibrary.Book;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.List;

import static java.lang.String.format;

public class AddReviewController {
    String bookId;

    int ratingNumber = 0;
    OpenLibrary openLibraryAPI = new OpenLibrary();

    Firebase firebase = new Firebase();

    @FXML
    TextField nameField;

    @FXML
    TextField titleField;

    @FXML
    TextField descriptionField;

    @FXML
    ImageView rating1;

    @FXML
    ImageView rating2;

    @FXML
    ImageView rating3;

    @FXML
    ImageView rating4;

    @FXML
    ImageView rating5;

    @FXML
    protected void submitReview() {

    }

    public void setRating (int newRating) {
        System.out.println("Setting rating to");
        System.out.println(newRating);
        ratingNumber = newRating;
    }


    public void fillIcons(int upTo, Image icon) {
        if (upTo > 0) {
            rating1.setImage(icon);
        }
        if (upTo > 1) {
            rating2.setImage(icon);
        }
        if (upTo > 2) {
            rating3.setImage(icon);
        }
        if (upTo > 3) {
            rating4.setImage(icon);
        }
        if (upTo > 4) {
            rating5.setImage(icon);
        }
    }

    public void setEvents(ImageView rating, int value, Image icon, Image iconFilled) {
        rating.setImage(icon);
        rating.hoverProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println(ratingNumber);
                if (ratingNumber != 5 && value != 5) {
                    //Empty all first
                    fillIcons(5, icon);
                }
                if (newValue) {
                    fillIcons(value, iconFilled);
                } else if (ratingNumber > 0) { //We have a proper rating to go back to
                    fillIcons(ratingNumber, iconFilled);
                }
        });

        rating.setOnMouseClicked(e -> {
            System.out.println("Yellow");
            setRating(value);
            if (value != 5) {
                //If we don't want to fill all, clear all 5 and then set new ones
                fillIcons(5, icon);
            }
            fillIcons(value, iconFilled);
        });
    }

    public void init(String bookId)
    {
        this.bookId = bookId;

        Image icon = new Image("file:images/unfilled.png");
        Image iconFilled = new Image("file:images/filled.png");
        rating1.setImage(icon);
        setEvents(rating1, 1, icon, iconFilled);
        setEvents(rating2, 2, icon, iconFilled);
        setEvents(rating3, 3, icon, iconFilled);
        setEvents(rating4, 4, icon, iconFilled);
        setEvents(rating5, 5, icon, iconFilled);

    }

}