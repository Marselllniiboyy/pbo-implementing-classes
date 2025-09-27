// PBO[package]: Menentukan paket domain.exception agar exception ini bisa digunakan di seluruh domain untuk kasus PIN tidak valid.
package domain.exception;

/**
 * PBO[class]: Exception untuk menandakan PIN yang dimasukkan tidak sesuai.
 *
 * <p>Kelas ini digunakan ketika pengguna atau sistem memasukkan PIN
 * yang tidak valid atau tidak cocok dengan data yang tersimpan.
 * Exception ini merupakan turunan dari {@link RuntimeException}
 * sehingga tidak perlu dideklarasikan secara eksplisit (unchecked exception).</p>
 *
 * <p>Biasanya dilempar pada proses autentikasi atau verifikasi PIN.</p>
 *
 * @since 1.0
 * @author
 *   Gede Dhanu Purnayasa
 *   Made Marsel Biliana Wijaya
 */
public class InvalidPin extends RuntimeException {
    /**
     * PBO[constructor]: Membuat InvalidPin exception baru
     * dengan pesan default bahwa PIN tidak sesuai.
     */
    public InvalidPin() {
        super("Pin yang anda masukkan tidak sesuai");
    }
}
