module com.example.rgz {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.rgz to javafx.fxml;
    exports com.example.rgz;
}