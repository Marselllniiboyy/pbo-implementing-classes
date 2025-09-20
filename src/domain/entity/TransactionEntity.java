package domain.entity;

import domain.value.TransactionType;

import java.math.BigDecimal;

/**
 * Entitas yang merepresentasikan transaksi keuangan dalam sistem perbankan.
 * 
 * <p>Record ini menyimpan informasi lengkap tentang setiap transaksi termasuk
 * akun sumber, akun tujuan (jika ada), jumlah, tipe transaksi, dan timestamp.</p>
 * 
 * @param id ID unik transaksi dalam sistem
 * @param accountId ID rekening sumber transaksi
 * @param destinationAccountId ID rekening tujuan (null untuk transaksi non-transfer)
 * @param amount Jumlah transaksi dalam mata uang lokal
 * @param transactionType Tipe transaksi (TRANSFER, DEPOSIT, WITHDRAW, dll)
 * @param date Tanggal transaksi dalam format YYYY-MM-DD
 * @param timestamp Timestamp transaksi dalam epoch seconds (timezone Asia/Makassar)
 * 
 * @author Gede Dhanu Purnayasa
 * @author Made Marsel Biliana Wijaya
 * @since 1.0
 */
public record TransactionEntity(int id, int accountId, Integer destinationAccountId, BigDecimal amount,
                                TransactionType transactionType, String date, long timestamp) {
}
