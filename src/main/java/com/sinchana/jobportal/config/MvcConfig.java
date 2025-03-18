package com.sinchana.jobportal.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Path;
import java.nio.file.Paths;

/*WebMvcConfigurer - This configuration class will map request for /photos to serve files from a
* directory on our file system*/

@Configuration
public class MvcConfig implements WebMvcConfigurer {
    private static final String UPLOAD_DIR = "photos";

    //overriding the default implementation to set up a custom resource handler
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
       // WebMvcConfigurer.super.addResourceHandlers(registry);
        exposeDirectory(UPLOAD_DIR,registry);
    }

    private void exposeDirectory(String uploadDir, ResourceHandlerRegistry registry) {
        Path path = Paths.get(uploadDir);
        registry.addResourceHandler("/"+ uploadDir+"/**").addResourceLocations("file:" + path.toAbsolutePath() + "/");

    }

}
