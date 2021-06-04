package com.clei;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;

import java.net.URI;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ControllerTest {

    @LocalServerPort
    private int port;

    private URI base;

    @Autowired
    private TestRestTemplate restTemplate;

    @BeforeAll
    public void setBase() {
        this.base = URI.create("http://localhost:" + port + "/test/test");
    }

    @Test
    public void test(){
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(base, String.class);
        Assert.hasText(responseEntity.getBody(), "test success!");
    }
}
