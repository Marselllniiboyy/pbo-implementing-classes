package application.dto;

import domain.entity.CustomerEntity;
import domain.value.AccountType;

import java.math.BigDecimal;

/**
 * Data Transfer Object (DTO) untuk membuat rekening bank baru.
 * 
 * <p>Record ini digunakan untuk mengirim data rekening dari presentation layer
 * ke service layer dalam operasi pembuatan rekening baru.</p>
 * 
 * @param customer entitas nasabah pemilik rekening
 * @param balance saldo awal rekening
 * @param accountType tipe rekening (SAVINGS, GIRO, TIME_DEPOSIT, CREDIT, BUSINESS)
 * 
 * @author Gede Dhanu Purnayasa
 * @author Made Marsel Biliana Wijaya
 * @since 1.0
 */
public record CreateAccountDto(CustomerEntity customer, BigDecimal balance, AccountType accountType) {
}
