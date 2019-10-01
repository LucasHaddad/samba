package samba.service;

import java.util.List;
import java.util.Map;

import samba.manager.MemoryVideoManager;
import samba.dto.VideoDTO;
import samba.exception.VideoInsertingException;

/**
 * Defines the video service.
 */
class VideoService {
    /**
     * Stores the given video as a register in the cache.
     * @param item The item to be stored.
     * @param timestamp The timestamp to compare.
     * @throws VideoInsertingException Thrown when the timestamp is lower than the last 60 seconds.
     */
    public void save(VideoDTO item, long timestamp) throws VideoInsertingException {
        if (item.getTimestamp() < timestamp) throw new VideoInsertingException(); 
        MemoryVideoManager.add(item);
    }

    /**
     * Frees the cache.
     */
    public void delete() {
        MemoryVideoManager.flush();
    }

    /**
     * Retrieves a Map containing video duration statistics.
     * @param sum The sum of durations.
     * @param avg The average of durations.
     * @param max The maximum duration.
     * @param min The minimum duration.
     * @param count The number of videos uploaded within the last 60 seconds.
     * @return A Map containing all statistics.
     */
    private Map<String, Object> buildStatistics(double sum, double avg, double max, double min, int count) {
        return Map.of(
            "sum", sum,
            "avg", avg,
            "max", max,
            "min", min,
            "count", count
        );
    }

    /**
     * Retrieves a Map containing subList of VideoDTOs filtered by the timestamp.
     * @param timestamp The timestamp to filter the subList.
     * @return A Map containing the statistics.
     */
    public Map<String, Double> getStatistics(long timestamp) {
        double sum = 0, max = 0, min = 0;
        int count = 0;

        List<VideoDTO> cached = MemoryVideoManager.getCachedVideos();

        // Iterates over all cached items comparing timestamps and generating the reports.
        for (int i = MemoryVideoManager.getLastIndex(); i > -1; i--) {
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
}