package samba.manager;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;

import samba.dto.VideoDTO;

/**
 * In memory cache stateful service class.
 */
public class MemoryVideoManager {
    /**
     * The list of cached videos.
     */
    private static List<VideoDTO> cached = new ArrayList<>();

    /**
     * The list size;
     */
    private static int lastIndex = -1;

    /**
     * Fetches all cached video items.
     * @return The list of cached videos.
     */
    public static List<VideoDTO> getCachedVideos() {
        return cached;
    }

    /**
     * Retrieves the last cached video index.
     * @return The last index integer representation.
     */
    public static int getLastIndex() {
        return lastIndex;
    }


    /**
     * Caches the given item at the end of the cached list.
     * @param item The item to cache.
     */
    public static void add(VideoDTO item) {
        cached.add(item);
        lastIndex++;
    }

    /**
     * Flushes all the cached data.
     */
    public static void flush() {
        cached = new ArrayList<>();
        lastIndex = -1;
    }
}