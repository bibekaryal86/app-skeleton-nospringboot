package nospring.service.skeleton.app.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Base64;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ConnectorUtil {

    private static HttpClient getHttpClient() {
        return HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(5L))
                .build();
    }

    private static URI getUri(String endpoint) {
        return URI.create(endpoint);
    }

    private static HttpRequest.BodyPublisher getPOST(Object object) {
        return HttpRequest.BodyPublishers.ofString(Util.getGson().toJson(object));
    }

    private static HttpRequest getHttpRequestBuilder(String endpoint,
                                                     Object bodyObject,
                                                     boolean isGet,
                                                     String authorization) {
        HttpRequest.Builder httpRequestBuilder = HttpRequest.newBuilder()
                .uri(getUri(endpoint))
                .header("Content-Type", "application/json");
                // add headers as required

        if (Util.hasText(authorization)) {
            httpRequestBuilder = httpRequestBuilder.header("Authorization", authorization);
        }

        if (isGet) {
            httpRequestBuilder = httpRequestBuilder.GET();
        } else {
            httpRequestBuilder = httpRequestBuilder.POST(getPOST(bodyObject));
        }

        return httpRequestBuilder.build();
    }

    private static HttpResponse<String> sendHttpRequest(HttpRequest httpRequest,
                                                        String endpoint,
                                                        Object bodyObject) throws IOException, InterruptedException {
        log.info("Sending Http Request to endpoint [ {} ] with request body [ {} ]",
                endpoint, bodyObject == null ? null : bodyObject.getClass().getName());
        HttpResponse<String> httpResponse = getHttpClient().send(httpRequest, HttpResponse.BodyHandlers.ofString());
        log.info("Received Http Response for endpoint [ {} ] with status [ {} ] and response body [ {} ]",
                endpoint, httpResponse.statusCode(), httpResponse.body().length());

        return httpResponse;
    }

    public static HttpResponse<String> sendHttpRequest(String endpoint,
                                                           Object bodyObject,
                                                           boolean isGet,
                                                           String authorization) {
        try {
            HttpRequest httpRequest = getHttpRequestBuilder(endpoint, bodyObject, isGet, authorization);
            return sendHttpRequest(httpRequest, endpoint, bodyObject);
        } catch (InterruptedException ex) {
            log.error("Error in HttpClient Send: {} | {}", endpoint, bodyObject, ex);
            Thread.currentThread().interrupt();
        } catch (Exception ex) {
            log.error("Error in HttpClient Send: {} | {}", endpoint, bodyObject, ex);
        }

        return null;
    }
}
