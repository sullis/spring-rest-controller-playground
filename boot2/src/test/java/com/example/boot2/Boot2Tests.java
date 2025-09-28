package com.example.boot2;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import com.example.testlib.TestHelper;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class Boot2Tests {
  @LocalServerPort int localServerPort;

  @Test
  void checkEndpoints() throws Exception {
    TestHelper.checkEndpoints("http://localhost:" + localServerPort);
  }

}
