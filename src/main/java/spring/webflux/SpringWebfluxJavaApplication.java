package spring.webflux;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@ConfigurationPropertiesScan
@SpringBootApplication
public class SpringWebfluxJavaApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringWebfluxJavaApplication.class, args);
    }

}
