// PBO[package]: Menentukan paket tempat entitas domain ini berada
package domain.entity;

/**
 * Entitas yang merepresentasikan kartu ATM/Debit yang terkait dengan rekening.
 * 
 * <p>Record ini menyimpan informasi kartu bank termasuk nomor kartu, PIN,
 * tipe kartu, status aktif, dan tanggal kedaluwarsa.</p>
 * 
 * @param id ID unik kartu dalam sistem
 * @param accountId ID rekening yang terkait dengan kartu ini
 * @param cardNumber Nomor kartu ATM/Debit (10 digit)
 * @param pin Personal Identification Number untuk autentikasi
 * @param cardTypeId ID tipe kartu (Diamond, Gold, Silver)
 * @param active Status aktif kartu (true = aktif, false = non-aktif)
 * @param expiredDate Tanggal kedaluwarsa kartu dalam format YYYY-MM-DD
 * 
 * @author Gede Dhanu Purnayasa
 * @author Made Marsel Biliana Wijaya
 * @since 1.0
 */

// PBO[record]: Mendeklarasikan record AccountCardEntity untuk menyimpan data kartu bank secara immutable
public record AccountCardEntity(int id, int accountId, String cardNumber, int pin, int cardTypeId, boolean active,
                                String expiredDate) {

}
