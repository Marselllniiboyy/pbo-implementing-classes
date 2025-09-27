// PBO[package]: Menentukan paket infrastructure.repository agar implementasi repository ini dapat digunakan di lapisan infrastruktur
package infrastructure.repository;

import domain.entity.CustomerEntity; // PBO[import]: Mengimpor entitas pelanggan untuk dimanipulasi oleh repository ini
import domain.exception.EntityNotFoundException; // PBO[import]: Mengimpor exception untuk dilempar jika data tidak ditemukan saat update
import domain.repository.CustomerRepository; // PBO[import]: Mengimpor interface repository agar kelas ini sesuai kontrak repository

import java.util.ArrayList; // PBO[import]: Struktur data dinamis untuk menyimpan entitas dalam memori
import java.util.Collections; // PBO[import]: Digunakan untuk membuat list yang aman untuk akses multi-thread
import java.util.List; // PBO[import]: Tipe koleksi yang digunakan untuk menampung CustomerEntity
import java.util.Optional; // PBO[import]: Tipe pembungkus hasil pencarian yang bisa kosong (empty) atau berisi nilai
import java.util.concurrent.atomic.AtomicInteger; // PBO[import]: Variabel counter thread-safe untuk menghasilkan ID unik secara otomatis

/**
 * PBO[class]: Implementasi in-memory untuk CustomerRepository.
 *
 * <p>Kelas ini menyimpan data pelanggan di dalam memori menggunakan List.
 * Cocok untuk pengujian (unit test) atau prototipe tanpa database.
 * ID baru akan di-generate otomatis menggunakan AtomicInteger.</p>
 *
 * @since 1.0
 * @author
 *  Gede Dhanu Purnayasa
 *  Made Marsel Biliana Wijaya
 */
public class InMemoryCustomerRepository implements CustomerRepository {
    // PBO[field]: List sinkron untuk menyimpan data pelanggan di memori agar aman diakses multi-thread
    private final List<CustomerEntity> customers = Collections.synchronizedList(new ArrayList<>());
    // PBO[field]: Counter ID otomatis thread-safe untuk memberi ID unik pada entitas baru
    private final AtomicInteger idCounter = new AtomicInteger(0);

    /**
     * PBO[method]: Mencari pelanggan berdasarkan ID unik.
     *
     * @param id ID pelanggan yang dicari
     * @return Optional berisi CustomerEntity jika ditemukan, kosong jika tidak
     */
    @Override
    public Optional<CustomerEntity> findById(int id) {
        return customers.stream()
                .filter(customer -> customer.id() == id)
                .findFirst();
    }

    /**
     * PBO[method]: Mencari pelanggan berdasarkan email.
     *
     * @param email alamat email pelanggan
     * @return Optional berisi CustomerEntity jika ditemukan, kosong jika tidak
     */
    @Override
    public Optional<CustomerEntity> findByEmail(String email) {
        return customers.stream()
                .filter(customer -> customer.email().equals(email))
                .findFirst();
    }

    /**
     * PBO[method]: Mengambil semua data pelanggan yang tersimpan di memori.
     *
     * @return List baru berisi semua CustomerEntity yang ada
     */
    @Override
    public List<CustomerEntity> findAll() {
        return new ArrayList<>(customers);
    }

    /**
     * PBO[method]: Memperbarui data pelanggan yang sudah ada di repository.
     *
     * @param customerEntity CustomerEntity dengan data yang diperbarui
     * @return CustomerEntity yang sudah diperbarui
     * @throws EntityNotFoundException jika ID pelanggan tidak ditemukan
     */
    @Override
    public CustomerEntity update(CustomerEntity customerEntity) {
        for (int i = 0; i < customers.size(); i++) {
            if (customers.get(i).id() == customerEntity.id()) {
                customers.set(i, customerEntity);
                return customerEntity;
            }
        }

        throw new EntityNotFoundException("Pelanggan dengan ID: " + customerEntity.id() + " tidak ditemukan");
    }

    /**
     * PBO[method]: Menyimpan pelanggan baru ke repository dengan ID baru otomatis.
     *
     * @param customer CustomerEntity yang akan disimpan (tanpa ID)
     * @return CustomerEntity yang sudah disimpan dengan ID yang sudah di-assign
     */
    @Override
    public CustomerEntity save(CustomerEntity customer) {
        int newId = idCounter.incrementAndGet();
        CustomerEntity insertionEntity = new CustomerEntity(
                newId,
                customer.name(),
                customer.email(),
                customer.phoneNumber(),
                customer.address(),
                customer.dateOfBirth()
        );
        customers.add(insertionEntity);
        return insertionEntity;
    }

    /**
     * PBO[method]: Menghapus pelanggan berdasarkan ID unik.
     *
     * @param id ID pelanggan yang akan dihapus
     * @return true jika berhasil dihapus, false jika tidak ditemukan
     */
    @Override
    public boolean deleteById(int id) {
        return customers.removeIf(customer -> customer.id() == id);
    }
}
