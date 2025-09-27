// PBO[package]: Menentukan paket infrastructure.repository agar implementasi repository ini dapat digunakan di lapisan infrastruktur
package infrastructure.repository;

import domain.entity.AccountCardEntity; // PBO[import]: Mengimpor entitas kartu rekening agar dapat dimanipulasi oleh repository ini
import domain.exception.EntityNotFoundException; // PBO[import]: Mengimpor exception untuk dilempar jika data tidak ditemukan saat update
import domain.repository.AccountCardRepository; // PBO[import]: Mengimpor interface repository agar kelas ini dapat mengimplementasikan kontrak repository

import java.util.ArrayList; // PBO[import]: Struktur data dinamis untuk menyimpan entitas dalam memori
import java.util.Collections; // PBO[import]: Digunakan untuk membuat list yang aman untuk akses multi-thread
import java.util.List; // PBO[import]: Tipe koleksi yang digunakan untuk menampung AccountCardEntity
import java.util.Optional; // PBO[import]: Tipe pembungkus hasil pencarian yang bisa kosong (empty) atau berisi nilai
import java.util.concurrent.atomic.AtomicInteger; // PBO[import]: Variabel counter thread-safe untuk menghasilkan ID unik secara otomatis

/**
 * PBO[class]: Implementasi in-memory untuk AccountCardRepository.
 *
 * <p>Kelas ini menyimpan data kartu rekening (ATM/Debit) di dalam memori menggunakan List.
 * Cocok untuk pengujian (unit test) atau prototipe tanpa database.
 * ID baru akan di-generate otomatis menggunakan AtomicInteger.</p>
 *
 * @since 1.0
 * @author
 *  Gede Dhanu Purnayasa
 *  Made Marsel Biliana Wijaya
 */
public class InMemoryAccountCardRepository implements AccountCardRepository {
    // PBO[field]: List sinkron untuk menyimpan data kartu rekening di memori agar aman diakses multi-thread
    private final List<AccountCardEntity> accountCards = Collections.synchronizedList(new ArrayList<>());
    // PBO[field]: Counter ID otomatis thread-safe untuk memberi ID unik pada entitas baru
    private final AtomicInteger idCounter = new AtomicInteger(0);

    /**
     * PBO[method]: Mencari kartu berdasarkan ID unik.
     *
     * @param id ID kartu yang dicari
     * @return Optional berisi AccountCardEntity jika ditemukan, kosong jika tidak
     */
    @Override
    public Optional<AccountCardEntity> findById(int id) {
        return accountCards.stream()
                .filter(accountCard -> accountCard.id() == id)
                .findFirst();
    }

    /**
     * PBO[method]: Mencari kartu berdasarkan ID rekening.
     *
     * @param accountId ID rekening pemilik kartu
     * @return Optional berisi AccountCardEntity jika ditemukan, kosong jika tidak
     */
    @Override
    public Optional<AccountCardEntity> findByAccountId(int accountId) {
        return accountCards.stream()
                .filter(accountCard -> accountCard.accountId() == accountId)
                .findFirst();
    }
    /**
     * PBO[method]: Mengambil semua data kartu yang tersimpan di memori.
     *
     * @return List baru berisi semua AccountCardEntity yang ada
     */
    @Override
    public List<AccountCardEntity> findAll() {
        return new ArrayList<>(accountCards);
    }
    /**
     * PBO[method]: Menyimpan kartu baru ke repository dengan ID baru otomatis.
     *
     * @param accountCard AccountCardEntity yang akan disimpan (tanpa ID)
     * @return AccountCardEntity yang sudah disimpan dengan ID yang sudah di-assign
     */
    @Override
    public AccountCardEntity save(AccountCardEntity accountCard) {
        int newId = idCounter.incrementAndGet();
        AccountCardEntity insertionEntity = new AccountCardEntity(
                newId,
                accountCard.accountId(),
                accountCard.cardNumber(),
                accountCard.pin(),
                accountCard.cardTypeId(),
                accountCard.active(),
                accountCard.expiredDate()
        );
        accountCards.add(insertionEntity);
        return insertionEntity;
    }
    /**
     * PBO[method]: Memperbarui data kartu yang sudah ada di repository.
     *
     * @param accountCard AccountCardEntity dengan data yang diperbarui
     * @return AccountCardEntity yang sudah diperbarui
     * @throws EntityNotFoundException jika ID kartu tidak ditemukan
     */
    @Override
    public AccountCardEntity update(AccountCardEntity accountCard) {
        for (int i = 0; i < accountCards.size(); i++) {
            if (accountCards.get(i).id() == accountCard.id()) {
                accountCards.set(i, accountCard);
                return accountCard;
            }
        }

        throw new EntityNotFoundException("Akun tidak ditemukan");
    }
    /**
     * PBO[method]: Menghapus kartu berdasarkan ID unik.
     *
     * @param id ID kartu yang akan dihapus
     * @return true jika berhasil dihapus, false jika tidak ditemukan
     */
    @Override
    public boolean deleteById(int id) {
        return accountCards.removeIf(accountCard -> accountCard.id() == id);
    }
}
