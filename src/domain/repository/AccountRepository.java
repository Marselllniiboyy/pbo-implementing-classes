// PBO[package]: Menentukan paket tempat repository Account berada.
package domain.repository;

import domain.entity.AccountEntity;

import java.util.List;
import java.util.Optional;

/**
 *  PBO[repository]: Interface repository untuk mengelola data rekening bank dalam sistem perbankan.
 * Interface repository untuk mengelola data rekening bank dalam sistem perbankan.
 * 
 * <p>Interface ini mendefinisikan operasi CRUD (Create, Read, Update, Delete)
 * untuk entitas AccountEntity dan mengikuti pola Repository Pattern.
 * Menyediakan berbagai metode pencarian berdasarkan ID, customer ID, dan nomor rekening.</p>
 * 
 * @author Gede Dhanu Purnayasa
 * @author Made Marsel Biliana Wijaya
 * @since 1.0
 */
// PBO[interface]: AccountRepository adalah kontrak/pola untuk pengelolaan entitas rekening bank.
public interface AccountRepository {
    
    /**
     * PBO[method]: Mencari rekening berdasarkan ID unik rekening.
     * Mencari rekening berdasarkan ID.
     * 
     * @param id ID rekening yang dicari
     * @return Optional yang berisi AccountEntity jika ditemukan, atau empty jika tidak
     */
    Optional<AccountEntity> findById(int id);

    /**
     * PBO[method]: Mencari rekening berdasarkan ID nasabah pemilik rekening.
     * Mencari rekening berdasarkan ID nasabah.
     * 
     * @param customerId ID nasabah pemilik rekening
     * @return Optional yang berisi AccountEntity jika ditemukan, atau empty jika tidak
     */
    Optional<AccountEntity> findByCustomerId(int customerId);

    /**
     * PBO[method]: Mencari rekening berdasarkan nomor rekening bank.
     * Mencari rekening berdasarkan nomor rekening.
     * 
     * @param accountNumber nomor rekening yang dicari (10 digit)
     * @return Optional yang berisi AccountEntity jika ditemukan, atau empty jika tidak
     */
    Optional<AccountEntity> findByAccountNumber(String accountNumber);

    /**
     * PBO[method]: Mengambil semua data rekening yang tersimpan di repository.
     * Mengambil semua data rekening yang tersimpan.
     * 
     * @return List berisi semua AccountEntity yang tersimpan
     */
    List<AccountEntity> findAll();

    /**
     * PBO[method]: Menyimpan rekening baru ke dalam repository.
     * Menyimpan rekening baru ke dalam repository.
     * 
     * @param account AccountEntity yang akan disimpan
     * @return AccountEntity yang sudah disimpan dengan ID yang sudah di-assign
     */
    AccountEntity save(AccountEntity account);

    /**
     * PBO[method]: Memperbarui data rekening yang sudah ada.
     * Memperbarui data rekening yang sudah ada.
     * 
     * @param account AccountEntity dengan data yang sudah diperbarui
     * @return AccountEntity yang sudah diperbarui
     * @throws domain.exception.EntityNotFoundException jika rekening tidak ditemukan
     */
    AccountEntity update(AccountEntity account);

    /**
     * PBO[method]: Menghapus rekening berdasarkan ID.
     * Menghapus rekening berdasarkan ID.
     * 
     * @param id ID rekening yang akan dihapus
     * @return true jika rekening berhasil dihapus, false jika tidak ditemukan
     */
    boolean deleteById(int id);
}
