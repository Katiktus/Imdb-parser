package ua.edu.sumdu.j2ee.pohorila.parse.model.converters;

import org.springframework.core.convert.converter.Converter;
import ua.edu.sumdu.j2ee.pohorila.parse.model.entities.Film;
import org.json.*;

public class StringToFilmConverter implements Converter<String, Film>{
        public Film convert(String source) {
            Film film = new Film();
            try {
                JSONObject obj = new JSONObject(source);
                film.setTitle(obj.getString("Title"));
                film.setDirectory(obj.getString("Director"));
                film.setDescription(obj.getString("Plot"));
                film.setPosterLink(obj.getString("Poster"));
                film.setDuration(obj.getString("Runtime"));
                film.setYear(obj.getString("Year"));
                film.setGenre(obj.getString("Genre"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return film;
       }

   /* */
}
