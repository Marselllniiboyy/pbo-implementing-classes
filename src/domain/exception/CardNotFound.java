package domain.exception;

public class CardNotFound extends RuntimeException {
    public CardNotFound(int accountId) {
        super("Kartu untuk rekening " + accountId + " tidak ditemukan");
    }
}
