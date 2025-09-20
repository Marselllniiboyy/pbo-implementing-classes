package domain.exception;

public class OutOfLimitTransaction extends RuntimeException {
    public OutOfLimitTransaction(String message) {
        super(message);
    }
}
