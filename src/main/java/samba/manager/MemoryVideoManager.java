package samba.cache;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;

import samba.model.VideoDTO;

/**
 * In memory cache stateful service class.
 */
public class Memory {
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
     * @param VideoDTO The item to cache.
     */
    public static void add(VideoDTO item) {
        cached.add(item);
        lastIndex++;
    }

    public static List<VideoDTO> getAll() {
        return cached;
    }

    /**
     * Flushes all the cached data.
     */
    public static void flush() {
        cached = new ArrayList<>();
        lastIndex = -1;
    }
}