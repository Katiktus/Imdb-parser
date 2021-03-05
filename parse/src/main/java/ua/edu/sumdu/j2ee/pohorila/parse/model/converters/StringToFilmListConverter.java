package ua.edu.sumdu.j2ee.pohorila.parse.model.converters;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.core.convert.converter.Converter;
import ua.edu.sumdu.j2ee.pohorila.parse.model.entities.Film;
import ua.edu.sumdu.j2ee.pohorila.parse.model.services.Services;

import java.util.ArrayList;
import java.util.List;

public class StringToFilmListConverter implements Converter<String, List<Film>> {

    public List<Film> convert(String source) {
        List<Film> films = new ArrayList<Film>();
        StringToFilmConverter converter = new StringToFilmConverter();
        try {
            JSONObject obj = new JSONObject(source);
            JSONArray arr = obj.getJSONArray("Search");
            for (int i=0; i<arr.length(); i++) {
                JSONObject search = arr.getJSONObject(i);
                String jsonResponse = Services.searchMovieByImdb(search.getString("imdbID"), "6b935860");
                Film film = converter.convert(jsonResponse);
                films.add(film);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return films;
    }
}
