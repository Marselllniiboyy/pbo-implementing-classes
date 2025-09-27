// PBO[package]: Menentukan paket tempat entitas domain ini berada.
package domain.entity;

import java.math.BigDecimal;

/**
 * Entitas yang merepresentasikan tipe kartu bank dengan berbagai fitur dan batasan.
 * 
 * <p>Record ini mendefinisikan karakteristik tipe kartu termasuk biaya bulanan,
 * batasan transaksi harian, dan saldo minimum yang diperlukan.</p>
 * 
 * @param id ID unik tipe kartu dalam sistem
 * @param name Nama tipe kartu (Diamond, Gold, Silver)
 * @param description Deskripsi dan target pengguna untuk tipe kartu ini
 * @param monthlyPrice Biaya bulanan yang dikenakan untuk kartu ini
 * @param dailyTransferLimit Batas maksimal transfer harian via kartu
 * @param dailyWithdrawLimit Batas maksimal penarikan harian via kartu
 * @param dailyDepositLimit Batas maksimal setoran harian via kartu
 * @param minimumBalance Saldo minimum yang harus dipertahankan di rekening
 * 
 * @author Gede Dhanu Purnayasa
 * @author Made Marsel Biliana Wijaya
 * @since 1.0
 */
// PBO[record]: CardTypeEntity adalah record immutable yang menyimpan data tipe kartu beserta batasan dan biaya yang berlaku.
public record CardTypeEntity(int id, String name, String description, BigDecimal monthlyPrice,
                             BigDecimal dailyTransferLimit, BigDecimal dailyWithdrawLimit, BigDecimal dailyDepositLimit,
                             BigDecimal minimumBalance) {

}
