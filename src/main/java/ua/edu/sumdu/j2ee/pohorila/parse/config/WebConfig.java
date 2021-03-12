package ua.edu.sumdu.j2ee.pohorila.parse.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import ua.edu.sumdu.j2ee.pohorila.parse.model.converters.StringToFilmConverter;
import ua.edu.sumdu.j2ee.pohorila.parse.model.converters.StringToFilmListConverter;

@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {
    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        configurer.favorPathExtension(false).
                favorParameter(true).
                defaultContentType(MediaType.APPLICATION_JSON).
                mediaType("xml", MediaType.APPLICATION_XML);
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new StringToFilmConverter());
        registry.addConverter(new StringToFilmListConverter());
    }

}
