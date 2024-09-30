package living_alone.eat.exception;

public class MyDuplicateId extends RuntimeException {

    public MyDuplicateId() {
    }

    public MyDuplicateId(String message) {
        super(message);
    }

    public MyDuplicateId(String message, Throwable cause) {
        super(message, cause);
    }

    public MyDuplicateId(Throwable cause) {
        super(cause);
    }
}
