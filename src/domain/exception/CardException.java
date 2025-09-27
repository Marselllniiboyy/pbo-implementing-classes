// PBO[package]: Menentukan paket domain.exception; semua exception khusus kartu dikelompokkan di sini.
package domain.exception;

// PBO[import]: Mengimpor BigDecimal (jika diperlukan untuk nilai numerik saldo/limit kartu di masa depan).
// PBO[import]: Mengimpor Map untuk menyimpan informasi tambahan (context) yang berkaitan dengan error.
import java.math.BigDecimal;
import java.util.Map;

/**
 * PBO[class]: CardException adalah turunan dari BankingException untuk masalah yang berkaitan dengan kartu.
 *
 * <p>Kelas ini menjadi induk untuk berbagai jenis error yang berhubungan dengan kartu,
 * seperti kartu tidak ditemukan, PIN salah, kartu expired, dsb.
 * Masing-masing error dibuat sebagai inner static class dengan payload lengkap (error code, pesan user/teknis, context).</p>
 *
 * @since 1.0
 */
public class CardException extends BankingException {
    /**
     * PBO[inner-class]: Exception untuk kasus kartu tidak ditemukan berdasarkan accountId atau cardNumber.
     */
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
    /**
     * PBO[inner-class]: Exception untuk kasus PIN salah (invalid PIN) baik berdasarkan accountId maupun cardNumber.
     */
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
    /**
     * PBO[inner-class]: Exception untuk kasus kartu sudah expired.
     */
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
    /**
     * PBO[inner-class]: Exception untuk kasus kartu tidak aktif.
     */
    public static class CardInactive extends CardException {
        public CardInactive(String cardNumber) {
            super("CARD_INACTIVE", 
                "Kartu tidak aktif", 
                String.format("Card %s is inactive", cardNumber),
                Map.of("cardNumber", cardNumber));
        }
    }
    /**
     * PBO[inner-class]: Exception untuk kasus akun sudah memiliki kartu yang terdaftar.
     */
    public static class CardAlreadyAssigned extends CardException {
        public CardAlreadyAssigned(int accountId) {
            super("CARD_ALREADY_ASSIGNED", 
                "Akun sudah memiliki kartu", 
                String.format("Account %d already has a card assigned", accountId),
                Map.of("accountId", accountId));
        }
    }
    /**
     * PBO[constructor-private]: Konstruktor utama untuk CardException; dipanggil oleh inner class.
     *
     * @param errorCode kode error unik
     * @param userMessage pesan ramah pengguna
     * @param technicalMessage pesan teknis untuk debugging
     * @param context map berisi info tambahan
     */
    private CardException(String errorCode, String userMessage, String technicalMessage, Map<String, Object> context) {
        super(errorCode, userMessage, technicalMessage, context);
    }
}
