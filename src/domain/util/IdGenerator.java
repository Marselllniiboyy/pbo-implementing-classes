package domain.util;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Utility class untuk menangani pembuatan ID pada setiap komponen aplikasi
 *
 * <p>Class ini menyediakan method untuk menangani berbagai jenis tools untuk
 * membuat identifier unik sesuai dengan format yang dibutuhkan.</p>
 *
 * @author Gede Dhanu Purnayasa
 * @author Made Marsel Biliana Wijaya
 * @since 1.0
 */
public class IdGenerator {
    /**
     * Menghasilkan nomor kartu ATM/Debit yang unik.
     *
     * <p>Method ini menggunakan ThreadLocalRandom untuk menghasilkan
     * nomor kartu 10 digit yang unik dalam sistem.</p>
     *
     * @return String berisi nomor kartu 10 digit
     */
    public static String generateCardNumber() {
        long min = 1_000_000_000L;

        // The upper bound (exclusive), which is the smallest 11-digit number
        long max = 10_000_000_000L;

        return Long.toString(ThreadLocalRandom.current().nextLong(min, max));
    }

    /**
     * Menghasilkan nomor rekening bank yang unik.
     *
     * <p>Method ini menggunakan ThreadLocalRandom untuk menghasilkan
     * nomor rekening 10 digit yang unik dalam sistem.</p>
     *
     * @return String berisi nomor rekening 10 digit
     */
    public static String generateAccountNumber() {
        long min = 1_000_000_000L;

        // The upper bound (exclusive), which is the smallest 11-digit number
        long max = 10_000_000_000L;

        return Long.toString(ThreadLocalRandom.current().nextLong(min, max));
    }
}
