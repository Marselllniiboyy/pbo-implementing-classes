package domain.value;

/**
 * Enum yang merepresentasikan berbagai tipe rekening bank yang tersedia.
 * 
 * <p>Setiap tipe rekening memiliki karakteristik dan aturan yang berbeda
 * dalam hal bunga, biaya, dan batasan transaksi.</p>
 * 
 * @author Gede Dhanu Purnayasa
 * @author Made Marsel Biliana Wijaya
 * @since 1.0
 */
public enum AccountType {
    /**
     * Rekening tabungan - rekening dasar untuk menyimpan uang dengan bunga rendah
     */
    SAVINGS,
    
    /**
     * Rekening giro - rekening untuk transaksi bisnis dengan fasilitas cek
     */
    GIRO,
    
    /**
     * Deposito berjangka - rekening dengan bunga tinggi dan jangka waktu tertentu
     */
    TIME_DEPOSIT,
    
    /**
     * Rekening kredit - rekening dengan fasilitas pinjaman
     */
    CREDIT,
    
    /**
     * Rekening bisnis - rekening khusus untuk perusahaan dengan fitur khusus
     */
    BUSINESS,
}
