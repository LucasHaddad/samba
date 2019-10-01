package samba.controller;

import samba.service.VideoService;
import samba.dto.VideoDTO;
import samba.exception.VideoPersistingException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.ResponseStatus;

import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
/**
 * Defines the video controller.
 */
@RestController
public class VideoController {
    /**
     * The Video Service instance.
     */
    private VideoService service = new VideoService();

    /**
     * Retrieves the millis correspondent of the last 60 seconds as long.
     * @return A long correspondent of the current time minus 60 seconds.
     */
    private long getLastMinuteInMillis() {
        return System.currentTimeMillis() - 60;
    }

    /**
     * Deletes all stored videos.
     * @return The http response.
     */
    @DeleteMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity delete() {
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /**
     * Persists a video to the database.
     * @param video The VideoDTO reference object.
     * @return The http response.
     */
    @PostMapping(value = "/videos", consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponses({
            @ApiResponse(code = 201, message = "Created!"),
            @ApiResponse(code = 204, message = "Too old!"),
    })
    public ResponseEntity post(@RequestBody VideoDTO video) {
        try {
            service.save(video, getLastMinuteInMillis());
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (VideoPersistingException exception) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, exception.getMessage(), exception);
        }
    } 

    /**
     * Retrieves the last 60 seconds videos persisted statistics.
     * @return The http response.
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity get() {
        return new ResponseEntity<>(service.getStatistics(getLastMinuteInMillis()), HttpStatus.OK);
    }
}