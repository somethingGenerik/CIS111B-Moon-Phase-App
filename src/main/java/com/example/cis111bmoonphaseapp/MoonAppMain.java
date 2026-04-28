/**
 *  Programmer(s):       [Jack Durand, Ahmed Ahmed, Taylor Hollaway]
 *
 *  Program Name:        [MoonAppMain.Java]
 *
 *  Date Written:        [4/29/2026]
 *
 */

package com.example.cis111bmoonphaseapp;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.util.*;

public class MoonAppMain extends Application
{
    public static void main(String[] args)
    {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception
    {
        // Load the GUI from FXML built in Scene Builder
        Parent root = FXMLLoader.load(getClass().getResource("MoonFMXL.fxml"));
        Scene scene = new Scene(root);
        stage.setTitle("LunarPhase Lite");
        // The stylesheet is currently set in Scene Builder
        // Note you can also load this programmatically
        // scene.getStylesheets().add("styles.css");

        stage.setScene(scene);
        stage.show();

        // All of the code that interacts with the API and the Scene
        //   is found in FXMLTempAppController.java

    }

    @Override
    public void stop() {
        System.out.println("Stop is called in Application class");
    }

}