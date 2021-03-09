package ua.edu.sumdu.j2ee.pohorila.parse.model.converters;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.core.convert.converter.Converter;
import ua.edu.sumdu.j2ee.pohorila.parse.model.entities.Film;
import ua.edu.sumdu.j2ee.pohorila.parse.model.services.ServicesInterface;

import java.util.ArrayList;
import java.util.List;

public class StringToFilmListConverter implements Converter<String, List<Film>> {
    private static final Logger logger = LogManager.getLogger();
    public List<Film> convert(String source) {
        List<Film> films = new ArrayList<Film>();
        StringToFilmConverter converter = new StringToFilmConverter();
        try {
            JSONObject obj = new JSONObject(source);
            JSONArray arr = obj.getJSONArray("Search");
            for (int i=0; i<arr.length(); i++) {
                JSONObject search = arr.getJSONObject(i);
                Film jsonResponse = ServicesInterface.getFilmById(search.getString("imdbID"),
                        "6b935860", "http://www.omdbapi.com/?i=IMDB&apikey=APIKEY") ;
                films.add(jsonResponse);
            }
        } catch (JSONException e) {
            logger.error("Error message", e);
        }
        return films;
    }
}
