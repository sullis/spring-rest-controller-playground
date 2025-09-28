
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
    check(baseUri + "/echo_json", "application/json", "{}");
    check(baseUri + "/echo_json", "application/json", "hello");
    check(baseUri + "/echo_json", "application/json", "\"quoted\"");

    check(baseUri + "/echo_plain_text", "text/plain", "hello");
    check(baseUri + "/echo_plain_text", "text/plain", "\"quoted\"");
    check(baseUri + "/echo_plain_text", "text/plain", "{}");
  }

  private static void check(String uri, String requestContentType, String requestBody) throws Exception {
    HttpResponse<String> response = post(uri, requestContentType, requestBody);
    assertThat(response.statusCode()).isEqualTo(200);
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