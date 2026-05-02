package cursor.cursortestingspring.dto;

import java.util.List;

public record ErrorResponse(
        int status,
        List<String> errors
) {
}