package com.citi.alan.myproject;

import java.io.InputStream;
import java.util.Properties;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;

@PropertySource({
    "classpath:properties/env-${spring.profiles.platform}.properties"
})
@SpringBootApplication
public class WebApplication extends SpringBootServletInitializer{
    
    static {
        nu.pattern.OpenCV.loadShared();
        System.loadLibrary(org.opencv.core.Core.NATIVE_LIBRARY_NAME);
      }
    
    public static void main(String[] args) throws Exception {
    	    Properties properties = new Properties();  
        InputStream in = WebApplication.class.getClassLoader().getResourceAsStream("app.properties");  
        properties.load(in);  
        SpringApplication app = new SpringApplication(WebApplication.class);  
        app.setDefaultProperties(properties);  
        app.run(args);  
    }
    
    @Override  
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {   
        builder.sources(this.getClass());  
        return super.configure(builder);  
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