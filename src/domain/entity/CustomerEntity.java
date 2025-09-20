package domain.entity;

/**
 * Entitas yang merepresentasikan data nasabah bank.
 * 
 * <p>Record ini menyimpan informasi lengkap tentang nasabah termasuk
 * identitas pribadi dan kontak yang diperlukan untuk layanan perbankan.</p>
 * 
 * @param id ID unik nasabah dalam sistem
 * @param name Nama lengkap nasabah
 * @param email Alamat email nasabah untuk komunikasi dan notifikasi
 * @param phoneNumber Nomor telepon nasabah untuk verifikasi dan kontak
 * @param address Alamat lengkap tempat tinggal nasabah
 * @param dateOfBirth Tanggal lahir nasabah dalam format YYYY-MM-DD
 * 
 * @author Gede Dhanu Purnayasa
 * @author Made Marsel Biliana Wijaya
 * @since 1.0
 */
public record CustomerEntity(int id, String name, String email, String phoneNumber, String address,
                             String dateOfBirth) {

}