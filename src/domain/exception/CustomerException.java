package domain.exception;

import java.util.Map;

/**
 * Exception untuk masalah terkait customer
 */
public class CustomerException extends BankingException {
    
    public static class CustomerNotFound extends CustomerException {
        public CustomerNotFound(int customerId) {
            super("CUSTOMER_NOT_FOUND", 
                "Nasabah tidak ditemukan", 
                String.format("Customer with ID %d not found", customerId),
                Map.of("customerId", customerId));
        }
        
        public CustomerNotFound(String email) {
            super("CUSTOMER_NOT_FOUND", 
                "Nasabah tidak ditemukan", 
                String.format("Customer with email %s not found", email),
                Map.of("email", email));
        }
    }
    
    public static class CustomerAlreadyExists extends CustomerException {
        public CustomerAlreadyExists(String email) {
            super("CUSTOMER_ALREADY_EXISTS", 
                "Email sudah terdaftar", 
                String.format("Customer with email %s already exists", email),
                Map.of("email", email));
        }
    }
    
    public static class InvalidCustomerData extends CustomerException {
        public InvalidCustomerData(String field, String value, String reason) {
            super("INVALID_CUSTOMER_DATA", 
                String.format("Data %s tidak valid", field), 
                String.format("Invalid customer data: %s = %s, reason: %s", field, value, reason),
                Map.of(
                    "field", field,
                    "value", value,
                    "reason", reason
                ));
        }
        
        public InvalidCustomerData(String message) {
            super("INVALID_CUSTOMER_DATA", 
                "Data nasabah tidak valid", 
                message,
                Map.of("message", message));
        }
    }
    
    public static class CustomerUnderage extends CustomerException {
        public CustomerUnderage(int age, int minimumAge) {
            super("CUSTOMER_UNDERAGE", 
                "Usia tidak memenuhi syarat", 
                String.format("Customer age %d below minimum %d", age, minimumAge),
                Map.of(
                    "age", age,
                    "minimumAge", minimumAge
                ));
        }
    }
    
    private CustomerException(String errorCode, String userMessage, String technicalMessage, Map<String, Object> context) {
        super(errorCode, userMessage, technicalMessage, context);
    }
}
