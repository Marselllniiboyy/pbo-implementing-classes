package domain.exception;

import java.time.LocalDateTime;
import java.util.Map;

/**
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
    private final String errorCode;
    private final LocalDateTime timestamp;
    private final Map<String, Object> context;
    private final String userMessage;
    private final String technicalMessage;

    /**
     * Konstruktor lengkap untuk BankingException.
     * 
     * @param errorCode kode error yang unik untuk jenis exception ini
     * @param userMessage pesan yang user-friendly untuk ditampilkan ke pengguna
     * @param technicalMessage pesan teknis untuk debugging developer
     * @param context map berisi informasi tambahan untuk debugging
     */
    protected BankingException(String errorCode, String userMessage, String technicalMessage, Map<String, Object> context) {
        super(technicalMessage);
        this.errorCode = errorCode;
        this.userMessage = userMessage;
        this.technicalMessage = technicalMessage;
        this.context = context != null ? Map.copyOf(context) : Map.of();
        this.timestamp = LocalDateTime.now();
    }

    /**
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
     * Konstruktor BankingException dengan pesan yang sama untuk user dan technical.
     * 
     * @param errorCode kode error yang unik untuk jenis exception ini
     * @param message pesan yang akan digunakan untuk user dan technical message
     */
    protected BankingException(String errorCode, String message) {
        this(errorCode, message, message, null);
    }

    /**
     * Mengembalikan kode error yang unik untuk jenis exception ini.
     * 
     * @return kode error sebagai string
     */
    public String getErrorCode() {
        return errorCode;
    }

    /**
     * Mengembalikan timestamp saat exception terjadi.
     * 
     * @return LocalDateTime saat exception terjadi
     */
    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    /**
     * Mengembalikan context map berisi informasi tambahan untuk debugging.
     * 
     * @return Map berisi key-value pairs untuk debugging
     */
    public Map<String, Object> getContext() {
        return context;
    }

    /**
     * Mengembalikan pesan yang user-friendly untuk ditampilkan ke pengguna.
     * 
     * @return pesan user-friendly sebagai string
     */
    public String getUserMessage() {
        return userMessage;
    }

    /**
     * Mengembalikan pesan teknis untuk debugging developer.
     * 
     * @return pesan teknis sebagai string
     */
    public String getTechnicalMessage() {
        return technicalMessage;
    }

    /**
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
