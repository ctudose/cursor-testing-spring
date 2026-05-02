package cursor.cursortestingspring.dto;

public record EventResponse(
        Long id,
        String title,
        String location,
        int capacity
) {
}