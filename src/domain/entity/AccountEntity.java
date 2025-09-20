package domain.entity;

import application.util.CurrencyFormatter;
import domain.value.AccountType;

import java.math.BigDecimal;

/**
 * Entitas yang merepresentasikan rekening bank nasabah.
 * 
 * <p>Record ini menyimpan informasi rekening bank termasuk saldo, tipe akun,
 * dan batasan transaksi harian yang berlaku untuk rekening tersebut.</p>
 * 
 * @param id ID unik rekening dalam sistem
 * @param accountNumber Nomor rekening bank yang unik (10 digit)
 * @param balance Saldo saat ini dalam rekening
 * @param accountType Tipe rekening (SAVINGS, GIRO, TIME_DEPOSIT, CREDIT, BUSINESS)
 * @param customerId ID nasabah pemilik rekening
 * @param dailyTransferLimit Batas maksimal transfer harian untuk rekening ini
 * @param dailyWithdrawLimit Batas maksimal penarikan harian untuk rekening ini
 * 
 * @author Gede Dhanu Purnayasa
 * @author Made Marsel Biliana Wijaya
 * @since 1.0
 */
public record AccountEntity(int id, String accountNumber, BigDecimal balance, AccountType accountType, int customerId,
                            BigDecimal dailyTransferLimit, BigDecimal dailyWithdrawLimit) {

    /**
     * Mengembalikan saldo rekening dalam format Rupiah Indonesia.
     * 
     * @return String saldo yang diformat dengan "Rp" dan pemisah ribuan
     * 
     * @example
     * <pre>
     * AccountEntity account = new AccountEntity(1, "1234567890", new BigDecimal("1000000"), ...);
     * String formattedBalance = account.getFormattedBalance();
     * // Output: "Rp 1.000.000"
     * </pre>
     */
    public String getFormattedBalance() {
        return CurrencyFormatter.format(balance);
    }

    /**
     * Mengembalikan saldo rekening dalam format Rupiah Indonesia dengan desimal.
     * 
     * @return String saldo yang diformat dengan "Rp", pemisah ribuan, dan 2 desimal
     * 
     * @example
     * <pre>
     * AccountEntity account = new AccountEntity(1, "1234567890", new BigDecimal("1000000.50"), ...);
     * String formattedBalance = account.getFormattedBalanceWithDecimals();
     * // Output: "Rp 1.000.000,50"
     * </pre>
     */
    public String getFormattedBalanceWithDecimals() {
        return CurrencyFormatter.formatWithDecimals(balance);
    }
}
