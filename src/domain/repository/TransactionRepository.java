package domain.repository;

import domain.entity.TransactionEntity;

import java.util.List;
import java.util.Optional;

/**
 * PBO[repository]: Interface repository untuk mengelola data transaksi keuangan dalam sistem perbankan.
 * Interface repository untuk mengelola data transaksi keuangan dalam sistem perbankan.
 *
 * <p>Interface ini mendefinisikan operasi CRUD (Create, Read, Update, Delete)
 * untuk entitas TransactionEntity dan mengikuti pola Repository Pattern.
 * Menyediakan metode khusus untuk pencarian transaksi berdasarkan akun dan tanggal.</p>
 *
 * @author Gede Dhanu Purnayasa
 * @author Made Marsel Biliana Wijaya
 * @since 1.0
 */
// PBO[interface]: TransactionRepository adalah kontrak untuk akses dan pengelolaan entitas transaksi bank.
public interface TransactionRepository {

    /**
     * PBO[method]: Mencari transaksi berdasarkan ID unik.
     * Mencari transaksi berdasarkan ID.
     *
     * @param id ID transaksi yang dicari
     * @return Optional yang berisi TransactionEntity jika ditemukan, atau empty jika tidak
     */
    Optional<TransactionEntity> findById(int id);

    /**
     *  PBO[method]: Mencari transaksi berdasarkan ID akun dan tanggal tertentu.
     * Mencari transaksi berdasarkan ID akun dan tanggal tertentu.
     *
     * @param accountId ID akun yang terkait dengan transaksi
     * @param date tanggal transaksi dalam format YYYY-MM-DD
     * @return List berisi semua TransactionEntity untuk akun dan tanggal tersebut
     */
    List<TransactionEntity> findByAccountIdWithDate(int accountId, String date);

    /**
     * PBO[method]: Mengambil semua data transaksi yang tersimpan di repository.
     * Mengambil semua data transaksi yang tersimpan.
     *
     * @return List berisi semua TransactionEntity yang tersimpan
     */
    List<TransactionEntity> findAll();

    /**
     * PBO[method]: Memperbarui data transaksi yang sudah ada.
     * Memperbarui data transaksi yang sudah ada.
     *
     * @param customer TransactionEntity dengan data yang sudah diperbarui
     * @return TransactionEntity yang sudah diperbarui
     * @throws domain.exception.EntityNotFoundException jika transaksi tidak ditemukan
     */
    TransactionEntity update(TransactionEntity customer);

    /**
     * PBO[method]: Menyimpan transaksi baru ke dalam repository.
     * Menyimpan transaksi baru ke dalam repository.
     *
     * @param customer TransactionEntity yang akan disimpan
     * @return TransactionEntity yang sudah disimpan dengan ID yang sudah di-assign
     */
    TransactionEntity save(TransactionEntity customer);

    /**
     * PBO[method]: Menghapus transaksi berdasarkan ID.
     * Menghapus transaksi berdasarkan ID.
     *
     * @param id ID transaksi yang akan dihapus
     * @return true jika transaksi berhasil dihapus, false jika tidak ditemukan
     */
    boolean deleteById(int id);
}
