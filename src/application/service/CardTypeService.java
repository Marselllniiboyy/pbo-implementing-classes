package application.service;

import application.dto.CreateCardTypeDto;
import domain.entity.CardTypeEntity;
import domain.repository.CardTypeRepository;

/**
 * Service untuk mengelola operasi bisnis terkait tipe kartu bank.
 * 
 * <p>Class ini menyediakan business logic untuk operasi tipe kartu seperti
 * pembuatan tipe kartu baru (Diamond, Gold, Silver) dengan berbagai
 * karakteristik dan batasan transaksi.</p>
 * 
 * @author Gede Dhanu Purnayasa
 * @author Made Marsel Biliana Wijaya
 * @since 1.0
 */
public class CardTypeService {
    private final CardTypeRepository cardTypeRepository;

    /**
     * Konstruktor untuk CardTypeService.
     * 
     * @param cardTypeRepository repository untuk mengakses data tipe kartu
     */
    public CardTypeService(CardTypeRepository cardTypeRepository) {
        this.cardTypeRepository = cardTypeRepository;
    }

    /**
     * Membuat tipe kartu baru dalam sistem perbankan.
     * 
     * <p>Method ini akan membuat entitas CardTypeEntity baru berdasarkan
     * data yang diberikan dalam DTO dan menyimpannya ke repository.</p>
     * 
     * @param cardTypeDto data tipe kartu yang akan dibuat
     * @return CardTypeEntity yang sudah disimpan dengan ID yang di-assign
     */
    public CardTypeEntity createCardType(CreateCardTypeDto cardTypeDto) {
        CardTypeEntity cardType = new CardTypeEntity(
                0,
                cardTypeDto.name(),
                cardTypeDto.description(),
                cardTypeDto.monthlyPrice(),
                cardTypeDto.dailyTransferLimit(),
                cardTypeDto.dailyWithdrawLimit(),
                cardTypeDto.dailyDepositLimit(),
                cardTypeDto.minimumBalance()
        );
        return cardTypeRepository.save(cardType);
    }
}
