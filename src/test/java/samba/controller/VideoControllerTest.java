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
        restTemplate.delete("http://localhost:" + port + "/videos");
    }

    @Test
    void postVideos() {
        long now = new System.currentTimeMillis();
        Map<String, Object> body = Map.of(
            "duration", 200.0,
            "timestamp", (now - 30),
        );
        Map<String, Object> response = restTemplate.postForObject("http://localhost:" + port + "/videos", body, Map.class);
        assertEquals(200, response.get("status"));
    }

    @Test
    void postVideosError() {
        long now = new System.currentTimeMillis();
        Map<String, Object> body = Map.of(
            "duration", 200.0,
            "timestamp", (now - 70),
        );
        Map<String, Object> response = restTemplate.postForObject("http://localhost:" + port + "/videos", Map.class);
        assertEquals(500, response.get("status"));
    }

    @Test
    void deleteVideos() {
        Map<String, Object> response = restTemplate.deleteForObject("http://localhost:" + port + "/videos", Map.class);
        assertEquals(200, response.get("status"));
    }

    @Test
    void getStatistics() {
        Map<String, Object> response = restTemplate.getForObject("http://localhost:" + port + "/statistics", Map.class);
        Map<String, Object> expected = Map.of(
            "sum", 100.0,
            "avg", 100.0,
            "max", 100.0,
            "min", 100.0,
            "count", 1
        );
        assertEquals(200, response.get("status"));
        assertEquals(expected, response.get("body"));
    }
}