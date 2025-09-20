package domain.exception;

public class InvalidPin extends RuntimeException {
    public InvalidPin() {
        super("Pin yang anda masukkan tidak sesuai");
    }
}
