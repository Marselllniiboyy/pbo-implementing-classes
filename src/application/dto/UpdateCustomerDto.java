package application.dto;

/**
 * Data Transfer Object untuk update informasi nasabah.
 * 
 * <p>DTO ini digunakan untuk mengirim data update nasabah dari presentation layer
 * ke service layer. Semua field bersifat optional untuk memungkinkan partial update.</p>
 * 
 * @author Gede Dhanu Purnayasa
 * @author Made Marsel Biliana Wijaya
 * @since 1.0
 */
public record UpdateCustomerDto(
    /**
     * Nama lengkap nasabah yang akan diupdate.
     * 
     * <p>Jika null, nama tidak akan diupdate. Jika tidak null, 
     * nama akan divalidasi dan diupdate.</p>
     */
    String name,
    
    /**
     * Email nasabah yang akan diupdate.
     * 
     * <p>Jika null, email tidak akan diupdate. Jika tidak null,
     * email akan divalidasi untuk memastikan format yang benar
     * dan tidak duplikat dengan nasabah lain.</p>
     */
    String email,
    
    /**
     * Nomor telepon nasabah yang akan diupdate.
     * 
     * <p>Jika null, nomor telepon tidak akan diupdate. Jika tidak null,
     * nomor telepon akan divalidasi format dan diupdate.</p>
     */
    String phoneNumber,
    
    /**
     * Alamat nasabah yang akan diupdate.
     * 
     * <p>Jika null, alamat tidak akan diupdate. Jika tidak null,
     * alamat akan divalidasi dan diupdate.</p>
     */
    String address,
    
    /**
     * Tanggal lahir nasabah yang akan diupdate.
     * 
     * <p>Jika null, tanggal lahir tidak akan diupdate. Jika tidak null,
     * tanggal lahir akan divalidasi format dan diupdate.</p>
     */
    String dateOfBirth
) {
    /**
     * Memeriksa apakah DTO ini memiliki data yang valid untuk diupdate.
     * 
     * <p>Method ini akan mengembalikan true jika setidaknya ada satu field
     * yang tidak null, yang berarti ada data yang akan diupdate.</p>
     * 
     * @return true jika ada data yang akan diupdate, false jika semua field null
     */
    public boolean hasDataToUpdate() {
        return name != null || email != null || phoneNumber != null || 
               address != null || dateOfBirth != null;
    }
    
    /**
     * Memeriksa apakah DTO ini kosong (semua field null).
     * 
     * @return true jika semua field null, false jika ada field yang tidak null
     */
    public boolean isEmpty() {
        return !hasDataToUpdate();
    }
}
