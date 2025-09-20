package domain.exception;

import java.math.BigDecimal;
import java.util.Map;

/**
 * Exception untuk masalah terkait jenis kartu
 */
public class CardTypeException extends BankingException {
    
    public static class CardTypeNotFound extends CardTypeException {
        public CardTypeNotFound(int cardTypeId) {
            super("CARD_TYPE_NOT_FOUND", 
                "Jenis kartu tidak ditemukan", 
                String.format("Card type with ID %d not found", cardTypeId),
                Map.of("cardTypeId", cardTypeId));
        }
        
        public CardTypeNotFound(String cardTypeName) {
            super("CARD_TYPE_NOT_FOUND", 
                "Jenis kartu tidak ditemukan", 
                String.format("Card type with name %s not found", cardTypeName),
                Map.of("cardTypeName", cardTypeName));
        }
    }
    
    public static class InvalidCardTypeData extends CardTypeException {
        public InvalidCardTypeData(String field, Object value, String reason) {
            super("INVALID_CARD_TYPE_DATA", 
                String.format("Data %s tidak valid", field), 
                String.format("Invalid card type data: %s = %s, reason: %s", field, value, reason),
                Map.of(
                    "field", field,
                    "value", value,
                    "reason", reason
                ));
        }
    }
    
    public static class NegativeLimit extends CardTypeException {
        public NegativeLimit(String limitType, BigDecimal value) {
            super("NEGATIVE_LIMIT", 
                String.format("Limit %s tidak boleh negatif", limitType), 
                String.format("Negative limit not allowed: %s = %s", limitType, value),
                Map.of(
                    "limitType", limitType,
                    "value", value
                ));
        }
    }
    
    public static class InvalidMonthlyPrice extends CardTypeException {
        public InvalidMonthlyPrice(BigDecimal monthlyPrice) {
            super("INVALID_MONTHLY_PRICE", 
                "Harga bulanan tidak valid", 
                String.format("Invalid monthly price: %s", monthlyPrice),
                Map.of("monthlyPrice", monthlyPrice));
        }
    }
    
    private CardTypeException(String errorCode, String userMessage, String technicalMessage, Map<String, Object> context) {
        super(errorCode, userMessage, technicalMessage, context);
    }
}
