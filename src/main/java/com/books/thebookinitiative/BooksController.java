package com.books.thebookinitiative;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class BooksController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}