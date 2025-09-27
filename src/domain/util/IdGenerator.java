// PBO[package]: Menentukan paket tempat utility IdGenerator berada.
package domain.util;

import java.util.concurrent.ThreadLocalRandom;

/**
 * PBO[utility]: Utility class untuk menangani pembuatan ID unik pada setiap komponen aplikasi.
 * Utility class untuk menangani pembuatan ID pada setiap komponen aplikasi
 *
 * <p>Class ini menyediakan method untuk menangani berbagai jenis tools untuk
 * membuat identifier unik sesuai dengan format yang dibutuhkan.</p>
 *
 * @author Gede Dhanu Purnayasa
 * @author Made Marsel Biliana Wijaya
 * @since 1.0
 */
// PBO[class-final]: IdGenerator adalah class utilitas final (tidak bisa di-extend) untuk menghasilkan nomor unik.
public final class IdGenerator {
    /**
     * PBO[static method]: Menghasilkan nomor kartu ATM/Debit yang unik dengan 10 digit.
     * Menghasilkan nomor kartu ATM/Debit yang unik.
     *
     * <p>Method ini menggunakan ThreadLocalRandom untuk menghasilkan
     * nomor kartu 10 digit yang unik dalam sistem.</p>
     *
     * @return String berisi nomor kartu 10 digit
     */
    public static String generateCardNumber() {
        long min = 1_000_000_000L; // PBO[local-variable]: Batas bawah untuk nomor 10 digit.

        // The upper bound (exclusive), which is the smallest 11-digit number
        long max = 10_000_000_000L; // PBO[local-variable]: Batas atas eksklusif untuk nomor 10 digit.

        return Long.toString(ThreadLocalRandom.current().nextLong(min, max));
    }

    /**
     * PBO[static method]: Menghasilkan nomor rekening bank yang unik dengan 10 digit.
     * Menghasilkan nomor rekening bank yang unik.
     *
     * <p>Method ini menggunakan ThreadLocalRandom untuk menghasilkan
     * nomor rekening 10 digit yang unik dalam sistem.</p>
     *
     * @return String berisi nomor rekening 10 digit
     */
    public static String generateAccountNumber() {
        long min = 1_000_000_000L; // PBO[local-variable]: Batas bawah untuk nomor 10 digit.

        // The upper bound (exclusive), which is the smallest 11-digit number
        long max = 10_000_000_000L; // PBO[local-variable]: Batas atas eksklusif untuk nomor 10 digit.

        return Long.toString(ThreadLocalRandom.current().nextLong(min, max));
    }
}
