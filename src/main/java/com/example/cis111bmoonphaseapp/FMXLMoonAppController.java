package com.example.cis111bmoonphaseapp;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

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
