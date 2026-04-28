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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

import java.time.Instant;
import java.util.Date;
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

    private HttpClient client;

    private Phase phase;

    private enum Phase {NEW_MOON, WAXING_CRESCENT,
                                FIRST_QUARTER,
                                WAXING_GIBBOUS,
                                FULL_MOON,
                                WANING_GIBBOUS,
                                THIRD_QUARTER,
                                WANING_CRESCENT};

    private MoonPhase currentPhase;

    private Date updateTime;

    public static final String MOON_PHASE = "moon_phase_key";

    @FXML
    protected void handleRefreshButtonAction(ActionEvent event){ updateMoonPhaseData(); }

    //was thinking we could use the enum like the temperature app does with celsius and fahrenheit.
    // i'm sure we can cut this is need be -taylor
//    @FXML
//    protected void handlePhaseConversionButtonAction(ActionEvent event){
//        if(event.getSource() == something?)
//
//    }

    protected void updateUI(){

        MoonPhaseName.setText(String.format("%d\u00B0",this.currentPhase.moon.phase_name));
        RiseTime.setText(String.format("%d\u00B0",this.currentPhase.moon.moonrise_timestamp));
        SetTime.setText(String.format("%d\u00B0",this.currentPhase.moon.moonset_timestamp));

        SimpleDateFormat fmt = new SimpleDateFormat("MM/dd/yy hh:mm a");
        SetTime.setText(fmt.format(this.updateTime));

    }

    protected void processMoonData(String data) {
        this.updateTime = new Date();

        System.out.println(data);

        Gson gson = new Gson();
        try {
            this.currentPhase = gson.fromJson(data, MoonPhase.class);
        }catch(Exception e){
            System.out.println("GSON Parsing Failed");
            return;
        }
        Platform.runLater( new Runnable() {
                            public void run() { updateUI(); }

        } );
    }

    protected void updateMoonPhaseData(){

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
            MoonPhase moonphase = gson.fromJson(response.body(), MoonPhase.class);

            //prints moon pase right now
            System.out.println(moonphase.moon.phase_name);
            //prints timestamp of rise
            System.out.println(moonphase.moon.moonrise_timestamp);
            //prints timestamp of set
            System.out.println(moonphase.moon.moonset_timestamp);


        } catch (Exception e) {
            //message that prints if anything fails
            System.out.println("Failed Parsing");
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Preferences p = Preferences.userNodeForPackage(FXMLMoonAppController.class);
        this.phase = Phase.valueOf( p.get(MOON_PHASE, Phase.WAXING_GIBBOUS.toString() ) );

        updateMoonPhaseData();
    }
}
