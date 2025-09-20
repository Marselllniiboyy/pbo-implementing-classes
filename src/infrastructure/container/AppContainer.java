package infrastructure.container;

import application.service.*;
import domain.repository.*;

public interface AppContainer {
    AccountCardRepository getAccountCardRepository();

    AccountRepository getAccountRepository();

    CardTypeRepository getCardTypeRepository();

    CustomerRepository getCustomerRepository();

    TransactionRepository getTransactionRepository();

    AccountService getAccountService();

    CardTypeService getCardTypeService();

    CustomerService getCustomerService();

    TransactionService getTransactionService();

    LogService getLogService();
}
