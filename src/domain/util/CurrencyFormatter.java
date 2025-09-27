// PBO[package]: Menentukan paket tempat utilitas domain ini berada
// Dalam OOP, pemisahan package membantu modularitas & keteraturan kode.
package domain.util;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * PBO[utility]: Utility class untuk formatting mata uang Rupiah Indonesia.
 * Utility class untuk formatting mata uang Rupiah Indonesia.
 * 
 * <p>Class ini menyediakan method-method untuk memformat nilai BigDecimal
 * menjadi string Rupiah dengan format yang konsisten di seluruh aplikasi.</p>
 * 
 * @author Gede Dhanu Purnayasa
 * @author Made Marsel Biliana Wijaya
 * @since 1.0
 */

// PBO[class]: CurrencyFormatter adalah class utilitas final (tidak bisa diturunkan).
// Final class di OOP = mencegah inheritance supaya perilaku tidak diubah subclass.
// Utility class = kumpulan fungsi statis, tidak boleh diinstansiasi.
public final class CurrencyFormatter {
    
    /**
     * PBO[field-static-final]: Konstanta static final untuk menyimpan formatter.
     * Formatter untuk menampilkan mata uang dalam format Rupiah Indonesia.
     * Menggunakan format "Rp 1.234.567" dengan pemisah ribuan.
     */
    private static final NumberFormat RUPIAH_FORMATTER = NumberFormat.getNumberInstance(Locale.of("id", "ID"));
    
    /**
     * // PBO[field-static-final]: Formatter untuk Rupiah dengan desimal.
     * Formatter untuk menampilkan mata uang dengan desimal.
     * Menggunakan format "Rp 1.234.567,50" dengan pemisah ribuan dan desimal.
     */
    private static final NumberFormat RUPIAH_FORMATTER_WITH_DECIMALS = NumberFormat.getNumberInstance(Locale.of("id", "ID"));

    // PBO[static block]: Blok inisialisasi statis yang mengatur jumlah digit desimal pada formatter dengan desimal agar selalu 2 digit.
    static {
        RUPIAH_FORMATTER_WITH_DECIMALS.setMinimumFractionDigits(2);
        RUPIAH_FORMATTER_WITH_DECIMALS.setMaximumFractionDigits(2);
    }

    // PBO[constructor]: Constructor privat untuk mencegah pembuatan instance CurrencyFormatter (utility class dipakai lewat metode statis).
    private CurrencyFormatter() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }
    
    /**
     * Mengembalikan nilai BigDecimal dalam format Rupiah Indonesia.
     * 
     * @param amount nilai yang akan diformat
     * @return String nilai yang diformat dengan "Rp" dan pemisah ribuan
     * 
     * @example
     * <pre>
     * String formatted = CurrencyFormatter.format(1000000);
     * // Output: "Rp 1.000.000"
     * </pre>
     */
    public static String format(BigDecimal amount) {
        if (amount == null) {
            return "Rp 0";
        }
        return "Rp " + RUPIAH_FORMATTER.format(amount);
    }
    
    /**
     * Mengembalikan nilai BigDecimal dalam format Rupiah Indonesia dengan desimal.
     * 
     * @param amount nilai yang akan diformat
     * @return String nilai yang diformat dengan "Rp", pemisah ribuan, dan 2 desimal
     * 
     * @example
     * <pre>
     * String formatted = CurrencyFormatter.formatWithDecimals(1000000.50);
     * // Output: "Rp 1.000.000,50"
     * </pre>
     */
    public static String formatWithDecimals(BigDecimal amount) {
        if (amount == null) {
            return "Rp 0,00";
        }
        return "Rp " + RUPIAH_FORMATTER_WITH_DECIMALS.format(amount);
    }
    
    /**
     * // PBO[static-method]: Memformat BigDecimal menjadi string Rupiah dengan pemisah ribuan dan prefix "Rp".
     * Mengembalikan nilai double dalam format Rupiah Indonesia.
     * 
     * @param amount nilai yang akan diformat
     * @return String nilai yang diformat dengan "Rp" dan pemisah ribuan
     * 
     * @example
     * <pre>
     * String formatted = CurrencyFormatter.format(1000000.0);
     * // Output: "Rp 1.000.000"
     * </pre>
     */

    public static String format(double amount) {
        return format(BigDecimal.valueOf(amount));
    }
    
    /**
     * Mengembalikan nilai long dalam format Rupiah Indonesia.
     * 
     * @param amount nilai yang akan diformat
     * @return String nilai yang diformat dengan "Rp" dan pemisah ribuan
     * 
     * @example
     * <pre>
     * String formatted = CurrencyFormatter.format(1000000L);
     * // Output: "Rp 1.000.000"
     * </pre>
     */
    public static String format(long amount) {
        return format(BigDecimal.valueOf(amount));
    }
    
    /**
     * Mengembalikan nilai int dalam format Rupiah Indonesia.
     * 
     * @param amount nilai yang akan diformat
     * @return String nilai yang diformat dengan "Rp" dan pemisah ribuan
     * 
     * @example
     * <pre>
     * String formatted = CurrencyFormatter.format(1000000);
     * // Output: "Rp 1.000.000"
     * </pre>
     */
    public static String format(int amount) {
        return format(BigDecimal.valueOf(amount));
    }
    
    /**
     * Mengembalikan nilai BigDecimal dalam format Rupiah Indonesia tanpa prefix "Rp".
     * Berguna untuk konteks tertentu yang sudah menyebutkan mata uang.
     * 
     * @param amount nilai yang akan diformat
     * @return String nilai yang diformat dengan pemisah ribuan saja
     * 
     * @example
     * <pre>
     * String formatted = CurrencyFormatter.formatWithoutPrefix(1000000);
     * // Output: "1.000.000"
     * </pre>
     */
    public static String formatWithoutPrefix(BigDecimal amount) {
        if (amount == null) {
            return "0";
        }
        return RUPIAH_FORMATTER.format(amount);
    }
    
    /**
     * Mengembalikan nilai BigDecimal dalam format Rupiah Indonesia dengan desimal tanpa prefix "Rp".
     * 
     * @param amount nilai yang akan diformat
     * @return String nilai yang diformat dengan pemisah ribuan dan desimal saja
     * 
     * @example
     * <pre>
     * String formatted = CurrencyFormatter.formatWithDecimalsWithoutPrefix(1000000.50);
     * // Output: "1.000.000,50"
     * </pre>
     */
    public static String formatWithDecimalsWithoutPrefix(BigDecimal amount) {
        if (amount == null) {
            return "0,00";
        }
        return RUPIAH_FORMATTER_WITH_DECIMALS.format(amount);
    }
}
