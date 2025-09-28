
package com.example.testlib;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublisher;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;

import static org.assertj.core.api.Assertions.assertThat;

public class TestHelper {

  public static void checkEndpoints(String baseUri) throws Exception {
    check(baseUri + "/echo_json", "application/json", "{}", 200);
    check(baseUri + "/echo_json", "application/json", "hello", 200);
    check(baseUri + "/echo_json", "application/json", "\"quoted\"", 200);

    check(baseUri + "/echo_plain_text", "text/plain", "hello", 200);
    check(baseUri + "/echo_plain_text", "text/plain", "\"quoted\"", 200);
    check(baseUri + "/echo_plain_text", "text/plain", "{}", 200);
  }

  private static void check(String uri, String requestContentType, String requestBody, int expectedStatusCode) throws Exception {
    HttpResponse<String> response = post(uri, requestContentType, requestBody);
    assertThat(response.statusCode()).isEqualTo(expectedStatusCode);
    assertThat(response.body()).isEqualTo(requestBody);
  }

  private static HttpResponse<String> post(String uri, String requestContentType, String requestBody) throws Exception {
    BodyPublisher bodyPublisher = (requestBody == null)
        ? BodyPublishers.noBody()
        : BodyPublishers.ofString(requestBody);
    HttpRequest req = HttpRequest.newBuilder().POST(bodyPublisher)
        .header("Content-Type", requestContentType)
        .uri(URI.create(uri))
        .build();
    try (HttpClient client = HttpClient.newBuilder().build()) {
      HttpResponse<String> response = client.send(req, HttpResponse.BodyHandlers.ofString());
      return response;
    }
  }
}