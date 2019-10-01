package samba.exception;

/**
 * Defines the video persisting exception.
 */
public class VideoPersistingException extends Exception {
    public VideoException() {
        super("Couldn't persist the video due to a timestamp lower than the last 60 seconds!", 500);
    }
}