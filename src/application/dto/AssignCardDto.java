package application.dto;

import domain.entity.AccountEntity;
import domain.entity.CardTypeEntity;

/**
 * Data Transfer Object (DTO) untuk menugaskan kartu ke rekening.
 * 
 * <p>Record ini digunakan untuk mengirim data penugasan kartu dari presentation layer
 * ke service layer dalam operasi penugasan kartu ATM/Debit ke rekening nasabah.</p>
 * 
 * @param account entitas rekening yang akan ditugaskan kartu
 * @param cardType entitas tipe kartu yang akan ditugaskan
 * @param pin Personal Identification Number untuk kartu yang akan dibuat
 * 
 * @author Gede Dhanu Purnayasa
 * @author Made Marsel Biliana Wijaya
 * @since 1.0
 */
public record AssignCardDto(AccountEntity account, CardTypeEntity cardType, int pin) {
}
