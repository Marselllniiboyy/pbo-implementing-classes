// PBO[package]: Menentukan paket domain.exception agar semua exception terkait nasabah dikelola terpusat.
package domain.exception;

// PBO[import]: Memanfaatkan Map untuk menyimpan context informasi tambahan saat error terjadi.
import java.util.Map;

/**
 * PBO[class]: Exception untuk masalah terkait customer (nasabah).
 *
 * <p>Kelas dasar untuk semua error yang berhubungan dengan entitas nasabah.
 * Mewarisi {@link BankingException} sehingga setiap exception membawa
 * errorCode, pesan user-friendly, pesan teknis, timestamp, dan context map
 * untuk debugging/logging.</p>
 *
 * <p>Gunakan subclass statis sesuai skenario:</p>
 * <ul>
 *   <li>{@code CustomerNotFound} – nasabah tidak ditemukan berdasarkan ID/email.</li>
 *   <li>{@code CustomerAlreadyExists} – email nasabah sudah terdaftar.</li>
 *   <li>{@code InvalidCustomerData} – data nasabah tidak valid.</li>
 *   <li>{@code CustomerUnderage} – usia nasabah di bawah batas minimum.</li>
 * </ul>
 *
 * @see domain.entity.CustomerEntity
 * @see BankingException
 *
 * @since 1.0
 * @author Gede Dhanu Purnayasa
 * @author Made Marsel Biliana Wijaya
 */
public class CustomerException extends BankingException {

    /**
     * PBO[subclass]: Dilempar ketika nasabah tidak ditemukan berdasarkan ID atau email.
     */
    public static class CustomerNotFound extends CustomerException {
        public CustomerNotFound(int customerId) {
            super("CUSTOMER_NOT_FOUND", 
                "Nasabah tidak ditemukan", 
                String.format("Customer with ID %d not found", customerId),
                Map.of("customerId", customerId));
        }
        
        public CustomerNotFound(String email) {
            super("CUSTOMER_NOT_FOUND", 
                "Nasabah tidak ditemukan", 
                String.format("Customer with email %s not found", email),
                Map.of("email", email));
        }
    }
    /**
     * PBO[subclass]: Dilempar ketika email nasabah sudah terdaftar.
     */
    public static class CustomerAlreadyExists extends CustomerException {
        public CustomerAlreadyExists(String email) {
            super("CUSTOMER_ALREADY_EXISTS", 
                "Email sudah terdaftar", 
                String.format("Customer with email %s already exists", email),
                Map.of("email", email));
        }
    }
    /**
     * PBO[subclass]: Dilempar ketika data field tertentu pada nasabah tidak valid.
     */
    public static class InvalidCustomerData extends CustomerException {
        public InvalidCustomerData(String field, String value, String reason) {
            super("INVALID_CUSTOMER_DATA", 
                String.format("Data %s tidak valid", field), 
                String.format("Invalid customer data: %s = %s, reason: %s", field, value, reason),
                Map.of(
                    "field", field,
                    "value", value,
                    "reason", reason
                ));
        }
        
        public InvalidCustomerData(String message) {
            super("INVALID_CUSTOMER_DATA", 
                "Data nasabah tidak valid", 
                message,
                Map.of("message", message));
        }
    }
    /**
     * PBO[subclass]: Dilempar ketika usia nasabah di bawah batas minimum.
     */
    public static class CustomerUnderage extends CustomerException {
        public CustomerUnderage(int age, int minimumAge) {
            super("CUSTOMER_UNDERAGE", 
                "Usia tidak memenuhi syarat", 
                String.format("Customer age %d below minimum %d", age, minimumAge),
                Map.of(
                    "age", age,
                    "minimumAge", minimumAge
                ));
        }
    }
    /**
     * Konstruktor privat hanya untuk dipanggil oleh subclass.
     */
    private CustomerException(String errorCode, String userMessage, String technicalMessage, Map<String, Object> context) {
        super(errorCode, userMessage, technicalMessage, context);
    }
}
