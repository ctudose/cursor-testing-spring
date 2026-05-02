package cursor.cursortestingspring.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record CreateEventRequest(
        @NotBlank(message = "title is required")
        String title,

        @NotBlank(message = "location is required")
        String location,

        @Min(value = 1, message = "capacity must be at least 1")
        int capacity
) {
}