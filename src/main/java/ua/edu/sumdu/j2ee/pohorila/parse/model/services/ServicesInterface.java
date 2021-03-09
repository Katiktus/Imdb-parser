package ua.edu.sumdu.j2ee.pohorila.parse.model.services;

import org.springframework.beans.factory.annotation.Value;
import ua.edu.sumdu.j2ee.pohorila.parse.model.converters.StringToFilmConverter;
import ua.edu.sumdu.j2ee.pohorila.parse.model.converters.StringToFilmListConverter;
import ua.edu.sumdu.j2ee.pohorila.parse.model.entities.Film;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import static ua.edu.sumdu.j2ee.pohorila.parse.model.SendGetRequest.sendGetRequest;

public interface ServicesInterface {

    static StringToFilmConverter converter = new StringToFilmConverter();
    static StringToFilmListConverter converters = new StringToFilmListConverter();

    public static Film getFilmById(String imdb, @Value("${sbpg.init.APIKEY}") String key, @Value("${sbpg.init.SEARCH_BY_IMDB_URL}") String myURL){
        String requestUrl = myURL.replaceAll("IMDB", imdb).replaceAll("APIKEY", key);
        String request = sendGetRequest(requestUrl);
        Film result = converter.convert(request);
        return result;
    }

    public static List<Film> getFilmByTitle(String title, @Value("${sbpg.init.APIKEY}") String key,  @Value("${sbpg.init.SEARCH_URL}") String myURL) throws UnsupportedEncodingException {
        List<Film> films;
        title = URLEncoder.encode(title, "UTF-8");
        String requestUrl = myURL.replaceAll("TITLE", title).replaceAll("APIKEY", key);
        String request = sendGetRequest(requestUrl);
        films = converters.convert(request);
        return films;
    }

}
