package com.books.thebookinitiative.controllers;

import com.books.thebookinitiative.Firebase;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import java.io.IOException;

public class AddReviewController {
    Stage stage;
    BookController bookController;
    String bookId;

    int ratingNumber = 0;

    Firebase firebase = new Firebase();

    @FXML
    TextField nameField;

    @FXML
    TextField titleField;

    @FXML
    TextArea descriptionField;

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
        try {
            firebase.addReview(bookId, ratingNumber, nameField.getText(), titleField.getText(),descriptionField.getText());
            bookController.getReviews(); //Refetch
            stage.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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

    public void init(Stage stage, String bookId, BookController bookController)
    {
        this.bookController = bookController;
        this.bookId = bookId;
        this.stage = stage;
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