// PBO[package]: Menentukan paket infrastructure.repository agar implementasi repository ini dapat digunakan di lapisan infrastruktur
package infrastructure.repository;

import domain.entity.CardTypeEntity; // PBO[import]: Mengimpor entitas jenis kartu untuk dimanipulasi oleh repository ini
import domain.exception.EntityNotFoundException; // PBO[import]: Mengimpor exception untuk dilempar jika data tidak ditemukan saat update
import domain.repository.CardTypeRepository; // PBO[import]: Mengimpor interface repository agar kelas ini sesuai kontrak repository

import java.util.ArrayList; // PBO[import]: Struktur data dinamis untuk menyimpan entitas dalam memori
import java.util.Collections; // PBO[import]: Digunakan untuk membuat list yang aman untuk akses multi-thread
import java.util.List; // PBO[import]: Tipe koleksi yang digunakan untuk menampung CardTypeEntity
import java.util.Optional; // PBO[import]: Tipe pembungkus hasil pencarian yang bisa kosong (empty) atau berisi nilai
import java.util.concurrent.atomic.AtomicInteger; // PBO[import]: Variabel counter thread-safe untuk menghasilkan ID unik secara otomatis

/**
 * PBO[class]: Implementasi in-memory untuk CardTypeRepository.
 *
 * <p>Kelas ini menyimpan data jenis kartu (ATM/Debit) di dalam memori menggunakan List.
 * Cocok untuk pengujian (unit test) atau prototipe tanpa database.
 * ID baru akan di-generate otomatis menggunakan AtomicInteger.</p>
 *
 * @since 1.0
 * @author
 *  Gede Dhanu Purnayasa
 *  Made Marsel Biliana Wijaya
 */
public class InMemoryCardTypeRepository implements CardTypeRepository {
    // PBO[field]: List sinkron untuk menyimpan data jenis kartu di memori agar aman diakses multi-thread
    private final List<CardTypeEntity> cardTypes = Collections.synchronizedList(new ArrayList<>());
    // PBO[field]: Counter ID otomatis thread-safe untuk memberi ID unik pada entitas baru
    private final AtomicInteger idCounter = new AtomicInteger(0);
    /**
     * PBO[method]: Mencari jenis kartu berdasarkan ID unik.
     *
     * @param id ID jenis kartu yang dicari
     * @return Optional berisi CardTypeEntity jika ditemukan, kosong jika tidak
     */
    @Override
    public Optional<CardTypeEntity> findById(int id) {
        return cardTypes.stream()
                .filter(cardType -> cardType.id() == id)
                .findFirst();
    }

    /**
     * PBO[method]: Mengambil semua data jenis kartu yang tersimpan di memori.
     *
     * @return List baru berisi semua CardTypeEntity yang ada
     */
    @Override
    public List<CardTypeEntity> findAll() {
        return new ArrayList<>(cardTypes);
    }

    /**
     * PBO[method]: Menyimpan jenis kartu baru ke repository dengan ID baru otomatis.
     *
     * @param cardType CardTypeEntity yang akan disimpan (tanpa ID)
     * @return CardTypeEntity yang sudah disimpan dengan ID yang sudah di-assign
     */
    @Override
    public CardTypeEntity save(CardTypeEntity cardType) {
        int newId = idCounter.incrementAndGet();
        CardTypeEntity insertionEntity = new CardTypeEntity(
                newId,
                cardType.name(),
                cardType.description(),
                cardType.monthlyPrice(),
                cardType.dailyTransferLimit(),
                cardType.dailyWithdrawLimit(),
                cardType.dailyDepositLimit(),
                cardType.minimumBalance()
        );
        cardTypes.add(insertionEntity);
        return insertionEntity;
    }

    /**
     * PBO[method]: Memperbarui data jenis kartu yang sudah ada di repository.
     *
     * @param cardType CardTypeEntity dengan data yang diperbarui
     * @return CardTypeEntity yang sudah diperbarui
     * @throws EntityNotFoundException jika ID jenis kartu tidak ditemukan
     */
    @Override
    public CardTypeEntity update(CardTypeEntity cardType) {
        for (int i = 0; i < cardTypes.size(); i++) {
            if (cardTypes.get(i).id() == cardType.id()) {
                cardTypes.set(i, cardType);
                return cardType;
            }
        }

        throw new EntityNotFoundException("Jenis kartu tidak ditemukan");
    }

    /**
     * PBO[method]: Menghapus jenis kartu berdasarkan ID unik.
     *
     * @param id ID jenis kartu yang akan dihapus
     * @return true jika berhasil dihapus, false jika tidak ditemukan
     */
    @Override
    public boolean deleteById(int id) {
        return cardTypes.removeIf(cardType -> cardType.id() == id);
    }
}
