package ru.beetlewar.strategy.infrastructure.http;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.BufferedReader;
import java.io.InputStreamReader;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class HttpTests {
    //    @Autowired
//    private MatchController controller;
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void some_test() throws Exception {
        String some = this.restTemplate.getForObject("/api/some", String.class);

        this.restTemplate.execute(
                "/api/events",
                HttpMethod.GET,
                request -> {
                },
                response -> {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(response.getBody()));

                    String line;
                    do {
                        line = reader.readLine();
                    }
                    while (line != null);

                    return response;
                });

        int i = 3;
    }
}
