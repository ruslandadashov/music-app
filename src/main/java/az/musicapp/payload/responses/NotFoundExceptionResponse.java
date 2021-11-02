package az.musicapp.payload.responses;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NotFoundExceptionResponse {

    private String notFound;
}
