// PBO[package]: Menentukan paket tempat class ini berada
package infrastructure.container;

// PBO[import]: Mengimpor class yang diperlukan dari package service, repository, dan implementasi infrastructure
import application.service.*;
import domain.repository.*;
import infrastructure.repository.*;
import infrastructure.service.CliLogService;

// PBO[class]: DefaultAppContainer adalah implementasi konkrit dari interface AppContainer
public class DefaultAppContainer implements AppContainer {
    // PBO[field]: Dependency repository yang disediakan oleh container
    private final AccountCardRepository accountCardRepository;
    private final AccountRepository accountRepository;
    private final CardTypeRepository cardTypeRepository;
    private final CustomerRepository customerRepository;
    private final TransactionRepository transactionRepository;

    // PBO[field]: Dependency service yang disediakan oleh container
    private final AccountService accountService;
    private final CardTypeService cardTypeService;
    private final CustomerService customerService;
    private final TransactionService transactionService;
    private final LogService logService;

    // PBO[constructor]: Menginisialisasi semua repository dan service yang digunakan aplikasi
    public DefaultAppContainer() {
        // PBO[repository instance]: Menggunakan implementasi in-memory untuk repository
        accountCardRepository = new InMemoryAccountCardRepository();
        accountRepository = new InMemoryAccountRepository();
        cardTypeRepository = new InMemoryCardTypeRepository();
        customerRepository = new InMemoryCustomerRepository();
        transactionRepository = new InMemoryTransactionRepository();

        // PBO[service instance]: Menginisialisasi service dengan dependency yang sesuai
        logService = new CliLogService();
        accountService = new AccountService(accountRepository, cardTypeRepository, accountCardRepository);
        cardTypeService = new CardTypeService(cardTypeRepository);
        customerService = new CustomerService(customerRepository);
        transactionService = new TransactionService(accountRepository, accountCardRepository, cardTypeRepository, transactionRepository);
    }


    // PBO[override]: Mengimplementasikan kontrak AppContainer — menyediakan AccountCardRepository (dikembalikan sebagai interface untuk loose coupling).
    @Override
    public AccountCardRepository getAccountCardRepository() {
        return accountCardRepository;
    }

    // PBO[override]: Mengimplementasikan kontrak AppContainer — menyediakan AccountRepository (abstraksi untuk operasi akun).
    @Override
    public AccountRepository getAccountRepository() {
        return accountRepository;
    }

    // PBO[override]: Mengimplementasikan kontrak AppContainer — menyediakan CardTypeRepository.
    @Override
    public CardTypeRepository getCardTypeRepository() {
        return cardTypeRepository;
    }

    // PBO[override]: Mengimplementasikan kontrak AppContainer — menyediakan CustomerRepository.
    @Override
    public CustomerRepository getCustomerRepository() {
        return customerRepository;
    }

    // PBO[override]: Mengimplementasikan kontrak AppContainer — menyediakan TransactionRepository.
    @Override
    public TransactionRepository getTransactionRepository() {
        return transactionRepository;
    }

    // PBO[override]: Mengimplementasikan kontrak AppContainer — menyediakan AccountService.
    @Override
    public AccountService getAccountService() {
        return accountService;
    }

    // PBO[override]: Mengimplementasikan kontrak AppContainer — menyediakan CardTypeService.
    @Override
    public CardTypeService getCardTypeService() {
        return cardTypeService;
    }

    // PBO[override]: Mengimplementasikan kontrak AppContainer — menyediakan CustomerService.
    @Override
    public CustomerService getCustomerService() {
        return customerService;
    }

    // PBO[override]: Mengimplementasikan kontrak AppContainer — menyediakan TransactionService.
    @Override
    public TransactionService getTransactionService() {
        return transactionService;
    }

    // PBO[override]: Mengimplementasikan kontrak AppContainer — menyediakan LogService.
    @Override
    public LogService getLogService() {
        return logService;
    }
}
