package md.bro.gateway;

import md.bro.gateway.exception.handler.RestTemplateResponseErrorHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class GatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatewayApplication.class, args);
	}

	@Bean
	@Autowired
	public RestTemplate getRestTemplate(RestTemplateBuilder restTemplateBuilder) {
		RestTemplate restTemplate = restTemplateBuilder
				.errorHandler(new RestTemplateResponseErrorHandler())
				.build();

		return restTemplate;
	}
}
