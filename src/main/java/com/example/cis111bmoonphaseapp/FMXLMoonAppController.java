package com.example.cis111bmoonphaseapp;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import java.util.ResourceBundle;
import java.util.Date;
import java.util.prefs.Preferences;
import java.text.SimpleDateFormat;
import javafx.application.Platform;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.net.URL;
import java.net.URI;
import java.net.URISyntaxException;
import java.io.IOException;

import com.google.gson.Gson;
import javafx.application.Application;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class FMXLMoonAppController implements Initializable {
    @FXML
    private Text APITitle;

    @FXML
    private ImageView Background;

    @FXML
    private Text Date;

    @FXML
    private ImageView MoonImage;

    @FXML
    private Text MoonPhaseName;

    @FXML
    private Button RefreshButton;

    @FXML
    private Text RiseTime;

    @FXML
    private Text SetTime;

    @FXML
    private Text TimeToUpdate;

    @FXML
    private Text Title;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
