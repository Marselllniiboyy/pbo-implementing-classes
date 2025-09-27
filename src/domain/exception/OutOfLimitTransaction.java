// PBO[package]: Menentukan paket domain.exception agar exception ini dapat digunakan di seluruh domain
package domain.exception;

/**
 * Exception untuk menandakan transaksi melebihi batas limit yang diperbolehkan.
 *
 * <p>Kelas ini digunakan ketika sebuah transaksi melebihi limit
 * harian, bulanan, atau limit tertentu yang telah ditetapkan oleh sistem
 * atau oleh kebijakan perbankan. Exception ini merupakan turunan dari
 * {@link RuntimeException} sehingga termasuk unchecked exception.</p>
 *
 * <p>Contoh penggunaan:
 * <pre>
 * if (jumlahTransaksi > limit) {
 *     throw new OutOfLimitTransaction("Transaksi melebihi limit harian");
 * }
 * </pre>
 * </p>
 *
 * @since 1.0
 * @author
 *  Gede Dhanu Purnayasa
 *  Made Marsel Biliana Wijaya
 */
public class OutOfLimitTransaction extends RuntimeException {
    /**
     * Membuat exception baru untuk kondisi transaksi melebihi limit.
     *
     * @param message pesan detail mengenai limit yang dilanggar
     */
    public OutOfLimitTransaction(String message) {
        super(message);
    }
}
