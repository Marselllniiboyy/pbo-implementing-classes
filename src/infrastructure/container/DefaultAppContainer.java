package infrastructure.container;

import application.service.*;
import domain.repository.*;
import infrastructure.repository.*;
import infrastructure.service.CliLogService;

public class DefaultAppContainer implements AppContainer {
    private final AccountCardRepository accountCardRepository;
    private final AccountRepository accountRepository;
    private final CardTypeRepository cardTypeRepository;
    private final CustomerRepository customerRepository;
    private final TransactionRepository transactionRepository;

    private final AccountService accountService;
    private final CardTypeService cardTypeService;
    private final CustomerService customerService;
    private final TransactionService transactionService;
    private final LogService logService;

    public DefaultAppContainer() {
        accountCardRepository = new InMemoryAccountCardRepository();
        accountRepository = new InMemoryAccountRepository();
        cardTypeRepository = new InMemoryCardTypeRepository();
        customerRepository = new InMemoryCustomerRepository();
        transactionRepository = new InMemoryTransactionRepository();

        logService = new CliLogService();
        accountService = new AccountService(accountRepository, cardTypeRepository, accountCardRepository);
        cardTypeService = new CardTypeService(cardTypeRepository);
        customerService = new CustomerService(customerRepository);
        transactionService = new TransactionService(accountRepository, accountCardRepository, cardTypeRepository, transactionRepository);
    }

    @Override
    public AccountCardRepository getAccountCardRepository() {
        return accountCardRepository;
    }

    @Override
    public AccountRepository getAccountRepository() {
        return accountRepository;
    }

    @Override
    public CardTypeRepository getCardTypeRepository() {
        return cardTypeRepository;
    }

    @Override
    public CustomerRepository getCustomerRepository() {
        return customerRepository;
    }

    @Override
    public TransactionRepository getTransactionRepository() {
        return transactionRepository;
    }

    @Override
    public AccountService getAccountService() {
        return accountService;
    }

    @Override
    public CardTypeService getCardTypeService() {
        return cardTypeService;
    }

    @Override
    public CustomerService getCustomerService() {
        return customerService;
    }

    @Override
    public TransactionService getTransactionService() {
        return transactionService;
    }

    @Override
    public LogService getLogService() {
        return logService;
    }
}
