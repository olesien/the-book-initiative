module com.books.thebookinitiative {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;

    opens com.books.thebookinitiative to javafx.fxml;
    exports com.books.thebookinitiative;
    exports com.books.thebookinitiative.openlibrary;
    opens com.books.thebookinitiative.openlibrary to javafx.fxml;
}