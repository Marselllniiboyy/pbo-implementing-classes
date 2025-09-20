# Banking Exception System

Sistem exception handling yang diperbaiki untuk aplikasi perbankan dengan payload yang kaya dan penanganan yang spesifik.

## Arsitektur Exception

### 1. Base Exception Class
- **`BankingException`**: Base class untuk semua exception dalam sistem perbankan
- Menyediakan payload yang kaya untuk debugging dan error handling
- Menggunakan error code, timestamp, context, dan pesan yang terpisah untuk user dan technical

### 2. Exception Categories

#### AccountException
- `AccountNotFound`: Rekening tidak ditemukan
- `InsufficientBalance`: Saldo tidak mencukupi
- `AccountAlreadyExists`: Rekening sudah ada
- `InvalidAccountType`: Tipe rekening tidak valid

#### CardException
- `CardNotFound`: Kartu tidak ditemukan
- `InvalidPin`: PIN salah
- `CardExpired`: Kartu sudah expired
- `CardInactive`: Kartu tidak aktif
- `CardAlreadyAssigned`: Akun sudah memiliki kartu

#### TransactionException
- `DailyLimitExceeded`: Batas harian terlampaui
- `InvalidTransactionAmount`: Jumlah transaksi tidak valid
- `SameAccountTransfer`: Transfer ke rekening yang sama
- `TransactionNotFound`: Transaksi tidak ditemukan
- `TransferFeeExceedsBalance`: Biaya transfer melebihi saldo

#### CustomerException
- `CustomerNotFound`: Nasabah tidak ditemukan
- `CustomerAlreadyExists`: Email sudah terdaftar
- `InvalidCustomerData`: Data nasabah tidak valid
- `CustomerUnderage`: Usia tidak memenuhi syarat

#### CardTypeException
- `CardTypeNotFound`: Jenis kartu tidak ditemukan
- `InvalidCardTypeData`: Data jenis kartu tidak valid
- `NegativeLimit`: Limit tidak boleh negatif
- `InvalidMonthlyPrice`: Harga bulanan tidak valid

## Keunggulan Sistem Baru

### 1. Rich Payload
```java
// Exception dengan context yang kaya
AccountException.InsufficientBalance e = new AccountException.InsufficientBalance(
    "1234567890", 
    new BigDecimal("100000"), 
    new BigDecimal("200000")
);

// Akses informasi detail
e.getErrorCode();           // "INSUFFICIENT_BALANCE"
e.getUserMessage();         // "Saldo tidak mencukupi"
e.getTechnicalMessage();    // "Insufficient balance. Current: 100000, Required: 200000"
e.getContext();             // Map dengan currentBalance, requiredAmount, shortfall
e.getTimestamp();           // LocalDateTime saat exception terjadi
```

### 2. Specific Exception Handling
```java
try {
    transactionService.sendMoneyUsingCard(fromAccount, toAccount, amount);
} catch (AccountException.InsufficientBalance e) {
    // Handle saldo tidak mencukupi dengan spesifik
    log.error("Saldo tidak mencukupi: " + e.getUserMessage());
    log.error("Saldo saat ini: " + e.getContext().get("currentBalance"));
    log.error("Kekurangan: " + e.getContext().get("shortfall"));
} catch (TransactionException.DailyLimitExceeded e) {
    // Handle batas harian dengan spesifik
    log.error("Batas harian terlampaui: " + e.getUserMessage());
    log.error("Total harian: " + e.getContext().get("currentTotal"));
    log.error("Batas maksimal: " + e.getContext().get("limit"));
} catch (CardException.CardNotFound e) {
    // Handle kartu tidak ditemukan
    log.error("Kartu tidak ditemukan: " + e.getUserMessage());
}
```

### 3. Exception Handler Utility
```java
ExceptionHandler handler = new ExceptionHandler(logService);

// Handle dengan logging konsisten
handler.handleBankingException(e, "Transfer Operation");

// Handle dengan retry logic
boolean success = handler.handleWithRetry(() -> {
    transactionService.sendMoneyUsingCard(from, to, amount);
}, "Transfer Operation", 3);
```

## Best Practices

### 1. Exception Hierarchy
- Gunakan exception yang paling spesifik untuk setiap skenario
- Jangan catch generic Exception kecuali untuk fallback
- Gunakan BankingException sebagai base untuk semua business exception

### 2. Context Information
- Selalu sertakan context yang relevan dalam exception
- Gunakan key yang konsisten untuk context (accountNumber, amount, dll)
- Sertakan nilai yang diperlukan untuk debugging

### 3. Error Messages
- Pisahkan pesan untuk user dan technical
- User message: Bahasa Indonesia, user-friendly
- Technical message: Bahasa Inggris, detail untuk developer

### 4. Logging
- Gunakan ExceptionHandler untuk logging yang konsisten
- Log error code, user message, technical message, dan context
- Sertakan timestamp dan operation name

## Contoh Penggunaan

### Di Service Layer
```java
public void transferMoney(String fromAccount, String toAccount, BigDecimal amount) {
    // Validasi input
    if (amount.compareTo(BigDecimal.ZERO) <= 0) {
        throw new TransactionException.InvalidTransactionAmount(amount);
    }
    
    if (fromAccount.equals(toAccount)) {
        throw new TransactionException.SameAccountTransfer(fromAccount);
    }
    
    // Business logic dengan exception yang spesifik
    AccountEntity origin = accountRepository.findByAccountNumber(fromAccount)
        .orElseThrow(() -> new AccountException.AccountNotFound(fromAccount));
    
    // ... rest of the logic
}
```

### Di Application Layer
```java
try {
    transactionService.transferMoney(fromAccount, toAccount, amount);
    log.info("Transfer berhasil");
} catch (AccountException.InsufficientBalance e) {
    exceptionHandler.handleBankingException(e, "Transfer Money");
    // Handle insufficient balance
} catch (TransactionException.DailyLimitExceeded e) {
    exceptionHandler.handleBankingException(e, "Transfer Money");
    // Handle daily limit exceeded
} catch (BankingException e) {
    exceptionHandler.handleBankingException(e, "Transfer Money");
    // Handle other banking exceptions
} catch (Exception e) {
    exceptionHandler.handleGeneralException(e, "Transfer Money");
    // Handle unexpected exceptions
}
```

## Migration dari Exception Lama

### Sebelum (Exception Lama)
```java
try {
    accountService.updatePin(account, oldPin, newPin);
} catch (InvalidPin e) {
    log.error("PIN salah");
}
```

### Sesudah (Exception Baru)
```java
try {
    accountService.updatePin(account, oldPin, newPin);
} catch (CardException.InvalidPin e) {
    log.error("PIN salah: " + e.getUserMessage());
    log.error("Account ID: " + e.getContext().get("accountId"));
    log.error("Attempt: " + e.getContext().get("attempts"));
} catch (CardException.CardNotFound e) {
    log.error("Kartu tidak ditemukan: " + e.getUserMessage());
}
```

Sistem exception handling yang baru memberikan kontrol yang lebih baik, informasi yang lebih detail, dan penanganan error yang lebih spesifik untuk aplikasi perbankan.
