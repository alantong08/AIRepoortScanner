package com.citi.alan.myproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;

@PropertySource({
    "classpath:properties/env-${spring.profiles.platform}.properties"
})
@SpringBootApplication
public class App {
    
    
    static {
        nu.pattern.OpenCV.loadShared();
        System.loadLibrary(org.opencv.core.Core.NATIVE_LIBRARY_NAME);
      }
    
    public static void main(String[] args) throws Exception {

        SpringApplication.run(App.class, args);  
    }
    
    @Bean
    public EmbeddedServletContainerCustomizer containerCustomizer(){
           return new EmbeddedServletContainerCustomizer() {
               @Override
               public void customize(ConfigurableEmbeddedServletContainer container) {
                    container.setSessionTimeout(5*60*1000);
              }
        };
    }
}