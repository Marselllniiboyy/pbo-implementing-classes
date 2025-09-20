package infrastructure.repository;

import domain.entity.CustomerEntity;
import domain.exception.EntityNotFoundException;
import domain.repository.CustomerRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

public class InMemoryCustomerRepository implements CustomerRepository {
    private final List<CustomerEntity> customers = Collections.synchronizedList(new ArrayList<>());
    private final AtomicInteger idCounter = new AtomicInteger(0);

    @Override
    public Optional<CustomerEntity> findById(int id) {
        return customers.stream()
                .filter(customer -> customer.id() == id)
                .findFirst();
    }

    @Override
    public Optional<CustomerEntity> findByEmail(String email) {
        return customers.stream()
                .filter(customer -> customer.email().equals(email))
                .findFirst();
    }

    @Override
    public List<CustomerEntity> findAll() {
        return new ArrayList<>(customers);
    }

    @Override
    public CustomerEntity update(CustomerEntity customerEntity) {
        for (int i = 0; i < customers.size(); i++) {
            if (customers.get(i).id() == customerEntity.id()) {
                customers.set(i, customerEntity);
                return customerEntity;
            }
        }

        throw new EntityNotFoundException("Pelanggan dengan ID: " + customerEntity.id() + " tidak ditemukan");
    }

    @Override
    public CustomerEntity save(CustomerEntity customer) {
        int newId = idCounter.incrementAndGet();
        CustomerEntity insertionEntity = new CustomerEntity(
                newId,
                customer.name(),
                customer.email(),
                customer.phoneNumber(),
                customer.address(),
                customer.dateOfBirth()
        );
        customers.add(insertionEntity);
        return insertionEntity;
    }

    @Override
    public boolean deleteById(int id) {
        return customers.removeIf(customer -> customer.id() == id);
    }
}
