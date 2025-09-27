package domain.repository;

import domain.entity.CustomerEntity;

import java.util.List;
import java.util.Optional;

/**
 * PBO[repository]: Interface repository untuk mengelola data nasabah dalam sistem perbankan.
 * Interface repository untuk mengelola data nasabah dalam sistem perbankan.
 * 
 * <p>Interface ini mendefinisikan operasi CRUD (Create, Read, Update, Delete)
 * untuk entitas CustomerEntity dan mengikuti pola Repository Pattern.</p>
 * 
 * @author Gede Dhanu Purnayasa
 * @author Made Marsel Biliana Wijaya
 * @since 1.0
 */
// PBO[interface]: CustomerRepository adalah kontrak untuk akses dan pengelolaan entitas nasabah.
public interface CustomerRepository {
    
    /**
     * PBO[method]: Mencari nasabah berdasarkan ID unik.
     * Mencari nasabah berdasarkan ID.
     * 
     * @param id ID nasabah yang dicari
     * @return Optional yang berisi CustomerEntity jika ditemukan, atau empty jika tidak
     */
    Optional<CustomerEntity> findById(int id);

    /**
     * PBO[method]: Mencari nasabah berdasarkan email unik.
     * Mencari nasabah berdasarkan email.
     * 
     * @param email email nasabah yang dicari
     * @return Optional yang berisi CustomerEntity jika ditemukan, atau empty jika tidak
     */
    Optional<CustomerEntity> findByEmail(String email);

    /**
     * PBO[method]: Mengambil semua data nasabah yang tersimpan di repository.
     * Mengambil semua data nasabah yang tersimpan.
     * 
     * @return List berisi semua CustomerEntity yang tersimpan
     */
    List<CustomerEntity> findAll();

    /**
     * PBO[method]: Memperbarui data nasabah yang sudah ada.
     * Memperbarui data nasabah yang sudah ada.
     * 
     * @param customer CustomerEntity dengan data yang sudah diperbarui
     * @return CustomerEntity yang sudah diperbarui
     * @throws domain.exception.EntityNotFoundException jika nasabah tidak ditemukan
     */
    CustomerEntity update(CustomerEntity customer);

    /**
     * PBO[method]: Menyimpan nasabah baru ke dalam repository.
     * Menyimpan nasabah baru ke dalam repository.
     * 
     * @param customer CustomerEntity yang akan disimpan
     * @return CustomerEntity yang sudah disimpan dengan ID yang sudah di-assign
     */
    CustomerEntity save(CustomerEntity customer);

    /**
     * PBO[method]: Menghapus nasabah berdasarkan ID.
     * Menghapus nasabah berdasarkan ID.
     * 
     * @param id ID nasabah yang akan dihapus
     * @return true jika nasabah berhasil dihapus, false jika tidak ditemukan
     */
    boolean deleteById(int id);
}
