package domain.exception;

public class LackOfBalance extends RuntimeException {
    public LackOfBalance() {
        super("Saldo anda tidak mencukupi");
    }
}
