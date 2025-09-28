
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

    check(baseUri + "/echo_json", null, "{}", 415);

    check(baseUri + "/echo_plain_text", "text/plain", "hello", 200);
    check(baseUri + "/echo_plain_text", "text/plain", "\"quoted\"", 200);
    check(baseUri + "/echo_plain_text", "text/plain", "{}", 200);
  }

  private static void check(String uri, String requestContentType, String requestBody, int expectedStatusCode) throws Exception {
    HttpResponse<String> response = post(uri, requestContentType, requestBody);
    assertThat(response.statusCode()).isEqualTo(expectedStatusCode);
    if (expectedStatusCode == 200) {
      assertThat(response.body()).isEqualTo(requestBody);
    }
  }

  private static HttpResponse<String> post(final String uri, final String requestContentType, final String requestBody) throws Exception {
    BodyPublisher bodyPublisher = (requestBody == null)
        ? BodyPublishers.noBody()
        : BodyPublishers.ofString(requestBody);
    HttpRequest.Builder reqBuilder = HttpRequest.newBuilder()
        .POST(bodyPublisher)
        .uri(URI.create(uri));
    if (requestContentType != null) {
      reqBuilder.header("Content-Type", requestContentType);
    }
    HttpRequest req = reqBuilder.build();

    try (HttpClient client = HttpClient.newBuilder().build()) {
      return client.send(req, HttpResponse.BodyHandlers.ofString());
    }
  }
}