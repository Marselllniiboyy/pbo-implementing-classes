package application.dto;

/**
 * Data Transfer Object (DTO) untuk membuat nasabah baru.
 * 
 * <p>Record ini digunakan untuk mengirim data nasabah dari presentation layer
 * ke service layer dalam operasi pembuatan nasabah baru.</p>
 * 
 * @param name nama lengkap nasabah
 * @param email alamat email nasabah untuk komunikasi dan notifikasi
 * @param phoneNumber nomor telepon nasabah untuk verifikasi dan kontak
 * @param address alamat lengkap tempat tinggal nasabah
 * @param dateOfBirth tanggal lahir nasabah dalam format YYYY-MM-DD
 * 
 * @author Gede Dhanu Purnayasa
 * @author Made Marsel Biliana Wijaya
 * @since 1.0
 */
public record CreateCustomerDto(String name, String email, String phoneNumber, String address, String dateOfBirth) {
}
