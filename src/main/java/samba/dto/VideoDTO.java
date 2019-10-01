package samba.dto;

/**
 * The video's reference data transfer object definition class.
 * @since 0.1
 */
public class VideoDTO {
    /**
     * The video's duration.
     */
    private double duration;
    
    /**
     * The video's timestamp.
     */
    private long timestamp;

    public VideoDTO(double duration, long timestamp) {
        this.duration = duration;
        this.timestamp = timestamp;
    }

    public double getDuration() {
        return duration;
    }

    public long getTimestamp() {
        return timestamp;
    }
}