package ua.edu.sumdu.j2ee.pohorila.parse.controller;

import org.apache.commons.io.FileUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.edu.sumdu.j2ee.pohorila.parse.model.entities.Film;
import ua.edu.sumdu.j2ee.pohorila.parse.model.services.Services;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
public class Controller {
    private List<Services> filmsServiceList;

    public Controller(List<Services> stockServiceList) {
        this.filmsServiceList = stockServiceList;
    }

    @RequestMapping(value = "/films", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> testMethod(@RequestParam(value = "id", defaultValue = "lol") String id) {
        System.out.println("Test method is here: " + id);
        for (Services temp : filmsServiceList) {
            temp.getFilmById(id);
        }
        return ResponseEntity.ok("Everything is ok");
    }

    public static String sendGetRequest(String requestUrl) {
        StringBuffer response = new StringBuffer();
        try {
            URL url = new URL(requestUrl);
            HttpURLConnection connection =
                    (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "*/*");
            connection.setRequestProperty("Content-Type",
                    "application/json; charset=UTF-8");
            InputStream stream = connection.getInputStream();
            InputStreamReader reader = new InputStreamReader(stream);
            BufferedReader buffer = new BufferedReader(reader);
            String line;
            while ((line = buffer.readLine()) != null) {
                response.append(line);
            }
            buffer.close();
            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response.toString();
    }


}

