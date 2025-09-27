// PBO[package]: Menentukan paket domain.exception; memisahkan exception khusus dari domain agar arsitektur lebih modular.
package domain.exception;

// PBO[import]: Mengimpor CurrencyFormatter untuk memformat nilai rupiah pada pesan exception saldo tidak mencukupi.
// PBO[import]: Mengimpor BigDecimal untuk representasi nilai uang yang presisi.
// PBO[import]: Mengimpor Map untuk menyimpan konteks tambahan (detail terkait exception).
import domain.util.CurrencyFormatter;
import java.math.BigDecimal;
import java.util.Map;

/**
 * PBO[exception]: AccountException adalah superclass untuk semua masalah terkait akun/rekening.
 * Exception untuk masalah terkait akun
 * p>Class ini memperluas BankingException dan menyediakan beberapa subclass statis
 * untuk skenario spesifik: akun tidak ditemukan, saldo tidak mencukupi, akun sudah ada,
 * atau tipe rekening tidak valid. Dengan pola ini, setiap jenis error punya kode,
 * pesan user-friendly, pesan teknis, dan context tambahan.</p>
 */
public class AccountException extends BankingException {

    /**
     * PBO[exception subclass]: Dilempar ketika nomor rekening atau ID rekening tidak ditemukan di sistem.
     */
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

    /**
     * PBO[exception subclass]: Dilempar ketika saldo akun tidak cukup untuk melakukan transaksi.
     *
     * <p>Menyimpan currentBalance, requiredAmount, dan shortfall sebagai konteks,
     * serta menyediakan method getter untuk mengambil nilai-nilai tersebut
     * dalam format Rupiah Indonesia agar lebih ramah pengguna.</p>
     */
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
        // PBO[helper method]: Mengembalikan saldo saat ini dalam format Rupiah.
        public String getFormattedCurrentBalance() {
            return CurrencyFormatter.format((BigDecimal) getContext().get("currentBalance"));
        }
        
        /**
         * Mengembalikan jumlah yang dibutuhkan dalam format Rupiah Indonesia.
         * 
         * @return String jumlah yang diformat dengan "Rp" dan pemisah ribuan
         */
        // PBO[helper method]: Mengembalikan jumlah yang dibutuhkan dalam format Rupiah.
        public String getFormattedRequiredAmount() {
            return CurrencyFormatter.format((BigDecimal) getContext().get("requiredAmount"));
        }
        
        /**
         * Mengembalikan kekurangan saldo dalam format Rupiah Indonesia.
         * 
         * @return String kekurangan yang diformat dengan "Rp" dan pemisah ribuan
         */
        // PBO[helper method]: Mengembalikan kekurangan saldo dalam format Rupiah.
        public String getFormattedShortfall() {
            return CurrencyFormatter.format((BigDecimal) getContext().get("shortfall"));
        }
    }

    /**
     * PBO[exception subclass]: Dilempar ketika mencoba membuat rekening baru dengan nomor yang sudah ada.
     */
    public static class AccountAlreadyExists extends AccountException {
        public AccountAlreadyExists(String accountNumber) {
            super("ACCOUNT_ALREADY_EXISTS", 
                "Rekening sudah ada", 
                String.format("Account with number %s already exists", accountNumber),
                Map.of("accountNumber", accountNumber));
        }
    }
    /**
     * PBO[exception subclass]: Dilempar ketika tipe rekening yang diminta tidak valid.
     */
    public static class InvalidAccountType extends AccountException {
        public InvalidAccountType(String accountType) {
            super("INVALID_ACCOUNT_TYPE", 
                "Tipe rekening tidak valid", 
                String.format("Invalid account type: %s", accountType),
                Map.of("accountType", accountType));
        }
    }

    // PBO[constructor-private]: Konstruktor private untuk memastikan semua exception harus berasal dari subclass di atas.
    private AccountException(String errorCode, String userMessage, String technicalMessage, Map<String, Object> context) {
        super(errorCode, userMessage, technicalMessage, context);
    }
}
