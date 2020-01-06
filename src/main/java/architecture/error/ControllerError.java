package architecture.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Auction not found!")
public class ControllerError extends BaseError {
    public ControllerError(String message) {
        super(message);
    }
}
