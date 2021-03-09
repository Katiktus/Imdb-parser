package ua.edu.sumdu.j2ee.pohorila.parse.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.edu.sumdu.j2ee.pohorila.parse.model.entities.Film;
import ua.edu.sumdu.j2ee.pohorila.parse.model.services.Services;
import ua.edu.sumdu.j2ee.pohorila.parse.model.services.ServicesInterface;

@RestController
public class Controller {
    private ServicesInterface filmsService;

    public Controller(Services filmsService) {
        this.filmsService = filmsService;
    }

    @RequestMapping(value = "/films", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> testMethod(@RequestParam(value = "id", defaultValue = "tt0372784") String id,
                                        @RequestParam(value = "apikey", defaultValue = "6b935860") String apikey,
                                        @Value("${sbpg.init.SEARCH_BY_IMDB_URL}") String myurl){
        System.out.println("Test method is here: " + id);
        ServicesInterface services = new Services();
        Film filmById = services.getFilmById(id, apikey, myurl);
        return ResponseEntity.ok("Everything is ok");
    }

}

