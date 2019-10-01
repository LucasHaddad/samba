package samba.manager;

import samba.dto.VideoDTO;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MemoryVideoManagerTest {
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
    void add() {
        MemoryVideoManager.add(new VideoDTO(60.0, 6L));
        assertEquals(60, MemoryVideoManager.getCachedVideos().get(5).getDuration(), "The duration of the 5th item must be 60.");
    }

    @Test
    void getCachedVideos() {
        assertEquals(5, MemoryVideoManager.getCachedVideos().size(), "There must be a total of 5 videos stored.");
    }

    @Test
    void getLastIndex() {
        assertEquals(4, MemoryVideoManager.getLastIndex(), "The last index must be 4.");
    }

    @Test
    void flush() {
        MemoryVideoManager.flush();
        assertEquals(-1, MemoryVideoManager.getLastIndex(), "The last index must be -1 after flush.");
    }
}