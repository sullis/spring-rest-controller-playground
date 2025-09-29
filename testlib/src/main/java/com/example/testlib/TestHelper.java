
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
    check(baseUri + "/consumes_application_json", "application/json", "{}", 200);
    check(baseUri + "/consumes_application_json", "application/json", "hello", 200);
    check(baseUri + "/consumes_application_json", "application/json", "\"quoted\"", 200);
    check(baseUri + "/consumes_application_json", null, "{}", 415);

    check(baseUri + "/consumes_text_plain", "text/plain", "hello", 200);
    check(baseUri + "/consumes_text_plain", "text/plain", "\"quoted\"", 200);
    check(baseUri + "/consumes_text_plain", "text/plain", "{}", 200);
    check(baseUri + "/consumes_text_plain", null, "{}", 415);

    check(baseUri + "/consumes_anything", "text/plain", "hello", 200);
    check(baseUri + "/consumes_anything", "text/plain", "\"quoted\"", 200);
    check(baseUri + "/consumes_anything", "text/plain", "{}", 200);
    check(baseUri + "/consumes_anything", null, "{}", 200);
    check(baseUri + "/consumes_anything", null, "hello", 200);
    check(baseUri + "/consumes_anything", null, "\"quoted\"", 200);
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