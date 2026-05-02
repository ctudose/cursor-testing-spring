package cursor.cursortestingspring.controller;

import cursor.cursortestingspring.dto.CreateEventRequest;
import cursor.cursortestingspring.dto.EventResponse;
import cursor.cursortestingspring.service.EventService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/events")
public class EventController {

    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EventResponse createEvent(@Valid @RequestBody CreateEventRequest request) {
        return null;//eventService.createEvent(request);
    }
}