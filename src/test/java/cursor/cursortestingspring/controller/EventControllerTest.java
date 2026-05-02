package cursor.cursortestingspring.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import cursor.cursortestingspring.dto.CreateEventRequest;
import cursor.cursortestingspring.dto.EventResponse;
import cursor.cursortestingspring.exception.GlobalExceptionHandler;
import cursor.cursortestingspring.service.EventService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EventController.class)
@Import(GlobalExceptionHandler.class)
class EventControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private EventService eventService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void createEvent_whenRequestIsValid_returnsCreatedEvent() throws Exception {
        CreateEventRequest request =
                new CreateEventRequest("Java Meetup", "Bucharest", 50);

        EventResponse response =
                new EventResponse(1L, "Java Meetup", "Bucharest", 50);

        when(eventService.createEvent(any(CreateEventRequest.class)))
                .thenReturn(response);

        mockMvc.perform(post("/api/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpectAll(
                        status().isCreated(),
                        content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON),
                        jsonPath("$.id").value(1),
                        jsonPath("$.title").value("Java Meetup"),
                        jsonPath("$.location").value("Bucharest"),
                        jsonPath("$.capacity").value(50)
                );
    }

//     @Test
//     void createEvent_whenTitleIsBlank_returnsBadRequestWithErrorResponse() throws Exception {
//         CreateEventRequest request =
//                 new CreateEventRequest("", "Bucharest", 50);

//         mockMvc.perform(post("/api/events")
//                         .contentType(MediaType.APPLICATION_JSON)
//                         .content(objectMapper.writeValueAsString(request)))
//                 .andExpectAll(
//                         status().isBadRequest(),
//                         jsonPath("$.status").value(400),
//                         jsonPath("$.errors").isArray(),
//                         jsonPath("$.errors[0]").value("title is required")
//                 );

//         verifyNoInteractions(eventService);
//     }

//     @Test
//     void createEvent_whenCapacityIsZero_returnsBadRequestWithErrorResponse() throws Exception {
//         CreateEventRequest request =
//                 new CreateEventRequest("Java Meetup", "Bucharest", 0);

//         mockMvc.perform(post("/api/events")
//                         .contentType(MediaType.APPLICATION_JSON)
//                         .content(objectMapper.writeValueAsString(request)))
//                 .andExpectAll(
//                         status().isBadRequest(),
//                         jsonPath("$.status").value(400),
//                         jsonPath("$.errors").isArray(),
//                         jsonPath("$.errors[0]").value("capacity must be at least 1")
//                 );

//         verifyNoInteractions(eventService);
//     }

    @Test
    void createEvent_whenServiceRejectsDuplicateEvent_returnsBadRequestWithErrorResponse() throws Exception {
        CreateEventRequest request =
                new CreateEventRequest("Duplicate Event", "Bucharest", 50);

        when(eventService.createEvent(any(CreateEventRequest.class)))
                .thenThrow(new IllegalArgumentException("Duplicate event"));

        mockMvc.perform(post("/api/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpectAll(
                        status().isBadRequest(),
                        jsonPath("$.status").value(400),
                        jsonPath("$.errors").isArray(),
                        jsonPath("$.errors[0]").value("Duplicate event")
                );
    }
}