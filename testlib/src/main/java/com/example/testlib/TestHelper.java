
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
    check(baseUri + "/echo_plain_text", "text/plain", "");
    check(baseUri + "/echo_plain_text", "text/plain", "hello");
    check(baseUri + "/echo_plain_text", "text/plain", "\"quoted\"");
  }

  private static void check(String uri, String contentType, String requestBody) throws Exception {
    assertThat(post(uri, contentType, requestBody).body())
        .isEqualTo("{}");
  }

  private static HttpResponse<String> post(String uri, String contentType, String requestBody) throws Exception {
    BodyPublisher bodyPublisher = (requestBody == null)
        ? BodyPublishers.noBody()
        : BodyPublishers.ofString(requestBody);
    HttpRequest req = HttpRequest.newBuilder().POST(bodyPublisher)
        .header("Content-Type", contentType)
        .uri(URI.create(uri))
        .build();
    HttpClient client = HttpClient.newBuilder().build();
    HttpResponse<String> response = client.send(req, HttpResponse.BodyHandlers.ofString());
    return response;
  }
}