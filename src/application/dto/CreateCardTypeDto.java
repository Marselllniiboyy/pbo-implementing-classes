package application.dto;

import java.math.BigDecimal;

/**
 * Data Transfer Object (DTO) untuk membuat tipe kartu baru.
 * 
 * <p>Record ini digunakan untuk mengirim data tipe kartu dari presentation layer
 * ke service layer dalam operasi pembuatan tipe kartu baru seperti Diamond, Gold, Silver.</p>
 * 
 * @param name nama tipe kartu (Diamond, Gold, Silver)
 * @param description deskripsi dan target pengguna untuk tipe kartu ini
 * @param monthlyPrice biaya bulanan yang dikenakan untuk kartu ini
 * @param dailyTransferLimit batas maksimal transfer harian via kartu
 * @param dailyWithdrawLimit batas maksimal penarikan harian via kartu
 * @param dailyDepositLimit batas maksimal setoran harian via kartu
 * @param minimumBalance saldo minimum yang harus dipertahankan di rekening
 * 
 * @author Gede Dhanu Purnayasa
 * @author Made Marsel Biliana Wijaya
 * @since 1.0
 */
public record CreateCardTypeDto(String name, String description, BigDecimal monthlyPrice, BigDecimal dailyTransferLimit,
                                BigDecimal dailyWithdrawLimit, BigDecimal dailyDepositLimit,
                                BigDecimal minimumBalance) {
}
