package domain.util;

import application.service.LogService;
import domain.exception.BankingException;

/**
 * Utility class untuk menangani exception dengan konsisten dalam sistem perbankan.
 * 
 * <p>Class ini menyediakan method untuk menangani berbagai jenis exception
 * dengan logging yang terstruktur dan retry logic untuk operasi yang dapat diulang.</p>
 * 
 * @author Gede Dhanu Purnayasa
 * @author Made Marsel Biliana Wijaya
 * @since 1.0
 */
public final class ExceptionHandler {
    private final LogService logService;
    
    /**
     * Konstruktor untuk ExceptionHandler.
     * 
     * @param logService service untuk logging
     */
    public ExceptionHandler(LogService logService) {
        this.logService = logService;
    }
    
    /**
     * Menangani BankingException dengan logging yang konsisten.
     * 
     * <p>Method ini akan menampilkan informasi lengkap tentang exception
     * termasuk error code, pesan user, pesan teknis, timestamp, dan context.</p>
     * 
     * @param e BankingException yang akan ditangani
     * @param operation nama operasi yang menyebabkan exception
     */
    public void handleBankingException(BankingException e, String operation) {
        logService.error("=== " + operation + " GAGAL ===");
        logService.error("Error Code: " + e.getErrorCode());
        logService.error("Pesan User: " + e.getUserMessage());
        logService.error("Pesan Teknis: " + e.getTechnicalMessage());
        logService.error("Timestamp: " + e.getTimestamp());
        
        if (!e.getContext().isEmpty()) {
            logService.error("Context:");
            e.getContext().forEach((key, value) -> 
                logService.error("  " + key + ": " + value)
            );
        }
        logService.error("================================");
    }
    
    /**
     * Menangani exception umum dengan logging yang konsisten.
     * 
     * <p>Method ini digunakan untuk menangani exception yang bukan BankingException
     * dengan menampilkan informasi dasar tentang exception tersebut.</p>
     * 
     * @param e Exception yang akan ditangani
     * @param operation nama operasi yang menyebabkan exception
     */
    public void handleGeneralException(Exception e, String operation) {
        logService.error("=== " + operation + " GAGAL ===");
        logService.error("Error: " + e.getClass().getSimpleName());
        logService.error("Message: " + e.getMessage());
        logService.error("================================");
    }
    
    /**
     * Menangani exception dengan retry logic untuk operasi yang dapat diulang.
     * 
     * <p>Method ini akan mencoba menjalankan operasi hingga berhasil atau
     * mencapai batas maksimal percobaan. Jika gagal, akan menampilkan
     * informasi exception yang terjadi.</p>
     * 
     * @param operation operasi yang akan dijalankan
     * @param operationName nama operasi untuk logging
     * @param maxRetries jumlah maksimal percobaan
     * @return true jika operasi berhasil, false jika gagal setelah semua percobaan
     */
    public boolean handleWithRetry(Runnable operation, String operationName, int maxRetries) {
        for (int i = 0; i < maxRetries; i++) {
            try {
                operation.run();
                return true;
            } catch (BankingException e) {
                if (i == maxRetries - 1) {
                    handleBankingException(e, operationName + " (Retry " + (i + 1) + ")");
                    return false;
                } else {
                    logService.warning(operationName + " gagal, mencoba lagi... (Attempt " + (i + 1) + ")");
                }
            } catch (Exception e) {
                if (i == maxRetries - 1) {
                    handleGeneralException(e, operationName + " (Retry " + (i + 1) + ")");
                    return false;
                } else {
                    logService.warning(operationName + " gagal, mencoba lagi... (Attempt " + (i + 1) + ")");
                }
            }
        }
        return false;
    }
}
