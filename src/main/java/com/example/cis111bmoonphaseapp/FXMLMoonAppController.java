/**
 *  Programmer(s):       [Jack Durand, Ahmed Ahmed, Taylor Hollaway]
 *
 *  Program Name:        [FXMLMoonAppController.Java]
 *
 *  Date Written:        [4/29/2026]
 *
 */

package com.example.cis111bmoonphaseapp;

import com.google.gson.Gson;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

import java.time.Instant;
import java.util.Date;
import java.text.SimpleDateFormat;
import javafx.application.Platform;
import javafx.util.Duration;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.net.URL;
import java.net.URI;
import java.net.URISyntaxException;
import java.io.IOException;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

public class FXMLMoonAppController implements Initializable {
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

    private Phase phase;

    private enum Phase {
        NEW_MOON, WAXING_CRESCENT,
        FIRST_QUARTER,
        WAXING_GIBBOUS,
        FULL_MOON,
        WANING_GIBBOUS,
        THIRD_QUARTER,
        WANING_CRESCENT
    }

    ;

    private MoonPhase currentPhase;

    private Date updateTime;

    private Timeline countdownTimer;
    private int secondsRemaining = 900;

    public static final String MOON_PHASE = "moon_phase_key";

    @FXML
    protected void handleRefreshButtonAction(ActionEvent event) {
        secondsRemaining = 900; // reset countdown on manual refresh
        RefreshButton.setDisable(true); // locks button after first refresh, forces to wait 15 min until next
        updateMoonPhaseData();
    }

    protected void updateUI() {
        // phase_name is a String, not an int
        MoonPhaseName.setText(currentPhase.moon.phase_name);

        Date today = new Date();
        SimpleDateFormat dateFmt = new SimpleDateFormat("MM/dd/yyyy");
        Date.setText(dateFmt.format(today));

        // Convert Unix timestamps to readable time
        SimpleDateFormat fmt = new SimpleDateFormat("hh:mm a");

        Date riseDate = new Date(currentPhase.moon.moonrise_timestamp * 1000L);
        Date setDate = new Date(currentPhase.moon.moonset_timestamp * 1000L);

        RiseTime.setText("Rise: " + fmt.format(riseDate));
        SetTime.setText("Set: " + fmt.format(setDate));

        setMoonImagebyPhase(currentPhase.moon.phase_name);
    }

    protected void updateMoonPhaseData() {

        //creates instance of ApiInput class which holds coordinates for blue bell and the time stamp used in URI
        com.example.cis111bmoonphaseapp.ApiInput input = new ApiInput();
        //variable to old request url
        String url = "https://moon-phase.p.rapidapi.com/advanced" + "?lat=" + input.getLatitude() + "&lon=" + input.getLongitude() + "&timestamp=" + Instant.now().getEpochSecond();
        //variable that holds the environmental variable API_KEY
        String apiKey = System.getenv("API_KEY");

        //new http client to handle request
        HttpClient client = HttpClient.newHttpClient();

        //new URI request
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("accept", "application/json")
                .header("x-rapidapi-key", apiKey)
                .GET()
                .build();

        //HTTP response needs try catch according to IntelliJ
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            //System.out.println(response.body());

            //new gson object to parse json
            Gson gson = new Gson();
            //parsing and matching data to MoonPhase object
            this.currentPhase = gson.fromJson(response.body(), MoonPhase.class);
            this.updateTime = new Date();
            Platform.runLater(this::updateUI);

            System.out.println(this.currentPhase.moon.phase_name);
            System.out.println(this.currentPhase.moon.moonrise_timestamp);
            System.out.println(this.currentPhase.moon.moonset_timestamp);


        } catch (Exception e) {
            //message that prints if anything fails
            System.out.println("Failed Parsing");
        }

    }

    protected void startCountdown() {
        countdownTimer = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            if (secondsRemaining > 0) {
                secondsRemaining--;
                int hours = secondsRemaining / 3600;
                int minutes = (secondsRemaining % 3600) / 60;
                int seconds = secondsRemaining % 60;
                TimeToUpdate.setText(String.format("Update in: %02d:%02d:%02d", hours, minutes, seconds));
            } else {
                secondsRemaining = 900; // reset to 15 min
                RefreshButton.setDisable(false); // unlocks button when timer is up
            }
        }));
        countdownTimer.setCycleCount(Timeline.INDEFINITE);
        countdownTimer.play();
    }

    public void setMoonImagebyPhase(String phase) {
        String imgphase = "";

        if(phase.equals("New Moon")) {
            imgphase = "/com/example/cis111bmoonphaseapp/phase1.png";
        }
        else if (phase.equals("Waxing crescent")) {
            imgphase = "/com/example/cis111bmoonphaseapp/phase2.png";
        }
        else if (phase.equals("First quarter")) {
            imgphase = "/com/example/cis111bmoonphaseapp/phase3.png";
        }
        else if(phase.equals("Waxing gibbous")) {
            imgphase = "/com/example/cis111bmoonphaseapp/phase4.png";
        }
        else if(phase.equals("Full moon")) {
            imgphase = "/com/example/cis111bmoonphaseapp/phase5.png";
        }
        else if (phase.equals("Waning gibbous"))
        {
            imgphase = "/com/example/cis111bmoonphaseapp/phase6.png";
        }
        else if (phase.equals("Third quarter")) {
            imgphase = "/com/example/cis111bmoonphaseapp/phase7.png";
        }
        else if(phase.equals("Waning crescent")) {
            imgphase = "/com/example/cis111bmoonphaseapp/phase8.png";
        }

        MoonImage.setImage(new Image(getClass().getResourceAsStream(imgphase)));
        }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Preferences p = Preferences.userNodeForPackage(FXMLMoonAppController.class);
        this.phase = Phase.valueOf( p.get(MOON_PHASE, Phase.WAXING_GIBBOUS.toString() ) );

        Image img = new Image(getClass().getResourceAsStream("/com/example/cis111bmoonphaseapp/background.jpg"));
        Background.setImage(img);

        updateMoonPhaseData();

        startCountdown();
    }
}
