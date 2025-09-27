// PBO[package]: Menentukan paket tempat repository CardType berada.
package domain.repository;

import domain.entity.CardTypeEntity;

import java.util.List;
import java.util.Optional;

/**
 * PBO[repository]: Interface repository untuk mengelola data tipe kartu dalam sistem perbankan.
 * Interface repository untuk mengelola data tipe kartu dalam sistem perbankan.
 * 
 * <p>Interface ini mendefinisikan operasi CRUD (Create, Read, Update, Delete)
 * untuk entitas CardTypeEntity dan mengikuti pola Repository Pattern.
 * Menyediakan metode untuk mengelola berbagai tipe kartu seperti Diamond, Gold, dan Silver.</p>
 * 
 * @author Gede Dhanu Purnayasa
 * @author Made Marsel Biliana Wijaya
 * @since 1.0
 */
// PBO[interface]: CardTypeRepository adalah kontrak untuk akses dan pengelolaan entitas tipe kartu.
public interface CardTypeRepository {
    
    /**
     * PBO[method]: Mencari tipe kartu berdasarkan ID unik.
     * Mencari tipe kartu berdasarkan ID.
     * 
     * @param id ID tipe kartu yang dicari
     * @return Optional yang berisi CardTypeEntity jika ditemukan, atau empty jika tidak
     */
    Optional<CardTypeEntity> findById(int id);

    /**
     * PBO[method]: Mengambil semua data tipe kartu yang tersimpan di repository.
     * Mengambil semua data tipe kartu yang tersimpan.
     * 
     * @return List berisi semua CardTypeEntity yang tersimpan
     */
    List<CardTypeEntity> findAll();

    /**
     * PBO[method]: Menyimpan tipe kartu baru ke dalam repository.
     * Menyimpan tipe kartu baru ke dalam repository.
     * 
     * @param account CardTypeEntity yang akan disimpan
     * @return CardTypeEntity yang sudah disimpan dengan ID yang sudah di-assign
     */
    CardTypeEntity save(CardTypeEntity account);

    /**
     * PBO[method]: Memperbarui data tipe kartu yang sudah ada.
     * Memperbarui data tipe kartu yang sudah ada.
     * 
     * @param account CardTypeEntity dengan data yang sudah diperbarui
     * @return CardTypeEntity yang sudah diperbarui
     * @throws domain.exception.EntityNotFoundException jika tipe kartu tidak ditemukan
     */
    CardTypeEntity update(CardTypeEntity account);

    /**
     * PBO[method]: Menghapus tipe kartu berdasarkan ID.
     * Menghapus tipe kartu berdasarkan ID.
     * 
     * @param id ID tipe kartu yang akan dihapus
     * @return true jika tipe kartu berhasil dihapus, false jika tidak ditemukan
     */
    boolean deleteById(int id);
}
