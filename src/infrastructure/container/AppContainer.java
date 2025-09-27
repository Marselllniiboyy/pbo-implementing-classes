// PBO[package]: Menentukan paket tempat interface ini berada
package infrastructure.container;

// PBO[import]: Mengimpor semua class yang diperlukan dari package service dan repository
import application.service.*;
import domain.repository.*;


// PBO[interface]: AppContainer adalah interface yang mendefinisikan kontrak untuk menyediakan dependency (Repository dan Service)
public interface AppContainer {

    // PBO[repository getter]: Mendapatkan objek AccountCardRepository
    AccountCardRepository getAccountCardRepository();

    // PBO[repository getter]: Mendapatkan objek AccountRepository
    AccountRepository getAccountRepository();

    // PBO[repository getter]: Mendapatkan objek CardTypeRepository
    CardTypeRepository getCardTypeRepository();

    // PBO[repository getter]: Mendapatkan objek CustomerRepository
    CustomerRepository getCustomerRepository();

    // PBO[repository getter]: Mendapatkan objek TransactionRepository
    TransactionRepository getTransactionRepository();

    // PBO[service getter]: Mendapatkan objek AccountService
    AccountService getAccountService();

    // PBO[service getter]: Mendapatkan objek CardTypeService
    CardTypeService getCardTypeService();

    // PBO[service getter]: Mendapatkan objek CustomerService
    CustomerService getCustomerService();

    // PBO[service getter]: Mendapatkan objek TransactionService
    TransactionService getTransactionService();

    // PBO[service getter]: Mendapatkan objek LogService
    LogService getLogService();
}
