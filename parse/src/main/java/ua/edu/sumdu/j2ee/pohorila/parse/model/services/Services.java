package ua.edu.sumdu.j2ee.pohorila.parse.model.services;

import ua.edu.sumdu.j2ee.pohorila.parse.model.entities.Film;

import java.io.*;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import static ua.edu.sumdu.j2ee.pohorila.parse.controller.Controller.*;

public class Services {
    public static final String SEARCH_BY_IMDB_URL = "http://www.omdbapi.com/?i=IMDB&apikey=APIKEY";
    public static final String SEARCH_URL = "http://www.omdbapi.com/?s=TITLE&apikey=APIKEY";

    public Film getFilmById(String id){
        Film film = new Film();
        return film;
    }

    public Film getFilmByTitle(String title){
        Film film = new Film();
        return film;
    }

    public static String searchMovieByTitle (String title, String key) throws UnsupportedEncodingException {
        title = URLEncoder.encode(title, "UTF-8");
        String requestUrl = SEARCH_URL.replaceAll("TITLE", title).replaceAll("APIKEY", key);
        return sendGetRequest(requestUrl);
    }


    public static String searchMovieByImdb (String imdb, String key) {
        String requestUrl = SEARCH_BY_IMDB_URL.replaceAll("IMDB", imdb).replaceAll("APIKEY", key);
        return sendGetRequest(requestUrl);
    }

   /* @CrossOrigin
    @RequestMapping(value = "/fileWrite", method = RequestMethod.GET)
    public @ResponseBody
    static void fileWrite(Film film) throws Exception {
        File file = new File("src/main/resources/templates/results.docx");
        StringBuilder sb = new StringBuilder();
        try {
            BufferedReader in = new BufferedReader(new FileReader( file.getAbsoluteFile()));
            PrintWriter out = new PrintWriter(file.getAbsoluteFile());
            try {
                String s;
                while ((s = in.readLine()) != null) {
                    sb.append(s);
                    sb.append("\n");
                }
                String data = sb.toString();
                data = data.replace("title", film.getTitle());
                data = data.replace("directory", film.getDirectory());
                data = data.replace("$year$", film.getYear());
                data = data.replace("$duration$", film.getDuration());
                data = data.replace("$genre$", film.getGenre());
                data = data.replace("$description$", film.getDescription());
                data = data.replace("$posterLink$", film.getPosterLink());
                out.print(data);
            } finally {
                in.close();
                out.close();
            }
        } catch(IOException e) {
            throw new RuntimeException(e);
        }

    }*/

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static void changeData(File targetFile, Film data) throws IOException{
        Map<String, String> film = new TreeMap<String, String>(){{
            put("$title$", data.getTitle());
            put("directory", data.getDirectory());
            put("$year$", data.getYear());
            put("$duration$", data.getDuration());
            put("$genre$", data.getGenre());
            put("$description$", data.getDescription());
            put("$posterLink$", data.getPosterLink());
            }};
        BufferedReader br = null;
        String docxTemplate = "";
        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(targetFile), "UTF-8"));
            String temp;
            while( (temp = br.readLine()) != null)
                docxTemplate = docxTemplate + temp;
            br.close();
            targetFile.delete();
        }
        catch (IOException e) {
            br.close();
            throw e;
        }

        Iterator substitutionDataIterator = film.entrySet().iterator();
        while(substitutionDataIterator.hasNext()){
            Map.Entry<String,String> pair = (Map.Entry<String,String>)substitutionDataIterator.next();
            if(docxTemplate.contains(pair.getKey())){
                if(pair.getValue() != null)
                    docxTemplate = docxTemplate.replace(pair.getKey(), pair.getValue());
            }
        }

        FileOutputStream fos = null;
        try{
            fos = new FileOutputStream(targetFile);
            fos.write(docxTemplate.getBytes("UTF-8"));
            fos.close();
        }
        catch (IOException e) {
            fos.close();
            throw e;
        }
    }
}
