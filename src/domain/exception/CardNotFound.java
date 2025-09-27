// PBO[package]: Menentukan paket domain.exception; semua exception khusus kartu dikelompokkan di sini.
package domain.exception;

/**
 * PBO[class]: Exception untuk kasus kartu tidak ditemukan.
 *
 * <p>Kelas ini digunakan untuk melempar error sederhana ketika kartu tidak ditemukan
 * berdasarkan ID rekening. Pesan error otomatis dibentuk dari parameter.</p>
 *
 * <p>Untuk sistem perbankan yang lebih kompleks, disarankan
 * menggunakan {@link CardException.CardNotFound} agar konsisten dengan
 * struktur error yang kaya (error code, user message, technical message, context).</p>
 *
 * @author Gede Dhanu Purnayasa
 * @author Made Marsel Biliana Wijaya
 * @since 1.0
 */
public class CardNotFound extends RuntimeException {
    /**
     * Konstruktor untuk membuat exception dengan pesan yang menyebutkan ID rekening.
     *
     * @param accountId ID rekening yang tidak ditemukan kartunya
     */
    public CardNotFound(int accountId) {
        super("Kartu untuk rekening " + accountId + " tidak ditemukan");
    }
}
