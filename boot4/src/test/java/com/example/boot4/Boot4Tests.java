package com.example.boot4;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import com.example.testlib.TestHelper;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class Boot4Tests {
  @LocalServerPort int localServerPort;

	@Test
	void checkEndpoints() throws Exception {
    TestHelper.checkEndpoints("http://localhost:" + localServerPort);
	}

}
