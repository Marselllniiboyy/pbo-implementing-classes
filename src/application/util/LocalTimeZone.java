package application.util;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * Utility class untuk mengelola waktu dan timezone dalam sistem perbankan.
 * 
 * <p>Class ini menyediakan method untuk mendapatkan waktu saat ini dan tanggal
 * dalam timezone Asia/Makassar (WITA) yang digunakan di Indonesia.</p>
 * 
 * @author Gede Dhanu Purnayasa
 * @author Made Marsel Biliana Wijaya
 * @since 1.0
 */
public class LocalTimeZone {
    
    /**
     * Mendapatkan timestamp saat ini dalam epoch seconds (timezone Asia/Makassar).
     * 
     * <p>Method ini mengembalikan waktu saat ini dalam format epoch seconds
     * yang dapat digunakan untuk pencatatan transaksi dan logging.</p>
     * 
     * @return timestamp saat ini dalam epoch seconds
     */
    public static long getNow() {
        return ZonedDateTime.now(ZoneId.of("Asia/Makassar")).toEpochSecond();
    }

    /**
     * Mendapatkan tanggal saat ini dalam format string YYYY-MM-DD.
     * 
     * <p>Method ini mengembalikan tanggal saat ini dalam format ISO 8601
     * yang dapat digunakan untuk pencatatan transaksi harian.</p>
     * 
     * @return tanggal saat ini dalam format YYYY-MM-DD
     */
    public static String getDate() {
        return LocalDate.now().toString();
    }
}
