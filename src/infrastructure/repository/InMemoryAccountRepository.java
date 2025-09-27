// PBO[package]: Menentukan paket infrastructure.repository agar implementasi repository ini dapat digunakan di lapisan infrastruktur
package infrastructure.repository;

import domain.entity.AccountEntity; // PBO[import]: Mengimpor entitas rekening untuk dimanipulasi oleh repository ini
import domain.exception.EntityNotFoundException; // PBO[import]: Mengimpor exception untuk dilempar jika data tidak ditemukan saat update
import domain.repository.AccountRepository; // PBO[import]: Mengimpor interface repository agar kelas ini sesuai kontrak repository

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger; // PBO[import]: Variabel counter thread-safe untuk menghasilkan ID unik secara otomatis

/**
 * PBO[class]: Implementasi in-memory untuk AccountRepository.
 *
 * <p>Kelas ini menyimpan data rekening di dalam memori menggunakan List.
 * Cocok untuk pengujian (unit test) atau prototipe tanpa database.
 * ID baru akan di-generate otomatis menggunakan AtomicInteger.</p>
 *
 * @since 1.0
 * @author
 *  Gede Dhanu Purnayasa
 *  Made Marsel Biliana Wijaya
 */
public class InMemoryAccountRepository implements AccountRepository {
    // PBO[field]: List sinkron untuk menyimpan data rekening di memori agar aman diakses multi-thread
    private final List<AccountEntity> accounts = Collections.synchronizedList(new ArrayList<>());
    // PBO[field]: Counter ID otomatis thread-safe untuk memberi ID unik pada entitas baru
    private final AtomicInteger idCounter = new AtomicInteger(0);

    /**
     * PBO[method]: Mencari rekening berdasarkan ID unik.
     *
     * @param id ID rekening yang dicari
     * @return Optional berisi AccountEntity jika ditemukan, kosong jika tidak
     */
    @Override
    public Optional<AccountEntity> findById(int id) {
        return accounts.stream()
                .filter(account -> account.id() == id)
                .findFirst();
    }

    /**
     * PBO[method]: Mencari rekening berdasarkan ID nasabah.
     *
     * @param customerId ID nasabah pemilik rekening
     * @return Optional berisi AccountEntity jika ditemukan, kosong jika tidak
     */
    @Override
    public Optional<AccountEntity> findByCustomerId(int customerId) {
        return accounts.stream()
                .filter(account -> account.customerId() == customerId)
                .findFirst();
    }

    /**
     * PBO[method]: Mencari rekening berdasarkan nomor rekening.
     *
     * @param accountNumber nomor rekening yang dicari
     * @return Optional berisi AccountEntity jika ditemukan, kosong jika tidak
     */
    @Override
    public Optional<AccountEntity> findByAccountNumber(String accountNumber) {
        return accounts.stream()
                .filter(account -> Objects.equals(account.accountNumber(), accountNumber))
                .findFirst();
    }

    /**
     * PBO[method]: Mengambil semua data rekening yang tersimpan di memori.
     *
     * @return List baru berisi semua AccountEntity yang ada
     */
    @Override
    public List<AccountEntity> findAll() {
        return new ArrayList<>(accounts);
    }

    /**
     * PBO[method]: Menyimpan rekening baru ke repository dengan ID baru otomatis.
     *
     * @param account AccountEntity yang akan disimpan (tanpa ID)
     * @return AccountEntity yang sudah disimpan dengan ID yang sudah di-assign
     */
    @Override
    public AccountEntity save(AccountEntity account) {
        int newId = idCounter.incrementAndGet();
        AccountEntity insertionEntity = new AccountEntity(
                newId,
                account.accountNumber(),
                account.balance(),
                account.accountType(),
                account.customerId(),
                account.dailyTransferLimit(),
                account.dailyWithdrawLimit()
        );
        accounts.add(insertionEntity);
        return insertionEntity;
    }

    /**
     * PBO[method]: Memperbarui data rekening yang sudah ada di repository.
     *
     * @param account AccountEntity dengan data yang diperbarui
     * @return AccountEntity yang sudah diperbarui
     * @throws EntityNotFoundException jika ID rekening tidak ditemukan
     */
    @Override
    public AccountEntity update(AccountEntity account) {
        for (int i = 0; i < accounts.size(); i++) {
            if (accounts.get(i).id() == account.id()) {
                accounts.set(i, account);
                return account;
            }
        }

        throw new EntityNotFoundException("Akun tidak ditemukan");
    }

    /**
     * PBO[method]: Menghapus rekening berdasarkan ID unik.
     *
     * @param id ID rekening yang akan dihapus
     * @return true jika berhasil dihapus, false jika tidak ditemukan
     */
    @Override
    public boolean deleteById(int id) {
        return accounts.removeIf(account -> account.id() == id);
    }
}
