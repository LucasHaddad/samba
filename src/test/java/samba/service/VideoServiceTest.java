package samba.service;

import samba.dto.VideoDTO;
import samba.manager.MemoryVideoManager;
import samba.exception.VideoPersistingException;

import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class VideoServiceTest {
    
    private VideoService service = new VideoService();
    
    @BeforeEach
    void setUp() {
        MemoryVideoManager.add(new VideoDTO(10.0, 1L));
        MemoryVideoManager.add(new VideoDTO(20.0, 2L));
        MemoryVideoManager.add(new VideoDTO(30.0, 3L));
        MemoryVideoManager.add(new VideoDTO(40.0, 4L));
        MemoryVideoManager.add(new VideoDTO(50.0, 5L));
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
    void save() throws VideoPersistingException {
        service.save(new VideoDTO(60.0, 6L), 70L);
        assertEquals(60, MemoryVideoManager.getCachedVideos().get(5).getDuration(), "The duration of the 5th item must be 60.");
    }

    @Test
    void saveWithException() {
        assertThrows(VideoPersistingException.class, () -> { 
            service.save(new VideoDTO(60.0, 6L), 80L);
        });
    }

    @Test
    void delete() {
        service.delete();
        assertEquals(-1, MemoryVideoManager.getLastIndex(), "The last index must be -1 after flush.");
    }
}