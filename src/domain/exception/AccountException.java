package domain.exception;

import domain.util.CurrencyFormatter;
import java.math.BigDecimal;
import java.util.Map;

/**
 * Exception untuk masalah terkait akun
 */
public class AccountException extends BankingException {
    
    public static class AccountNotFound extends AccountException {
        public AccountNotFound(String accountNumber) {
            super("ACCOUNT_NOT_FOUND", 
                "Rekening tidak ditemukan", 
                String.format("Account with number %s not found", accountNumber),
                Map.of("accountNumber", accountNumber));
        }
        
        public AccountNotFound(int accountId) {
            super("ACCOUNT_NOT_FOUND", 
                "Rekening tidak ditemukan", 
                String.format("Account with ID %d not found", accountId),
                Map.of("accountId", accountId));
        }
    }
    
    public static class InsufficientBalance extends AccountException {
        public InsufficientBalance(String accountNumber, BigDecimal currentBalance, BigDecimal requiredAmount) {
            super("INSUFFICIENT_BALANCE", 
                "Saldo tidak mencukupi", 
                String.format("Insufficient balance. Current: %s, Required: %s", currentBalance, requiredAmount),
                Map.of(
                    "accountNumber", accountNumber,
                    "currentBalance", currentBalance,
                    "requiredAmount", requiredAmount,
                    "shortfall", requiredAmount.subtract(currentBalance)
                ));
        }
        
        /**
         * Mengembalikan saldo saat ini dalam format Rupiah Indonesia.
         * 
         * @return String saldo yang diformat dengan "Rp" dan pemisah ribuan
         */
        public String getFormattedCurrentBalance() {
            return CurrencyFormatter.format((BigDecimal) getContext().get("currentBalance"));
        }
        
        /**
         * Mengembalikan jumlah yang dibutuhkan dalam format Rupiah Indonesia.
         * 
         * @return String jumlah yang diformat dengan "Rp" dan pemisah ribuan
         */
        public String getFormattedRequiredAmount() {
            return CurrencyFormatter.format((BigDecimal) getContext().get("requiredAmount"));
        }
        
        /**
         * Mengembalikan kekurangan saldo dalam format Rupiah Indonesia.
         * 
         * @return String kekurangan yang diformat dengan "Rp" dan pemisah ribuan
         */
        public String getFormattedShortfall() {
            return CurrencyFormatter.format((BigDecimal) getContext().get("shortfall"));
        }
    }
    
    public static class AccountAlreadyExists extends AccountException {
        public AccountAlreadyExists(String accountNumber) {
            super("ACCOUNT_ALREADY_EXISTS", 
                "Rekening sudah ada", 
                String.format("Account with number %s already exists", accountNumber),
                Map.of("accountNumber", accountNumber));
        }
    }
    
    public static class InvalidAccountType extends AccountException {
        public InvalidAccountType(String accountType) {
            super("INVALID_ACCOUNT_TYPE", 
                "Tipe rekening tidak valid", 
                String.format("Invalid account type: %s", accountType),
                Map.of("accountType", accountType));
        }
    }
    
    private AccountException(String errorCode, String userMessage, String technicalMessage, Map<String, Object> context) {
        super(errorCode, userMessage, technicalMessage, context);
    }
}
