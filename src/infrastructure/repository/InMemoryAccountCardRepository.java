package infrastructure.repository;

import domain.entity.AccountCardEntity;
import domain.exception.EntityNotFoundException;
import domain.repository.AccountCardRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

public class InMemoryAccountCardRepository implements AccountCardRepository {
    private final List<AccountCardEntity> accountCards = Collections.synchronizedList(new ArrayList<>());
    private final AtomicInteger idCounter = new AtomicInteger(0);

    @Override
    public Optional<AccountCardEntity> findById(int id) {
        return accountCards.stream()
                .filter(accountCard -> accountCard.id() == id)
                .findFirst();
    }

    @Override
    public Optional<AccountCardEntity> findByAccountId(int accountId) {
        return accountCards.stream()
                .filter(accountCard -> accountCard.accountId() == accountId)
                .findFirst();
    }

    @Override
    public List<AccountCardEntity> findAll() {
        return new ArrayList<>(accountCards);
    }

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

    @Override
    public boolean deleteById(int id) {
        return accountCards.removeIf(accountCard -> accountCard.id() == id);
    }
}
