package infrastructure.repository;

import domain.entity.TransactionEntity;
import domain.exception.EntityNotFoundException;
import domain.repository.TransactionRepository;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class InMemoryTransactionRepository implements TransactionRepository {
    private final List<TransactionEntity> transactions = Collections.synchronizedList(new ArrayList<>());
    private final AtomicInteger idCounter = new AtomicInteger(0);

    @Override
    public Optional<TransactionEntity> findById(int id) {
        return transactions.stream()
                .filter(transaction -> transaction.id() == id)
                .findFirst();
    }

    @Override
    public List<TransactionEntity> findByAccountIdWithDate(int accountId, String date) {
        return transactions.stream()
                .filter(
                        transaction -> transaction.accountId() == accountId
                                && Objects.equals(transaction.date(), date)
                )
                .toList();
    }

    @Override
    public List<TransactionEntity> findAll() {
        return new ArrayList<>(transactions);
    }

    @Override
    public TransactionEntity update(TransactionEntity transaction) {
        for (int i = 0; i < transactions.size(); i++) {
            if (transactions.get(i).id() == transaction.id()) {
                transactions.set(i, transaction);
                return transaction;
            }
        }

        throw new EntityNotFoundException("Akun tidak ditemukan");
    }

    @Override
    public TransactionEntity save(TransactionEntity transaction) {
        int newId = idCounter.incrementAndGet();
        TransactionEntity insertionEntity = new TransactionEntity(
                newId,
                transaction.accountId(),
                transaction.destinationAccountId(),
                transaction.amount(),
                transaction.transactionType(),
                transaction.date(),
                transaction.timestamp()
        );
        transactions.add(insertionEntity);
        return insertionEntity;
    }

    @Override
    public boolean deleteById(int id) {
        return transactions.removeIf(storedTransaction -> storedTransaction.id() == id);
    }
}
