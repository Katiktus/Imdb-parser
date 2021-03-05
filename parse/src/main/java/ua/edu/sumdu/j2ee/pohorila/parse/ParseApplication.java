package ua.edu.sumdu.j2ee.pohorila.parse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import ua.edu.sumdu.j2ee.pohorila.parse.model.entities.Film;
import ua.edu.sumdu.j2ee.pohorila.parse.model.services.Services;
import ua.edu.sumdu.j2ee.pohorila.parse.model.converters.StringToFilmConverter;
import ua.edu.sumdu.j2ee.pohorila.parse.model.converters.StringToFilmListConverter;

import java.io.File;
import java.util.List;

@SpringBootApplication
public class ParseApplication {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(ParseApplication.class, args);
        StringToFilmConverter converter1 = new StringToFilmConverter();
        StringToFilmListConverter converter2 = new StringToFilmListConverter();
        String jsonResponse = Services
                .searchMovieByTitle("batman", "6b935860");
        System.out.println(jsonResponse);
        List<Film> results = converter2.convert(jsonResponse);
        System.out.println(results.toString());
        jsonResponse = Services.searchMovieByImdb("tt0372784", "6b935860");
        System.out.println(jsonResponse);
        Film result = converter1.convert(jsonResponse);
        System.out.println(result.toString());
        Services.changeData(new File("src/main/resources/templates/results.docx"), result);
    }
}
