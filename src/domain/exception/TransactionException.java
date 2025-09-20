package domain.exception;

import application.util.CurrencyFormatter;
import java.math.BigDecimal;
import java.util.Map;

/**
 * Exception untuk masalah terkait transaksi
 */
public class TransactionException extends BankingException {
    
    public static class DailyLimitExceeded extends TransactionException {
        public DailyLimitExceeded(String accountNumber, String transactionType, BigDecimal currentTotal, BigDecimal limit) {
            super("DAILY_LIMIT_EXCEEDED", 
                String.format("Batas %s harian terlampaui", getTransactionTypeName(transactionType)), 
                String.format("Daily %s limit exceeded. Current: %s, Limit: %s", transactionType, currentTotal, limit),
                Map.of(
                    "accountNumber", accountNumber,
                    "transactionType", transactionType,
                    "currentTotal", currentTotal,
                    "limit", limit,
                    "excess", currentTotal.subtract(limit)
                ));
        }
        
        /**
         * Mengembalikan total harian saat ini dalam format Rupiah Indonesia.
         * 
         * @return String total harian yang diformat dengan "Rp" dan pemisah ribuan
         */
        public String getFormattedCurrentTotal() {
            return CurrencyFormatter.format((BigDecimal) getContext().get("currentTotal"));
        }
        
        /**
         * Mengembalikan batas maksimal dalam format Rupiah Indonesia.
         * 
         * @return String batas maksimal yang diformat dengan "Rp" dan pemisah ribuan
         */
        public String getFormattedLimit() {
            return CurrencyFormatter.format((BigDecimal) getContext().get("limit"));
        }
        
        /**
         * Mengembalikan kelebihan batas dalam format Rupiah Indonesia.
         * 
         * @return String kelebihan yang diformat dengan "Rp" dan pemisah ribuan
         */
        public String getFormattedExcess() {
            return CurrencyFormatter.format((BigDecimal) getContext().get("excess"));
        }
        
        private static String getTransactionTypeName(String type) {
            return switch (type) {
                case "TRANSFER" -> "transfer";
                case "WITHDRAW" -> "penarikan";
                case "DEPOSIT" -> "setoran";
                case "TRANSFER_VIA_CARD" -> "transfer via kartu";
                case "WITHDRAW_VIA_CARD" -> "penarikan via kartu";
                case "DEPOSIT_VIA_CARD" -> "setoran via kartu";
                default -> type.toLowerCase();
            };
        }
    }
    
    public static class InvalidTransactionAmount extends TransactionException {
        public InvalidTransactionAmount(BigDecimal amount) {
            super("INVALID_TRANSACTION_AMOUNT", 
                "Jumlah transaksi tidak valid", 
                String.format("Invalid transaction amount: %s", amount),
                Map.of("amount", amount));
        }
        
        public InvalidTransactionAmount(BigDecimal amount, BigDecimal minimumAmount) {
            super("INVALID_TRANSACTION_AMOUNT", 
                String.format("Jumlah transaksi minimal %s", minimumAmount), 
                String.format("Transaction amount %s below minimum %s", amount, minimumAmount),
                Map.of(
                    "amount", amount,
                    "minimumAmount", minimumAmount
                ));
        }
    }
    
    public static class SameAccountTransfer extends TransactionException {
        public SameAccountTransfer(String accountNumber) {
            super("SAME_ACCOUNT_TRANSFER", 
                "Tidak dapat transfer ke rekening yang sama", 
                String.format("Cannot transfer to same account %s", accountNumber),
                Map.of("accountNumber", accountNumber));
        }
    }
    
    public static class TransactionNotFound extends TransactionException {
        public TransactionNotFound(int transactionId) {
            super("TRANSACTION_NOT_FOUND", 
                "Transaksi tidak ditemukan", 
                String.format("Transaction with ID %d not found", transactionId),
                Map.of("transactionId", transactionId));
        }
    }
    
    public static class TransferFeeExceedsBalance extends TransactionException {
        public TransferFeeExceedsBalance(String accountNumber, BigDecimal balance, BigDecimal transferAmount, BigDecimal fee) {
            super("TRANSFER_FEE_EXCEEDS_BALANCE", 
                "Biaya transfer melebihi saldo", 
                String.format("Transfer fee %s exceeds balance %s for account %s", fee, balance, accountNumber),
                Map.of(
                    "accountNumber", accountNumber,
                    "balance", balance,
                    "transferAmount", transferAmount,
                    "fee", fee,
                    "totalRequired", transferAmount.add(fee)
                ));
        }
        
        /**
         * Mengembalikan saldo dalam format Rupiah Indonesia.
         * 
         * @return String saldo yang diformat dengan "Rp" dan pemisah ribuan
         */
        public String getFormattedBalance() {
            return CurrencyFormatter.format((BigDecimal) getContext().get("balance"));
        }
        
        /**
         * Mengembalikan biaya transfer dalam format Rupiah Indonesia.
         * 
         * @return String biaya yang diformat dengan "Rp" dan pemisah ribuan
         */
        public String getFormattedFee() {
            return CurrencyFormatter.format((BigDecimal) getContext().get("fee"));
        }
        
        /**
         * Mengembalikan total yang dibutuhkan dalam format Rupiah Indonesia.
         * 
         * @return String total yang diformat dengan "Rp" dan pemisah ribuan
         */
        public String getFormattedTotalRequired() {
            return CurrencyFormatter.format((BigDecimal) getContext().get("totalRequired"));
        }
    }
    
    private TransactionException(String errorCode, String userMessage, String technicalMessage, Map<String, Object> context) {
        super(errorCode, userMessage, technicalMessage, context);
    }
}
