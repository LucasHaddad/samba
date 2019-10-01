package samba.manager;

import samba.dto.VideoDTO;
import samba.exception.VideoPersistingException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MemoryVideoManagerTest {
    
    private VideoService service = new VideoService();
    
    @BeforeEach
    void setUp() {
        MemoryVideoManager.add(new VideoDTO(10, 1));
        MemoryVideoManager.add(new VideoDTO(20, 2));
        MemoryVideoManager.add(new VideoDTO(30, 3));
        MemoryVideoManager.add(new VideoDTO(40, 4));
        MemoryVideoManager.add(new VideoDTO(50, 5));
    }    
    
    @AfterEach
    void tearDown() {
        MemoryVideoManager.flush();
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
            service.getStatistics(3L), 
            "The statistics must consider only items within the given timestamp filter."
        );
    }

    @Test
    void save() {
        service.save(new VideoDTO(60, 6), 70);
        assertEquals(60, MemoryVideoManager.getCachedVideos().get(5).getDuration(), "The duration of the 5th item must be 60.");
    }

    @Test
    void createWithException() {
        assertThrows(VideoPersistingException.class);
        service.create(new VideoDTO(60, 6), 80);
    }

    @Test
    void delete() {
        service.delete();
        assertEquals(-1, MemoryVideoManager.getLastIndex(), "The last index must be -1 after flush.");
    }
}