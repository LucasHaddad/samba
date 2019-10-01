package samba.manager;

import samba.dto.VideoDTO;
import samba.App;

import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = App.class)
@ExtendWith(SpringExtension.class)
class VideoControllerTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        restTemplate.postForObject(
            "http://localhost:" + port + "/videos", 
            Map.of(
                "duration", 100.0,
                "timestamp", new System.currentTimeMillis()
            ), 
            Map.class
        );
    }

    @AfterEach
    void tearDown() {
        restTemplate.deleteForObject("http://localhost:" + port + "/videos");
    }

    @Test
    void postVideos() {
        Map response = restTemplate.postForObject("http://localhost:" + port + "/videos", Map.class);
        assertEquals(200, response.get("status"));
        assertEquals(Map.of(), response.get("body"));
    }

    @Test
    void postVideosError() {
        Map response = restTemplate.postForObject("http://localhost:" + port + "/videos", Map.class);
        assertEquals(500, response.get("status"));
        assertEquals(Map.of(), response.get("body"));
    }

    @Test
    void deleteVideos() {
        Map response = restTemplate.deleteForObject("http://localhost:" + port + "/videos", Map.class);
        assertEquals(200, response.get("status"));
        assertEquals(Map.of(), response.get("body"));
    }

    @Test
    void statistics() {
        Map response = restTemplate.getForObject("http://localhost:" + port + "/statistics", Map.class);
        assertEquals(200, response.get("status"));
        assertEquals(Map.of(), response.get("body"));
    }
}