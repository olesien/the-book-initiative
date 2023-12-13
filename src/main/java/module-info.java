module com.books.thebookinitiative {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.books.thebookinitiative to javafx.fxml;
    exports com.books.thebookinitiative;
}