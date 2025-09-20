package infrastructure.repository;

import domain.entity.AccountEntity;
import domain.exception.EntityNotFoundException;
import domain.repository.AccountRepository;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class InMemoryAccountRepository implements AccountRepository {
    private final List<AccountEntity> accounts = Collections.synchronizedList(new ArrayList<>());
    private final AtomicInteger idCounter = new AtomicInteger(0);

    @Override
    public Optional<AccountEntity> findById(int id) {
        return accounts.stream()
                .filter(account -> account.id() == id)
                .findFirst();
    }

    @Override
    public Optional<AccountEntity> findByCustomerId(int customerId) {
        return accounts.stream()
                .filter(account -> account.customerId() == customerId)
                .findFirst();
    }

    @Override
    public Optional<AccountEntity> findByAccountNumber(String accountNumber) {
        return accounts.stream()
                .filter(account -> Objects.equals(account.accountNumber(), accountNumber))
                .findFirst();
    }

    @Override
    public List<AccountEntity> findAll() {
        return new ArrayList<>(accounts);
    }

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

    @Override
    public boolean deleteById(int id) {
        return accounts.removeIf(account -> account.id() == id);
    }
}
