package infrastructure.repository;

import domain.entity.CardTypeEntity;
import domain.exception.EntityNotFoundException;
import domain.repository.CardTypeRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

public class InMemoryCardTypeRepository implements CardTypeRepository {
    private final List<CardTypeEntity> cardTypes = Collections.synchronizedList(new ArrayList<>());
    private final AtomicInteger idCounter = new AtomicInteger(0);

    @Override
    public Optional<CardTypeEntity> findById(int id) {
        return cardTypes.stream()
                .filter(cardType -> cardType.id() == id)
                .findFirst();
    }

    @Override
    public List<CardTypeEntity> findAll() {
        return new ArrayList<>(cardTypes);
    }

    @Override
    public CardTypeEntity save(CardTypeEntity cardType) {
        int newId = idCounter.incrementAndGet();
        CardTypeEntity insertionEntity = new CardTypeEntity(
                newId,
                cardType.name(),
                cardType.description(),
                cardType.monthlyPrice(),
                cardType.dailyTransferLimit(),
                cardType.dailyWithdrawLimit(),
                cardType.dailyDepositLimit(),
                cardType.minimumBalance()
        );
        cardTypes.add(insertionEntity);
        return insertionEntity;
    }

    @Override
    public CardTypeEntity update(CardTypeEntity cardType) {
        for (int i = 0; i < cardTypes.size(); i++) {
            if (cardTypes.get(i).id() == cardType.id()) {
                cardTypes.set(i, cardType);
                return cardType;
            }
        }

        throw new EntityNotFoundException("Jenis kartu tidak ditemukan");
    }

    @Override
    public boolean deleteById(int id) {
        return cardTypes.removeIf(cardType -> cardType.id() == id);
    }
}
