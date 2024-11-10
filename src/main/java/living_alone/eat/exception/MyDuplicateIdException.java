package living_alone.eat.exception;

public class MyDuplicateIdException extends RuntimeException {

    public MyDuplicateIdException() {
    }

    public MyDuplicateIdException(String message) {
        super(message);
    }

    public MyDuplicateIdException(String message, Throwable cause) {
        super(message, cause);
    }

    public MyDuplicateIdException(Throwable cause) {
        super(cause);
    }
}
