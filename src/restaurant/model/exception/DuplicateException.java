package restaurant.model.exception;

public class DuplicateException extends GeneralException {

    public DuplicateException() {
    }

    public DuplicateException(String message) {
        super(message);
    }
}
