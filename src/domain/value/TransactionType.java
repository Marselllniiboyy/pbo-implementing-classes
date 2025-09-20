package domain.value;

/**
 * Enum yang merepresentasikan berbagai jenis transaksi keuangan dalam sistem perbankan.
 * 
 * <p>Setiap tipe transaksi memiliki karakteristik dan aturan yang berbeda
 * dalam hal biaya, batasan, dan cara pelaksanaan.</p>
 * 
 * @author Gede Dhanu Purnayasa
 * @author Made Marsel Biliana Wijaya
 * @since 1.0
 */
public enum TransactionType {
    /**
     * Biaya bulanan kartu - biaya yang dikenakan setiap bulan untuk kartu
     */
    MONTHLY_CHARGE,
    
    /**
     * Transfer via teller - transfer uang antar rekening melalui teller bank
     */
    TRANSFER,
    
    /**
     * Setoran via teller - menyimpan uang ke rekening melalui teller bank
     */
    DEPOSIT,
    
    /**
     * Penarikan via teller - mengambil uang dari rekening melalui teller bank
     */
    WITHDRAW,
    
    /**
     * Transfer via kartu - transfer uang antar rekening menggunakan kartu ATM/Debit
     */
    TRANSFER_VIA_CARD,
    
    /**
     * Setoran via kartu - menyimpan uang ke rekening menggunakan kartu ATM/Debit
     */
    DEPOSIT_VIA_CARD,
    
    /**
     * Penarikan via kartu - mengambil uang dari rekening menggunakan kartu ATM/Debit
     */
    WITHDRAW_VIA_CARD,
}
