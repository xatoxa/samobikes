package com.xatoxa.samobikes.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class MvcConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String dirBikeName = "photos/bike-photos";
        Path bikePhotosDir = Paths.get(dirBikeName);

        String dirPartName = "photos/part-photos";
        Path partPhotosDir = Paths.get(dirPartName);

        String bikePhotosPath = bikePhotosDir.toFile().getAbsolutePath();
        String partPhotosPath = partPhotosDir.toFile().getAbsolutePath();

        registry.addResourceHandler("/" + dirBikeName + "/**")
                .addResourceLocations("file:/" + bikePhotosPath + "/");
        registry.addResourceHandler("/" + dirPartName + "/**")
                .addResourceLocations("file:/" + partPhotosPath + "/");
    }
}
