module com.example.tictactoefeb {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.tictactoefeb to javafx.fxml;
    exports com.example.tictactoefeb;
}