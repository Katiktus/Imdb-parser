package ua.edu.sumdu.j2ee.pohorila.parse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class ParseApplication {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(ParseApplication.class, args);
        /*StringToFilmConverter converter1 = new StringToFilmConverter();
        StringToFilmListConverter converter2 = new StringToFilmListConverter();
        Film jsonResponse = ServicesInterface.getFilmById("tt0372784", "6b935860", "http://www.omdbapi.com/?i=IMDB&apikey=APIKEY");
        System.out.println(jsonResponse.toString());

        Services.writeData(jsonResponse);*/
    }
}
