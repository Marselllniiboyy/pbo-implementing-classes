// PBO[package]: Menentukan paket tempat repository AccountCard berada.
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
// PBO[interface]: AccountCardRepository adalah kontrak/pola untuk pengelolaan entitas kartu ATM/Debit.
public interface AccountCardRepository {
    
    /**
     * PBO[method]: Mencari kartu berdasarkan ID unik kartu.
     * Mencari kartu berdasarkan ID.
     * 
     * @param id ID kartu yang dicari
     * @return Optional yang berisi AccountCardEntity jika ditemukan, atau empty jika tidak
     */
    Optional<AccountCardEntity> findById(int id);

    /**
     *PBO[method]: Mencari kartu berdasarkan ID rekening yang terkait.
     * Mencari kartu berdasarkan ID rekening.
     * 
     * @param accountId ID rekening yang terkait dengan kartu
     * @return Optional yang berisi AccountCardEntity jika ditemukan, atau empty jika tidak
     */
    Optional<AccountCardEntity> findByAccountId(int accountId);

    /**
     * PBO[method]: Mengambil semua data kartu yang tersimpan dalam repository.
     * Mengambil semua data kartu yang tersimpan.
     * 
     * @return List berisi semua AccountCardEntity yang tersimpan
     */
    List<AccountCardEntity> findAll();

    /**
     * PBO[method]: Menyimpan kartu baru ke dalam repository.
     * Menyimpan kartu baru ke dalam repository.
     * 
     * @param account AccountCardEntity yang akan disimpan
     * @return AccountCardEntity yang sudah disimpan dengan ID yang sudah di-assign
     */
    AccountCardEntity save(AccountCardEntity account);

    /**
     * PBO[method]: Memperbarui data kartu yang sudah ada.
     * Memperbarui data kartu yang sudah ada.
     * 
     * @param account AccountCardEntity dengan data yang sudah diperbarui
     * @return AccountCardEntity yang sudah diperbarui
     * @throws domain.exception.EntityNotFoundException jika kartu tidak ditemukan
     */
    AccountCardEntity update(AccountCardEntity account);

    /**
     * PBO[method]: Menghapus kartu berdasarkan ID.
     * Menghapus kartu berdasarkan ID.
     * 
     * @param id ID kartu yang akan dihapus
     * @return true jika kartu berhasil dihapus, false jika tidak ditemukan
     */
    boolean deleteById(int id);
}
