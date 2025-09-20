package application.service;

import application.util.LocalTimeZone;
import domain.entity.AccountCardEntity;
import domain.entity.AccountEntity;
import domain.entity.CardTypeEntity;
import domain.entity.TransactionEntity;
import domain.exception.AccountException;
import domain.exception.CardException;
import domain.exception.CardTypeException;
import domain.exception.TransactionException;
import domain.repository.AccountCardRepository;
import domain.repository.AccountRepository;
import domain.repository.CardTypeRepository;
import domain.repository.TransactionRepository;
import domain.value.TransactionType;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

/**
 * Service untuk mengelola operasi bisnis terkait transaksi keuangan.
 * 
 * <p>Class ini menyediakan business logic untuk berbagai jenis transaksi
 * seperti transfer, deposit, withdraw, dan biaya bulanan kartu. Termasuk
 * validasi saldo, batasan harian, dan perhitungan biaya transaksi.</p>
 * 
 * @author Gede Dhanu Purnayasa
 * @author Made Marsel Biliana Wijaya
 * @since 1.0
 */
public class TransactionService {
    private final AccountRepository accountRepository;
    private final AccountCardRepository accountCardRepository;
    private final CardTypeRepository cardTypeRepository;
    private final TransactionRepository transactionRepository;

    /**
     * Konstruktor untuk TransactionService.
     * 
     * @param accountRepository repository untuk mengakses data rekening
     * @param accountCardRepository repository untuk mengakses data kartu rekening
     * @param cardTypeRepository repository untuk mengakses data tipe kartu
     * @param transactionRepository repository untuk mengakses data transaksi
     */
    public TransactionService(
            AccountRepository accountRepository, AccountCardRepository accountCardRepository, CardTypeRepository cardTypeRepository, TransactionRepository transactionRepository) {
        this.accountRepository = accountRepository;
        this.accountCardRepository = accountCardRepository;
        this.cardTypeRepository = cardTypeRepository;
        this.transactionRepository = transactionRepository;
    }

    /**
     * Menerapkan biaya bulanan kartu ke rekening.
     * 
     * <p>Method ini akan memotong biaya bulanan kartu dari saldo rekening
     * dan mencatatnya sebagai transaksi MONTHLY_CHARGE.</p>
     * 
     * @param accountNumber nomor rekening yang akan dikenakan biaya bulanan
     * @throws AccountException.AccountNotFound jika rekening tidak ditemukan
     * @throws CardException.CardNotFound jika kartu tidak ditemukan
     * @throws CardTypeException.CardTypeNotFound jika tipe kartu tidak ditemukan
     * @throws AccountException.InsufficientBalance jika saldo tidak mencukupi
     */
    public void applyCardMonthlyCharge(String accountNumber) {
        AccountEntity account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new AccountException.AccountNotFound(accountNumber));
        AccountCardEntity accountCard = accountCardRepository.findByAccountId(account.id())
                .orElseThrow(() -> new CardException.CardNotFound(account.id()));
        CardTypeEntity cardType = cardTypeRepository.findById(accountCard.cardTypeId())
                .orElseThrow(() -> new CardTypeException.CardTypeNotFound(accountCard.cardTypeId()));

        BigDecimal updatedBalance = account.balance().subtract(cardType.monthlyPrice());

        // jika saldo kurang dari 0 ketika apply charge, maka error
        if (updatedBalance.compareTo(BigDecimal.ZERO) < 0) {
            throw new AccountException.InsufficientBalance(accountNumber, account.balance(), cardType.monthlyPrice());
        }

        AccountEntity updatedAccount = new AccountEntity(
                account.id(),
                account.accountNumber(),
                updatedBalance,
                account.accountType(),
                account.customerId(),
                account.dailyTransferLimit(),
                account.dailyWithdrawLimit()
        );
        TransactionEntity transaction = new TransactionEntity(
                0,
                account.id(),
                null,
                cardType.monthlyPrice(),
                TransactionType.MONTHLY_CHARGE,
                LocalTimeZone.getDate(),
                LocalTimeZone.getNow()
        );

