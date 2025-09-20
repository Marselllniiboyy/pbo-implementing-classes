package application.service;

import application.dto.CreateCustomerDto;
import application.dto.UpdateCustomerDto;
import domain.entity.CustomerEntity;
import domain.exception.CustomerException;
import domain.repository.CustomerRepository;

/**
 * Service untuk mengelola operasi bisnis terkait nasabah bank.
 * 
 * <p>Class ini menyediakan business logic untuk operasi nasabah seperti
 * pembuatan nasabah baru, validasi data, dan koordinasi dengan repository layer.</p>
 * 
 * @author Gede Dhanu Purnayasa
 * @author Made Marsel Biliana Wijaya
 * @since 1.0
 */
public class CustomerService {
    private final CustomerRepository customerRepository;

    /**
     * Konstruktor untuk CustomerService.
     * 
     * @param customerRepository repository untuk mengakses data nasabah
     */
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    /**
     * Membuat nasabah baru dalam sistem perbankan.
     * 
     * <p>Method ini akan membuat entitas CustomerEntity baru berdasarkan
     * data yang diberikan dalam DTO dan menyimpannya ke repository.</p>
     * 
     * @param customerDto data nasabah yang akan dibuat
     * @return CustomerEntity yang sudah disimpan dengan ID yang di-assign
     * @throws CustomerException.InvalidCustomerData jika data nasabah tidak valid
     * @throws CustomerException.CustomerAlreadyExists jika email sudah terdaftar
     */
    public CustomerEntity createCustomer(CreateCustomerDto customerDto) {
        CustomerEntity customer = new CustomerEntity(
                0,
                customerDto.name(),
                customerDto.email(),
                customerDto.phoneNumber(),
                customerDto.address(),
                customerDto.dateOfBirth()
        );
        return customerRepository.save(customer);
    }

    /**
     * Mengupdate informasi nasabah yang sudah ada.
     * 
     * <p>Method ini akan mengupdate data nasabah berdasarkan ID yang diberikan.
     * Hanya field yang tidak null dalam DTO yang akan diupdate. Method ini
     * mendukung partial update, sehingga nasabah dapat mengupdate hanya field
     * tertentu tanpa mempengaruhi field lainnya.</p>
     * 
     * @param customerId ID nasabah yang akan diupdate
     * @param updateDto data update nasabah (field null akan diabaikan)
     * @return CustomerEntity yang sudah diupdate
     * @throws CustomerException.CustomerNotFound jika nasabah dengan ID tersebut tidak ditemukan
     * @throws CustomerException.InvalidCustomerData jika data update tidak valid
     * @throws CustomerException.CustomerAlreadyExists jika email baru sudah digunakan nasabah lain
     */
    public CustomerEntity updateCustomer(int customerId, UpdateCustomerDto updateDto) {
        // Validasi input
        if (updateDto == null) {
            throw new CustomerException.InvalidCustomerData("Data update tidak boleh null");
        }
        
        if (updateDto.isEmpty()) {
            throw new CustomerException.InvalidCustomerData("Tidak ada data yang akan diupdate");
        }
        
        // Cari nasabah yang akan diupdate
        CustomerEntity existingCustomer = customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomerException.CustomerNotFound(customerId));
        
        // Validasi email jika akan diupdate
        if (updateDto.email() != null && !updateDto.email().equals(existingCustomer.email())) {
            if (customerRepository.findByEmail(updateDto.email()).isPresent()) {
                throw new CustomerException.CustomerAlreadyExists(updateDto.email());
            }
        }
        
        // Buat customer entity baru dengan data yang diupdate
        CustomerEntity updatedCustomer = new CustomerEntity(
                existingCustomer.id(),
                updateDto.name() != null ? updateDto.name() : existingCustomer.name(),
                updateDto.email() != null ? updateDto.email() : existingCustomer.email(),
                updateDto.phoneNumber() != null ? updateDto.phoneNumber() : existingCustomer.phoneNumber(),
                updateDto.address() != null ? updateDto.address() : existingCustomer.address(),
                updateDto.dateOfBirth() != null ? updateDto.dateOfBirth() : existingCustomer.dateOfBirth()
        );
        
        return customerRepository.update(updatedCustomer);
    }
}
