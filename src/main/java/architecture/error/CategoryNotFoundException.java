package architecture.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Category not found!")
public class CategoryNotFoundException extends BaseControllerException {
    public CategoryNotFoundException(String message) {
        super(message);
    }
}
