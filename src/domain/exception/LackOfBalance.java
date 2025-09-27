// PBO[package]: Menentukan paket domain.exception agar exception ini bisa digunakan di seluruh domain untuk kasus saldo tidak mencukupi.
package domain.exception;

/**
 * PBO[class]: Exception untuk menandakan saldo tidak mencukupi.
 *
 * <p>Kelas ini digunakan ketika saldo pada akun pengguna
 * tidak cukup untuk melakukan transaksi atau penarikan dana.
 * Exception ini merupakan turunan dari {@link RuntimeException}
 * sehingga tidak perlu dideklarasikan secara eksplisit (unchecked exception).</p>
 *
 * <p>Biasanya dilempar pada proses debit, transfer, atau pembayaran
 * ketika saldo kurang dari jumlah yang dibutuhkan.</p>
 *
 * @since 1.0
 * @author
 *   Gede Dhanu Purnayasa
 *   Made Marsel Biliana Wijaya
 */
public class LackOfBalance extends RuntimeException {
    /**
     * PBO[constructor]: Membuat LackOfBalance exception baru
     * dengan pesan default bahwa saldo tidak mencukupi.
     */
    public LackOfBalance() {
        super("Saldo anda tidak mencukupi");
    }
}
