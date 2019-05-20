package md.bro.gateway.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@Component
public class UserManagementClient<T> {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${ms.ms-user.url}")
    private String msUrl;

    public ResponseEntity<String> exchange (String path, T params, HttpMethod httpMethod) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<T> entity = new HttpEntity<T>(params,headers);

        return restTemplate.exchange(msUrl + path, httpMethod, entity, String.class);
    }
}
