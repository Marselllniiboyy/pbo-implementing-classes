// PBO[package]: Menentukan paket domain.exception agar exception ini bisa digunakan di seluruh domain untuk kasus entitas tidak ditemukan.
package domain.exception;

/**
 * PBO[class]: Exception umum yang dilempar ketika entitas tidak ditemukan dalam sistem.
 *
 * <p>Kelas ini digunakan sebagai pengecualian generik untuk semua kasus
 * pencarian entitas yang gagal. Misalnya ketika mencoba update atau delete
 * tapi entitasnya tidak ada di database.</p>
 *
 * <p>Berbeda dengan {@link BankingException}, exception ini lebih sederhana
 * dan hanya membawa pesan error saja tanpa context map.</p>
 *
 * @since 1.0
 * @author
 *   Gede Dhanu Purnayasa
 *   Made Marsel Biliana Wijaya
 */
public class EntityNotFoundException extends RuntimeException {
    /**
     * PBO[constructor]: Membuat EntityNotFoundException baru dengan pesan tertentu.
     *
     * @param message pesan error yang menjelaskan entitas apa yang tidak ditemukan
     */
    public EntityNotFoundException(String message) {
        super(message);
    }
}
