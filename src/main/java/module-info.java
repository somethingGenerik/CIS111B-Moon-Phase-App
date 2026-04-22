module com.example.cis111bmoonphaseapp {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.cis111bmoonphaseapp to javafx.fxml;
    exports com.example.cis111bmoonphaseapp;
}