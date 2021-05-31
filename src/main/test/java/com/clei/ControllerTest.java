package com.clei;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.URI;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ControllerTest {

    @LocalServerPort
    private int port;

    private URI base;

    @Autowired
    private TestRestTemplate restTemplate;

    @Before
    public void setBase() {
        this.base = URI.create("http://localhost:" + port + "/test/test");
    }

    @Test
    public void test(){
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(base, String.class);
        Assert.assertThat(responseEntity.getBody(), Matchers.equalTo("test success!"));
    }
}
