module com.example.cis111bmoonphaseapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.net.http;
    requires java.prefs;
    requires com.google.gson;
    requires javafx.graphics;


    opens com.example.cis111bmoonphaseapp to javafx.fxml;
    exports com.example.cis111bmoonphaseapp;
    //exports;
    //opens to
}