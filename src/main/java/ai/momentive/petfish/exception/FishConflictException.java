package ai.momentive.petfish.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT, reason = "There is another species of fish that conflicts with this fish")
public class FishConflictException extends RuntimeException {
}
