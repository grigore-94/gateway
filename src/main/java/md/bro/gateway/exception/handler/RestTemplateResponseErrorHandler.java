package md.bro.gateway.exception.handler;

import com.google.common.io.ByteStreams;
import md.bro.gateway.exception.BadRequestException;
import md.bro.gateway.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;
import java.nio.charset.Charset;

@Component
public class RestTemplateResponseErrorHandler implements ResponseErrorHandler {

    @Override
    public boolean hasError(ClientHttpResponse httpResponse) throws IOException {

        return (
                httpResponse.getStatusCode().series() == HttpStatus.Series.CLIENT_ERROR
                        || httpResponse.getStatusCode().series() == HttpStatus.Series.SERVER_ERROR);
    }

    @Override
    public void handleError(ClientHttpResponse httpResponse) throws IOException {
        if (httpResponse.getStatusCode()
                .series() == HttpStatus.Series.SERVER_ERROR) {
        } else if (httpResponse.getStatusCode()
                .series() == HttpStatus.Series.CLIENT_ERROR) {
            if (httpResponse.getStatusCode() == HttpStatus.NOT_FOUND) {
                throw new ResourceNotFoundException("Resource not found");
            }
            if (httpResponse.getStatusCode() == HttpStatus.BAD_REQUEST) {
                String responseString = new String(
                        ByteStreams.toByteArray(httpResponse.getBody()),
                        Charset.forName("UTF-8")
                );

                throw new BadRequestException(responseString);
            }
        }
    }
}
