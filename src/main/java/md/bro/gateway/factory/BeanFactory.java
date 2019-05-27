package md.bro.gateway.factory;

import md.bro.gateway.exception.handler.RestTemplateResponseErrorHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class BeanFactory {
    @Bean
    @Autowired
    public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {
        RestTemplate restTemplate = restTemplateBuilder
                .errorHandler(new RestTemplateResponseErrorHandler())
                .build();

        return restTemplate;
    }
}
