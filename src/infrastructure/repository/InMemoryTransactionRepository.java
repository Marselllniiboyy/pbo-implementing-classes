// PBO[package]: Menentukan paket infrastructure.repository agar implementasi repository ini dapat digunakan di lapisan infrastruktur
package infrastructure.repository;

import domain.entity.TransactionEntity; // PBO[import]: Mengimpor entitas transaksi untuk dimanipulasi oleh repository ini
import domain.exception.EntityNotFoundException; // PBO[import]: Mengimpor exception untuk dilempar jika data tidak ditemukan saat update
import domain.repository.TransactionRepository; // PBO[import]: Mengimpor interface repository agar kelas ini sesuai kontrak repository

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger; // PBO[import]: Variabel counter thread-safe untuk menghasilkan ID unik secara otomatis

/**
 * PBO[class]: Implementasi in-memory untuk TransactionRepository.
 *
 * <p>Kelas ini menyimpan data transaksi di dalam memori menggunakan List.
 * Cocok untuk pengujian (unit test) atau prototipe tanpa database.
 * ID baru akan di-generate otomatis menggunakan AtomicInteger.</p>
 *
 * @since 1.0
 * @author
 *  Gede Dhanu Purnayasa
 *  Made Marsel Biliana Wijaya
 */
public class InMemoryTransactionRepository implements TransactionRepository {
    // PBO[field]: List sinkron untuk menyimpan data transaksi di memori agar aman diakses multi-thread
    private final List<TransactionEntity> transactions = Collections.synchronizedList(new ArrayList<>());
    // PBO[field]: Counter ID otomatis thread-safe untuk memberi ID unik pada entitas baru
    private final AtomicInteger idCounter = new AtomicInteger(0);

    /**
     * PBO[method]: Mencari transaksi berdasarkan ID unik.
     *
     * @param id ID transaksi yang dicari
     * @return Optional berisi TransactionEntity jika ditemukan, kosong jika tidak
     */
    @Override
    public Optional<TransactionEntity> findById(int id) {
        return transactions.stream()
                .filter(transaction -> transaction.id() == id)
                .findFirst();
    }

    /**
     * PBO[method]: Mencari transaksi berdasarkan ID akun dan tanggal tertentu.
     *
     * @param accountId ID akun pemilik transaksi
     * @param date tanggal transaksi (format String)
     * @return List berisi TransactionEntity yang sesuai kriteria
     */
    @Override
    public List<TransactionEntity> findByAccountIdWithDate(int accountId, String date) {
        return transactions.stream()
                .filter(
                        transaction -> transaction.accountId() == accountId
                                && Objects.equals(transaction.date(), date)
                )
                .toList();
    }

    /**
     * PBO[method]: Mengambil semua data transaksi yang tersimpan di memori.
     *
     * @return List baru berisi semua TransactionEntity yang ada
     */
    @Override
    public List<TransactionEntity> findAll() {
        return new ArrayList<>(transactions);
    }

    /**
     * PBO[method]: Memperbarui data transaksi yang sudah ada di repository.
     *
     * @param transaction TransactionEntity dengan data yang diperbarui
     * @return TransactionEntity yang sudah diperbarui
     * @throws EntityNotFoundException jika ID transaksi tidak ditemukan
     */
    @Override
    public TransactionEntity update(TransactionEntity transaction) {
        for (int i = 0; i < transactions.size(); i++) {
            if (transactions.get(i).id() == transaction.id()) {
                transactions.set(i, transaction);
                return transaction;
            }
        }

        throw new EntityNotFoundException("Akun tidak ditemukan");
    }

    /**
     * PBO[method]: Menyimpan transaksi baru ke repository dengan ID baru otomatis.
     *
     * @param transaction TransactionEntity yang akan disimpan (tanpa ID)
     * @return TransactionEntity yang sudah disimpan dengan ID yang sudah di-assign
     */
    @Override
    public TransactionEntity save(TransactionEntity transaction) {
        int newId = idCounter.incrementAndGet();
        TransactionEntity insertionEntity = new TransactionEntity(
                newId,
                transaction.accountId(),
                transaction.destinationAccountId(),
                transaction.amount(),
                transaction.transactionType(),
                transaction.date(),
                transaction.timestamp()
        );
        transactions.add(insertionEntity);
        return insertionEntity;
    }
    /**
     * PBO[method]: Menghapus transaksi berdasarkan ID unik.
     *
     * @param id ID transaksi yang akan dihapus
     * @return true jika berhasil dihapus, false jika tidak ditemukan
     */
    @Override
    public boolean deleteById(int id) {
        return transactions.removeIf(storedTransaction -> storedTransaction.id() == id);
    }
}
