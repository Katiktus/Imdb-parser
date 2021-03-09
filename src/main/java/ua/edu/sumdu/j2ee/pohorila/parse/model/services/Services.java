package ua.edu.sumdu.j2ee.pohorila.parse.model.services;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import ua.edu.sumdu.j2ee.pohorila.parse.model.entities.Film;
import java.io.*;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import static ua.edu.sumdu.j2ee.pohorila.parse.model.SendGetRequest.sendGetRequest;


@Service
public class Services implements  ServicesInterface{
    @Autowired
    private ConversionService conversionService;

    public Services() {
    }

    public Film getFilmById(String imdb, @Value("${sbpg.init.APIKEY}") String key, @Value("${sbpg.init.SEARCH_BY_IMDB_URL}") String myURL){
        String requestUrl = myURL.replaceAll("IMDB", imdb).replaceAll("APIKEY", key);
        String request = sendGetRequest(requestUrl);
        Film result = conversionService.convert(request, Film.class);
        return result;
    }

    public List<Film> getFilmByTitle(String title, @Value("${sbpg.init.APIKEY}") String key, @Value("${sbpg.init.SEARCH_URL}") String myURL) throws UnsupportedEncodingException {
        List<Film> films = new ArrayList<>();
        title = URLEncoder.encode(title, "UTF-8");
        String requestUrl = myURL.replaceAll("TITLE", title).replaceAll("APIKEY", key);
        String request = sendGetRequest(requestUrl);
        films = conversionService.convert(request, films.getClass());
        return films;
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

    //File targetFile
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public void writeData(Film data) throws IOException{
        String output = "result.docx";
        File file = new File("src//main//resources//templates//results.docx");
        FileInputStream fis = new FileInputStream(file.getAbsolutePath());
        try (XWPFDocument document = new XWPFDocument(fis)) {
            String documentLine = document.getDocument().toString();
            XWPFParagraph title = document.createParagraph();
            XWPFRun titleRun = title.createRun();
            Film finalData = data;

            Map<String, String> film = new TreeMap<String, String>() {{
                put("$title$", finalData.getTitle());
                put("directory", finalData.getDirectory());
                put("$year$", finalData.getYear());
                put("$duration$", finalData.getDuration());
                put("$genre$", finalData.getGenre());
                put("$description$", finalData.getDescription());
                put("$posterLink$", finalData.getPosterLink());
            }};

            documentLine = documentLine.replace("$title$", finalData.getTitle());
            documentLine = documentLine.replace("$directory$", finalData.getDirectory());
            documentLine = documentLine.replace("$year$", finalData.getYear());
            documentLine = documentLine.replace("$duration$", finalData.getDuration());
            documentLine = documentLine.replace("$genre$", finalData.getGenre());
            documentLine = documentLine.replace("$description$", finalData.getDescription());
            documentLine = documentLine.replace("$posterLink$", finalData.getPosterLink());

            titleRun.setText(documentLine);

            FileOutputStream out = new FileOutputStream(output);
            document.write(out);
            out.close();
        }

    }
}

