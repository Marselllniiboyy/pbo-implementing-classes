package domain.repository;

import domain.entity.AccountCardEntity;

import java.util.List;
import java.util.Optional;

/**
 * Interface repository untuk mengelola data kartu ATM/Debit dalam sistem perbankan.
 * 
 * <p>Interface ini mendefinisikan operasi CRUD (Create, Read, Update, Delete)
 * untuk entitas AccountCardEntity dan mengikuti pola Repository Pattern.
 * Menyediakan metode pencarian berdasarkan ID dan ID rekening.</p>
 * 
 * @author Gede Dhanu Purnayasa
 * @author Made Marsel Biliana Wijaya
 * @since 1.0
 */
public interface AccountCardRepository {
    
    /**
     * Mencari kartu berdasarkan ID.
     * 
     * @param id ID kartu yang dicari
     * @return Optional yang berisi AccountCardEntity jika ditemukan, atau empty jika tidak
     */
    Optional<AccountCardEntity> findById(int id);

    /**
     * Mencari kartu berdasarkan ID rekening.
     * 
     * @param accountId ID rekening yang terkait dengan kartu
     * @return Optional yang berisi AccountCardEntity jika ditemukan, atau empty jika tidak
     */
    Optional<AccountCardEntity> findByAccountId(int accountId);

    /**
     * Mengambil semua data kartu yang tersimpan.
     * 
     * @return List berisi semua AccountCardEntity yang tersimpan
     */
    List<AccountCardEntity> findAll();

    /**
     * Menyimpan kartu baru ke dalam repository.
     * 
     * @param account AccountCardEntity yang akan disimpan
     * @return AccountCardEntity yang sudah disimpan dengan ID yang sudah di-assign
     */
    AccountCardEntity save(AccountCardEntity account);

    /**
     * Memperbarui data kartu yang sudah ada.
     * 
     * @param account AccountCardEntity dengan data yang sudah diperbarui
     * @return AccountCardEntity yang sudah diperbarui
     * @throws domain.exception.EntityNotFoundException jika kartu tidak ditemukan
     */
    AccountCardEntity update(AccountCardEntity account);

    /**
     * Menghapus kartu berdasarkan ID.
     * 
     * @param id ID kartu yang akan dihapus
     * @return true jika kartu berhasil dihapus, false jika tidak ditemukan
     */
    boolean deleteById(int id);
}
