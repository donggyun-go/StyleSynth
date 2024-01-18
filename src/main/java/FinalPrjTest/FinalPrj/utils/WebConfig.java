package FinalPrjTest.FinalPrj.utils;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:///img/designers/")
                .addResourceLocations("file:///img/designersportfolio/")
                .addResourceLocations("file:///img/photos/")
                .addResourceLocations("file:///img/AiResult/")
                .addResourceLocations("file:///img/request/");
        registry.addResourceHandler("/upload/**")
                .addResourceLocations("file:///img/designers/")
                .addResourceLocations("file:///img/designersportfolio/");
    }

}
