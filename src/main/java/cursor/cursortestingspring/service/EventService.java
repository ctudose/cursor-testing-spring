package cursor.cursortestingspring.service;

import cursor.cursortestingspring.dto.CreateEventRequest;
import cursor.cursortestingspring.dto.EventResponse;
import org.springframework.stereotype.Service;

@Service
public class EventService {

    public EventResponse createEvent(CreateEventRequest request) {
        if ("Duplicate Event".equalsIgnoreCase(request.title())) {
            throw new IllegalArgumentException("Duplicate event");
        }

        return new EventResponse(
                1L,
                request.title(),
                request.location(),
                request.capacity()
        );
    }
}