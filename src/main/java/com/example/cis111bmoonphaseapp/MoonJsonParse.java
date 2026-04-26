package com.example.cis111bmoonphaseapp;

import com.google.gson.Gson;

import java.time.Instant;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class MoonJsonParse {

    public static void main(String[] args) {

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

            //prints moon pase rigt now
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

}

//        String data = """
//            {
//           "timestamp": 1768327440,
//           "datestamp": "Tue, 13 Jan 2026 18:04:00 +0000",
//           "plan": "MEGA",
//           "sun": {
//             "sunrise": 1768291282,
//             "sunrise_timestamp": "08:01",
//             "sunset": 1768321044,
//             "sunset_timestamp": "16:17",
//             "solar_noon": "12:09",
//             "day_length": "08:16",
//             "position": {
//               "altitude": -15.787896,
//               "azimuth": 255.330169,
//               "distance": 147145019639.96698
//             },
//             "next_solar_eclipse": {
//               "timestamp": 1771329600,
//               "datestamp": "Tue, 17 Feb 2026 12:00:00 +0000",
//               "type": "Annular Solar Eclipse",
//               "visibility_regions": "s Argentina & Chile, s Africa, Antarctica ; [Annular: Antarctica]"
//             }
//           },
//           "moon": {
//             "phase": 0.8294482328,
//             "phase_name": "Waning crescent",
//             "major_phase": "New Moon",
//             "stage": "waning",
//             "illumination": "22%",
//             "age_days": 24,
//             "lunar_cycle": "22.49%",
//             "emoji": "🌒",
//             "zodiac": {
//               "sun_sign": "Capricorn",
//               "moon_sign": "Scorpio"
//             },
//             "moonrise": "02:27",
//             "moonrise_timestamp": 1768184871,
//             "moonset": "11:17",
//             "moonset_timestamp": 1768216634,
//             "detailed": {
//               "position": {
//                 "altitude": -55.204021,
//                 "azimuth": 308.80069,
//                 "distance": 405395288.669185,
//                 "parallactic_angle": 29.033486,
//                 "phase_angle": 123.39
//               },
//               "visibility": {
//                 "visible_hours": 0,
//                 "best_viewing_time": "02:27",
//                 "visibility_rating": "Poor",
//                 "illumination": "22.49%",
//                 "viewing_conditions": {
//                   "phase_quality": "Good for observing surface detail along terminator line",
//                   "recommended_equipment": {
//                     "filters": "No filters needed",
//                     "telescope": "4-inch or larger recommended",
//                     "best_magnification": "High magnification (100-200x) for crater detail"
//                   }
//                 }
//               },
//               "upcoming_phases": {
//                 "new_moon": {
//                   "last": {
//                     "timestamp": 1766024400,
//                     "datestamp": "Wed, 07 Jan 2026 00:00:00 +0000",
//                     "days_ago": 6
//                   },
//                   "next": {
//                     "timestamp": 1768732800,
//                     "datestamp": "Fri, 06 Feb 2026 00:00:00 +0000",
//                     "days_ahead": 24
//                   }
//                 }
//               },
//               "illumination_details": {
//                 "percentage": 22.49,
//                 "visible_fraction": 0.2249,
//                 "phase_angle": 123.39
//               }
//             }
//           }
//         }   """;