package samba.manager;

import samba.dto.VideoDTO;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MemoryVideoManagerTest {
    @BeforeEach
    void setUp() {
        Test.add(new VideoDTO(10, 1));
        Test.add(new VideoDTO(20, 2));
        Test.add(new VideoDTO(30, 3));
        Test.add(new VideoDTO(40, 4));
        Test.add(new VideoDTO(50, 5));
    }    
    
    @AfterEach
    void tearDown() {
        Test.flush();
    }

    @Test
    void add() {
        Test.add(new VideoDTO(60, 6));
        assertEquals(60, Test.getCachedVideos().get(5).getDuration(), "The duration of the 5th item must be 60.");
    }

    @Test
    void getStatistics() {
        assertEquals(
            Map.of(
                "sum", 60d,
                "avg", 20d,
                "max", 30d,
                "min", 10d,
                "count", 3L
            ), 
            Test.getStatistics(3L), 
            "The statistics must consider only items within the given timestamp filter."
        );
    }

    @Test
    void getCachedVideos() {
        assertEquals(5, Test.getCachedVideos().size(), "There must be a total of 5 videos stored.");
    }

    @Test
    void getLastIndex() {
        assertEquals(4, Test.getLastIndex(), "The last index must be 4.");
    }

    @Test
    void flush() {
        Test.flush();
        assertEquals(-1, Test.getLastIndex(), "The last index must be -1 after flush.");
    }
}