        transactionRepository.save(transaction);
        accountRepository.update(updatedAccount);
    }

    /**
     * Transfer uang antar rekening menggunakan kartu ATM/Debit.
     * 
     * <p>Method ini akan mentransfer uang dari rekening sumber ke rekening tujuan
     * dengan biaya transfer 1% dari jumlah transfer. Termasuk validasi saldo,
     * batasan harian, dan pencatatan transaksi.</p>
     * 
     * @param originAccountNumber nomor rekening sumber transfer
     * @param destinationAccountNumber nomor rekening tujuan transfer
     * @param amount jumlah uang yang akan ditransfer
     * @param pin PIN kartu untuk validasi
     * @throws TransactionException.InvalidTransactionAmount jika jumlah transfer tidak valid
     * @throws TransactionException.SameAccountTransfer jika transfer ke rekening yang sama
     * @throws AccountException.AccountNotFound jika rekening tidak ditemukan
     * @throws CardException.CardNotFound jika kartu tidak ditemukan
     * @throws CardException.InvalidPin jika PIN tidak valid
     * @throws CardTypeException.CardTypeNotFound jika tipe kartu tidak ditemukan
     * @throws AccountException.InsufficientBalance jika saldo tidak mencukupi
     * @throws TransactionException.DailyLimitExceeded jika batas harian terlampaui
     */
    public void sendMoneyUsingCard(String originAccountNumber, String destinationAccountNumber, BigDecimal amount, int pin) {
        // Validasi input
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new TransactionException.InvalidTransactionAmount(amount);
        }
        
        if (originAccountNumber.equals(destinationAccountNumber)) {
            throw new TransactionException.SameAccountTransfer(originAccountNumber);
        }
        
        AccountEntity originAccount = accountRepository.findByAccountNumber(originAccountNumber)
                .orElseThrow(() -> new AccountException.AccountNotFound(originAccountNumber));
        AccountEntity destinationAccount = accountRepository.findByAccountNumber(destinationAccountNumber)
                .orElseThrow(() -> new AccountException.AccountNotFound(destinationAccountNumber));
        AccountCardEntity originAccountCard = accountCardRepository.findByAccountId(originAccount.id())
                .orElseThrow(() -> new CardException.CardNotFound(originAccount.id()));
        
        // Validasi PIN
        if (originAccountCard.pin() != pin) {
            throw new CardException.InvalidPin(originAccountCard.id(), pin);
        }
        
        CardTypeEntity originAccountCardType = cardTypeRepository.findById(originAccountCard.cardTypeId())
                .orElseThrow(() -> new CardTypeException.CardTypeNotFound(originAccountCard.cardTypeId()));

        BigDecimal transferFee = amount.divide(new BigDecimal(100), RoundingMode.UNNECESSARY);
        BigDecimal totalAmount = amount.add(transferFee);
        BigDecimal finalOriginAmount = originAccount.balance().subtract(totalAmount);
        BigDecimal finalDestinationAmount = destinationAccount.balance().add(amount);

        if (finalOriginAmount.compareTo(BigDecimal.ZERO) < 0) {
            throw new AccountException.InsufficientBalance(originAccountNumber, originAccount.balance(), totalAmount);
        } 
        
        BigDecimal currentDailyTotal = getTotalDailyCardTransfer(originAccount.id(), LocalTimeZone.getDate());
        if (currentDailyTotal.add(amount).compareTo(originAccountCardType.dailyTransferLimit()) > 0) {
            throw new TransactionException.DailyLimitExceeded(originAccountNumber, "TRANSFER_VIA_CARD", 
                currentDailyTotal.add(amount), originAccountCardType.dailyTransferLimit());
        }

        applyMoneyTransfer(
                TransactionType.TRANSFER_VIA_CARD,
                originAccount,
                destinationAccount,
                totalAmount,
                finalOriginAmount,
                finalDestinationAmount
        );
    }

    /**
     * Transfer uang antar rekening melalui teller bank.
     * 
     * <p>Method ini akan mentransfer uang dari rekening sumber ke rekening tujuan
     * tanpa biaya transfer. Termasuk validasi saldo, batasan harian, dan pencatatan transaksi.</p>
     * 
     * @param originAccountNumber nomor rekening sumber transfer
     * @param destinationAccountNumber nomor rekening tujuan transfer
     * @param amount jumlah uang yang akan ditransfer
     * @throws TransactionException.InvalidTransactionAmount jika jumlah transfer tidak valid
     * @throws TransactionException.SameAccountTransfer jika transfer ke rekening yang sama
     * @throws AccountException.AccountNotFound jika rekening tidak ditemukan
     * @throws AccountException.InsufficientBalance jika saldo tidak mencukupi
     * @throws TransactionException.DailyLimitExceeded jika batas harian terlampaui
     */
    public void sendMoneyViaTeller(String originAccountNumber, String destinationAccountNumber, BigDecimal amount) {
        // Validasi input
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new TransactionException.InvalidTransactionAmount(amount);
        }
        
        if (originAccountNumber.equals(destinationAccountNumber)) {
            throw new TransactionException.SameAccountTransfer(originAccountNumber);
        }
        
        AccountEntity originAccount = accountRepository.findByAccountNumber(originAccountNumber)
                .orElseThrow(() -> new AccountException.AccountNotFound(originAccountNumber));
        AccountEntity destinationAccount = accountRepository.findByAccountNumber(destinationAccountNumber)
                .orElseThrow(() -> new AccountException.AccountNotFound(destinationAccountNumber));

        BigDecimal finalOriginAmount = originAccount.balance().subtract(amount);
        BigDecimal finalDestinationAmount = destinationAccount.balance().add(amount);

        if (finalOriginAmount.compareTo(BigDecimal.ZERO) < 0) {
            throw new AccountException.InsufficientBalance(originAccountNumber, originAccount.balance(), amount);
        } 
        
        BigDecimal currentDailyTotal = getTotalDailyTransfer(originAccount.id(), LocalTimeZone.getDate());
        if (currentDailyTotal.add(amount).compareTo(originAccount.dailyTransferLimit()) > 0) {
            throw new TransactionException.DailyLimitExceeded(originAccountNumber, "TRANSFER", 
                currentDailyTotal.add(amount), originAccount.dailyTransferLimit());
        }

        applyMoneyTransfer(
                TransactionType.TRANSFER,
                originAccount,
                destinationAccount,
                amount,
                finalOriginAmount,
                finalDestinationAmount
        );
    }

    /**
     * Penarikan uang dari rekening menggunakan kartu ATM/Debit.
     * 
     * <p>Method ini akan menarik uang dari rekening menggunakan kartu dengan
     * validasi saldo dan batasan harian sesuai tipe kartu.</p>
     * 
     * @param accountNumber nomor rekening yang akan ditarik uangnya
     * @param amount jumlah uang yang akan ditarik
     * @param pin PIN kartu untuk validasi
     * @throws TransactionException.InvalidTransactionAmount jika jumlah penarikan tidak valid
     * @throws AccountException.AccountNotFound jika rekening tidak ditemukan
     * @throws CardException.CardNotFound jika kartu tidak ditemukan
     * @throws CardException.InvalidPin jika PIN tidak valid
     * @throws CardTypeException.CardTypeNotFound jika tipe kartu tidak ditemukan
     * @throws AccountException.InsufficientBalance jika saldo tidak mencukupi
     * @throws TransactionException.DailyLimitExceeded jika batas harian terlampaui
     */
    public void withdrawMoneyUsingCard(String accountNumber, BigDecimal amount, int pin) {
        // Validasi input
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new TransactionException.InvalidTransactionAmount(amount);
        }
        
        AccountEntity account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new AccountException.AccountNotFound(accountNumber));
        AccountCardEntity accountCard = accountCardRepository.findByAccountId(account.id())
                .orElseThrow(() -> new CardException.CardNotFound(account.id()));
        
        // Validasi PIN
        if (accountCard.pin() != pin) {
            throw new CardException.InvalidPin(accountCard.id(), pin);
        }
        
        CardTypeEntity cardType = cardTypeRepository.findById(accountCard.cardTypeId())
                .orElseThrow(() -> new CardTypeException.CardTypeNotFound(accountCard.cardTypeId()));

        BigDecimal finalBalance = account.balance().subtract(amount);
        if (finalBalance.compareTo(BigDecimal.ZERO) < 0) {
            throw new AccountException.InsufficientBalance(accountNumber, account.balance(), amount);
        } 
        
        BigDecimal currentDailyTotal = getTotalDailyCardWithdraw(account.id(), LocalTimeZone.getDate());
        if (currentDailyTotal.add(amount).compareTo(cardType.dailyWithdrawLimit()) > 0) {
            throw new TransactionException.DailyLimitExceeded(accountNumber, "WITHDRAW_VIA_CARD", 
                currentDailyTotal.add(amount), cardType.dailyWithdrawLimit());
        }

        applyMoneyWithdrawViaCard(account, amount, finalBalance);
    }

    /**
     * Setoran uang ke rekening menggunakan kartu ATM/Debit.
     * 
     * <p>Method ini akan menyimpan uang ke rekening menggunakan kartu dengan
     * validasi batasan harian sesuai tipe kartu.</p>
     * 
     * @param accountNumber nomor rekening yang akan disetor uangnya
     * @param amount jumlah uang yang akan disetor
     * @param pin PIN kartu untuk validasi
     * @throws TransactionException.InvalidTransactionAmount jika jumlah setoran tidak valid
     * @throws AccountException.AccountNotFound jika rekening tidak ditemukan
     * @throws CardException.CardNotFound jika kartu tidak ditemukan
     * @throws CardException.InvalidPin jika PIN tidak valid
     * @throws CardTypeException.CardTypeNotFound jika tipe kartu tidak ditemukan
     * @throws TransactionException.DailyLimitExceeded jika batas harian terlampaui
     */
    public void depositMoneyUsingCard(String accountNumber, BigDecimal amount, int pin) {
        // Validasi input
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new TransactionException.InvalidTransactionAmount(amount);
        }
        
        AccountEntity account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new AccountException.AccountNotFound(accountNumber));
        AccountCardEntity accountCard = accountCardRepository.findByAccountId(account.id())
                .orElseThrow(() -> new CardException.CardNotFound(account.id()));
        
        // Validasi PIN
        if (accountCard.pin() != pin) {
            throw new CardException.InvalidPin(accountCard.id(), pin);
        }
        
        CardTypeEntity cardType = cardTypeRepository.findById(accountCard.cardTypeId())
                .orElseThrow(() -> new CardTypeException.CardTypeNotFound(accountCard.cardTypeId()));

        BigDecimal finalBalance = account.balance().add(amount);
        BigDecimal currentDailyTotal = getTotalDailyCardDeposit(account.id(), LocalTimeZone.getDate());
        if (currentDailyTotal.add(amount).compareTo(cardType.dailyDepositLimit()) > 0) {
            throw new TransactionException.DailyLimitExceeded(accountNumber, "DEPOSIT_VIA_CARD", 
                currentDailyTotal.add(amount), cardType.dailyDepositLimit());
        }

        applyMoneyDeposit(account, amount, finalBalance);
    }

    /**
     * Penarikan uang dari rekening melalui teller bank.
     * 
     * <p>Method ini akan menarik uang dari rekening melalui teller dengan
     * validasi saldo dan batasan harian rekening.</p>
     * 
     * @param accountNumber nomor rekening yang akan ditarik uangnya
     * @param amount jumlah uang yang akan ditarik
     * @throws TransactionException.InvalidTransactionAmount jika jumlah penarikan tidak valid
     * @throws AccountException.AccountNotFound jika rekening tidak ditemukan
     * @throws AccountException.InsufficientBalance jika saldo tidak mencukupi
     * @throws TransactionException.DailyLimitExceeded jika batas harian terlampaui
     */
    public void withdrawMoneyViaTeller(String accountNumber, BigDecimal amount) {
        // Validasi input
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new TransactionException.InvalidTransactionAmount(amount);
        }
        
        AccountEntity account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new AccountException.AccountNotFound(accountNumber));

        BigDecimal finalBalance = account.balance().subtract(amount);
        if (finalBalance.compareTo(BigDecimal.ZERO) < 0) {
            throw new AccountException.InsufficientBalance(accountNumber, account.balance(), amount);
        } 
        
        BigDecimal currentDailyTotal = getTotalDailyWithdraw(account.id(), LocalTimeZone.getDate());
        if (currentDailyTotal.add(amount).compareTo(account.dailyWithdrawLimit()) > 0) {
            throw new TransactionException.DailyLimitExceeded(accountNumber, "WITHDRAW", 
                currentDailyTotal.add(amount), account.dailyWithdrawLimit());
        }

        applyMoneyWithdraw(account, amount, finalBalance);
    }

    /**
     * Setoran uang ke rekening melalui teller bank.
     * 
     * <p>Method ini akan menyimpan uang ke rekening melalui teller tanpa
     * batasan harian khusus.</p>
     * 
     * @param accountNumber nomor rekening yang akan disetor uangnya
     * @param amount jumlah uang yang akan disetor
     * @throws TransactionException.InvalidTransactionAmount jika jumlah setoran tidak valid
     * @throws AccountException.AccountNotFound jika rekening tidak ditemukan
     */
    public void depositMoneyViaTeller(String accountNumber, BigDecimal amount) {
        // Validasi input
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new TransactionException.InvalidTransactionAmount(amount);
        }
        
        AccountEntity account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new AccountException.AccountNotFound(accountNumber));

        BigDecimal finalBalance = account.balance().add(amount);

        applyMoneyDeposit(account, amount, finalBalance);
    }

    /**
     * Menghitung total transfer harian via kartu untuk akun tertentu.
     * 
     * <p>Method ini akan menghitung total jumlah transfer yang dilakukan
     * menggunakan kartu pada tanggal tertentu untuk akun yang ditentukan.</p>
     * 
     * @param accountId ID akun yang akan dihitung total transfernya
     * @param date tanggal dalam format YYYY-MM-DD
     * @return total transfer harian via kartu
     */
    public BigDecimal getTotalDailyCardTransfer(int accountId, String date) {
        List<TransactionEntity> transferHistories = transactionRepository.findByAccountIdWithDate(accountId, date);
        BigDecimal total = new BigDecimal(0);

        for (TransactionEntity transferHistory : transferHistories) {
            if (!transferHistory.transactionType().equals(TransactionType.TRANSFER_VIA_CARD)) {
                continue;
            }

            total = total.add(transferHistory.amount());
        }

        return total;
    }

    /**
     * Menghitung total transfer harian via teller untuk akun tertentu.
     * 
     * <p>Method ini akan menghitung total jumlah transfer yang dilakukan
     * melalui teller pada tanggal tertentu untuk akun yang ditentukan.</p>
     * 
     * @param accountId ID akun yang akan dihitung total transfernya
     * @param date tanggal dalam format YYYY-MM-DD
     * @return total transfer harian via teller
     */
    public BigDecimal getTotalDailyTransfer(int accountId, String date) {
        List<TransactionEntity> transferHistories = transactionRepository.findByAccountIdWithDate(accountId, date);
        BigDecimal total = new BigDecimal(0);

        for (TransactionEntity transferHistory : transferHistories) {
            if (!transferHistory.transactionType().equals(TransactionType.TRANSFER)) {
                continue;
            }

            total = total.add(transferHistory.amount());
        }

        return total;
    }

    /**
     * Menghitung total penarikan harian via kartu untuk akun tertentu.
     * 
     * <p>Method ini akan menghitung total jumlah penarikan yang dilakukan
     * menggunakan kartu pada tanggal tertentu untuk akun yang ditentukan.</p>
     * 
     * @param accountId ID akun yang akan dihitung total penarikannya
     * @param date tanggal dalam format YYYY-MM-DD
     * @return total penarikan harian via kartu
     */
    public BigDecimal getTotalDailyCardWithdraw(int accountId, String date) {
        List<TransactionEntity> transferHistories = transactionRepository.findByAccountIdWithDate(accountId, date);
        BigDecimal total = new BigDecimal(0);

        for (TransactionEntity transferHistory : transferHistories) {
            if (!transferHistory.transactionType().equals(TransactionType.WITHDRAW_VIA_CARD)) {
                continue;
            }

            total = total.add(transferHistory.amount());
        }

        return total;
    }

    /**
     * Menghitung total penarikan harian via teller untuk akun tertentu.
     * 
     * <p>Method ini akan menghitung total jumlah penarikan yang dilakukan
     * melalui teller pada tanggal tertentu untuk akun yang ditentukan.</p>
     * 
     * @param accountId ID akun yang akan dihitung total penarikannya
     * @param date tanggal dalam format YYYY-MM-DD
     * @return total penarikan harian via teller
     */
    public BigDecimal getTotalDailyWithdraw(int accountId, String date) {
        List<TransactionEntity> transferHistories = transactionRepository.findByAccountIdWithDate(accountId, date);
        BigDecimal total = new BigDecimal(0);

        for (TransactionEntity transferHistory : transferHistories) {
            if (!transferHistory.transactionType().equals(TransactionType.WITHDRAW)) {
                continue;
            }

            total = total.add(transferHistory.amount());
        }

        return total;
    }

    /**
     * Menghitung total setoran harian via kartu untuk akun tertentu.
     * 
     * <p>Method ini akan menghitung total jumlah setoran yang dilakukan
     * menggunakan kartu pada tanggal tertentu untuk akun yang ditentukan.</p>
     * 
     * @param accountId ID akun yang akan dihitung total setorannya
     * @param date tanggal dalam format YYYY-MM-DD
     * @return total setoran harian via kartu
     */
    public BigDecimal getTotalDailyCardDeposit(int accountId, String date) {
        List<TransactionEntity> transferHistories = transactionRepository.findByAccountIdWithDate(accountId, date);
        BigDecimal total = new BigDecimal(0);

        for (TransactionEntity transferHistory : transferHistories) {
            if (!transferHistory.transactionType().equals(TransactionType.DEPOSIT_VIA_CARD)) {
                continue;
            }

            total = total.add(transferHistory.amount());
        }

        return total;
    }

    /**
     * Menghitung total setoran harian via teller untuk akun tertentu.
     * 
     * <p>Method ini akan menghitung total jumlah setoran yang dilakukan
     * melalui teller pada tanggal tertentu untuk akun yang ditentukan.</p>
     * 
     * @param accountId ID akun yang akan dihitung total setorannya
     * @param date tanggal dalam format YYYY-MM-DD
     * @return total setoran harian via teller
     */
    public BigDecimal getTotalDailyDeposit(int accountId, String date) {
        List<TransactionEntity> transferHistories = transactionRepository.findByAccountIdWithDate(accountId, date);
        BigDecimal total = new BigDecimal(0);

        for (TransactionEntity transferHistory : transferHistories) {
            if (!transferHistory.transactionType().equals(TransactionType.DEPOSIT)) {
                continue;
            }

            total = total.add(transferHistory.amount());
        }

        return total;
    }

    /**
     * Menerapkan transfer uang antar rekening.
     * 
     * <p>Method private ini akan mencatat transaksi transfer dan memperbarui
     * saldo rekening sumber dan tujuan sesuai dengan jumlah yang ditransfer.</p>
     * 
     * @param transactionType tipe transaksi (TRANSFER atau TRANSFER_VIA_CARD)
     * @param originAccount rekening sumber transfer
     * @param destinationAccount rekening tujuan transfer
     * @param amount jumlah yang ditransfer (termasuk biaya jika ada)
     * @param finalOriginAmount saldo akhir rekening sumber setelah transfer
     * @param finalDestinationAmount saldo akhir rekening tujuan setelah transfer
     */
    private void applyMoneyTransfer(
            TransactionType transactionType, AccountEntity originAccount, AccountEntity destinationAccount,
            BigDecimal amount, BigDecimal finalOriginAmount, BigDecimal finalDestinationAmount) {
        TransactionEntity transaction = new TransactionEntity(
                0,
                originAccount.id(),
                destinationAccount.id(),
                amount,
                transactionType,
                LocalTimeZone.getDate(),
                LocalTimeZone.getNow()
        );
        AccountEntity updatedOriginAccount = new AccountEntity(
                originAccount.id(),
                originAccount.accountNumber(),
                finalOriginAmount,
                originAccount.accountType(),
                originAccount.customerId(),
                originAccount.dailyTransferLimit(),
                originAccount.dailyWithdrawLimit()
        );
        AccountEntity updatedDestinationAccount = new AccountEntity(
                destinationAccount.id(),
                destinationAccount.accountNumber(),
                finalDestinationAmount,
                destinationAccount.accountType(),
                destinationAccount.customerId(),
                destinationAccount.dailyTransferLimit(),
                destinationAccount.dailyWithdrawLimit()
        );

        transactionRepository.save(transaction);
        accountRepository.update(updatedOriginAccount);
        accountRepository.update(updatedDestinationAccount);
    }

    /**
     * Menerapkan penarikan uang dari rekening via kartu.
     * 
     * <p>Method private ini akan mencatat transaksi penarikan via kartu dan memperbarui
     * saldo rekening sesuai dengan jumlah yang ditarik.</p>
     * 
     * @param account rekening yang akan ditarik uangnya
     * @param amount jumlah uang yang ditarik
     * @param finalBalance saldo akhir rekening setelah penarikan
     */
    private void applyMoneyWithdrawViaCard(AccountEntity account, BigDecimal amount, BigDecimal finalBalance) {
        AccountEntity updatedAccount = new AccountEntity(
                account.id(),
                account.accountNumber(),
                finalBalance,
                account.accountType(),
                account.customerId(),
                account.dailyTransferLimit(),
                account.dailyWithdrawLimit()
        );
        TransactionEntity transaction = new TransactionEntity(
                0,
                account.id(),
                null,
                amount,
                TransactionType.WITHDRAW_VIA_CARD,
                LocalTimeZone.getDate(),
                LocalTimeZone.getNow()
        );

        transactionRepository.save(transaction);
        accountRepository.update(updatedAccount);
    }

    /**
     * Menerapkan penarikan uang dari rekening via teller.
     * 
     * <p>Method private ini akan mencatat transaksi penarikan via teller dan memperbarui
     * saldo rekening sesuai dengan jumlah yang ditarik.</p>
     * 
     * @param account rekening yang akan ditarik uangnya
     * @param amount jumlah uang yang ditarik
     * @param finalBalance saldo akhir rekening setelah penarikan
     */
    private void applyMoneyWithdraw(AccountEntity account, BigDecimal amount, BigDecimal finalBalance) {
        AccountEntity updatedAccount = new AccountEntity(
                account.id(),
                account.accountNumber(),
                finalBalance,
                account.accountType(),
                account.customerId(),
                account.dailyTransferLimit(),
                account.dailyWithdrawLimit()
        );
        TransactionEntity transaction = new TransactionEntity(
                0,
                account.id(),
                null,
                amount,
                TransactionType.WITHDRAW,
                LocalTimeZone.getDate(),
                LocalTimeZone.getNow()
        );

        transactionRepository.save(transaction);
        accountRepository.update(updatedAccount);
    }

    /**
     * Menerapkan setoran uang ke rekening.
     * 
     * <p>Method private ini akan mencatat transaksi setoran dan memperbarui
     * saldo rekening sesuai dengan jumlah yang disetor.</p>
     * 
     * @param account rekening yang akan disetor uangnya
     * @param amount jumlah uang yang disetor
     * @param finalBalance saldo akhir rekening setelah setoran
     */
    private void applyMoneyDeposit(AccountEntity account, BigDecimal amount, BigDecimal finalBalance) {
        AccountEntity updatedAccount = new AccountEntity(
                account.id(),
                account.accountNumber(),
                finalBalance,
                account.accountType(),
                account.customerId(),
                account.dailyTransferLimit(),
                account.dailyWithdrawLimit()
        );
        TransactionEntity transaction = new TransactionEntity(
                0,
                account.id(),
                null,
                amount,
                TransactionType.DEPOSIT,
                LocalTimeZone.getDate(),
                LocalTimeZone.getNow()
        );

        transactionRepository.save(transaction);
        accountRepository.update(updatedAccount);
    }
}
