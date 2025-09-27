// PBO[package]: Menentukan paket domain.exception agar exception ini dapat digunakan di seluruh domain
package domain.exception;
// PBO[import]: Mengimpor utilitas untuk format mata uang Rupiah
import domain.util.CurrencyFormatter;
// PBO[import]: Mengimpor BigDecimal untuk nilai numerik presisi tinggi
// PBO[import]: Mengimpor Map untuk menyimpan context tambahan pada exception
import java.math.BigDecimal;
import java.util.Map;

/**
 * PBO[class]: Kelas induk untuk semua exception terkait transaksi
 * Exception untuk masalah terkait transaksi.
 *
 * <p>Kelas induk ini dan inner class-nya mewakili berbagai kondisi error
 * yang dapat terjadi saat melakukan transaksi dalam sistem perbankan.
 * Setiap inner class memiliki errorCode, pesan user-friendly, pesan teknis,
 * dan context tambahan untuk memudahkan debugging.</p>
 *
 * <p>Contoh penggunaan:
 * <pre>
 * if (totalHariIni.compareTo(limit) > 0) {
 *     throw new TransactionException.DailyLimitExceeded(noRekening, "TRANSFER", totalHariIni, limit);
 * }
 * </pre>
 * </p>
 *
 * @since 1.0
 * @see BankingException
 */
public class TransactionException extends BankingException {
    // PBO[inner-class]: Exception untuk batas transaksi harian terlampaui
    public static class DailyLimitExceeded extends TransactionException {
        // PBO[konstruktor]: Membuat exception DailyLimitExceeded dengan context limit transaksi
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
         * PBO[method]: Mendapatkan total harian diformat Rupiah
         * Mengembalikan total harian saat ini dalam format Rupiah Indonesia.
         * 
         * @return String total harian yang diformat dengan "Rp" dan pemisah ribuan
         */
        public String getFormattedCurrentTotal() {
            return CurrencyFormatter.format((BigDecimal) getContext().get("currentTotal"));
        }
        
        /**
         * PBO[method]: Mendapatkan limit diformat Rupiah
         * Mengembalikan batas maksimal dalam format Rupiah Indonesia.
         * 
         * @return String batas maksimal yang diformat dengan "Rp" dan pemisah ribuan
         */
        public String getFormattedLimit() {
            return CurrencyFormatter.format((BigDecimal) getContext().get("limit"));
        }
        
        /**
         * PBO[method]: Mendapatkan kelebihan diformat Rupiah
         * Mengembalikan kelebihan batas dalam format Rupiah Indonesia.
         * 
         * @return String kelebihan yang diformat dengan "Rp" dan pemisah ribuan
         */
        public String getFormattedExcess() {
            return CurrencyFormatter.format((BigDecimal) getContext().get("excess"));
        }

        // PBO[utility]: Mengubah kode transaksi menjadi nama Indonesia user-friendly
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
    // PBO[inner-class]: Exception untuk jumlah transaksi tidak valid
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
    // PBO[inner-class]: Exception untuk transfer ke rekening yang sama
    public static class SameAccountTransfer extends TransactionException {
        public SameAccountTransfer(String accountNumber) {
            super("SAME_ACCOUNT_TRANSFER", 
                "Tidak dapat transfer ke rekening yang sama", 
                String.format("Cannot transfer to same account %s", accountNumber),
                Map.of("accountNumber", accountNumber));
        }
    }
    // PBO[inner-class]: Exception untuk transaksi tidak ditemukan
    public static class TransactionNotFound extends TransactionException {
        public TransactionNotFound(int transactionId) {
            super("TRANSACTION_NOT_FOUND", 
                "Transaksi tidak ditemukan", 
                String.format("Transaction with ID %d not found", transactionId),
                Map.of("transactionId", transactionId));
        }
    }
    // PBO[inner-class]: Exception untuk biaya transfer melebihi saldo
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
    // PBO[konstruktor]: Konstruktor privat untuk TransactionException agar hanya inner class yang bisa membuat
    private TransactionException(String errorCode, String userMessage, String technicalMessage, Map<String, Object> context) {
        super(errorCode, userMessage, technicalMessage, context);
    }
}
