package application.service;

import application.dto.AssignCardDto;
import application.dto.CreateAccountDto;
import domain.entity.AccountCardEntity;
import domain.entity.AccountEntity;
import domain.exception.AccountException;
import domain.exception.CardException;
import domain.repository.AccountCardRepository;
import domain.repository.AccountRepository;
import domain.repository.CardTypeRepository;
import domain.util.IdGenerator;

import java.math.BigDecimal;

/**
 * Service untuk mengelola operasi bisnis terkait rekening bank.
 * 
 * <p>Class ini menyediakan business logic untuk operasi rekening seperti
 * pembuatan rekening baru, penugasan kartu, update PIN, dan koordinasi
 * dengan repository layer untuk data persistence.</p>
 * 
 * @author Gede Dhanu Purnayasa
 * @author Made Marsel Biliana Wijaya
 * @since 1.0
 */
public class AccountService {
    private final AccountCardRepository accountCardRepository;
    private final CardTypeRepository cardTypeRepository;
    private final AccountRepository accountRepository;

    /**
     * Konstruktor untuk AccountService.
     * 
     * @param accountRepository repository untuk mengakses data rekening
     * @param cardTypeRepository repository untuk mengakses data tipe kartu
     * @param accountCardRepository repository untuk mengakses data kartu rekening
     */
    public AccountService(AccountRepository accountRepository, CardTypeRepository cardTypeRepository, AccountCardRepository accountCardRepository) {
        this.accountRepository = accountRepository;
        this.cardTypeRepository = cardTypeRepository;
        this.accountCardRepository = accountCardRepository;
    }

    /**
     * Membuat rekening bank baru untuk nasabah.
     * 
     * <p>Method ini akan membuat entitas AccountEntity baru dengan nomor rekening
     * yang di-generate otomatis dan batasan transaksi default.</p>
     * 
     * @param accountDto data rekening yang akan dibuat
     * @return AccountEntity yang sudah disimpan dengan ID dan nomor rekening yang di-assign
     */
    public AccountEntity createAccount(CreateAccountDto accountDto) {
        AccountEntity account = new AccountEntity(
                0,
                IdGenerator.generateAccountNumber(),
                accountDto.balance(),
                accountDto.accountType(),
                accountDto.customer().id(),
                new BigDecimal("5000000"),
                new BigDecimal("10000000")
        );
        return accountRepository.save(account);
    }

    /**
     * Memuat ulang data rekening dari repository.
     * 
     * <p>Method ini berguna untuk mendapatkan data rekening yang terbaru
     * setelah terjadi perubahan saldo atau data lainnya.</p>
     * 
     * @param account rekening yang akan di-reload
     * @return AccountEntity dengan data terbaru dari repository
     * @throws AccountException.AccountNotFound jika rekening tidak ditemukan
     */
    public AccountEntity reloadAccount(AccountEntity account) {
        return accountRepository.findById(account.id())
                .orElseThrow(() -> new AccountException.AccountNotFound(account.id()));
    }

    /**
     * Memperbarui PIN kartu ATM/Debit.
     * 
     * <p>Method ini akan memvalidasi PIN lama sebelum menggantinya dengan PIN baru.
     * PIN harus sesuai dengan yang tersimpan di database.</p>
     * 
     * @param account rekening yang kartunya akan di-update PIN-nya
     * @param oldPin PIN lama untuk verifikasi
     * @param newPin PIN baru yang akan menggantikan PIN lama
     * @throws CardException.CardNotFound jika kartu tidak ditemukan untuk rekening ini
     * @throws CardException.InvalidPin jika PIN lama tidak sesuai
     */
    public void updatePin(AccountEntity account, int oldPin, int newPin) {
        AccountCardEntity accountCard = accountCardRepository.findByAccountId(account.id())
                .orElseThrow(() -> new CardException.CardNotFound(account.id()));

        if (oldPin != accountCard.pin()) {
            throw new CardException.InvalidPin(account.id(), 1);
        }

        AccountCardEntity updatedAccountCard = new AccountCardEntity(
                accountCard.id(),
                accountCard.accountId(),
                accountCard.cardNumber(),
                newPin,
                accountCard.cardTypeId(),
                true,
                accountCard.expiredDate()
        );

        accountCardRepository.update(updatedAccountCard);
    }

    /**
     * Menugaskan kartu ATM/Debit ke rekening.
     * 
     * <p>Method ini akan membuat kartu baru dengan nomor kartu yang di-generate
     * otomatis dan mengaitkannya dengan rekening yang ditentukan.</p>
     * 
     * @param assignCardDto data penugasan kartu termasuk rekening, tipe kartu, dan PIN
     * @return AccountCardEntity yang sudah disimpan dengan ID dan nomor kartu yang di-assign
     */
    public AccountCardEntity assignCard(AssignCardDto assignCardDto) {
        AccountCardEntity accountCard = new AccountCardEntity(
                0,
                assignCardDto.account().id(),
                IdGenerator.generateCardNumber(),
                assignCardDto.pin(),
                assignCardDto.cardType().id(),
                true,
                "2027-09-09"

        );

        return accountCardRepository.save(accountCard);
    }
}
