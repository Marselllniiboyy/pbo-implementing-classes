package domain.exception;

import java.math.BigDecimal;
import java.util.Map;

/**
 * Exception untuk masalah terkait kartu
 */
public class CardException extends BankingException {
    
    public static class CardNotFound extends CardException {
        public CardNotFound(int accountId) {
            super("CARD_NOT_FOUND", 
                "Kartu tidak ditemukan", 
                String.format("Card for account ID %d not found", accountId),
                Map.of("accountId", accountId));
        }
        
        public CardNotFound(String cardNumber) {
            super("CARD_NOT_FOUND", 
                "Kartu tidak ditemukan", 
                String.format("Card with number %s not found", cardNumber),
                Map.of("cardNumber", cardNumber));
        }
    }
    
    public static class InvalidPin extends CardException {
        public InvalidPin(int accountId, int attempts) {
            super("INVALID_PIN", 
                "PIN yang Anda masukkan salah", 
                String.format("Invalid PIN for account %d, attempt %d", accountId, attempts),
                Map.of(
                    "accountId", accountId,
                    "attempts", attempts
                ));
        }
        
        public InvalidPin(String cardNumber) {
            super("INVALID_PIN", 
                "PIN yang Anda masukkan salah", 
                String.format("Invalid PIN for card %s", cardNumber),
                Map.of("cardNumber", cardNumber));
        }
    }
    
    public static class CardExpired extends CardException {
        public CardExpired(String cardNumber, String expiredDate) {
            super("CARD_EXPIRED", 
                "Kartu sudah expired", 
                String.format("Card %s expired on %s", cardNumber, expiredDate),
                Map.of(
                    "cardNumber", cardNumber,
                    "expiredDate", expiredDate
                ));
        }
    }
    
    public static class CardInactive extends CardException {
        public CardInactive(String cardNumber) {
            super("CARD_INACTIVE", 
                "Kartu tidak aktif", 
                String.format("Card %s is inactive", cardNumber),
                Map.of("cardNumber", cardNumber));
        }
    }
    
    public static class CardAlreadyAssigned extends CardException {
        public CardAlreadyAssigned(int accountId) {
            super("CARD_ALREADY_ASSIGNED", 
                "Akun sudah memiliki kartu", 
                String.format("Account %d already has a card assigned", accountId),
                Map.of("accountId", accountId));
        }
    }
    
    private CardException(String errorCode, String userMessage, String technicalMessage, Map<String, Object> context) {
        super(errorCode, userMessage, technicalMessage, context);
    }
}
