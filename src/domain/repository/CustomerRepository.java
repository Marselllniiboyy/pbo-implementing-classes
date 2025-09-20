package domain.repository;

import domain.entity.CustomerEntity;

import java.util.List;
import java.util.Optional;

/**
 * Interface repository untuk mengelola data nasabah dalam sistem perbankan.
 * 
 * <p>Interface ini mendefinisikan operasi CRUD (Create, Read, Update, Delete)
 * untuk entitas CustomerEntity dan mengikuti pola Repository Pattern.</p>
 * 
 * @author Gede Dhanu Purnayasa
 * @author Made Marsel Biliana Wijaya
 * @since 1.0
 */
public interface CustomerRepository {
    
    /**
     * Mencari nasabah berdasarkan ID.
     * 
     * @param id ID nasabah yang dicari
     * @return Optional yang berisi CustomerEntity jika ditemukan, atau empty jika tidak
     */
    Optional<CustomerEntity> findById(int id);

    /**
     * Mencari nasabah berdasarkan email.
     * 
     * @param email email nasabah yang dicari
     * @return Optional yang berisi CustomerEntity jika ditemukan, atau empty jika tidak
     */
    Optional<CustomerEntity> findByEmail(String email);

    /**
     * Mengambil semua data nasabah yang tersimpan.
     * 
     * @return List berisi semua CustomerEntity yang tersimpan
     */
    List<CustomerEntity> findAll();

    /**
     * Memperbarui data nasabah yang sudah ada.
     * 
     * @param customer CustomerEntity dengan data yang sudah diperbarui
     * @return CustomerEntity yang sudah diperbarui
     * @throws domain.exception.EntityNotFoundException jika nasabah tidak ditemukan
     */
    CustomerEntity update(CustomerEntity customer);

    /**
     * Menyimpan nasabah baru ke dalam repository.
     * 
     * @param customer CustomerEntity yang akan disimpan
     * @return CustomerEntity yang sudah disimpan dengan ID yang sudah di-assign
     */
    CustomerEntity save(CustomerEntity customer);

    /**
     * Menghapus nasabah berdasarkan ID.
     * 
     * @param id ID nasabah yang akan dihapus
     * @return true jika nasabah berhasil dihapus, false jika tidak ditemukan
     */
    boolean deleteById(int id);
}
