package ai.momentive.petfish.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT, reason = "Aquarium size is not suitable for the fish")
public class AquariumSizeException extends RuntimeException {
}
