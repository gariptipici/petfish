package ai.momentive.petfish.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Aquarium not found")
public class AquariumNotFoundException extends RuntimeException {
}
