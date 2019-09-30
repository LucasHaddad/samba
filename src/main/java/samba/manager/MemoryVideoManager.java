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
     * Retrieves a Map containing video duration statistics.
     * @param double The sum of durations.
     * @param double The average of durations.
     * @param double The maximum duration.
     * @param double The minimum duration.
     * @param int The number of videos uploaded within the last 60 seconds.
     * @return A Map containing all statistics.
     */
    private static Map<String, Object> buildStatistics(double sum, double avg, double max, double min, int count) {
        return Map.of(
            "sum", sum,
            "avg", avg,
            "max", max,
            "min", min,
            "count", count
        );
    }

    /**
     * Caches the given item at the end of the cached list.
     * @param VideoDTO The item to cache.
     */
    public static void add(VideoDTO item) {
        cached.add(item);
        lastIndex++;
    }

    /**
     * Retrieves a Map containing subList of VideoDTOs filtered by the timestamp.
     * @param long The timestamp to filter the subList.
     * @return A Map containing the statistics.
     */
    public static Map<String, Double> getStatistics(long timestamp) {
        double sum = 0, max = 0, min = 0;
        int count = 0;

        for (int i = lastIndex; i > -1; i--) {
            VideoDTO item = cached.get(i); 
            long itemTimestamp = item.getTimestamp();
            double itemDuration = item.getDuration();
            if (itemTimestamp < timestamp) {
                return buildStatistics(sum, sum/count, max, min, count);
            } else {
                sum += itemDuration;
                max = itemDuration >= max ? itemDuration : max;
                min = itemDuration <= min ? itemDuration : min;
                count++;
            }
        }
    }

    /**
     * Flushes all the cached data.
     */
    public static void flush() {
        cached = new ArrayList<>();
        lastIndex = -1;
    }
}