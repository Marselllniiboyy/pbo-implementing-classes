// PBO[package]: Menentukan paket domain.exception; semua exception sistem perbankan dikelompokkan di sini.
package domain.exception;

// PBO[import]: Mengimpor LocalDateTime untuk mencatat waktu kapan exception terjadi.
// PBO[import]: Mengimpor Map untuk menyimpan context informasi tambahan yang berkaitan dengan error.
import java.util.Map;
import java.time.LocalDateTime;
import java.util.Map;

/**
 * PBO[abstract class]: BankingException adalah base/induk untuk semua exception di sistem perbankan.
 * Base exception class untuk semua exception dalam sistem perbankan.
 * 
 * <p>Class ini menyediakan payload yang kaya untuk debugging dan error handling
 * termasuk error code, timestamp, context map, dan pesan yang terpisah untuk
 * user dan technical. Semua exception dalam sistem perbankan harus extend
 * class ini untuk konsistensi.</p>
 * 
 * @author Gede Dhanu Purnayasa
 * @author Made Marsel Biliana Wijaya
 * @since 1.0
 */
public abstract class BankingException extends RuntimeException {
    private final String errorCode;// PBO[field-final]: Kode error unik untuk mengidentifikasi jenis exception.
    private final LocalDateTime timestamp;// PBO[field-final]: Timestamp kapan exception dibuat.
    private final Map<String, Object> context;// PBO[field-final]: Map berisi informasi tambahan (context) terkait error ini.
    private final String userMessage;// PBO[field-final]: Pesan yang user-friendly untuk ditampilkan ke pengguna.
    private final String technicalMessage;// PBO[field-final]: Pesan teknis untuk developer/debugging.

    /**
     * PBO[constructor]: Konstruktor lengkap untuk BankingException.
     * Konstruktor lengkap untuk BankingException.
     * 
     * @param errorCode kode error yang unik untuk jenis exception ini
     * @param userMessage pesan yang user-friendly untuk ditampilkan ke pengguna
     * @param technicalMessage pesan teknis untuk debugging developer
     * @param context map berisi informasi tambahan untuk debugging
     */
    protected BankingException(String errorCode, String userMessage, String technicalMessage, Map<String, Object> context) {
        super(technicalMessage); // memanggil RuntimeException dengan pesan teknis
        this.errorCode = errorCode;
        this.userMessage = userMessage;
        this.technicalMessage = technicalMessage;
        this.context = context != null ? Map.copyOf(context) : Map.of();
        this.timestamp = LocalDateTime.now();
    }

    /**
     * PBO[constructor-overload]: Konstruktor tanpa context tambahan.
     * Konstruktor BankingException tanpa context.
     * 
     * @param errorCode kode error yang unik untuk jenis exception ini
     * @param userMessage pesan yang user-friendly untuk ditampilkan ke pengguna
     * @param technicalMessage pesan teknis untuk debugging developer
     */
    protected BankingException(String errorCode, String userMessage, String technicalMessage) {
        this(errorCode, userMessage, technicalMessage, null);
    }

    /**
     * PBO[constructor-overload]: Konstruktor dengan pesan yang sama untuk user dan technical.
     * Konstruktor BankingException dengan pesan yang sama untuk user dan technical.
     * 
     * @param errorCode kode error yang unik untuk jenis exception ini
     * @param message pesan yang akan digunakan untuk user dan technical message
     */
    protected BankingException(String errorCode, String message) {
        this(errorCode, message, message, null);
    }

    /**
     * PBO[getter]: Mengembalikan kode error unik.
     * Mengembalikan kode error yang unik untuk jenis exception ini.
     * 
     * @return kode error sebagai string
     */
    public String getErrorCode() {
        return errorCode;
    }

    /**
     * PBO[getter]: Mengembalikan timestamp kapan exception dibuat.
     * Mengembalikan timestamp saat exception terjadi.
     * 
     * @return LocalDateTime saat exception terjadi
     */
    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    /**
     * PBO[getter]: Mengembalikan map context informasi tambahan untuk debugging.
     * Mengembalikan context map berisi informasi tambahan untuk debugging.
     * 
     * @return Map berisi key-value pairs untuk debugging
     */
    public Map<String, Object> getContext() {
        return context;
    }

    /**
     * PBO[getter]: Mengembalikan pesan user-friendly untuk ditampilkan ke pengguna.
     * Mengembalikan pesan yang user-friendly untuk ditampilkan ke pengguna.
     * 
     * @return pesan user-friendly sebagai string
     */
    public String getUserMessage() {
        return userMessage;
    }

    /**
     * PBO[getter]: Mengembalikan pesan teknis untuk debugging developer.
     * Mengembalikan pesan teknis untuk debugging developer.
     * 
     * @return pesan teknis sebagai string
     */
    public String getTechnicalMessage() {
        return technicalMessage;
    }

    /**
     * PBO[override toString]: Mengembalikan representasi string exception yang lengkap:
     * [errorCode] userMessage - technicalMessage (Context: contextMap)
     * Mengembalikan representasi string dari exception ini.
     * 
     * <p>Format: [errorCode] userMessage - technicalMessage (Context: contextMap)</p>
     * 
     * @return string representation dari exception
     */
    @Override
    public String toString() {
        return String.format("[%s] %s - %s (Context: %s)", 
            errorCode, userMessage, technicalMessage, context);
    }
}
